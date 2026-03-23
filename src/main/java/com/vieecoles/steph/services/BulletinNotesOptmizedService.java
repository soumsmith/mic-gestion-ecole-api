package com.vieecoles.steph.services;
// ============================================================
//  Service principal — calcul optimisé des bulletins
// ============================================================

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.vieecoles.steph.entities.AbsenceEleve;
import com.vieecoles.steph.entities.Bulletin;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.ClasseEleve;
import com.vieecoles.steph.entities.ClasseEleveMatiere;
import com.vieecoles.steph.entities.ClasseElevePeriode;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.EcoleHasMatiere;
import com.vieecoles.steph.entities.Eleve;
import com.vieecoles.steph.entities.MoyenneAdjustment;
import com.vieecoles.steph.entities.Notes;
import com.vieecoles.steph.entities.Periode;
import com.vieecoles.steph.util.CommonUtils;
import com.vieecoles.steph.dto.bulletin.BulletinClasseResultDto;
import com.vieecoles.steph.dto.bulletin.BulletinEleveResultDto;
import com.vieecoles.steph.dto.bulletin.ClasseInfoDto;
import com.vieecoles.steph.dto.bulletin.EcoleInfoDto;
import com.vieecoles.steph.dto.bulletin.InfoAnnuelleEleveDto;
import com.vieecoles.steph.dto.bulletin.MatiereResultDto;
import com.vieecoles.steph.dto.bulletin.NoteDetailDto;
import com.vieecoles.steph.dto.bulletin.StatClasseDto;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;

/**
 * Service de calcul des bulletins scolaires.
 *
 * <p><b>Stratégie de parallélisation :</b>
 * <ul>
 *   <li>Phase 1 – Collecte des données : un seul accès DB groupé par classe/année/période.
 *   <li>Phase 2 – Calcul par élève : parallélisé via {@link ForkJoinPool} (stream parallèle).
 *       Chaque élève est traité indépendamment dans son propre thread.
 *   <li>Phase 3 – Classement : sequential, car nécessite la vue complète de la classe.
 * </ul>
 *
 * <p><b>Complexité :</b> O(E × M) où E = nombre d'élèves, M = nombre de matières.
 * La parallélisation réduit le temps réel d'un facteur proche du nombre de cœurs disponibles.
 */
@RequestScoped
public class BulletinNotesOptmizedService {
 
    // ── Constantes de calcul ───────────────────────────────────────────────────
    private static final int    PRECISION           = 2;
    private static final double DEFAULT_DIVISOR     = 1.0;
    private static final double ZERO                = 0.0;
    private static final double MOYENNE_NON_CLASSE  = -999.0;
    private static final int    TEST_LOURD_POIDS    = 2;  // poids du test lourd dans la formule
    private static final int    POIDS_TOTAL_DIVISOR = 3;  // dénominateur (1 normal + 2 test lourd)
 
    // ── Services injectés ─────────────────────────────────────────────────────
    @Inject ClasseEleveService         classeEleveService;
    @Inject ClasseService              classeService;
    @Inject EvaluationService          evaluationService;
    @Inject ClasseEleveMatiereService  classeEleveMatiereService;
    @Inject ClasseElevePeriodeService  classeElevePeriodeService;
    @Inject AbsenceService             absenceService;
    @Inject MoyenneAdjustmentService   adjustmentService;
    @Inject ClasseMatiereService       classeMatiereService;
    @Inject BulletinService            bulletinService;
    @Inject DetailBulletinService      detailBulletinService;
    @Inject EcoleService               ecoleService;
 
    // ══════════════════════════════════════════════════════════════════════════
    //  POINT D'ENTRÉE PRINCIPAL
    // ══════════════════════════════════════════════════════════════════════════
 
    /**
     * Calcule les bulletins de toute une classe pour une période donnée.
     *
     * @param classeId  ID de la classe
     * @param anneeId   ID de l'année scolaire
     * @param periodeId ID de la période
     * @return résultat complet de la classe, trié par rang
     */
    @ActivateRequestContext
    public BulletinClasseResultDto calculerBulletinsClasse(Long classeId, Long anneeId, Long periodeId) {
 
        // ── Phase 0 : Chargement des référentiels (une seule fois) ────────────
        Classe classe     = classeService.findById(classeId);
        Periode periode   = Periode.findById(periodeId);
        boolean estFinale = "O".equals(periode.getIsfinal());
 
        // Coefficients des matières pour la branche (Map matiereId → coef)
        Map<Long, Double> coefParMatiere = chargerCoefficients(classe);
 
        // Toutes les notes pec=1 de la classe pour cette période, groupées par élève
        // Structure : matricule → (matiereId → List<Notes>)
        Map<String, Map<Long, List<Notes>>> notesParEleveEtMatiere =
                chargerEtGrouperNotes(classeId, anneeId, periodeId);
 
        // Tous les ajustements valides (repêchages) groupés par matricule → matiereId
        Map<String, Map<Long, MoyenneAdjustment>> ajustementsParEleve =
                chargerAjustements(anneeId, periodeId, classe);
 
        // Restrictions de classement par élève dans chaque matière
        Map<Long, Map<Long, Boolean>> restrictionsParEleve =
                chargerRestrictionsMatiere(classeId, anneeId, periodeId, classe);
 
        // Statuts de classement général par élève sur la période
        Map<Long, Boolean> classeElevePeriodeMap =
                chargerClassementPeriode(classeId, anneeId, periodeId, classe);
 
        // Absences par élève
        Map<Long, AbsenceEleve> absencesParEleve =
                chargerAbsences(anneeId, periodeId, classe);
 
        // Liste des ClasseEleve pour associer les élèves
        List<ClasseEleve> classeEleves =
                classeEleveService.getByClasseAnnee(classeId, anneeId);
 
        // ── Phase 1 : Calcul par élève en parallèle ───────────────────────────
        //   Chaque élève est traité de façon complètement indépendante.
        //   ConcurrentHashMap pour la collecte thread-safe des résultats.
        List<BulletinEleveResultDto> resultatsEleves =
                classeEleves.parallelStream()
                        .map(ce -> calculerBulletinEleve(
                                ce,
                                notesParEleveEtMatiere,
                                ajustementsParEleve,
                                restrictionsParEleve,
                                classeElevePeriodeMap,
                                absencesParEleve,
                                coefParMatiere,
                                classe))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
 
        // ── Phase 2 : Classement (séquentiel, vue globale requise) ────────────
        attribuerRangsParMatiere(resultatsEleves, classe, coefParMatiere);
        attribuerRangsGeneraux(resultatsEleves);
 
        // ── Phase 3 : Calcul annuel si période finale ─────────────────────────
        if (estFinale) {
            calculerMoyennesAnnuelles(resultatsEleves, classe, periode, classeEleves);
        }
 
        // ── Phase 4 : Assemblage du résultat classe ───────────────────────────
        resultatsEleves.sort(Comparator.comparingInt(BulletinEleveResultDto::getRang));
 
        BulletinClasseResultDto resultat = new BulletinClasseResultDto();
        resultat.setClasse(new ClasseInfoDto(
                classe.getId(), classe.getCode(), classe.getLibelle(),
                classe.getBranche() != null ? classe.getBranche().getLibelle() : null));
        resultat.setEcole(new EcoleInfoDto(
                classe.getEcole().getId(), classe.getEcole().getCode(), classe.getEcole().getLibelle()));
        resultat.setPeriodeLibelle(periode.getLibelle());
        resultat.setEstPeriodeFinale(estFinale);
        resultat.setEleves(resultatsEleves);
        resultat.setStatsClasse(calculerStatsClasse(resultatsEleves));
 
        return resultat;
    }
 
    // ══════════════════════════════════════════════════════════════════════════
    //  CALCUL D'UN SEUL ÉLÈVE  (exécuté en parallèle)
    // ══════════════════════════════════════════════════════════════════════════
 
    /**
     * Calcule le bulletin d'un élève : notes, moyennes par matière, moyenne générale.
     * Cette méthode est conçue pour être appelée dans un stream parallèle.
     * Elle est pure (pas d'état partagé mutable) et thread-safe.
     */
    private BulletinEleveResultDto calculerBulletinEleve(
            ClasseEleve ce,
            Map<String, Map<Long, List<Notes>>> notesParEleveEtMatiere,
            Map<String, Map<Long, MoyenneAdjustment>> ajustementsParEleve,
            Map<Long, Map<Long, Boolean>> restrictionsParEleve,
            Map<Long, Boolean> classeElevePeriodeMap,
            Map<Long, AbsenceEleve> absencesParEleve,
            Map<Long, Double> coefParMatiere,
            Classe classe) {
 
        Eleve eleve = ce.getInscription().getEleve();
        String matricule = eleve.getMatricule();
 
        // Notes de cet élève groupées par matière
        Map<Long, List<Notes>> notesParMatiere =
                notesParEleveEtMatiere.getOrDefault(matricule, Collections.emptyMap());
 
        if (notesParMatiere.isEmpty()) {
            return null; // élève sans notes : ignoré
        }
 
        // Ajustements (repêchages) de cet élève
        Map<Long, MoyenneAdjustment> ajustements =
                ajustementsParEleve.getOrDefault(matricule, Collections.emptyMap());
 
        // Restrictions matière de cet élève
        Map<Long, Boolean> restrictions =
                restrictionsParEleve.getOrDefault(ce.getId(), Collections.emptyMap());
 
        // Statut de classement de l'élève pour la période
        boolean estClasse = classeElevePeriodeMap.getOrDefault(eleve.getId(), true);
 
        // ── Calcul par matière ────────────────────────────────────────────────
        List<MatiereResultDto> matiereResults = new ArrayList<>();
        double sommePonderee = 0.0;
        double sommeCoefs    = 0.0;
 
        for (Map.Entry<Long, List<Notes>> entry : notesParMatiere.entrySet()) {
            Long matiereId   = entry.getKey();
            List<Notes> notes = entry.getValue();
 
            // On prend la première note pour les métadonnées de la matière
            Notes ref = notes.stream()
                    .filter(n -> n.getEvaluation() != null)
                    .findFirst().orElse(null);
            if (ref == null) continue;
 
            EcoleHasMatiere ehm = ref.getEvaluation().getMatiereEcole();
 
            // Calcul de la moyenne pour cette matière
            double coef    = coefParMatiere.getOrDefault(matiereId, 1.0);
            double moyenne = calculerMoyenneMatiere(notes, ajustements.get(matiereId));
 
            // Est-ce que l'élève est classé dans cette matière ?
            boolean classeMatiere = restrictions.getOrDefault(matiereId, true);
 
            // Construction du DTO matière
            MatiereResultDto matiereDto = new MatiereResultDto();
            matiereDto.setMatiereId(matiereId);
            matiereDto.setMatiereCode(ehm.getCode());
            matiereDto.setMatiereLibelle(ehm.getLibelle());
            matiereDto.setCoef(coef);
            matiereDto.setMoyenne(moyenne);
            matiereDto.setAppreciation(CommonUtils.appreciation(moyenne));
            matiereDto.setEstAjuste(ajustements.containsKey(matiereId));
 
            // Récupération du professeur si disponible
			/*
			 * if (ehm.getProfesseur() != null) {
			 * matiereDto.setProfesseurNom(ehm.getProfesseur().getNom());
			 * matiereDto.setProfesseurPrenom(ehm.getProfesseur().getPrenom()); }
			 */
 
            // Notes détaillées
            matiereDto.setNotes(construireNotesDetail(notes));
 
            matiereResults.add(matiereDto);
 
            // Cumul pour la moyenne générale (uniquement si pec=1 et élève classé)
            if (ehm.getPec() != null && ehm.getPec() == 1 && classeMatiere && estClasse) {
                if (ehm.getBonus() != null && ehm.getBonus() == 1) {
                    // Matière bonus : seul l'excédent au-dessus de 10 est ajouté
                    double bonus = Math.max(0.0, moyenne - 10.0);
                    sommePonderee += bonus;
                    // Le coef bonus n'est pas ajouté au dénominateur
                } else {
                    sommePonderee += moyenne * coef;
                    sommeCoefs    += coef;
                }
            }
        }
 
        // Moyenne générale pondérée
        double moyenneGenerale = sommeCoefs > ZERO
                ? arrondi(sommePonderee / sommeCoefs)
                : ZERO;
 
        // Absences
        AbsenceEleve abs = absencesParEleve.get(eleve.getId());
 
        // ── Construction du DTO élève ─────────────────────────────────────────
        BulletinEleveResultDto dto = new BulletinEleveResultDto();
        dto.setMatricule(matricule);
        dto.setNom(eleve.getNom());
        dto.setPrenom(eleve.getPrenom());
        dto.setUrlPhoto(eleve.getUrlPhoto());
        dto.setSexe(eleve.getSexe());
        dto.setMoyenneGenerale(moyenneGenerale);
        dto.setAppreciation(CommonUtils.appreciation(moyenneGenerale));
        dto.setEstClasse(estClasse);
        dto.setAbsencesJustifiees(abs != null ? abs.getAbsJustifiee() : 0);
        dto.setAbsencesNonJustifiees(abs != null ? abs.getAbsNonJustifiee() : 0);
        dto.setMatieres(matiereResults);
 
        return dto;
    }
 
    // ══════════════════════════════════════════════════════════════════════════
    //  CALCUL DE MOYENNE D'UNE MATIÈRE
    // ══════════════════════════════════════════════════════════════════════════
 
    /**
     * Calcule la moyenne d'une matière pour un élève.
     *
     * <p>Règles appliquées :
     * <ol>
     *   <li>Si un ajustement (repêchage) existe → utiliser directement la moyenne ajustée.
     *   <li>Séparer les notes normales et les tests lourds (code {@code CODE_TEST_LOURD}).
     *   <li>Moyenne normale = Σ(notes) / Σ(noteSur / 20).
     *   <li>Si test lourd présent → moyenne finale = (moy_normale + moy_test_lourd × 2) / 3.
     * </ol>
     *
     * @param notes      liste des notes pec=1 pour cette matière
     * @param ajustement ajustement éventuel (peut être null)
     * @return moyenne arrondie à 2 décimales
     */
    private double calculerMoyenneMatiere(List<Notes> notes, MoyenneAdjustment ajustement) {
 
        // Cas 1 : repêchage saisi manuellement
        if (ajustement != null && ajustement.getId() != null) {
            return arrondi(ajustement.getMoyenne());
        }
 
        // Cas 2 : calcul standard
        double sommeNormale      = 0.0, diviseurNormale      = 0.0;
        double sommeTestLourd    = 0.0, diviseurTestLourd    = 0.0;
 
        for (Notes note : notes) {
            // Seules les notes avec evaluation.pec=1 ET note.pec=1 comptent
            if (!isNotePriseEnCompte(note)) continue;
 
            double valeur  = note.getNote();
            double noteSur = Double.parseDouble(note.getEvaluation().getNoteSur());
            double poids   = noteSur / Double.parseDouble(Constants.DEFAULT_NOTE_SUR);
 
            if (isTestLourd(note)) {
                sommeTestLourd   += valeur;
                diviseurTestLourd += poids;
            } else {
                sommeNormale   += valeur;
                diviseurNormale += poids;
            }
        }
 
        double moyenneNormale = diviseurNormale > ZERO
                ? sommeNormale / diviseurNormale
                : sommeNormale; // évite division par zéro
 
        if (diviseurTestLourd == ZERO) {
            // Pas de test lourd → formule classique
            return arrondi(moyenneNormale);
        }
 
        // Test lourd présent → formule pondérée
        double moyenneTestLourd = sommeTestLourd / diviseurTestLourd;
        double moyenneFinale    =
                (moyenneNormale + moyenneTestLourd * TEST_LOURD_POIDS) / POIDS_TOTAL_DIVISOR;
 
        return arrondi(moyenneFinale);
    }
 
    // ══════════════════════════════════════════════════════════════════════════
    //  CLASSEMENT PAR MATIÈRE  (séquentiel)
    // ══════════════════════════════════════════════════════════════════════════
 
    /**
     * Attribue les rangs par matière à tous les élèves.
     * Les ex-æquo reçoivent le même rang.
     * Les élèves non classés dans une matière sont mis en fin de liste.
     */
    private void attribuerRangsParMatiere(
            List<BulletinEleveResultDto> eleves,
            Classe classe,
            Map<Long, Double> coefParMatiere) {
 
        // Récupérer les IDs de toutes les matières présentes
        Set<Long> toutesLesMatieres = eleves.stream()
                .flatMap(e -> e.getMatieres().stream())
                .map(MatiereResultDto::getMatiereId)
                .collect(Collectors.toSet());
 
        for (Long matiereId : toutesLesMatieres) {
            // Collecter les élèves qui ont cette matière, triés par moyenne décroissante
            // Les non-classés vont en bas (MOYENNE_NON_CLASSE)
            List<BulletinEleveResultDto> elevesAvecMatiere = eleves.stream()
                    .filter(e -> e.getMatieres().stream()
                            .anyMatch(m -> matiereId.equals(m.getMatiereId())))
                    .sorted((e1, e2) -> {
                        double m1 = getMoyenneMatiere(e1, matiereId, !e1.isEstClasse());
                        double m2 = getMoyenneMatiere(e2, matiereId, !e2.isEstClasse());
                        return Double.compare(m2, m1); // décroissant
                    })
                    .collect(Collectors.toList());
 
            // Attribution des rangs avec gestion des ex-æquo
            int rang           = 0;
            int compteur       = 0;
            double prevMoyenne = Double.MAX_VALUE;
 
            for (BulletinEleveResultDto eleve : elevesAvecMatiere) {
                compteur++;
                double moy = getMoyenneMatiere(eleve, matiereId, !eleve.isEstClasse());
 
                if (Double.compare(moy, prevMoyenne) != 0) {
                    rang = compteur;
                    prevMoyenne = moy;
                }
                final int rangFinal = rang; 
                // Mise à jour du rang dans le DTO matière
                eleve.getMatieres().stream()
                        .filter(m -> matiereId.equals(m.getMatiereId()))
                        .findFirst()
                        .ifPresent(m -> m.setRang(moy <= MOYENNE_NON_CLASSE ? "-" : String.valueOf(rangFinal)));
            }
        }
    }
 
    // ══════════════════════════════════════════════════════════════════════════
    //  CLASSEMENT GÉNÉRAL  (séquentiel)
    // ══════════════════════════════════════════════════════════════════════════
 
    /**
     * Attribue les rangs généraux à tous les élèves classés,
     * en gérant les ex-æquo.
     */
    private void attribuerRangsGeneraux(List<BulletinEleveResultDto> eleves) {
        // Trier par moyenne décroissante, les non-classés en dernier
        List<BulletinEleveResultDto> classes = eleves.stream()
                .filter(BulletinEleveResultDto::isEstClasse)
                .sorted(Comparator.comparingDouble(BulletinEleveResultDto::getMoyenneGenerale).reversed())
                .collect(Collectors.toList());
 
        int rang           = 0;
        int compteur       = 0;
        double prevMoyenne = Double.MAX_VALUE;
 
        for (BulletinEleveResultDto eleve : classes) {
            compteur++;
            if (Double.compare(eleve.getMoyenneGenerale(), prevMoyenne) != 0) {
                rang = compteur;
                prevMoyenne = eleve.getMoyenneGenerale();
            }
            eleve.setRang(rang);
        }
 
        // Élèves non classés → rang = 0
        eleves.stream()
                .filter(e -> !e.isEstClasse())
                .forEach(e -> e.setRang(0));
    }
 
    // ══════════════════════════════════════════════════════════════════════════
    //  CALCUL ANNUEL  (période finale uniquement)
    // ══════════════════════════════════════════════════════════════════════════
 
    /**
     * Calcule les moyennes annuelles pour chaque élève et les rangs annuels.
     * Utilise les bulletins des périodes précédentes et les coefficients de période.
     */
    private void calculerMoyennesAnnuellesOld(
            List<BulletinEleveResultDto> eleves,
            Classe classe,
            Periode periodeCourante,
            List<ClasseEleve> classeEleves) {
 
        // Récupérer toutes les périodes de la périodicité jusqu'à la période finale
        List<Periode> toutesLesPeriodes = Periode.find(
                "niveau <= ?1 and periodicite.id = ?2 order by niveau",
                periodeCourante.getNiveau(),
                periodeCourante.getPeriodicite().getId()).list();
 
        double coefFinal = periodeCourante.getCoef() != null && !periodeCourante.getCoef().isBlank()
                ? Double.parseDouble(periodeCourante.getCoef())
                : 1.0;
 
        // Collecter les moyennes annuelles pour le classement
        List<Double> classeurAnnuel = Collections.synchronizedList(new ArrayList<>());
 
        // Calcul en parallèle par élève
        Map<String, BulletinEleveResultDto> eleveParMatricule = eleves.stream()
                .collect(Collectors.toMap(BulletinEleveResultDto::getMatricule, e -> e));
 
        classeEleves.parallelStream().forEach(ce -> {
            String matricule = ce.getInscription().getEleve().getMatricule();
            BulletinEleveResultDto eleveDto = eleveParMatricule.get(matricule);
            if (eleveDto == null) return;
 
            // Bulletins des périodes précédentes
            List<Bulletin> bulletins = bulletinService.getBulletinsEleveByAnnee(
            		ce.getInscription().getAnnee().getId(),
                    matricule,
                    ce.getClasse().getId());
 
            double moyAn  = 0.0;
            double coefAn = 0.0;
 
            // Accumuler les moyennes des périodes non finales depuis les bulletins
            for (Bulletin bul : bulletins) {
                for (Periode p : toutesLesPeriodes) {
                    if (bul.getPeriodeId().equals(p.getId())
                            && "O".equals(bul.getIsClassed())
                            && p.getIsfinal() == null) {
 
                        double coefP = p.getCoef() != null && !p.getCoef().isBlank()
                                ? Double.parseDouble(p.getCoef()) : 1.0;
                        moyAn  += bul.getMoyGeneral() * coefP;
                        coefAn += coefP;
                        break;
                    }
                }
            }
 
            // Ajouter la période courante (finale)
            if (eleveDto.isEstClasse()) {
                moyAn  += eleveDto.getMoyenneGenerale() * coefFinal;
                coefAn += coefFinal;
            }
 
            double moyenneAnnuelle = coefAn > ZERO
                    ? arrondi(moyAn / coefAn)
                    : 0.0;
 
            // Construction du DTO annuel
            InfoAnnuelleEleveDto infoAn = new InfoAnnuelleEleveDto();
            infoAn.setMoyenneAnnuelle(moyenneAnnuelle);
            infoAn.setAppreciationAnnuelle(CommonUtils.appreciation(moyenneAnnuelle));
            eleveDto.setInfoAnnuelle(infoAn);
 
            classeurAnnuel.add(moyenneAnnuelle);
        });
 
        // Trier le classeur annuel pour les rangs
        List<Double> classeTriee = classeurAnnuel.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
 
        // Attribuer les rangs annuels
        eleves.stream()
                .filter(e -> e.getInfoAnnuelle() != null)
                .forEach(e -> {
                    int rangAn = getRangParValeur(classeTriee, e.getInfoAnnuelle().getMoyenneAnnuelle());
                    e.getInfoAnnuelle().setRangAnnuel(rangAn > 0 ? String.valueOf(rangAn) : "-");
                });
    }
    
    private void calculerMoyennesAnnuelles(
            List<BulletinEleveResultDto> eleves,
            Classe classe,
            Periode periodeCourante,
            List<ClasseEleve> classeEleves) {

        List<Periode> toutesLesPeriodes = Periode.find(
                "niveau <= ?1 and periodicite.id = ?2 order by niveau",
                periodeCourante.getNiveau(),
                periodeCourante.getPeriodicite().getId()).list();

        double coefFinal = periodeCourante.getCoef() != null && !periodeCourante.getCoef().isBlank()
                ? Double.parseDouble(periodeCourante.getCoef()) : 1.0;

        Map<String, BulletinEleveResultDto> eleveParMatricule = eleves.stream()
                .collect(Collectors.toMap(BulletinEleveResultDto::getMatricule, e -> e));

        // ✅ Pré-chargement dans le thread principal (contexte CDI actif)
        Map<String, List<Bulletin>> bulletinsParMatricule = classeEleves.stream()
                .collect(Collectors.toMap(
                        ce -> ce.getInscription().getEleve().getMatricule(),
                        ce -> bulletinService.getBulletinsEleveByAnnee(
                        		ce.getInscription().getAnnee().getId(),
                                ce.getInscription().getEleve().getMatricule(),
                                ce.getClasse().getId())
                ));

        List<Double> classeurAnnuel = Collections.synchronizedList(new ArrayList<>());

        // ✅ Le lambda ne touche plus aucun bean CDI
        classeEleves.parallelStream().forEach(ce -> {
            String matricule = ce.getInscription().getEleve().getMatricule();
            BulletinEleveResultDto eleveDto = eleveParMatricule.get(matricule);
            if (eleveDto == null) return;

            List<Bulletin> bulletins = bulletinsParMatricule.getOrDefault(matricule, List.of());

            double moyAn  = 0.0;
            double coefAn = 0.0;

            for (Bulletin bul : bulletins) {
                for (Periode p : toutesLesPeriodes) {
                    if (bul.getPeriodeId().equals(p.getId())
                            && "O".equals(bul.getIsClassed())
                            && p.getIsfinal() == null) {

                        double coefP = p.getCoef() != null && !p.getCoef().isBlank()
                                ? Double.parseDouble(p.getCoef()) : 1.0;
                        moyAn  += bul.getMoyGeneral() * coefP;
                        coefAn += coefP;
                        break;
                    }
                }
            }

            if (eleveDto.isEstClasse()) {
                moyAn  += eleveDto.getMoyenneGenerale() * coefFinal;
                coefAn += coefFinal;
            }

            double moyenneAnnuelle = coefAn > 0 ? arrondi(moyAn / coefAn) : 0.0;

            InfoAnnuelleEleveDto infoAn = new InfoAnnuelleEleveDto();
            infoAn.setMoyenneAnnuelle(moyenneAnnuelle);
            infoAn.setAppreciationAnnuelle(CommonUtils.appreciation(moyenneAnnuelle));
            eleveDto.setInfoAnnuelle(infoAn);

            classeurAnnuel.add(moyenneAnnuelle);
        });

        // Rangs annuels (séquentiel)
        List<Double> classeTriee = classeurAnnuel.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        eleves.stream()
                .filter(e -> e.getInfoAnnuelle() != null)
                .forEach(e -> {
                    int rangAn = getRangParValeur(classeTriee, e.getInfoAnnuelle().getMoyenneAnnuelle());
                    e.getInfoAnnuelle().setRangAnnuel(rangAn > 0 ? String.valueOf(rangAn) : "-");
                });
    }
 
    // ══════════════════════════════════════════════════════════════════════════
    //  CHARGEMENT DES DONNÉES  (requêtes groupées)
    // ══════════════════════════════════════════════════════════════════════════
 
    /**
     * Charge et groupe toutes les notes pec=1 de la classe pour la période.
     * Structure : matricule → matiereId → List&lt;Notes&gt;
     *
     * <p>Une seule requête DB pour toute la classe (vs une requête par élève avant).
     */
    private Map<String, Map<Long, List<Notes>>> chargerEtGrouperNotes(
            Long classeId, Long anneeId, Long periodeId) {
 
        List<Notes> toutesNotes = Notes.find(
                "evaluation.classe.id = ?1 " +
                "AND evaluation.annee.id = ?2 " +
                "AND evaluation.periode.id = ?3 " +
                "AND pec = ?4 " +
                "AND evaluation.pec = ?4",
                classeId, anneeId, periodeId, Constants.PEC_1).list();
 
        // Grouper : matricule → matiereId → notes
        return toutesNotes.stream().collect(Collectors.groupingBy(
                n -> n.getClasseEleve().getInscription().getEleve().getMatricule(),
                Collectors.groupingBy(
                        n -> n.getEvaluation().getMatiereEcole().getId()
                )
        ));
    }
 
    /**
     * Charge les coefficients des matières pour la branche de la classe.
     * Retourne un Map : matiereId → coef (double).
     */
    private Map<Long, Double> chargerCoefficients(Classe classe) {
        return classeMatiereService
                .getByBranche(classe.getBranche().getId(), classe.getEcole().getId())
                .stream()
                .collect(Collectors.toMap(
                        cm -> cm.getMatiere().getId(),
                        cm -> {
                            try { return Double.parseDouble(cm.getCoef()); }
                            catch (NumberFormatException e) { return 1.0; }
                        },
                        (a, b) -> a // en cas de doublon, garder le premier
                ));
    }
 
    /**
     * Charge tous les ajustements de moyenne valides pour la classe/période.
     * Structure : matricule → matiereId → MoyenneAdjustment
     */
    private Map<String, Map<Long, MoyenneAdjustment>> chargerAjustements(
            Long anneeId, Long periodeId, Classe classe) {
 
        // Récupérer tous les élèves de la classe
        List<ClasseEleve> classeEleves =
                classeEleveService.getByClasseAnnee(classe.getId(),
                		anneeId);
 
        Map<String, Map<Long, MoyenneAdjustment>> result = new HashMap<>();
 
        for (ClasseEleve ce : classeEleves) {
            String matricule = ce.getInscription().getEleve().getMatricule();
            List<MoyenneAdjustment> ajustements = adjustmentService
                    .getByAnneePeriodeMatriculeAndStatut(anneeId, periodeId, matricule, Constants.VALID);
 
            if (!ajustements.isEmpty()) {
                result.put(matricule, ajustements.stream()
                        .collect(Collectors.toMap(MoyenneAdjustment::getMatiere, a -> a)));
            }
        }
        return result;
    }
 
    /**
     * Charge les restrictions de classement par élève et par matière.
     * Structure : classeEleveId → matiereId → estClasse (true = classé)
     */
    private Map<Long, Map<Long, Boolean>> chargerRestrictionsMatiere(
            Long classeId, Long anneeId, Long periodeId, Classe classe) {
 
        List<ClasseEleve> classeEleves =
                classeEleveService.getByClasseAnnee(classeId,
                		anneeId);
 
        Map<Long, Map<Long, Boolean>> result = new HashMap<>();
        for (ClasseEleve ce : classeEleves) {
            List<ClasseEleveMatiere> restrictions = classeEleveMatiereService
                    .findByClasseAndEleveAndAnneeAndPeriode(
                            classeId, ce.getInscription().getEleve().getId(), anneeId, periodeId);
 
            Map<Long, Boolean> restrMatiere = restrictions.stream()
                    .collect(Collectors.toMap(
                            r -> r.getMatiere().getId(),
                            r -> !Constants.NON.equals(r.getIsClassed())
                    ));
            result.put(ce.getId(), restrMatiere);
        }
        return result;
    }
 
    /**
     * Charge les statuts de classement général par élève sur la période.
     * Structure : eleveId → estClasse
     */
    private Map<Long, Boolean> chargerClassementPeriode(
            Long classeId, Long anneeId, Long periodeId, Classe classe) {
 
        List<ClasseEleve> classeEleves =
                classeEleveService.getByClasseAnnee(classeId,
                		anneeId);
 
        Map<Long, Boolean> result = new HashMap<>();
        for (ClasseEleve ce : classeEleves) {
            ClasseElevePeriode cep = classeElevePeriodeService
                    .findByClasseAndEleveAndAnneeAndPeriode(
                            classeId, ce.getInscription().getEleve().getId(), anneeId, periodeId);
            boolean estClasse = cep == null || !Constants.NON.equals(cep.getIsClassed());
            result.put(ce.getInscription().getEleve().getId(), estClasse);
        }
        return result;
    }
 
    /**
     * Charge les absences de tous les élèves pour la période.
     * Structure : eleveId → AbsenceEleve
     */
    private Map<Long, AbsenceEleve> chargerAbsences(Long anneeId, Long periodeId, Classe classe) {
        List<ClasseEleve> classeEleves =
                classeEleveService.getByClasseAnnee(classe.getId(),
                		anneeId);
 
        Map<Long, AbsenceEleve> result = new HashMap<>();
        for (ClasseEleve ce : classeEleves) {
            Long eleveId = ce.getInscription().getEleve().getId();
            AbsenceEleve abs = absenceService.getByAnneeAndEleveAndPeriode(anneeId, eleveId, periodeId);
            if (abs != null) result.put(eleveId, abs);
        }
        return result;
    }
 
    // ══════════════════════════════════════════════════════════════════════════
    //  UTILITAIRES
    // ══════════════════════════════════════════════════════════════════════════
 
    /** Construit la liste des notes détaillées à partir des entités Notes */
    private List<NoteDetailDto> construireNotesDetail(List<Notes> notes) {
        return notes.stream()
                .filter(this::isNotePriseEnCompte)
                .map(n -> {
                    NoteDetailDto d = new NoteDetailDto();
                    d.setNote(n.getNote());
                    d.setNoteSur(n.getEvaluation().getNoteSur());
                    d.setNumero(n.getEvaluation().getNumero());
                    d.setEstTestLourd(isTestLourd(n));
                    if (n.getEvaluation().getType() != null) {
                        d.setTypeEvaluation(n.getEvaluation().getType().getLibelle());
                    }
                    return d;
                })
                .collect(Collectors.toList());
    }
 
    /** Calcule les statistiques globales de la classe */
    private StatClasseDto calculerStatsClasse(List<BulletinEleveResultDto> eleves) {
        List<Double> moyennes = eleves.stream()
                .filter(BulletinEleveResultDto::isEstClasse)
                .map(BulletinEleveResultDto::getMoyenneGenerale)
                .collect(Collectors.toList());
 
        StatClasseDto stats = new StatClasseDto();
        stats.setNombreEleves(eleves.size());
        stats.setNombreElevesClasses((int) eleves.stream().filter(BulletinEleveResultDto::isEstClasse).count());
 
        if (!moyennes.isEmpty()) {
            stats.setMoyenneClasse(arrondi(
                    moyennes.stream().mapToDouble(Double::doubleValue).average().orElse(0)));
            stats.setMoyenneMin(Collections.min(moyennes));
            stats.setMoyenneMax(Collections.max(moyennes));
        }
        return stats;
    }
 
    /** Retrouve le rang d'une valeur dans une liste triée décroissante (ex-æquo pris en compte) */
    private int getRangParValeur(List<Double> listeTriee, Double valeur) {
        if (valeur == null) return -1;
        int rang = 0;
        for (Double v : listeTriee) {
            rang++;
            if (v.toString().equals(valeur.toString())) return rang;
        }
        return -1;
    }
 
    /** Récupère la moyenne d'un élève dans une matière, ou MOYENNE_NON_CLASSE si non trouvée */
    private double getMoyenneMatiere(BulletinEleveResultDto eleve, Long matiereId, boolean nonClasse) {
        if (nonClasse) return MOYENNE_NON_CLASSE;
        return eleve.getMatieres().stream()
                .filter(m -> matiereId.equals(m.getMatiereId()))
                .map(MatiereResultDto::getMoyenne)
                .findFirst()
                .orElse(MOYENNE_NON_CLASSE);
    }
 
    /** Arrondit à 2 décimales avec la règle HALF_UP */
    private double arrondi(double valeur) {
        return new BigDecimal(valeur)
                .setScale(PRECISION, RoundingMode.HALF_UP)
                .doubleValue();
    }
 
    /** Vérifie que la note et son évaluation sont pec=1 */
    private boolean isNotePriseEnCompte(Notes note) {
        return note.getEvaluation() != null
                && note.getEvaluation().getPec() == Constants.PEC_1
                && note.getPec() != null
                && note.getPec() == Constants.PEC_1;
    }
 
    /** Vérifie si la note est un test lourd */
    private boolean isTestLourd(Notes note) {
        return note.getEvaluation().getType() != null
                && Constants.CODE_TEST_LOURD.equals(note.getEvaluation().getType().getCode());
    }
}
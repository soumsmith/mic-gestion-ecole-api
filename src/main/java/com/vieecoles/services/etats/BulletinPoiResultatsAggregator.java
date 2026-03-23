package com.vieecoles.services.etats;

import com.vieecoles.dto.BulletinStatsLite;
import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.dto.ResultatsElevesNonAffecteDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Remplace le modèle « 1 requête par indicateur et par classe » des services POI résultats
 * par <strong>une seule requête</strong> sur les bulletins (projection sans BLOB) puis agrégation en mémoire.
 * <p>
 * À volumétrie élevée (centaines de milliers de lignes dans {@code Bulletins}), le gain est majeur
 * pour les rapports Word qui appellent {@code CalculResultatsEleveAffecte} pour chaque ordre de niveau.
 */
@ApplicationScoped
public class BulletinPoiResultatsAggregator {

    private static final String AFFECTE = "AFFECTE";
    private static final String NON_AFFECTE = "NON_AFFECTE";
    private static final String SEXE_F = "FEMININ";
    private static final String SEXE_G = "MASCULIN";
    private static final String CLASS_O = "O";
    private static final String CLASS_N = "N";

    private static final String JPQL_LITE = "SELECT new com.vieecoles.dto.BulletinStatsLite("
            + "b.libelleClasse, b.niveau, b.ordreNiveau, b.effectif, b.sexe, b.isClassed, b.moyGeneral) "
            + "FROM Bulletin b WHERE b.ecoleId = :idEcole AND b.affecte = :affecte "
            + "AND b.libellePeriode = :periode AND b.anneeLibelle = :annee AND b.ordreNiveau = :order";

    /** Tous les ordres de niveau : une seule requête (ex. rapports Dren4 affectés + non affectés combinés). */
    private static final String JPQL_LITE_ALL = "SELECT new com.vieecoles.dto.BulletinStatsLite("
            + "b.libelleClasse, b.niveau, b.ordreNiveau, b.effectif, b.sexe, b.isClassed, b.moyGeneral) "
            + "FROM Bulletin b WHERE b.ecoleId = :idEcole AND b.affecte = :affecte "
            + "AND b.libellePeriode = :periode AND b.anneeLibelle = :annee";

    /** Même chose en annuel : la moyenne annuelle est projetée dans le dernier champ (traité comme moyGeneral). */
    private static final String JPQL_LITE_ALL_ANNUEL = "SELECT new com.vieecoles.dto.BulletinStatsLite("
            + "b.libelleClasse, b.niveau, b.ordreNiveau, b.effectif, b.sexe, b.isClassed, b.moyAn) "
            + "FROM Bulletin b WHERE b.ecoleId = :idEcole AND b.affecte = :affecte "
            + "AND b.libellePeriode = :periode AND b.anneeLibelle = :annee";

    @Inject
    EntityManager em;

    public List<ResultatsElevesAffecteDto> calculResultatsAffectes(Long idEcole, String libelleAnnee,
            String libelleTrimestre, int order) {
        return buildDtos(loadRows(idEcole, libelleAnnee, libelleTrimestre, order, AFFECTE));
    }

    /**
     * Résultats affectés pour toutes les classes / niveaux (équivalent à l’ancien
     * {@code dren4.Services.resultatsPoiServices#CalculResultatsEleveAffecte} sans filtre d’ordre).
     */
    public List<ResultatsElevesAffecteDto> calculResultatsAffectesTousNiveaux(Long idEcole, String libelleAnnee,
            String libelleTrimestre) {
        return buildDtos(loadRowsAll(idEcole, libelleAnnee, libelleTrimestre, AFFECTE, JPQL_LITE_ALL));
    }

    public List<ResultatsElevesAffecteDto> calculResultatsAffectesTousNiveauxAnnuel(Long idEcole, String libelleAnnee,
            String libelleTrimestre) {
        return buildDtos(loadRowsAll(idEcole, libelleAnnee, libelleTrimestre, AFFECTE, JPQL_LITE_ALL_ANNUEL));
    }

    public List<ResultatsElevesNonAffecteDto> calculResultatsNonAffectes(Long idEcole, String libelleAnnee,
            String libelleTrimestre, int order) {
        return buildDtos(loadRows(idEcole, libelleAnnee, libelleTrimestre, order, NON_AFFECTE)).stream()
                .map(BulletinPoiResultatsAggregator::toNonAffecte)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<BulletinStatsLite> loadRows(Long idEcole, String libelleAnnee, String libelleTrimestre, int order,
            String affecte) {
        TypedQuery<BulletinStatsLite> q = em.createQuery(JPQL_LITE, BulletinStatsLite.class);
        q.setParameter("idEcole", idEcole);
        q.setParameter("affecte", affecte);
        q.setParameter("periode", libelleTrimestre);
        q.setParameter("annee", libelleAnnee);
        q.setParameter("order", order);
        return q.getResultList();
    }

    private List<BulletinStatsLite> loadRowsAll(Long idEcole, String libelleAnnee, String libelleTrimestre,
            String affecte, String jpql) {
        TypedQuery<BulletinStatsLite> q = em.createQuery(jpql, BulletinStatsLite.class);
        q.setParameter("idEcole", idEcole);
        q.setParameter("affecte", affecte);
        q.setParameter("periode", libelleTrimestre);
        q.setParameter("annee", libelleAnnee);
        return q.getResultList();
    }

    private List<ResultatsElevesAffecteDto> buildDtos(List<BulletinStatsLite> rows) {
        if (rows.isEmpty()) {
            return new ArrayList<>();
        }
        Map<String, List<BulletinStatsLite>> byNiveau = rows.stream()
                .collect(Collectors.groupingBy(r -> Objects.toString(r.getNiveau(), "")));

        Map<String, NiveauMoyennes> moyParNiveau = new HashMap<>();
        for (Map.Entry<String, List<BulletinStatsLite>> e : byNiveau.entrySet()) {
            moyParNiveau.put(e.getKey(), computeNiveauMoyennes(e.getValue()));
        }

        Map<ClasseNiveauKey, List<BulletinStatsLite>> byClasseNiveau = rows.stream()
                .collect(Collectors.groupingBy(r -> new ClasseNiveauKey(r.getLibelleClasse(), r.getNiveau())));

        List<ClasseNiveauKey> keys = new ArrayList<>(byClasseNiveau.keySet());
        keys.sort(Comparator
                .comparing((ClasseNiveauKey k) -> ordreForKey(k, byClasseNiveau))
                .thenComparing(k -> Objects.toString(k.niveau, ""), Comparator.nullsLast(String::compareTo))
                .thenComparing(k -> Objects.toString(k.classe, ""), Comparator.nullsLast(String::compareTo)));

        List<ResultatsElevesAffecteDto> out = new ArrayList<>(keys.size());
        for (ClasseNiveauKey key : keys) {
            List<BulletinStatsLite> group = byClasseNiveau.get(key);
            NiveauMoyennes nm = moyParNiveau.getOrDefault(Objects.toString(key.niveau, ""), NiveauMoyennes.EMPTY);
            out.add(buildOneDto(group, nm));
        }
        return out;
    }

    private static int ordreForKey(ClasseNiveauKey key, Map<ClasseNiveauKey, List<BulletinStatsLite>> byClasseNiveau) {
        List<BulletinStatsLite> g = byClasseNiveau.get(key);
        if (g == null || g.isEmpty()) {
            return Integer.MAX_VALUE;
        }
        Integer o = g.get(0).getOrdreNiveau();
        return o != null ? o : Integer.MAX_VALUE;
    }

    private static NiveauMoyennes computeNiveauMoyennes(List<BulletinStatsLite> niveauRows) {
        List<BulletinStatsLite> ranked = niveauRows.stream()
                .filter(r -> CLASS_O.equals(r.getIsClassed()))
                .collect(Collectors.toList());
        return new NiveauMoyennes(avgMoy(ranked, r -> true),
                avgMoy(ranked, r -> SEXE_F.equals(r.getSexe())),
                avgMoy(ranked, r -> SEXE_G.equals(r.getSexe())));
    }

    private static double avgMoy(List<BulletinStatsLite> list, java.util.function.Predicate<BulletinStatsLite> extra) {
        return list.stream()
                .filter(extra)
                .map(BulletinStatsLite::getMoyGeneral)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0d);
    }

    private static ResultatsElevesAffecteDto buildOneDto(List<BulletinStatsLite> group, NiveauMoyennes nm) {
        Integer ordreNiveau = group.stream()
                .map(BulletinStatsLite::getOrdreNiveau)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(0);

        long effeF = group.stream().filter(r -> SEXE_F.equals(r.getSexe())).count();
        long effeG = group.stream().filter(r -> SEXE_G.equals(r.getSexe())).count();
        long classF = group.stream().filter(r -> SEXE_F.equals(r.getSexe()) && CLASS_O.equals(r.getIsClassed())).count();
        long classG = group.stream().filter(r -> SEXE_G.equals(r.getSexe()) && CLASS_O.equals(r.getIsClassed())).count();
        long nonclassF = group.stream().filter(r -> SEXE_F.equals(r.getSexe()) && CLASS_N.equals(r.getIsClassed())).count();
        long nonclassG = group.stream().filter(r -> SEXE_G.equals(r.getSexe()) && CLASS_N.equals(r.getIsClassed())).count();

        long nbreMoySup10F = group.stream()
                .filter(r -> SEXE_F.equals(r.getSexe()) && CLASS_O.equals(r.getIsClassed())
                        && r.getMoyGeneral() != null && r.getMoyGeneral() >= 10.0)
                .count();
        long nbreMoySup10G = group.stream()
                .filter(r -> SEXE_G.equals(r.getSexe()) && CLASS_O.equals(r.getIsClassed())
                        && r.getMoyGeneral() != null && r.getMoyGeneral() >= 10.0)
                .count();
        long nbreMoyInf999F = group.stream()
                .filter(r -> SEXE_F.equals(r.getSexe()) && CLASS_O.equals(r.getIsClassed())
                        && r.getMoyGeneral() != null && r.getMoyGeneral() >= 8.5 && r.getMoyGeneral() <= 9.99)
                .count();
        long nbreMoyInf999G = group.stream()
                .filter(r -> SEXE_G.equals(r.getSexe()) && CLASS_O.equals(r.getIsClassed())
                        && r.getMoyGeneral() != null && r.getMoyGeneral() >= 8.5 && r.getMoyGeneral() <= 9.99)
                .count();
        long nbreMoyInf85F = group.stream()
                .filter(r -> SEXE_F.equals(r.getSexe()) && CLASS_O.equals(r.getIsClassed())
                        && r.getMoyGeneral() != null && r.getMoyGeneral() < 8.5)
                .count();
        long nbreMoyInf85G = group.stream()
                .filter(r -> SEXE_G.equals(r.getSexe()) && CLASS_O.equals(r.getIsClassed())
                        && r.getMoyGeneral() != null && r.getMoyGeneral() < 8.5)
                .count();

        double moyClasse = avgMoy(group, r -> CLASS_O.equals(r.getIsClassed()));
        double moyClasseF = avgMoy(group, r -> CLASS_O.equals(r.getIsClassed()) && SEXE_F.equals(r.getSexe()));
        double moyClasseG = avgMoy(group, r -> CLASS_O.equals(r.getIsClassed()) && SEXE_G.equals(r.getSexe()));

        double pourMoyInf85G = classG != 0L ? (nbreMoyInf85G * 100d) / classG : 0d;
        double pourMoyInf85F = classF != 0L ? (nbreMoyInf85F * 100d) / classF : 0d;
        double pourMoyInf999G = classG != 0L ? (nbreMoyInf999G * 100d) / classG : 0d;
        double pourMoyInf999F = classF != 0L ? (nbreMoyInf999F * 100d) / classF : 0d;
        double pourMoySup10F = classF != 0L ? (nbreMoySup10F * 100d) / classF : 0d;
        double pourMoySup10G = classG != 0L ? (nbreMoySup10G * 100d) / classG : 0d;

        BulletinStatsLite any = group.get(0);
        ResultatsElevesAffecteDto dto = new ResultatsElevesAffecteDto();
        dto.setNiveau(any.getNiveau());
        dto.setClasse(any.getLibelleClasse());
        dto.setEffeG(effeG);
        dto.setEffeF(effeF);
        dto.setClassG(classG);
        dto.setClassF(classF);
        dto.setNonclassF(nonclassF);
        dto.setNonclassG(nonclassG);
        dto.setNbreMoySup10F(nbreMoySup10F);
        dto.setNbreMoySup10G(nbreMoySup10G);
        dto.setPourMoySup10F(pourMoySup10F);
        dto.setPourMoySup10G(pourMoySup10G);
        dto.setNbreMoyInf999F(nbreMoyInf999F);
        dto.setNbreMoyInf999G(nbreMoyInf999G);
        dto.setPourMoyInf999F(pourMoyInf999F);
        dto.setPourMoyInf999G(pourMoyInf999G);
        dto.setNbreMoyInf85G(nbreMoyInf85G);
        dto.setNbreMoyInf85F(nbreMoyInf85F);
        dto.setPourMoyInf85G(pourMoyInf85G);
        dto.setPourMoyInf85F(pourMoyInf85F);
        dto.setMoyClasseG(moyClasseG);
        dto.setMoyClasseF(moyClasseF);
        dto.setOrdre_niveau(ordreNiveau);
        dto.setMoyClasse(moyClasse);
        dto.setMoyClasseniv(nm.moyNiv);
        dto.setMoyClasseFniv(nm.moyNivF);
        dto.setMoyClasseGniv(nm.moyNivG);
        return dto;
    }

    private static ResultatsElevesNonAffecteDto toNonAffecte(ResultatsElevesAffecteDto a) {
        ResultatsElevesNonAffecteDto n = new ResultatsElevesNonAffecteDto();
        n.setNiveau(a.getNiveau());
        n.setClasse(a.getClasse());
        n.setEffeG(a.getEffeG());
        n.setEffeF(a.getEffeF());
        n.setClassG(a.getClassG());
        n.setClassF(a.getClassF());
        n.setNonclassF(a.getNonclassF());
        n.setNonclassG(a.getNonclassG());
        n.setNbreMoySup10F(a.getNbreMoySup10F());
        n.setNbreMoySup10G(a.getNbreMoySup10G());
        n.setPourMoySup10F(a.getPourMoySup10F());
        n.setPourMoySup10G(a.getPourMoySup10G());
        n.setNbreMoyInf999F(a.getNbreMoyInf999F());
        n.setNbreMoyInf999G(a.getNbreMoyInf999G());
        n.setPourMoyInf999F(a.getPourMoyInf999F());
        n.setPourMoyInf999G(a.getPourMoyInf999G());
        n.setNbreMoyInf85G(a.getNbreMoyInf85G());
        n.setNbreMoyInf85F(a.getNbreMoyInf85F());
        n.setPourMoyInf85G(a.getPourMoyInf85G());
        n.setPourMoyInf85F(a.getPourMoyInf85F());
        n.setMoyClasseG(a.getMoyClasseG());
        n.setMoyClasseF(a.getMoyClasseF());
        n.setOrdre_niveau(a.getOrdre_niveau());
        n.setMoyClasse(a.getMoyClasse());
        n.setMoyClasseniv(a.getMoyClasseniv());
        n.setMoyClasseFniv(a.getMoyClasseFniv());
        n.setMoyClasseGniv(a.getMoyClasseGniv());
        return n;
    }

    private static final class ClasseNiveauKey {
        final String classe;
        final String niveau;

        ClasseNiveauKey(String classe, String niveau) {
            this.classe = classe;
            this.niveau = niveau;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ClasseNiveauKey that = (ClasseNiveauKey) o;
            return Objects.equals(classe, that.classe) && Objects.equals(niveau, that.niveau);
        }

        @Override
        public int hashCode() {
            return Objects.hash(classe, niveau);
        }
    }

    private static final class NiveauMoyennes {
        static final NiveauMoyennes EMPTY = new NiveauMoyennes(0d, 0d, 0d);
        final double moyNiv;
        final double moyNivF;
        final double moyNivG;

        NiveauMoyennes(double moyNiv, double moyNivF, double moyNivG) {
            this.moyNiv = moyNiv;
            this.moyNivF = moyNivF;
            this.moyNivG = moyNivG;
        }
    }
}

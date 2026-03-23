package com.vieecoles.processors.yamoussoukro;

import com.vieecoles.processors.yamoussoukro.Annuels.WordTempListAffectesAnnuelsProcessor;
import com.vieecoles.processors.yamoussoukro.Annuels.WordTempListMajorAnnuelsProcessor;
import com.vieecoles.processors.yamoussoukro.Annuels.WordTempListNonAffectesAnnuelsProcessor;
import com.vieecoles.processors.yamoussoukro.Annuels.WordTempRecapAffNonAffResultatAnnuelsProcessor;
import com.vieecoles.processors.yamoussoukro.Annuels.WordTempRecapNonResultatAnnuelsProcessor;
import com.vieecoles.processors.yamoussoukro.Annuels.WordTempRecapResultatAnnuelsProcessor;
import com.vieecoles.processors.yamoussoukro.Annuels.WordTempResultaAffAnnuelsProcessor;
import com.vieecoles.processors.yamoussoukro.Annuels.WordTempResultaNonAffAnnuelsProcessor;
import com.vieecoles.services.etats.BulletinAnneeLookupService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jboss.logging.Logger;

/**
 * Orchestration des rapports Word Yamoussoukro. L'ordre des appels aux sous-processeurs est conservé
 * pour ne pas modifier la mise en page du modèle (.docx).
 */
@ApplicationScoped
public class WordTempProcessor {

    private static final Logger LOG = Logger.getLogger(WordTempProcessor.class);

    /** Libellé strict utilisé pour activer les blocs annuels (identique au reste du métier). */
    private static final String TROISIEME_TRIMESTRE = "Troisième Trimestre";

    @Inject
    BulletinAnneeLookupService bulletinAnneeLookupService;
    @Inject
    WordTempRecapResultatProcessor wordTempRecapResultatProcessor;
    @Inject
    WordTempResultaAffProcessor wordTempResultaAffProcessor;
    @Inject
    WordTempListAffectesProcessor wordTempListAffectesProcessor;
    @Inject
    WordTempListMajorProcessor wordTempListMajorProcessor;
    @Inject
    WordTempListTransfertProcessor wordTempListTransfertProcessor;
    @Inject
    WordTempRepartitionAnneeNaissProcessor wordTempRepartitionAnneeNaissProcessor;
    @Inject
    WordTempListBoursiersProcessor wordTempListBoursiersProcessor;
    @Inject
    WordTempEffectifApprocheNiveauGenre wordTempEffectifApprocheNiveauGenre;
    @Inject
    WordTempIdentiteProcessor wordTempIdentiteProcessor;
    @Inject
    WordTempResultaNonAffProcessor wordTempResultaNonAffProcessor;
    @Inject
    WordTempRecapNonResultatProcessor wordTempRecapNonAffResultatProcessor;
    @Inject
    WordTempRecapAffNonAffResultatProcessor wordTempRecapAffNonAffResultatProcessor;
    @Inject
    WordTempListNonAffectesProcessor wordTempListNonAffectesProcessor;
    @Inject
    WordTempStatistiqueLangueVivanteProcessor wordTempStatistiqueLangueVivanteProcessor;
    @Inject
    WordTempResultaAffAnnuelsProcessor wordTempResultaAffAnnuelsProcessor;
    @Inject
    WordTempRecapResultatAnnuelsProcessor wordTempRecapResultatAnnuelsProcessor;
    @Inject
    WordTempResultaNonAffAnnuelsProcessor wordTempResultaNonAffAnnuelsProcessor;
    @Inject
    WordTempRecapNonResultatAnnuelsProcessor wordTempRecapNonResultatAnnuelsProcessor;
    @Inject
    WordTempListAffectesAnnuelsProcessor wordTempListAffectesAnnuelsProcessor;
    @Inject
    WordTempListMajorAnnuelsProcessor wordTempListMajorAnnuelsProcessor;
    @Inject
    WordTempListNonAffectesAnnuelsProcessor wordTempListNonAffectesAnnuelsProcessor;
    @Inject
    WordTempRecapAffNonAffResultatAnnuelsProcessor wordTempRecapAffNonAffResultatAnnuelsProcessor;

    public byte[] generateWordFile(Long idEcole, String libelleAnnee, String libelleTrimetre, ByteArrayInputStream fis)
            throws Exception {
        Long anneeId = bulletinAnneeLookupService.findAnneeId(idEcole, libelleAnnee, libelleTrimetre);
        XWPFDocument document = new XWPFDocument(fis);
        try {
            if (TROISIEME_TRIMESTRE.equals(libelleTrimetre)) {
                fillTroisiemeTrimestre(document, idEcole, libelleAnnee, libelleTrimetre, anneeId);
            } else {
                fillAutresTrimestres(document, idEcole, libelleAnnee, libelleTrimetre, anneeId);
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.write(outputStream);
            return outputStream.toByteArray();
        } finally {
            document.close();
            fis.close();
        }
    }

    /**
     * Parcours historique T3 : listes affectés / non affectés / major avant les tableaux de résultats,
     * puis blocs annuels.
     */
    private void fillTroisiemeTrimestre(XWPFDocument document, Long idEcole, String libelleAnnee, String libelleTrimetre,
            Long anneeId) throws Exception {
        wordTempIdentiteProcessor.getIdentiteProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Statistique Identité");

        wordTempListAffectesProcessor.getEleveAffecteParClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Liste élèves affectés");
        wordTempListNonAffectesProcessor.getEleveNosAffecteParClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Liste élèves non affectés");
        wordTempListMajorProcessor.getListeMajorClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Liste major");

        fillResultatsEtRecapsTrimestre(document, idEcole, libelleAnnee, libelleTrimetre);
        fillTransfertRepartitionBoursiersEffectifLangue(document, idEcole, libelleAnnee, libelleTrimetre, anneeId);

        fillBlocsAnnuels(document, idEcole, libelleAnnee, libelleTrimetre);
    }

    /**
     * Autres trimestres : résultats / récaps puis major puis listes, puis blocs communs (sans annuels).
     */
    private void fillAutresTrimestres(XWPFDocument document, Long idEcole, String libelleAnnee, String libelleTrimetre,
            Long anneeId) throws Exception {
        wordTempIdentiteProcessor.getIdentiteProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Statistique Identité");

        fillResultatsEtRecapsTrimestre(document, idEcole, libelleAnnee, libelleTrimetre);

        wordTempListMajorProcessor.getListeMajorClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Liste major");
        wordTempListAffectesProcessor.getEleveAffecteParClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Liste élèves affectés");
        wordTempListNonAffectesProcessor.getEleveNosAffecteParClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Liste élèves non affectés");

        fillTransfertRepartitionBoursiersEffectifLangue(document, idEcole, libelleAnnee, libelleTrimetre, anneeId);
    }

    private void fillResultatsEtRecapsTrimestre(XWPFDocument document, Long idEcole, String libelleAnnee,
            String libelleTrimetre) throws Exception {
        wordTempResultaAffProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Résultats affectés");
        wordTempRecapResultatProcessor.getRecapResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Récap résultats affectés");
        wordTempResultaNonAffProcessor.getResultatNonAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Résultats non affectés");
        wordTempRecapNonAffResultatProcessor.getRecapResultatANonffProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Récap non affectés");
        wordTempRecapAffNonAffResultatProcessor.getRecapResultatAffProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Récap affectés et non affectés");
    }

    private void fillTransfertRepartitionBoursiersEffectifLangue(XWPFDocument document, Long idEcole,
            String libelleAnnee, String libelleTrimetre, Long anneeId) throws Exception {
        wordTempListTransfertProcessor.getListTransfert(document, idEcole);
        trace("Liste transferts");
        wordTempRepartitionAnneeNaissProcessor.getListRepartitionParAnnee(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Répartition année naissance");
        wordTempListBoursiersProcessor.getListeBoursierClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Liste boursiers");
        wordTempEffectifApprocheNiveauGenre.getListeApprocheParNiveau(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Effectif approche niveau / genre");
        wordTempStatistiqueLangueVivanteProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre, anneeId);
        trace("Statistique langue vivante");
    }

    private void fillBlocsAnnuels(XWPFDocument document, Long idEcole, String libelleAnnee, String libelleTrimetre)
            throws Exception {
        wordTempResultaAffAnnuelsProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Résultats affectés annuels");
        wordTempRecapResultatAnnuelsProcessor.getRecapResultatAffProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Récap résultats affectés annuels");
        wordTempResultaNonAffAnnuelsProcessor.getResultatNonAffProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Résultats non affectés annuels");
        wordTempRecapNonResultatAnnuelsProcessor.getRecapResultatANonffProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Récap non affectés annuels");
        wordTempListAffectesAnnuelsProcessor.getEleveAffecteParClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Liste affectés annuels");
        wordTempListNonAffectesAnnuelsProcessor.getEleveNosAffecteParClasse(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Liste non affectés annuels");
        wordTempRecapAffNonAffResultatAnnuelsProcessor.getRecapResultatAffProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Récap affectés / non affectés annuels");
        wordTempListMajorAnnuelsProcessor.getListeMajorClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Liste major annuels");
    }

    private static void trace(String step) {
        if (LOG.isTraceEnabled()) {
            LOG.tracef("WordTempProcessor: %s", step);
        }
    }
}

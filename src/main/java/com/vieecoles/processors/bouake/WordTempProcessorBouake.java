package com.vieecoles.processors.bouake;

import com.vieecoles.processors.bouake.annuels.WordTempIdentiteAnnuelProcessor;
import com.vieecoles.processors.bouake.annuels.WordTempListAffecteNonAffectesAnnuelProcessor;
import com.vieecoles.processors.bouake.annuels.WordTempListAffectesAnnuelProcessor;
import com.vieecoles.processors.bouake.annuels.WordTempListMajorAnnuelProcessor;
import com.vieecoles.processors.bouake.annuels.WordTempResultaAffAnnuelProcessor;
import com.vieecoles.processors.bouake.annuels.WordTempResultaAffEtNonAffAnnuelProcessor;
import com.vieecoles.processors.bouake.annuels.WordTempResultaNonAffAnnuelProcessor;
import com.vieecoles.services.etats.BulletinAnneeLookupService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jboss.logging.Logger;

@ApplicationScoped
public class WordTempProcessorBouake {

    private static final Logger LOG = Logger.getLogger(WordTempProcessorBouake.class);
    private static final String TROISIEME_TRIMESTRE = "Troisième Trimestre";

    @Inject
    BulletinAnneeLookupService bulletinAnneeLookupService;
    /** Nom historique : processeur des résultats affectés (trimestre). */
    @Inject
    WordTempResultaAffProcessor wordTempRecapResultatProcessor;
    @Inject
    WordTempResultaAffAnnuelProcessor wordTempRecapResultatAnnuelProcessor;
    @Inject
    WordTempListAffectesProcessor wordTempListAffectesProcessor;
    @Inject
    WordTempListAffectesAnnuelProcessor wordTempListAffectesAnnuelProcessor;
    @Inject
    WordTempListMajorProcessor wordTempListMajorProcessor;
    @Inject
    WordTempListMajorAnnuelProcessor wordTempListMajorAnnuelProcessor;
    @Inject
    WordTempListTransfertProcessor wordTempListTransfertProcessor;
    @Inject
    WordTempIdentiteProcessor wordTempIdentiteProcessor;
    @Inject
    WordTempIdentiteAnnuelProcessor wordTempIdentiteAnnuelProcessor;
    @Inject
    WordTempResultaNonAffProcessor wordTempResultaNonAffProcessor;
    @Inject
    WordTempResultaNonAffAnnuelProcessor wordTempResultaNonAffAnnuelProcessor;
    @Inject
    WordTempResultaAffEtNonAffProcessor wordTempResultaAffEtNonAffProcessor;
    @Inject
    WordTempResultaAffEtNonAffAnnuelProcessor wordTempResultaAffEtNonAffAnnuelProcessor;
    @Inject
    WordTempEffectifConseilClasseProcessor wordTempEffectifConseilClasseProcessor;
    @Inject
    WordTempEffectifConseilClasse2Processor wordTempEffectifConseilClasse2Processor;
    @Inject
    WordTempPyramideProcessor wordTempPyramideProcessor;
    @Inject
    WordTempStatistiqueAgeProcessor wordTempStatistiqueAgeProcessor;
    @Inject
    WordTempListAffecteNonAffectesProcessor wordTempListAffecteNonAffectesProcessor;
    @Inject
    WordTempListAffecteNonAffectesAnnuelProcessor wordTempListAffecteNonAffectesAnnuelProcessor;
    @Inject
    WordTempStatistiqueProfesseurProcessor wordTempStatistiqueProfesseurProcessor;

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

    private void fillTroisiemeTrimestre(XWPFDocument document, Long idEcole, String libelleAnnee, String libelleTrimetre,
            Long anneeId) throws Exception {
        wordTempIdentiteAnnuelProcessor.getIdentiteProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Identité annuelle");

        wordTempListAffecteNonAffectesProcessor.getEleveNosAffecteParClasse(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Liste aff+non aff");
        wordTempListAffecteNonAffectesAnnuelProcessor.getEleveNosAffecteParClasse(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Liste aff+non aff annuel");

        wordTempListAffectesProcessor.getEleveAffecteParClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Liste affectés");
        wordTempListAffectesAnnuelProcessor.getEleveAffecteParClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Liste affectés annuel");

        wordTempRecapResultatProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Résultats affectés");
        wordTempRecapResultatAnnuelProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Résultats affectés annuel");

        wordTempResultaNonAffProcessor.getResultatNonAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Résultats non affectés");
        wordTempResultaNonAffAnnuelProcessor.getResultatNonAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Résultats non affectés annuel");

        wordTempResultaAffEtNonAffProcessor.getResultatAffEtNonAffProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Résultats aff+non aff");
        wordTempResultaAffEtNonAffAnnuelProcessor.getResultatAffEtNonAffProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Résultats aff+non aff annuel");

        wordTempListMajorProcessor.getListeMajorClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Majors");
        wordTempListMajorAnnuelProcessor.getListeMajorClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Majors annuel");

        wordTempListTransfertProcessor.getListTransfert(document, idEcole);
        trace("Transferts");
        wordTempEffectifConseilClasseProcessor.getEffectifConseilProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Effectif conseil 1");
        wordTempEffectifConseilClasse2Processor.getEffectifConseilProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Effectif conseil 2");
        wordTempPyramideProcessor.getNombreClasseProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Pyramide");
        wordTempStatistiqueAgeProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Âge");
        wordTempStatistiqueProfesseurProcessor.getNbreProfessProcessor(document, idEcole, libelleAnnee, libelleTrimetre,
                anneeId);
        trace("Professeurs");
    }

    private void fillAutresTrimestres(XWPFDocument document, Long idEcole, String libelleAnnee, String libelleTrimetre,
            Long anneeId) throws Exception {
        wordTempIdentiteProcessor.getIdentiteProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Identité");

        wordTempListAffecteNonAffectesProcessor.getEleveNosAffecteParClasse(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Liste aff+non aff");
        wordTempListAffectesProcessor.getEleveAffecteParClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Liste affectés");

        wordTempRecapResultatProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Résultats affectés");
        wordTempResultaNonAffProcessor.getResultatNonAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Résultats non affectés");
        wordTempResultaAffEtNonAffProcessor.getResultatAffEtNonAffProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Résultats aff+non aff");

        wordTempListMajorProcessor.getListeMajorClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Majors");
        wordTempListTransfertProcessor.getListTransfert(document, idEcole);
        trace("Transferts");
        wordTempEffectifConseilClasseProcessor.getEffectifConseilProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Effectif conseil 1");
        wordTempEffectifConseilClasse2Processor.getEffectifConseilProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Effectif conseil 2");
        wordTempPyramideProcessor.getNombreClasseProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Pyramide");
        wordTempStatistiqueAgeProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Âge");
        wordTempStatistiqueProfesseurProcessor.getNbreProfessProcessor(document, idEcole, libelleAnnee, libelleTrimetre,
                anneeId);
        trace("Professeurs");
    }

    private static void trace(String step) {
        if (LOG.isTraceEnabled()) {
            LOG.tracef("WordTempProcessorBouake: %s", step);
        }
    }
}

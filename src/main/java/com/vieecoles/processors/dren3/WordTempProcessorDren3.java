package com.vieecoles.processors.dren3;

import com.vieecoles.processors.dren3.annuels.WordTempIdentiteAnnuelProcessor;
import com.vieecoles.processors.dren3.annuels.WordTempListAffecteNonAffectesAnnuelProcessor;
import com.vieecoles.processors.dren3.annuels.WordTempListAffectesAnnuelProcessor;
import com.vieecoles.processors.dren3.annuels.WordTempListMajorAnnuelProcessor;
import com.vieecoles.processors.dren3.annuels.WordTempListNonAffectesAnnuelProcessor;
import com.vieecoles.processors.dren3.annuels.WordTempRecapAffNonAffResultatAnnuelProcessor;
import com.vieecoles.processors.dren3.annuels.WordTempStatistiqueAdmiRedoublantAnnuelProcessor;
import com.vieecoles.processors.dren3.annuels.WordTempStatistiqueResultatAnnuelProcessor;
import com.vieecoles.services.etats.BulletinAnneeLookupService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jboss.logging.Logger;

/**
 * Orchestration Dren3 : ordre des appels inchangé pour le modèle Word.
 */
@ApplicationScoped
public class WordTempProcessorDren3 {

    private static final Logger LOG = Logger.getLogger(WordTempProcessorDren3.class);
    private static final String TROISIEME_TRIMESTRE = "Troisième Trimestre";

    @Inject
    BulletinAnneeLookupService bulletinAnneeLookupService;
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
    WordTempRecapAffNonAffResultatProcessor wordTempRecapAffNonAffResultatProcessor;
    @Inject
    WordTempRecapAffNonAffResultatAnnuelProcessor wordTempRecapAffNonAffResultatAnnuelProcessor;
    @Inject
    WordTempListNonAffectesProcessor wordTempListNonAffectesProcessor;
    @Inject
    WordTempListNonAffectesAnnuelProcessor wordTempListNonAffectesAnnuelProcessor;
    @Inject
    WordTempStatistiqueResultatProcessor wordTempStatistiqueResultatProcessor;
    @Inject
    WordTempStatistiqueResultatAnnuelProcessor wordTempStatistiqueResultatAnnuelProcessor;
    @Inject
    WordTempStatistiqueAdmiRedoublantProcessor wordTempStatistiqueAdmiRedoublantProcessor;
    @Inject
    WordTempStatistiqueAdmiRedoublantAnnuelProcessor wordTempStatistiqueAdmiRedoublantAnnuelProcessor;
    @Inject
    WordTempStatistiqueNationaliteProcessor wordTempStatistiqueNationaliteProcessor;
    @Inject
    WordTempStatistiqueTransfertProcessor wordTempStatistiqueTransfertProcessor;
    @Inject
    WordTempStatistiqueAgeProcessor wordTempStatistiqueAgeProcessor;
    @Inject
    WordTempStatistiqueBoursierProcessor wordTempStatistiqueBoursierProcessor;
    @Inject
    WordTempStatistiqueGenreProcessor wordTempStatistiqueGenreProcessor;
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
        wordTempRecapAffNonAffResultatProcessor.getRecapResultatAffProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Récap aff/non aff");
        wordTempRecapAffNonAffResultatAnnuelProcessor.getRecapResultatAffProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Récap aff/non aff annuel");
        wordTempStatistiqueResultatProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Statistique résultats");
        wordTempStatistiqueResultatAnnuelProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Statistique résultats annuel");
        wordTempStatistiqueAdmiRedoublantProcessor.getStatistiqueAdminRedoublantProcessor(document, idEcole,
                libelleAnnee, libelleTrimetre);
        trace("Redoublants");
        wordTempStatistiqueAdmiRedoublantAnnuelProcessor.getStatistiqueAdminRedoublantProcessor(document, idEcole,
                libelleAnnee, libelleTrimetre);
        trace("Redoublants annuel");

        wordTempListAffectesProcessor.getEleveAffecteParClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Liste affectés");
        wordTempListAffectesAnnuelProcessor.getEleveAffecteParClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Liste affectés annuel");

        wordTempListNonAffectesProcessor.getEleveNosAffecteParClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Liste non affectés");
        wordTempListNonAffectesAnnuelProcessor.getEleveNosAffecteParClasse(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Liste non affectés annuel");

        wordTempListAffecteNonAffectesProcessor.getEleveNosAffecteParClasse(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Liste aff+non aff");
        wordTempListAffecteNonAffectesAnnuelProcessor.getEleveNosAffecteParClasse(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Liste aff+non aff annuel");

        wordTempListMajorProcessor.getListeMajorClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Majors");
        wordTempListMajorAnnuelProcessor.getListeMajorClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Majors annuel");

        wordTempListTransfertProcessor.getListTransfert(document, idEcole);
        trace("Transferts");
        wordTempStatistiqueTransfertProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre,
                anneeId);
        trace("Stat transferts");
        wordTempStatistiqueNationaliteProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre,
                anneeId);
        trace("Nationalité");
        wordTempStatistiqueAgeProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre,
                anneeId);
        trace("Âge");
        wordTempStatistiqueBoursierProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre,
                anneeId);
        trace("Boursiers");
        wordTempStatistiqueGenreProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre,
                anneeId);
        trace("Genre");
        wordTempStatistiqueProfesseurProcessor.getNbreProfessProcessor(document, idEcole, libelleAnnee, libelleTrimetre,
                anneeId);
        trace("Professeurs");
    }

    private void fillAutresTrimestres(XWPFDocument document, Long idEcole, String libelleAnnee, String libelleTrimetre,
            Long anneeId) throws Exception {
        wordTempIdentiteProcessor.getIdentiteProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Identité");
        wordTempRecapAffNonAffResultatProcessor.getRecapResultatAffProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Récap aff/non aff");
        wordTempStatistiqueResultatProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Statistique résultats");
        wordTempStatistiqueAdmiRedoublantProcessor.getStatistiqueAdminRedoublantProcessor(document, idEcole,
                libelleAnnee, libelleTrimetre);
        trace("Redoublants");

        wordTempListAffectesProcessor.getEleveAffecteParClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Liste affectés");
        wordTempListNonAffectesProcessor.getEleveNosAffecteParClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Liste non affectés");
        wordTempListAffecteNonAffectesProcessor.getEleveNosAffecteParClasse(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Liste aff+non aff");
        wordTempListMajorProcessor.getListeMajorClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Majors");

        wordTempListTransfertProcessor.getListTransfert(document, idEcole);
        trace("Transferts");
        wordTempStatistiqueTransfertProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre,
                anneeId);
        trace("Stat transferts");
        wordTempStatistiqueNationaliteProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre,
                anneeId);
        trace("Nationalité");
        wordTempStatistiqueAgeProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre,
                anneeId);
        trace("Âge");
        wordTempStatistiqueBoursierProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre,
                anneeId);
        trace("Boursiers");
        wordTempStatistiqueGenreProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre,
                anneeId);
        trace("Genre");
        wordTempStatistiqueProfesseurProcessor.getNbreProfessProcessor(document, idEcole, libelleAnnee, libelleTrimetre,
                anneeId);
        trace("Professeurs");
    }

    private static void trace(String step) {
        if (LOG.isTraceEnabled()) {
            LOG.tracef("WordTempProcessorDren3: %s", step);
        }
    }
}

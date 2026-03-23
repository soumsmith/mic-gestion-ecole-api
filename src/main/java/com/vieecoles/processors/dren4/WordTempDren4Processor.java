package com.vieecoles.processors.dren4;

import com.vieecoles.processors.dren4.Annuels.WordTempListAffecteNonAffectesAnnuelsProcessor;
import com.vieecoles.processors.dren4.Annuels.WordTempListMajorAnnuelsProcessor;
import com.vieecoles.processors.dren4.Annuels.WordTempListStatistiqueDfaAnnuelsProcessor;
import com.vieecoles.processors.dren4.Annuels.WordTempResultaAffetNonAffAnnuelsProcessor;
import com.vieecoles.processors.dren4.Annuels.WordTempTauxPromotionInterneProcessor;
import com.vieecoles.services.etats.BulletinAnneeLookupService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jboss.logging.Logger;

@ApplicationScoped
public class WordTempDren4Processor {

    private static final Logger LOG = Logger.getLogger(WordTempDren4Processor.class);
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
    WordTempListAffecteNonAffectesProcessor wordTempListAffecteNonAffectesProcessor;
    @Inject
    WordTempListStatistiqueDfaAnnuelsProcessor wordTempListStatistiqueDfaAnnuelsProcessor;
    @Inject
    WordTempTauxPromotionInterneProcessor wordTempTauxPromotionInterneProcessor;
    @Inject
    WordTempResultaAffetNonAffProcessor wordTempResultaAffetNonAffProcessor;
    @Inject
    WordTempListAffecteNonAffectesAnnuelsProcessor wordTempListAffecteNonAffectesAnnuelsProcessor;
    @Inject
    WordTempResultaAffetNonAffAnnuelsProcessor wordTempResultaAffetNonAffAnnuelsProcessor;
    @Inject
    WordTempListMajorAnnuelsProcessor wordTempListMajorAnnuelsProcessor;
    @Inject
    WordTempStatistiqueGenreProcessor wordTempStatistiqueGenreProcessor;
    @Inject
    WordTempStatistiqueLangueVivanteProcessor wordTempStatistiqueLangueVivanteProcessor;

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
        wordTempListAffecteNonAffectesProcessor.getEleveNosAffecteParClasse(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Liste aff+non aff");
        wordTempListMajorProcessor.getListeMajorClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Majors");
        wordTempListAffecteNonAffectesAnnuelsProcessor.getEleveNosAffecteParClasse(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Liste aff+non aff annuel");
        wordTempListMajorAnnuelsProcessor.getListeMajorClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Majors annuel");
        wordTempStatistiqueGenreProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre,
                anneeId);
        trace("Genre");
        wordTempResultaAffetNonAffProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Résultats aff+non aff");
        wordTempResultaAffetNonAffAnnuelsProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Résultats aff+non aff annuel");
        wordTempListStatistiqueDfaAnnuelsProcessor.getListeMajorClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Statistique DFA");
        wordTempTauxPromotionInterneProcessor.creerTableauTauxPromotion(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Taux promotion interne");
    }

    private void fillAutresTrimestres(XWPFDocument document, Long idEcole, String libelleAnnee, String libelleTrimetre,
            Long anneeId) throws Exception {
        wordTempIdentiteProcessor.getIdentiteProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Identité");
        wordTempResultaAffProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Résultats affectés");
        wordTempRecapResultatProcessor.getRecapResultatAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Récap affectés");
        wordTempResultaNonAffProcessor.getResultatNonAffProcessor(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Résultats non affectés");
        wordTempRecapNonAffResultatProcessor.getRecapResultatANonffProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Récap non affectés");
        wordTempRecapAffNonAffResultatProcessor.getRecapResultatAffProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Récap aff+non aff");
        wordTempListMajorProcessor.getListeMajorClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Majors");
        wordTempListAffectesProcessor.getEleveAffecteParClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Liste affectés");
        wordTempListNonAffectesProcessor.getEleveNosAffecteParClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Liste non affectés");
        wordTempListTransfertProcessor.getListTransfert(document, idEcole);
        trace("Transferts");
        wordTempRepartitionAnneeNaissProcessor.getListRepartitionParAnnee(document, idEcole, libelleAnnee,
                libelleTrimetre);
        trace("Répartition naissance");
        wordTempListBoursiersProcessor.getListeBoursierClasse(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Boursiers");
        wordTempEffectifApprocheNiveauGenre.getListeApprocheParNiveau(document, idEcole, libelleAnnee, libelleTrimetre);
        trace("Effectif niveau/genre");
        wordTempStatistiqueLangueVivanteProcessor.getResultatAffProcessor(document, idEcole, libelleAnnee,
                libelleTrimetre, anneeId);
        trace("Langue vivante");
    }

    private static void trace(String step) {
        if (LOG.isTraceEnabled()) {
            LOG.tracef("WordTempDren4Processor: %s", step);
        }
    }
}

package com.vieecoles.processors.dren4;

import com.vieecoles.dto.RecapDesResultatsElevesAffecteDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;
import com.vieecoles.processors.dren4.Annuels.WordTempListAffecteNonAffectesAnnuelsProcessor;
import com.vieecoles.processors.dren4.Annuels.WordTempListMajorAnnuelsProcessor;
import com.vieecoles.processors.dren4.Annuels.WordTempListStatistiqueDfaAnnuelsProcessor;
import com.vieecoles.processors.dren4.Annuels.WordTempResultaAffetNonAffAnnuelsProcessor;
import com.vieecoles.processors.dren4.Annuels.WordTempTauxPromotionInterneProcessor;
import com.vieecoles.services.etats.EleveAffecteParClasseServices;
import com.vieecoles.services.etats.appachePoi.resultatsPoiServices;
import com.vieecoles.services.etats.resultatsRecapServices;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

@ApplicationScoped
public class WordTempDren4Processor {
    @Inject
    EntityManager em;
    @Inject
    resultatsPoiServices resultatsServices ;
    @Inject
    resultatsRecapServices resultatsRecapServices ;
    @Inject
    WordTempRecapResultatProcessor wordTempRecapResultatProcessor ;
    @Inject
    WordTempResultaAffetNonAffProcessor wordTempResultaAffProcessor ;
    @Inject
    WordTempListAffectesProcessor wordTempListAffectesProcessor ;
    @Inject
    EleveAffecteParClasseServices eleveAffecteParClasseServices ;
    @Inject
    WordTempListMajorProcessor wordTempListMajorProcessor ;
    @Inject
    WordTempListTransfertProcessor wordTempListTransfertProcessor ;
    @Inject
    com.vieecoles.processors.dren4.WordTempRepartitionAnneeNaissProcessor
        WordTempRepartitionAnneeNaissProcessor ;
    @Inject
    WordTempListBoursiersProcessor wordTempListBoursiersProcessor ;
    @Inject
    WordTempEffectifApprocheNiveauGenre wordTempEffectifApprocheNiveauGenre ;
    @Inject
    WordTempIdentiteProcessor wordTempIdentiteProcessor;
    @Inject
    WordTempResultaNonAffProcessor wordTempResultaNonAffProcessor ;
    @Inject
    WordTempRecapNonResultatProcessor wordTempRecapNonAffResultatProcessor ;
    @Inject
    WordTempRecapAffNonAffResultatProcessor wordTempRecapAffNonAffResultatProcessor ;
    @Inject
    WordTempListNonAffectesProcessor wordTempListNonAffectesProcessor;
    @Inject
    WordTempListAffecteNonAffectesProcessor wordTempListAffecteNonAffectesProcessor;
    @Inject
    WordTempListStatistiqueDfaAnnuelsProcessor wordTempListStatistiqueDfaAnnuelsProcessor ;
    @Inject
    WordTempTauxPromotionInterneProcessor wordTempTauxPromotionInterneProcessor ;
    @Inject
    WordTempResultaAffetNonAffProcessor  wordTempResultaAffetNonAffProcessor ;
    @Inject
    WordTempListAffecteNonAffectesAnnuelsProcessor wordTempListAffecteNonAffectesAnnuelsProcessor ;
    @Inject
    WordTempResultaAffetNonAffAnnuelsProcessor wordTempResultaAffetNonAffAnnuelsProcessor ;
    @Inject
    WordTempListMajorAnnuelsProcessor wordTempListMajorAnnuelsProcessor;
    @Inject
    WordTempStatistiqueGenreProcessor wordTempStatistiqueGenreProcessor ;
    public  byte[] generateWordFile(Long idEcole,String libelleAnnee ,String  libelleTrimetre, ByteArrayInputStream  fis) throws Exception {

        Long anneeId=getIdAnnee(idEcole,libelleAnnee,libelleTrimetre);
        List<eleveAffecteParClasseDto>  elevAffectes = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        elevAffectes= eleveAffecteParClasseServices.eleveAffecteParClasse(idEcole ,libelleAnnee,libelleTrimetre) ;

        List<RecapDesResultatsElevesAffecteDto> recapResulAff = new ArrayList<>();
        try {
            recapResulAff = resultatsRecapServices.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre);

        } catch (Exception e) {
            e.printStackTrace();
        }

        XWPFDocument document = new XWPFDocument(fis);
        if (libelleTrimetre.equals("Troisième Trimestre")) {
            wordTempResultaAffetNonAffProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println(" Statistique Resultat aff et Non Aff ok");
            wordTempListAffecteNonAffectesProcessor.getEleveNosAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println("Liste des élèves aff et Non Aff ok");
            wordTempListMajorProcessor.getListeMajorClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println("Liste des major  ok");
            wordTempListAffecteNonAffectesAnnuelsProcessor.getEleveNosAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println("Liste des élèves aff et Non Aff  Annuels ok");
            wordTempResultaAffetNonAffAnnuelsProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println(" Statistique Resultat aff et Non Aff  Annuelsok");
            wordTempListMajorAnnuelsProcessor.getListeMajorClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println("Liste des major Annuels ok");
            wordTempStatistiqueGenreProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre,anneeId);
            System.out.println("Statistique genre ok");

            wordTempListStatistiqueDfaAnnuelsProcessor.getListeMajorClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println("Statistique DFA ok");
            wordTempTauxPromotionInterneProcessor.creerTableauTauxPromotion(document,idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println("Statistique Promotion interne ok");
        } else {
            wordTempResultaAffetNonAffProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println(" Statistique Resultat aff et Non Aff ok");
            wordTempListAffecteNonAffectesProcessor.getEleveNosAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println("Liste des élèves aff et Non Aff ok");
            wordTempListMajorProcessor.getListeMajorClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println("Liste des major  ok");
           
        }

        // Sauvegarder le document modifié dans un tableau de bytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.write(outputStream);
        document.close();
        fis.close();

        return outputStream.toByteArray();
    }
    public  Long getIdAnnee(Long idEcole ,String libelleAnnee,String periode){
        try {
            Long   anneeID = (Long) em.createQuery("select distinct b.anneeId  from Bulletin b  where   b.anneeLibelle=:libelleAnnee " +
                    " and b.libellePeriode=:periode and b.ecoleId=:idEcole ")
                .setParameter("periode",periode)
                .setParameter("libelleAnnee", libelleAnnee)
                .setParameter("idEcole", idEcole)
                .getSingleResult();
            return  anneeID ;
        } catch (NoResultException e){
            return null ;
        }

    }

}

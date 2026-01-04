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
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
    WordTempResultaAffProcessor wordTempResultaAffProcessor ;
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
    @Inject
    WordTempStatistiqueLangueVivanteProcessor wordTempStatistiqueLangueVivanteProcessor ;
    public  byte[] generateWordFile(Long idEcole,String libelleAnnee ,String  libelleTrimetre, ByteArrayInputStream  fis) throws Exception {

        Long anneeId=getIdAnnee(idEcole,libelleAnnee,libelleTrimetre);
        List<eleveAffecteParClasseDto>  elevAffectes = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        elevAffectes= eleveAffecteParClasseServices.eleveAffecteParClasse(idEcole ,libelleAnnee,libelleTrimetre) ;

       

        XWPFDocument document = new XWPFDocument(fis);
        if (libelleTrimetre.equals("Troisième Trimestre")) {

             wordTempListAffecteNonAffectesProcessor.getEleveNosAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println("Liste des élèves aff et Non Aff ok");
            wordTempListMajorProcessor.getListeMajorClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println("Liste des major  ok");
            wordTempListAffecteNonAffectesAnnuelsProcessor.getEleveNosAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println("Liste des élèves aff et Non Aff  Annuels ok");

            wordTempListMajorAnnuelsProcessor.getListeMajorClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println("Liste des major Annuels ok");
            wordTempStatistiqueGenreProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre,anneeId);
            System.out.println("Statistique genre ok");

            wordTempResultaAffetNonAffProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println(" Statistique Resultat aff et Non Aff ok");
            wordTempResultaAffetNonAffAnnuelsProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println(" Statistique Resultat aff et Non Aff  Annuelsok");
            wordTempListStatistiqueDfaAnnuelsProcessor.getListeMajorClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println("Statistique DFA ok>>>");
            wordTempTauxPromotionInterneProcessor.creerTableauTauxPromotion(document,idEcole ,libelleAnnee,libelleTrimetre);
            System.out.println("Statistique Promotion interne ok");
        } else {
           // System.out.println("Trimestre non trouvé "+libelleTrimetre);

             wordTempIdentiteProcessor.getIdentiteProcessor(document,idEcole,libelleAnnee,libelleTrimetre);
        System.out.println("Statistique Identité okjj");
       wordTempResultaAffProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("ResultaAff ok");
       wordTempRecapResultatProcessor.getRecapResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("RecapResultaAff ok");
        wordTempResultaNonAffProcessor.getResultatNonAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("ResultaNonAff ok");
       wordTempRecapNonAffResultatProcessor.getRecapResultatANonffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("RecapResultaNonAff ok");
      wordTempRecapAffNonAffResultatProcessor.getRecapResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("RecapResultaAffEtNonAff ok"); 
       wordTempListMajorProcessor.getListeMajorClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("ListMajor ok");
        wordTempListAffectesProcessor.getEleveAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Liste Eleve Affectes ok");
        wordTempListNonAffectesProcessor.getEleveNosAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Liste  Eleve non Affectes ok");

         wordTempListTransfertProcessor.getListTransfert(document,idEcole) ;
        System.out.println("ListeTransfert ok");
        WordTempRepartitionAnneeNaissProcessor.getListRepartitionParAnnee(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("ListRepartition Annee Naissance ok");
        wordTempListBoursiersProcessor.getListeBoursierClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("List Boursier  ok");
        wordTempEffectifApprocheNiveauGenre.getListeApprocheParNiveau(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("List Approche Niveau Genre ok");
        wordTempStatistiqueLangueVivanteProcessor.getResultatAffProcessor(document,idEcole,libelleAnnee,libelleTrimetre,anneeId); 
 
 
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

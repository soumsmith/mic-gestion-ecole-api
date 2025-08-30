package com.vieecoles.processors.bouake;

import com.vieecoles.dto.RecapDesResultatsElevesAffecteDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;
import com.vieecoles.processors.bouake.annuels.WordTempIdentiteAnnuelProcessor;
import com.vieecoles.processors.bouake.annuels.WordTempListAffecteNonAffectesAnnuelProcessor;
import com.vieecoles.processors.bouake.annuels.WordTempListAffectesAnnuelProcessor;
import com.vieecoles.processors.bouake.annuels.WordTempListMajorAnnuelProcessor;
import com.vieecoles.processors.bouake.annuels.WordTempResultaAffAnnuelProcessor;
import com.vieecoles.processors.bouake.annuels.WordTempResultaAffEtNonAffAnnuelProcessor;
import com.vieecoles.processors.bouake.annuels.WordTempResultaNonAffAnnuelProcessor;
import com.vieecoles.services.etats.EleveAffecteParClasseServices;
import com.vieecoles.services.etats.appachePoi.resultatsPoiServices;
import com.vieecoles.services.etats.resultatsRecapServices;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.apache.poi.xwpf.usermodel.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class WordTempProcessorBouake {
  @Inject
  EntityManager em;
    @Inject
    resultatsPoiServices resultatsServices ;
    @Inject
    resultatsRecapServices resultatsRecapServices ;
    @Inject
    WordTempResultaAffProcessor  wordTempRecapResultatProcessor ;
  @Inject
  WordTempResultaAffAnnuelProcessor wordTempRecapResultatAnnuelProcessor ;


    @Inject
    WordTempListAffectesProcessor  wordTempListAffectesProcessor ;
  @Inject
  WordTempListAffectesAnnuelProcessor wordTempListAffectesAnnuelProcessor ;
    @Inject
    EleveAffecteParClasseServices eleveAffecteParClasseServices ;
    @Inject
    WordTempListMajorProcessor  wordTempListMajorProcessor ;
  @Inject
  WordTempListMajorAnnuelProcessor wordTempListMajorAnnuelProcessor ;
    @Inject
    WordTempListTransfertProcessor  wordTempListTransfertProcessor ;
    @Inject
    com.vieecoles.processors.yamoussoukro.WordTempRepartitionAnneeNaissProcessor
        WordTempRepartitionAnneeNaissProcessor ;
    @Inject
    WordTempListBoursiersProcessor wordTempListBoursiersProcessor ;
    @Inject
    WordTempEffectifApprocheNiveauGenre  wordTempEffectifApprocheNiveauGenre ;
    @Inject
    WordTempIdentiteProcessor wordTempIdentiteProcessor;
  @Inject
  WordTempIdentiteAnnuelProcessor wordTempIdentiteAnnuelProcessor;
    @Inject
    WordTempResultaNonAffProcessor  wordTempResultaNonAffProcessor ;
  @Inject
  WordTempResultaNonAffAnnuelProcessor  wordTempResultaNonAffAnnuelProcessor ;
  @Inject
  WordTempResultaAffEtNonAffProcessor  wordTempResultaAffEtNonAffProcessor ;
  @Inject
  WordTempResultaAffEtNonAffAnnuelProcessor wordTempResultaAffEtNonAffAnnuelProcessor ;
    @Inject
    WordTempEffectifConseilClasseProcessor wordTempEffectifConseilClasseProcessor ;

  @Inject
  WordTempEffectifConseilClasse2Processor wordTempEffectifConseilClasse2Processor ;
  @Inject
  WordTempPyramideProcessor wordTempPyramideProcessor ;
    @Inject
    WordTempRecapAffNonAffResultatProcessor wordTempRecapAffNonAffResultatProcessor ;
    @Inject
    WordTempStatistiqueAgeProcessor wordTempStatistiqueAgeProcessor;
    @Inject
  WordTempListAffecteNonAffectesProcessor wordTempListAffecteNonAffectesProcessor ;
  @Inject
  WordTempListAffecteNonAffectesAnnuelProcessor wordTempListAffecteNonAffectesAnnuelProcessor ;
    @Inject

  WordTempStatistiqueLangueVivanteProcessor wordTempStatistiqueLangueVivanteProcessor ;
        @Inject
   WordTempStatistiqueProfesseurProcessor wordTempStatistiqueProfesseurProcessor ;

    public  byte[] generateWordFile(Long idEcole,String libelleAnnee ,String  libelleTrimetre, ByteArrayInputStream  fis) throws Exception {
      Long anneeId=getIdAnnee(idEcole,libelleAnnee,libelleTrimetre);



        XWPFDocument document = new XWPFDocument(fis);
      if (libelleTrimetre.equals("Troisième Trimestre")) {


        wordTempIdentiteAnnuelProcessor.getIdentiteProcessor(document,idEcole,libelleAnnee,libelleTrimetre);
        wordTempListAffecteNonAffectesProcessor.getEleveNosAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Liste affecte et non affecté Ok");
        wordTempListAffecteNonAffectesAnnuelProcessor.getEleveNosAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Liste affecte et non affecté Annuel Ok");

        wordTempListAffectesProcessor.getEleveAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Liste Eleve Affectes ok");
        wordTempListAffectesAnnuelProcessor.getEleveAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Liste Eleve Affectes Annuel ok");

        wordTempRecapResultatProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("RecapResultaAff ok");
        wordTempRecapResultatAnnuelProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("RecapResultaAff Annuel ok");


        wordTempResultaNonAffProcessor.getResultatNonAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("ResultaNonAff ok");
        wordTempResultaNonAffAnnuelProcessor.getResultatNonAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("ResultaNonAff Annuel ok");


        wordTempResultaAffEtNonAffProcessor.getResultatAffEtNonAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("ResultaAffEtNonAff ok");
        wordTempResultaAffEtNonAffAnnuelProcessor.getResultatAffEtNonAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("ResultaAffEtNonAff Annuel ok");

      wordTempListMajorProcessor.getListeMajorClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("ListMajor ok");

        wordTempListMajorAnnuelProcessor.getListeMajorClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("ListMajor ok Annuel");


        wordTempListTransfertProcessor.getListTransfert(document,idEcole) ;
        System.out.println("ListeTransfert ok");
        wordTempEffectifConseilClasseProcessor.getEffectifConseilProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Effectifi Conseil ok");
        wordTempEffectifConseilClasse2Processor.getEffectifConseilProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Effectifi Conseil ok");
        wordTempPyramideProcessor.getNombreClasseProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Pyramide ok");
        wordTempStatistiqueAgeProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Statistique ok");
        wordTempStatistiqueProfesseurProcessor.getNbreProfessProcessor(document,idEcole,libelleAnnee,libelleTrimetre,anneeId);
        System.out.println("Statistique professeur ok");
      } else {
        wordTempIdentiteProcessor.getIdentiteProcessor(document,idEcole,libelleAnnee,libelleTrimetre);
        wordTempListAffecteNonAffectesProcessor.getEleveNosAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Liste affecte et non affecté Ok");
        wordTempListAffectesProcessor.getEleveAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Liste Eleve Affectes ok");
        wordTempRecapResultatProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("RecapResultaAff ok");
        wordTempResultaNonAffProcessor.getResultatNonAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("ResultaNonAff ok");
        wordTempResultaAffEtNonAffProcessor.getResultatAffEtNonAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("ResultaAffEtNonAff ok");
        wordTempListMajorProcessor.getListeMajorClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("ListMajor ok");
        wordTempListTransfertProcessor.getListTransfert(document,idEcole) ;
        System.out.println("ListeTransfert ok");
        wordTempEffectifConseilClasseProcessor.getEffectifConseilProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Effectifi Conseil ok");
        wordTempEffectifConseilClasse2Processor.getEffectifConseilProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Effectifi Conseil ok");
        wordTempPyramideProcessor.getNombreClasseProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Pyramide ok");
        wordTempStatistiqueAgeProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Statistique ok");
        wordTempStatistiqueProfesseurProcessor.getNbreProfessProcessor(document,idEcole,libelleAnnee,libelleTrimetre,anneeId);
        System.out.println("Statistique professeur ok");
      }



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

package com.vieecoles.processors.bouake;

import com.vieecoles.dto.RecapDesResultatsElevesAffecteDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;
import com.vieecoles.services.etats.EleveAffecteParClasseServices;
import com.vieecoles.services.etats.appachePoi.resultatsPoiServices;
import com.vieecoles.services.etats.resultatsRecapServices;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.apache.poi.xwpf.usermodel.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
    WordTempResultaAffProcessor  wordTempResultaAffProcessor ;
    @Inject
    WordTempListAffectesProcessor  wordTempListAffectesProcessor ;
    @Inject
    EleveAffecteParClasseServices eleveAffecteParClasseServices ;
    @Inject
    WordTempListMajorProcessor  wordTempListMajorProcessor ;
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
    WordTempResultaNonAffProcessor  wordTempResultaNonAffProcessor ;
  @Inject
  WordTempResultaAffEtNonAffProcessor  wordTempResultaAffEtNonAffProcessor ;
    @Inject
    WordTempEffectifConseilClasseProcessor wordTempEffectifConseilClasseProcessor ;
  @Inject
  WordTempPyramideProcessor wordTempPyramideProcessor ;
    @Inject
    WordTempRecapAffNonAffResultatProcessor wordTempRecapAffNonAffResultatProcessor ;
    @Inject
    WordTempStatistiqueAgeProcessor wordTempStatistiqueAgeProcessor;
    @Inject
  WordTempListAffecteNonAffectesProcessor wordTempListAffecteNonAffectesProcessor ;
        @Inject

  WordTempStatistiqueLangueVivanteProcessor wordTempStatistiqueLangueVivanteProcessor ;

    public  byte[] generateWordFile(Long idEcole,String libelleAnnee ,String  libelleTrimetre, ByteArrayInputStream  fis) throws Exception {
      Long anneeId=getIdAnnee(idEcole,libelleAnnee,libelleTrimetre);



        XWPFDocument document = new XWPFDocument(fis);
     /* wordTempListAffecteNonAffectesProcessor.getEleveNosAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
      System.out.println("Liste affecte et non affecté Ok");

      wordTempListAffectesProcessor.getEleveAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
      System.out.println("Liste Eleve Affectes ok");

      wordTempRecapResultatProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);

      System.out.println("RecapResultaAff ok");

      wordTempResultaNonAffProcessor.getResultatNonAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
      System.out.println("ResultaNonAff ok");

      wordTempResultaAffEtNonAffProcessor.getResultatAffEtNonAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
      System.out.println("ResultaAffEtNonAff ok");

      wordTempListMajorProcessor.getListeMajorParNiveau(document,idEcole ,libelleAnnee,libelleTrimetre);
      System.out.println("ListMajor ok");
      wordTempListTransfertProcessor.getListTransfert(document,idEcole) ;
      System.out.println("ListeTransfert ok");

      wordTempEffectifConseilClasseProcessor.getEffectifConseilProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
      System.out.println("Effectifi Conseil ok");

      wordTempPyramideProcessor.getNombreClasseProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
      System.out.println("Pyramide ok");*/

      wordTempStatistiqueAgeProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
      System.out.println("Statistique ok");

      /*
       wordTempRecapNonAffResultatProcessor.getRecapResultatANonffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);


      System.out.println("RecapResultaAff ok");*/

      /*
        wordTempResultaAffProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("ResultaAff ok");

        wordTempRecapAffNonAffResultatProcessor.getRecapResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("RecapResultaAffEtNonAff ok");
        wordTempListMajorProcessor.getListeMajorClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("ListMajor ok");

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
      wordTempStatistiqueLangueVivanteProcessor.getResultatAffProcessor(document,idEcole,libelleAnnee,libelleTrimetre,anneeId);*/
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

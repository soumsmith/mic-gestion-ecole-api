package com.vieecoles.processors.yamoussoukro;

import com.vieecoles.dto.RecapDesResultatsElevesAffecteDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;
import com.vieecoles.processors.yamoussoukro.Annuels.WordTempListAffectesAnnuelsProcessor;
import com.vieecoles.processors.yamoussoukro.Annuels.WordTempListMajorAnnuelsProcessor;
import com.vieecoles.processors.yamoussoukro.Annuels.WordTempListNonAffectesAnnuelsProcessor;
import com.vieecoles.processors.yamoussoukro.Annuels.WordTempRecapAffNonAffResultatAnnuelsProcessor;
import com.vieecoles.processors.yamoussoukro.Annuels.WordTempRecapNonResultatAnnuelsProcessor;
import com.vieecoles.processors.yamoussoukro.Annuels.WordTempRecapResultatAnnuelsProcessor;
import com.vieecoles.processors.yamoussoukro.Annuels.WordTempResultaAffAnnuelsProcessor;
import com.vieecoles.processors.yamoussoukro.Annuels.WordTempResultaNonAffAnnuelsProcessor;
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
public class WordTempProcessor {
  @Inject
  EntityManager em;
    @Inject
    resultatsPoiServices resultatsServices ;
    @Inject
    resultatsRecapServices resultatsRecapServices ;
    @Inject
    WordTempRecapResultatProcessor  wordTempRecapResultatProcessor ;
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
    WordTempRecapNonResultatProcessor wordTempRecapNonAffResultatProcessor ;
    @Inject
    WordTempRecapAffNonAffResultatProcessor wordTempRecapAffNonAffResultatProcessor ;
    @Inject
    WordTempListNonAffectesProcessor wordTempListNonAffectesProcessor;
    @Inject
    WordTempStatistiqueLangueVivanteProcessor wordTempStatistiqueLangueVivanteProcessor ;
    @Inject
    WordTempResultaAffAnnuelsProcessor  wordTempResultaAffAnnuelsProcessor ;
    @Inject
    WordTempRecapResultatAnnuelsProcessor wordTempRecapResultatAnnuelsProcessor ;
    @Inject
    WordTempResultaNonAffAnnuelsProcessor wordTempResultaNonAffAnnuelsProcessor;
    @Inject
    WordTempRecapNonResultatAnnuelsProcessor  wordTempRecapNonResultatAnnuelsProcessor;
    @Inject
    WordTempListAffectesAnnuelsProcessor wordTempListAffectesAnnuelsProcessor;
    @Inject
    WordTempListMajorAnnuelsProcessor wordTempListMajorAnnuelsProcessor ;
    @Inject
    WordTempListNonAffectesAnnuelsProcessor wordTempListNonAffectesAnnuelsProcessor;
    @Inject
  WordTempRecapAffNonAffResultatAnnuelsProcessor wordTempRecapAffNonAffResultatAnnuelsProcessor ;

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
        XWPFTable table = document.getTableArray(20);




      if (libelleTrimetre.equals("Troisième Trimestre")) {
        wordTempIdentiteProcessor.getIdentiteProcessor(document,idEcole,libelleAnnee,libelleTrimetre);
        System.out.println("Statistique Identité okjj");
        wordTempListAffectesProcessor.getEleveAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Liste Eleve Affectes ok");
        wordTempListNonAffectesProcessor.getEleveNosAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Liste  Eleve non Affectes ok");
        wordTempListMajorProcessor.getListeMajorClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("ListMajor ok");
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

        wordTempListTransfertProcessor.getListTransfert(document,idEcole) ;
        System.out.println("ListeTransfert ok");
        WordTempRepartitionAnneeNaissProcessor.getListRepartitionParAnnee(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("ListRepartition Annee Naissance ok");
        wordTempListBoursiersProcessor.getListeBoursierClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("List Boursier  ok");
        wordTempEffectifApprocheNiveauGenre.getListeApprocheParNiveau(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("List Approche Niveau Genre ok");
        wordTempStatistiqueLangueVivanteProcessor.getResultatAffProcessor(document,idEcole,libelleAnnee,libelleTrimetre,anneeId);

      // Annuels
    wordTempResultaAffAnnuelsProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
    System.out.println("Resultats Affectés Annuels ok");
      wordTempRecapResultatAnnuelsProcessor.getRecapResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
      System.out.println("Racap Resultats Affectés Annuels ok");
      wordTempResultaNonAffAnnuelsProcessor.getResultatNonAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
      System.out.println("Resultats Non Affectés Annuels ok");
      wordTempRecapNonResultatAnnuelsProcessor.getRecapResultatANonffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
      System.out.println("Racap Resultats Non Affectés Annuels ok");
      wordTempListAffectesAnnuelsProcessor.getEleveAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
      System.out.println("Liste Affectés Annuels ok");
      wordTempListNonAffectesAnnuelsProcessor.getEleveNosAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
      System.out.println("Liste NON Affectés Annuels ok");
       // wordTempRecapAffNonAffResultatAnnuelsProcessor
        wordTempRecapAffNonAffResultatAnnuelsProcessor.getRecapResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("RecapResultaAffEtNonAff Annuels ok");
      wordTempListMajorAnnuelsProcessor.getListeMajorClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
      System.out.println("Liste Major Annuels ok");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.write(outputStream);
        document.close();
        fis.close();

        return outputStream.toByteArray();
      } else {
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

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.write(outputStream);
        document.close();
        fis.close();
        return outputStream.toByteArray();
      }

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

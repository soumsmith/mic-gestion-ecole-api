package com.vieecoles.processors.dren3;

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
public class WordTempProcessorDren3 {
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
    WordTempListMajorNewProcessor  wordTempListMajorProcessor ;
    @Inject
    WordTempListTransfertProcessor  wordTempListTransfertProcessor ;
    @Inject
    WordTempRepartitionAnneeNaissProcessor WordTempRepartitionAnneeNaissProcessor ;
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
    WordTempStatistiqueResultatProcessor wordTempStatistiqueResultatProcessor ;
    @Inject
    WordTempStatistiqueAdmiRedoublantProcessor wordTempStatistiqueAdmiRedoublantProcessor;
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

     /* wordTempIdentiteProcessor.getIdentiteProcessor(document,idEcole,libelleAnnee,libelleTrimetre);
      System.out.println("Statistique Identité ok");*/
      wordTempRecapAffNonAffResultatProcessor.getRecapResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Statistique Ok");
        /*wordTempStatistiqueResultatProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
      System.out.println("Details Statistique Ok");
    wordTempStatistiqueAdmiRedoublantProcessor.getStatistiqueAdminRedoublantProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
      System.out.println("Redoublant Ok");
        wordTempListAffectesProcessor.getEleveAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
      System.out.println("Liste affecte Ok");
        wordTempListNonAffectesProcessor.getEleveNosAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
      System.out.println("Liste Non affecte Ok");
      wordTempListAffecteNonAffectesProcessor.getEleveNosAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
      System.out.println("Liste affecte et non affecté Ok");
      wordTempListMajorProcessor.getMajorParNiveau(document,idEcole ,libelleAnnee,libelleTrimetre);
      System.out.println("Liste Major Ok");
      wordTempListTransfertProcessor.getListTransfert(document,idEcole) ;
      System.out.println("Liste Transfert1 Ok");
      wordTempStatistiqueTransfertProcessor.getResultatAffProcessor(document,idEcole,libelleAnnee,libelleTrimetre,anneeId);
      System.out.println("Liste Transfert2 Ok");
       wordTempStatistiqueNationaliteProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre,anneeId);
      System.out.println("Statistique  Nationalité Ok");
      wordTempStatistiqueAgeProcessor.getResultatAffProcessor(document,idEcole,libelleAnnee,libelleTrimetre,anneeId);
      System.out.println("Statistique Age Ok");
      wordTempStatistiqueBoursierProcessor.getResultatAffProcessor(document,idEcole,libelleAnnee,libelleTrimetre,anneeId);
      System.out.println("Statistique Boursier Ok");

      wordTempStatistiqueGenreProcessor.getResultatAffProcessor(document,idEcole,libelleAnnee,libelleTrimetre,anneeId);
*/
      System.out.println("Statistique Genre Ok");
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

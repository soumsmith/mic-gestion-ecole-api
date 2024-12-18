package com.vieecoles.processors.Duekoue;

import com.vieecoles.dto.RecapDesResultatsElevesAffecteDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;
import com.vieecoles.processors.dren3.WordTempEffectifApprocheNiveauGenre;
import com.vieecoles.processors.dren3.WordTempIdentiteProcessor;
import com.vieecoles.processors.dren3.WordTempListAffecteNonAffectesProcessor;
import com.vieecoles.processors.dren3.WordTempListAffectesProcessor;
import com.vieecoles.processors.dren3.WordTempListBoursiersProcessor;
import com.vieecoles.processors.dren3.WordTempListMajorNewProcessor;
import com.vieecoles.processors.dren3.WordTempListNonAffectesProcessor;
import com.vieecoles.processors.dren3.WordTempListTransfertProcessor;
import com.vieecoles.processors.dren3.WordTempRecapAffNonAffResultatProcessor;
import com.vieecoles.processors.dren3.WordTempRecapNonResultatProcessor;
import com.vieecoles.processors.dren3.WordTempRecapResultatProcessor;
import com.vieecoles.processors.dren3.WordTempResultaAffProcessor;
import com.vieecoles.processors.dren3.WordTempResultaNonAffProcessor;
import com.vieecoles.processors.dren3.WordTempStatistiqueAdmiRedoublantProcessor;
import com.vieecoles.processors.dren3.WordTempStatistiqueAgeProcessor;
import com.vieecoles.processors.dren3.WordTempStatistiqueBoursierProcessor;
import com.vieecoles.processors.dren3.WordTempStatistiqueGenreProcessor;
import com.vieecoles.processors.dren3.WordTempStatistiqueNationaliteProcessor;
import com.vieecoles.processors.dren3.WordTempStatistiqueResultatProcessor;
import com.vieecoles.processors.dren3.WordTempStatistiqueTransfertProcessor;
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
public class WordTempProcessorDuekoue {
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
    WordTempListMajorNewProcessor wordTempListMajorProcessor ;
    @Inject
    WordTempListTransfertProcessor wordTempListTransfertProcessor ;
    @Inject
    com.vieecoles.processors.dren3.WordTempRepartitionAnneeNaissProcessor
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


      wordTempRecapAffNonAffResultatProcessor.getRecapResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Statistique Ok");
        wordTempStatistiqueResultatProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
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

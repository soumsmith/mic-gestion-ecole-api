package com.vieecoles.processors.dren3;

import com.vieecoles.dto.RecapDesResultatsElevesAffecteDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;
import com.vieecoles.processors.dren3.annuels.WordTempIdentiteAnnuelProcessor;
import com.vieecoles.processors.dren3.annuels.WordTempListAffecteNonAffectesAnnuelProcessor;
import com.vieecoles.processors.dren3.annuels.WordTempListAffectesAnnuelProcessor;
import com.vieecoles.processors.dren3.annuels.WordTempListMajorAnnuelProcessor;
import com.vieecoles.processors.dren3.annuels.WordTempListNonAffectesAnnuelProcessor;
import com.vieecoles.processors.dren3.annuels.WordTempRecapAffNonAffResultatAnnuelProcessor;
import com.vieecoles.processors.dren3.annuels.WordTempStatistiqueAdmiRedoublantAnnuelProcessor;
import com.vieecoles.processors.dren3.annuels.WordTempStatistiqueResultatAnnuelProcessor;
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
  WordTempListAffectesAnnuelProcessor wordTempListAffectesAnnuelProcessor ;
    @Inject
    EleveAffecteParClasseServices eleveAffecteParClasseServices ;
    @Inject
    WordTempListMajorProcessor  wordTempListMajorProcessor ;
  @Inject
  WordTempListMajorAnnuelProcessor  wordTempListMajorAnnuelProcessor ;
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
  WordTempIdentiteAnnuelProcessor wordTempIdentiteAnnuelProcessor;
    @Inject
    WordTempResultaNonAffProcessor  wordTempResultaNonAffProcessor ;
    @Inject
    WordTempRecapNonResultatProcessor wordTempRecapNonAffResultatProcessor ;
    @Inject
    WordTempRecapAffNonAffResultatProcessor wordTempRecapAffNonAffResultatProcessor ;
  @Inject
  WordTempRecapAffNonAffResultatAnnuelProcessor wordTempRecapAffNonAffResultatAnnuelProcessor ;
    @Inject
    WordTempListNonAffectesProcessor wordTempListNonAffectesProcessor;
  @Inject
  WordTempListNonAffectesAnnuelProcessor wordTempListNonAffectesAnnuelProcessor;
    @Inject
    WordTempStatistiqueResultatProcessor wordTempStatistiqueResultatProcessor ;
  @Inject
  WordTempStatistiqueResultatAnnuelProcessor wordTempStatistiqueResultatAnnuelProcessor ;
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
        wordTempIdentiteAnnuelProcessor.getIdentiteProcessor(document,idEcole,libelleAnnee,libelleTrimetre);
        System.out.println("Statistique Identité okjj");
       wordTempRecapAffNonAffResultatProcessor.getRecapResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Statistique Ok");
        wordTempRecapAffNonAffResultatAnnuelProcessor.getRecapResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Statistique Annuel Ok");
        wordTempStatistiqueResultatProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Details Statistique Ok");
        wordTempStatistiqueResultatAnnuelProcessor.getResultatAffProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Details Statistique AnnuelOk");
        wordTempStatistiqueAdmiRedoublantProcessor.getStatistiqueAdminRedoublantProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Redoublant Ok");
        wordTempStatistiqueAdmiRedoublantAnnuelProcessor.getStatistiqueAdminRedoublantProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Redoublant Annuel Ok");

        wordTempListAffectesProcessor.getEleveAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Liste affecte Ok");
        wordTempListAffectesAnnuelProcessor.getEleveAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Liste affecte Annuel Ok");

        wordTempListNonAffectesProcessor.getEleveNosAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Liste Non affecte Ok");
        wordTempListNonAffectesAnnuelProcessor.getEleveNosAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Liste Non affecte Annuel Ok");

        wordTempListAffecteNonAffectesProcessor.getEleveNosAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Liste affecte et non affecté Ok");
        wordTempListAffecteNonAffectesAnnuelProcessor.getEleveNosAffecteParClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Liste affecte et non affecté Annuel Ok");

        wordTempListMajorProcessor.getListeMajorClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Liste Major Ok");

        wordTempListMajorAnnuelProcessor.getListeMajorClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
        System.out.println("Liste Major Annuel Ok");
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
        System.out.println("Statistique Genregg Ok");
        wordTempStatistiqueProfesseurProcessor.getNbreProfessProcessor(document,idEcole ,libelleAnnee,libelleTrimetre,anneeId);
      } else {
        wordTempIdentiteProcessor.getIdentiteProcessor(document,idEcole,libelleAnnee,libelleTrimetre);
        System.out.println("Statistique Identité okjj");
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
        wordTempListMajorProcessor.getListeMajorClasse(document,idEcole ,libelleAnnee,libelleTrimetre);
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
        System.out.println("Statistique Genregg Ok");
        wordTempStatistiqueProfesseurProcessor.getNbreProfessProcessor(document,idEcole ,libelleAnnee,libelleTrimetre,anneeId);
       
      
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

package com.vieecoles.processors.yamoussoukro;

import com.vieecoles.dto.RecapDesResultatsElevesAffecteDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;
import com.vieecoles.services.etats.EleveAffecteParClasseServices;
import com.vieecoles.services.etats.appachePoi.resultatsPoiServices;
import com.vieecoles.services.etats.resultatsRecapServices;
import org.apache.poi.xwpf.usermodel.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class WordTempProcessor {
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

    public  byte[] generateWordFile(Long idEcole,String libelleAnnee ,String  libelleTrimetre, ByteArrayInputStream  fis) throws Exception {


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

      //  wordTempRecapResultatProcessor.recapResultatAffecte(recapResulAff,table);
     // wordTempIdentiteProcessor.getIdentiteProcessor(document,idEcole ,libelleAnnee,libelleTrimetre);
      //  System.out.println("Identité ok");
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
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.write(outputStream);
        document.close();
        fis.close();

        return outputStream.toByteArray();
    }

}

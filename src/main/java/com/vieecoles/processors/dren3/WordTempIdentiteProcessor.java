package com.vieecoles.processors.dren3;

import com.vieecoles.dto.IdentiteEtatDto;
import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;
import com.vieecoles.services.etats.IdentiteEtatService;
import com.vieecoles.services.etats.appachePoi.resultatsPoiServices;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.*;

@ApplicationScoped
public class WordTempIdentiteProcessor {
    @Inject
    resultatsPoiServices resultatsServices ;
    @Inject
    IdentiteEtatService identiteEtatService ;
    public   void getIdentiteProcessor(XWPFDocument document ,
                                           Long idEcole ,String libelleAnnee , String libelleTrimestre) {
        List<IdentiteEtatDto>  identiteEtatDto = new ArrayList<>() ;
        identiteEtatDto= identiteEtatService.getIdentiteDto(idEcole) ;
        // Map pour les paragraphes
        // Parcourir tous les paragraphes du document
        String textToReplace ="NOM_ECOLE";

        for (XWPFParagraph paragraph : document.getParagraphs()) {
            // Boucler à travers les runs du paragraphe
            for (XWPFRun run : paragraph.getRuns()) {
                // Si nous trouvons un texte à modifier, on le change
                if (run.getText(0) != null && run.getText(0).contains("NOM_ECOLE")) {
                    run.setText(run.getText(0).replace("NOM_ECOLE", "Texte modifié"), 0);
                }
            }
        }
    }





}

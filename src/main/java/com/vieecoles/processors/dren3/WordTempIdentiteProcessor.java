package com.vieecoles.processors.dren3;

import com.microsoft.schemas.vml.CTTextbox;
import com.vieecoles.dto.IdentiteEtatDto;
import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.services.etats.IdentiteEtatService;
import com.vieecoles.services.etats.appachePoi.resultatsPoiServices;
import com.vieecoles.steph.entities.Ecole;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;
import org.openxmlformats.schemas.presentationml.x2006.main.CTShape;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

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

        String tableTitle = "CODE_TITRE_ENTREE";
        String ecoleName;
        Ecole myEcole= new Ecole();
        myEcole=Ecole.findById(idEcole);
        String libelleAnneeNew= libelleAnnee.replace("Année ", "") ;
        ecoleName=myEcole.getLibelle().toUpperCase();
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            // Parcourir toutes les "runs" du paragraphe
            for (XWPFRun run : paragraph.getRuns()) {
                String text = run.getText(0); // Récupérer le texte de la "run"
                if (text != null && text.contains("Ancien texte")) {
                    // Remplacer le texte
                    text = text.replace("Ancien texte", "RAPPORT DE FIN \n\nDU " + libelleTrimestre + " \n" + ecoleName + "\n " + libelleAnneeNew);
                    run.setText(text, 0); // Appliquer le texte modifié
                    paragraph.setAlignment(ParagraphAlignment.CENTER);
                }
            }
        }





    }





}

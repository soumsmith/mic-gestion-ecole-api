package com.vieecoles.processors.yamoussoukro;

import com.vieecoles.dto.IdentiteEtatDto;
import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.services.etats.IdentiteEtatService;
import com.vieecoles.services.etats.appachePoi.resultatsPoiServices;
import com.vieecoles.steph.entities.Ecole;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;

@ApplicationScoped
public class WordTempIdentiteProcessor {
    @Inject
    resultatsPoiServices resultatsServices ;
    @Inject
    IdentiteEtatService identiteEtatService ;
    public   void getIdentiteProcessor(XWPFDocument document ,
                                           Long idEcole ,String libelleAnnee , String libelleTrimestre) {
        List<IdentiteEtatDto>  identiteEtatDto = new ArrayList<>() ;
        //identiteEtatDto= identiteEtatService.getIdentiteDto(idEcole) ;

        String ecoleName;
        Ecole myEcole= new Ecole();
        myEcole=Ecole.findById(idEcole);
        String libelleAnneeNew= libelleAnnee.replace("Année ", "") ;
        ecoleName=myEcole.getLibelle().toUpperCase();
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            // Parcourir toutes les "runs" du paragraphe
            for (XWPFRun run : paragraph.getRuns()) {
                String text = run.getText(0); // Récupérer le texte de la "run"
                if (text != null && text.contains("DENOMINATION")) {
                    // Remplacer le texte
                    text = text.replace("DENOMINATION",  ecoleName );
                    run.setText(text, 0); // Appliquer le texte modifié
                    paragraph.setAlignment(ParagraphAlignment.LEFT);
                }
            }
        }
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            // Parcourir toutes les "runs" du paragraphe
            for (XWPFRun run : paragraph.getRuns()) {
                String text = run.getText(0); // Récupérer le texte de la "run"
                if (text != null && text.contains("Ancien texte")) {
                    // Remplacer le texte
                    text = text.replace("Ancien texte",  libelleAnneeNew);
                    run.setText(text, 0); // Appliquer le texte modifié
                    paragraph.setAlignment(ParagraphAlignment.CENTER);
                }
            }
        }

    }

    public static void replacePlaceholdersInShapes(XWPFDocument document, Map<String, String> dataMap) {
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                // Vérifier si le run contient un dessin
                CTDrawing[] drawings = run.getCTR().getDrawingArray();
                if (drawings != null && drawings.length > 0) {
                    for (CTDrawing drawing : drawings) {
                        XmlCursor cursor = drawing.newCursor();
                        cursor.selectPath("./*");

                        while (cursor.toNextSelection()) {
                            XmlObject object = cursor.getObject();
                            // Vérifier si l'objet contient du texte modifiable
                            if (object.xmlText().contains("txbxContent")) {
                                String text = object.xmlText();
                                for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                                    text = text.replace(entry.getKey(), entry.getValue());
                                }
                                // Mettre à jour le contenu XML
                                cursor.setTextValue(text);
                            }
                        }
                    }
                }
            }
        }
    }



    public static double arrondie(double d) {
        return Double.parseDouble(String.format("%.2f", d));
    }
}

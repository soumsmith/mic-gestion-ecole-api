package com.vieecoles.processors.dren3;

import com.vieecoles.dto.IdentiteEtatDto;
import com.vieecoles.services.etats.IdentiteEtatService;
import com.vieecoles.services.etats.appachePoi.resultatsPoiServices;
import com.vieecoles.steph.entities.Ecole;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
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
        identiteEtatDto= identiteEtatService.getIdentiteDto(idEcole) ;
        
        String ecoleName;
        Ecole myEcole= new Ecole();
        myEcole=Ecole.findById(idEcole);
        ecoleName=myEcole.getLibelle().toUpperCase();
        String trimestreLibelle = libelleTrimestre != null ? libelleTrimestre : "";

        replaceBodyPlaceholder(document, "{{NOM_ECOLE}}", ecoleName);
        replaceBodyPlaceholder(document, "{{TRIMESTRE}}", trimestreLibelle);

        replacePlaceholdersInTextBoxes(document, ecoleName, "{{NOM_ECOLE}}");
        replacePlaceholdersInTextBoxes(document, trimestreLibelle, "{{TRIMESTRE}}");
    }

    private static void replaceBodyPlaceholder(XWPFDocument document, String placeholder, String replacementValue) {
        if (replacementValue == null) {
            replacementValue = "";
        }
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            String fullText = paragraph.getText();
            if (fullText != null && fullText.contains(placeholder)) {
                String newFullText = fullText.replace(placeholder, replacementValue);

                boolean isBold = false;
                boolean isItalic = false;
                int fontSize = 0;
                String fontFamily = null;
                String color = null;

                for (XWPFRun run : paragraph.getRuns()) {
                    try {
                        if (color == null || color.isEmpty()) {
                            String runColor = run.getColor();
                            if (runColor != null && !runColor.isEmpty()) {
                                color = runColor;
                            }
                        }
                    } catch (Exception e) {
                        // ignore
                    }

                    if (!isBold) {
                        try {
                            isBold = run.isBold();
                        } catch (Exception e) {
                            // ignore
                        }
                    }
                    if (!isItalic) {
                        try {
                            isItalic = run.isItalic();
                        } catch (Exception e) {
                            // ignore
                        }
                    }
                    if (fontSize == 0) {
                        try {
                            fontSize = run.getFontSize();
                        } catch (Exception e) {
                            try {
                                double fontSizeDouble = run.getFontSizeAsDouble();
                                if (fontSizeDouble > 0) {
                                    fontSize = (int) fontSizeDouble;
                                }
                            } catch (Exception e2) {
                                // ignore
                            }
                        }
                    }
                    if (fontFamily == null || fontFamily.isEmpty()) {
                        try {
                            fontFamily = run.getFontFamily();
                        } catch (Exception e) {
                            // ignore
                        }
                    }
                }

                int runsCount = paragraph.getRuns().size();
                for (int i = runsCount - 1; i >= 0; i--) {
                    paragraph.removeRun(i);
                }

                XWPFRun newRun = paragraph.createRun();
                newRun.setText(newFullText);

                try {
                    newRun.setBold(isBold);
                } catch (Exception e) {
                    // ignore
                }
                try {
                    newRun.setItalic(isItalic);
                } catch (Exception e) {
                    // ignore
                }
                try {
                    if (fontSize > 0) {
                        newRun.setFontSize(fontSize);
                    }
                } catch (Exception e) {
                    // ignore
                }
                try {
                    if (fontFamily != null && !fontFamily.isEmpty()) {
                        newRun.setFontFamily(fontFamily);
                    }
                } catch (Exception e) {
                    // ignore
                }
                try {
                    if (color != null && !color.isEmpty()) {
                        newRun.setColor(color);
                    }
                } catch (Exception e) {
                    // ignore
                }

                paragraph.setAlignment(ParagraphAlignment.CENTER);
            }
        }

        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        String fullText = paragraph.getText();
                        if (fullText != null && fullText.contains(placeholder)) {
                            String newFullText = fullText.replace(placeholder, replacementValue);

                            String color = null;
                            boolean isBold = false;
                            int fontSize = 0;
                            String fontFamily = null;

                            for (XWPFRun run : paragraph.getRuns()) {
                                try {
                                    if (color == null || color.isEmpty()) {
                                        String runColor = run.getColor();
                                        if (runColor != null && !runColor.isEmpty()) {
                                            color = runColor;
                                        }
                                    }
                                } catch (Exception e) {
                                    // ignore
                                }
                                if (!isBold) {
                                    try {
                                        isBold = run.isBold();
                                    } catch (Exception e) {
                                        // ignore
                                    }
                                }
                                if (fontSize == 0) {
                                    try {
                                        fontSize = run.getFontSize();
                                    } catch (Exception e) {
                                        try {
                                            double fontSizeDouble = run.getFontSizeAsDouble();
                                            if (fontSizeDouble > 0) {
                                                fontSize = (int) fontSizeDouble;
                                            }
                                        } catch (Exception e2) {
                                            // ignore
                                        }
                                    }
                                }
                                if (fontFamily == null || fontFamily.isEmpty()) {
                                    try {
                                        fontFamily = run.getFontFamily();
                                    } catch (Exception e) {
                                        // ignore
                                    }
                                }
                            }

                            int runsCount = paragraph.getRuns().size();
                            for (int i = runsCount - 1; i >= 0; i--) {
                                paragraph.removeRun(i);
                            }

                            XWPFRun newRun = paragraph.createRun();
                            newRun.setText(newFullText);

                            try {
                                newRun.setBold(isBold);
                            } catch (Exception e) {
                                // ignore
                            }
                            try {
                                if (fontSize > 0) {
                                    newRun.setFontSize(fontSize);
                                }
                            } catch (Exception e) {
                                // ignore
                            }
                            try {
                                if (fontFamily != null && !fontFamily.isEmpty()) {
                                    newRun.setFontFamily(fontFamily);
                                }
                            } catch (Exception e) {
                                // ignore
                            }
                            try {
                                if (color != null && !color.isEmpty()) {
                                    newRun.setColor(color);
                                }
                            } catch (Exception e) {
                                // ignore
                            }

                            paragraph.setAlignment(ParagraphAlignment.CENTER);
                        }
                    }
                }
            }
        }
    }

    /**
     * Remplace les placeholders dans les TextBox et formes (cadres)
     */
    private static void replacePlaceholdersInTextBoxes(XWPFDocument document, String ecoleName, String placeholder) {
        try {
            // Parcourir tous les paragraphes
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                replaceInParagraphDrawings(paragraph, ecoleName, placeholder);
            }
            
            // Parcourir aussi les paragraphes dans les tableaux
            for (XWPFTable table : document.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            replaceInParagraphDrawings(paragraph, ecoleName, placeholder);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Remplace les placeholders dans les Drawing objects d'un paragraphe
     */
    private static void replaceInParagraphDrawings(XWPFParagraph paragraph, String ecoleName, String placeholder) {
        try {
            for (XWPFRun run : paragraph.getRuns()) {
                // Vérifier si le run contient un dessin (Drawing)
                CTDrawing[] drawings = run.getCTR().getDrawingArray();
                if (drawings != null && drawings.length > 0) {
                    for (CTDrawing drawing : drawings) {
                        replaceInDrawing(drawing, ecoleName, placeholder);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Remplace les placeholders dans un Drawing object (TextBox)
     */
    private static void replaceInDrawing(CTDrawing drawing, String ecoleName, String placeholder) {
        try {
            // Méthode 1: Modifier directement le XML en string (plus robuste)
            String xmlString = drawing.xmlText();
            if (xmlString != null && xmlString.contains(placeholder)) {
                String modifiedXml = xmlString.replace(placeholder, ecoleName);
                
                if (!modifiedXml.equals(xmlString)) {
                    try {
                        // Réappliquer le XML modifié
                        org.apache.xmlbeans.XmlObject newDrawing = org.apache.xmlbeans.XmlObject.Factory.parse(modifiedXml);
                        drawing.set(newDrawing);
                    } catch (Exception e) {
                        // Si set() échoue, essayer de modifier via XmlCursor
                        try {
                            XmlCursor cursor = drawing.newCursor();
                            replaceInXmlRecursive(cursor, ecoleName, placeholder);
                            cursor.dispose();
                        } catch (Exception e2) {
                            // Ignore si tout échoue
                        }
                    }
                }
            } else {
                // Si pas de placeholder dans XML string, essayer avec XmlCursor
                try {
                    XmlCursor cursor = drawing.newCursor();
                    replaceInXmlRecursive(cursor, ecoleName, placeholder);
                    cursor.dispose();
                } catch (Exception e) {
                    // Ignore
                }
            }
        } catch (Exception e) {
            // Ignore errors
        }
    }

    /**
     * Remplace récursivement dans le XML en utilisant XmlCursor
     */
    private static void replaceInXmlRecursive(XmlCursor cursor, String ecoleName, String placeholder) {
        try {
            cursor.toStartDoc();
            while (cursor.hasNextToken()) {
                org.apache.xmlbeans.XmlCursor.TokenType token = cursor.currentTokenType();
                if (token != null && token == org.apache.xmlbeans.XmlCursor.TokenType.TEXT) {
                    String text = cursor.getTextValue();
                    if (text != null && text.contains(placeholder)) {
                        String newText = text.replace(placeholder, ecoleName);
                        if (!newText.equals(text)) {
                            cursor.setTextValue(newText);
                        }
                    }
                }
                cursor.toNextToken();
            }
        } catch (Exception e) {
            // Ignore errors
        }
    }
}

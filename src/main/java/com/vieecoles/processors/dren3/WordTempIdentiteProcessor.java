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
        
        String placeholder = "{{NOM_ECOLE}}";
        
        // Remplacer dans les paragraphes
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            String fullText = paragraph.getText();
            if (fullText != null && fullText.contains(placeholder)) {
                // Remplacer le placeholder dans le texte complet
                String newFullText = fullText.replace(placeholder, ecoleName);
                
                // Sauvegarder le style du premier run (pour conserver la couleur verte)
                boolean isBold = false;
                boolean isItalic = false;
                int fontSize = 0;
                String fontFamily = null;
                String color = null;
                
                // Parcourir tous les runs pour trouver la couleur (peut être dans n'importe quel run)
                for (XWPFRun run : paragraph.getRuns()) {
                    try {
                        if (color == null || color.isEmpty()) {
                            String runColor = run.getColor();
                            if (runColor != null && !runColor.isEmpty()) {
                                color = runColor;
                            }
                        }
                    } catch (Exception e) {}
                    
                    // Prendre le style du premier run non vide
                    if (!isBold) {
                        try { isBold = run.isBold(); } catch (Exception e) {}
                    }
                    if (!isItalic) {
                        try { isItalic = run.isItalic(); } catch (Exception e) {}
                    }
                    if (fontSize == 0) {
                        try { fontSize = run.getFontSize(); } catch (Exception e) {
                            try { double fontSizeDouble = run.getFontSizeAsDouble(); if (fontSizeDouble > 0) { fontSize = (int)fontSizeDouble; } } catch (Exception e2) {}
                        }
                    }
                    if (fontFamily == null || fontFamily.isEmpty()) {
                        try { fontFamily = run.getFontFamily(); } catch (Exception e) {}
                    }
                }
                
                // Supprimer tous les runs existants
                int runsCount = paragraph.getRuns().size();
                for (int i = runsCount - 1; i >= 0; i--) {
                    paragraph.removeRun(i);
                }
                
                // Créer un nouveau run avec le texte modifié
                XWPFRun newRun = paragraph.createRun();
                newRun.setText(newFullText);
                
                // Restaurer le style (y compris la couleur verte)
                try { newRun.setBold(isBold); } catch (Exception e) {}
                try { newRun.setItalic(isItalic); } catch (Exception e) {}
                try { if (fontSize > 0) { newRun.setFontSize(fontSize); } } catch (Exception e) {}
                try { if (fontFamily != null && !fontFamily.isEmpty()) { newRun.setFontFamily(fontFamily); } } catch (Exception e) {}
                try { if (color != null && !color.isEmpty()) { newRun.setColor(color); } } catch (Exception e) {}
                
                // Forcer l'alignement CENTER
                paragraph.setAlignment(ParagraphAlignment.CENTER);
            }
        }
        
        // Remplacer aussi dans les tableaux
        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        String fullText = paragraph.getText();
                        if (fullText != null && fullText.contains(placeholder)) {
                            String newFullText = fullText.replace(placeholder, ecoleName);
                            
                            // Sauvegarder le style (couleur, etc.)
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
                                } catch (Exception e) {}
                                if (!isBold) {
                                    try { isBold = run.isBold(); } catch (Exception e) {}
                                }
                                if (fontSize == 0) {
                                    try { fontSize = run.getFontSize(); } catch (Exception e) {
                                        try { double fontSizeDouble = run.getFontSizeAsDouble(); if (fontSizeDouble > 0) { fontSize = (int)fontSizeDouble; } } catch (Exception e2) {}
                                    }
                                }
                                if (fontFamily == null || fontFamily.isEmpty()) {
                                    try { fontFamily = run.getFontFamily(); } catch (Exception e) {}
                                }
                            }
                            
                            // Supprimer tous les runs existants
                            int runsCount = paragraph.getRuns().size();
                            for (int i = runsCount - 1; i >= 0; i--) {
                                paragraph.removeRun(i);
                            }
                            
                            // Créer un nouveau run avec le texte modifié
                            XWPFRun newRun = paragraph.createRun();
                            newRun.setText(newFullText);
                            
                            // Restaurer le style
                            try { newRun.setBold(isBold); } catch (Exception e) {}
                            try { if (fontSize > 0) { newRun.setFontSize(fontSize); } } catch (Exception e) {}
                            try { if (fontFamily != null && !fontFamily.isEmpty()) { newRun.setFontFamily(fontFamily); } } catch (Exception e) {}
                            try { if (color != null && !color.isEmpty()) { newRun.setColor(color); } } catch (Exception e) {}
                            
                            // Forcer l'alignement CENTER
                            paragraph.setAlignment(ParagraphAlignment.CENTER);
                        }
                    }
                }
            }
        }
        
        // Remplacer dans les TextBox et formes (cadres)
        replacePlaceholdersInTextBoxes(document, ecoleName, placeholder);
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

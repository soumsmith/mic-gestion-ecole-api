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
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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
        
        // Formater l'année scolaire (ex: "2023 - 2024")
        String anneeScolaire = libelleAnneeNew;
        // Si le libellé contient déjà " - ", on le garde tel quel
        if (libelleAnneeNew.contains(" - ")) {
            anneeScolaire = libelleAnneeNew;
        } else if (libelleAnneeNew.matches("\\d{4}")) {
            // Si c'est juste une année (ex: "2023"), créer le format "2023 - 2024"
            int annee = Integer.parseInt(libelleAnneeNew);
            anneeScolaire = annee + " - " + (annee + 1);
        } else if (libelleAnneeNew.matches("\\d{4}-\\d{4}")) {
            // Si c'est au format "2023-2024", remplacer le tiret par " - "
            anneeScolaire = libelleAnneeNew.replace("-", " - ");
        }
        
        // Remplacer {{DENOMINATION}}, {{TRIMESTRE}} et {{ANNEE_SCOLAIRE}} dans tous les paragraphes
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            // Récupérer tout le texte du paragraphe pour gérer les placeholders répartis sur plusieurs runs
            String fullText = paragraph.getText();
            if (fullText != null && (fullText.contains("{{") || fullText.contains("202..."))) {
                String newFullText = fullText;
                boolean paragraphModified = false;
                
                // Remplacer tous les placeholders
                if (newFullText.contains("{{DENOMINATION}}")) {
                    newFullText = newFullText.replace("{{DENOMINATION}}", ecoleName);
                    paragraphModified = true;
                }
                if (newFullText.contains("{{TRIMESTRE}}")) {
                    newFullText = newFullText.replace("{{TRIMESTRE}}", libelleTrimestre != null ? libelleTrimestre.toUpperCase() : "");
                    paragraphModified = true;
                }
                if (newFullText.contains("{{ANNEE_SCOLAIRE}}")) {
                    newFullText = newFullText.replace("{{ANNEE_SCOLAIRE}}", anneeScolaire);
                    paragraphModified = true;
                }
                if (newFullText.contains("202... - 202...")) {
                    newFullText = newFullText.replace("202... - 202...", anneeScolaire);
                    paragraphModified = true;
                }
                if (newFullText.contains("202… - 202…")) {
                    newFullText = newFullText.replace("202… - 202…", anneeScolaire);
                    paragraphModified = true;
                }
                
                // Si le paragraphe a été modifié, remplacer le texte en préservant le style
                if (paragraphModified) {
                    // Préserver l'alignement du paragraphe
                    ParagraphAlignment alignment = paragraph.getAlignment();
                    
                    // Préserver le formatage du premier run AVANT de supprimer les runs
                    boolean isBold = false;
                    boolean isItalic = false;
                    UnderlinePatterns underline = null;
                    int fontSize = 0;
                    String fontFamily = null;
                    String color = null;
                    
                    if (!paragraph.getRuns().isEmpty()) {
                        XWPFRun firstRun = paragraph.getRuns().get(0);
                        try {
                            isBold = firstRun.isBold();
                        } catch (Exception e) {
                            // Ignorer
                        }
                        try {
                            isItalic = firstRun.isItalic();
                        } catch (Exception e) {
                            // Ignorer
                        }
                        try {
                            underline = firstRun.getUnderline();
                        } catch (Exception e) {
                            // Ignorer
                        }
                        try {
                            fontSize = firstRun.getFontSize();
                        } catch (Exception e) {
                            try {
                                double fontSizeDouble = firstRun.getFontSizeAsDouble();
                                if (fontSizeDouble > 0) {
                                    fontSize = (int)fontSizeDouble;
                                }
                            } catch (Exception e2) {
                                // Ignorer
                            }
                        }
                        try {
                            fontFamily = firstRun.getFontFamily();
                        } catch (Exception e) {
                            // Ignorer
                        }
                        try {
                            color = firstRun.getColor();
                        } catch (Exception e) {
                            // Ignorer
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
                    
                    // Restaurer le formatage sauvegardé
                    try {
                        newRun.setBold(isBold);
                    } catch (Exception e) {
                        // Ignorer
                    }
                    try {
                        newRun.setItalic(isItalic);
                    } catch (Exception e) {
                        // Ignorer
                    }
                    try {
                        if (underline != null) {
                            newRun.setUnderline(underline);
                        }
                    } catch (Exception e) {
                        // Ignorer
                    }
                    try {
                        if (fontSize > 0) {
                            newRun.setFontSize(fontSize);
                        }
                    } catch (Exception e) {
                        // Ignorer
                    }
                    try {
                        if (fontFamily != null && !fontFamily.isEmpty()) {
                            newRun.setFontFamily(fontFamily);
                        }
                    } catch (Exception e) {
                        // Ignorer
                    }
                    try {
                        if (color != null && !color.isEmpty()) {
                            newRun.setColor(color);
                        }
                    } catch (Exception e) {
                        // Ignorer
                    }
                    
                    // Restaurer l'alignement du paragraphe
                    if (alignment != null) {
                        paragraph.setAlignment(alignment);
                    }
                } else {
                    // Sinon, remplacer run par run (méthode originale) pour préserver le style
                    for (XWPFRun run : paragraph.getRuns()) {
                        String text = run.getText(0);
                        if (text != null) {
                            boolean modified = false;
                            if (text.contains("{{DENOMINATION}}")) {
                                text = text.replace("{{DENOMINATION}}", ecoleName);
                                modified = true;
                            }
                            if (text.contains("{{TRIMESTRE}}")) {
                                text = text.replace("{{TRIMESTRE}}", libelleTrimestre != null ? libelleTrimestre : "");
                                modified = true;
                            }
                            if (text.contains("{{ANNEE_SCOLAIRE}}")) {
                                text = text.replace("{{ANNEE_SCOLAIRE}}", anneeScolaire);
                                modified = true;
                            }
                            if (text.contains("202... - 202...") || text.contains("202… - 202…")) {
                                text = text.replace("202... - 202...", anneeScolaire);
                                text = text.replace("202… - 202…", anneeScolaire);
                                modified = true;
                            }
                            if (modified) {
                                run.setText(text, 0);
                            }
                        }
                    }
                }
            }
        }
        
        // Remplacer aussi dans les tableaux
        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        for (XWPFRun run : paragraph.getRuns()) {
                            String text = run.getText(0);
                            if (text != null) {
                                boolean modified = false;
                                if (text.contains("{{DENOMINATION}}")) {
                                    text = text.replace("{{DENOMINATION}}", ecoleName);
                                    modified = true;
                                }
                                if (text.contains("{{TRIMESTRE}}")) {
                                    text = text.replace("{{TRIMESTRE}}", libelleTrimestre != null ? libelleTrimestre : "");
                                    modified = true;
                                }
                                if (text.contains("{{ANNEE_SCOLAIRE}}")) {
                                    text = text.replace("{{ANNEE_SCOLAIRE}}", anneeScolaire);
                                    modified = true;
                                }
                                if (text.contains("202... - 202...") || text.contains("202… - 202…")) {
                                    text = text.replace("202... - 202...", anneeScolaire);
                                    text = text.replace("202… - 202…", anneeScolaire);
                                    modified = true;
                                }
                                if (modified) {
                                    run.setText(text, 0);
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // Remplacer dans les TextBox et formes (cadres)
        replacePlaceholdersInTextBoxes(document, ecoleName, libelleTrimestre, anneeScolaire);

    }

    /**
     * Remplace les placeholders dans les TextBox et formes (cadres)
     */
    private static void replacePlaceholdersInTextBoxes(XWPFDocument document, String ecoleName, String libelleTrimestre, String anneeScolaire) {
        try {
            // Parcourir tous les paragraphes
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                replaceInParagraphDrawings(paragraph, ecoleName, libelleTrimestre, anneeScolaire);
            }
            
            // Parcourir aussi les paragraphes dans les tableaux
            for (XWPFTable table : document.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            replaceInParagraphDrawings(paragraph, ecoleName, libelleTrimestre, anneeScolaire);
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
    private static void replaceInParagraphDrawings(XWPFParagraph paragraph, String ecoleName, String libelleTrimestre, String anneeScolaire) {
        try {
            for (XWPFRun run : paragraph.getRuns()) {
                // Vérifier si le run contient un dessin (Drawing)
                CTDrawing[] drawings = run.getCTR().getDrawingArray();
                if (drawings != null && drawings.length > 0) {
                    for (CTDrawing drawing : drawings) {
                        replaceInDrawing(drawing, ecoleName, libelleTrimestre, anneeScolaire);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Remplace les placeholders dans un Drawing object (TextBox)
     * Utilise une approche sans XPath pour éviter les conflits de versions
     */
    private static void replaceInDrawing(CTDrawing drawing, String ecoleName, String libelleTrimestre, String anneeScolaire) {
        try {
            // Méthode 1: Modifier directement le XML en string (plus robuste et prioritaire)
            String xmlString = drawing.xmlText();
            if (xmlString != null && xmlString.contains("{{")) {
                String modifiedXml = xmlString;
                boolean modified = false;
                
                if (modifiedXml.contains("{{DENOMINATION}}")) {
                    modifiedXml = modifiedXml.replace("{{DENOMINATION}}", ecoleName);
                    modified = true;
                }
                if (modifiedXml.contains("{{TRIMESTRE}}")) {
                    modifiedXml = modifiedXml.replace("{{TRIMESTRE}}", libelleTrimestre != null ? libelleTrimestre : "");
                    modified = true;
                }
                if (modifiedXml.contains("{{ANNEE_SCOLAIRE}}")) {
                    modifiedXml = modifiedXml.replace("{{ANNEE_SCOLAIRE}}", anneeScolaire);
                    modified = true;
                }
                if (modifiedXml.contains("202... - 202...")) {
                    modifiedXml = modifiedXml.replace("202... - 202...", anneeScolaire);
                    modified = true;
                }
                if (modifiedXml.contains("202… - 202…")) {
                    modifiedXml = modifiedXml.replace("202… - 202…", anneeScolaire);
                    modified = true;
                }
                
                if (modified && !modifiedXml.equals(xmlString)) {
                    try {
                        // Réappliquer le XML modifié en créant un nouvel objet
                        org.apache.xmlbeans.XmlObject newDrawing = org.apache.xmlbeans.XmlObject.Factory.parse(modifiedXml);
                        // Copier le contenu modifié dans le drawing existant
                        drawing.set(newDrawing);
                    } catch (Exception e) {
                        // Si set() échoue, essayer de modifier via XmlCursor
                        try {
                            XmlCursor cursor = drawing.newCursor();
                            replaceInXmlRecursive(cursor, ecoleName, libelleTrimestre, anneeScolaire);
                            cursor.dispose();
                        } catch (Exception e2) {
                            // Ignorer si tout échoue
                        }
                    }
                }
            } else {
                // Si pas de placeholder dans le XML string, essayer quand même avec XmlCursor
                try {
                    XmlCursor cursor = drawing.newCursor();
                    replaceInXmlRecursive(cursor, ecoleName, libelleTrimestre, anneeScolaire);
                    cursor.dispose();
                } catch (Exception e) {
                    // Ignorer
                }
            }
        } catch (Exception e) {
            // Ignorer les erreurs pour ne pas bloquer le traitement
        }
    }
    
    /**
     * Parcourt récursivement le XML sans utiliser XPath
     */
    private static void replaceInXmlRecursive(XmlCursor cursor, String ecoleName, String libelleTrimestre, String anneeScolaire) {
        try {
            cursor.toStartDoc();
            
            // Parcourir tous les nœuds en utilisant hasNextToken
            while (cursor.hasNextToken()) {
                org.apache.xmlbeans.XmlCursor.TokenType token = cursor.currentTokenType();
                
                // Si c'est un nœud texte, vérifier et remplacer
                if (token != null && token == org.apache.xmlbeans.XmlCursor.TokenType.TEXT) {
                    String text = cursor.getTextValue();
                    if (text != null && (text.contains("{{") || text.contains("202..."))) {
                        String newText = text;
                        if (newText.contains("{{DENOMINATION}}")) {
                            newText = newText.replace("{{DENOMINATION}}", ecoleName);
                        }
                        if (newText.contains("{{TRIMESTRE}}")) {
                            newText = newText.replace("{{TRIMESTRE}}", libelleTrimestre != null ? libelleTrimestre : "");
                        }
                        if (newText.contains("{{ANNEE_SCOLAIRE}}")) {
                            newText = newText.replace("{{ANNEE_SCOLAIRE}}", anneeScolaire);
                        }
                        if (newText.contains("202... - 202...")) {
                            newText = newText.replace("202... - 202...", anneeScolaire);
                        }
                        if (newText.contains("202… - 202…")) {
                            newText = newText.replace("202… - 202…", anneeScolaire);
                        }
                        if (!newText.equals(text)) {
                            cursor.setTextValue(newText);
                        }
                    }
                }
                
                // Passer au nœud suivant
                cursor.toNextToken();
            }
        } catch (Exception e) {
            // Ignorer les erreurs pour ne pas bloquer le traitement
        }
    }
    
    /**
     * Remplace les placeholders dans un objet XML texte
     */
    private static void replaceTextInXmlObject(XmlCursor cursor, String ecoleName, String libelleTrimestre, String anneeScolaire) {
        try {
            XmlObject obj = cursor.getObject();
            String text = obj.xmlText();
            if (text != null && (text.contains("{{DENOMINATION}}") || text.contains("{{TRIMESTRE}}") ||
                text.contains("{{ANNEE_SCOLAIRE}}") || text.contains("202... - 202...") ||
                text.contains("DENOMINATION") || text.contains("TRIMESTRE"))) {
                String newText = text;
                if (newText.contains("{{DENOMINATION}}")) {
                    newText = newText.replace("{{DENOMINATION}}", ecoleName);
                }
                if (newText.contains("{{TRIMESTRE}}")) {
                    newText = newText.replace("{{TRIMESTRE}}", libelleTrimestre != null ? libelleTrimestre : "");
                }
                if (newText.contains("{{ANNEE_SCOLAIRE}}")) {
                    newText = newText.replace("{{ANNEE_SCOLAIRE}}", anneeScolaire);
                }
                if (newText.contains("202... - 202...") || newText.contains("202… - 202…")) {
                    newText = newText.replace("202... - 202...", anneeScolaire);
                    newText = newText.replace("202… - 202…", anneeScolaire);
                }
                if (newText.contains("DENOMINATION") && !newText.contains("{{DENOMINATION}}")) {
                    newText = newText.replace("DENOMINATION", ecoleName);
                }
                if (newText.contains("TRIMESTRE") && !newText.contains("{{TRIMESTRE}}")) {
                    newText = newText.replace("TRIMESTRE", libelleTrimestre != null ? libelleTrimestre : "");
                }
                if (!newText.equals(text)) {
                    cursor.setTextValue(newText);
                }
            }
        } catch (Exception e) {
            // Ignorer les erreurs de modification
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

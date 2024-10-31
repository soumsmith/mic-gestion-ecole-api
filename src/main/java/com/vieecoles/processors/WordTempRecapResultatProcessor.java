package com.vieecoles.processors;

import com.vieecoles.dto.RecapDesResultatsElevesAffecteDto;
import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;
import com.vieecoles.services.etats.appachePoi.resultatsPoiServices;
import com.vieecoles.services.etats.resultatsRecapServices;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class WordTempRecapResultatProcessor {
    @Inject
    resultatsPoiServices resultatsServices ;
    @Inject
    resultatsRecapServices resultatsRecapServices ;



    // Méthode pour remplacer les placeholders dans une ligne de tableau
    private static void replacePlaceholdersInTableRow(XWPFTableRow row, Map<String, String> dataMap) {
        for (XWPFTableCell cell : row.getTableCells()) {
            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                replacePlaceholdersInParagraph(paragraph, dataMap);
            }
        }
    }
    // Méthode pour remplacer les placeholders dans un paragraphe
    private static void replacePlaceholdersInParagraph(XWPFParagraph paragraph, Map<String, String> dataMap) {
        StringBuilder fullText = new StringBuilder();
        List<XWPFRun> runs = paragraph.getRuns();

        // Reconstituer le texte complet du paragraphe à partir des runs
        for (XWPFRun run : runs) {
            String text = run.getText(0);
            if (text != null) {
                fullText.append(text);
            }
        }

        String paragraphText = fullText.toString();

        // Remplacer les placeholders dans le texte complet du paragraphe
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            paragraphText = paragraphText.replace(entry.getKey(), entry.getValue());
        }

        // Si le texte du paragraphe a été modifié, mettre à jour les runs existants
        if (!fullText.toString().equals(paragraphText)) {
            int runIndex = 0;
            for (XWPFRun run : runs) {
                String runText = run.getText(0);
                if (runText != null) {
                    // Découper le texte modifié en morceaux correspondant à la taille des runs originaux
                    String newText = paragraphText.substring(0, Math.min(runText.length(), paragraphText.length()));
                    run.setText(newText, 0);
                    paragraphText = paragraphText.substring(newText.length());
                }
                runIndex++;
            }

            // Si des caractères restent après avoir modifié tous les runs existants, les ajouter à un nouveau run
            if (!paragraphText.isEmpty()) {
                XWPFRun newRun = paragraph.createRun();
                newRun.setText(paragraphText);
            }
        }
    }


    private static void ensureCellCount(XWPFTableRow row, int cellCount) {
        int currentCellCount = row.getTableCells().size();
        for (int i = currentCellCount; i < cellCount; i++) {
            row.addNewTableCell(); // Ajouter une nouvelle cellule si nécessaire
        }
    }

    public   void recapResultatAffecte(List<RecapDesResultatsElevesAffecteDto> detailsBull, XWPFTable table) {
        for (RecapDesResultatsElevesAffecteDto classe : detailsBull) {
            long nombMoySup10 =
                    (classe.getNbreMoySup10F() != null ? classe.getNbreMoySup10F() : 0L) +
                            (classe.getNbreMoySup10G() != null ? classe.getNbreMoySup10G() : 0L);

            long nombMoyInf10 =
                    (classe.getNbreMoyInf999F() != null ? classe.getNbreMoyInf999F() : 0L) +
                            (classe.getNbreMoyInf999G() != null ? classe.getNbreMoyInf999G() : 0L);

            long nombMoyInf8_5 =
                    (classe.getNbreMoyInf85F() != null ? classe.getNbreMoyInf85F() : 0L) +
                            (classe.getNbreMoyInf85G() != null ? classe.getNbreMoyInf85G() : 0L);

            long effectifClasse =
                    (classe.getClassF() != null ? classe.getClassF() : 0L) +
                            (classe.getClassG() != null ? classe.getClassG() : 0L);

// Éviter une division par zéro pour effectifClasse
            double pourSup10,pourInf10,pourInf8_5;
            if (effectifClasse > 0) {
                pourSup10 = nombMoySup10 / (Double.valueOf(effectifClasse)) * 100d;
                pourInf10 = nombMoyInf10 / (Double.valueOf(effectifClasse)) * 100d;
                pourInf8_5 = nombMoyInf8_5 / (Double.valueOf(effectifClasse)) * 100d;

                // Calculer d'autres statistiques ou utiliser ces valeurs
            } else {
                // Gérer le cas où effectifClasse est 0 pour éviter une division par zéro
                pourSup10 = 0d;
                pourInf10 = 0d;
                pourInf8_5 = 0d;
                // Logique alternative ou gestion d'erreur
            }

            long effectif =
                    (classe.getEffeF() != null ? classe.getEffeF() : 0L) +
                            (classe.getEffeG() != null ? classe.getEffeG() : 0L);

            long effNonClass =
                    (classe.getNonclassF() != null ? classe.getNonclassF() : 0L) +
                            (classe.getNonclassG() != null ? classe.getNonclassG() : 0L);

            // Créer une ligne principale pour la classe (6ème 1, 6ème 2, etc.)
            //XWPFTableRow classRow = table.createRow();
            // ensureCellCount(classRow, 14); // 14 colonnes comme dans le modèle

        // Remplir la ligne pour les filles (F)
            XWPFTableRow fillesRow = table.createRow();
            ensureCellCount(fillesRow, 12);
            fillesRow.getCell(0).setText(classe.getNiveau());
            fillesRow.getCell(1).setText(" F");
            fillesRow.getCell(2).setText(String.valueOf(classe.getEffeF()));
            fillesRow.getCell(3).setText(String.valueOf(classe.getClassF()));
            fillesRow.getCell(4).setText(String.valueOf(classe.getNonclassF()));
            fillesRow.getCell(5).setText(String.valueOf(classe.getNbreMoySup10F()));
            fillesRow.getCell(6).setText(String.valueOf(arrondie(classe.getPourMoySup10F())));
            fillesRow.getCell(7).setText(String.valueOf(classe.getNbreMoyInf999F()));
            fillesRow.getCell(8).setText(String.valueOf(arrondie(classe.getPourMoyInf999F())));
            fillesRow.getCell(9).setText(String.valueOf(classe.getNbreMoyInf85F()));
            fillesRow.getCell(10).setText(String.valueOf(arrondie(classe.getPourMoyInf85F())));
            fillesRow.getCell(11).setText(String.valueOf(arrondie(classe.getMoyClasseF())));

// Remplir la ligne pour les garçons (G)
            XWPFTableRow garconsRow = table.createRow();
            ensureCellCount(garconsRow, 12);
            garconsRow.getCell(1).setText(" G");
            garconsRow.getCell(2).setText(String.valueOf(classe.getEffeG()));
            garconsRow.getCell(3).setText(String.valueOf(classe.getClassG()));
            garconsRow.getCell(4).setText(String.valueOf(classe.getNonclassG()));
            garconsRow.getCell(5).setText(String.valueOf(classe.getNbreMoySup10G()));
            garconsRow.getCell(6).setText(String.valueOf(arrondie(classe.getPourMoySup10G())));
            garconsRow.getCell(7).setText(String.valueOf(classe.getNbreMoyInf999G()));
            garconsRow.getCell(8).setText(String.valueOf(arrondie(classe.getPourMoyInf999G())));
            garconsRow.getCell(9).setText(String.valueOf(classe.getNbreMoyInf85G()));
            garconsRow.getCell(10).setText(String.valueOf(arrondie(classe.getPourMoyInf85G())));
            garconsRow.getCell(11).setText(String.valueOf(arrondie(classe.getMoyClasseG())));

// Remplir la ligne pour le total (T)
            XWPFTableRow totalRow = table.createRow();
            ensureCellCount(totalRow, 12);
            totalRow.getCell(1).setText(" T");
            totalRow.getCell(2).setText(String.valueOf(effectif));
            totalRow.getCell(3).setText(String.valueOf(effectifClasse));
            totalRow.getCell(4).setText(String.valueOf(effNonClass));
            totalRow.getCell(5).setText(String.valueOf(nombMoySup10));
            totalRow.getCell(6).setText(String.valueOf(arrondie(pourSup10)));
            totalRow.getCell(7).setText(String.valueOf(nombMoyInf10));
            totalRow.getCell(8).setText(String.valueOf(arrondie(pourInf10)));
            totalRow.getCell(9).setText(String.valueOf(nombMoyInf8_5));
            totalRow.getCell(10).setText(String.valueOf(arrondie(pourInf8_5)));
            totalRow.getCell(11).setText(String.valueOf(arrondie(classe.getMoyClasse())));
            mergeCellsVertically(table, 0, table.getNumberOfRows() - 3, table.getNumberOfRows() -1);
        }
        // Remplir la ligne pour les Total Etablissement
        XWPFTableRow etabliRow = table.createRow();
        ensureCellCount(etabliRow, 12);
        etabliRow.getCell(0).setText("Total Etabli");
        etabliRow.getCell(1).setText(" F");
        etabliRow.getCell(2).setText(String.valueOf(""));
        etabliRow.getCell(3).setText(String.valueOf(""));
        etabliRow.getCell(4).setText(String.valueOf(""));
        etabliRow.getCell(5).setText(String.valueOf(""));
        etabliRow.getCell(6).setText(String.valueOf(arrondie(0d)));
        etabliRow.getCell(7).setText(String.valueOf(""));
        etabliRow.getCell(8).setText(String.valueOf(arrondie(0d)));
        etabliRow.getCell(9).setText(String.valueOf(""));
        etabliRow.getCell(10).setText(String.valueOf(arrondie(0d)));
        etabliRow.getCell(11).setText(String.valueOf(arrondie(0d)));
    }





    private static void resultatsElevesAffectes(List<ResultatsElevesAffecteDto> resultatsBull,XWPFTable table){
        for (int i = 1; i < table.getRows().size(); i++) {
            XWPFTableRow row = table.getRow(i);
            Map<String, String> rowData = new HashMap<>();

            rowData.put("{{NOMBRE_MOY_SUP_10_6E}}", "SOUMAHORO Moustapha");
            rowData.put("{{NOMBRE_MOY_INF_10_6E}}", "0358 pm 455");
            rowData.put("{{NOMBRE_MOY_INF_08_5_6E}}", "0345897564122");
            rowData.put("{{POURC_MOY_SUP_10_6E}}", "0345897564122");
            rowData.put("{{POURC_MOY_INF_10_6E}}", "0345897564122");
            rowData.put("{{POURC_MOY_INF_08_5_6E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_6E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_CLASSES_6E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_NON_CLASSES_6E}}", "0345897564122");
            //
            rowData.put("{{NOMBRE_MOY_SUP_10_5E}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_INF_10_5E}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_INF_08_5_5E}}", "0345897564122");
            rowData.put("{{POURC_MOY_SUP_10_5E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_5E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_CLASSES_5E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_NON_CLASSES_5E}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_SUP_10_4E}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_INF_10_4E}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_INF_08_5_4E}}", "0345897564122");
            rowData.put("{{POURC_MOY_SUP_10_4E}}", "0345897564122");
            rowData.put("{{POURC_MOY_INF_10_4E}}", "0345897564122");
            rowData.put("{{POURC_MOY_INF_08_5_4E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_4E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_CLASSES_4E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_NON_CLASSES_4E}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_SUP_10_3E}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_INF_10_3E}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_INF_08_5_3E}}", "0345897564122");
            rowData.put("{{POURC_MOY_SUP_10_3E}}", "0345897564122");
            rowData.put("{{POURC_MOY_INF_10_3E}}", "0345897564122");
            rowData.put("{{POURC_MOY_INF_08_5_3E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_3E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_CLASSES_3E}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_SUP_10_CLASSE}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_INF_10_CLASSE}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_INF_08_5_CLASSE}}", "0345897564122");
            rowData.put("{{POURC_MOY_SUP_10_CLASSE}}", "0345897564122");
            rowData.put("{{POURC_MOY_INF_10_CLASSE}}", "0345897564122");
            rowData.put("{{POURC_MOY_INF_08_5_CLASSE}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_CLASSE}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_CLASSES_CLASSE}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_NON_CLASSES_CLASSE}}", "0345897564122");
            replacePlaceholdersInTableRow(row, rowData);
        }


    }

    private static void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            if (rowIndex == fromRow) {
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    public static double arrondie(double d) {
        return Double.parseDouble(String.format("%.2f", d));
    }
}

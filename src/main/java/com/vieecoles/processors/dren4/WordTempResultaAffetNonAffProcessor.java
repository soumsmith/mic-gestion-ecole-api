package com.vieecoles.processors.dren4;

import com.vieecoles.dto.MajorParClasseNiveauDto;
import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.processors.dren4.Services.resultatsPoiServices;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import java.math.BigInteger;

import java.util.stream.Collectors;

@ApplicationScoped
public class WordTempResultaAffetNonAffProcessor {
    @Inject
    resultatsPoiServices resultatsServices;

    public void getResultatAffProcessor(XWPFDocument document,
                                        Long idEcole, String libelleAnnee, String libelleTrimetre) {
        List<ResultatsElevesAffecteDto> detailsBull6 = new ArrayList<>();
        System.out.println("classeNiveauDtoList entree");
        try {
            detailsBull6 = resultatsServices.CalculResultatsEleveAffecte(idEcole, libelleAnnee, libelleTrimetre);

            System.out.println("classeNiveauDtoList Sortie");
        } catch (Exception e) {
            e.printStackTrace();
        }

        XWPFTable table = obtenirUnTableau(document, "CODE_STAT_RESULTATS");
        try {
            detailsBull6.sort(Comparator.comparing(ResultatsElevesAffecteDto::getOrdre_niveau));
            ajoutTableauDynamiqueAmeliore(detailsBull6, table);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public XWPFTable obtenirUnTableau(XWPFDocument document, String tableTitle) {
        XWPFTable targetTable = null;
        for (int i = 0; i < document.getBodyElements().size(); i++) {
            IBodyElement element = document.getBodyElements().get(i);
            if (element.getElementType() == BodyElementType.PARAGRAPH) {
                XWPFParagraph paragraph = (XWPFParagraph) element;
                if (paragraph.getText().trim().equalsIgnoreCase(tableTitle)) {
                    paragraph.removeRun(0);

                    for (int j = i + 1; j < document.getBodyElements().size(); j++) {
                        IBodyElement nextElement = document.getBodyElements().get(j);
                        if (nextElement.getElementType() == BodyElementType.TABLE) {
                            targetTable = (XWPFTable) nextElement;
                            break;
                        }
                    }
                    break;
                }
            }
        }
        return targetTable;
    }

    private static void ajoutTableauDynamiqueAmeliore(List<ResultatsElevesAffecteDto> detailsBull, XWPFTable table) {
        // Grouper les données par niveau (6ème, 5ème, 4ème, 3ème)
        Map<String, List<ResultatsElevesAffecteDto>> dataByLevel = grouperParNiveau(detailsBull);

        int rowIndex = 1; // Commencer après l'en-tête

        for (Map.Entry<String, List<ResultatsElevesAffecteDto>> entry : dataByLevel.entrySet()) {
            String niveau = entry.getKey();
            List<ResultatsElevesAffecteDto> classesNiveau = entry.getValue();

            // Variables pour calculer les totaux du niveau
            long totalEffectif = 0, totalEffectifClasse = 0, totalEffNonClass = 0;
            long totalNombMoySup10 = 0, totalNombMoyInf10 = 0, totalNombMoyInf8_5 = 0;
            double sommeMoyClasse = 0;

            // Ajouter chaque classe du niveau
            for (int i = 0; i < classesNiveau.size(); i++) {
                ResultatsElevesAffecteDto classe = classesNiveau.get(i);

                // Calculer les valeurs pour cette classe
                long nombMoySup10 = (classe.getNbreMoySup10F() != null ? classe.getNbreMoySup10F() : 0L) +
                    (classe.getNbreMoySup10G() != null ? classe.getNbreMoySup10G() : 0L);

                long nombMoyInf10 = (classe.getNbreMoyInf999F() != null ? classe.getNbreMoyInf999F() : 0L) +
                    (classe.getNbreMoyInf999G() != null ? classe.getNbreMoyInf999G() : 0L);

                long nombMoyInf8_5 = (classe.getNbreMoyInf85F() != null ? classe.getNbreMoyInf85F() : 0L) +
                    (classe.getNbreMoyInf85G() != null ? classe.getNbreMoyInf85G() : 0L);

                long effectifClasse = (classe.getClassF() != null ? classe.getClassF() : 0L) +
                    (classe.getClassG() != null ? classe.getClassG() : 0L);

                long effectif = (classe.getEffeF() != null ? classe.getEffeF() : 0L) +
                    (classe.getEffeG() != null ? classe.getEffeG() : 0L);

                long effNonClass = (classe.getNonclassF() != null ? classe.getNonclassF() : 0L) +
                    (classe.getNonclassG() != null ? classe.getNonclassG() : 0L);

                // Calculer les pourcentages
                double pourSup10 = effectifClasse > 0 ? (nombMoySup10 * 100.0) / effectifClasse : 0.0;
                double pourInf10 = effectifClasse > 0 ? (nombMoyInf10 * 100.0) / effectifClasse : 0.0;
                double pourInf8_5 = effectifClasse > 0 ? (nombMoyInf8_5 * 100.0) / effectifClasse : 0.0;

                // Créer une nouvelle ligne pour cette classe
                XWPFTableRow row = table.createRow();
                ensureCellCount(row, 11);

                // Remplir les données de la classe
                String nomClasse = niveau + " " + (i + 1); // Ex: "6ème 1", "6ème 2"
                row.getCell(0).setText(nomClasse);
                row.getCell(1).setText(String.valueOf(effectif));
                row.getCell(2).setText(String.valueOf(effectifClasse));
                row.getCell(3).setText(String.valueOf(effNonClass));
                row.getCell(4).setText(String.valueOf(nombMoySup10));
                row.getCell(5).setText(String.valueOf(arrondie(pourSup10)));
                row.getCell(6).setText(String.valueOf(nombMoyInf10));
                row.getCell(7).setText(String.valueOf(arrondie(pourInf10)));
                row.getCell(8).setText(String.valueOf(nombMoyInf8_5));
                row.getCell(9).setText(String.valueOf(arrondie(pourInf8_5)));
                row.getCell(10).setText(String.valueOf(arrondie(classe.getMoyClasse())));

                // Ajouter les bordures
                ajouterBordures(row);

                // Accumuler pour les totaux
                totalEffectif += effectif;
                totalEffectifClasse += effectifClasse;
                totalEffNonClass += effNonClass;
                totalNombMoySup10 += nombMoySup10;
                totalNombMoyInf10 += nombMoyInf10;
                totalNombMoyInf8_5 += nombMoyInf8_5;
                sommeMoyClasse += classe.getMoyClasse();

                rowIndex++;
            }

            // Ajouter la ligne de total pour ce niveau
            if (!classesNiveau.isEmpty()) {
                XWPFTableRow totalRow = table.createRow();
                ensureCellCount(totalRow, 11);

                // Calculer les pourcentages totaux
                double totalPourSup10 = totalEffectifClasse > 0 ? (totalNombMoySup10 * 100.0) / totalEffectifClasse : 0.0;
                double totalPourInf10 = totalEffectifClasse > 0 ? (totalNombMoyInf10 * 100.0) / totalEffectifClasse : 0.0;
                double totalPourInf8_5 = totalEffectifClasse > 0 ? (totalNombMoyInf8_5 * 100.0) / totalEffectifClasse : 0.0;
                double moyenneNiveau = classesNiveau.size() > 0 ? sommeMoyClasse / classesNiveau.size() : 0.0;

                // Remplir la ligne de total
                totalRow.getCell(0).setText("Total " + niveau);
                totalRow.getCell(1).setText(String.valueOf(totalEffectif));
                totalRow.getCell(2).setText(String.valueOf(totalEffectifClasse));
                totalRow.getCell(3).setText(String.valueOf(totalEffNonClass));
                totalRow.getCell(4).setText(String.valueOf(totalNombMoySup10));
                totalRow.getCell(5).setText(String.valueOf(arrondie(totalPourSup10)));
                totalRow.getCell(6).setText(String.valueOf(totalNombMoyInf10));
                totalRow.getCell(7).setText(String.valueOf(arrondie(totalPourInf10)));
                totalRow.getCell(8).setText(String.valueOf(totalNombMoyInf8_5));
                totalRow.getCell(9).setText(String.valueOf(arrondie(totalPourInf8_5)));
                totalRow.getCell(10).setText(String.valueOf(arrondie(moyenneNiveau)));

                // Mettre en gras la ligne de total et ajouter une couleur de fond
                mettreEnGrasEtColorer(totalRow);
                ajouterBordures(totalRow);

                rowIndex++;
            }
        }
    }

    private static Map<String, List<ResultatsElevesAffecteDto>> grouperParNiveau(List<ResultatsElevesAffecteDto> detailsBull) {
        Map<String, List<ResultatsElevesAffecteDto>> grouped = new HashMap<>();

        for (ResultatsElevesAffecteDto classe : detailsBull) {
            // Supposons que vous avez une méthode pour obtenir le niveau depuis l'objet classe
            // Vous devrez adapter cette partie selon votre structure de données
            String niveau = obtenirNiveau(classe); // À implémenter selon votre logique

            grouped.computeIfAbsent(niveau, k -> new ArrayList<>()).add(classe);
        }

        return grouped;
    }

    private static String obtenirNiveau(ResultatsElevesAffecteDto classe) {
        if (classe.getOrdre_niveau() == null) return "";

        switch (classe.getOrdre_niveau()) {
            case 1: return "6ème";
            case 2: return "5ème";
            case 3: return "4ème";
            case 4: return "3ème";
            case 5: return "2nde A";
            case 6: return "2nde C";
            case 7: return "1ère A";
            case 8: return "1ère C";
            case 9: return "1ère D";
            case 10: return "Tle A";
            case 11: return "Tle A1";
            case 12: return "Tle A2";
            case 13: return "Tle C";
            case 14: return "Tle D";
            default: return String.valueOf(classe.getOrdre_niveau());
        }
    }

    private static void ajouterBordures(XWPFTableRow row) {
        for (XWPFTableCell cell : row.getTableCells()) {
            CTTcPr tcPr = cell.getCTTc().getTcPr();
            if (tcPr == null) {
                tcPr = cell.getCTTc().addNewTcPr();
            }

            CTTcBorders borders = tcPr.getTcBorders();
            if (borders == null) {
                borders = tcPr.addNewTcBorders();
            }

            // Ajouter des bordures de tous les côtés
            CTBorder topBorder = borders.addNewTop();
            topBorder.setVal(STBorder.SINGLE);
            topBorder.setSz(BigInteger.valueOf(4));
            topBorder.setColor("000000");

            CTBorder bottomBorder = borders.addNewBottom();
            bottomBorder.setVal(STBorder.SINGLE);
            bottomBorder.setSz(BigInteger.valueOf(4));
            bottomBorder.setColor("000000");

            CTBorder leftBorder = borders.addNewLeft();
            leftBorder.setVal(STBorder.SINGLE);
            leftBorder.setSz(BigInteger.valueOf(4));
            leftBorder.setColor("000000");

            CTBorder rightBorder = borders.addNewRight();
            rightBorder.setVal(STBorder.SINGLE);
            rightBorder.setSz(BigInteger.valueOf(4));
            rightBorder.setColor("000000");
        }
    }

    private static void mettreEnGrasEtColorer(XWPFTableRow row) {
        for (XWPFTableCell cell : row.getTableCells()) {
            // Colorer le fond de la cellule
            CTTcPr tcPr = cell.getCTTc().getTcPr();
            if (tcPr == null) {
                tcPr = cell.getCTTc().addNewTcPr();
            }

            CTShd shd = tcPr.getShd();
            if (shd == null) {
                shd = tcPr.addNewShd();
            }
            shd.setFill("E6E6E6"); // Couleur gris clair

            // Mettre le texte en gras
            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    run.setBold(true);
                }
            }
        }
    }

    private static void ensureCellCount(XWPFTableRow row, int cellCount) {
        int currentCellCount = row.getTableCells().size();
        for (int i = currentCellCount; i < cellCount; i++) {
            row.addNewTableCell();
        }
    }

    public static double arrondie(double d) {
        return Double.parseDouble(String.format("%.2f", d));
    }

    private static void mettreEnGrasEtColorerTotalGeneral(XWPFTableRow row) {
        for (XWPFTableCell cell : row.getTableCells()) {
            // Colorer le fond de la cellule avec une couleur plus foncée pour le total général
            CTTcPr tcPr = cell.getCTTc().getTcPr();
            if (tcPr == null) {
                tcPr = cell.getCTTc().addNewTcPr();
            }

            CTShd shd = tcPr.getShd();
            if (shd == null) {
                shd = tcPr.addNewShd();
            }
            shd.setFill("D0D0D0"); // Couleur gris plus foncé pour le total général

            // Mettre le texte en gras
            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    run.setBold(true);
                }
            }
        }
    }

    // Méthodes existantes conservées pour compatibilité
    private static void replacePlaceholdersInTableRow(XWPFTableRow row, Map<String, String> dataMap) {
        for (XWPFTableCell cell : row.getTableCells()) {
            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                replacePlaceholdersInParagraph(paragraph, dataMap);
            }
        }
    }

    private static void replacePlaceholdersInParagraph(XWPFParagraph paragraph, Map<String, String> dataMap) {
        StringBuilder fullText = new StringBuilder();
        List<XWPFRun> runs = paragraph.getRuns();

        for (XWPFRun run : runs) {
            String text = run.getText(0);
            if (text != null) {
                fullText.append(text);
            }
        }

        String paragraphText = fullText.toString();

        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            paragraphText = paragraphText.replace(entry.getKey(), entry.getValue());
        }

        if (!fullText.toString().equals(paragraphText)) {
            int runIndex = 0;
            for (XWPFRun run : runs) {
                String runText = run.getText(0);
                if (runText != null) {
                    String newText = paragraphText.substring(0, Math.min(runText.length(), paragraphText.length()));
                    run.setText(newText, 0);
                    paragraphText = paragraphText.substring(newText.length());
                }
                runIndex++;
            }

            if (!paragraphText.isEmpty()) {
                XWPFRun newRun = paragraph.createRun();
                newRun.setText(paragraphText);
            }
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
}

package com.vieecoles.processors.dren4.Annuels;

import com.vieecoles.dto.StatistiquesNiveauSexeDto;
import com.vieecoles.processors.dren4.Services.StatistiquesNiveauSexeService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

@ApplicationScoped
public class WordTempListStatistiqueDfaAnnuelsProcessor {
    @Inject
    EntityManager em;

    @Inject
    StatistiquesNiveauSexeService dfaServices;

    int LongTableau;

    private static void ensureCellCount(XWPFTableRow row, int cellCount) {
        int currentCellCount = row.getTableCells().size();
        for (int i = currentCellCount; i < cellCount; i++) {
            row.addNewTableCell();
        }
    }

    public void getListeMajorClasse(XWPFDocument document,
                                    Long idEcole, String libelleAnnee, String libelleTrimestre) {

        try {
            // Rechercher l'endroit où insérer le tableau
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            int indexToInsert = -1;

            for (int i = 0; i < paragraphs.size(); i++) {
                String text = paragraphs.get(i).getText();
                if (text.contains("EFFECTIFS ET  PYRAMIDES  APRES LES CONSEILS DE FIN D’ANNEE")) {
                    indexToInsert = i + 1;
                    break;
                }
            }

            if (indexToInsert == -1) {
                System.err.println("Position d'insertion du tableau non trouvée");
                return;
            }

            // Récupérer les données par niveau
            List<StatistiquesNiveauSexeDto> listDfa = dfaServices.getStatistiqueDfa(idEcole,libelleAnnee,libelleTrimestre);

            if (listDfa.size() > 0) {
                // Créer le tableau principal
                XWPFTable table = document.insertNewTbl(document.getParagraphs().get(indexToInsert).getCTP().newCursor());

                // Configuration du style de tableau
                configurerStyleTableau(table);

                // Créer l'en-tête du tableau
                creerEnteteTableau(table);

                // Organiser les données par cycles
                Map<String, List<StatistiquesNiveauSexeDto>> donneesParCycle = organiserDonneesParCycle(listDfa);

                // Remplir le tableau avec les données
                remplirTableauAvecDonnees(table, donneesParCycle);

                // Ajouter les lignes de totaux
                ajouterLignesTotaux(table, listDfa);

                System.out.println("Tableau DFA créé avec succès avec " + listDfa.size() + " niveaux");
            } else {
                System.out.println("Aucune donnée trouvée pour créer le tableau");
            }

        } catch (Exception e) {
            System.err.println("Erreur générale dans getListeMajorClasse: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void configurerStyleTableau(XWPFTable table) {
        // Configuration de base du tableau
        table.setWidth("100%");
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        if (tblPr == null) {
            tblPr = table.getCTTbl().addNewTblPr();
        }

        // Bordures du tableau
        CTTblBorders borders = tblPr.addNewTblBorders();
        borders.addNewTop().setVal(STBorder.SINGLE);
        borders.addNewBottom().setVal(STBorder.SINGLE);
        borders.addNewLeft().setVal(STBorder.SINGLE);
        borders.addNewRight().setVal(STBorder.SINGLE);
        borders.addNewInsideH().setVal(STBorder.SINGLE);
        borders.addNewInsideV().setVal(STBorder.SINGLE);
    }

    private void creerEnteteTableau(XWPFTable table) {
        // Première ligne d'en-tête (titres principaux)
        XWPFTableRow headerRow1 = table.getRow(0);
        ensureCellCount(headerRow1, 17); // 17 colonnes au total

        // Remplir les cellules de la première ligne
        headerRow1.getCell(0).setText("NIVEAU");
        headerRow1.getCell(1).setText("EFF. ET PYRA.\n(Avant les\nConseils)");
        headerRow1.getCell(5).setText("ADMIS");
        headerRow1.getCell(8).setText("REDOUBLANTS");
        headerRow1.getCell(11).setText("EXCLUS");
        headerRow1.getCell(14).setText("EFF. ET PYRA.\n(après les Conseils)");

        // Configurer le style des cellules d'en-tête
        for (int i = 0; i < headerRow1.getTableCells().size(); i++) {
            configurerCelluleEntete(headerRow1.getCell(i));
        }

        // Fusion des cellules de la première ligne
        fusionnerCellulesHorizontales(table, 0, 1, 4);  // EFF. ET PYRA (Avant) - colonnes 1-4
        fusionnerCellulesHorizontales(table, 0, 5, 7);  // ADMIS - colonnes 5-7
        fusionnerCellulesHorizontales(table, 0, 8, 10); // REDOUBLANTS - colonnes 8-10
        fusionnerCellulesHorizontales(table, 0, 11, 13); // EXCLUS - colonnes 11-13
        fusionnerCellulesHorizontales(table, 0, 14, 16); // EFF. ET PYRA (Après) - colonnes 14-16

        // Deuxième ligne d'en-tête (sous-titres)
        XWPFTableRow headerRow2 = table.createRow();
        ensureCellCount(headerRow2, 17);

        String[] titresLigne2 = {
            "", // NIVEAU (fusionné verticalement)
            "NC", "G", "F", "T", // EFF. ET PYRA (Avant les Conseils)
            "G", "F", "T",       // ADMIS
            "G", "F", "T",       // REDOUBLANTS
            "G", "F", "T",       // EXCLUS
            "NC", "G", "F", "T"  // EFF. ET PYRA (après les Conseils)
        };

        for (int i = 0; i < titresLigne2.length && i < headerRow2.getTableCells().size(); i++) {
            XWPFTableCell cell = headerRow2.getCell(i);
            cell.setText(titresLigne2[i]);
            configurerCelluleEntete(cell);
        }

        // Fusion verticale pour la colonne NIVEAU
        fusionnerCellulesVerticales(table, 0, 1, 0);
    }

    private Map<String, List<StatistiquesNiveauSexeDto>> organiserDonneesParCycle(List<StatistiquesNiveauSexeDto> listDfa) {
        Map<String, List<StatistiquesNiveauSexeDto>> cycles = new HashMap<>();

        for (StatistiquesNiveauSexeDto stat : listDfa) {
            String cycle = determinerCycle(stat.getOrdreNiveau());
            cycles.computeIfAbsent(cycle, k -> new java.util.ArrayList<>()).add(stat);
        }

        return cycles;
    }

    private String determinerCycle(Integer ordreNiveau) {
        if (ordreNiveau  <5) {
            return "1er Cycle";
        } else
            return "2nd Cycle";

    }

    private void remplirTableauAvecDonnees(XWPFTable table, Map<String, List<StatistiquesNiveauSexeDto>> donneesParCycle) {
        int currentRow = 2; // Commencer après les en-têtes

        // Traiter le 1er cycle (6ème à 3ème)
        if (donneesParCycle.containsKey("1er Cycle")) {
            List<StatistiquesNiveauSexeDto> cycle1 = donneesParCycle.get("1er Cycle");

            for (StatistiquesNiveauSexeDto stat : cycle1) {
                ajouterLigneNiveau(table, currentRow++, stat);
            }

            // Ligne total 1er cycle
            ajouterLigneTotalCycle(table, currentRow++, "Total 1er Cycle", cycle1);
        }

        // Traiter le 2nd cycle avec sous-totaux pour les 2nde
        if (donneesParCycle.containsKey("2nd Cycle")) {
            List<StatistiquesNiveauSexeDto> cycle2 = donneesParCycle.get("2nd Cycle");

            // Séparer les niveaux du 2nd cycle
            List<StatistiquesNiveauSexeDto> secondes = new ArrayList<>();
            List<StatistiquesNiveauSexeDto> premieres = new ArrayList<>();
            List<StatistiquesNiveauSexeDto> terminales = new ArrayList<>();

            for (StatistiquesNiveauSexeDto stat : cycle2) {
                int ordre = stat.getOrdreNiveau();
                if (ordre == 5 || ordre == 6) { // 2nde A et 2nde C
                    secondes.add(stat);
                } else if (ordre >= 7 && ordre <= 9) { // 1ère A, C, D
                    premieres.add(stat);
                } else if (ordre >= 10 && ordre <= 14) { // Terminales
                    terminales.add(stat);
                }
            }

            // Traiter les 2nde
            if (!secondes.isEmpty()) {
                // Ajouter toutes les 2nde (A et C)
                for (StatistiquesNiveauSexeDto stat : secondes) {
                    ajouterLigneNiveau(table, currentRow++, stat);
                }

                // Total des 2nde (A + C)
                ajouterLigneTotalCycle(table, currentRow++, "Total 2nde", secondes);
            }

            // Traiter les 1ère
            for (StatistiquesNiveauSexeDto stat : premieres) {
                ajouterLigneNiveau(table, currentRow++, stat);
            }
            if (!premieres.isEmpty()) {
                ajouterLigneTotalCycle(table, currentRow++, "Total 1ère", premieres);
            }

            // Traiter les Terminales
            for (StatistiquesNiveauSexeDto stat : terminales) {
                ajouterLigneNiveau(table, currentRow++, stat);
            }
            if (!terminales.isEmpty()) {
                ajouterLigneTotalCycle(table, currentRow++, "Total Terminale", terminales);
            }

            // Ligne total 2nd cycle
            ajouterLigneTotalCycle(table, currentRow++, "Total 2nd Cycle", cycle2);
        }
    }

    private void ajouterLigneNiveau(XWPFTable table, int rowIndex, StatistiquesNiveauSexeDto stat) {
        XWPFTableRow row = table.createRow();
        ensureCellCount(row, 17); // 17 colonnes maintenant

        // Convertir le niveau en libellé (6 -> 6ème, etc.)
        String niveauLibelle = convertirNiveauEnLibelle(stat.getOrdreNiveau());

        // Remplir les données
        row.getCell(0).setText(niveauLibelle); // NIVEAU

        // EFF. ET PYRA (Avant les Conseils)
        row.getCell(1).setText(String.valueOf(stat.getNombreClasses())); // NC (Nombre de classes)
        row.getCell(2).setText(String.valueOf(stat.getTotalGarcon())); // G
        row.getCell(3).setText(String.valueOf(stat.getTotalFille())); // F
        row.getCell(4).setText(String.valueOf(stat.getTotalEleves())); // T

        // ADMIS
        row.getCell(5).setText(String.valueOf(stat.getAdmisGarcon())); // G
        row.getCell(6).setText(String.valueOf(stat.getAdmisFille())); // F
        row.getCell(7).setText(String.valueOf(stat.getTotalAdmis())); // T

        // REDOUBLANTS
        row.getCell(8).setText(String.valueOf(stat.getRedoublantGarcon())); // G
        row.getCell(9).setText(String.valueOf(stat.getRedoublantFille())); // F
        row.getCell(10).setText(String.valueOf(stat.getTotalRedoublants())); // T

        // EXCLUS
        row.getCell(11).setText(String.valueOf(stat.getExclusGarcon())); // G
        row.getCell(12).setText(String.valueOf(stat.getExclusFille())); // F
        row.getCell(13).setText(String.valueOf(stat.getTotalExclus())); // T

        // EFF. ET PYRA (après les Conseils) - Calculé automatiquement
        int apresConseilsGarcons = 0;
        int apresConseilsFilles = 0;
        int apresConseilsTotal = 0;

        row.getCell(14).setText("0"); // NC
        row.getCell(15).setText(String.valueOf(apresConseilsGarcons)); // G
        row.getCell(16).setText(String.valueOf(apresConseilsFilles)); // F
        // Note: Il manque une colonne pour le total, vérifiez si vous avez bien 17 colonnes ou ajustez

        // Configurer le style des cellules
        for (XWPFTableCell cell : row.getTableCells()) {
            configurerCelluleData(cell);
        }
    }

    private void ajouterLigneTotalCycle(XWPFTable table, int rowIndex, String libelleCycle, List<StatistiquesNiveauSexeDto> donneesCycle) {
        XWPFTableRow row = table.createRow();
        ensureCellCount(row, 16);

        // Calculer les totaux du cycle
        long totalClasses = donneesCycle.stream().mapToLong(StatistiquesNiveauSexeDto::getNombreClasses).sum();
        long totalGarcons = donneesCycle.stream().mapToLong(StatistiquesNiveauSexeDto::getTotalGarcon).sum();
        long totalFilles = donneesCycle.stream().mapToLong(StatistiquesNiveauSexeDto::getTotalFille).sum();
        long totalEleves = donneesCycle.stream().mapToLong(StatistiquesNiveauSexeDto::getTotalEleves).sum();
        long totalAdmisG = donneesCycle.stream().mapToLong(StatistiquesNiveauSexeDto::getAdmisGarcon).sum();
        long totalAdmisF = donneesCycle.stream().mapToLong(StatistiquesNiveauSexeDto::getAdmisFille).sum();
        long totalAdmis = donneesCycle.stream().mapToLong(StatistiquesNiveauSexeDto::getTotalAdmis).sum();
        long totalRedoubG = donneesCycle.stream().mapToLong(StatistiquesNiveauSexeDto::getRedoublantGarcon).sum();
        long totalRedoubF = donneesCycle.stream().mapToLong(StatistiquesNiveauSexeDto::getRedoublantFille).sum();
        long totalRedoub = donneesCycle.stream().mapToLong(StatistiquesNiveauSexeDto::getTotalRedoublants).sum();
        long totalExclG = donneesCycle.stream().mapToLong(StatistiquesNiveauSexeDto::getExclusGarcon).sum();
        long totalExclF = donneesCycle.stream().mapToLong(StatistiquesNiveauSexeDto::getExclusFille).sum();
        long totalExcl = donneesCycle.stream().mapToLong(StatistiquesNiveauSexeDto::getTotalExclus).sum();

        // Remplir la ligne de total
        row.getCell(0).setText(libelleCycle);
        row.getCell(1).setText(String.valueOf(totalClasses)); // NC
        row.getCell(2).setText(String.valueOf(totalGarcons)); // G
        row.getCell(3).setText(String.valueOf(totalFilles)); // F
        row.getCell(4).setText(String.valueOf(totalEleves)); // T
        row.getCell(5).setText(String.valueOf(totalAdmisG)); // Admis G
        row.getCell(6).setText(String.valueOf(totalAdmisF)); // Admis F
        row.getCell(7).setText(String.valueOf(totalAdmis)); // Admis T
        row.getCell(8).setText(String.valueOf(totalRedoubG)); // Redoub G
        row.getCell(9).setText(String.valueOf(totalRedoubF)); // Redoub F
        row.getCell(10).setText(String.valueOf(totalRedoub)); // Redoub T
        row.getCell(11).setText(String.valueOf(totalExclG)); // Excl G
        row.getCell(12).setText(String.valueOf(totalExclF)); // Excl F
        row.getCell(13).setText(String.valueOf(totalExcl)); // Excl T

        // Style en gras pour les totaux
        for (XWPFTableCell cell : row.getTableCells()) {
            configurerCelluleTotal(cell);
        }
    }

    private void ajouterLignesTotaux(XWPFTable table, List<StatistiquesNiveauSexeDto> listDfa) {
        // Total général
        XWPFTableRow totalRow = table.createRow();
        ensureCellCount(totalRow, 16);

        // Calculer les totaux généraux
        long totalClasses = listDfa.stream().mapToLong(StatistiquesNiveauSexeDto::getNombreClasses).sum();
        long totalGarcons = listDfa.stream().mapToLong(StatistiquesNiveauSexeDto::getTotalGarcon).sum();
        long totalFilles = listDfa.stream().mapToLong(StatistiquesNiveauSexeDto::getTotalFille).sum();
        long totalEleves = listDfa.stream().mapToLong(StatistiquesNiveauSexeDto::getTotalEleves).sum();
        long totalAdmisG = listDfa.stream().mapToLong(StatistiquesNiveauSexeDto::getAdmisGarcon).sum();
        long totalAdmisF = listDfa.stream().mapToLong(StatistiquesNiveauSexeDto::getAdmisFille).sum();
        long totalAdmis = listDfa.stream().mapToLong(StatistiquesNiveauSexeDto::getTotalAdmis).sum();
        long totalRedoubG = listDfa.stream().mapToLong(StatistiquesNiveauSexeDto::getRedoublantGarcon).sum();
        long totalRedoubF = listDfa.stream().mapToLong(StatistiquesNiveauSexeDto::getRedoublantFille).sum();
        long totalRedoub = listDfa.stream().mapToLong(StatistiquesNiveauSexeDto::getTotalRedoublants).sum();
        long totalExclG = listDfa.stream().mapToLong(StatistiquesNiveauSexeDto::getExclusGarcon).sum();
        long totalExclF = listDfa.stream().mapToLong(StatistiquesNiveauSexeDto::getExclusFille).sum();
        long totalExcl = listDfa.stream().mapToLong(StatistiquesNiveauSexeDto::getTotalExclus).sum();

        // Remplir la ligne de total général
        totalRow.getCell(0).setText("Total Général");
        totalRow.getCell(1).setText(String.valueOf(totalClasses)); // NC
        totalRow.getCell(2).setText(String.valueOf(totalGarcons));
        totalRow.getCell(3).setText(String.valueOf(totalFilles));
        totalRow.getCell(4).setText(String.valueOf(totalEleves));
        totalRow.getCell(5).setText(String.valueOf(totalAdmisG));
        totalRow.getCell(6).setText(String.valueOf(totalAdmisF));
        totalRow.getCell(7).setText(String.valueOf(totalAdmis));
        totalRow.getCell(8).setText(String.valueOf(totalRedoubG));
        totalRow.getCell(9).setText(String.valueOf(totalRedoubF));
        totalRow.getCell(10).setText(String.valueOf(totalRedoub));
        totalRow.getCell(11).setText(String.valueOf(totalExclG));
        totalRow.getCell(12).setText(String.valueOf(totalExclF));
        totalRow.getCell(13).setText(String.valueOf(totalExcl));

        // Style en gras pour le total général
        for (XWPFTableCell cell : totalRow.getTableCells()) {
            configurerCelluleTotal(cell);
        }
    }

    private String convertirNiveauEnLibelle(Integer ordreNiveau) {
        if (ordreNiveau == null) return "";

        switch (ordreNiveau) {
            case 1 : return "6ème";
            case 2 : return "5ème";
            case 3 : return "4ème";
            case 4 : return "3ème";
            case 5 : return "2nde A";
            case 6 : return "2nde C";
            case 7 : return "1ère A";
            case 8 : return "1ère C";
            case 9 : return "1ère D";
            case 10 : return "Tle A";
            case 11 : return"Tle A1";
            case 12 : return "Tle A2";
            case 13 : return "Tle C";
            case 14 : return "Tle D";
            default : return String.valueOf(ordreNiveau);
        }
    }

    private void configurerCelluleEntete(XWPFTableCell cell) {
        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        // Couleur de fond grise pour l'en-tête
        cell.setColor("D3D3D3");
    }

    private void configurerCelluleData(XWPFTableCell cell) {
        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
    }

    private void configurerCelluleTotal(XWPFTableCell cell) {
        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        // Couleur de fond légèrement grise pour les totaux
        cell.setColor("F0F0F0");
    }

    private void fusionnerCellulesHorizontales(XWPFTable table, int row, int startCol, int endCol) {
        try {
            if (table == null || row < 0 || row >= table.getRows().size() || startCol < 0 || endCol < startCol) {
                return;
            }

            XWPFTableRow tableRow = table.getRow(row);
            if (tableRow == null) {
                return;
            }

            for (int i = startCol; i <= endCol && i < tableRow.getTableCells().size(); i++) {
                XWPFTableCell cell = tableRow.getCell(i);
                if (cell != null) {
                    CTTcPr tcPr = cell.getCTTc().getTcPr();
                    if (tcPr == null) {
                        tcPr = cell.getCTTc().addNewTcPr();
                    }

                    CTHMerge hMerge = tcPr.isSetHMerge() ? tcPr.getHMerge() : tcPr.addNewHMerge();
                    if (i == startCol) {
                        hMerge.setVal(STMerge.RESTART);
                    } else {
                        hMerge.setVal(STMerge.CONTINUE);
                        // Vider le contenu des cellules fusionnées (sauf la première)
                        cell.removeParagraph(0);
                        cell.addParagraph();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la fusion horizontale des cellules: " + e.getMessage());
        }
    }

    private void fusionnerCellulesVerticales(XWPFTable table, int startRow, int endRow, int col) {
        try {
            if (table == null || startRow < 0 || endRow < startRow || col < 0) {
                return;
            }

            for (int i = startRow; i <= endRow && i < table.getRows().size(); i++) {
                XWPFTableRow row = table.getRow(i);
                if (row != null && col < row.getTableCells().size()) {
                    XWPFTableCell cell = row.getCell(col);
                    if (cell != null) {
                        CTTcPr tcPr = cell.getCTTc().getTcPr();
                        if (tcPr == null) {
                            tcPr = cell.getCTTc().addNewTcPr();
                        }
                        CTVMerge vMerge = tcPr.isSetVMerge() ? tcPr.getVMerge() : tcPr.addNewVMerge();
                        if (i == startRow) {
                            vMerge.setVal(STMerge.RESTART);
                        } else {
                            vMerge.setVal(STMerge.CONTINUE);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la fusion des cellules: " + e.getMessage());
        }
    }
}

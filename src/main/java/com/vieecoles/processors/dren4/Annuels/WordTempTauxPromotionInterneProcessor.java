package com.vieecoles.processors.dren4.Annuels;

import com.vieecoles.dto.StatistiquesNiveauSexeDto;
import com.vieecoles.processors.dren4.Services.StatistiquesNiveauSexeService;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

@ApplicationScoped
public class WordTempTauxPromotionInterneProcessor {
  @Inject
  EntityManager em;

  @Inject
  StatistiquesNiveauSexeService dfaServices;

  private static void ensureCellCount(XWPFTableRow row, int cellCount) {
    int currentCellCount = row.getTableCells().size();
    for (int i = currentCellCount; i < cellCount; i++) {
      row.addNewTableCell();
    }
  }

  public void creerTableauTauxPromotion(XWPFDocument document,
                                        Long idEcole, String libelleAnnee, String libelleTrimestre) {

    try {
      // Rechercher l'endroit où insérer le tableau
      List<XWPFParagraph> paragraphs = document.getParagraphs();
      int indexToInsert = -1;

      for (int i = 0; i < paragraphs.size(); i++) {
        String text = paragraphs.get(i).getText();
        if (text.contains("TAUX DE PROMOTION INTERNE")) {
          indexToInsert = i + 1;
          break;
        }
      }

      if (indexToInsert == -1) {
        System.err.println("Position d'insertion du tableau non trouvée");
        return;
      }

      // Récupérer les données par niveau
      List<StatistiquesNiveauSexeDto> listDfa = dfaServices.obtenirStatistiquesParNiveauEtSexe(idEcole, libelleTrimestre, libelleAnnee);
      //listDfa.sort(Comparator.comparing(StatistiquesNiveauSexeDto::getOrdreNiveau).reversed());
      if (listDfa.size() > 0) {
        // Organiser les données par cycles
        Map<String, List<StatistiquesNiveauSexeDto>> donneesParCycle = organiserDonneesParCycle(listDfa);

        // Créer le titre "PREMIER CYCLE"
        ajouterTitreCycle(document, indexToInsert, "PREMIER CYCLE");

        // Créer le tableau pour le 1er cycle
        XWPFTable table1erCycle = document.insertNewTbl(document.getParagraphs().get(indexToInsert + 1).getCTP().newCursor());
        configurerTableauCycle(table1erCycle, donneesParCycle.get("1er Cycle"));

        // Créer le titre "SECOND CYCLE"
        ajouterTitreCycle(document, indexToInsert + 2, "SECOND CYCLE");

        // Créer le tableau pour le 2nd cycle
        XWPFTable table2ndCycle = document.insertNewTbl(document.getParagraphs().get(indexToInsert + 3).getCTP().newCursor());
        configurerTableauCycle(table2ndCycle, donneesParCycle.get("2nd Cycle"));

        System.out.println("Tableaux de taux de promotion créés avec succès");
      } else {
        System.out.println("Aucune donnée trouvée pour créer les tableaux");
      }

    } catch (Exception e) {
      System.err.println("Erreur générale dans creerTableauTauxPromotion: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private void ajouterTitreCycle(XWPFDocument document, int index, String titre) {
    XWPFParagraph paragraph = document.insertNewParagraph(document.getParagraphs().get(index).getCTP().newCursor());
    XWPFRun run = paragraph.createRun();
    run.setText(titre);
    run.setBold(true);
    paragraph.setAlignment(ParagraphAlignment.CENTER);
  }

  private void configurerTableauCycle(XWPFTable table, List<StatistiquesNiveauSexeDto> donneesCycle) {
    if (donneesCycle == null || donneesCycle.isEmpty()) {
      return;
    }

    // Configuration du style de tableau
    configurerStyleTableau(table);

    // Créer l'en-tête du tableau
    creerEnteteTableauPromotion(table);

    // Trier les données par ordre décroissant
   // donneesCycle.sort((a, b) -> Integer.compare(b.getOrdreNiveau(), a.getOrdreNiveau()));

    // Remplir le tableau avec les données
    int currentRow = 2; // Commencer après les en-têtes
    for (StatistiquesNiveauSexeDto stat : donneesCycle) {
      ajouterLigneNiveauPromotion(table, currentRow++, stat);
    }

    // Ajouter la ligne TOTAL
    ajouterLigneTotalPromotion(table, currentRow, donneesCycle);
  }

  private void configurerStyleTableau(XWPFTable table) {
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

  private void creerEnteteTableauPromotion(XWPFTable table) {
    // Première ligne d'en-tête
    XWPFTableRow headerRow1 = table.getRow(0);
    ensureCellCount(headerRow1, 13); // 13 colonnes

    String[] titresLigne1 = {
        "NIVEAUX", "EFF",
        "ADMIS", "", "", "",
        "REDOUBLENT", "", "", "",
        "EXCLU ( E ) S", "", ""
    };

    for (int i = 0; i < titresLigne1.length && i < headerRow1.getTableCells().size(); i++) {
      XWPFTableCell cell = headerRow1.getCell(i);
      if (!titresLigne1[i].isEmpty()) {
        cell.setText(titresLigne1[i]);
        configurerCelluleEntete(cell);
      }
    }

    // Fusion des cellules de la première ligne
    fusionnerCellulesHorizontales(table, 0, 2, 5);  // ADMIS
    fusionnerCellulesHorizontales(table, 0, 6, 9);  // REDOUBLENT
    fusionnerCellulesHorizontales(table, 0, 10, 12); // EXCLU(E)S

    // Deuxième ligne d'en-tête
    XWPFTableRow headerRow2 = table.createRow();
    ensureCellCount(headerRow2, 13);

    String[] titresLigne2 = {
        "", "", // NIVEAUX et EFF (fusionnés verticalement)
        "Garçons", "Filles", "TOTAL", "%",
        "Garçons", "Filles", "TOTAL", "%",
        "Garçons", "Filles", "TOTAL %"
    };

    for (int i = 0; i < titresLigne2.length && i < headerRow2.getTableCells().size(); i++) {
      XWPFTableCell cell = headerRow2.getCell(i);
      cell.setText(titresLigne2[i]);
      configurerCelluleEntete(cell);
    }

    // Fusion verticale pour NIVEAUX et EFF
    fusionnerCellulesVerticales(table, 0, 1, 0); // NIVEAUX
    fusionnerCellulesVerticales(table, 0, 1, 1); // EFF
  }

  private void ajouterLigneNiveauPromotion(XWPFTable table, int rowIndex, StatistiquesNiveauSexeDto stat) {
    XWPFTableRow row = table.createRow();
    ensureCellCount(row, 13);

    String niveauLibelle = convertirNiveauEnLibelle(stat.getOrdreNiveau());

    // NIVEAU
    row.getCell(0).setText(niveauLibelle);

    // EFF (Effectif total)
    row.getCell(1).setText(String.valueOf(stat.getTotalEleves()));

    // ADMIS
    row.getCell(2).setText(String.valueOf(stat.getAdmisGarcon()));
    row.getCell(3).setText(String.valueOf(stat.getAdmisFille()));
    row.getCell(4).setText(String.valueOf(stat.getTotalAdmis()));
    row.getCell(5).setText(calculerPourcentage(stat.getTotalAdmis(), stat.getTotalEleves()) + "%");

    // REDOUBLENT
    row.getCell(6).setText(String.valueOf(stat.getRedoublantGarcon()));
    row.getCell(7).setText(String.valueOf(stat.getRedoublantFille()));
    row.getCell(8).setText(String.valueOf(stat.getTotalRedoublants()));
    row.getCell(9).setText(calculerPourcentage(stat.getTotalRedoublants(), stat.getTotalEleves()) + "%");

    // EXCLU(E)S
    row.getCell(10).setText(String.valueOf(stat.getExclusGarcon()));
    row.getCell(11).setText(String.valueOf(stat.getExclusFille()));
    row.getCell(12).setText(String.valueOf(stat.getTotalExclus()) + " " + calculerPourcentage(stat.getTotalExclus(), stat.getTotalEleves()) + "%");

    // Configurer le style des cellules
    for (XWPFTableCell cell : row.getTableCells()) {
      configurerCelluleData(cell);
    }
  }

  private void ajouterLigneTotalPromotion(XWPFTable table, int rowIndex, List<StatistiquesNiveauSexeDto> donneesCycle) {
    XWPFTableRow row = table.createRow();
    ensureCellCount(row, 13);

    // Calculer les totaux
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
    row.getCell(0).setText("TOTAL");
    row.getCell(1).setText(String.valueOf(totalEleves));
    row.getCell(2).setText(String.valueOf(totalAdmisG));
    row.getCell(3).setText(String.valueOf(totalAdmisF));
    row.getCell(4).setText(String.valueOf(totalAdmis));
    row.getCell(5).setText(calculerPourcentage(totalAdmis, totalEleves) + "%");
    row.getCell(6).setText(String.valueOf(totalRedoubG));
    row.getCell(7).setText(String.valueOf(totalRedoubF));
    row.getCell(8).setText(String.valueOf(totalRedoub));
    row.getCell(9).setText(calculerPourcentage(totalRedoub, totalEleves) + "%");
    row.getCell(10).setText(String.valueOf(totalExclG));
    row.getCell(11).setText(String.valueOf(totalExclF));
    row.getCell(12).setText(String.valueOf(totalExcl) + " " + calculerPourcentage(totalExcl, totalEleves) + "%");

    // Style en gras pour les totaux
    for (XWPFTableCell cell : row.getTableCells()) {
      configurerCelluleTotal(cell);
    }
  }

  private String calculerPourcentage(long numerateur, long denominateur) {
    if (denominateur == 0) return "0.0";
    double pourcentage = (double) numerateur / denominateur * 100;
    return String.format("%.1f", pourcentage);
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
    if (ordreNiveau < 5) {
      return "1er Cycle";
    } else {
      return "2nd Cycle";
    }
  }

  private String convertirNiveauEnLibelle(Integer ordreNiveau) {
    if (ordreNiveau == null) return "";

    switch (ordreNiveau) {
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
      default: return String.valueOf(ordreNiveau);
    }
  }

  private void configurerCelluleEntete(XWPFTableCell cell) {
    XWPFParagraph paragraph = cell.getParagraphs().get(0);
    XWPFRun run = paragraph.createRun();
    run.setBold(true);
    paragraph.setAlignment(ParagraphAlignment.CENTER);
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

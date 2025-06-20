package com.vieecoles.processors.dren4.Annuels;

import com.vieecoles.dto.MajorParClasseNiveauDto;
import com.vieecoles.services.etats.appachePoi.Annuels.EleveAffecteAnnuelsParClassePoiServices;
import com.vieecoles.services.etats.appachePoi.Annuels.MajorAnnuelsParClasseNiveauPoiServices;
import com.vieecoles.services.etats.appachePoi.EleveAffecteParClassePoiServices;
import com.vieecoles.services.etats.appachePoi.MajorParClasseNiveauPoiServices;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

@ApplicationScoped
public class WordTempListMajorAnnuelsProcessor {
    @Inject
    EntityManager em;
    @Inject
    EleveAffecteAnnuelsParClassePoiServices eleveAffecteParClassePoiServices ;
    @Inject
    MajorAnnuelsParClasseNiveauPoiServices majorServices ;
    int LongTableau;

    private static void ensureCellCount(XWPFTableRow row, int cellCount) {
        int currentCellCount = row.getTableCells().size();
        for (int i = currentCellCount; i < cellCount; i++) {
            row.addNewTableCell(); // Ajouter une nouvelle cellule si nécessaire
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
                // Identifier l'emplacement où insérer le tableau (par exemple après "Liste des élèves affectés par classe")
                if (text.contains("Liste des  majors de classe par niveau(Annuels)")) {
                    indexToInsert = i + 1; // Ajouter après ce paragraphe
                    break;
                }
            }


            // Récupérer les données
            List<MajorParClasseNiveauDto> listeMajors = majorServices.MajorParNiveauClasse(idEcole, libelleAnnee, libelleTrimestre);
            listeMajors.sort(Comparator.comparing(MajorParClasseNiveauDto::getOrdre_niveau));
            // Vérifier si on a trouvé l'emplacement et si on a des données
            if (indexToInsert != -1 && listeMajors != null && !listeMajors.isEmpty()) {

                // Méthode alternative pour créer le tableau
                XWPFTable table;

                if (indexToInsert < paragraphs.size() - 1) {
                    // Insérer après le paragraphe trouvé
                    XWPFParagraph targetParagraph = paragraphs.get(indexToInsert);
                    table = document.insertNewTbl(targetParagraph.getCTP().newCursor());
                } else {
                    // Si c'est le dernier paragraphe, créer le tableau à la fin
                    table = document.createTable();
                }

                // Vérifier que le tableau a été créé
                if (table == null) {
                    System.err.println("Impossible de créer le tableau");
                    return;
                }

                // Créer la première ligne si elle n'existe pas
                XWPFTableRow headerRow;
                if (table.getRows().isEmpty()) {
                    headerRow = table.createRow();
                } else {
                    headerRow = table.getRow(0);
                }

                // S'assurer que la ligne d'en-tête a 10 cellules
                ensureCellCount(headerRow, 10);

                // Remplir l'en-tête - vérifier chaque cellule avant de l'utiliser
                if (headerRow.getTableCells().size() >= 10) {
                    headerRow.getCell(0).setText("NIVEAU");
                    headerRow.getCell(1).setText("RANG");
                    headerRow.getCell(2).setText("MATRICULE");
                    headerRow.getCell(3).setText("NOM ET PRENOMS");
                    headerRow.getCell(4).setText("ANNEE NAIS");
                    headerRow.getCell(5).setText("SEXE");
                    headerRow.getCell(6).setText("STATUT (AFF/NAFF)");
                    headerRow.getCell(7).setText("CLASSE");
                    headerRow.getCell(8).setText("MOY");
                    headerRow.getCell(9).setText("LV2");
                } else {
                    System.err.println("Impossible de créer assez de cellules dans l'en-tête");
                    return;
                }

                String lastNiveau = null;
                int startRowIndex = -1;
                int rowIndex = 1;
                int rangInNiveau = 1;

                // Parcourir la liste
                for (MajorParClasseNiveauDto eleve : listeMajors) {
                    if (eleve == null) continue;

                    XWPFTableRow row = table.createRow();
                    if (row == null) continue;

                    // S'assurer que chaque ligne a 10 cellules
                    ensureCellCount(row, 10);

                    // Vérifier que nous avons bien 10 cellules
                    if (row.getTableCells().size() < 10) {
                        System.err.println("Impossible de créer assez de cellules dans la ligne " + rowIndex);
                        continue;
                    }

                    String currentNiveau = eleve.getNiveau();

                    // Gérer le changement de niveau
                    if (lastNiveau == null || !lastNiveau.equals(currentNiveau)) {
                        // Nouveau niveau
                        if (lastNiveau != null && startRowIndex != -1) {
                            // Fusionner les cellules du niveau précédent
                            mergeVerticalCells(table, startRowIndex, rowIndex - 1, 0);
                        }

                        lastNiveau = currentNiveau;
                        startRowIndex = rowIndex;
                        rangInNiveau = 1;
                        row.getCell(0).setText(currentNiveau != null ? currentNiveau : "");
                    } else {
                        // Même niveau
                        rangInNiveau++;
                        row.getCell(0).setText("");
                    }

                    // Colonne RANG
                    String rang = "";
                    switch (rangInNiveau) {
                        case 1: rang = "1er"; break;
                        case 2: rang = "2è"; break;
                        case 3: rang = "3è"; break;
                        default: rang = String.valueOf(rangInNiveau) + "è"; break;
                    }
                    row.getCell(1).setText(rang);

                    // Remplir les autres colonnes avec vérification null
                    row.getCell(2).setText(eleve.getMatricule() != null ? eleve.getMatricule() : "");

                    String nomComplet = "";
                    if (eleve.getNom() != null || eleve.getPrenom() != null) {
                        nomComplet = (eleve.getNom() != null ? eleve.getNom() : "") + " " +
                            (eleve.getPrenom() != null ? eleve.getPrenom() : "");
                    }
                    row.getCell(3).setText(nomComplet.trim());

                    row.getCell(4).setText(eleve.getAnneeNaiss() != null ? eleve.getAnneeNaiss() : "");
                    row.getCell(5).setText(eleve.getSexe() != null ? eleve.getSexe() : "");
                    row.getCell(6).setText(eleve.getAffecte() != null ? eleve.getAffecte() : "");
                    row.getCell(7).setText(eleve.getClasseLibelle() != null ? eleve.getClasseLibelle() : "");
                    row.getCell(8).setText(eleve.getMoyGeneral() != null ? String.valueOf(eleve.getMoyGeneral()) : "");
                    row.getCell(9).setText(eleve.getLv2() != null ? String.valueOf(eleve.getLv2()) : "");

                    rowIndex++;
                }

                // Fusionner les cellules du dernier niveau
                if (lastNiveau != null && startRowIndex != -1 && startRowIndex < rowIndex - 1) {
                    mergeVerticalCells(table, startRowIndex, rowIndex - 1, 0);
                }

                System.out.println("Tableau créé avec succès avec " + (rowIndex - 1) + " lignes de données");
            } else {
                if (indexToInsert == -1) {
                    System.err.println("Texte 'Liste des majors de classe par niveau (Annuels)' non trouvé dans le document");
                }
                if (listeMajors == null || listeMajors.isEmpty()) {
                    System.err.println("Aucune donnée de majors trouvée");
                }
            }

        } catch (Exception e) {
            System.err.println("Erreur générale dans getListeMajorClasse: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mergeVerticalCells(XWPFTable table, int startRow, int endRow, int col) {
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

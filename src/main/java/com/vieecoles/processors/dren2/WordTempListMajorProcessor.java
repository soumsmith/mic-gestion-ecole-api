package com.vieecoles.processors.dren2;

import com.vieecoles.dto.MajorParClasseNiveauDto;
import com.vieecoles.services.etats.appachePoi.EleveAffecteParClassePoiServices;
import com.vieecoles.services.etats.appachePoi.MajorParClasseNiveauPoiServices;
import java.util.ArrayList;
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
public class WordTempListMajorProcessor {
    @Inject
    EntityManager em;
    @Inject
    EleveAffecteParClassePoiServices eleveAffecteParClassePoiServices ;
    @Inject
    MajorParClasseNiveauPoiServices majorServices ;
    int LongTableau;

    private static void ensureCellCount(XWPFTableRow row, int cellCount) {
        int currentCellCount = row.getTableCells().size();
        for (int i = currentCellCount; i < cellCount; i++) {
            row.addNewTableCell(); // Ajouter une nouvelle cellule si nécessaire
        }
    }
    public   void getListeMajorClasse(XWPFDocument document ,
                                           Long idEcole ,String libelleAnnee , String libelleTrimestre) {


//Get classe

        // Créer une nouvelle ligne dans le tableau

        // Rechercher l'endroit où insérer le tableau
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        int indexToInsert = -1;

        for (int i = 0; i < paragraphs.size(); i++) {
            String text = paragraphs.get(i).getText();
            // Identifier l'emplacement où insérer le tableau (par exemple après "Liste des élèves affectés par classe")
            if (text.contains("Liste des majors de classe par niveau")) {
                indexToInsert = i + 1; // Ajouter après ce paragraphe

                break;
            }
        }


            List<MajorParClasseNiveauDto>  listeMajors = new ArrayList<>() ;
            listeMajors= majorServices.MajorParNiveauClasse(idEcole ,libelleAnnee,libelleTrimestre) ;

        if (indexToInsert != -1) {

            XWPFTable table = document.insertNewTbl(paragraphs.get(indexToInsert).getCTP().newCursor());

            // Créer l'en-tête du tableau (1 ligne, 11 colonnes)
            XWPFTableRow headerRow = table.getRow(0);
            headerRow.getCell(0).setText("NIVEAU");
            headerRow.addNewTableCell().setText("CLASSE");
            headerRow.addNewTableCell().setText("MATRICULE");
            headerRow.addNewTableCell().setText("NOM ET PRENOMS");
            headerRow.addNewTableCell().setText("ANNEE NAIS");
            headerRow.addNewTableCell().setText("SEXE");
            headerRow.addNewTableCell().setText("NATURE");
            headerRow.addNewTableCell().setText("R");
            headerRow.addNewTableCell().setText("MOY");
            headerRow.addNewTableCell().setText("LV2");
            String lastNiveau = null;
            int startRowIndex = -1;
            int rowIndex = 1;// Exemple de 3 lignes
            // Ajouter des lignes au tableau
            for (MajorParClasseNiveauDto eleve : listeMajors) {

                XWPFTableRow row = table.createRow();
                row.getCell(1).setText(eleve.getClasseLibelle());
                row.getCell(2).setText(eleve.getMatricule());
                row.getCell(3).setText(eleve.getNom()+" "+eleve.getPrenom());
                row.getCell(4).setText(eleve.getAnneeNaiss());
                row.getCell(5).setText(eleve.getSexe());
                row.getCell(6).setText(eleve.getNature());
                row.getCell(7).setText(eleve.getRedoublant());
                row.getCell(8).setText(String.valueOf(eleve.getMoyGeneral()));
                row.getCell(9).setText(String.valueOf(eleve.getLv2()));

                String currentNiveau = eleve.getNiveau();

                if (lastNiveau == null || !lastNiveau.equals(currentNiveau)) {
                    // Nouveau niveau, on commence un nouveau groupe de fusion
                    lastNiveau = currentNiveau;
                    startRowIndex = rowIndex;
                    row.getCell(0).setText(currentNiveau);  // Afficher le niveau dans la première ligne du groupe
                } else {
                    // Même niveau que la ligne précédente, fusionner verticalement
                    mergeVerticalCells(table, startRowIndex, rowIndex, 0);  // Fusionne la colonne "NIVEAU"
                    row.getCell(0).setText("");  // Supprimer le texte dans les cellules fusionnées
                }

                rowIndex++;
            }

        }

    }

    private void mergeVerticalCells(XWPFTable table, int startRow, int endRow, int col) {
        for (int i = startRow; i <= endRow; i++) {
            XWPFTableCell cell = table.getRow(i).getCell(col);
            CTTcPr tcPr = cell.getCTTc().getTcPr();
            if (tcPr == null) {
                tcPr = cell.getCTTc().addNewTcPr();
            }
            CTVMerge vMerge = tcPr.isSetVMerge() ? tcPr.getVMerge() : tcPr.addNewVMerge();
            if (i == startRow) {
                vMerge.setVal(STMerge.RESTART);  // Démarre la fusion
            } else {
                vMerge.setVal(STMerge.CONTINUE);  // Continue la fusion
            }
        }
    }


}

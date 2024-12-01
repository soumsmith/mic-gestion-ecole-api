package com.vieecoles.processors.yamoussoukro;

import com.vieecoles.dto.EffApprocheNiveauGenreDto;
import com.vieecoles.services.etats.ApprocheParNiveauParGenreServices;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

@ApplicationScoped
public class WordTempEffectifApprocheNiveauGenre {
    @Inject
    EntityManager em;
    @Inject
    ApprocheParNiveauParGenreServices approcheParNiveauParGenreServices ;
    int LongTableau;

    private static void ensureCellCount(XWPFTableRow row, int cellCount) {
        int currentCellCount = row.getTableCells().size();
        for (int i = currentCellCount; i < cellCount; i++) {
            row.addNewTableCell(); // Ajouter une nouvelle cellule si nécessaire
        }
    }
    public   void getListeApprocheParNiveau(XWPFDocument document ,
                                           Long idEcole ,String libelleAnnee , String libelleTrimestre) {


//Get classe

        // Créer une nouvelle ligne dans le tableau

        // Rechercher l'endroit où insérer le tableau
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        int indexToInsert = -1;

        for (int i = 0; i < paragraphs.size(); i++) {
            String text = paragraphs.get(i).getText();
            // Identifier l'emplacement où insérer le tableau (par exemple après "Liste des élèves affectés par classe")
            if (text.toLowerCase().contains("EFFECTIF AVEC L’APPROCHE PAR NIVEAU ET PAR GENRE".toLowerCase())) {
                indexToInsert = i + 1; // Ajouter après ce paragraphe

                break;
            }
        }

        EffApprocheNiveauGenreDto listEffectifNiveau = new EffApprocheNiveauGenreDto()  ;
        listEffectifNiveau= approcheParNiveauParGenreServices.EffApprocheNiveauGenre(idEcole ,libelleAnnee,libelleTrimestre) ;

        if (indexToInsert != -1) {
          //  XWPFTable table = document.insertNewTbl(paragraphs.get(indexToInsert).getCTP().newCursor());
            createTableForLevels(document,listEffectifNiveau,indexToInsert,paragraphs);
        }

    }

    private void mergeCellsHorizontally(XWPFTable table, int row, int fromCell, int toCell) {
        XWPFTableRow tableRow = table.getRow(row);
        if (tableRow != null) {
            ensureCellCount(tableRow, toCell + 1); // S'assurer que toutes les cellules nécessaires sont présentes
            for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
                XWPFTableCell cell = tableRow.getCell(cellIndex);
                if (cell != null) {
                    if (cellIndex == fromCell) {
                        cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
                    } else {
                        cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
                    }
                }
            }
        }
    }




    // Méthode utilitaire pour définir les textes d'une ligne
    private void setRowTexts(XWPFTableRow row, String[] texts) {
        for (int i = 0; i < texts.length; i++) {
            row.getCell(i).setText(texts[i]);
        }
    }
    private void createRowForLevel(XWPFTable table, String niveau, Long base, Long fillesRED, Long fillesNRED, Long garconsRED, Long garconsNRED) {
        XWPFTableRow row = table.createRow();
        Long fillesTotal = fillesRED + fillesNRED;
        Long garconsTotal = garconsRED + garconsNRED;
        Long totalRED = fillesRED + garconsRED;
        Long totalNRED = fillesNRED + garconsNRED;
        Long total = fillesTotal + garconsTotal;

        setRowTexts(row, new String[] {
            niveau,
            String.valueOf(base),
            String.valueOf(fillesRED),
            String.valueOf(fillesNRED),
            String.valueOf(fillesTotal),
            String.valueOf(garconsRED),
            String.valueOf(garconsNRED),
            String.valueOf(garconsTotal),
            String.valueOf(totalRED),
            String.valueOf(totalNRED),
            String.valueOf(total)
        });
    }
    public void createTableForLevels(XWPFDocument document, EffApprocheNiveauGenreDto listEffectifNiveau, int indexToInsert ,List<XWPFParagraph> paragraphs ) {
        XWPFTable table = document.insertNewTbl(paragraphs.get(indexToInsert).getCTP().newCursor());

        XWPFTableRow headerRow1 = table.getRow(0);
        ensureCellCount(headerRow1, 11); // Assurez-vous que la ligne d'en-tête a 11 cellules
        headerRow1.getCell(0).setText("Niveau");
        headerRow1.getCell(1).setText("Base");
        headerRow1.getCell(2).setText("Filles");
        headerRow1.getCell(5).setText("Garçons");
        headerRow1.getCell(8).setText("Effectif total");

        // Fusion des cellules de la première ligne
        mergeCellsHorizontally(table, 0, 2, 4); // Fusion pour "Filles"
        mergeCellsHorizontally(table, 0, 5, 7); // Fusion pour "Garçons"
        mergeCellsHorizontally(table, 0, 8, 10); // Fusion pour "Effectif total"

        // Deuxième ligne : sous-en-têtes
        XWPFTableRow headerRow2 = table.createRow();
        ensureCellCount(headerRow2, 11);  // Assurez-vous que la ligne des sous-en-têtes a 11 cellules
        setRowTexts(headerRow2, new String[] {"", "", "RED", "N-RED", "TOTAL", "RED", "N-RED", "TOTAL", "RED", "N-RED", "TOTAL"});
  //Totaux premier cycle
        Long totalCollege = listEffectifNiveau.getN6() + listEffectifNiveau.getN5() + listEffectifNiveau.getN4() + listEffectifNiveau.getN3();
        Long totalCollegeRF = listEffectifNiveau.getN6RF() + listEffectifNiveau.getN5RF() + listEffectifNiveau.getN4RF() + listEffectifNiveau.getN3RF();
        Long totalCollegeNRF = listEffectifNiveau.getN6NRF() + listEffectifNiveau.getN5NRF() + listEffectifNiveau.getN4NRF() + listEffectifNiveau.getN3NRF();
        Long totalRedNredFCollege=totalCollegeRF+totalCollegeNRF;
        Long totalCollegeRG = listEffectifNiveau.getN6RG() + listEffectifNiveau.getN5RG() + listEffectifNiveau.getN4RG() + listEffectifNiveau.getN3RG();
        Long totalCollegeNRG = listEffectifNiveau.getN6NRG() + listEffectifNiveau.getN5NRG() + listEffectifNiveau.getN4NRG() + listEffectifNiveau.getN3NRG();
        Long totalRedNredGCollege =totalCollegeNRG+totalCollegeNRG;
        Long totalRedCollege = totalCollegeRF+totalCollegeRG;
        Long totalNredCollege =totalCollegeNRF+totalCollegeNRG;
        Long totalRedNredCollege = totalRedCollege+totalNredCollege;

        //Totaux 2nd
        Long total2nd = listEffectifNiveau.getN2ndA() + listEffectifNiveau.getN2ndC() ;
        Long total2ndRF = listEffectifNiveau.getN2ndARF() + listEffectifNiveau.getN2ndCRF() ;
        Long total2ndNRF = listEffectifNiveau.getN2ndANRF() + listEffectifNiveau.getN2ndCNRF() ;
        Long total2ndRG = listEffectifNiveau.getN2ndARG() + listEffectifNiveau.getN2ndCRG() ;
        Long total2ndNRG = listEffectifNiveau.getN2ndANRG() + listEffectifNiveau.getN2ndCNRG() ;

        Long totalRedNredF2nde=total2ndRF+total2ndNRF;
        Long totalRedNredG2nde =total2ndRG+total2ndNRG;
        Long totalRed2nde = total2ndRF+total2ndRG;
        Long totalNred2nde =total2ndNRF+total2ndNRG;
        Long totalRedNred2nde = totalRed2nde+totalNred2nde;

        //Totaux 1ere

        Long total1ere = listEffectifNiveau.getN1ereA() + listEffectifNiveau.getN1ereC() + listEffectifNiveau.getN1ereD();
        Long total1ereRF = listEffectifNiveau.getN1ereARF() + listEffectifNiveau.getN1ereCRF() + listEffectifNiveau.getN1ereDRF() ;
        Long total1ereNRF = listEffectifNiveau.getN1ereANRF() + listEffectifNiveau.getN1ereCNRF() + listEffectifNiveau.getN1ereDNRF() ;
        Long total1ereRG = listEffectifNiveau.getN1ereARG() + listEffectifNiveau.getN1ereCRG() + listEffectifNiveau.getN1ereDRG() ;
        Long total1ereNRG = listEffectifNiveau.getN1ereANRG() + listEffectifNiveau.getN1ereCNRG() + listEffectifNiveau.getN1ereDNRG() ;

        Long totalRedNredF1ere =total1ereRF+total1ereNRF;
        Long totalRedNredG1ere =total1ereRG+total1ereNRG;
        Long totalRed1ere = total1ereRF+total1ereRG;
        Long totalNred1ere =total1ereNRF+total1ereNRG;
        Long totalRedNred1ere = totalRed1ere+totalNred1ere;

       //Totaux Tle
        Long totalTle = listEffectifNiveau.getnTleA1() + listEffectifNiveau.getnTleA2() + listEffectifNiveau.getnTleC() + listEffectifNiveau.getnTleD();
        Long totalTleRF = listEffectifNiveau.getnTleA1RF() + listEffectifNiveau.getnTleA2RF() + listEffectifNiveau.getnTleCRF() + listEffectifNiveau.getnTleDRF();
        Long totalTleNRF = listEffectifNiveau.getnTleA1NRF() + listEffectifNiveau.getnTleA2NRF() + listEffectifNiveau.getnTleCNRF() + listEffectifNiveau.getnTleDNRF();
        Long totalTleRG = listEffectifNiveau.getnTleA1RG() + listEffectifNiveau.getnTleA2RG() + listEffectifNiveau.getnTleCRG() + listEffectifNiveau.getnTleDRG();
        Long totalTleNRG = listEffectifNiveau.getnTleA1NRG() + listEffectifNiveau.getnTleA2NRG() + listEffectifNiveau.getnTlCNRG() + listEffectifNiveau.getnTleDNRG();

        Long totalRedNredFTle =totalTleRF+totalTleNRF;
        Long totalRedNredGTle =totalTleRG+totalTleNRG;
        Long totalRedTle = totalTleRF+totalTleRG;
        Long totalNredTle =totalTleNRF+totalTleNRG;
        Long totalRedNredTle = totalRedTle+totalNredTle;

        //Totaux Etablissement
        Long totalGeneral = totalCollege + total2nd + total1ere + totalTle;
        Long totalGeneralRF = totalCollegeRF + total2ndRF + total1ereRF +totalTleRF;
        Long totalGeneralNRF = totalCollegeNRF + total2ndNRF + total1ereNRF +totalTleNRF;
        Long totalGeneralRG = totalCollegeRG + total2ndRG +total1ereRG +totalTleRG;
        Long totalGeneralNRG = totalCollegeNRG + total2ndNRG + total1ereNRG+totalTleNRG;

        Long totalRedNredFGeneral =totalGeneralRF+totalGeneralNRF;
        Long totalRedNredGGeneral =totalGeneralRG+totalGeneralNRG;
        Long totalRedGeneral = totalGeneralRF+totalGeneralRG;
        Long totalNredGeneral =totalGeneralNRF+totalGeneralNRG;
        Long totalRedNredGeneral = totalRedGeneral+totalNredGeneral;


// Répétez pour chaque groupe de niveaux, par exemple pour les "2nde" et "1ère-TLE".


        // Ajouter les niveaux en appelant createRowForLevel pour chaque niveau
        createRowForLevel(table, "6ème", listEffectifNiveau.getN6(), listEffectifNiveau.getN6RF(), listEffectifNiveau.getN6NRF(),
            listEffectifNiveau.getN6RG(), listEffectifNiveau.getN6NRG());
        createRowForLevel(table, "5ème", listEffectifNiveau.getN5(), listEffectifNiveau.getN5RF(), listEffectifNiveau.getN5NRF(),
            listEffectifNiveau.getN5RG(), listEffectifNiveau.getN5NRG());
        createRowForLevel(table, "4ème", listEffectifNiveau.getN4(), listEffectifNiveau.getN4RF(), listEffectifNiveau.getN4NRF(),
            listEffectifNiveau.getN4RG(), listEffectifNiveau.getN4NRG());
        createRowForLevel(table, "3ème", listEffectifNiveau.getN3(), listEffectifNiveau.getN3RF(), listEffectifNiveau.getN3NRF(),
            listEffectifNiveau.getN3RG(), listEffectifNiveau.getN3NRG());
        createTotalRow(table, "Total 1er Cycle", totalCollege, totalCollegeRF, totalCollegeNRF,totalRedNredFCollege,
            totalCollegeRG, totalCollegeNRG,totalRedNredGCollege,totalRedCollege,totalNredCollege,totalRedNredCollege);

        createRowForLevel(table, "2ndeA", listEffectifNiveau.getN2ndA(), listEffectifNiveau.getN2ndARF(), listEffectifNiveau.getN2ndANRF(),
            listEffectifNiveau.getN2ndARG(), listEffectifNiveau.getN2ndANRG());
        createRowForLevel(table, "2ndeC", listEffectifNiveau.getN2ndC(), listEffectifNiveau.getN2ndCRF(), listEffectifNiveau.getN2ndCNRF(),
            listEffectifNiveau.getN2ndCRG(), listEffectifNiveau.getN2ndCNRG());




        createTotalRow(table, "Total 2nd", total2nd, total2ndRF, total2ndNRF,totalRedNredF2nde, total2ndRG, total2ndNRG,totalRedNredG2nde,totalRed2nde,totalNred2nde,totalRedNred2nde);

        createRowForLevel(table, "1ère A", listEffectifNiveau.getN1ereA(), listEffectifNiveau.getN1ereARF(), listEffectifNiveau.getN1ereANRF(),
            listEffectifNiveau.getN1ereARG(), listEffectifNiveau.getN1ereANRG());

        createRowForLevel(table, "1ère C", listEffectifNiveau.getN1ereC(), listEffectifNiveau.getN1ereCRF(), listEffectifNiveau.getN1ereCNRF(),
            listEffectifNiveau.getN1ereCRG(), listEffectifNiveau.getN1ereCNRG());

        createRowForLevel(table, "1ère D", listEffectifNiveau.getN1ereD(), listEffectifNiveau.getN1ereDRF(), listEffectifNiveau.getN1ereDNRF(),
            listEffectifNiveau.getN1ereDRG(), listEffectifNiveau.getN1ereDNRG());

        createTotalRow(table, "Total 1ère", total1ere, total1ereRF, total1ereNRF,totalRedNredF1ere, total1ereRG, total1ereNRG,totalRedNredG1ere,
            totalRed1ere,totalNred1ere,totalRedNred1ere);

        createRowForLevel(table, "TLE A1", listEffectifNiveau.getnTleA1(), listEffectifNiveau.getnTleA1RF(), listEffectifNiveau.getnTleA1NRF(),
            listEffectifNiveau.getnTleA1RG(), listEffectifNiveau.getnTleA1NRG());

        createRowForLevel(table, "TLE A2", listEffectifNiveau.getnTleA2(), listEffectifNiveau.getnTleA2RF(), listEffectifNiveau.getnTleA2NRF(),
            listEffectifNiveau.getnTleA2RG(), listEffectifNiveau.getnTleA2NRG());

        createRowForLevel(table, "TLE D", listEffectifNiveau.getnTleD(), listEffectifNiveau.getnTleDRF(), listEffectifNiveau.getnTleDNRF(),
            listEffectifNiveau.getnTleDRG(), listEffectifNiveau.getnTleDNRG());

        createRowForLevel(table, "TLE C", listEffectifNiveau.getnTleC(), listEffectifNiveau.getnTleCRF(), listEffectifNiveau.getnTleCNRF(),
            listEffectifNiveau.getnTleCRG(), listEffectifNiveau.getnTlCNRG());

        createTotalRow(table, "TOTAL TERMINALES", totalTle, totalTleRF, totalTleNRF,totalRedNredFTle, totalTleRG,
            totalTleNRG,totalRedNredGTle,totalRedTle,totalNredTle,totalRedNredTle);

        createTotalRow(table, "TOTAL 2nd CYCLE", totalTle +total1ere+total2nd, totalTleRF+total1ereRF+total2ndRF, totalTleNRF+total1ereNRF+total2ndNRF,
            totalRedNredFTle+totalRedNredF1ere+totalRedNredF2nde, totalTleRG+total1ereRG+total2ndRG, totalTleNRG+total1ereNRG+total2ndNRG,
            totalRedNredGTle+totalRedNredG1ere+totalRedNredG2nde,totalRedTle+totalRed1ere+totalRed2nde,totalNredTle+totalNred1ere+totalNred2nde,totalRedNredTle+totalRedNred1ere+totalRedNred2nde);

        createTotalRow(table, "TOTAL ETABLISSEMENT", totalGeneral, totalGeneralRF, totalGeneralNRF,totalRedNredFGeneral,
            totalGeneralRG, totalGeneralNRG,totalRedNredGGeneral,totalRedGeneral,totalNredGeneral,totalRedNredGeneral);

    }
    private void createTotalRow(XWPFTable table, String label, Long total, Long totalRF, Long totalNRF,Long totalF, Long totalRG,
                                Long totalNRG,Long totalG ,Long effRed ,Long effNRed ,Long  eff) {
        XWPFTableRow row = table.createRow();
        row.getCell(0).setText(label);
        row.getCell(1).setText(String.valueOf(total));
        row.getCell(2).setText(String.valueOf(totalRF));
        row.getCell(3).setText(String.valueOf(totalNRF));
        row.getCell(4).setText(String.valueOf(totalF));
        row.getCell(5).setText(String.valueOf(totalRG));
        row.getCell(6).setText(String.valueOf(totalNRG));
        row.getCell(7).setText(String.valueOf(totalG));
        row.getCell(8).setText(String.valueOf(effRed));
        row.getCell(9).setText(String.valueOf(effNRed));
        row.getCell(10).setText(String.valueOf(eff));
    }

}

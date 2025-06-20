package com.vieecoles.processors.seguela;

import com.vieecoles.dto.RepartitionEleveParAnNaissDto;
import com.vieecoles.services.etats.RepartitionElevParAnNaissServices;
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
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

@ApplicationScoped
public class WordTempRepartitionAnneeNaissProcessor {
    @Inject
    EntityManager em;
    @Inject
    RepartitionElevParAnNaissServices repartitionElevParAnNaissServices ;
    int LongTableau;

    private static void ensureCellCount(XWPFTableRow row, int cellCount) {
        int currentCellCount = row.getTableCells().size();
        for (int i = currentCellCount; i < cellCount; i++) {
            row.addNewTableCell(); // Ajouter une nouvelle cellule si nécessaire
        }
    }
    public   void getListRepartitionParAnnee(XWPFDocument document ,
                                           Long idEcole ,String libelleAnnee , String libelleTrimestre) {


//Get classe

        List<XWPFParagraph> paragraphs = document.getParagraphs();
        int indexToInsert = -1;

        for (int i = 0; i < paragraphs.size(); i++) {
            String text = paragraphs.get(i).getText();
            // Identifier l'emplacement où insérer le tableau (par exemple après "Liste des élèves affectés par classe")
            if (text.toLowerCase().contains("repartition des eleves par annee de naissance".toLowerCase())) {
                indexToInsert = i + 1; // Ajouter après ce paragraphe

                break;
            }
        }


            List<RepartitionEleveParAnNaissDto> repartitionEleveParAnNaissDto = new ArrayList<>() ;
            repartitionEleveParAnNaissDto= repartitionElevParAnNaissServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimestre);

        if (indexToInsert != -1) {

            // Insérer le tableau juste après le paragraphe
            XWPFTable table = document.insertNewTbl(paragraphs.get(indexToInsert+1).getCTP().newCursor());

            // Créer l'en-tête du tableau (1 ligne, 11 colonnes)
            // Créer l'en-tête du tableau (1 ligne, 11 colonnes)
            XWPFTableRow headerRow = table.getRow(0);
            headerRow.getCell(0).setText("ANNEE°");
            headerRow.addNewTableCell().setText("SEXE");
            headerRow.addNewTableCell().setText("6è");
            headerRow.addNewTableCell().setText("5è");
            headerRow.addNewTableCell().setText("4è");
            headerRow.addNewTableCell().setText("3è");
            headerRow.addNewTableCell().setText("ST1");
            headerRow.addNewTableCell().setText("2A");
            headerRow.addNewTableCell().setText("2C");
            headerRow.addNewTableCell().setText("1A");
            headerRow.addNewTableCell().setText("1C");
            headerRow.addNewTableCell().setText("1A");
            headerRow.addNewTableCell().setText("1D");
            headerRow.addNewTableCell().setText("TA");
            headerRow.addNewTableCell().setText("TC");
            headerRow.addNewTableCell().setText("TD");
            headerRow.addNewTableCell().setText("ST2");

            // Ajouter des lignes au tableau
            for (RepartitionEleveParAnNaissDto eleve : repartitionEleveParAnNaissDto) {  // Exemple de 3 lignes

                // Remplir la ligne pour les filles (F)
                XWPFTableRow fillesRow = table.createRow();
                ensureCellCount(fillesRow, 17);
                fillesRow.getCell(0).setText(eleve.getAnnee());
                fillesRow.getCell(1).setText(" F");
                fillesRow.getCell(2).setText(String.valueOf(eleve.getAn6F()));
                fillesRow.getCell(3).setText(String.valueOf(eleve.getAn5F()));
                fillesRow.getCell(4).setText(String.valueOf(eleve.getAn4F()));
                fillesRow.getCell(5).setText(String.valueOf(eleve.getAn3F()));
                Long st1= eleve.getAn6F()+eleve.getAn5F()+eleve.getAn4F()+eleve.getAn3F();
                fillesRow.getCell(6).setText(String.valueOf(st1));
                fillesRow.getCell(7).setText(String.valueOf((eleve.getAn2AF())));
                fillesRow.getCell(8).setText(String.valueOf(eleve.getAn2CF()));
                fillesRow.getCell(9).setText(String.valueOf(eleve.getAn1AF()));
                fillesRow.getCell(10).setText(String.valueOf(eleve.getAn1CF()));
                fillesRow.getCell(11).setText(String.valueOf(eleve.getAn1DF()));
                fillesRow.getCell(12).setText(String.valueOf(eleve.getAnTAF()));
                fillesRow.getCell(13).setText(String.valueOf(eleve.getAnTCF()));
                fillesRow.getCell(14).setText(String.valueOf(eleve.getAnTDF()));
                Long st2= eleve.getAn2AF()+eleve.getAn2CF()+eleve.getAn1AF()+eleve.getAn1CF()+eleve.getAn1DF()+
                        eleve.getAnTAF()+eleve.getAnTCF()+eleve.getAnTDF();
                fillesRow.getCell(15).setText(String.valueOf(st2));
                Long tf= st1+st2 ;
                fillesRow.getCell(16).setText(String.valueOf(tf));

// Remplir la ligne pour les garçons (G)
                XWPFTableRow garconsRow = table.createRow();
                ensureCellCount(garconsRow, 17);
                garconsRow.getCell(1).setText(" G");
                garconsRow.getCell(2).setText(String.valueOf(eleve.getAn6G()));
                garconsRow.getCell(3).setText(String.valueOf(eleve.getAn5G()));
                garconsRow.getCell(4).setText(String.valueOf(eleve.getAn4G()));
                garconsRow.getCell(5).setText(String.valueOf(eleve.getAn3G()));
                Long st1G= eleve.getAn6G()+eleve.getAn5G()+eleve.getAn4G()+eleve.getAn3G();
                garconsRow.getCell(6).setText(String.valueOf(st1G));
                garconsRow.getCell(7).setText(String.valueOf((eleve.getAn2AG())));
                garconsRow.getCell(8).setText(String.valueOf(eleve.getAn2CG()));
                garconsRow.getCell(9).setText(String.valueOf(eleve.getAn1AG()));
                garconsRow.getCell(10).setText(String.valueOf(eleve.getAn1CG()));
                garconsRow.getCell(11).setText(String.valueOf(eleve.getAn1DG()));
                garconsRow.getCell(12).setText(String.valueOf(eleve.getAnTAG()));
                garconsRow.getCell(13).setText(String.valueOf(eleve.getAnTCG()));
                garconsRow.getCell(14).setText(String.valueOf(eleve.getAnTDG()));
                Long st2G= eleve.getAn2AG()+eleve.getAn2CG()+eleve.getAn1AG()+eleve.getAn1CG()+eleve.getAn1DG()+
                        eleve.getAnTAG()+eleve.getAnTCG()+eleve.getAnTDG();
                garconsRow.getCell(15).setText(String.valueOf(st2G));
                Long tG= st1G+st2G ;
                garconsRow.getCell(16).setText(String.valueOf(tG));

                table.getRow(table.getRows().size() - 2).getCell(0).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);  // Début de la fusion
                table.getRow(table.getRows().size() - 1).getCell(0).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);  // Continuer la fusion

            }
            XWPFTableRow etabliRow = table.createRow();
            ensureCellCount(etabliRow, 17);
            etabliRow.getCell(0).setText("TOTAL");
            Long t6 = 0L,t5 = 0L,t4 = 0L,t3= 0L,t2A= 0L,t2C= 0L,t1A= 0L,t1C= 0L,t1D= 0L,tTA= 0L ,tTC= 0L,tTD= 0L;

            for (RepartitionEleveParAnNaissDto eleve : repartitionEleveParAnNaissDto) {
                t6= eleve.getT6();
                t5= eleve.getT5();
                t4= eleve.getT4();
                t3= eleve.getT3();
                t2A= eleve.getT2A();
                t2C= eleve.getT2C();
                t1A= eleve.getT1A();
                t1C= eleve.getT1C();
                t1D= eleve.getT1D();
                tTA= eleve.gettTA();
                tTC= eleve.gettTC();
                tTD= eleve.gettTD();
            }

            etabliRow.getCell(2).setText(String.valueOf(t6));
            etabliRow.getCell(3).setText(String.valueOf(t5));
            etabliRow.getCell(4).setText(String.valueOf(t4));
            etabliRow.getCell(5).setText(String.valueOf(t3));
            Long totalSt1= t6+t5+t4+t3 ;
           etabliRow.getCell(6).setText(String.valueOf(totalSt1));
            etabliRow.getCell(7).setText(String.valueOf(t2A));
            etabliRow.getCell(8).setText(String.valueOf(t2C));
            etabliRow.getCell(9).setText(String.valueOf(t1A));
            etabliRow.getCell(10).setText(String.valueOf(t1C));
            etabliRow.getCell(11).setText(String.valueOf(t1D));
            etabliRow.getCell(12).setText(String.valueOf(tTA));
            etabliRow.getCell(13).setText(String.valueOf(tTC));
            etabliRow.getCell(14).setText(String.valueOf(tTD));
            Long totalSt2= t2A+t2C+t1A+t1C+t1D+tTA+tTC+tTD;
             etabliRow.getCell(15).setText(String.valueOf(totalSt2));
             Long total= totalSt1+totalSt2;
            etabliRow.getCell(16).setText(String.valueOf(total));
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

package com.vieecoles.processors.yamoussoukro;

import com.vieecoles.dto.RepartitionEleveParAnNaissDto;
import com.vieecoles.services.etats.RepartitionElevParAnNaissServices;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
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


        String tableTitle = "CODE_REPART_ANNE_NAISS";
        XWPFTable targetTable = null;
        for (int i = 0; i < document.getBodyElements().size(); i++) {
            IBodyElement element = document.getBodyElements().get(i);
            if (element.getElementType() == BodyElementType.PARAGRAPH) {
                XWPFParagraph paragraph = (XWPFParagraph) element;
                if (paragraph.getText().trim().equalsIgnoreCase(tableTitle)) {
                    paragraph.removeRun(0);
                    // Le tableau devrait suivre immédiatement le titre
                    if (i + 1 < document.getBodyElements().size() &&
                        document.getBodyElements().get(i + 1).getElementType() == BodyElementType.TABLE) {
                        targetTable = (XWPFTable) document.getBodyElements().get(i + 1);
                    }
                    break;
                }
            }
        }






            List<RepartitionEleveParAnNaissDto> repartitionEleveParAnNaissDto = new ArrayList<>() ;
            repartitionEleveParAnNaissDto= repartitionElevParAnNaissServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimestre);


            // Ajouter des lignes au tableau

        if (targetTable != null) {



            for (RepartitionEleveParAnNaissDto eleve : repartitionEleveParAnNaissDto) {  // Exemple de 3 lignes

                // Remplir la ligne pour les filles (F)
                XWPFTableRow fillesRow = targetTable.createRow();
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
                XWPFTableRow garconsRow = targetTable.createRow();
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

                targetTable.getRow(targetTable.getRows().size() - 2).getCell(0).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);  // Début de la fusion
                targetTable.getRow(targetTable.getRows().size() - 1).getCell(0).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);  // Continuer la fusion

            }
        }
            XWPFTableRow etabliRow = targetTable.createRow();
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

    public static void removeCellBorders(XWPFTableCell cell) {
        if (cell != null && cell.getCTTc() != null) {
            CTTcPr tcPr = cell.getCTTc().isSetTcPr() ? cell.getCTTc().getTcPr() : cell.getCTTc().addNewTcPr();
            if (tcPr != null) {
                CTTcBorders borders = tcPr.isSetTcBorders() ? tcPr.getTcBorders() : tcPr.addNewTcBorders();
                if (borders != null) {
                    borders.addNewTop().setVal(STBorder.NONE);
                    borders.addNewBottom().setVal(STBorder.NONE);
                    borders.addNewLeft().setVal(STBorder.NONE);
                    borders.addNewRight().setVal(STBorder.NONE);
                }
            }
        }
    }

    private static void mergeCellsHorizontal(XWPFTable table, int row, int fromCol, int toCol) {
        XWPFTableRow tableRow = table.getRow(row);
        for (int colIndex = fromCol; colIndex <= toCol; colIndex++) {
            XWPFTableCell cell = tableRow.getCell(colIndex);
            if (cell == null) continue;

            CTTcPr tcPr = cell.getCTTc().addNewTcPr();
            if (colIndex == fromCol) {
                tcPr.addNewGridSpan().setVal(BigInteger.valueOf(toCol - fromCol + 1));
            } else {
                cell.getCTTc().getDomNode().getParentNode().removeChild(cell.getCTTc().getDomNode());
                tableRow.getCtRow().getDomNode().getChildNodes().item(colIndex).setNodeValue("");
            }
        }
    }
    private static void ensureCellExists(XWPFTableRow row, int cellIndex) {
        while (row.getTableCells().size() <= cellIndex) {
            row.addNewTableCell();
        }
    }

}

package com.vieecoles.processors.bouake;

import com.vieecoles.dto.EnseignantDisciplineDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.processors.bouake.services.EnseignantParDisciplineServices;
import com.vieecoles.steph.entities.Ecole;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

@ApplicationScoped
public class WordTempStatistiqueProfesseurProcessor {
    @Inject
    EnseignantParDisciplineServices resultatsServices ;
  @Inject
  EntityManager em;

      public   void getNbreProfessProcessor(XWPFDocument document ,
          Long idEcole ,String libelleAnnee , String libelleTrimetre,Long idAnnee) {
        EnseignantDisciplineDto elevesPremierCycle = new EnseignantDisciplineDto();

        try {

          elevesPremierCycle= resultatsServices.EnseignantParDiscipline(idEcole,libelleTrimetre,libelleAnnee);
          System.out.println("elevesPremierCycle "+elevesPremierCycle.toString());
        } catch (Exception e) {
          e.printStackTrace();
        }



        String tableTitle = "CODE_ENSEIGNANT_PAR_DSCIPLINE";
        XWPFTable targetTable = null;
        for (int i = 0; i < document.getBodyElements().size(); i++) {
          IBodyElement element = document.getBodyElements().get(i);
          if (element.getElementType() == BodyElementType.PARAGRAPH) {
            XWPFParagraph paragraph = (XWPFParagraph) element;
            if (paragraph.getText().trim().equalsIgnoreCase(tableTitle)) {
              paragraph.removeRun(0); // Supprimer le texte du titre

              // Utiliser un curseur pour trouver le tableau
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


        try {
         ajoutTableauDynamique(elevesPremierCycle,targetTable, idEcole,libelleAnnee,libelleTrimetre);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

      }



    private static void ensureCellCount(XWPFTableRow row, int cellCount) {
        int currentCellCount = row.getTableCells().size();
        for (int i = currentCellCount; i < cellCount; i++) {
            row.addNewTableCell(); // Ajouter une nouvelle cellule si nécessaire
        }
    }


    private  void ajoutTableauDynamique(EnseignantDisciplineDto elevesPremierCycle,
                                              XWPFTable table,Long idEcole ,String libelleAnnee, String trimestre) {





      String currentValue = "";
      int nombreTotalClasseParNiveau1=0;
      int nombreTotalClasseParNiveau2=0;
      Double moyenCycle1F=0d;
      Double moyenCycle1G=0d;
      Double moyenCycle2F=0d;
      Double moyenCycle2G=0d;
      Double moyenGeneralF=0d;
      Double moyenGeneralG=0d;
      if(elevesPremierCycle!=null){

        // Supposons que targetTable a déjà été initialisé
        int rowIndex = 3; // Index de la ligne (0 pour la première ligne)
        int row2Index = 7;
        int cellFrCycle1H = 0;
        int cellFrCycle1F = 1;
        int cellFrCycle2H = 2;
        int cellFrCycle2F = 3;

        int cellHGCycle1H = 4;
        int cellHGCycle1F = 5;
        int cellHGCycle2H = 6;
        int cellHGCycle2F = 7;

        int cellANGCycle1H = 8;
        int cellANGCycle1F = 9;
        int cellANGCycle2H = 10;
        int cellANGCycle2F = 11;

        int cellPHILOCycle1H = 12;
        int cellPHILOCycle1F = 13;
        int cellPHILOCycle2H = 14;
        int cellPHILOCycle2F = 15;

        int cellALLCycle1H = 16;
        int cellALLCycle1F = 17;
        int cellALLCycle2H = 18;
        int cellALLCycle2F = 19;

        int cellESPCycle1H = 20;
        int cellESPCycle1F = 21;
        int cellESPCycle2H = 22;
        int cellESPCycle2F = 23;

        int cellMATHCycle1H = 24;
        int cellMATHCycle1F = 25;
        int cellMATHCycle2H = 26;
        int cellMATHCycle2F = 27;
        //Deuxieme ligne
        int cellSVTCycle1H = 0;
        int cellSVTCycle1F = 1;
        int cellSVTCycle2H = 2;
        int cellSVTCycle2F = 3;

        int cellEDHCCycle1H = 4;
        int cellEDHCCycle1F = 5;
        int cellEDHCCycle2H = 6;
        int cellEDHCCycle2F = 7;

        int cellARTPCycle1H = 8;
        int cellARTPCycle1F = 9;
        int cellARTPCycle2H = 10;
        int cellARTPCycle2F = 11;

        int cellPCCycle1H = 12;
        int cellPCCycle1F = 13;
        int cellPCCycle2H = 14;
        int cellPCCycle2F = 15;

        int cellEPSCycle1H = 16;
        int cellEPSCycle1F = 17;
        int cellEPSCycle2H = 18;
        int cellEPSCycle2F = 19;

        int cellINFCycle1H = 20;
        int cellINFCycle1F = 21;
        int cellINFCycle2H = 22;
        int cellINFCycle2F = 23;

        int cellTOTCycle1H = 24;
        int cellTOTCycle1F = 25;
        int cellTOTCycle2H = 26;
        int cellTOTCycle2F = 27;

// Vérifiez que la ligne existe
        if (rowIndex < table.getNumberOfRows()) {
          XWPFTableRow row = table.getRow(rowIndex);
          XWPFTableRow row2 = table.getRow(row2Index);
                     //Fr cycle1
          // Vérifiez que la cellule existe
          if (cellFrCycle1H < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellFrCycle1H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGFR1())); // Mettez votre valeur ici
          }
        if (cellFrCycle1F < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellFrCycle1F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFFR1())); // Mettez votre valeur ici
          }
          //Fr cycle2
           if (cellFrCycle2H < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellFrCycle2H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGFR2())); // Mettez votre valeur ici
          }
          if (cellFrCycle2F < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellFrCycle2F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFFR2())); // Mettez votre valeur ici
          }

          //Hg cycle1
          // Vérifiez que la cellule existe
          if (cellHGCycle1H < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellHGCycle1H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGHG1())); // Mettez votre valeur ici
          }
          if (cellHGCycle1F < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellHGCycle1F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFHG1())); // Mettez votre valeur ici
          }
          //HG cycle2
          if (cellHGCycle2H < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellHGCycle2H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGHG2())); // Mettez votre valeur ici
          }
          if (cellHGCycle2F < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellHGCycle2F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFHG2())); // Mettez votre valeur ici
          }

          //ANG cycle1
          // Vérifiez que la cellule existe
          if (cellANGCycle1H < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellANGCycle1H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGAN1())); // Mettez votre valeur ici
          }
          if (cellANGCycle1F < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellANGCycle1F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFAN1())); // Mettez votre valeur ici
          }
          //ANG cycle2
          if (cellANGCycle2H < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellANGCycle2H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGAN2())); // Mettez votre valeur ici
          }
          if (cellANGCycle2F < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellANGCycle2F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFAN2())); // Mettez votre valeur ici
          }

          //PHILO cycle1
          // Vérifiez que la cellule existe
          if (cellPHILOCycle1H < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellPHILOCycle1H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGPHILO1())); // Mettez votre valeur ici
          }
          if (cellPHILOCycle1F < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellPHILOCycle1F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFPHILO1())); // Mettez votre valeur ici
          }
          //PHILO cycle2
          if (cellPHILOCycle2H < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellPHILOCycle2H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGPHILO2())); // Mettez votre valeur ici
          }
          if (cellPHILOCycle2F < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellPHILOCycle2F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFPHILO2())); // Mettez votre valeur ici
          }

          //ALL cycle1
          // Vérifiez que la cellule existe
          if (cellALLCycle1H < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellALLCycle1H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGAll1())); // Mettez votre valeur ici
          }
          if (cellALLCycle1F < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellALLCycle1F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFAll1())); // Mettez votre valeur ici
          }
          //ALL cycle2
          if (cellALLCycle2H < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellALLCycle2H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGAll2())); // Mettez votre valeur ici
          }
          if (cellALLCycle2F < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellALLCycle2F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFAll2())); // Mettez votre valeur ici
          }

          //ESP cycle1
          // Vérifiez que la cellule existe
          if (cellESPCycle1H < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellESPCycle1H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGESP1())); // Mettez votre valeur ici
          }
          if (cellESPCycle1F < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellESPCycle1F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFESP1())); // Mettez votre valeur ici
          }
          //ESP cycle2
          if (cellESPCycle2H < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellESPCycle2H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGESP2())); // Mettez votre valeur ici
          }
          if (cellESPCycle2F < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellESPCycle2F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFESP2())); // Mettez votre valeur ici
          }

          //MATH cycle1
          // Vérifiez que la cellule existe
          if (cellMATHCycle1H < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellMATHCycle1H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGMATH1())); // Mettez votre valeur ici
          }
          if (cellMATHCycle1F < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellMATHCycle1F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFMATH1())); // Mettez votre valeur ici
          }
          //MATH cycle2
          if (cellMATHCycle2H < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellMATHCycle2H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGMATH2())); // Mettez votre valeur ici
          }
          if (cellMATHCycle2F < row.getTableCells().size()) {
            XWPFTableCell cell = row.getCell(cellMATHCycle2F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFMATH2())); // Mettez votre valeur ici
          }

          //SVT cycle1
          // Vérifiez que la cellule existe
          if (cellSVTCycle1H < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellSVTCycle1H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGSVT1())); // Mettez votre valeur ici
          }
          if (cellSVTCycle1F < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellSVTCycle1F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFSVT1())); // Mettez votre valeur ici
          }
          //SVT cycle2
          if (cellSVTCycle2H < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellSVTCycle2H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGSVT2())); // Mettez votre valeur ici
          }
          if (cellSVTCycle2F < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellSVTCycle2F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFSVT2())); // Mettez votre valeur ici
          }

          //EDHC cycle1
          // Vérifiez que la cellule existe
          if (cellEDHCCycle1H < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellEDHCCycle1H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGEDHC1())); // Mettez votre valeur ici
          }
          if (cellEDHCCycle1F < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellEDHCCycle1F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFEDHC1())); // Mettez votre valeur ici
          }
          //EDHC cycle2
          if (cellEDHCCycle2H < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellEDHCCycle2H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGEDHC2())); // Mettez votre valeur ici
          }
          if (cellEDHCCycle2F < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellEDHCCycle2F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFEDHC2())); // Mettez votre valeur ici
          }

          //ARPL cycle1
          // Vérifiez que la cellule existe
          if (cellARTPCycle1H < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellARTPCycle1H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGARPLMusi1())); // Mettez votre valeur ici
          }
          if (cellARTPCycle1F < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellARTPCycle1F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFARPLMusi1())); // Mettez votre valeur ici
          }
          //ARTP cycle2
          if (cellARTPCycle2H < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellARTPCycle2H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGARPLMusi2())); // Mettez votre valeur ici
          }
          if (cellARTPCycle2F < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellARTPCycle2F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFARPLMusi2())); // Mettez votre valeur ici
          }

          //PC cycle1
          // Vérifiez que la cellule existe
          if (cellPCCycle1H < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellPCCycle1H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGPC1())); // Mettez votre valeur ici
          }
          if (cellPCCycle1F < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellPCCycle1F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFPC1())); // Mettez votre valeur ici
          }
          //PC cycle2
          if (cellPCCycle2H < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellPCCycle2H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGPC2())); // Mettez votre valeur ici
          }
          if (cellPCCycle2F < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellPCCycle2F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFPC2())); // Mettez votre valeur ici
          }

          //EPS cycle1
          // Vérifiez que la cellule existe
          if (cellEPSCycle1H < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellEPSCycle1H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGEPS1())); // Mettez votre valeur ici
          }
          if (cellEPSCycle1F < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellEPSCycle1F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFEPS1())); // Mettez votre valeur ici
          }
          //EPS cycle2
          if (cellEPSCycle2H < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellEPSCycle2H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGEPS2())); // Mettez votre valeur ici
          }
          if (cellEPSCycle2F < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellEPSCycle2F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFEPS2())); // Mettez votre valeur ici
          }

          //INF cycle1
          // Vérifiez que la cellule existe
          if (cellINFCycle1H < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellINFCycle1H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGINFO1())); // Mettez votre valeur ici
          }
          if (cellINFCycle1F < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellINFCycle1F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFINFO1())); // Mettez votre valeur ici
          }
          //INF cycle2
          if (cellINFCycle2H < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellINFCycle2H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreGINFO2())); // Mettez votre valeur ici
          }
          if (cellINFCycle2F < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellINFCycle2F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreFINFO2())); // Mettez votre valeur ici
          }

          //TOT cycle1
          // Vérifiez que la cellule existe
          if (cellTOTCycle1H < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellTOTCycle1H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreG1())); // Mettez votre valeur ici
          }
          if (cellTOTCycle1F < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellTOTCycle1F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreF1())); // Mettez votre valeur ici
          }
          //TOT cycle2
          if (cellTOTCycle2H < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellTOTCycle2H);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreG2())); // Mettez votre valeur ici
          }
          if (cellTOTCycle2F < row2.getTableCells().size()) {
            XWPFTableCell cell = row2.getCell(cellTOTCycle2F);
            cell.setText(String.valueOf(elevesPremierCycle.getNbreF2())); // Mettez votre valeur ici
          }

          else {
            System.out.println("La cellule spécifiée n'existe pas.");
          }
        } else {
          System.out.println("La ligne spécifiée n'existe pas.");
        }






        /*  Ecole MyEcole;
          String lastValue = null;
          MyEcole= Ecole.findById(idEcole);
           currentValue = MyEcole.getLibelle();

          XWPFTableRow allRow = table.createRow();
          ensureCellCount(allRow, 15);
       // nbreClasseRow.getCell(0).setText(currentValue);
        allRow.getCell(1).setText("ALL");
        allRow.getCell(2).setText(String.valueOf(elevesPremierCycle.getNbreFAll1()));
        allRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getNbreGAll1()));
        allRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getNbreFAll1()+elevesPremierCycle.getNbreGAll1()));
        allRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getNbreFAll1()+elevesPremierCycle.getNbreGAll1()));
        allRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getNbreFAll2()));
        allRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getNbreGAll2()));
        allRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getNbreFAll2()+elevesPremierCycle.getNbreGAll2()));
        allRow.getCell(9).setText(String.valueOf(elevesPremierCycle.getNbreFAll2()+elevesPremierCycle.getNbreGAll2()));
        allRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getNbreFAll1()+elevesPremierCycle.getNbreFAll2()));
        allRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getNbreGAll1()+elevesPremierCycle.getNbreGAll2()));
        allRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getNbreFAll1()+elevesPremierCycle.getNbreFAll2()+
            elevesPremierCycle.getNbreGAll1()+elevesPremierCycle.getNbreGAll2()));
        allRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getNbreFAll1()+elevesPremierCycle.getNbreFAll2()+
            elevesPremierCycle.getNbreGAll1()+elevesPremierCycle.getNbreGAll2()));
        allRow.getCell(14).setText("");

        XWPFTableRow anRow = table.createRow();
        ensureCellCount(anRow, 15);
        // nbreClasseRow.getCell(0).setText(currentValue);
        anRow.getCell(1).setText("AN");
        anRow.getCell(2).setText(String.valueOf(elevesPremierCycle.getNbreFAN1()));
        anRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getNbreGAN1()));
        anRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getNbreFAN1()+elevesPremierCycle.getNbreGAN1()));
        anRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getNbreFAN1()+elevesPremierCycle.getNbreGAN1()));
        anRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getNbreFAN2()));
        anRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getNbreGAN2()));
        anRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getNbreFAN2()+elevesPremierCycle.getNbreGAN2()));
        anRow.getCell(9).setText(String.valueOf(elevesPremierCycle.getNbreFAN2()+elevesPremierCycle.getNbreGAN2()));
        anRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getNbreFAN1()+elevesPremierCycle.getNbreFAN2()));
        anRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getNbreGAN1()+elevesPremierCycle.getNbreGAN2()));
        anRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getNbreFAN1()+elevesPremierCycle.getNbreFAN2()+
            elevesPremierCycle.getNbreGAN1()+elevesPremierCycle.getNbreGAN2()));
        anRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getNbreFAN1()+elevesPremierCycle.getNbreFAN2()+
            elevesPremierCycle.getNbreGAN1()+elevesPremierCycle.getNbreGAN2()));
        anRow.getCell(14).setText("");

        XWPFTableRow edhcRow = table.createRow();
        ensureCellCount(edhcRow, 15);
        // nbreClasseRow.getCell(0).setText(currentValue);
        edhcRow.getCell(1).setText("EDHC");
        edhcRow.getCell(2).setText(String.valueOf(elevesPremierCycle.getNbreFEDHC1()));
        edhcRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getNbreGEDHC1()));
        edhcRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getNbreFEDHC1()+elevesPremierCycle.getNbreGEDHC1()));
        edhcRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getNbreFEDHC1()+elevesPremierCycle.getNbreGEDHC1()));
        edhcRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getNbreFEDHC2()));
        edhcRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getNbreGEDHC2()));
        edhcRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getNbreFEDHC2()+elevesPremierCycle.getNbreGEDHC2()));
        edhcRow.getCell(9).setText(String.valueOf(elevesPremierCycle.getNbreFEDHC2()+elevesPremierCycle.getNbreGEDHC2()));
        edhcRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getNbreFEDHC1()+elevesPremierCycle.getNbreFEDHC2()));
        edhcRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getNbreGEDHC1()+elevesPremierCycle.getNbreGEDHC2()));
        edhcRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getNbreFEDHC1()+elevesPremierCycle.getNbreFEDHC2()+
            elevesPremierCycle.getNbreGEDHC1()+elevesPremierCycle.getNbreGEDHC2()));
        edhcRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getNbreFEDHC1()+elevesPremierCycle.getNbreFEDHC2()+
            elevesPremierCycle.getNbreGEDHC1()+elevesPremierCycle.getNbreGEDHC2()));
        edhcRow.getCell(14).setText("");

        XWPFTableRow epsRow = table.createRow();
        ensureCellCount(epsRow, 15);
        // nbreClasseRow.getCell(0).setText(currentValue);
        epsRow.getCell(1).setText("EPS");
        epsRow.getCell(2).setText(String.valueOf(elevesPremierCycle.getNbreFEPS1()));
        epsRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getNbreGEPS1()));
        epsRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getNbreFEPS1()+elevesPremierCycle.getNbreGEPS1()));
        epsRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getNbreFEPS1()+elevesPremierCycle.getNbreGEPS1()));
        epsRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getNbreFEPS2()));
        epsRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getNbreGEPS2()));
        epsRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getNbreFEPS2()+elevesPremierCycle.getNbreGEPS2()));
        epsRow.getCell(9).setText(String.valueOf(elevesPremierCycle.getNbreFEPS2()+elevesPremierCycle.getNbreGEPS2()));
        epsRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getNbreFEPS1()+elevesPremierCycle.getNbreFEPS2()));
        epsRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getNbreGEPS1()+elevesPremierCycle.getNbreGEPS2()));
        epsRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getNbreFEPS1()+elevesPremierCycle.getNbreFEPS2()+
            elevesPremierCycle.getNbreGEPS1()+elevesPremierCycle.getNbreGEPS2()));
        epsRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getNbreFEPS1()+elevesPremierCycle.getNbreFEPS2()+
            elevesPremierCycle.getNbreGEPS1()+elevesPremierCycle.getNbreGEPS2()));
        epsRow.getCell(14).setText("");

        XWPFTableRow espRow = table.createRow();
        ensureCellCount(espRow, 15);
        // nbreClasseRow.getCell(0).setText(currentValue);
        espRow.getCell(1).setText("ESP");
        espRow.getCell(2).setText(String.valueOf(elevesPremierCycle.getNbreFESP1()));
        espRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getNbreGESP1()));
        espRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getNbreFEPS1()+elevesPremierCycle.getNbreGESP1()));
        espRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getNbreFEPS1()+elevesPremierCycle.getNbreGESP1()));
        espRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getNbreFESP2()));
        espRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getNbreGESP2()));
        espRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getNbreFESP2()+elevesPremierCycle.getNbreGESP2()));
        espRow.getCell(9).setText(String.valueOf(elevesPremierCycle.getNbreFESP2()+elevesPremierCycle.getNbreGESP2()));
        espRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getNbreFESP1()+elevesPremierCycle.getNbreFEPS2()));
        espRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getNbreGESP1()+elevesPremierCycle.getNbreGESP2()));
        espRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getNbreFESP1()+elevesPremierCycle.getNbreFESP2()+
            elevesPremierCycle.getNbreGESP1()+elevesPremierCycle.getNbreGESP2()));
        espRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getNbreFESP1()+elevesPremierCycle.getNbreFESP2()+
            elevesPremierCycle.getNbreGESP1()+elevesPremierCycle.getNbreGESP2()));
        espRow.getCell(14).setText("");

        XWPFTableRow frRow = table.createRow();
        ensureCellCount(frRow, 15);
        // nbreClasseRow.getCell(0).setText(currentValue);
        frRow.getCell(1).setText("FR");
        frRow.getCell(2).setText(String.valueOf(elevesPremierCycle.getNbreFFR1()));
        frRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getNbreGFR1()));
        frRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getNbreFFR1()+elevesPremierCycle.getNbreGFR1()));
        frRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getNbreFFR1()+elevesPremierCycle.getNbreGFR1()));
        frRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getNbreFFR2()));
        frRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getNbreGFR2()));
        frRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getNbreFFR2()+elevesPremierCycle.getNbreGFR2()));
        frRow.getCell(9).setText(String.valueOf(elevesPremierCycle.getNbreFFR2()+elevesPremierCycle.getNbreGFR2()));
        frRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getNbreFFR1()+elevesPremierCycle.getNbreFFR2()));
        frRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getNbreGFR1()+elevesPremierCycle.getNbreGFR2()));
        frRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getNbreFFR1()+elevesPremierCycle.getNbreFFR2()+
            elevesPremierCycle.getNbreGFR1()+elevesPremierCycle.getNbreGFR2()));
        frRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getNbreFFR1()+elevesPremierCycle.getNbreFFR2()+
            elevesPremierCycle.getNbreGFR1()+elevesPremierCycle.getNbreGFR2()));
        frRow.getCell(14).setText("");

        XWPFTableRow hgRow = table.createRow();
        ensureCellCount(hgRow, 15);
        // nbreClasseRow.getCell(0).setText(currentValue);
        hgRow.getCell(1).setText("HG");
        hgRow.getCell(2).setText(String.valueOf(elevesPremierCycle.getNbreFHG1()));
        hgRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getNbreGHG1()));
        hgRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getNbreFHG1()+elevesPremierCycle.getNbreGHG1()));
        hgRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getNbreFHG1()+elevesPremierCycle.getNbreGHG1()));
        hgRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getNbreFHG2()));
        hgRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getNbreGHG2()));
        hgRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getNbreFHG2()+elevesPremierCycle.getNbreGHG2()));
        hgRow.getCell(9).setText(String.valueOf(elevesPremierCycle.getNbreFHG2()+elevesPremierCycle.getNbreGHG2()));
        hgRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getNbreFHG1()+elevesPremierCycle.getNbreFHG2()));
        hgRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getNbreGHG1()+elevesPremierCycle.getNbreGHG2()));
        hgRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getNbreFHG1()+elevesPremierCycle.getNbreFHG2()+
            elevesPremierCycle.getNbreGHG1()+elevesPremierCycle.getNbreGHG2()));
        hgRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getNbreFHG1()+elevesPremierCycle.getNbreFHG2()+
            elevesPremierCycle.getNbreGHG1()+elevesPremierCycle.getNbreGHG2()));
        hgRow.getCell(14).setText("");

        XWPFTableRow mathRow = table.createRow();
        ensureCellCount(mathRow, 15);
        // nbreClasseRow.getCell(0).setText(currentValue);
        mathRow.getCell(1).setText("MATH");
        mathRow.getCell(2).setText(String.valueOf(elevesPremierCycle.getNbreFMATH1()));
        mathRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getNbreGMATH1()));
        mathRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getNbreFMATH1()+elevesPremierCycle.getNbreGMATH1()));
        mathRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getNbreFMATH1()+elevesPremierCycle.getNbreGMATH1()));
        mathRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getNbreFMATH2()));
        mathRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getNbreGMATH2()));
        mathRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getNbreFMATH2()+elevesPremierCycle.getNbreGMATH2()));
        mathRow.getCell(9).setText(String.valueOf(elevesPremierCycle.getNbreFMATH2()+elevesPremierCycle.getNbreGMATH2()));
        mathRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getNbreFMATH1()+elevesPremierCycle.getNbreFMATH2()));
        mathRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getNbreGMATH1()+elevesPremierCycle.getNbreGMATH2()));
        mathRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getNbreFMATH1()+elevesPremierCycle.getNbreFMATH2()+
            elevesPremierCycle.getNbreGMATH1()+elevesPremierCycle.getNbreGMATH2()));
        mathRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getNbreFMATH1()+elevesPremierCycle.getNbreFMATH2()+
            elevesPremierCycle.getNbreGMATH1()+elevesPremierCycle.getNbreGMATH2()));
        mathRow.getCell(14).setText("");

        XWPFTableRow pcRow = table.createRow();
        ensureCellCount(pcRow, 15);
        // nbreClasseRow.getCell(0).setText(currentValue);
        pcRow.getCell(1).setText("PC");
        pcRow.getCell(2).setText(String.valueOf(elevesPremierCycle.getNbreFPC1()));
        pcRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getNbreGPC1()));
        pcRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getNbreFPC1()+elevesPremierCycle.getNbreGPC1()));
        pcRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getNbreFPC1()+elevesPremierCycle.getNbreGPC1()));
        pcRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getNbreFPC2()));
        pcRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getNbreGPC2()));
        pcRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getNbreFPC2()+elevesPremierCycle.getNbreGPC2()));
        pcRow.getCell(9).setText(String.valueOf(elevesPremierCycle.getNbreFPC2()+elevesPremierCycle.getNbreGPC2()));
        pcRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getNbreFPC1()+elevesPremierCycle.getNbreFPC2()));
        pcRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getNbreGPC1()+elevesPremierCycle.getNbreGPC2()));
        pcRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getNbreFPC1()+elevesPremierCycle.getNbreFPC2()+
            elevesPremierCycle.getNbreGPC1()+elevesPremierCycle.getNbreGPC2()));
        pcRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getNbreFPC1()+elevesPremierCycle.getNbreFPC2()+
            elevesPremierCycle.getNbreGPC1()+elevesPremierCycle.getNbreGPC2()));
        pcRow.getCell(14).setText("");

        XWPFTableRow philoRow = table.createRow();
        ensureCellCount(philoRow, 15);
        // nbreClasseRow.getCell(0).setText(currentValue);
        philoRow.getCell(1).setText("PHILO");
        philoRow.getCell(2).setText(String.valueOf(elevesPremierCycle.getNbreFPHILO1()));
        philoRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getNbreGPHILO1()));
        philoRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getNbreFPHILO1()+elevesPremierCycle.getNbreGPHILO1()));
        philoRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getNbreFPHILO1()+elevesPremierCycle.getNbreGPHILO1()));
        philoRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getNbreFPHILO2()));
        philoRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getNbreGPHILO2()));
        philoRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getNbreFPHILO2()+elevesPremierCycle.getNbreGPHILO2()));
        philoRow.getCell(9).setText(String.valueOf(elevesPremierCycle.getNbreFPHILO2()+elevesPremierCycle.getNbreGPHILO2()));
        philoRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getNbreFPHILO1()+elevesPremierCycle.getNbreFPHILO2()));
        philoRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getNbreGPHILO1()+elevesPremierCycle.getNbreGPHILO2()));
        philoRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getNbreFPHILO1()+elevesPremierCycle.getNbreFPHILO2()+
            elevesPremierCycle.getNbreGPHILO1()+elevesPremierCycle.getNbreGPHILO2()));
        philoRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getNbreFPHILO1()+elevesPremierCycle.getNbreFPHILO2()+
            elevesPremierCycle.getNbreGPHILO1()+elevesPremierCycle.getNbreGPHILO2()));
        philoRow.getCell(14).setText("");

        XWPFTableRow svtRow = table.createRow();
        ensureCellCount(svtRow, 15);
        // nbreClasseRow.getCell(0).setText(currentValue);
        svtRow.getCell(1).setText("SVT");
        svtRow.getCell(2).setText(String.valueOf(elevesPremierCycle.getNbreFSVT1()));
        svtRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getNbreGSVT1()));
        svtRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getNbreFSVT1()+elevesPremierCycle.getNbreGSVT1()));
        svtRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getNbreFSVT1()+elevesPremierCycle.getNbreGSVT1()));
        svtRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getNbreFSVT2()));
        svtRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getNbreGSVT2()));
        svtRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getNbreFSVT2()+elevesPremierCycle.getNbreGSVT2()));
        svtRow.getCell(9).setText(String.valueOf(elevesPremierCycle.getNbreFSVT2()+elevesPremierCycle.getNbreGSVT2()));
        svtRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getNbreFSVT1()+elevesPremierCycle.getNbreFSVT2()));
        svtRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getNbreGSVT1()+elevesPremierCycle.getNbreGSVT2()));
        svtRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getNbreFSVT1()+elevesPremierCycle.getNbreFSVT2()+
            elevesPremierCycle.getNbreGSVT1()+elevesPremierCycle.getNbreGSVT2()));
        svtRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getNbreFSVT1()+elevesPremierCycle.getNbreFSVT2()+
            elevesPremierCycle.getNbreGSVT1()+elevesPremierCycle.getNbreGSVT2()));
        svtRow.getCell(14).setText("");

        XWPFTableRow totalRow = table.createRow();
        ensureCellCount(totalRow, 15);
        // nbreClasseRow.getCell(0).setText(currentValue);
        totalRow.getCell(1).setText("TOTAL ETABLISSEMENT");
        totalRow.getCell(2).setText(String.valueOf(elevesPremierCycle.getNbreF1()));
        totalRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getNbreG1()));
        totalRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getNbreF1()+elevesPremierCycle.getNbreG1()));
        totalRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getNbreF1()+elevesPremierCycle.getNbreG1()));
        totalRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getNbreF2()));
        totalRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getNbreG2()));
        totalRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getNbreF2()+elevesPremierCycle.getNbreG2()));
        totalRow.getCell(9).setText(String.valueOf(elevesPremierCycle.getNbreF2()+elevesPremierCycle.getNbreG2()));
        totalRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getNbreF1()+elevesPremierCycle.getNbreF2()));
        totalRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getNbreG1()+elevesPremierCycle.getNbreG2()));
        totalRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getNbreF1()+elevesPremierCycle.getNbreF2()+
            elevesPremierCycle.getNbreG1()+elevesPremierCycle.getNbreG2()));
        totalRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getNbreF1()+elevesPremierCycle.getNbreF2()+
            elevesPremierCycle.getNbreG1()+elevesPremierCycle.getNbreG2()));
        totalRow.getCell(14).setText("");

        for (XWPFTableRow row : table.getRows()) {
          for (XWPFTableCell cell : row.getTableCells()) {
            // Obtenez les propriétés de la cellule
            CTTcPr cellProperties = cell.getCTTc().addNewTcPr();
            CTTcBorders borders = cellProperties.addNewTcBorders();

            // Définir les bordures pour chaque côté
            setBorder(borders.addNewTop(), "000000", 8);
            setBorder(borders.addNewBottom(), "000000", 8);
            setBorder(borders.addNewLeft(), "000000", 8);
            setBorder(borders.addNewRight(), "000000", 8);
          }
        }
        mergeCellsVertically(table, 0, 3, table.getNumberOfRows()-1 );
        table.getRow(3).getCell(0).setText(currentValue);*/
      }

    //  mergeCellsVertically(table, 0, 3, table.getNumberOfRows()-1 );
    }

  private static void setBorder(CTBorder border, String color, int size) {
    border.setVal(STBorder.SINGLE);
    border.setSz(BigInteger.valueOf(size));
    border.setColor(color);
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
      BigDecimal bd = new BigDecimal(Double.toString(d));
      bd = bd.setScale(2, RoundingMode.HALF_UP); // Arrondi à deux chiffres après la virgule
      return bd.doubleValue();
    }

    public int getNombreDeclasseParNiveau(Long idEcole,String libelleAnnee ,String libelleTrimetre,String niveau){
      List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
      TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.libelleClasse) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee and b.niveau=:niveau " +
          "group by b.libelleClasse", NiveauDto.class);
      classeNiveauDtoList = q.setParameter("idEcole", idEcole)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimetre)
          .setParameter("niveau", niveau)
          . getResultList() ;
      return classeNiveauDtoList.size() ;
    }

}

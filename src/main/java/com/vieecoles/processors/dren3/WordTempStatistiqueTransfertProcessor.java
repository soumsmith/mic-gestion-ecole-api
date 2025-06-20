package com.vieecoles.processors.dren3;

import com.vieecoles.dto.BoursiersDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.StatistiqueTransfertDto;
import com.vieecoles.processors.dren3.services.StatistiqueBoursierServices;
import com.vieecoles.processors.dren3.services.StatistiqueTransfertsServices;
import com.vieecoles.steph.entities.Ecole;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

@ApplicationScoped
public class WordTempStatistiqueTransfertProcessor {
    @Inject
    StatistiqueTransfertsServices resultatsServices ;
  @Inject
  EntityManager em;

      public   void getResultatAffProcessor(XWPFDocument document ,
          Long idEcole ,String libelleAnnee , String libelleTrimetre,Long idAnnee) {
        StatistiqueTransfertDto elevesPremierCycle = new StatistiqueTransfertDto();

        try {

          elevesPremierCycle= resultatsServices.CalculRepartElevParAnnNaiss(idEcole,libelleAnnee,libelleTrimetre);
        } catch (Exception e) {
          e.printStackTrace();
        }



        String tableTitle = "CODE_POINT_TRANSFERT";
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


    private  void ajoutTableauDynamique(StatistiqueTransfertDto elevesPremierCycle,
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
          Ecole MyEcole;
          String lastValue = null;
          MyEcole= Ecole.findById(idEcole);
           currentValue = MyEcole.getLibelle();

          XWPFTableRow nbreClasseRow = table.createRow();
          ensureCellCount(nbreClasseRow, 16);
        nbreClasseRow.getCell(0).setText(currentValue);
        nbreClasseRow.getCell(1).setText(String.valueOf(elevesPremierCycle.getNbreClasse6()));
        nbreClasseRow.getCell(2).setText(String.valueOf(elevesPremierCycle.getNbreClasse5()));
        nbreClasseRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getNbreClasse4()));
        nbreClasseRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getNbreClasse3()));
        nbreClasseRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getNbreClasse6()+
            elevesPremierCycle.getNbreClasse5()+elevesPremierCycle.getNbreClasse4()+
            elevesPremierCycle.getNbreClasse3()));
        nbreClasseRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getNbreClasse2A()));
        nbreClasseRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getNbreClasse2C()));
        nbreClasseRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getNbreClasse1A()));
        nbreClasseRow.getCell(9).setText(String.valueOf(elevesPremierCycle.getNbreClasse1C()));
        nbreClasseRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getNbreClasse1D()));
        nbreClasseRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getNbreClasseTleA()));
        nbreClasseRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getNbreClasseTleC()));
        nbreClasseRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getNbreClasseTleD()));
        nbreClasseRow.getCell(14).setText(String.valueOf(elevesPremierCycle.getNbreClasse2A()+
            elevesPremierCycle.getNbreClasse2C()+elevesPremierCycle.getNbreClasse1A()+
            elevesPremierCycle.getNbreClasse1C()+elevesPremierCycle.getNbreClasse1D()+
            elevesPremierCycle.getNbreClasseTleA()+elevesPremierCycle.getNbreClasseTleC()+
            elevesPremierCycle.getNbreClasseTleD()));
        nbreClasseRow.getCell(15).setText(String.valueOf(elevesPremierCycle.getNbreClasseTotalEt()));



      }

      mergeCellsVertically(table, 0, 1, table.getNumberOfRows()-1 );
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

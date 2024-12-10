package com.vieecoles.processors.dren3;

import com.vieecoles.dto.ApprocheGenreDto;
import com.vieecoles.dto.BoursiersDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.processors.dren3.services.ApprocheParGenreServices;
import com.vieecoles.processors.dren3.services.StatistiqueBoursierServices;
import com.vieecoles.steph.entities.Ecole;
import java.math.BigDecimal;
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
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

@ApplicationScoped
public class WordTempStatistiqueBoursierProcessor {
    @Inject
    StatistiqueBoursierServices resultatsServices ;
  @Inject
  EntityManager em;

      public   void getResultatAffProcessor(XWPFDocument document ,
          Long idEcole ,String libelleAnnee , String libelleTrimetre,Long idAnnee) {
        BoursiersDto elevesPremierCycle = new BoursiersDto();

        try {

          elevesPremierCycle= resultatsServices.CalculRepartElevParAnnNaiss(idEcole,libelleAnnee,libelleTrimetre);
        } catch (Exception e) {
          e.printStackTrace();
        }



        String tableTitle = "CODE_BOURSIERS";
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


    private  void ajoutTableauDynamique(BoursiersDto elevesPremierCycle,
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
          ensureCellCount(nbreClasseRow, 33);
        nbreClasseRow.getCell(0).setText(currentValue);
        nbreClasseRow.getCell(1).setText("BE");
        nbreClasseRow.getCell(2).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier6G()));
        nbreClasseRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier6F()));
        nbreClasseRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier5G()));
        nbreClasseRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier5F()));
        nbreClasseRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier4G()));
        nbreClasseRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier4F()));
        nbreClasseRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier3G()));
        nbreClasseRow.getCell(9).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier3F()));
        nbreClasseRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier6G()+elevesPremierCycle.getEffectifBoursier5G()+
            elevesPremierCycle.getEffectifBoursier4G()+elevesPremierCycle.getEffectifBoursier3G()));
        nbreClasseRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier6F()+elevesPremierCycle.getEffectifBoursier5F()+
            elevesPremierCycle.getEffectifBoursier4F()+elevesPremierCycle.getEffectifBoursier3F()));
        nbreClasseRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier2AG()));
        nbreClasseRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier2AF()));
        nbreClasseRow.getCell(14).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier2CG()));
        nbreClasseRow.getCell(15).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier2CF()));
        nbreClasseRow.getCell(16).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier1AG()));
        nbreClasseRow.getCell(17).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier1AF()));
        nbreClasseRow.getCell(18).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier1CG()));
        nbreClasseRow.getCell(19).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier1CF()));
        nbreClasseRow.getCell(20).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier1DG()));
        nbreClasseRow.getCell(21).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier1DF()));
        nbreClasseRow.getCell(22).setText(String.valueOf(elevesPremierCycle.getEffectifBoursierTleAG()));
        nbreClasseRow.getCell(23).setText(String.valueOf(elevesPremierCycle.getEffectifBoursierTleAF()));
        nbreClasseRow.getCell(24).setText(String.valueOf(elevesPremierCycle.getEffectifBoursierTleCG()));
        nbreClasseRow.getCell(25).setText(String.valueOf(elevesPremierCycle.getEffectifBoursierTleCF()));
        nbreClasseRow.getCell(26).setText(String.valueOf(elevesPremierCycle.getEffectifBoursierTleDG()));
        nbreClasseRow.getCell(27).setText(String.valueOf(elevesPremierCycle.getEffectifBoursierTleDF()));
        nbreClasseRow.getCell(28).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier2AG()+
            elevesPremierCycle.getEffectifBoursier2CG()+
            elevesPremierCycle.getEffectifBoursier1AG()+elevesPremierCycle.getEffectifBoursier1CG()+
            elevesPremierCycle.getEffectifBoursier1DG()+elevesPremierCycle.getEffectifBoursierTleAG()+
            elevesPremierCycle.getEffectifBoursierTleCG()+elevesPremierCycle.getEffectifBoursierTleDG()));

        nbreClasseRow.getCell(29).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier2AF()+
            elevesPremierCycle.getEffectifBoursier2CF()+
            elevesPremierCycle.getEffectifBoursier1AF()+elevesPremierCycle.getEffectifBoursier1CF()+
            elevesPremierCycle.getEffectifBoursier1DF()+elevesPremierCycle.getEffectifBoursierTleAF()+
            elevesPremierCycle.getEffectifBoursierTleCF()+elevesPremierCycle.getEffectifBoursierTleDF()));
        nbreClasseRow.getCell(30).setText(String.valueOf(elevesPremierCycle.getEffectifBoursierTotalEtG()));
        nbreClasseRow.getCell(31).setText(String.valueOf(elevesPremierCycle.getEffectifBoursierTotalEtF()));
        nbreClasseRow.getCell(32).setText(String.valueOf(elevesPremierCycle.getEffectifBoursierTotalEtF()+elevesPremierCycle.getEffectifBoursierTotalEtG()));


        XWPFTableRow nbreClasse2Row = table.createRow();
        ensureCellCount(nbreClasse2Row, 33);
        nbreClasse2Row.getCell(0).setText(currentValue);
        nbreClasse2Row.getCell(1).setText("TOTAL");
        nbreClasse2Row.getCell(2).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier6G()));
        nbreClasse2Row.getCell(3).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier6F()));
        nbreClasse2Row.getCell(4).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier5G()));
        nbreClasse2Row.getCell(5).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier5F()));
        nbreClasse2Row.getCell(6).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier4G()));
        nbreClasse2Row.getCell(7).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier4F()));
        nbreClasse2Row.getCell(8).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier3G()));
        nbreClasse2Row.getCell(9).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier3F()));
        nbreClasse2Row.getCell(10).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier6G()+elevesPremierCycle.getEffectifBoursier5G()+
            elevesPremierCycle.getEffectifBoursier4G()+elevesPremierCycle.getEffectifBoursier3G()));
        nbreClasse2Row.getCell(11).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier6F()+elevesPremierCycle.getEffectifBoursier5F()+
            elevesPremierCycle.getEffectifBoursier4F()+elevesPremierCycle.getEffectifBoursier3F()));
        nbreClasse2Row.getCell(12).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier2AG()));
        nbreClasse2Row.getCell(13).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier2AF()));
        nbreClasse2Row.getCell(14).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier2CG()));
        nbreClasse2Row.getCell(15).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier2CF()));
        nbreClasse2Row.getCell(16).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier1AG()));
        nbreClasse2Row.getCell(17).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier1AF()));
        nbreClasse2Row.getCell(18).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier1CG()));
        nbreClasse2Row.getCell(19).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier1CF()));
        nbreClasse2Row.getCell(20).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier1DG()));
        nbreClasse2Row.getCell(21).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier1DF()));
        nbreClasse2Row.getCell(22).setText(String.valueOf(elevesPremierCycle.getEffectifBoursierTleAG()));
        nbreClasse2Row.getCell(23).setText(String.valueOf(elevesPremierCycle.getEffectifBoursierTleAF()));
        nbreClasse2Row.getCell(24).setText(String.valueOf(elevesPremierCycle.getEffectifBoursierTleCG()));
        nbreClasse2Row.getCell(25).setText(String.valueOf(elevesPremierCycle.getEffectifBoursierTleCF()));
        nbreClasse2Row.getCell(26).setText(String.valueOf(elevesPremierCycle.getEffectifBoursierTleDG()));
        nbreClasse2Row.getCell(27).setText(String.valueOf(elevesPremierCycle.getEffectifBoursierTleDF()));
        nbreClasse2Row.getCell(28).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier2AG()+
            elevesPremierCycle.getEffectifBoursier2CG()+
            elevesPremierCycle.getEffectifBoursier1AG()+elevesPremierCycle.getEffectifBoursier1CG()+
            elevesPremierCycle.getEffectifBoursier1DG()+elevesPremierCycle.getEffectifBoursierTleAG()+
            elevesPremierCycle.getEffectifBoursierTleCG()+elevesPremierCycle.getEffectifBoursierTleDG()));

        nbreClasse2Row.getCell(29).setText(String.valueOf(elevesPremierCycle.getEffectifBoursier2AF()+
            elevesPremierCycle.getEffectifBoursier2CF()+
            elevesPremierCycle.getEffectifBoursier1AF()+elevesPremierCycle.getEffectifBoursier1CF()+
            elevesPremierCycle.getEffectifBoursier1DF()+elevesPremierCycle.getEffectifBoursierTleAF()+
            elevesPremierCycle.getEffectifBoursierTleCF()+elevesPremierCycle.getEffectifBoursierTleDF()));
        nbreClasse2Row.getCell(30).setText(String.valueOf(elevesPremierCycle.getEffectifBoursierTotalEtG()));
        nbreClasse2Row.getCell(31).setText(String.valueOf(elevesPremierCycle.getEffectifBoursierTotalEtF()));
        nbreClasse2Row.getCell(32).setText(String.valueOf(elevesPremierCycle.getEffectifBoursierTotalEtF()+elevesPremierCycle.getEffectifBoursierTotalEtG()));





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

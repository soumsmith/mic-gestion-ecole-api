package com.vieecoles.processors.dren3;

import com.vieecoles.dto.ApprocheGenreDto;
import com.vieecoles.dto.EleveParAnneNaissNombreDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.processors.dren3.services.ApprocheParGenreServices;
import com.vieecoles.processors.dren3.services.RepartitionElevParAnNaissServices;
import com.vieecoles.steph.entities.Ecole;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

@ApplicationScoped
public class WordTempStatistiqueGenreProcessor {
    @Inject
    ApprocheParGenreServices resultatsServices ;
  @Inject
  EntityManager em;

      public   void getResultatAffProcessor(XWPFDocument document ,
          Long idEcole ,String libelleAnnee , String libelleTrimetre,Long idAnnee) {
        ApprocheGenreDto elevesPremierCycle = new ApprocheGenreDto();

        try {

          elevesPremierCycle= resultatsServices.CalculRepartElevParAnnNaiss(idEcole,libelleAnnee,libelleTrimetre);
        } catch (Exception e) {
          e.printStackTrace();
        }



        String tableTitle = "CODE_APPROCHE_GENRE";
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


    private  void ajoutTableauDynamique(ApprocheGenreDto elevesPremierCycle,
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
          ensureCellCount(nbreClasseRow, 19);
        nbreClasseRow.getCell(0).setText(currentValue);
        nbreClasseRow.getCell(1).setText("Nombre de Classes");
        nbreClasseRow.getCell(2).setText("");
        nbreClasseRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getNbreClasse6()));
        nbreClasseRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getNbreClasse5()));
        nbreClasseRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getNbreClasse4()));
        nbreClasseRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getNbreClasse3()));
        nbreClasseRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getNbreClasse6()+elevesPremierCycle.getNbreClasse5()+
            elevesPremierCycle.getNbreClasse4()+elevesPremierCycle.getNbreClasse3()));
        nbreClasseRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getNbreClasse2A()));
        nbreClasseRow.getCell(9).setText(String.valueOf(elevesPremierCycle.getNbreClasse2C()));
        nbreClasseRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getNbreClasse1A()));
        nbreClasseRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getNbreClasse1C()));
        nbreClasseRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getNbreClasse1D()));
        nbreClasseRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getNbreClasseTleA()));
        nbreClasseRow.getCell(14).setText(String.valueOf(elevesPremierCycle.getNbreClasseTleC()));
        nbreClasseRow.getCell(15).setText(String.valueOf(elevesPremierCycle.getNbreClasseTleD()));
        nbreClasseRow.getCell(16).setText(String.valueOf(elevesPremierCycle.getNbreClasse2A()+elevesPremierCycle.getNbreClasse2C()+
            elevesPremierCycle.getNbreClasse1A()+elevesPremierCycle.getNbreClasse1C()+elevesPremierCycle.getNbreClasse1D()+
            elevesPremierCycle.getNbreClasseTleA()+elevesPremierCycle.getNbreClasseTleC()+elevesPremierCycle.getNbreClasseTleD()));
        nbreClasseRow.getCell(17).setText(String.valueOf(elevesPremierCycle.getNbreClasseTotalEt()));
        nbreClasseRow.getCell(18).setText(String.valueOf(""));

        XWPFTableRow effeParClasseRow = table.createRow();
        ensureCellCount(effeParClasseRow, 19);
        //effeParClasseRow.getCell(0).setText(currentValue);
        effeParClasseRow.getCell(1).setText("Effectifs Par classe");
        effeParClasseRow.getCell(2).setText("");
        effeParClasseRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse6G()+elevesPremierCycle.getEffectifParClasse6F()));
        effeParClasseRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse5G()+elevesPremierCycle.getEffectifParClasse5F()));
        effeParClasseRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse4G()+elevesPremierCycle.getEffectifParClasse4F()));
        effeParClasseRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse3G()+elevesPremierCycle.getEffectifParClasse3F()));
        effeParClasseRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse6G()+elevesPremierCycle.getEffectifParClasse6F()+
            elevesPremierCycle.getEffectifParClasse5G()+elevesPremierCycle.getEffectifParClasse5F()+
            elevesPremierCycle.getEffectifParClasse4G()+elevesPremierCycle.getEffectifParClasse4F()+
            elevesPremierCycle.getEffectifParClasse3G()+elevesPremierCycle.getEffectifParClasse3F()));
        effeParClasseRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse2AG()+elevesPremierCycle.getEffectifParClasse2AF()));
        effeParClasseRow.getCell(9).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse2CG()+elevesPremierCycle.getEffectifParClasse2CF()));
        effeParClasseRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse1AG()+elevesPremierCycle.getEffectifParClasse1AF()));
        effeParClasseRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse1CG()+elevesPremierCycle.getEffectifParClasse1AF()));
        effeParClasseRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse1DG()+elevesPremierCycle.getEffectifParClasse1DF()));
        effeParClasseRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getEffectifParClasseTleAG()+elevesPremierCycle.getEffectifParClasseTleAF()));
        effeParClasseRow.getCell(14).setText(String.valueOf(elevesPremierCycle.getEffectifParClasseTleCG()+elevesPremierCycle.getEffectifParClasseTleCF()));
        effeParClasseRow.getCell(15).setText(String.valueOf(elevesPremierCycle.getEffectifParClasseTleDG()+elevesPremierCycle.getEffectifParClasseTleDF()));
        effeParClasseRow.getCell(16).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse2AG()+elevesPremierCycle.getEffectifParClasse2AF()+
        elevesPremierCycle.getEffectifParClasse2CG()+elevesPremierCycle.getEffectifParClasse2CF()+
        elevesPremierCycle.getEffectifParClasse1AG()+elevesPremierCycle.getEffectifParClasse1AF()+
        elevesPremierCycle.getEffectifParClasse1CG()+elevesPremierCycle.getEffectifParClasse1CF()+
        elevesPremierCycle.getEffectifParClasse1DG()+elevesPremierCycle.getEffectifParClasse1DF()+
        elevesPremierCycle.getEffectifParClasseTleAG()+elevesPremierCycle.getEffectifParClasseTleAF()+
        elevesPremierCycle.getEffectifParClasseTleCG()+elevesPremierCycle.getEffectifParClasseTleCF()+
        elevesPremierCycle.getEffectifParClasseTleDG()+elevesPremierCycle.getEffectifParClasseTleDF()));
    effeParClasseRow.getCell(17).setText(String.valueOf(elevesPremierCycle.getEffectifParClasseTotalEtG()+elevesPremierCycle.getEffectifParClasseTotalEtF()));
        effeParClasseRow.getCell(18).setText(String.valueOf(""));

        XWPFTableRow effeParClasseGRow = table.createRow();
        ensureCellCount(effeParClasseGRow, 19);
        //effeParClasseGRow.getCell(0).setText(currentValue);
        effeParClasseGRow.getCell(1).setText("Effectifs par Niveau et par Genre");
        effeParClasseGRow.getCell(2).setText("G");
        effeParClasseGRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse6G()));
        effeParClasseGRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse5G()));
        effeParClasseGRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse4G()));
        effeParClasseGRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse3G()));
        effeParClasseGRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse6G()+
            elevesPremierCycle.getEffectifParClasse5G()+
            elevesPremierCycle.getEffectifParClasse4G()+
            elevesPremierCycle.getEffectifParClasse3G()));
        effeParClasseGRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse2AG()));
        effeParClasseGRow.getCell(9).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse2CG()));
        effeParClasseGRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse1AG()));
        effeParClasseGRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse1CG()));
        effeParClasseGRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse1DG()));
        effeParClasseGRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getEffectifParClasseTleAG()));
        effeParClasseGRow.getCell(14).setText(String.valueOf(elevesPremierCycle.getEffectifParClasseTleCG()));
        effeParClasseGRow.getCell(15).setText(String.valueOf(elevesPremierCycle.getEffectifParClasseTleDG()));
        effeParClasseGRow.getCell(16).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse2AG()+
            elevesPremierCycle.getEffectifParClasse2CG()+
            elevesPremierCycle.getEffectifParClasse1AG()+
            elevesPremierCycle.getEffectifParClasse1CG()+
            elevesPremierCycle.getEffectifParClasse1DG()+
            elevesPremierCycle.getEffectifParClasseTleAG()+
            elevesPremierCycle.getEffectifParClasseTleCG()+
            elevesPremierCycle.getEffectifParClasseTleDG()));
        effeParClasseGRow.getCell(17).setText(String.valueOf(elevesPremierCycle.getEffectifParClasseTotalEtG()));
        effeParClasseGRow.getCell(18).setText(String.valueOf(""));

        XWPFTableRow effeParClasseFRow = table.createRow();
        ensureCellCount(effeParClasseFRow, 19);
     //   effeParClasseFRow.getCell(0).setText(currentValue);
        //effeParClasseFRow.getCell(1).setText("Effectifs par Niveau et par Genre");
        effeParClasseFRow.getCell(2).setText("F");
        effeParClasseFRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse6F()));
        effeParClasseFRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse5F()));
        effeParClasseFRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse4F()));
        effeParClasseFRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse3F()));
        effeParClasseFRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse6F()+
            elevesPremierCycle.getEffectifParClasse5F()+
            elevesPremierCycle.getEffectifParClasse4F()+
            elevesPremierCycle.getEffectifParClasse3F()));
        effeParClasseFRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse2AF()));
        effeParClasseFRow.getCell(9).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse2CF()));
        effeParClasseFRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse1AF()));
        effeParClasseFRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse1CF()));
        effeParClasseFRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse1DF()));
        effeParClasseFRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getEffectifParClasseTleAF()));
        effeParClasseFRow.getCell(14).setText(String.valueOf(elevesPremierCycle.getEffectifParClasseTleCF()));
        effeParClasseFRow.getCell(15).setText(String.valueOf(elevesPremierCycle.getEffectifParClasseTleDF()));
        effeParClasseFRow.getCell(16).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse2AF()+
            elevesPremierCycle.getEffectifParClasse2CF()+
            elevesPremierCycle.getEffectifParClasse1AF()+
            elevesPremierCycle.getEffectifParClasse1CF()+
            elevesPremierCycle.getEffectifParClasse1DF()+
            elevesPremierCycle.getEffectifParClasseTleAF()+
            elevesPremierCycle.getEffectifParClasseTleCF()+
            elevesPremierCycle.getEffectifParClasseTleDF()));
        effeParClasseFRow.getCell(17).setText(String.valueOf(elevesPremierCycle.getEffectifParClasseTotalEtF()));
        effeParClasseFRow.getCell(18).setText(String.valueOf(""));

        XWPFTableRow effeParClasseTRow = table.createRow();
        ensureCellCount(effeParClasseTRow, 19);
        //effeParClasseTRow.getCell(0).setText(currentValue);
       // effeParClasseTRow.getCell(1).setText("Effectifs par Niveau et par Genre");
        effeParClasseTRow.getCell(2).setText("T");
        effeParClasseTRow.getCell(3).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse6G()+elevesPremierCycle.getEffectifParClasse6F()));
        effeParClasseTRow.getCell(4).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse5G()+elevesPremierCycle.getEffectifParClasse5F()));
        effeParClasseTRow.getCell(5).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse4G()+elevesPremierCycle.getEffectifParClasse4F()));
        effeParClasseTRow.getCell(6).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse3G()+elevesPremierCycle.getEffectifParClasse3F()));
        effeParClasseTRow.getCell(7).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse6G()+elevesPremierCycle.getEffectifParClasse6F()+
            elevesPremierCycle.getEffectifParClasse5G()+elevesPremierCycle.getEffectifParClasse5F()+
            elevesPremierCycle.getEffectifParClasse4G()+elevesPremierCycle.getEffectifParClasse4F()+
            elevesPremierCycle.getEffectifParClasse3G()+elevesPremierCycle.getEffectifParClasse3F()));
        effeParClasseTRow.getCell(8).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse2AG()+elevesPremierCycle.getEffectifParClasse2AF()));
        effeParClasseTRow.getCell(9).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse2CG()+elevesPremierCycle.getEffectifParClasse2CF()));
        effeParClasseTRow.getCell(10).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse1AG()+elevesPremierCycle.getEffectifParClasse1AF()));
        effeParClasseTRow.getCell(11).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse1CG()+elevesPremierCycle.getEffectifParClasse1AF()));
        effeParClasseTRow.getCell(12).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse1DG()+elevesPremierCycle.getEffectifParClasse1DF()));
        effeParClasseTRow.getCell(13).setText(String.valueOf(elevesPremierCycle.getEffectifParClasseTleAG()+elevesPremierCycle.getEffectifParClasseTleAF()));
        effeParClasseTRow.getCell(14).setText(String.valueOf(elevesPremierCycle.getEffectifParClasseTleCG()+elevesPremierCycle.getEffectifParClasseTleCF()));
        effeParClasseTRow.getCell(15).setText(String.valueOf(elevesPremierCycle.getEffectifParClasseTleDG()+elevesPremierCycle.getEffectifParClasseTleDF()));
        effeParClasseTRow.getCell(16).setText(String.valueOf(elevesPremierCycle.getEffectifParClasse2AG()+elevesPremierCycle.getEffectifParClasse2AF()+
            elevesPremierCycle.getEffectifParClasse2CG()+elevesPremierCycle.getEffectifParClasse2CF()+
            elevesPremierCycle.getEffectifParClasse1AG()+elevesPremierCycle.getEffectifParClasse1AF()+
            elevesPremierCycle.getEffectifParClasse1CG()+elevesPremierCycle.getEffectifParClasse1CF()+
            elevesPremierCycle.getEffectifParClasse1DG()+elevesPremierCycle.getEffectifParClasse1DF()+
            elevesPremierCycle.getEffectifParClasseTleAG()+elevesPremierCycle.getEffectifParClasseTleAF()+
            elevesPremierCycle.getEffectifParClasseTleCG()+elevesPremierCycle.getEffectifParClasseTleCF()+
            elevesPremierCycle.getEffectifParClasseTleDG()+elevesPremierCycle.getEffectifParClasseTleDF()));
        effeParClasseTRow.getCell(17).setText(String.valueOf(elevesPremierCycle.getEffectifParClasseTotalEtG()+elevesPremierCycle.getEffectifParClasseTotalEtF()));
        effeParClasseTRow.getCell(18).setText(String.valueOf(""));

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
        mergeCellsVertically(table, 1, table.getNumberOfRows() - 3, table.getNumberOfRows() -1);
      }

      mergeCellsVertically(table, 0, 3, table.getNumberOfRows()-1 );
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

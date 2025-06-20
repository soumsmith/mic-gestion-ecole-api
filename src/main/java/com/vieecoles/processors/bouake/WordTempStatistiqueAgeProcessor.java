package com.vieecoles.processors.bouake;

import com.vieecoles.dto.EleveParAnneNaissNombreDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.processors.bouake.services.RepartitionElevParAnNaissServices;
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
public class WordTempStatistiqueAgeProcessor {
    @Inject
    RepartitionElevParAnNaissServices resultatsServices ;
  @Inject
  com.vieecoles.processors.dren3.services.RepartitionElevParAnNaissServices totalresultatsServices ;
  @Inject
  EntityManager em;

      public   void getResultatAffProcessor(XWPFDocument document ,
          Long idEcole ,String libelleAnnee , String libelleTrimetre) {


        EleveParAnneNaissNombreDto detailsBull6 = new EleveParAnneNaissNombreDto();
        EleveParAnneNaissNombreDto detailstotal = new EleveParAnneNaissNombreDto();

        detailstotal=totalresultatsServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimetre)  ;

        try {
          detailsBull6= resultatsServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimetre,1)  ;
          System.out.println("classeNiveauDtoList Sortie");
        } catch (Exception e) {
          e.printStackTrace();
        }
        // Cinquième

        EleveParAnneNaissNombreDto detailsBull5 = new EleveParAnneNaissNombreDto();
        try {
          detailsBull5= resultatsServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimetre,2)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Quatrieme

        EleveParAnneNaissNombreDto detailsBull4 = new EleveParAnneNaissNombreDto();
        try {
          detailsBull4= resultatsServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimetre,3)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }


        // Troixième

        EleveParAnneNaissNombreDto detailsBull3 = new EleveParAnneNaissNombreDto();
        try {
          detailsBull3= resultatsServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimetre,4)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }
        // Seconde A
        EleveParAnneNaissNombreDto detailsBull2NDA = new EleveParAnneNaissNombreDto();
        try {
          detailsBull2NDA= resultatsServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimetre,5)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Seconde C
        EleveParAnneNaissNombreDto detailsBull2NDC = new EleveParAnneNaissNombreDto();
        try {
          detailsBull2NDC= resultatsServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimetre,6)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Premiere A
        EleveParAnneNaissNombreDto detailsBull1EREA = new EleveParAnneNaissNombreDto();
        try {
          detailsBull1EREA= resultatsServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimetre,7)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Premiere C
        EleveParAnneNaissNombreDto detailsBull1EREC = new EleveParAnneNaissNombreDto();
        try {
          detailsBull1EREC = resultatsServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimetre,8)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Premiere D
        EleveParAnneNaissNombreDto detailsBull1ERED = new EleveParAnneNaissNombreDto();
        try {
          detailsBull1ERED = resultatsServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimetre,9)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Terminale A
        EleveParAnneNaissNombreDto detailsBullTLEA = new EleveParAnneNaissNombreDto();
        try {
          detailsBullTLEA = resultatsServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimetre,10)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Terminale A1
        EleveParAnneNaissNombreDto detailsBullTLEA1 = new EleveParAnneNaissNombreDto();
        try {
          detailsBullTLEA1 = resultatsServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimetre,11)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Terminale A2
        EleveParAnneNaissNombreDto detailsBullTLEA2 = new EleveParAnneNaissNombreDto();
        try {
          detailsBullTLEA2 = resultatsServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimetre,12)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Terminale C
        EleveParAnneNaissNombreDto detailsBullTLEC = new EleveParAnneNaissNombreDto();
        try {
          detailsBullTLEC = resultatsServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimetre,13)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Terminale D
        EleveParAnneNaissNombreDto detailsBullTLED = new EleveParAnneNaissNombreDto();
        try {
          detailsBullTLED = resultatsServices.CalculRepartElevParAnnNaiss(idEcole ,libelleAnnee,libelleTrimetre,14)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }



        XWPFTable targetTable = null;
        // 3. Gestion des tableaux dynamiques
        // Sixième
        try{
          String tableTitle = "CODE_STATISTIQUE_AGE";

          for (int i = 0; i < document.getBodyElements().size(); i++) {
            IBodyElement element = document.getBodyElements().get(i);
            if (element.getElementType() == BodyElementType.PARAGRAPH) {
              XWPFParagraph paragraph = (XWPFParagraph) element;
              if (paragraph.getText().trim().equalsIgnoreCase(tableTitle)) {
                while(paragraph.getRuns().size() > 0) {
                  paragraph.removeRun(0);
                }
                // Le tableau devrait suivre immédiatement le titre
                if (i + 1 < document.getBodyElements().size() &&
                    document.getBodyElements().get(i + 1).getElementType() == BodyElementType.TABLE) {
                  targetTable = (XWPFTable) document.getBodyElements().get(i + 1);
                }
                break;
              }
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }


        ///XWPFTable table = document.getTableArray(6);
        try {
          if(detailsBull6 != null)
            ajoutTableauDynamique(detailsBull6,targetTable,"6ème");
        } catch (RuntimeException e) {
          e.printStackTrace();
        }
        // Cinquième
        // XWPFTable tableCinquieme = document.getTableArray(6);
        try {
          if(detailsBull5 != null)
            ajoutTableauDynamique(detailsBull5,targetTable,"5ème");
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Quatrieme

        try {
          if(detailsBull4 != null)
            ajoutTableauDynamique(detailsBull4,targetTable,"4ème");
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Troixième

        try {
          if(detailsBull3!= null)
            ajoutTableauDynamique(detailsBull3,targetTable,"3ème");
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // SecondeA
        // XWPFTable table2NDA = document.getTableArray(10);
        try {
          if(detailsBull2NDA != null)
            ajoutTableauDynamique(detailsBull2NDA,targetTable,"2ndeA");
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // SecondeC

        try {
          if(detailsBull2NDC!= null)
            ajoutTableauDynamique(detailsBull2NDC,targetTable,"2ndeC");
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // PremiereA

        try {
          if(detailsBull1EREA!= null)
            ajoutTableauDynamique(detailsBull1EREA,targetTable,"1ereA");
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // PremiereC

        try {
          if(detailsBull1EREC!= null)
            ajoutTableauDynamique(detailsBull1EREC,targetTable,"1ereC");
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // PremiereD

        try {
          if(detailsBull1ERED!= null)
            ajoutTableauDynamique(detailsBull1ERED,targetTable,"1ereD");
        } catch (RuntimeException e) {
          e.printStackTrace();
        }


        // Terminale A

        try {
          if(detailsBullTLEA!= null)
            ajoutTableauDynamique(detailsBullTLEA,targetTable,"TleA");
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Terminale A1

        try {
          if(detailsBullTLEA1!= null)
            ajoutTableauDynamique(detailsBullTLEA1,targetTable,"TleA1");
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Terminale A2

        try {
          if(detailsBullTLEA2!= null)
            ajoutTableauDynamique(detailsBullTLEA2,targetTable,"TleA2");
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Terminale C

        try {
          if(detailsBullTLEC!= null)
            ajoutTableauDynamique(detailsBullTLEC,targetTable,"TleC");
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Terminale D

        try {
          if(detailsBullTLED!= null)
            ajoutTableauDynamique(detailsBullTLED,targetTable,"TleD");
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        try {
          if(detailstotal!= null)
            ajoutLigneTotal(detailstotal,targetTable,"TOTAL");
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


    private  void ajoutTableauDynamique(EleveParAnneNaissNombreDto elevesPremierCycle,
                                              XWPFTable table,String niveau) {


      if(elevesPremierCycle!=null){


        Long total01G=elevesPremierCycle.getNbre01G();
        Long total98G=elevesPremierCycle.getNbre98G();
        Long total99G=elevesPremierCycle.getNbre99G();
        Long total2000G=elevesPremierCycle.getNbre2000G();
        Long total2001G=elevesPremierCycle.getNbre2001G();
        Long total2002G=elevesPremierCycle.getNbre2002G();
        Long total2003G=elevesPremierCycle.getNbre2003G();
        Long total2004G=elevesPremierCycle.getNbre2004G();
        Long total2005G=elevesPremierCycle.getNbre2005G();
        Long total2006G=elevesPremierCycle.getNbre2006G();
        Long total2007G=elevesPremierCycle.getNbre2007G();
        Long total2008G=elevesPremierCycle.getNbre2008G();
        Long total2009G=elevesPremierCycle.getNbre2009G();
        Long total2010G=elevesPremierCycle.getNbre2010G();
        Long total2011G=elevesPremierCycle.getNbre2011G();
        Long total2012G=elevesPremierCycle.getNbre2012G();
        Long total2013G=elevesPremierCycle.getNbre2013G();
        Long total2014G=elevesPremierCycle.getNbre2014G();
        Long total2015G=elevesPremierCycle.getNbre2015G();
        Long total2016G=elevesPremierCycle.getNbre2016G();
        Long total2020G=elevesPremierCycle.getNbre2020G();
        Long total2021G=elevesPremierCycle.getNbre2021G();
        Long TotalGeneralG=total01G+total98G+total99G+total2000G+total2001G+total2002G+total2003G+total2004G+total2005G+total2006G+total2007G+
            total2008G+total2009G+total2010G+total2011G+total2012G+total2013G+total2014G+total2015G+total2016G+total2020G+total2021G;



        Long total01F=elevesPremierCycle.getNbre01F();
        Long total98F=elevesPremierCycle.getNbre98F();
        Long total99F=elevesPremierCycle.getNbre99F();
        Long total2000F=elevesPremierCycle.getNbre2000F();
        Long total2001F=elevesPremierCycle.getNbre2001F();
        Long total2002F=elevesPremierCycle.getNbre2002F();
        Long total2003F=elevesPremierCycle.getNbre2003F();
        Long total2004F=elevesPremierCycle.getNbre2004F();
        Long total2005F=elevesPremierCycle.getNbre2005F();
        Long total2006F=elevesPremierCycle.getNbre2006F();
        Long total2007F=elevesPremierCycle.getNbre2007F();
        Long total2008F=elevesPremierCycle.getNbre2008F();
        Long total2009F=elevesPremierCycle.getNbre2009F();
        Long total2010F=elevesPremierCycle.getNbre2010F();
        Long total2011F=elevesPremierCycle.getNbre2011F();
        Long total2012F=elevesPremierCycle.getNbre2012F();
        Long total2013F=elevesPremierCycle.getNbre2013F();
        Long total2014F=elevesPremierCycle.getNbre2014F();
        Long total2015F=elevesPremierCycle.getNbre2015F();
        Long total2016F=elevesPremierCycle.getNbre2016F();
        Long total2020F=elevesPremierCycle.getNbre2020F();
        Long total2021F=elevesPremierCycle.getNbre2021F();
        Long TotalGeneralF=total01F+total98F+total99F+total2000F+total2001F+total2002F+total2003F+total2004F+
            total2005F+total2006F+total2007F+
            total2008F+total2009F+total2010F+total2011F+total2012F+total2013F+total2014F+total2015F+total2016F+total2020F+total2021F;

        //


        Long total01=elevesPremierCycle.getNbre01F()+elevesPremierCycle.getNbre01G();
        Long total98=elevesPremierCycle.getNbre99F()+elevesPremierCycle.getNbre98G();
        Long total99=elevesPremierCycle.getNbre99F()+elevesPremierCycle.getNbre99G();
        Long total2000=elevesPremierCycle.getNbre2000F()+elevesPremierCycle.getNbre2000G();
        Long total2001=elevesPremierCycle.getNbre2001F()+elevesPremierCycle.getNbre2001G();
        Long total2002=elevesPremierCycle.getNbre2002F()+elevesPremierCycle.getNbre2002G();
        Long total2003=elevesPremierCycle.getNbre2003F()+elevesPremierCycle.getNbre2003G();
        Long total2004=elevesPremierCycle.getNbre2004F()+elevesPremierCycle.getNbre2004G();
        Long total2005=elevesPremierCycle.getNbre2005F()+elevesPremierCycle.getNbre2005G();
        Long total2006=elevesPremierCycle.getNbre2006F()+elevesPremierCycle.getNbre2006G();
        Long total2007=elevesPremierCycle.getNbre2007F()+elevesPremierCycle.getNbre2007G();
        Long total2008=elevesPremierCycle.getNbre2008F()+elevesPremierCycle.getNbre2008G();
        Long total2009=elevesPremierCycle.getNbre2009F()+elevesPremierCycle.getNbre2009G();
        Long total2010=elevesPremierCycle.getNbre2010F()+elevesPremierCycle.getNbre2010G();
        Long total2011=elevesPremierCycle.getNbre2011F()+elevesPremierCycle.getNbre2011G();
        Long total2012=elevesPremierCycle.getNbre2012F()+elevesPremierCycle.getNbre2012G();
        Long total2013=elevesPremierCycle.getNbre2013F()+elevesPremierCycle.getNbre2013G();
        Long total2014=elevesPremierCycle.getNbre2014F()+elevesPremierCycle.getNbre2014G();
        Long total2015=elevesPremierCycle.getNbre2015F()+elevesPremierCycle.getNbre2015G();
        Long total2016=elevesPremierCycle.getNbre2016F()+elevesPremierCycle.getNbre2016G();
        Long total2020=elevesPremierCycle.getNbre2020F()+elevesPremierCycle.getNbre2020G();
        Long total2021=elevesPremierCycle.getNbre2021F()+elevesPremierCycle.getNbre2021G();
        Long TotalGeneral=total01+total98+total99+total2000+total2001+total2002+total2003+total2004+total2005+total2006+total2007+
            total2008+total2009+total2010+total2011+total2012+total2013+total2014+total2015+total2016+total2020+total2021;


        XWPFTableRow totalRow = table.createRow();
        ensureCellCount(totalRow, 19);
        totalRow.getCell(0).setText(niveau);
        totalRow.getCell(1).setText(String.valueOf(safeValue(total01)));
        totalRow.getCell(2).setText(String.valueOf(safeValue(total98)));
        totalRow.getCell(3).setText(String.valueOf(safeValue(total99)));
        totalRow.getCell(4).setText(String.valueOf(safeValue(total2000)));
        totalRow.getCell(5).setText(String.valueOf(safeValue(total2001)));
        totalRow.getCell(6).setText(String.valueOf(safeValue(total2002)));
        totalRow.getCell(7).setText(String.valueOf(safeValue(total2003)));
        totalRow.getCell(8).setText(String.valueOf(safeValue(total2004)));
        totalRow.getCell(9).setText(String.valueOf(safeValue(total2005)));
        totalRow.getCell(10).setText(String.valueOf(safeValue(total2006)));
        totalRow.getCell(11).setText(String.valueOf(safeValue(total2007)));
        totalRow.getCell(12).setText(String.valueOf(safeValue(total2008)));
        totalRow.getCell(13).setText(String.valueOf(safeValue(total2009)));
        totalRow.getCell(14).setText(String.valueOf(safeValue(total2010)));
        totalRow.getCell(15).setText(String.valueOf(safeValue(total2011)));
        totalRow.getCell(16).setText(String.valueOf(safeValue(total2012)));
        totalRow.getCell(17).setText(String.valueOf(safeValue(total2013)));
        totalRow.getCell(18).setText(String.valueOf(safeValue(total2014)));
        //totalRow.getCell(19).setText(String.valueOf(safeValue(TotalGeneral)));

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

      }

     // mergeCellsVertically(table, 0, 1, table.getNumberOfRows()-1 );
    }

  private  void ajoutLigneTotal(EleveParAnneNaissNombreDto elevesPremierCycle,
                                      XWPFTable table,String niveau) {


    if(elevesPremierCycle!=null){


      Long total01G=elevesPremierCycle.getNbre01G();
      Long total98G=elevesPremierCycle.getNbre98G();
      Long total99G=elevesPremierCycle.getNbre99G();
      Long total2000G=elevesPremierCycle.getNbre2000G();
      Long total2001G=elevesPremierCycle.getNbre2001G();
      Long total2002G=elevesPremierCycle.getNbre2002G();
      Long total2003G=elevesPremierCycle.getNbre2003G();
      Long total2004G=elevesPremierCycle.getNbre2004G();
      Long total2005G=elevesPremierCycle.getNbre2005G();
      Long total2006G=elevesPremierCycle.getNbre2006G();
      Long total2007G=elevesPremierCycle.getNbre2007G();
      Long total2008G=elevesPremierCycle.getNbre2008G();
      Long total2009G=elevesPremierCycle.getNbre2009G();
      Long total2010G=elevesPremierCycle.getNbre2010G();
      Long total2011G=elevesPremierCycle.getNbre2011G();
      Long total2012G=elevesPremierCycle.getNbre2012G();
      Long total2013G=elevesPremierCycle.getNbre2013G();
      Long total2014G=elevesPremierCycle.getNbre2014G();
      Long total2015G=elevesPremierCycle.getNbre2015G();
      Long total2016G=elevesPremierCycle.getNbre2016G();
      Long total2020G=elevesPremierCycle.getNbre2020G();
      Long total2021G=elevesPremierCycle.getNbre2021G();
      Long TotalGeneralG=total01G+total98G+total99G+total2000G+total2001G+total2002G+total2003G+total2004G+total2005G+total2006G+total2007G+
          total2008G+total2009G+total2010G+total2011G+total2012G+total2013G+total2014G+total2015G+total2016G+total2020G+total2021G;



      Long total01F=elevesPremierCycle.getNbre01F();
      Long total98F=elevesPremierCycle.getNbre98F();
      Long total99F=elevesPremierCycle.getNbre99F();
      Long total2000F=elevesPremierCycle.getNbre2000F();
      Long total2001F=elevesPremierCycle.getNbre2001F();
      Long total2002F=elevesPremierCycle.getNbre2002F();
      Long total2003F=elevesPremierCycle.getNbre2003F();
      Long total2004F=elevesPremierCycle.getNbre2004F();
      Long total2005F=elevesPremierCycle.getNbre2005F();
      Long total2006F=elevesPremierCycle.getNbre2006F();
      Long total2007F=elevesPremierCycle.getNbre2007F();
      Long total2008F=elevesPremierCycle.getNbre2008F();
      Long total2009F=elevesPremierCycle.getNbre2009F();
      Long total2010F=elevesPremierCycle.getNbre2010F();
      Long total2011F=elevesPremierCycle.getNbre2011F();
      Long total2012F=elevesPremierCycle.getNbre2012F();
      Long total2013F=elevesPremierCycle.getNbre2013F();
      Long total2014F=elevesPremierCycle.getNbre2014F();
      Long total2015F=elevesPremierCycle.getNbre2015F();
      Long total2016F=elevesPremierCycle.getNbre2016F();
      Long total2020F=elevesPremierCycle.getNbre2020F();
      Long total2021F=elevesPremierCycle.getNbre2021F();
      Long TotalGeneralF=total01F+total98F+total99F+total2000F+total2001F+total2002F+total2003F+total2004F+
          total2005F+total2006F+total2007F+
          total2008F+total2009F+total2010F+total2011F+total2012F+total2013F+total2014F+total2015F+total2016F+total2020F+total2021F;

      //


      Long total01=elevesPremierCycle.getNbre01F()+elevesPremierCycle.getNbre01G();
      Long total98=elevesPremierCycle.getNbre99F()+elevesPremierCycle.getNbre98G();
      Long total99=elevesPremierCycle.getNbre99F()+elevesPremierCycle.getNbre99G();
      Long total2000=elevesPremierCycle.getNbre2000F()+elevesPremierCycle.getNbre2000G();
      Long total2001=elevesPremierCycle.getNbre2001F()+elevesPremierCycle.getNbre2001G();
      Long total2002=elevesPremierCycle.getNbre2002F()+elevesPremierCycle.getNbre2002G();
      Long total2003=elevesPremierCycle.getNbre2003F()+elevesPremierCycle.getNbre2003G();
      Long total2004=elevesPremierCycle.getNbre2004F()+elevesPremierCycle.getNbre2004G();
      Long total2005=elevesPremierCycle.getNbre2005F()+elevesPremierCycle.getNbre2005G();
      Long total2006=elevesPremierCycle.getNbre2006F()+elevesPremierCycle.getNbre2006G();
      Long total2007=elevesPremierCycle.getNbre2007F()+elevesPremierCycle.getNbre2007G();
      Long total2008=elevesPremierCycle.getNbre2008F()+elevesPremierCycle.getNbre2008G();
      Long total2009=elevesPremierCycle.getNbre2009F()+elevesPremierCycle.getNbre2009G();
      Long total2010=elevesPremierCycle.getNbre2010F()+elevesPremierCycle.getNbre2010G();
      Long total2011=elevesPremierCycle.getNbre2011F()+elevesPremierCycle.getNbre2011G();
      Long total2012=elevesPremierCycle.getNbre2012F()+elevesPremierCycle.getNbre2012G();
      Long total2013=elevesPremierCycle.getNbre2013F()+elevesPremierCycle.getNbre2013G();
      Long total2014=elevesPremierCycle.getNbre2014F()+elevesPremierCycle.getNbre2014G();
      Long total2015=elevesPremierCycle.getNbre2015F()+elevesPremierCycle.getNbre2015G();
      Long total2016=elevesPremierCycle.getNbre2016F()+elevesPremierCycle.getNbre2016G();
      Long total2020=elevesPremierCycle.getNbre2020F()+elevesPremierCycle.getNbre2020G();
      Long total2021=elevesPremierCycle.getNbre2021F()+elevesPremierCycle.getNbre2021G();
      Long TotalGeneral=total01+total98+total99+total2000+total2001+total2002+total2003+total2004+total2005+total2006+total2007+
          total2008+total2009+total2010+total2011+total2012+total2013+total2014+total2015+total2016+total2020+total2021;


      XWPFTableRow totalRow = table.createRow();
      ensureCellCount(totalRow, 19);
      totalRow.getCell(0).setText(niveau);
      totalRow.getCell(1).setText(String.valueOf(safeValue(total01)));
      totalRow.getCell(2).setText(String.valueOf(safeValue(total98)));
      totalRow.getCell(3).setText(String.valueOf(safeValue(total99)));
      totalRow.getCell(4).setText(String.valueOf(safeValue(total2000)));
      totalRow.getCell(5).setText(String.valueOf(safeValue(total2001)));
      totalRow.getCell(6).setText(String.valueOf(safeValue(total2002)));
      totalRow.getCell(7).setText(String.valueOf(safeValue(total2003)));
      totalRow.getCell(8).setText(String.valueOf(safeValue(total2004)));
      totalRow.getCell(9).setText(String.valueOf(safeValue(total2005)));
      totalRow.getCell(10).setText(String.valueOf(safeValue(total2006)));
      totalRow.getCell(11).setText(String.valueOf(safeValue(total2007)));
      totalRow.getCell(12).setText(String.valueOf(safeValue(total2008)));
      totalRow.getCell(13).setText(String.valueOf(safeValue(total2009)));
      totalRow.getCell(14).setText(String.valueOf(safeValue(total2010)));
      totalRow.getCell(15).setText(String.valueOf(safeValue(total2011)));
      totalRow.getCell(16).setText(String.valueOf(safeValue(total2012)));
      totalRow.getCell(17).setText(String.valueOf(safeValue(total2013)));
      totalRow.getCell(18).setText(String.valueOf(safeValue(total2014)));
      //totalRow.getCell(19).setText(String.valueOf(safeValue(TotalGeneral)));

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

    }

    // mergeCellsVertically(table, 0, 1, table.getNumberOfRows()-1 );
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

  private String safeValue(Long value) {
    return value != null ? String.valueOf(value) : "0";
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

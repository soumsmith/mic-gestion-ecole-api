package com.vieecoles.processors.dren3;

import com.vieecoles.dto.EffectifNiveauGenreNationaliteDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.RecapResultatsElevesAffeEtNonAffDto;
import com.vieecoles.processors.dren3.services.RecapitulatifStatistiqueServices;
import com.vieecoles.processors.dren3.services.RecapitulatifStatistiqueServices1;
import com.vieecoles.processors.dren3.services.RecapitulatifStatistiqueServicesCycle2;
import com.vieecoles.processors.dren3.services.resultatsRecapAffEtNonAffCycle1Services;
import com.vieecoles.processors.dren3.services.resultatsRecapAffEtNonAffCycle2Services;
import com.vieecoles.processors.dren3.services.resultatsRecapAffEtNonAffCycleServices;
import com.vieecoles.services.etats.resultatsRecapAffEtNonAffServices;
import com.vieecoles.services.etats.resultatsRecapServices;
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
public class WordTempStatistiqueNationaliteProcessor {
    @Inject
    resultatsRecapAffEtNonAffCycleServices resultatsServices ;
  @Inject
  RecapitulatifStatistiqueServices1 resultatsServices1 ;
  @Inject
  RecapitulatifStatistiqueServicesCycle2 resultatsServices2 ;
    @Inject
    resultatsRecapServices resultatsRecapServices ;
  @Inject
  RecapitulatifStatistiqueServices resultatsServices3 ;
  @Inject
  EntityManager em;

      public   void getResultatAffProcessor(XWPFDocument document ,
          Long idEcole ,String libelleAnnee , String libelleTrimetre,Long idAnnee) {
        List<EffectifNiveauGenreNationaliteDto> elevesPremierCycle = new ArrayList<>();
        List<EffectifNiveauGenreNationaliteDto> elevesSecondCycle = new ArrayList<>();
        List<EffectifNiveauGenreNationaliteDto> elevesGeneral = new ArrayList<>();
        int nombreClasseCycle1 = 0;  int nombreClasseCycle2=0;

        try {

          elevesPremierCycle= resultatsServices1.getEffectifNiveauGenre(idEcole ,idAnnee,libelleAnnee,libelleTrimetre);

         elevesSecondCycle= resultatsServices2.getEffectifNiveauGenre(idEcole ,idAnnee,libelleAnnee,libelleTrimetre);
          elevesGeneral=resultatsServices3.getEffectifNiveauGenre(idEcole ,idAnnee,libelleAnnee,libelleTrimetre);
         // System.out.println("elevesSecondycle "+elevesSecondCycle.size());
        } catch (Exception e) {
          e.printStackTrace();
        }
        // Avoir nombre de classe
        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.libelleClasse) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee and b.ordreNiveau<5 " +
            "group by b.libelleClasse", NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
            .setParameter("annee", libelleAnnee)
            .setParameter("periode", libelleTrimetre)
            . getResultList() ;

        if(!classeNiveauDtoList.isEmpty()){
          nombreClasseCycle1= classeNiveauDtoList.size();
        }
        List<NiveauDto> classeNiveauDtoList2 = new ArrayList<>() ;
        TypedQuery<NiveauDto> qi = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.libelleClasse) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee and b.ordreNiveau>=5 " +
            "group by b.libelleClasse", NiveauDto.class);
        classeNiveauDtoList2 = qi.setParameter("idEcole", idEcole)
            .setParameter("annee", libelleAnnee)
            .setParameter("periode", libelleTrimetre)
            . getResultList() ;

        if(!classeNiveauDtoList2.isEmpty()){
          nombreClasseCycle2= classeNiveauDtoList2.size();
        }
       System.out.println("elevesPremierCycle "+elevesPremierCycle.size());
        System.out.println("elevesSecondCycle "+elevesSecondCycle.size());

        String tableTitle = "CODE_NIVEAU_NATIONALITE";
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
         ajoutTableauDynamique(elevesPremierCycle,elevesSecondCycle,targetTable, idEcole,libelleAnnee,libelleTrimetre,elevesGeneral);
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


    private  void ajoutTableauDynamique(List<EffectifNiveauGenreNationaliteDto> elevesPremierCycle, List<EffectifNiveauGenreNationaliteDto> elevesSecondCycle,
                                              XWPFTable table,Long idEcole ,String libelleAnnee, String trimestre,List<EffectifNiveauGenreNationaliteDto> elevesGeneral) {
      String lastNiveau = null;
      int startRowIndex = -1;
      int rowIndex = 1;

      long nombMoySup10Cycle1 =0l ; long nombMoyInf10Cycle1 =0l ;long nombMoyInf8_5Cycle1 =0l;
      long effectifClasseCycle1=0l ;  long effNonClassCycle1=0l;
      long nombMoySup10Cycle1G =0l ; long nombMoyInf10Cycle1G =0l ;long nombMoyInf8_5Cycle1G =0l;
      long effectifClasseCycle1G=0l ;  long effNonClassCycle1G=0l;

      long nombMoySup10Cycle1F =0l ; long nombMoyInf10Cycle1F =0l ;long nombMoyInf8_5Cycle1F =0l;
      long effectifClasseCycle1F=0l ;  long effNonClassCycle1F=0l;

      double pourSup10Cycle1= 0d,pourInf10Cycle1 = 0d,pourInf8_5Cycle1 = 0d;
      double pourSup10Cycle1G= 0d,pourInf10Cycle1G = 0d,pourInf8_5Cycle1G = 0d;
      double pourSup10Cycle1F= 0d,pourInf10Cycle1F = 0d,pourInf8_5Cycle1F = 0d;

      Long effectifCycle1 = 0L; Long effectifCycle1G = 0L; Long effectifCycle1F = 0L;
      Long effectifNonClasseCycle1 = 0L; Long effectifNonClasseCycle1G = 0L; Long effectifNonClasseCycle1F = 0L;


      Double moyCycle1=0d;


      Double moyCycle2=0d;

      long nombMoySup10Cycle2 =0l ; long nombMoyInf10Cycle2 =0l ;long nombMoyInf8_5Cycle2 =0l;
      long effectifClasseCycle2=0l ;   long effNonClassCycle2=0l;
      long nombMoySup10Cycle2G =0l ; long nombMoyInf10Cycle2G =0l ;long nombMoyInf8_5Cycle2G =0l;
      long effectifClasseCycle2G=0l ;   long effNonClassCycle2G=0l;
      long nombMoySup10Cycle2F =0l ; long nombMoyInf10Cycle2F =0l ;long nombMoyInf8_5Cycle2F =0l;
      long effectifClasseCycle2F=0l ;   long effNonClassCycle2F=0l;


      double pourSup10Cycle2= 0d,pourInf10Cycle2 = 0d,pourInf8_5Cycle2 = 0d;
      double pourSup10Cycle2G= 0d,pourInf10Cycle2G = 0d,pourInf8_5Cycle2G = 0d;
      double pourSup10Cycle2F= 0d,pourInf10Cycle2F = 0d,pourInf8_5Cycle2F = 0d;

      double pourSup10GeneralG= 0d,pourInf10GeneralG = 0d,pourInf8_5GeneralG = 0d;
      double pourSup10GeneralF= 0d,pourInf10GeneralF = 0d,pourInf8_5GeneralF = 0d;


      Long effectifCycle2 = 0L; Long effectifCycle2G = 0L;Long effectifCycle2F = 0L;
      Long effectifNonClasseCycle2 = 0L; Long effectifNonClasseCycle2G = 0L; Long effectifNonClasseCycle2F = 0L;

        Long nombreClasseCycle1=0L; Long nbreRedouIvoirienCycle1G=0L,nbreRedouIvoirienCycle1F=0L,nbreRedouEtrangerCycle1G=0L,nbreRedouEtrangerCycle1F=0L;
      Long nbreNonRedouIvoirienCycle1G=0L,nbreNonRedouIvoirienCycle1F=0L,nbreNonRedouEtrangerCycle1G=0L,nbreNonRedouEtrangerCycle1F=0L;
      Long totalCycle1G=0L;Long totalCycle1F=0L;Long totalCycle1=0L;

      Long nombreClasseCycle2=0L; Long nbreRedouIvoirienCycle2G=0L,nbreRedouIvoirienCycle2F=0L,nbreRedouEtrangerCycle2G=0L,nbreRedouEtrangerCycle2F=0L;
      Long nbreNonRedouIvoirienCycle2G=0L,nbreNonRedouIvoirienCycle2F=0L,nbreNonRedouEtrangerCycle2G=0L,nbreNonRedouEtrangerCycle2F=0L;
      Long totalCycle2G=0L;Long totalCycle2F=0L;Long totalCycle2=0L;

      String currentValue = "";
      int nombreTotalClasseParNiveau1=0;
      int nombreTotalClasseParNiveau2=0;
      Double moyenCycle1F=0d;
      Double moyenCycle1G=0d;
      Double moyenCycle2F=0d;
      Double moyenCycle2G=0d;
      Double moyenGeneralF=0d;
      Double moyenGeneralG=0d;
      if(!elevesPremierCycle.isEmpty()){


        for (EffectifNiveauGenreNationaliteDto eleve : elevesPremierCycle) {


          Ecole MyEcole;
          String lastValue = null;
          MyEcole= Ecole.findById(idEcole);
           currentValue = MyEcole.getLibelle();

          nombreClasseCycle1= eleve.getNombreCycleClasse();
          nbreNonRedouIvoirienCycle1G=eleve.getNbreNonRedouIvoireCycleG();
          nbreNonRedouIvoirienCycle1F=eleve.getNbreNonRedouIvoireCycleF();
          nbreRedouIvoirienCycle1G=eleve.getNbreRedouIvoireCycleG();
          nbreRedouIvoirienCycle1F=eleve.getNbreRedouIvoireCycleF();

          nbreNonRedouEtrangerCycle1G=eleve.getNbreNonRedouEtrangerCycleG();
          nbreNonRedouEtrangerCycle1F=eleve.getNbreNonRedouEtrangerCycleF();
          nbreRedouEtrangerCycle1G=eleve.getNbreRedouEtrangerCycleG();
          nbreRedouEtrangerCycle1F=eleve.getNbreRedouEtrangerCycleF();
          totalCycle1G=eleve.getTotalCycleG();
          totalCycle1F=eleve.getTotalCycleF();


          int nombreClasseParNiveau = getNombreDeclasseParNiveau(idEcole, libelleAnnee,trimestre,eleve.getNiveau());
          nombreTotalClasseParNiveau1= nombreTotalClasseParNiveau1+nombreClasseParNiveau ;
          // Remplir la ligne pour les filles (F)
          XWPFTableRow premierCycleRow = table.createRow();
          ensureCellCount(premierCycleRow, 18);
          premierCycleRow.getCell(0).setText(currentValue);
          premierCycleRow.getCell(1).setText(afficherValeurParNiveau(eleve.getNiveau()));
          premierCycleRow.getCell(2).setText(String.valueOf(eleve.getNombreClasse()));
          premierCycleRow.getCell(3).setText(String.valueOf(eleve.getNbreNonRedouIvoireG()));
          premierCycleRow.getCell(4).setText(String.valueOf(eleve.getNbreNonRedouIvoireF()));
          premierCycleRow.getCell(5).setText(String.valueOf(eleve.getNbreNonRedouIvoireG()+eleve.getNbreNonRedouIvoireF()));
          premierCycleRow.getCell(6).setText(String.valueOf(eleve.getNbreNonRedouEtrangerG()));
          premierCycleRow.getCell(7).setText(String.valueOf(eleve.getNbreNonRedouEtrangerF()));
          premierCycleRow.getCell(8).setText(String.valueOf(eleve.getNbreNonRedouEtrangerG()+eleve.getNbreNonRedouEtrangerF()));
          premierCycleRow.getCell(9).setText(String.valueOf(eleve.getNbreRedouIvoireG()));
          premierCycleRow.getCell(10).setText(String.valueOf(eleve.getNbreRedouIvoireF()));
          premierCycleRow.getCell(11).setText(String.valueOf(eleve.getNbreRedouIvoireG()+eleve.getNbreRedouIvoireF()));
          premierCycleRow.getCell(12).setText(String.valueOf(eleve.getNbreRedouEtrangerG()));
          premierCycleRow.getCell(13).setText(String.valueOf(eleve.getNbreRedouEtrangerF()));
          premierCycleRow.getCell(14).setText(String.valueOf(eleve.getNbreRedouEtrangerF()+eleve.getNbreRedouEtrangerG()));
          premierCycleRow.getCell(15).setText(String.valueOf(eleve.getTotalNiveauF()));
          premierCycleRow.getCell(16).setText(String.valueOf(eleve.getTotalNiveauG()));
          premierCycleRow.getCell(17).setText(String.valueOf(eleve.getTotalNiveauF()+eleve.getTotalNiveauG()));

        }
      }
      XWPFTableRow totalCycleRow = table.createRow();
      ensureCellCount(totalCycleRow, 18);
      totalCycleRow.getCell(0).setText(currentValue);
      totalCycleRow.getCell(1).setText("Total 1er Cycle");
      totalCycleRow.getCell(2).setText(String.valueOf(nombreClasseCycle1));
      totalCycleRow.getCell(3).setText(String.valueOf(nbreNonRedouIvoirienCycle1G));
      totalCycleRow.getCell(4).setText(String.valueOf(nbreNonRedouIvoirienCycle1F));
      totalCycleRow.getCell(5).setText(String.valueOf(nbreNonRedouIvoirienCycle1G+nbreNonRedouIvoirienCycle1F));
      totalCycleRow.getCell(6).setText(String.valueOf(nbreNonRedouEtrangerCycle1G));
      totalCycleRow.getCell(7).setText(String.valueOf(nbreNonRedouEtrangerCycle1F));
      totalCycleRow.getCell(8).setText(String.valueOf(nbreNonRedouEtrangerCycle1G+nbreNonRedouEtrangerCycle1F));
      totalCycleRow.getCell(9).setText(String.valueOf(nbreRedouIvoirienCycle1G));
      totalCycleRow.getCell(10).setText(String.valueOf(nbreRedouIvoirienCycle1F));
      totalCycleRow.getCell(11).setText(String.valueOf(nbreRedouIvoirienCycle1G+nbreRedouIvoirienCycle1F));
      totalCycleRow.getCell(12).setText(String.valueOf(nbreRedouEtrangerCycle1G));
      totalCycleRow.getCell(13).setText(String.valueOf(nbreRedouEtrangerCycle1F));
      totalCycleRow.getCell(14).setText(String.valueOf(nbreRedouEtrangerCycle1G+nbreRedouEtrangerCycle1F));
      totalCycleRow.getCell(15).setText(String.valueOf(totalCycle1G));
      totalCycleRow.getCell(16).setText(String.valueOf(totalCycle1F));
      totalCycleRow.getCell(17).setText(String.valueOf(totalCycle1G+totalCycle1F));



      if(!elevesSecondCycle.isEmpty()){

        //Second cycle
      for (EffectifNiveauGenreNationaliteDto eleve : elevesSecondCycle) {
        nombreClasseCycle2= eleve.getNombreCycleClasse();
        nbreNonRedouIvoirienCycle2G=eleve.getNbreNonRedouIvoireCycleG();
        nbreNonRedouIvoirienCycle2F=eleve.getNbreNonRedouIvoireCycleF();
        nbreRedouIvoirienCycle2G=eleve.getNbreRedouIvoireCycleG();
        nbreRedouIvoirienCycle2F=eleve.getNbreRedouIvoireCycleF();

        nbreNonRedouEtrangerCycle2G=eleve.getNbreNonRedouEtrangerCycleG();
        nbreNonRedouEtrangerCycle2F=eleve.getNbreNonRedouEtrangerCycleF();
        nbreRedouEtrangerCycle2G=eleve.getNbreRedouEtrangerCycleG();
        nbreRedouEtrangerCycle2F=eleve.getNbreRedouEtrangerCycleF();
        totalCycle2G=eleve.getTotalCycleG();
        totalCycle2F=eleve.getTotalCycleF();

        XWPFTableRow secondCycleRow = table.createRow();
        ensureCellCount(secondCycleRow, 18);
        secondCycleRow.getCell(0).setText(currentValue);
        secondCycleRow.getCell(1).setText(afficherValeurParNiveau(eleve.getNiveau()));
        secondCycleRow.getCell(2).setText(String.valueOf(eleve.getNombreClasse()));
        secondCycleRow.getCell(3).setText(String.valueOf(eleve.getNbreNonRedouIvoireG()));
        secondCycleRow.getCell(4).setText(String.valueOf(eleve.getNbreNonRedouIvoireF()));
        secondCycleRow.getCell(5).setText(String.valueOf(eleve.getNbreNonRedouIvoireG()+eleve.getNbreNonRedouIvoireF()));
        secondCycleRow.getCell(6).setText(String.valueOf(eleve.getNbreNonRedouEtrangerG()));
        secondCycleRow.getCell(7).setText(String.valueOf(eleve.getNbreNonRedouEtrangerF()));
        secondCycleRow.getCell(8).setText(String.valueOf(eleve.getNbreNonRedouEtrangerG()+eleve.getNbreNonRedouEtrangerF()));
        secondCycleRow.getCell(9).setText(String.valueOf(eleve.getNbreRedouIvoireG()));
        secondCycleRow.getCell(10).setText(String.valueOf(eleve.getNbreRedouIvoireF()));
        secondCycleRow.getCell(11).setText(String.valueOf(eleve.getNbreRedouIvoireG()+eleve.getNbreRedouIvoireF()));
        secondCycleRow.getCell(12).setText(String.valueOf(eleve.getNbreRedouEtrangerG()));
        secondCycleRow.getCell(13).setText(String.valueOf(eleve.getNbreRedouEtrangerF()));
        secondCycleRow.getCell(14).setText(String.valueOf(eleve.getNbreRedouEtrangerF()+eleve.getNbreRedouEtrangerG()));
        secondCycleRow.getCell(15).setText(String.valueOf(eleve.getTotalNiveauF()));
        secondCycleRow.getCell(16).setText(String.valueOf(eleve.getTotalNiveauG()));
        secondCycleRow.getCell(17).setText(String.valueOf(eleve.getTotalNiveauF()+eleve.getTotalNiveauG()));
      }
        XWPFTableRow totalCycle2Row = table.createRow();
        ensureCellCount(totalCycle2Row, 18);
        totalCycle2Row.getCell(0).setText(currentValue);
        totalCycle2Row.getCell(1).setText("Total 2nd Cycle");
        totalCycle2Row.getCell(2).setText(String.valueOf(nombreClasseCycle2));
        totalCycle2Row.getCell(3).setText(String.valueOf(nbreNonRedouIvoirienCycle2G));
        totalCycle2Row.getCell(4).setText(String.valueOf(nbreNonRedouIvoirienCycle2F));
        totalCycle2Row.getCell(5).setText(String.valueOf(nbreNonRedouIvoirienCycle2G+nbreNonRedouIvoirienCycle2F));
        totalCycle2Row.getCell(6).setText(String.valueOf(nbreNonRedouEtrangerCycle2G));
        totalCycle2Row.getCell(7).setText(String.valueOf(nbreNonRedouEtrangerCycle2F));
        totalCycle2Row.getCell(8).setText(String.valueOf(nbreNonRedouEtrangerCycle2G+nbreNonRedouEtrangerCycle2F));
        totalCycle2Row.getCell(9).setText(String.valueOf(nbreRedouIvoirienCycle2G));
        totalCycle2Row.getCell(10).setText(String.valueOf(nbreRedouIvoirienCycle2F));
        totalCycle2Row.getCell(11).setText(String.valueOf(nbreRedouIvoirienCycle2G+nbreRedouIvoirienCycle2F));
        totalCycle2Row.getCell(12).setText(String.valueOf(nbreRedouEtrangerCycle2G));
        totalCycle2Row.getCell(13).setText(String.valueOf(nbreRedouEtrangerCycle2F));
        totalCycle2Row.getCell(14).setText(String.valueOf(nbreRedouEtrangerCycle2G+nbreRedouEtrangerCycle2F));
        totalCycle2Row.getCell(15).setText(String.valueOf(totalCycle2G));
        totalCycle2Row.getCell(16).setText(String.valueOf(totalCycle2F));
        totalCycle2Row.getCell(17).setText(String.valueOf(totalCycle2G+totalCycle2F));
      }
      //Infos General


      XWPFTableRow totalGeneralRow = table.createRow();
      ensureCellCount(totalGeneralRow, 18);
      totalGeneralRow.getCell(0).setText(currentValue);
      totalGeneralRow.getCell(1).setText("Total Etabliss");
      totalGeneralRow.getCell(2).setText(String.valueOf(nombreClasseCycle1+nombreClasseCycle2));
      totalGeneralRow.getCell(3).setText(String.valueOf(nbreNonRedouIvoirienCycle2G+nbreNonRedouIvoirienCycle1G));
      totalGeneralRow.getCell(4).setText(String.valueOf(nbreNonRedouIvoirienCycle2F+nbreNonRedouIvoirienCycle1F));
      totalGeneralRow.getCell(5).setText(String.valueOf(nbreNonRedouIvoirienCycle2G+nbreNonRedouIvoirienCycle2F +nbreNonRedouIvoirienCycle1G+nbreNonRedouIvoirienCycle1F));
      totalGeneralRow.getCell(6).setText(String.valueOf(nbreNonRedouEtrangerCycle2G+nbreNonRedouEtrangerCycle1G));
      totalGeneralRow.getCell(7).setText(String.valueOf(nbreNonRedouEtrangerCycle2F+nbreNonRedouEtrangerCycle1F));
      totalGeneralRow.getCell(8).setText(String.valueOf(nbreNonRedouEtrangerCycle2G+nbreNonRedouEtrangerCycle2F+nbreNonRedouEtrangerCycle1G+nbreNonRedouEtrangerCycle1F));
      totalGeneralRow.getCell(9).setText(String.valueOf(nbreRedouIvoirienCycle2G+nbreRedouIvoirienCycle1G));
      totalGeneralRow.getCell(10).setText(String.valueOf(nbreRedouIvoirienCycle2F+nbreRedouIvoirienCycle1F));
      totalGeneralRow.getCell(11).setText(String.valueOf(nbreRedouIvoirienCycle2G+nbreRedouIvoirienCycle2F+nbreRedouIvoirienCycle1G+nbreRedouIvoirienCycle1F));
      totalGeneralRow.getCell(12).setText(String.valueOf(nbreRedouEtrangerCycle2G+nbreRedouEtrangerCycle1G));
      totalGeneralRow.getCell(13).setText(String.valueOf(nbreRedouEtrangerCycle2F+nbreRedouEtrangerCycle1F));
      totalGeneralRow.getCell(14).setText(String.valueOf(nbreRedouEtrangerCycle2G+nbreRedouEtrangerCycle2F+nbreRedouEtrangerCycle1G+nbreRedouEtrangerCycle1F));
      totalGeneralRow.getCell(15).setText(String.valueOf(totalCycle2G+totalCycle1G));
      totalGeneralRow.getCell(16).setText(String.valueOf(totalCycle2F+totalCycle1F));
      totalGeneralRow.getCell(17).setText(String.valueOf(totalCycle2G+totalCycle2F+totalCycle1G+totalCycle1F));
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

  public static String afficherValeurParNiveau(String niveau) {
    // Mapping des niveaux avec leurs correspondants
    switch (niveau) {
      case "Sixième":
        return "6ème";
      case "Cinquième":
        return "5ème";
      case "Quatrième":
        return "4ème";
      case "Troisième":
        return "3ème";
      case "Seconde C":
        return "2nde C";
      case "Seconde A":
        return "2nde A";
      case "Première A":
        return "1ère A";
      case "Première C":
        return "1ère C";
      case "Première D":
        return "1ère D";
      case "Terminale A":
        return "Tle A";
      case "Terminale A1":
        return "Tle A1";
      case "Terminale A2":
        return "Tle A2";
      case "Terminale C":
        return "Tle C";
      case "Terminale D":
        return "Tle D";
      default:
        return "Niveau inconnu";
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

package com.vieecoles.processors.dren3;

import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.RecapResultatsElevesAffeEtNonAffDto;
import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.processors.dren3.services.resultatsRecapAffEtNonAffCycle1Services;
import com.vieecoles.processors.dren3.services.resultatsRecapAffEtNonAffCycle2Services;
import com.vieecoles.processors.dren3.services.resultatsRecapAffEtNonAffCycleServices;
import com.vieecoles.services.etats.appachePoi.resultatsPoiServices;
import com.vieecoles.services.etats.resultatsRecapAffEtNonAffServices;
import com.vieecoles.services.etats.resultatsRecapServices;
import com.vieecoles.steph.entities.Ecole;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import java.math.BigInteger;

@ApplicationScoped
public class WordTempStatistiqueResultatProcessor {
    @Inject
    resultatsRecapAffEtNonAffCycleServices resultatsServices ;
  @Inject
  resultatsRecapAffEtNonAffCycle1Services resultatsServices1 ;
  @Inject
  resultatsRecapAffEtNonAffCycle2Services resultatsServices2 ;
    @Inject
    resultatsRecapServices resultatsRecapServices ;
  @Inject
  resultatsRecapAffEtNonAffServices resultatsServices3 ;
  @Inject
  EntityManager em;

      public   void getResultatAffProcessor(XWPFDocument document ,
          Long idEcole ,String libelleAnnee , String libelleTrimetre) {
        List<RecapResultatsElevesAffeEtNonAffDto> elevesPremierCycle = new ArrayList<>();
        List<RecapResultatsElevesAffeEtNonAffDto> elevesSecondCycle = new ArrayList<>();
        List<RecapResultatsElevesAffeEtNonAffDto> elevesGeneral = new ArrayList<>();
        int nombreClasseCycle1 = 0;  int nombreClasseCycle2=0;

        try {

          elevesPremierCycle= resultatsServices1.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre);
         elevesSecondCycle= resultatsServices2.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre);
          elevesGeneral=resultatsServices3.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre);
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


        // 3. Gestion des tableaux dynamiques
        // Sixième
        XWPFTable table = document.getTableArray(9);
        try {
         ajoutTableauDynamique(elevesPremierCycle,elevesSecondCycle,table, idEcole,libelleAnnee,libelleTrimetre,elevesGeneral);
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


    private  void ajoutTableauDynamique(List<RecapResultatsElevesAffeEtNonAffDto> elevesPremierCycle, List<RecapResultatsElevesAffeEtNonAffDto> elevesSecondCycle,
                                              XWPFTable table,Long idEcole ,String libelleAnnee, String trimestre,List<RecapResultatsElevesAffeEtNonAffDto> elevesGeneral) {
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

      long nombMoySup10Cycle =0l ; long nombMoyInf10Cycle =0l ;long nombMoyInf8_5Cycle =0l;
      long effectifClasseCycle=0l ;   long effNonClassCycle=0l;

      double pourSup10Cycle= 0d,pourInf10Cycle = 0d,pourInf8_5Cycle = 0d;
      Long effectifCycle = 0L;

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


        for (RecapResultatsElevesAffeEtNonAffDto eleve : elevesPremierCycle) {

          moyenCycle1F= eleve.getMoyClasseF_ET();

          moyenCycle1G= eleve.getMoyClasseG_ET();
          Ecole MyEcole;
          String lastValue = null;
          MyEcole= Ecole.findById(idEcole);
           currentValue = MyEcole.getLibelle();

          Long effectifNiveau1=eleve.getEffeG()+eleve.getEffeF();
          Long effectifNiveau1G=eleve.getEffeG();
          Long effectifNiveau1F=eleve.getEffeF();

          Long effectifClasse1 =eleve.getClassF()+eleve.getClassG();
          Long effectifClasse1G=eleve.getClassG();
          Long effectifClasse1F=eleve.getClassF();

          Long effectifNonClasse1 =eleve.getNonclassF()+eleve.getNonclassG();
          Long effectifNonClasse1G=eleve.getNonclassG();
          Long effectifNonClasse1F=eleve.getNonclassF();



          effectifClasseCycle1=effectifClasseCycle1+effectifClasse1;
          effectifClasseCycle1G=effectifClasseCycle1G+effectifClasse1G;
          effectifClasseCycle1F=effectifClasseCycle1F+effectifClasse1F;

          effectifCycle1= effectifCycle1+effectifNiveau1;
          effectifCycle1G= effectifCycle1G+effectifNiveau1G;
          effectifCycle1F= effectifCycle1F+effectifNiveau1F;


          effectifNonClasseCycle1= effectifNonClasseCycle1+effectifNonClasse1;
          effectifNonClasseCycle1G= effectifNonClasseCycle1G+effectifNonClasse1G;
          effectifNonClasseCycle1F= effectifNonClasseCycle1F+effectifNonClasse1F;

          long nombMoySup10 =
              (eleve.getNbreMoySup10F() != null ? eleve.getNbreMoySup10F() : 0L) +
                  (eleve.getNbreMoySup10G() != null ? eleve.getNbreMoySup10G() : 0L);
          nombMoySup10Cycle1=nombMoySup10Cycle1+nombMoySup10;

          long nombMoySup10G =
              (eleve.getNbreMoySup10G() != null ? eleve.getNbreMoySup10G() : 0L);
          nombMoySup10Cycle1G=nombMoySup10Cycle1G+nombMoySup10G;

          long nombMoySup10F =
              (eleve.getNbreMoySup10F() != null ? eleve.getNbreMoySup10F() : 0L);
          nombMoySup10Cycle1F=nombMoySup10Cycle1F+nombMoySup10F;

          long nombMoyInf10 =
              (eleve.getNbreMoyInf999F() != null ? eleve.getNbreMoyInf999F() : 0L) +
                  (eleve.getNbreMoyInf999G() != null ? eleve.getNbreMoyInf999G() : 0L);
          nombMoyInf10Cycle1=nombMoyInf10Cycle1+nombMoyInf10;

          long nombMoyInf10G =eleve.getNbreMoyInf999G() != null ? eleve.getNbreMoyInf999G() : 0L;
          nombMoyInf10Cycle1G=nombMoyInf10Cycle1G+nombMoyInf10G;

          long nombMoyInf10F = (eleve.getNbreMoyInf999F() != null ? eleve.getNbreMoyInf999F() : 0L) ;
          nombMoyInf10Cycle1F=nombMoyInf10Cycle1F+nombMoyInf10F;





          long nombMoyInf8_5 =
              (eleve.getNbreMoyInf85F() != null ? eleve.getNbreMoyInf85F() : 0L) +
                  (eleve.getNbreMoyInf85G() != null ? eleve.getNbreMoyInf85G() : 0L);
          nombMoyInf8_5Cycle1=nombMoyInf8_5Cycle1+nombMoyInf8_5;

          long nombMoyInf8_5G =
                  (eleve.getNbreMoyInf85G() != null ? eleve.getNbreMoyInf85G() : 0L);
          nombMoyInf8_5Cycle1G=nombMoyInf8_5Cycle1G+nombMoyInf8_5G;

          long nombMoyInf8_5F =(eleve.getNbreMoyInf85F() != null ? eleve.getNbreMoyInf85F() : 0L) ;
          nombMoyInf8_5Cycle1F=nombMoyInf8_5Cycle1F+nombMoyInf8_5F;



          long effectifClasse =
              (eleve.getClassF() != null ? eleve.getClassF() : 0L) +
                  (eleve.getClassG() != null ? eleve.getClassG() : 0L);
          effectifClasseCycle1=effectifClasseCycle1+effectifClasse;




          long effectifNonClasse =
              (eleve.getNonclassF() != null ? eleve.getNonclassF() : 0L) +
                  (eleve.getNonclassG() != null ? eleve.getNonclassG() : 0L);
          effectifNonClasseCycle1=effectifNonClasseCycle1+effectifNonClasse;


          long effectif =
              (eleve.getEffeF() != null ? eleve.getEffeF() : 0L) +
                  (eleve.getEffeG() != null ? eleve.getEffeG() : 0L);
          effectifCycle1=effectifCycle1+effectif;






          // Pourcentage cycle 1 Garcon
          if (effectifClasseCycle1G > 0) {
            pourSup10Cycle1G = nombMoySup10Cycle1G / (Double.valueOf(effectifClasseCycle1G)) * 100d;
            pourInf10Cycle1G = nombMoyInf10Cycle1G / (Double.valueOf(effectifClasseCycle1G)) * 100d;
            pourInf8_5Cycle1G = nombMoyInf8_5Cycle1G / (Double.valueOf(effectifClasseCycle1G)) * 100d;

            // Calculer d'autres statistiques ou utiliser ces valeurs
          } else {
            // Gérer le cas où effectifClasse est 0 pour éviter une division par zéro
            pourSup10Cycle1G = 0d;
            pourInf10Cycle1G = 0d;
            pourInf8_5Cycle1G = 0d;
            // Logique alternative ou gestion d'erreur
          }

          // Pourcentage cycle 1 Fille
          if (effectifClasseCycle1F > 0) {
            pourSup10Cycle1F = nombMoySup10Cycle1F / (Double.valueOf(effectifClasseCycle1F)) * 100d;
            pourInf10Cycle1F = nombMoyInf10Cycle1F / (Double.valueOf(effectifClasseCycle1F)) * 100d;
            pourInf8_5Cycle1F = nombMoyInf8_5Cycle1F / (Double.valueOf(effectifClasseCycle1F)) * 100d;

            // Calculer d'autres statistiques ou utiliser ces valeurs
          } else {
            // Gérer le cas où effectifClasse est 0 pour éviter une division par zéro
            pourSup10Cycle1F = 0d;
            pourInf10Cycle1F = 0d;
            pourInf8_5Cycle1F = 0d;
            // Logique alternative ou gestion d'erreur
          }

          int nombreClasseParNiveau = getNombreDeclasseParNiveau(idEcole, libelleAnnee,trimestre,eleve.getNiveau());
          nombreTotalClasseParNiveau1= nombreTotalClasseParNiveau1+nombreClasseParNiveau ;
          // Remplir la ligne pour les filles (F)
          XWPFTableRow garconRow = table.createRow();
          ensureCellCount(garconRow, 14);
        //  garconRow.getCell(0).setText(currentValue);
          garconRow.getCell(1).setText(afficherValeurParNiveau(eleve.getNiveau()));
          garconRow.getCell(2).setText("G");
          garconRow.getCell(3).setText(String.valueOf(nombreClasseParNiveau));
          garconRow.getCell(4).setText(String.valueOf(eleve.getEffeG()));
          garconRow.getCell(5).setText(String.valueOf(eleve.getClassG()));
          garconRow.getCell(6).setText(String.valueOf(eleve.getNonclassG()));
          garconRow.getCell(7).setText(String.valueOf(eleve.getNbreMoySup10G()));
          garconRow.getCell(8).setText(String.valueOf(arrondie(eleve.getPourMoySup10G())));
          garconRow.getCell(9).setText(String.valueOf(eleve.getNbreMoyInf999G()));
          garconRow.getCell(10).setText(String.valueOf(arrondie(eleve.getPourMoyInf999G())));
          garconRow.getCell(11).setText(String.valueOf(eleve.getNbreMoyInf85G()));
          garconRow.getCell(12).setText(String.valueOf(arrondie(eleve.getPourMoyInf85G())));
          garconRow.getCell(13).setText(String.valueOf(arrondie(eleve.getMoyClasseG())));

          // Remplir la ligne pour les garçons (G)

          XWPFTableRow filleRow = table.createRow();
          ensureCellCount(filleRow, 14);
        //  filleRow.getCell(1).setText(eleve.getNiveau());
          filleRow.getCell(2).setText("F");
          filleRow.getCell(4).setText(String.valueOf(eleve.getEffeF()));
          filleRow.getCell(5).setText(String.valueOf(eleve.getClassF()));
          filleRow.getCell(6).setText(String.valueOf(eleve.getNonclassF()));
          filleRow.getCell(7).setText(String.valueOf(eleve.getNbreMoySup10F()));
          filleRow.getCell(8).setText(String.valueOf(arrondie(eleve.getPourMoySup10F())));
          filleRow.getCell(9).setText(String.valueOf(eleve.getNbreMoyInf999F()));
          filleRow.getCell(10).setText(String.valueOf(arrondie(eleve.getPourMoyInf999F())));
          filleRow.getCell(11).setText(String.valueOf(eleve.getNbreMoyInf85F()));
          filleRow.getCell(12).setText(String.valueOf(arrondie(eleve.getPourMoyInf85F())));
          filleRow.getCell(13).setText(String.valueOf(arrondie(eleve.getMoyClasseF())));
          mergeCellsVertically(table, 1, table.getNumberOfRows() - 2, table.getNumberOfRows() -1);
          mergeCellsVertically(table, 3, table.getNumberOfRows() - 2, table.getNumberOfRows() -1);

        }
      }
      XWPFTableRow garconTotalCycleRow = table.createRow();
      ensureCellCount(garconTotalCycleRow, 14);
      //garconTotalCycleRow.getCell(0).setText(currentValue);
      garconTotalCycleRow.getCell(1).setText("1er Cycle");
      garconTotalCycleRow.getCell(2).setText("G");
      garconTotalCycleRow.getCell(3).setText(String.valueOf(nombreTotalClasseParNiveau1));
      garconTotalCycleRow.getCell(4).setText(String.valueOf(effectifCycle1G));
      garconTotalCycleRow.getCell(5).setText(String.valueOf(effectifClasseCycle1G));
      garconTotalCycleRow.getCell(6).setText(String.valueOf(effectifNonClasseCycle1G));
      garconTotalCycleRow.getCell(7).setText(String.valueOf(nombMoySup10Cycle1G));
      garconTotalCycleRow.getCell(8).setText(String.valueOf(arrondie(pourSup10Cycle1G)));
      garconTotalCycleRow.getCell(9).setText(String.valueOf(nombMoyInf10Cycle1G));
      garconTotalCycleRow.getCell(10).setText(String.valueOf(arrondie(pourInf10Cycle1G)));
      garconTotalCycleRow.getCell(11).setText(String.valueOf(nombMoyInf8_5Cycle1G));
      garconTotalCycleRow.getCell(12).setText(String.valueOf(arrondie(pourInf8_5Cycle1G)));
      garconTotalCycleRow.getCell(13).setText(String.valueOf(arrondie(moyenCycle1G)));

      // Remplir la ligne pour les garçons (G)

      XWPFTableRow filleCycleRow = table.createRow();
      ensureCellCount(filleCycleRow, 14);
      //  filleRow.getCell(1).setText(eleve.getNiveau());
      filleCycleRow.getCell(2).setText("F");
      filleCycleRow.getCell(4).setText(String.valueOf(effectifCycle1F));
      filleCycleRow.getCell(5).setText(String.valueOf(effectifClasseCycle1F));
      filleCycleRow.getCell(6).setText(String.valueOf(effectifNonClasseCycle1F));
      filleCycleRow.getCell(7).setText(String.valueOf(nombMoySup10Cycle1F));
      filleCycleRow.getCell(8).setText(String.valueOf(arrondie(pourSup10Cycle1F)));
      filleCycleRow.getCell(9).setText(String.valueOf(nombMoyInf10Cycle1F));
      filleCycleRow.getCell(10).setText(String.valueOf(arrondie(pourInf10Cycle1F)));
      filleCycleRow.getCell(11).setText(String.valueOf(nombMoyInf8_5Cycle1F));
      filleCycleRow.getCell(12).setText(String.valueOf(arrondie(pourInf8_5Cycle1F)));
      filleCycleRow.getCell(13).setText(String.valueOf(arrondie(moyenCycle1F)));
      mergeCellsVertically(table, 1, table.getNumberOfRows() - 2, table.getNumberOfRows() -1);
      mergeCellsVertically(table, 3, table.getNumberOfRows() - 2, table.getNumberOfRows() -1);

      if(!elevesSecondCycle.isEmpty()){

        //Second cycle
      for (RecapResultatsElevesAffeEtNonAffDto eleve : elevesSecondCycle) {
        moyenCycle2F= eleve.getMoyClasseF_ET();

        moyenCycle2G= eleve.getMoyClasseG_ET();
        Ecole MyEcole;
        String lastValue = null;
        MyEcole= Ecole.findById(idEcole);
        currentValue = MyEcole.getLibelle();

        Long effectifNiveau2=eleve.getEffeG()+eleve.getEffeF();
        Long effectifNiveau2G=eleve.getEffeG();
        Long effectifNiveau2F=eleve.getEffeF();

        Long effectifClasse2 =eleve.getClassF()+eleve.getClassG();
        Long effectifClasse2G=eleve.getClassG();
        Long effectifClasse2F=eleve.getClassF();

        Long effectifNonClasse2 =eleve.getNonclassF()+eleve.getNonclassG();
        Long effectifNonClasse2G=eleve.getNonclassG();
        Long effectifNonClasse2F=eleve.getNonclassF();



        effectifClasseCycle2=effectifClasseCycle1+effectifClasse2;
        effectifClasseCycle2G=effectifClasseCycle1G+effectifClasse2G;
        effectifClasseCycle2F=effectifClasseCycle1F+effectifClasse2F;

        effectifCycle2= effectifCycle2+effectifNiveau2;
        effectifCycle2G= effectifCycle2G+effectifNiveau2G;
        effectifCycle2F= effectifCycle2F+effectifNiveau2F;


        effectifNonClasseCycle2= effectifNonClasseCycle1+effectifNonClasse2;
        effectifNonClasseCycle2G= effectifNonClasseCycle1G+effectifNonClasse2G;
        effectifNonClasseCycle2F= effectifNonClasseCycle1F+effectifNonClasse2F;

        long nombMoySup10 =
            (eleve.getNbreMoySup10F() != null ? eleve.getNbreMoySup10F() : 0L) +
                (eleve.getNbreMoySup10G() != null ? eleve.getNbreMoySup10G() : 0L);
        nombMoySup10Cycle2=nombMoySup10Cycle2+nombMoySup10;

        long nombMoySup10G =
            (eleve.getNbreMoySup10G() != null ? eleve.getNbreMoySup10G() : 0L);
        nombMoySup10Cycle2G=nombMoySup10Cycle2G+nombMoySup10G;

        long nombMoySup10F =
            (eleve.getNbreMoySup10F() != null ? eleve.getNbreMoySup10F() : 0L);
        nombMoySup10Cycle2F=nombMoySup10Cycle2F+nombMoySup10F;

        long nombMoyInf10 =
            (eleve.getNbreMoyInf999F() != null ? eleve.getNbreMoyInf999F() : 0L) +
                (eleve.getNbreMoyInf999G() != null ? eleve.getNbreMoyInf999G() : 0L);
        nombMoyInf10Cycle2=nombMoyInf10Cycle2+nombMoyInf10;

        long nombMoyInf10G =eleve.getNbreMoyInf999G() != null ? eleve.getNbreMoyInf999G() : 0L;
        nombMoyInf10Cycle1G=nombMoyInf10Cycle1G+nombMoyInf10G;

        long nombMoyInf10F = (eleve.getNbreMoyInf999F() != null ? eleve.getNbreMoyInf999F() : 0L) ;
        nombMoyInf10Cycle2F=nombMoyInf10Cycle2F+nombMoyInf10F;





        long nombMoyInf8_5 =
            (eleve.getNbreMoyInf85F() != null ? eleve.getNbreMoyInf85F() : 0L) +
                (eleve.getNbreMoyInf85G() != null ? eleve.getNbreMoyInf85G() : 0L);
        nombMoyInf8_5Cycle2=nombMoyInf8_5Cycle2+nombMoyInf8_5;

        long nombMoyInf8_5G =
            (eleve.getNbreMoyInf85G() != null ? eleve.getNbreMoyInf85G() : 0L);
        nombMoyInf8_5Cycle2G=nombMoyInf8_5Cycle2G+nombMoyInf8_5G;

        long nombMoyInf8_5F =(eleve.getNbreMoyInf85F() != null ? eleve.getNbreMoyInf85F() : 0L) ;
        nombMoyInf8_5Cycle2F=nombMoyInf8_5Cycle2F+nombMoyInf8_5F;



        long effectifClasse =
            (eleve.getClassF() != null ? eleve.getClassF() : 0L) +
                (eleve.getClassG() != null ? eleve.getClassG() : 0L);
        effectifClasseCycle2=effectifClasseCycle2+effectifClasse;


        long effectifNonClasse =
            (eleve.getNonclassF() != null ? eleve.getNonclassF() : 0L) +
                (eleve.getNonclassG() != null ? eleve.getNonclassG() : 0L);
        effectifNonClasseCycle2=effectifNonClasseCycle2+effectifNonClasse;



        long effectif =
            (eleve.getEffeF() != null ? eleve.getEffeF() : 0L) +
                (eleve.getEffeG() != null ? eleve.getEffeG() : 0L);
        effectifCycle2=effectifCycle2+effectif;

        long effectifF =
            (eleve.getEffeF() != null ? eleve.getEffeF() : 0L) ;


        long effectifG =(eleve.getEffeG() != null ? eleve.getEffeG() : 0L);



        // Pourcentage cycle 1 Garcon
        if (effectifClasseCycle2G > 0) {
          pourSup10Cycle2G = nombMoySup10Cycle2G / (Double.valueOf(effectifClasseCycle2G)) * 100d;
          pourInf10Cycle2G = nombMoyInf10Cycle2G / (Double.valueOf(effectifClasseCycle2G)) * 100d;
          pourInf8_5Cycle2G = nombMoyInf8_5Cycle2G / (Double.valueOf(effectifClasseCycle2G)) * 100d;

          // Calculer d'autres statistiques ou utiliser ces valeurs
        } else {
          // Gérer le cas où effectifClasse est 0 pour éviter une division par zéro
          pourSup10Cycle2G = 0d;
          pourInf10Cycle2G = 0d;
          pourInf8_5Cycle2G = 0d;
          // Logique alternative ou gestion d'erreur
        }

        // Pourcentage cycle 2 Fille
        if (effectifClasseCycle2F > 0) {
          pourSup10Cycle2F = nombMoySup10Cycle2F / (Double.valueOf(effectifClasseCycle2F)) * 100d;
          pourInf10Cycle2F = nombMoyInf10Cycle2F / (Double.valueOf(effectifClasseCycle2F)) * 100d;
          pourInf8_5Cycle2F = nombMoyInf8_5Cycle2F / (Double.valueOf(effectifClasseCycle2F)) * 100d;

          // Calculer d'autres statistiques ou utiliser ces valeurs
        } else {
          // Gérer le cas où effectifClasse est 0 pour éviter une division par zéro
          pourSup10Cycle2F = 0d;
          pourInf10Cycle2F = 0d;
          pourInf8_5Cycle2F = 0d;
          // Logique alternative ou gestion d'erreur
        }

        int nombreClasseParNiveau = getNombreDeclasseParNiveau(idEcole, libelleAnnee,trimestre,eleve.getNiveau()) ;
        nombreTotalClasseParNiveau2= nombreTotalClasseParNiveau2+nombreClasseParNiveau;
        // Remplir la ligne pour les filles (F)
        XWPFTableRow garconRow = table.createRow();
        ensureCellCount(garconRow, 14);
        //garconRow.getCell(0).setText(currentValue);
        garconRow.getCell(1).setText(afficherValeurParNiveau(eleve.getNiveau()));
        garconRow.getCell(2).setText("G");
        garconRow.getCell(3).setText(String.valueOf(nombreClasseParNiveau));
        garconRow.getCell(4).setText(String.valueOf(eleve.getEffeG()));
        garconRow.getCell(5).setText(String.valueOf(eleve.getClassG()));
        garconRow.getCell(6).setText(String.valueOf(eleve.getNonclassG()));
        garconRow.getCell(7).setText(String.valueOf(eleve.getNbreMoySup10G()));
        garconRow.getCell(8).setText(String.valueOf(arrondie(eleve.getPourMoySup10G())));
        garconRow.getCell(9).setText(String.valueOf(eleve.getNbreMoyInf999G()));
        garconRow.getCell(10).setText(String.valueOf(arrondie(eleve.getPourMoyInf999G())));
        garconRow.getCell(11).setText(String.valueOf(eleve.getNbreMoyInf85G()));
        garconRow.getCell(12).setText(String.valueOf(arrondie(eleve.getPourMoyInf85G())));
        garconRow.getCell(13).setText(String.valueOf(arrondie(eleve.getMoyClasseG())));

        // Remplir la ligne pour les garçons (G)

        XWPFTableRow filleRow = table.createRow();
        ensureCellCount(filleRow, 14);
        //  filleRow.getCell(1).setText(eleve.getNiveau());
        filleRow.getCell(2).setText("F");
        filleRow.getCell(4).setText(String.valueOf(eleve.getEffeF()));
        filleRow.getCell(5).setText(String.valueOf(eleve.getClassF()));
        filleRow.getCell(6).setText(String.valueOf(eleve.getNonclassF()));
        filleRow.getCell(7).setText(String.valueOf(eleve.getNbreMoySup10F()));
        filleRow.getCell(8).setText(String.valueOf(arrondie(eleve.getPourMoySup10F())));
        filleRow.getCell(9).setText(String.valueOf(eleve.getNbreMoyInf999F()));
        filleRow.getCell(10).setText(String.valueOf(arrondie(eleve.getPourMoyInf999F())));
        filleRow.getCell(11).setText(String.valueOf(eleve.getNbreMoyInf85F()));
        filleRow.getCell(12).setText(String.valueOf(arrondie(eleve.getPourMoyInf85F())));
        filleRow.getCell(13).setText(String.valueOf(arrondie(eleve.getMoyClasseF())));
        mergeCellsVertically(table, 1, table.getNumberOfRows() - 2, table.getNumberOfRows() -1);
        mergeCellsVertically(table, 3, table.getNumberOfRows() - 2, table.getNumberOfRows() -1);

      }
        XWPFTableRow garconTotalCycle2Row = table.createRow();
        ensureCellCount(garconTotalCycle2Row, 14);
        //garconTotalCycle2Row.getCell(0).setText(currentValue);
        garconTotalCycle2Row.getCell(1).setText("2nd Cycle");
        garconTotalCycle2Row.getCell(2).setText("G");
        garconTotalCycle2Row.getCell(3).setText(String.valueOf(nombreTotalClasseParNiveau2));
        garconTotalCycle2Row.getCell(4).setText(String.valueOf(effectifCycle2G));
        garconTotalCycle2Row.getCell(5).setText(String.valueOf(effectifClasseCycle2G));
        garconTotalCycle2Row.getCell(6).setText(String.valueOf(effectifNonClasseCycle2G));
        garconTotalCycle2Row.getCell(7).setText(String.valueOf(nombMoySup10Cycle2G));
        garconTotalCycle2Row.getCell(8).setText(String.valueOf(arrondie(pourSup10Cycle2G)));
        garconTotalCycle2Row.getCell(9).setText(String.valueOf(nombMoyInf10Cycle2G));
        garconTotalCycle2Row.getCell(10).setText(String.valueOf(arrondie(pourInf10Cycle2G)));
        garconTotalCycle2Row.getCell(11).setText(String.valueOf(nombMoyInf8_5Cycle2G));
        garconTotalCycle2Row.getCell(12).setText(String.valueOf(arrondie(pourInf8_5Cycle2G)));
        garconTotalCycle2Row.getCell(13).setText(String.valueOf(arrondie(moyenCycle2G)));

        // Remplir la ligne pour les garçons (G)

        XWPFTableRow filleCycle2Row = table.createRow();
        ensureCellCount(filleCycle2Row, 14);
        //  filleRow.getCell(1).setText(eleve.getNiveau());
        filleCycle2Row.getCell(2).setText("F");
        filleCycle2Row.getCell(4).setText(String.valueOf(effectifCycle2F));
        filleCycle2Row.getCell(5).setText(String.valueOf(effectifClasseCycle2F));
        filleCycle2Row.getCell(6).setText(String.valueOf(effectifNonClasseCycle2F));
        filleCycle2Row.getCell(7).setText(String.valueOf(nombMoySup10Cycle2F));
        filleCycle2Row.getCell(8).setText(String.valueOf(arrondie(pourSup10Cycle2F)));
        filleCycle2Row.getCell(9).setText(String.valueOf(nombMoyInf10Cycle2F));
        filleCycle2Row.getCell(10).setText(String.valueOf(arrondie(pourInf10Cycle2F)));
        filleCycle2Row.getCell(11).setText(String.valueOf(nombMoyInf8_5Cycle2F));
        filleCycle2Row.getCell(12).setText(String.valueOf(arrondie(pourInf8_5Cycle2F)));
        filleCycle2Row.getCell(13).setText(String.valueOf(arrondie(moyenCycle2F)));
        mergeCellsVertically(table, 1, table.getNumberOfRows() - 2, table.getNumberOfRows() -1);
        mergeCellsVertically(table, 3, table.getNumberOfRows() - 2, table.getNumberOfRows() -1);


      }
      //Infos General




      Long effectifClasseGeneralG=effectifClasseCycle1G+effectifClasseCycle2G;
      Long effectifClasseGeneralF=effectifClasseCycle1F+effectifClasseCycle2F;

      Long effectifNonClasseGeneralG=effectifNonClasseCycle2G+effectifNonClasseCycle1G;
      Long effectifNonClasseGeneralF=effectifNonClasseCycle2F+effectifNonClasseCycle1F;



    Long  effectifCycleGeneralG= effectifCycle2G+effectifCycle1G;
   Long   effectifCycleGeneralF= effectifCycle2F+effectifCycle1F;




      long nombMoySup10GeneralG =nombMoySup10Cycle2G+nombMoySup10Cycle1G;

      long nombMoySup10GeneralF = nombMoySup10Cycle2F+nombMoySup10Cycle1F;;

      long nombMoyInf10GeneralG =nombMoyInf10Cycle1G+nombMoyInf10Cycle2G;

      long nombMoyInf10GeneralF = nombMoyInf10Cycle1F+nombMoyInf10Cycle2F;;




      long nombMoyInf8_5GeneralG = nombMoyInf8_5Cycle2G+nombMoyInf8_5Cycle1G;
      long nombMoyInf8_5GeneralF = nombMoyInf8_5Cycle2F+nombMoyInf8_5Cycle1F;



      long effectifGeneralF =effectifCycle2F+effectifCycle1F;
      long effectifGeneralG =effectifCycle2G+effectifCycle1G;

      moyenGeneralG=elevesGeneral.get(0).getMoyClasseG_ET();
      moyenGeneralF=elevesGeneral.get(0).getMoyClasseF_ET();
      // Pourcentage cycle 1 Garcon
      if (effectifClasseGeneralG > 0) {
        pourSup10GeneralG = nombMoySup10GeneralG / (Double.valueOf(effectifClasseGeneralG)) * 100d;
        pourInf10GeneralG = nombMoyInf10GeneralG / (Double.valueOf(effectifClasseGeneralG)) * 100d;
        pourInf8_5GeneralG = nombMoyInf8_5GeneralG / (Double.valueOf(effectifClasseGeneralG)) * 100d;

        // Calculer d'autres statistiques ou utiliser ces valeurs
      } else {
        // Gérer le cas où effectifClasse est 0 pour éviter une division par zéro
        pourSup10GeneralG = 0d;
        pourInf10GeneralG = 0d;
        pourInf8_5GeneralG = 0d;
        // Logique alternative ou gestion d'erreur
      }

      // Pourcentage cycle 2 Fille
      if (effectifClasseGeneralF > 0) {
        pourSup10GeneralF = nombMoySup10GeneralF / (Double.valueOf(effectifClasseGeneralF)) * 100d;
        pourInf10GeneralF = nombMoyInf10GeneralF / (Double.valueOf(effectifClasseGeneralF)) * 100d;
        pourInf8_5GeneralF = nombMoyInf8_5GeneralF / (Double.valueOf(effectifClasseGeneralF)) * 100d;

        // Calculer d'autres statistiques ou utiliser ces valeurs
      } else {
        // Gérer le cas où effectifClasse est 0 pour éviter une division par zéro
        pourSup10GeneralF = 0d;
        pourInf10GeneralF = 0d;
        pourInf8_5GeneralF = 0d;
        // Logique alternative ou gestion d'erreur
      }




      XWPFTableRow garconGenerelRow = table.createRow();
      ensureCellCount(garconGenerelRow, 14);
    //  garconGenerelRow.getCell(0).setText(currentValue);
      garconGenerelRow.getCell(1).setText("TOTAL GENERAL");
      garconGenerelRow.getCell(2).setText("G");
      garconGenerelRow.getCell(3).setText(String.valueOf(nombreTotalClasseParNiveau2+nombreTotalClasseParNiveau1));
      garconGenerelRow.getCell(4).setText(String.valueOf(effectifCycle2G+effectifCycle1G));
      garconGenerelRow.getCell(5).setText(String.valueOf(effectifClasseCycle2G+effectifClasseCycle1G));
      garconGenerelRow.getCell(6).setText(String.valueOf(effectifNonClasseCycle2G+effectifNonClasseCycle1G));
      garconGenerelRow.getCell(7).setText(String.valueOf(nombMoySup10GeneralG));
      garconGenerelRow.getCell(8).setText(String.valueOf(arrondie(pourSup10GeneralG)));
      garconGenerelRow.getCell(9).setText(String.valueOf(nombMoyInf10GeneralG));
      garconGenerelRow.getCell(10).setText(String.valueOf(arrondie(pourInf10GeneralG)));
      garconGenerelRow.getCell(11).setText(String.valueOf(nombMoyInf8_5GeneralG));
      garconGenerelRow.getCell(12).setText(String.valueOf(arrondie(pourInf8_5GeneralG)));
      garconGenerelRow.getCell(13).setText(String.valueOf(arrondie(moyenGeneralG)));

      // Remplir la ligne pour les garçons (G)

      XWPFTableRow filleGeneralRow = table.createRow();
      ensureCellCount(filleGeneralRow, 14);
      //  filleRow.getCell(1).setText(eleve.getNiveau());
      filleGeneralRow.getCell(2).setText("F");
      filleGeneralRow.getCell(4).setText(String.valueOf(effectifCycle2F+effectifCycle1F));
      filleGeneralRow.getCell(5).setText(String.valueOf(effectifClasseCycle2F+effectifClasseCycle1F));
      filleGeneralRow.getCell(6).setText(String.valueOf(effectifNonClasseCycle2F+effectifNonClasseCycle1F));
      filleGeneralRow.getCell(7).setText(String.valueOf(nombMoySup10GeneralF));
      filleGeneralRow.getCell(8).setText(String.valueOf(arrondie(pourSup10GeneralF)));
      filleGeneralRow.getCell(9).setText(String.valueOf(nombMoyInf10GeneralF));
      filleGeneralRow.getCell(10).setText(String.valueOf(arrondie(pourInf10GeneralF)));
      filleGeneralRow.getCell(11).setText(String.valueOf(nombMoyInf8_5GeneralF));
      filleGeneralRow.getCell(12).setText(String.valueOf(arrondie(pourInf8_5GeneralF)));
      filleGeneralRow.getCell(13).setText(String.valueOf(arrondie(moyenGeneralG)));
      mergeCellsVertically(table, 1, table.getNumberOfRows() - 2, table.getNumberOfRows() -1);
      mergeCellsVertically(table, 3, table.getNumberOfRows() - 2, table.getNumberOfRows() -1);


       mergeCellsVertically(table, 0, 1, table.getNumberOfRows()-1 );
      table.getRow(2).getCell(0).setText(currentValue);
      // Ajuster la largeur de toutes les colonnes
      // Ajuster la largeur de toutes les colonnes
      /*for (int rowIndex2 = 0; rowIndex2 < table.getRows().size(); rowIndex2++) {
        XWPFTableRow row = table.getRow(rowIndex2);
        for (int cellIndex = 0; cellIndex < row.getTableCells().size(); cellIndex++) {
          XWPFTableCell cell = row.getCell(cellIndex);

          if (cellIndex == 0) {
            // Créer un BigInteger pour la largeur (par exemple 4000 EMU)
            BigInteger width = BigInteger.valueOf(4000); // Largeur de la cellule
            CTTblWidth tblWidth = cell.getCTTc().getTcPr().addNewTcW();
            tblWidth.setW(width);  // Définir la largeur avec BigInteger
            tblWidth.setType(STTblWidth.DXA); // Spécifier l'unité de mesure (DXA)
          }
        }
      }*/

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

}

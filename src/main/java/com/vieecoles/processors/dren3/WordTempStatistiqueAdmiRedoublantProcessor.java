package com.vieecoles.processors.dren3;

import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.RecapResultatsElevesAffeEtNonAffDto;
import com.vieecoles.processors.dren3.services.resultatsRecapAffEtNonAffCycle1Services;
import com.vieecoles.processors.dren3.services.resultatsRecapAffEtNonAffCycle2Services;
import com.vieecoles.processors.dren3.services.resultatsRecapAffEtNonAffCycleServices;
import com.vieecoles.processors.dren3.services.resultatsRecapAffEtNonAffRedoubCycle1Services;
import com.vieecoles.processors.dren3.services.resultatsRecapAffEtNonAffRedoubCycle2Services;
import com.vieecoles.processors.dren3.services.resultatsRecapAffEtNonAffRestrictCycle1Services;
import com.vieecoles.processors.dren3.services.resultatsRecapAffEtNonAffRestrictCycle2Services;
import com.vieecoles.services.etats.resultatsRecapAffEtNonAffServices;
import com.vieecoles.services.etats.resultatsRecapServices;
import com.vieecoles.steph.entities.Ecole;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

@ApplicationScoped
public class WordTempStatistiqueAdmiRedoublantProcessor {
    @Inject
    resultatsRecapAffEtNonAffCycleServices resultatsServices ;
  @Inject
  resultatsRecapAffEtNonAffRestrictCycle1Services resultatsServices1 ;
  @Inject
  resultatsRecapAffEtNonAffRestrictCycle2Services resultatsServices2 ;
    @Inject
    resultatsRecapServices resultatsRecapServices ;
  @Inject
  resultatsRecapAffEtNonAffServices resultatsServices3 ;
  @Inject
  resultatsRecapAffEtNonAffRedoubCycle1Services resultatsRedoubServices1 ;
  @Inject
  resultatsRecapAffEtNonAffRedoubCycle2Services resultatsRedoubServices2 ;
  @Inject
  EntityManager em;

      public   void getStatistiqueAdminRedoublantProcessor(XWPFDocument document ,
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


        XWPFTable table = document.getTableArray(10);
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
                                              XWPFTable table,Long idEcole ,String libelleAnnee, String trimestre,
                                        List<RecapResultatsElevesAffeEtNonAffDto> elevesGeneral ) {
      String lastNiveau = null;
      int startRowIndex = -1;
      int rowIndex = 1;

      long nombMoySup10Cycle1 =0l ; long nombMoyInf10Cycle1 =0l ;long nombMoyInf8_5Cycle1 =0l;
      long nombMoySup10RedoublCycle1G =0l ; long nombMoyInf10RedoublCycle1G =0l ;long nombMoyInf8_5RedoublCycle1G =0l;
      long nombMoySup10RedoublCycle1F =0l ; long nombMoyInf10RedoublCycle1F =0l ;long nombMoyInf8_5RedoublCycle1F =0l;
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
      long nombMoySup10RedoublCycle2G =0l ; long nombMoyInf10RedoublCycle2G =0l ;long nombMoyInf8_5RedoublCycle2G =0l;
      long nombMoySup10RedoublCycle2F =0l ; long nombMoyInf10RedoublCycle2F =0l ;long nombMoyInf8_5RedoublCycle2F =0l;
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
      Long nombreMoyInf10RedoubG=0L;
      Long nombreMoyInf10RedoubF=0L;
      Long nombreMoyInf8_5RedoubG=0L;
      Long nombreMoyInf8_5RedoubF=0L;

      if(!elevesPremierCycle.isEmpty()){


        for (RecapResultatsElevesAffeEtNonAffDto eleve : elevesPremierCycle) {
          List<RecapResultatsElevesAffeEtNonAffDto> elevesPremierRedoublantCycle = new ArrayList<>();
          elevesPremierRedoublantCycle= resultatsRedoubServices1.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,trimestre,eleve.getNiveau());

           if(!elevesPremierRedoublantCycle.isEmpty()) {
             nombreMoyInf10RedoubG=elevesPremierRedoublantCycle.get(0).getNbreMoyInf999G() ;
             nombreMoyInf10RedoubF=elevesPremierRedoublantCycle.get(0).getNbreMoyInf999F() ;
             nombreMoyInf8_5RedoubG=elevesPremierRedoublantCycle.get(0).getNbreMoyInf85G();
             nombreMoyInf8_5RedoubF=elevesPremierRedoublantCycle.get(0).getNbreMoyInf85F();
           }





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

          if(!elevesPremierRedoublantCycle.isEmpty()) {
            long nombMoyInf10GRedoublantG =elevesPremierRedoublantCycle.get(0).getNbreMoyInf999G() != null ? elevesPremierRedoublantCycle.get(0).getNbreMoyInf999G() : 0L;
            nombMoyInf10RedoublCycle1G=nombMoyInf10RedoublCycle1G+nombMoyInf10GRedoublantG;

            long nombMoyInf10RedoublantF = (elevesPremierRedoublantCycle.get(0).getNbreMoyInf999F() != null ? elevesPremierRedoublantCycle.get(0).getNbreMoyInf999F() : 0L) ;
            nombMoyInf10RedoublCycle1F=nombMoyInf10RedoublCycle1F+nombMoyInf10RedoublantF;
          }







          long nombMoyInf8_5 =
              (eleve.getNbreMoyInf85F() != null ? eleve.getNbreMoyInf85F() : 0L) +
                  (eleve.getNbreMoyInf85G() != null ? eleve.getNbreMoyInf85G() : 0L);
          nombMoyInf8_5Cycle1=nombMoyInf8_5Cycle1+nombMoyInf8_5;

          long nombMoyInf8_5G =
                  (eleve.getNbreMoyInf85G() != null ? eleve.getNbreMoyInf85G() : 0L);
          nombMoyInf8_5Cycle1G=nombMoyInf8_5Cycle1G+nombMoyInf8_5G;

          long nombMoyInf8_5F =(eleve.getNbreMoyInf85F() != null ? eleve.getNbreMoyInf85F() : 0L) ;
          nombMoyInf8_5Cycle1F=nombMoyInf8_5Cycle1F+nombMoyInf8_5F;

          if(!elevesPremierRedoublantCycle.isEmpty()) {
            long nombMoyInf8_5GRedoublant =
                (elevesPremierRedoublantCycle.get(0).getNbreMoyInf85G() != null ? elevesPremierRedoublantCycle.get(0).getNbreMoyInf85G() : 0L);
            nombMoyInf8_5RedoublCycle1G=nombMoyInf8_5RedoublCycle1G+nombMoyInf8_5GRedoublant;

            long nombMoyInf8_5FRedoublant =(elevesPremierRedoublantCycle.get(0).getNbreMoyInf85F() != null ? elevesPremierRedoublantCycle.get(0).getNbreMoyInf85F() : 0L) ;
            nombMoyInf8_5RedoublCycle1F=nombMoyInf8_5RedoublCycle1F+nombMoyInf8_5FRedoublant;
          }




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




          int nombreClasseParNiveau = getNombreDeclasseParNiveau(idEcole, libelleAnnee,trimestre,eleve.getNiveau());
          nombreTotalClasseParNiveau1= nombreTotalClasseParNiveau1+nombreClasseParNiveau ;
          // Remplir la ligne pour les filles (F)
          XWPFTableRow row1 = table.createRow();
          ensureCellCount(row1, 17);
          row1.getCell(0).setText(eleve.getNiveau());
          row1.getCell(1).setText(String.valueOf(eleve.getNbreMoySup10G()));
          row1.getCell(2).setText(String.valueOf(eleve.getNbreMoySup10F()));
          row1.getCell(3).setText(String.valueOf(eleve.getNbreMoySup10G()+eleve.getNbreMoySup10F()));
          row1.getCell(4).setText(String.valueOf(eleve.getNbreMoyInf999G()));
          row1.getCell(5).setText(String.valueOf(eleve.getNbreMoyInf999F()));
          row1.getCell(6).setText(String.valueOf(eleve.getNbreMoyInf999G()+eleve.getNbreMoyInf999F()));
          if(!elevesPremierRedoublantCycle.isEmpty()) {
            row1.getCell(7).setText(String.valueOf(elevesPremierRedoublantCycle.get(0).getNbreMoyInf999G()));
            row1.getCell(8).setText(String.valueOf(elevesPremierRedoublantCycle.get(0).getNbreMoyInf999F()));
            row1.getCell(9).setText(String.valueOf(elevesPremierRedoublantCycle.get(0).getNbreMoyInf999G()+
                elevesPremierRedoublantCycle.get(0).getNbreMoyInf999F()));
          } else {
            row1.getCell(7).setText(String.valueOf("00"));
            row1.getCell(8).setText(String.valueOf("00"));
            row1.getCell(9).setText(String.valueOf("00"));
          }


          row1.getCell(10).setText(String.valueOf(eleve.getNbreMoyInf85G()));
          row1.getCell(11).setText(String.valueOf(eleve.getNbreMoyInf85F()));
          row1.getCell(12).setText(String.valueOf(eleve.getNbreMoyInf85G()+eleve.getNbreMoyInf85F()));
          if(!elevesPremierRedoublantCycle.isEmpty()) {
            row1.getCell(13).setText(String.valueOf(elevesPremierRedoublantCycle.get(0).getNbreMoyInf85G()));
            row1.getCell(14).setText(String.valueOf(elevesPremierRedoublantCycle.get(0).getNbreMoyInf85F()));
            row1.getCell(15).setText(String.valueOf(elevesPremierRedoublantCycle.get(0).getNbreMoyInf85G()+
                elevesPremierRedoublantCycle.get(0).getNbreMoyInf85F()));
            row1.getCell(16).setText(String.valueOf(elevesPremierRedoublantCycle.get(0).getNbreMoyInf999G()+
                elevesPremierRedoublantCycle.get(0).getNbreMoyInf999F()+
                elevesPremierRedoublantCycle.get(0).getNbreMoyInf85G()+elevesPremierRedoublantCycle.get(0).getNbreMoyInf85F()+
                eleve.getNbreMoyInf85G()+eleve.getNbreMoyInf85F()));
          } else {
            row1.getCell(13).setText(String.valueOf("00"));
            row1.getCell(14).setText(String.valueOf("00"));
            row1.getCell(15).setText(String.valueOf("00"));
            row1.getCell(16).setText(String.valueOf(00+eleve.getNbreMoyInf85G()+eleve.getNbreMoyInf85F()));

          }




        }
      }
      XWPFTableRow rowCycle1 = table.createRow();
      ensureCellCount(rowCycle1, 17);
      rowCycle1.getCell(0).setText("Total 1er Cycle");
      rowCycle1.getCell(1).setText(String.valueOf(nombMoySup10Cycle1G));
      rowCycle1.getCell(2).setText(String.valueOf(nombMoySup10Cycle1F));
      rowCycle1.getCell(3).setText(String.valueOf(nombMoySup10Cycle1G+nombMoySup10Cycle1F));
      rowCycle1.getCell(4).setText(String.valueOf(nombMoyInf10Cycle1G));
      rowCycle1.getCell(5).setText(String.valueOf(nombMoyInf10Cycle1F));
      rowCycle1.getCell(6).setText(String.valueOf(nombMoyInf10Cycle1F+nombMoyInf10Cycle1G));
      rowCycle1.getCell(7).setText(String.valueOf(nombMoyInf10RedoublCycle1G));
      rowCycle1.getCell(8).setText(String.valueOf(nombMoyInf10RedoublCycle1F));
      rowCycle1.getCell(9).setText(String.valueOf(nombMoyInf10RedoublCycle1G+nombMoyInf10RedoublCycle1F));
      rowCycle1.getCell(10).setText(String.valueOf(nombMoyInf8_5Cycle1G));
      rowCycle1.getCell(11).setText(String.valueOf(nombMoyInf8_5Cycle1F));
      rowCycle1.getCell(12).setText(String.valueOf(nombMoyInf8_5Cycle1G+nombMoyInf8_5Cycle1F));
      rowCycle1.getCell(13).setText(String.valueOf(nombMoyInf8_5RedoublCycle1G));
      rowCycle1.getCell(14).setText(String.valueOf(nombMoyInf8_5RedoublCycle1F));
      rowCycle1.getCell(15).setText(String.valueOf(nombMoyInf8_5RedoublCycle1G+nombMoyInf8_5RedoublCycle1F));
      rowCycle1.getCell(16).setText(String.valueOf(nombMoyInf10RedoublCycle1G+nombMoyInf10RedoublCycle1F+
          nombMoyInf8_5Cycle1G+nombMoyInf8_5Cycle1F+nombMoyInf8_5RedoublCycle1G+nombMoyInf8_5RedoublCycle1F));



      if(!elevesSecondCycle.isEmpty()){

        //Second cycle
      for (RecapResultatsElevesAffeEtNonAffDto eleve : elevesSecondCycle) {


        List<RecapResultatsElevesAffeEtNonAffDto> elevesSecondRedoublantCycle = new ArrayList<>();

        elevesSecondRedoublantCycle= resultatsRedoubServices2.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,trimestre ,eleve.getNiveau());



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


        if(!elevesSecondRedoublantCycle.isEmpty()){
          long nombMoyInf10GRedoublantG =elevesSecondRedoublantCycle.get(0).getNbreMoyInf999G() != null ? elevesSecondRedoublantCycle.get(0).getNbreMoyInf999G() : 0L;
          nombMoyInf10RedoublCycle2G=nombMoyInf10RedoublCycle2G+nombMoyInf10GRedoublantG;

          long nombMoyInf10RedoublantF = (elevesSecondRedoublantCycle.get(0).getNbreMoyInf999F() != null ? elevesSecondRedoublantCycle.get(0).getNbreMoyInf999F() : 0L) ;
          nombMoyInf10RedoublCycle2F=nombMoyInf10RedoublCycle2F+nombMoyInf10RedoublantF;
        }

        long nombMoyInf8_5 =
            (eleve.getNbreMoyInf85F() != null ? eleve.getNbreMoyInf85F() : 0L) +
                (eleve.getNbreMoyInf85G() != null ? eleve.getNbreMoyInf85G() : 0L);
        nombMoyInf8_5Cycle2=nombMoyInf8_5Cycle2+nombMoyInf8_5;

        long nombMoyInf8_5G =
            (eleve.getNbreMoyInf85G() != null ? eleve.getNbreMoyInf85G() : 0L);
        nombMoyInf8_5Cycle2G=nombMoyInf8_5Cycle2G+nombMoyInf8_5G;

        long nombMoyInf8_5F =(eleve.getNbreMoyInf85F() != null ? eleve.getNbreMoyInf85F() : 0L) ;
        nombMoyInf8_5Cycle2F=nombMoyInf8_5Cycle2F+nombMoyInf8_5F;

        if(!elevesSecondRedoublantCycle.isEmpty()){
          long nombMoyInf8_5GRedoublant =
              (elevesSecondRedoublantCycle.get(0).getNbreMoyInf85G() != null ? elevesSecondRedoublantCycle.get(0).getNbreMoyInf85G() : 0L);
          nombMoyInf8_5RedoublCycle2G=nombMoyInf8_5RedoublCycle2G+nombMoyInf8_5GRedoublant;

          long nombMoyInf8_5FRedoublant =(elevesSecondRedoublantCycle.get(0).getNbreMoyInf85F() != null ? elevesSecondRedoublantCycle.get(0).getNbreMoyInf85F() : 0L) ;
          nombMoyInf8_5RedoublCycle2F=nombMoyInf8_5RedoublCycle2F+nombMoyInf8_5FRedoublant;
        }





        int nombreClasseParNiveau = getNombreDeclasseParNiveau(idEcole, libelleAnnee,trimestre,eleve.getNiveau()) ;
        nombreTotalClasseParNiveau2= nombreTotalClasseParNiveau2+nombreClasseParNiveau;
        // Remplir la ligne pour les filles (F)
        XWPFTableRow row2 = table.createRow();
            ensureCellCount(row2, 17);
            row2.getCell(0).setText(eleve.getNiveau());
            row2.getCell(1).setText(String.valueOf(eleve.getNbreMoySup10G()));
            row2.getCell(2).setText(String.valueOf(eleve.getNbreMoySup10F()));
            row2.getCell(3).setText(String.valueOf(eleve.getNbreMoySup10G()+eleve.getNbreMoySup10F()));
            row2.getCell(4).setText(String.valueOf(eleve.getNbreMoyInf999G()));
            row2.getCell(5).setText(String.valueOf(eleve.getNbreMoyInf999F()));
            row2.getCell(6).setText(String.valueOf(eleve.getNbreMoyInf999G()+eleve.getNbreMoyInf999F()));
        if(!elevesSecondRedoublantCycle.isEmpty()){
          row2.getCell(7).setText(String.valueOf(elevesSecondRedoublantCycle.get(0).getNbreMoyInf999G()));
          row2.getCell(8).setText(String.valueOf(elevesSecondRedoublantCycle.get(0).getNbreMoyInf999F()));
          row2.getCell(9).setText(String.valueOf(elevesSecondRedoublantCycle.get(0).getNbreMoyInf999G()+elevesSecondRedoublantCycle.get(0).getNbreMoyInf999F()));
        } else {
          row2.getCell(7).setText(String.valueOf("00"));
          row2.getCell(8).setText(String.valueOf("00"));
          row2.getCell(9).setText(String.valueOf("00"));
        }

            row2.getCell(10).setText(String.valueOf(eleve.getNbreMoyInf85G()));
            row2.getCell(11).setText(String.valueOf(eleve.getNbreMoyInf85F()));
            row2.getCell(12).setText(String.valueOf(eleve.getNbreMoyInf85G()+eleve.getNbreMoyInf85F()));
        if(!elevesSecondRedoublantCycle.isEmpty()){
          row2.getCell(13).setText(String.valueOf(elevesSecondRedoublantCycle.get(0).getNbreMoyInf85G()));
          row2.getCell(14).setText(String.valueOf(elevesSecondRedoublantCycle.get(0).getNbreMoyInf85F()));
          row2.getCell(15).setText(String.valueOf(elevesSecondRedoublantCycle.get(0).getNbreMoyInf85G()+elevesSecondRedoublantCycle.get(0).getNbreMoyInf85F()));
          row2.getCell(16).setText(String.valueOf(elevesSecondRedoublantCycle.get(0).getNbreMoyInf999G()+elevesSecondRedoublantCycle.get(0).getNbreMoyInf999F()+
              elevesSecondRedoublantCycle.get(0).getNbreMoyInf85G()+elevesSecondRedoublantCycle.get(0).getNbreMoyInf85F()+eleve.getNbreMoyInf85G()+eleve.getNbreMoyInf85F()));
        } else {
          row2.getCell(13).setText(String.valueOf("00"));
          row2.getCell(14).setText(String.valueOf("00"));
          row2.getCell(15).setText(String.valueOf("00"));
          row2.getCell(16).setText(String.valueOf(0+eleve.getNbreMoyInf85G()+eleve.getNbreMoyInf85F()));
        }




      }
        XWPFTableRow rowCycle2 = table.createRow();
        ensureCellCount(rowCycle2, 17);
        rowCycle2.getCell(0).setText("Total 2nd Cycle");
        rowCycle2.getCell(1).setText(String.valueOf(nombMoySup10Cycle2G));
        rowCycle2.getCell(2).setText(String.valueOf(nombMoySup10Cycle2F));
        rowCycle2.getCell(3).setText(String.valueOf(nombMoySup10Cycle2G+nombMoySup10Cycle2F));
        rowCycle2.getCell(4).setText(String.valueOf(nombMoyInf10Cycle2G));
        rowCycle2.getCell(5).setText(String.valueOf(nombMoyInf10Cycle2F));
        rowCycle2.getCell(6).setText(String.valueOf(nombMoyInf10Cycle2F+nombMoyInf10Cycle2G));
        rowCycle2.getCell(7).setText(String.valueOf(nombMoyInf10RedoublCycle2G));
        rowCycle2.getCell(8).setText(String.valueOf(nombMoyInf10RedoublCycle2F));
        rowCycle2.getCell(9).setText(String.valueOf(nombMoyInf10RedoublCycle2G+nombMoyInf10RedoublCycle2F));
        rowCycle2.getCell(10).setText(String.valueOf(nombMoyInf8_5Cycle2G));
        rowCycle2.getCell(11).setText(String.valueOf(nombMoyInf8_5Cycle2F));
        rowCycle2.getCell(12).setText(String.valueOf(nombMoyInf8_5Cycle2G+nombMoyInf8_5Cycle2F));
        rowCycle2.getCell(13).setText(String.valueOf(nombMoyInf8_5RedoublCycle2G));
        rowCycle2.getCell(14).setText(String.valueOf(nombMoyInf8_5RedoublCycle2F));
        rowCycle2.getCell(15).setText(String.valueOf(nombMoyInf8_5RedoublCycle2G+nombMoyInf8_5RedoublCycle2F));
        rowCycle2.getCell(16).setText(String.valueOf(nombMoyInf10RedoublCycle2G+nombMoyInf10RedoublCycle2F+
            nombMoyInf8_5Cycle2G+nombMoyInf8_5Cycle2F+nombMoyInf8_5RedoublCycle2G+nombMoyInf8_5RedoublCycle2F));
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




      XWPFTableRow generelRow = table.createRow();
      ensureCellCount(generelRow, 17);
      generelRow.getCell(0).setText("Total Genral");
      generelRow.getCell(1).setText(String.valueOf(nombMoySup10Cycle1G+nombMoySup10Cycle2G));
      generelRow.getCell(2).setText(String.valueOf(nombMoySup10Cycle1F+nombMoySup10Cycle2F));
      generelRow.getCell(3).setText(String.valueOf(nombMoySup10Cycle1G+nombMoySup10Cycle1F+nombMoySup10Cycle2G+nombMoySup10Cycle2F));
      generelRow.getCell(4).setText(String.valueOf(nombMoyInf10Cycle1G+nombMoyInf10Cycle2G));
      generelRow.getCell(5).setText(String.valueOf(nombMoyInf10Cycle1F+nombMoyInf10Cycle2F));
      generelRow.getCell(6).setText(String.valueOf(nombMoyInf10Cycle1F+nombMoyInf10Cycle1G+nombMoyInf10Cycle2F+nombMoyInf10Cycle2G));
      generelRow.getCell(7).setText(String.valueOf(nombMoyInf10RedoublCycle1G+nombMoyInf10RedoublCycle2G));
      generelRow.getCell(8).setText(String.valueOf(nombMoyInf10RedoublCycle1F+nombMoyInf10RedoublCycle2F));
      generelRow.getCell(9).setText(String.valueOf(nombMoyInf10RedoublCycle1G+nombMoyInf10RedoublCycle1F+nombMoyInf10RedoublCycle2G+nombMoyInf10RedoublCycle2F));
      generelRow.getCell(10).setText(String.valueOf(nombMoyInf8_5Cycle1G+nombMoyInf8_5Cycle2G));
      generelRow.getCell(11).setText(String.valueOf(nombMoyInf8_5Cycle1F+nombMoyInf8_5Cycle2F));
      generelRow.getCell(12).setText(String.valueOf(nombMoyInf8_5Cycle1G+nombMoyInf8_5Cycle1F+nombMoyInf8_5Cycle2G+nombMoyInf8_5Cycle2F));
      generelRow.getCell(13).setText(String.valueOf(nombMoyInf8_5RedoublCycle1G+nombMoyInf8_5RedoublCycle2G));
      generelRow.getCell(14).setText(String.valueOf(nombMoyInf8_5RedoublCycle1F+nombMoyInf8_5RedoublCycle2F));
      generelRow.getCell(15).setText(String.valueOf(nombMoyInf8_5RedoublCycle1G+nombMoyInf8_5RedoublCycle1F+nombMoyInf8_5RedoublCycle2G+nombMoyInf8_5RedoublCycle2F));
      generelRow.getCell(16).setText(String.valueOf(nombMoyInf10RedoublCycle1G+nombMoyInf10RedoublCycle1F+
          nombMoyInf8_5Cycle1G+nombMoyInf8_5Cycle1F+nombMoyInf8_5RedoublCycle1G+nombMoyInf8_5RedoublCycle1F +
              nombMoyInf10RedoublCycle2G+nombMoyInf10RedoublCycle2F+
              nombMoyInf8_5Cycle2G+nombMoyInf8_5Cycle2F+nombMoyInf8_5RedoublCycle2G+nombMoyInf8_5RedoublCycle2F
          ));




   // mergeCellsVertically(table, 0, 1, table.getNumberOfRows()-1 );
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

package com.vieecoles.processors.dren3;

import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.RecapResultatsElevesAffeEtNonAffDto;
import com.vieecoles.processors.dren3.services.resultatsRecapAffEtNonAffCycle1Services ;
import com.vieecoles.processors.dren3.services.resultatsRecapAffEtNonAffCycle2Services;
import com.vieecoles.steph.entities.Ecole;
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
public class WordTempRecapAffNonAffResultatProcessor {
    @Inject
    EntityManager em;
    @Inject
    resultatsRecapAffEtNonAffCycle1Services resultatsRecapCycle1Services ;
    @Inject
    resultatsRecapAffEtNonAffCycle2Services resultatsRecapCycle2Services ;

    public   void getRecapResultatAffProcessor(XWPFDocument document ,
                                          Long idEcole ,String libelleAnnee , String libelleTrimetre) {
        XWPFTable table = document.getTableArray(8);
        List<RecapResultatsElevesAffeEtNonAffDto> detailsCycle1= new ArrayList<>();
        List<RecapResultatsElevesAffeEtNonAffDto> detailsCycle2= new ArrayList<>();

        long nombMoySup10Cycle1 =0l ; long nombMoyInf10Cycle1 =0l ;long nombMoyInf8_5Cycle1 =0l;
        long effectifClasseCycle1=0l ;  long effNonClassCycle1=0l;

        double pourSup10Cycle1= 0d,pourInf10Cycle1 = 0d,pourInf8_5Cycle1 = 0d;
        Long effectifCycle1 = 0L;
        Long effectifNonClasseCycle1 = 0L;
        Double moyCycle1=0d; Double moyCycle2=0d;

        long nombMoySup10Cycle2 =0l ; long nombMoyInf10Cycle2 =0l ;long nombMoyInf8_5Cycle2 =0l;
        long effectifClasseCycle2=0l ;   long effNonClassCycle2=0l;

        double pourSup10Cycle2= 0d,pourInf10Cycle2 = 0d,pourInf8_5Cycle2 = 0d;
        Long effectifCycle2 = 0L;
        Long effectifNonClasseCycle2 = 0L;

        long nombMoySup10Cycle =0l ; long nombMoyInf10Cycle =0l ;long nombMoyInf8_5Cycle =0l;
        long effectifClasseCycle=0l ;   long effNonClassCycle=0l;

        double pourSup10Cycle= 0d,pourInf10Cycle = 0d,pourInf8_5Cycle = 0d;
        Long effectifCycle = 0L;
        Long effectifNonClasseCycle = 0L;


        detailsCycle1=   resultatsRecapCycle1Services.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre);
        detailsCycle2=   resultatsRecapCycle2Services.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre);

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.libelleClasse) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee and b.ordreNiveau<5 " +
            "group by b.libelleClasse", NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
            .setParameter("annee", libelleAnnee)
            .setParameter("periode", libelleTrimetre)
            . getResultList() ;

        List<NiveauDto> classeNiveauDtoList2 = new ArrayList<>() ;
        TypedQuery<NiveauDto> qi = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.libelleClasse) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee and b.ordreNiveau>=5 " +
            "group by b.libelleClasse", NiveauDto.class);
        classeNiveauDtoList2 = qi.setParameter("idEcole", idEcole)
            .setParameter("annee", libelleAnnee)
            .setParameter("periode", libelleTrimetre)
            . getResultList() ;

if(!detailsCycle1.isEmpty()){
    System.out.println("taille Infos detailsCycle1++++ "+detailsCycle1.size());


        for (RecapResultatsElevesAffeEtNonAffDto classe : detailsCycle1) {
        /*    Long effectifNiveau1=classe.getEffeG()+classe.getEffeF();
            Long effectifClasse1 =classe.getClassF()+classe.getClassG();
            Long effectifNonClasse1 =classe.getNonclassF()+classe.getNonclassG();


            effectifClasseCycle1=effectifClasseCycle1+effectifClasse1;
             effectifCycle1= effectifCycle1+effectifNiveau1;
            effectifNonClasseCycle1= effectifNonClasseCycle1+effectifNonClasse1;*/

            long nombMoySup10 =
                (classe.getNbreMoySup10F() != null ? classe.getNbreMoySup10F() : 0L) +
                    (classe.getNbreMoySup10G() != null ? classe.getNbreMoySup10G() : 0L);
            nombMoySup10Cycle1=nombMoySup10Cycle1+nombMoySup10;

            long nombMoyInf10 =
                (classe.getNbreMoyInf999F() != null ? classe.getNbreMoyInf999F() : 0L) +
                    (classe.getNbreMoyInf999G() != null ? classe.getNbreMoyInf999G() : 0L);
            nombMoyInf10Cycle1=nombMoyInf10Cycle1+nombMoyInf10;

            long nombMoyInf8_5 =
                (classe.getNbreMoyInf85F() != null ? classe.getNbreMoyInf85F() : 0L) +
                    (classe.getNbreMoyInf85G() != null ? classe.getNbreMoyInf85G() : 0L);
            nombMoyInf8_5Cycle1=nombMoyInf8_5Cycle1+nombMoyInf8_5;

            long effectifClasse =
                (classe.getClassF() != null ? classe.getClassF() : 0L) +
                    (classe.getClassG() != null ? classe.getClassG() : 0L);
            effectifClasseCycle1=effectifClasseCycle1+effectifClasse;

            long effectifNonClasse =
                (classe.getNonclassF() != null ? classe.getNonclassF() : 0L) +
                    (classe.getNonclassG() != null ? classe.getNonclassG() : 0L);
            effectifNonClasseCycle1=effectifNonClasseCycle1+effectifNonClasse;

            long effectif =
                (classe.getEffeF() != null ? classe.getEffeF() : 0L) +
                    (classe.getEffeG() != null ? classe.getEffeG() : 0L);
            effectifCycle1=effectifCycle1+effectif;

        }
}
  // Mise à jour cycle 2
        if(!detailsCycle2.isEmpty()){
            System.out.println("taille Infos detailsCycle2++++ "+detailsCycle2.size());


        for (RecapResultatsElevesAffeEtNonAffDto classe : detailsCycle2) {
          /*  Long effectifNiveau2=classe.getEffeG()+classe.getEffeF();
            Long effectifClasse2 =classe.getClassF()+classe.getClassG();
            Long effectifNonClasse2 =classe.getNonclassF()+classe.getNonclassG();


            effectifClasseCycle2=effectifClasseCycle2+effectifClasse2;
            effectifCycle2= effectifCycle2+effectifNiveau2;
            effectifNonClasseCycle2= effectifNonClasseCycle2+effectifNonClasse2;*/

            long nombMoySup10 =
                (classe.getNbreMoySup10F() != null ? classe.getNbreMoySup10F() : 0L) +
                    (classe.getNbreMoySup10G() != null ? classe.getNbreMoySup10G() : 0L);
            nombMoySup10Cycle2=nombMoySup10Cycle2+nombMoySup10;

            long nombMoyInf10 =
                (classe.getNbreMoyInf999F() != null ? classe.getNbreMoyInf999F() : 0L) +
                    (classe.getNbreMoyInf999G() != null ? classe.getNbreMoyInf999G() : 0L);
            nombMoyInf10Cycle2=nombMoyInf10Cycle2+nombMoyInf10;

            long nombMoyInf8_5 =
                (classe.getNbreMoyInf85F() != null ? classe.getNbreMoyInf85F() : 0L) +
                    (classe.getNbreMoyInf85G() != null ? classe.getNbreMoyInf85G() : 0L);
            nombMoyInf8_5Cycle2=nombMoyInf8_5Cycle2+nombMoyInf8_5;

            long effectifClasse =
                (classe.getClassF() != null ? classe.getClassF() : 0L) +
                    (classe.getClassG() != null ? classe.getClassG() : 0L);
            effectifClasseCycle2=effectifClasseCycle2+effectifClasse;

            long effectifNonClasse =
                (classe.getNonclassF() != null ? classe.getNonclassF() : 0L) +
                    (classe.getNonclassG() != null ? classe.getNonclassG() : 0L);
            effectifNonClasseCycle2=effectifNonClasseCycle2+effectifNonClasse;

            long effectif =
                (classe.getEffeF() != null ? classe.getEffeF() : 0L) +
                    (classe.getEffeG() != null ? classe.getEffeG() : 0L);
            effectifCycle2=effectifCycle2+effectif;

        }
        }
        if(!detailsCycle1.isEmpty()){
            moyCycle1=detailsCycle1.get(0).getMoyClasse();
        }

        if(!detailsCycle2.isEmpty()){
            moyCycle2=detailsCycle2.get(0).getMoyClasse();
        }


        Ecole libelleEcole = new Ecole();
        libelleEcole= Ecole.findById(idEcole);

        effectifClasseCycle=effectifClasseCycle1+effectifClasseCycle2;
        effectifNonClasseCycle=effectifNonClasseCycle1+effectifNonClasseCycle2;
        effectifCycle= effectifCycle1+effectifCycle2;

        nombMoySup10Cycle=nombMoySup10Cycle1+nombMoySup10Cycle2;
        nombMoyInf10Cycle=nombMoyInf10Cycle1+nombMoyInf10Cycle2;
        nombMoyInf8_5Cycle=nombMoyInf8_5Cycle1+nombMoyInf8_5Cycle2;

     // Pourcentage cycle 1
        if (effectifClasseCycle1 > 0) {
            pourSup10Cycle1 = nombMoySup10Cycle1 / (Double.valueOf(effectifClasseCycle1)) * 100d;
            pourInf10Cycle1 = nombMoyInf10Cycle1 / (Double.valueOf(effectifClasseCycle1)) * 100d;
            pourInf8_5Cycle1 = nombMoyInf8_5Cycle1 / (Double.valueOf(effectifClasseCycle1)) * 100d;

            // Calculer d'autres statistiques ou utiliser ces valeurs
        } else {
            // Gérer le cas où effectifClasse est 0 pour éviter une division par zéro
            pourSup10Cycle1 = 0d;
            pourInf10Cycle1 = 0d;
            pourInf8_5Cycle1 = 0d;
            // Logique alternative ou gestion d'erreur
        }

        // Pourcentage cycle 2
        if (effectifClasseCycle2 > 0) {
            pourSup10Cycle2 = nombMoySup10Cycle2 / (Double.valueOf(effectifClasseCycle2)) * 100d;
            pourInf10Cycle2 = nombMoyInf10Cycle2 / (Double.valueOf(effectifClasseCycle2)) * 100d;
            pourInf8_5Cycle2 = nombMoyInf8_5Cycle2 / (Double.valueOf(effectifClasseCycle2)) * 100d;

            // Calculer d'autres statistiques ou utiliser ces valeurs
        } else {
            // Gérer le cas où effectifClasse est 0 pour éviter une division par zéro
            pourSup10Cycle2 = 0d;
            pourInf10Cycle2 = 0d;
            pourInf8_5Cycle2 = 0d;
            // Logique alternative ou gestion d'erreur
        }
        if (effectifClasseCycle > 0) {
            pourSup10Cycle = nombMoySup10Cycle / (Double.valueOf(effectifClasseCycle)) * 100d;
            pourInf10Cycle = nombMoyInf10Cycle / (Double.valueOf(effectifClasseCycle)) * 100d;
            pourInf8_5Cycle = nombMoyInf8_5Cycle / (Double.valueOf(effectifClasseCycle)) * 100d;

            // Calculer d'autres statistiques ou utiliser ces valeurs
        } else {
            // Gérer le cas où effectifClasse est 0 pour éviter une division par zéro
            pourSup10Cycle = 0d;
            pourInf10Cycle = 0d;
            pourInf8_5Cycle = 0d;
            // Logique alternative ou gestion d'erreur
        }



        long nombreClasseCycle1 = classeNiveauDtoList.size();
        XWPFTableRow firstRow = table.getRow(2); // Obtenir la première ligne
        if (firstRow != null) {
            XWPFTableCell colonne0 = firstRow.getCell(0);
            XWPFTableCell colonne1 = firstRow.getCell(2); // Obtenir la première cellule
            XWPFTableCell colonne2 = firstRow.getCell(3);
            XWPFTableCell colonne3 = firstRow.getCell(4);
            XWPFTableCell colonne4 = firstRow.getCell(5);
            XWPFTableCell colonne5 = firstRow.getCell(6);
            XWPFTableCell colonne6 = firstRow.getCell(7);
            XWPFTableCell colonne7= firstRow.getCell(8);
            XWPFTableCell colonne8 = firstRow.getCell(9);
            XWPFTableCell colonne9 = firstRow.getCell(10);
            XWPFTableCell colonne10 = firstRow.getCell(11);
            XWPFTableCell colonne11 = firstRow.getCell(12);
            XWPFTableCell colonne12 = firstRow.getCell(13);

            if (colonne0 != null) {
                colonne0.setText(String.valueOf(libelleEcole.getLibelle()));
            }
            if (colonne1 != null) {
                colonne1.setText(String.valueOf(nombreClasseCycle1));
            }
            if (colonne2 != null) {
                colonne2.setText(String.valueOf(effectifCycle1));
            }
            if (colonne3 != null) {
                colonne3.setText(String.valueOf(effectifClasseCycle1));
            }
            if (colonne4 != null) {
                colonne4.setText(String.valueOf(effNonClassCycle1));
            }
            if (colonne5 != null) {
                colonne5.setText(String.valueOf(nombMoySup10Cycle1));
            }
            if (colonne6 != null) {
                colonne6.setText( String.format("%.2f", pourSup10Cycle1));
            }
            if (colonne7 != null) {
                colonne7.setText(String.valueOf(nombMoyInf10Cycle1));
            }
            if (colonne8 != null) {
                colonne8.setText(String.format("%.2f", pourInf10Cycle1));
            }

            if (colonne9 != null) {
                colonne9.setText(String.valueOf(nombMoyInf8_5Cycle1));
            }
            if (colonne10 != null) {
                colonne10.setText(String.format("%.2f", pourInf8_5Cycle1));
            }
            if (colonne11 != null) {
                colonne11.setText(String.format("%.2f", moyCycle2));
            }
            if (colonne12 != null) {
                colonne12.setText(String.valueOf(""));
            }

        }
        long nombreClasseCycle2 = classeNiveauDtoList2.size();
        XWPFTableRow secondeRow = table.getRow(3); // Obtenir la première ligne
        if (secondeRow != null) {
            XWPFTableCell colonne1 = secondeRow.getCell(2); // Obtenir la première cellule
            XWPFTableCell colonne2 = secondeRow.getCell(3);
            XWPFTableCell colonne3 = secondeRow.getCell(4);
            XWPFTableCell colonne4 = secondeRow.getCell(5);
            XWPFTableCell colonne5 = secondeRow.getCell(6);
            XWPFTableCell colonne6 = secondeRow.getCell(7);
            XWPFTableCell colonne7= secondeRow.getCell(8);
            XWPFTableCell colonne8 = secondeRow.getCell(9);
            XWPFTableCell colonne9 = secondeRow.getCell(10);
            XWPFTableCell colonne10 = secondeRow.getCell(11);
            XWPFTableCell colonne11 = secondeRow.getCell(12);
            XWPFTableCell colonne12 = secondeRow.getCell(13);
            if (colonne1 != null) {
                colonne1.setText(String.valueOf(nombreClasseCycle2));
            }
            if (colonne2 != null) {
                colonne2.setText(String.valueOf(effectifCycle2));
            }
            if (colonne3 != null) {
                colonne3.setText(String.valueOf(effectifClasseCycle2));
            }
            if (colonne4 != null) {
                colonne4.setText(String.valueOf(effNonClassCycle2));
            }
            if (colonne5 != null) {
                colonne5.setText(String.valueOf(nombMoySup10Cycle2));
            }
            if (colonne6 != null) {
                colonne6.setText(String.format("%.2f", pourSup10Cycle2));
            }
            if (colonne7 != null) {
                colonne7.setText(String.valueOf(nombMoyInf10Cycle2));
            }
            if (colonne8 != null) {
                colonne8.setText(String.format("%.2f", pourInf10Cycle2));
            }

            if (colonne9 != null) {
                colonne9.setText(String.valueOf(nombMoyInf8_5Cycle2));
            }
            if (colonne10 != null) {
                colonne10.setText(String.format("%.2f", pourInf8_5Cycle2));
            }
            if (colonne11 != null) {
                colonne11.setText(String.format("%.2f", moyCycle2));
            }
            if (colonne12 != null) {
                colonne12.setText(String.valueOf(""));
            }

        }
//Total general
    long    nombreClasseCycle=nombreClasseCycle1+nombreClasseCycle2;
        Double moyCycle= ((moyCycle1+moyCycle2)/2d);
        XWPFTableRow thirdRow = table.getRow(4);
        if (thirdRow != null) {
            XWPFTableCell colonne1 = thirdRow.getCell(2); // Obtenir la première cellule
            XWPFTableCell colonne2 = thirdRow.getCell(3);
            XWPFTableCell colonne3 = thirdRow.getCell(4);
            XWPFTableCell colonne4 = thirdRow.getCell(5);
            XWPFTableCell colonne5 = thirdRow.getCell(6);
            XWPFTableCell colonne6 = thirdRow.getCell(7);
            XWPFTableCell colonne7= thirdRow.getCell(8);
            XWPFTableCell colonne8 = thirdRow.getCell(9);
            XWPFTableCell colonne9 = thirdRow.getCell(10);
            XWPFTableCell colonne10 = thirdRow.getCell(11);
            XWPFTableCell colonne11 = thirdRow.getCell(12);
            XWPFTableCell colonne12 = thirdRow.getCell(13);
            if (colonne1 != null) {
                colonne1.setText(String.valueOf(nombreClasseCycle));
            }
            if (colonne2 != null) {
                colonne2.setText(String.valueOf(effectifCycle));
            }
            if (colonne3 != null) {
                colonne3.setText(String.valueOf(effectifClasseCycle));
            }
            if (colonne4 != null) {
                colonne4.setText(String.valueOf(effNonClassCycle));
            }
            if (colonne5 != null) {
                colonne5.setText(String.valueOf(nombMoySup10Cycle));
            }
            if (colonne6 != null) {
                colonne6.setText(String.format("%.2f", pourSup10Cycle) );
            }
            if (colonne7 != null) {
                colonne7.setText(String.valueOf(nombMoyInf10Cycle));
            }
            if (colonne8 != null) {
                colonne8.setText(String.format("%.2f", pourInf10Cycle));
            }

            if (colonne9 != null) {
                colonne9.setText(String.valueOf(nombMoyInf8_5Cycle));
            }
            if (colonne10 != null) {
                colonne10.setText(String.format("%.2f", pourInf8_5Cycle));
            }
            if (colonne11 != null) {
                colonne11.setText(String.format("%.2f", moyCycle));
            }
            if (colonne12 != null) {
                colonne12.setText(String.valueOf(""));
            }

        }




    }



    private static void ensureCellCount(XWPFTableRow row, int cellCount) {
        int currentCellCount = row.getTableCells().size();
        for (int i = currentCellCount; i < cellCount; i++) {
            row.addNewTableCell(); // Ajouter une nouvelle cellule si nécessaire
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

    public static double arrondie(double d) {
        return Double.parseDouble(String.format("%.2f", d));
    }
}

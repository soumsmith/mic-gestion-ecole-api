package com.vieecoles.processors.agboville;

import com.vieecoles.dto.RecapDesResultatsElevesAffecteDto;
import com.vieecoles.services.etats.appachePoi.resultatsPoiServices;
import com.vieecoles.services.etats.resultatsRecapServices;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

@ApplicationScoped
public class WordTempRecapResultatProcessor {
    @Inject
    resultatsPoiServices resultatsServices ;
    @Inject
    resultatsRecapServices resultatsRecapServices ;

    public   void getRecapResultatAffProcessor(XWPFDocument document ,
                                          Long idEcole ,String libelleAnnee , String libelleTrimetre) {
        XWPFTable table = document.getTableArray(20);
        List<RecapDesResultatsElevesAffecteDto> detailsBull= new ArrayList<>();
        detailsBull=   resultatsRecapServices.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre);
        long nombMoySup10Etabli =0l ; long nombMoyInf10Etabli =0l ;long nombMoyInf8_5Etabli =0l;
        long effectifClasseEtabli=0l ;  long effectifEtabli=0l; long effNonClassEtabli=0l; Double
            moyEtablissementEtabli=0d;
        long nombMoySup10EtabliG =0l ; long nombMoyInf10EtabliG =0l ;long nombMoyInf8_5EtabliG =0l;
        long effectifClasseEtabliG=0l ;  long effectifEtabliG=0l; long effNonClassEtabliG=0l; Double
            moyEtablissementEtabliG=0d;

       double pourSup10Etabli= 0d,pourInf10Etabli = 0d,pourInf8_5Etabli = 0d;

        for (RecapDesResultatsElevesAffecteDto classe : detailsBull) {
            long nombMoySup10 =
                (classe.getNbreMoySup10F() != null ? classe.getNbreMoySup10F() : 0L) +
                    (classe.getNbreMoySup10G() != null ? classe.getNbreMoySup10G() : 0L);
            nombMoySup10Etabli=nombMoySup10Etabli+nombMoySup10;


            long nombMoyInf10 =
                (classe.getNbreMoyInf999F() != null ? classe.getNbreMoyInf999F() : 0L) +
                    (classe.getNbreMoyInf999G() != null ? classe.getNbreMoyInf999G() : 0L);
            nombMoyInf10Etabli=nombMoyInf10Etabli+nombMoyInf10;

            long nombMoyInf8_5 =
                (classe.getNbreMoyInf85F() != null ? classe.getNbreMoyInf85F() : 0L) +
                    (classe.getNbreMoyInf85G() != null ? classe.getNbreMoyInf85G() : 0L);

            nombMoyInf8_5Etabli=nombMoyInf8_5Etabli+nombMoyInf8_5;

            long effectifClasse =
                (classe.getClassF() != null ? classe.getClassF() : 0L) +
                    (classe.getClassG() != null ? classe.getClassG() : 0L);
            effectifClasseEtabli=effectifClasseEtabli+effectifClasse;

// Éviter une division par zéro pour effectifClasse
            double pourSup10,pourInf10,pourInf8_5;
            if (effectifClasse > 0) {
                pourSup10 = nombMoySup10 / (Double.valueOf(effectifClasse)) * 100d;
                pourInf10 = nombMoyInf10 / (Double.valueOf(effectifClasse)) * 100d;
                pourInf8_5 = nombMoyInf8_5 / (Double.valueOf(effectifClasse)) * 100d;

                // Calculer d'autres statistiques ou utiliser ces valeurs
            } else {
                // Gérer le cas où effectifClasse est 0 pour éviter une division par zéro
                pourSup10 = 0d;
                pourInf10 = 0d;
                pourInf8_5 = 0d;
                // Logique alternative ou gestion d'erreur
            }

            //Calucul pourcentage Etablissement

            if (effectifClasseEtabli > 0) {
                pourSup10Etabli = nombMoySup10Etabli / (Double.valueOf(effectifClasseEtabli)) * 100d;
                pourInf10Etabli = nombMoyInf10Etabli / (Double.valueOf(effectifClasseEtabli)) * 100d;
                pourInf8_5Etabli = nombMoyInf8_5Etabli / (Double.valueOf(effectifClasseEtabli)) * 100d;

                // Calculer d'autres statistiques ou utiliser ces valeurs
            } else {
                // Gérer le cas où effectifClasse est 0 pour éviter une division par zéro
                pourSup10Etabli = 0d;
                pourInf10Etabli = 0d;
                pourInf8_5Etabli = 0d;
                // Logique alternative ou gestion d'erreur
            }



            long effectif =
                (classe.getEffeF() != null ? classe.getEffeF() : 0L) +
                    (classe.getEffeG() != null ? classe.getEffeG() : 0L);
            effectifEtabli=effectifEtabli+effectif;
            long effNonClass =
                (classe.getNonclassF() != null ? classe.getNonclassF() : 0L) +
                    (classe.getNonclassG() != null ? classe.getNonclassG() : 0L);
            effNonClassEtabli=effNonClassEtabli+effNonClass;

            // Créer une ligne principale pour la classe (6ème 1, 6ème 2, etc.)
            //XWPFTableRow classRow = table.createRow();
            // ensureCellCount(classRow, 14); // 14 colonnes comme dans le modèle

            // Remplir la ligne pour les filles (F)
            XWPFTableRow fillesRow = table.createRow();
            ensureCellCount(fillesRow, 12);
            fillesRow.getCell(0).setText(classe.getNiveau());
            fillesRow.getCell(1).setText(" F");
            fillesRow.getCell(2).setText(String.valueOf(classe.getEffeF()));
            fillesRow.getCell(3).setText(String.valueOf(classe.getClassF()));
            fillesRow.getCell(4).setText(String.valueOf(classe.getNonclassF()));
            fillesRow.getCell(5).setText(String.valueOf(classe.getNbreMoySup10F()));
            fillesRow.getCell(6).setText(String.valueOf(arrondie(classe.getPourMoySup10F())));
            fillesRow.getCell(7).setText(String.valueOf(classe.getNbreMoyInf999F()));
            fillesRow.getCell(8).setText(String.valueOf(arrondie(classe.getPourMoyInf999F())));
            fillesRow.getCell(9).setText(String.valueOf(classe.getNbreMoyInf85F()));
            fillesRow.getCell(10).setText(String.valueOf(arrondie(classe.getPourMoyInf85F())));
            fillesRow.getCell(11).setText(String.valueOf(arrondie(classe.getMoyClasseF())));

// Remplir la ligne pour les garçons (G)
            XWPFTableRow garconsRow = table.createRow();
            ensureCellCount(garconsRow, 12);
            garconsRow.getCell(1).setText(" G");
            garconsRow.getCell(2).setText(String.valueOf(classe.getEffeG()));
            garconsRow.getCell(3).setText(String.valueOf(classe.getClassG()));
            garconsRow.getCell(4).setText(String.valueOf(classe.getNonclassG()));
            garconsRow.getCell(5).setText(String.valueOf(classe.getNbreMoySup10G()));
            garconsRow.getCell(6).setText(String.valueOf(arrondie(classe.getPourMoySup10G())));
            garconsRow.getCell(7).setText(String.valueOf(classe.getNbreMoyInf999G()));
            garconsRow.getCell(8).setText(String.valueOf(arrondie(classe.getPourMoyInf999G())));
            garconsRow.getCell(9).setText(String.valueOf(classe.getNbreMoyInf85G()));
            garconsRow.getCell(10).setText(String.valueOf(arrondie(classe.getPourMoyInf85G())));
            garconsRow.getCell(11).setText(String.valueOf(arrondie(classe.getMoyClasseG())));

// Remplir la ligne pour le total (T)
            XWPFTableRow totalRow = table.createRow();
            ensureCellCount(totalRow, 12);
            totalRow.getCell(1).setText(" T");
            totalRow.getCell(2).setText(String.valueOf(effectif));
            totalRow.getCell(3).setText(String.valueOf(effectifClasse));
            totalRow.getCell(4).setText(String.valueOf(effNonClass));
            totalRow.getCell(5).setText(String.valueOf(nombMoySup10));
            totalRow.getCell(6).setText(String.valueOf(arrondie(pourSup10)));
            totalRow.getCell(7).setText(String.valueOf(nombMoyInf10));
            totalRow.getCell(8).setText(String.valueOf(arrondie(pourInf10)));
            totalRow.getCell(9).setText(String.valueOf(nombMoyInf8_5));
            totalRow.getCell(10).setText(String.valueOf(arrondie(pourInf8_5)));
            totalRow.getCell(11).setText(String.valueOf(arrondie(classe.getMoyClasse())));
            mergeCellsVertically(table, 0, table.getNumberOfRows() - 3, table.getNumberOfRows() -1);
            moyEtablissementEtabli=classe.getMoyClasse_ET() ;
        }
        /*// Remplir la ligne pour les Total Etablissement
        XWPFTableRow etabliRowF = table.createRow();
        ensureCellCount(etabliRowF, 12);
        etabliRowF.getCell(0).setText("Total Etabli");
        etabliRowF.getCell(1).setText(" F");
        etabliRowF.getCell(2).setText(String.valueOf(""));
        etabliRowF.getCell(3).setText(String.valueOf(""));
        etabliRowF.getCell(4).setText(String.valueOf(""));
        etabliRowF.getCell(5).setText(String.valueOf(""));
        etabliRowF.getCell(6).setText(String.valueOf(arrondie(0d)));
        etabliRowF.getCell(7).setText(String.valueOf(""));
        etabliRowF.getCell(8).setText(String.valueOf(arrondie(0d)));
        etabliRowF.getCell(9).setText(String.valueOf(""));
        etabliRowF.getCell(10).setText(String.valueOf(arrondie(0d)));
        etabliRowF.getCell(11).setText(String.valueOf(arrondie(0d)));
        //Total Etablissement Garçon
        XWPFTableRow etabliRowG = table.createRow();
        ensureCellCount(etabliRowG, 12);
        etabliRowG.getCell(0).setText("Total Etabli");
        etabliRowG.getCell(1).setText("G");
        etabliRowG.getCell(2).setText(String.valueOf(""));
        etabliRowG.getCell(3).setText(String.valueOf(""));
        etabliRowG.getCell(4).setText(String.valueOf(""));
        etabliRowG.getCell(5).setText(String.valueOf(""));
        etabliRowG.getCell(6).setText(String.valueOf(arrondie(0d)));
        etabliRowG.getCell(7).setText(String.valueOf(""));
        etabliRowG.getCell(8).setText(String.valueOf(arrondie(0d)));
        etabliRowG.getCell(9).setText(String.valueOf(""));
        etabliRowG.getCell(10).setText(String.valueOf(arrondie(0d)));
        etabliRowG.getCell(11).setText(String.valueOf(arrondie(0d)));
*/
        //Total Etablissement
        XWPFTableRow etabliRow = table.createRow();
        ensureCellCount(etabliRow, 12);
        etabliRow.getCell(0).setText("Total Etabli");
        etabliRow.getCell(2).setText(String.valueOf(effectifEtabli));
        etabliRow.getCell(3).setText(String.valueOf(effectifClasseEtabli));
        etabliRow.getCell(4).setText(String.valueOf(effNonClassEtabli));
        etabliRow.getCell(5).setText(String.valueOf(nombMoySup10Etabli));
        etabliRow.getCell(6).setText(String.valueOf(arrondie(pourSup10Etabli)));
        etabliRow.getCell(7).setText(String.valueOf(nombMoyInf10Etabli));
        etabliRow.getCell(8).setText(String.valueOf(arrondie(pourInf10Etabli)));
        etabliRow.getCell(9).setText(String.valueOf(nombMoyInf8_5Etabli));
        etabliRow.getCell(10).setText(String.valueOf(arrondie(pourInf8_5Etabli)));
        etabliRow.getCell(11).setText(String.valueOf(arrondie(moyEtablissementEtabli)));


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

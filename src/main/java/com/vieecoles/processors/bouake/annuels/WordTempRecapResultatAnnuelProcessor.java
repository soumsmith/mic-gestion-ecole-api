package com.vieecoles.processors.bouake.annuels;

import static com.vieecoles.processors.yamoussoukro.WordTempResultaAffProcessor.afficherValeurParNiveau;

import com.vieecoles.dto.RecapDesResultatsElevesAffecteDto;
import com.vieecoles.services.etats.appachePoi.resultatsPoiServices;
import com.vieecoles.services.etats.resultatsRecapServices;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

@ApplicationScoped
public class WordTempRecapResultatAnnuelProcessor {
    @Inject
    resultatsPoiServices resultatsServices ;
    @Inject
    resultatsRecapServices resultatsRecapServices ;

    public   void getRecapResultatAffProcessor(XWPFDocument document ,
                                          Long idEcole ,String libelleAnnee , String libelleTrimetre) {


        String tableTitle = "CODE_RECAP_NON_AFF";
        XWPFTable targetTable = null;
        for (int i = 0; i < document.getBodyElements().size(); i++) {
            IBodyElement element = document.getBodyElements().get(i);
            if (element.getElementType() == BodyElementType.PARAGRAPH) {
                XWPFParagraph paragraph = (XWPFParagraph) element;
                if (paragraph.getText().trim().equalsIgnoreCase(tableTitle)) {
                    paragraph.removeRun(0);
                    // Le tableau devrait suivre immédiatement le titre
                    if (i + 1 < document.getBodyElements().size() &&
                        document.getBodyElements().get(i + 1).getElementType() == BodyElementType.TABLE) {
                        targetTable = (XWPFTable) document.getBodyElements().get(i + 1);
                    }
                    break;
                }
            }
        }




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



// Remplir la ligne pour le total (T)
            XWPFTableRow totalRow = targetTable.createRow();
            ensureCellCount(totalRow, 9);
            totalRow.getCell(0).setText(afficherValeurParNiveau(classe.getNiveau()));
            totalRow.getCell(1).setText(String.valueOf(effectif));
            totalRow.getCell(2).setText(String.valueOf(effectifClasse));
            totalRow.getCell(3).setText(String.valueOf(nombMoySup10));
            totalRow.getCell(4).setText(String.valueOf(arrondie(pourSup10)));
            totalRow.getCell(5).setText(String.valueOf(nombMoyInf10));
            totalRow.getCell(6).setText(String.valueOf(arrondie(pourInf10)));
            totalRow.getCell(7).setText(String.valueOf(nombMoyInf8_5));
            totalRow.getCell(8).setText(String.valueOf(arrondie(pourInf8_5)));
            moyEtablissementEtabli=classe.getMoyClasse_ET() ;
        }

        //Total Etablissement
        XWPFTableRow etabliRow = targetTable.createRow();
        ensureCellCount(etabliRow, 9);
        etabliRow.getCell(0).setText("Total");
        etabliRow.getCell(1).setText(String.valueOf(effectifEtabli));
        etabliRow.getCell(2).setText(String.valueOf(effectifClasseEtabli));
        etabliRow.getCell(3).setText(String.valueOf(nombMoySup10Etabli));
        etabliRow.getCell(4).setText(String.valueOf(arrondie(pourSup10Etabli)));
        etabliRow.getCell(5).setText(String.valueOf(nombMoyInf10Etabli));
        etabliRow.getCell(6).setText(String.valueOf(arrondie(pourInf10Etabli)));
        etabliRow.getCell(7).setText(String.valueOf(nombMoyInf8_5Etabli));
        etabliRow.getCell(8).setText(String.valueOf(arrondie(pourInf8_5Etabli)));


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

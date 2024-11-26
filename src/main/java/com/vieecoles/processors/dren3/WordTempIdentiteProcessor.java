package com.vieecoles.processors.dren3;

import com.vieecoles.dto.IdentiteEtatDto;
import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;
import com.vieecoles.services.etats.IdentiteEtatService;
import com.vieecoles.services.etats.appachePoi.resultatsPoiServices;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

@ApplicationScoped
public class WordTempIdentiteProcessor {
    @Inject
    resultatsPoiServices resultatsServices ;
    @Inject
    IdentiteEtatService identiteEtatService ;
    public   void getIdentiteProcessor(XWPFDocument document ,
                                           Long idEcole ,String libelleAnnee , String libelleTrimestre) {
        List<IdentiteEtatDto>  identiteEtatDto = new ArrayList<>() ;
        identiteEtatDto= identiteEtatService.getIdentiteDto(idEcole) ;
        // Map pour les paragraphes
        Map<String, String> paragraphDataMap = new HashMap<>();
        paragraphDataMap.put("{{DENOMINATION}}", identiteEtatDto.get(0).getDenominEtabli());
        paragraphDataMap.put("{{DECI_OUVERTURE}}", identiteEtatDto.get(0).getNumDecisionOuver());
        paragraphDataMap.put("{{DECI_RENAI}}","");
        paragraphDataMap.put("{{CODE_ECOLE}}", identiteEtatDto.get(0).getCodeEtabli());
        paragraphDataMap.put("{{SITUATION_GEOGRA}}", identiteEtatDto.get(0).getSituationGeogra());
        paragraphDataMap.put("{{ADRESSE}}", identiteEtatDto.get(0).getAdressieEtablis());
        paragraphDataMap.put("{{TELEPHONE}}", identiteEtatDto.get(0).getTelephonEtablisse());
        paragraphDataMap.put("{{FAX}}", identiteEtatDto.get(0).getFaxEtabliss());
        paragraphDataMap.put("{{EMAIL}}", identiteEtatDto.get(0).getEmailEtablissem());

        // 1. Remplacer les placeholders dans les paragraphes
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            replacePlaceholdersInParagraph(paragraph, paragraphDataMap);
        }

       // replacePlaceholdersInStaticTables(document);

    }


    // Méthode pour remplacer les placeholders dans une ligne de tableau
    private static void replacePlaceholdersInTableRow(XWPFTableRow row, Map<String, String> dataMap) {
        for (XWPFTableCell cell : row.getTableCells()) {
            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                replacePlaceholdersInParagraph(paragraph, dataMap);
            }
        }
    }
    // Méthode pour remplacer les placeholders dans un paragraphe
    private static void replacePlaceholdersInParagraph(XWPFParagraph paragraph, Map<String, String> dataMap) {
        StringBuilder fullText = new StringBuilder();
        List<XWPFRun> runs = paragraph.getRuns();

        // Reconstituer le texte complet du paragraphe à partir des runs
        for (XWPFRun run : runs) {
            String text = run.getText(0);
            if (text != null) {
                fullText.append(text);
            }
        }

        String paragraphText = fullText.toString();

        // Remplacer les placeholders dans le texte complet du paragraphe
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            paragraphText = paragraphText.replace(entry.getKey(), entry.getValue());
        }

        // Si le texte du paragraphe a été modifié, mettre à jour les runs existants
        if (!fullText.toString().equals(paragraphText)) {
            int runIndex = 0;
            for (XWPFRun run : runs) {
                String runText = run.getText(0);
                if (runText != null) {
                    // Découper le texte modifié en morceaux correspondant à la taille des runs originaux
                    String newText = paragraphText.substring(0, Math.min(runText.length(), paragraphText.length()));
                    run.setText(newText, 0);
                    paragraphText = paragraphText.substring(newText.length());
                }
                runIndex++;
            }

            // Si des caractères restent après avoir modifié tous les runs existants, les ajouter à un nouveau run
            if (!paragraphText.isEmpty()) {
                XWPFRun newRun = paragraph.createRun();
                newRun.setText(paragraphText);
            }
        }
    }


    private static void replacePlaceholdersInStaticTables(XWPFDocument document) {
        // Table statique 1
        XWPFTable staticTable = document.getTables().get(0); // Premier tableau
        for (int i = 1; i < staticTable.getRows().size(); i++) { // On saute la première ligne (en-tête)
            XWPFTableRow row = staticTable.getRow(i);
            Map<String, String> rowData = new HashMap<>();
            rowData.put("{{NOM_ECOLE}}", "IRIS 3 YOPOUGON");
            rowData.put("{{DECISION_OUVERTURE}}", "MM458PPPP");
            rowData.put("{{DECISION_RECONNAISANCE}}", "487558KLO866");
            rowData.put("{{SITUATION_ETABLI}}", "Yopougon Agbayaté");
            rowData.put("{{ADRESSE_ECOLE}}", "03 BP 988 Abidjan 03");
            rowData.put("{{CONTACT_ECOLE}}", "054896985");
            replacePlaceholdersInTableRow(row, rowData);
        }

        // Table statique 2
        XWPFTable staticTable2 = document.getTables().get(1); // Deuxième tableau
        for (int i = 1; i < staticTable2.getRows().size(); i++) {
            XWPFTableRow row = staticTable2.getRow(i);
            Map<String, String> rowData = new HashMap<>();
            rowData.put("{{NOM_FONDATEUR}}", "SOUMAHORO SEBASTIEN");
            rowData.put("{{FONCTION_FONDATEUR}}", "FONDATEUR");
            rowData.put("{{ADRESSE_FONDATEUR}}", "03 KK ABIDJAN 012");
            replacePlaceholdersInTableRow(row, rowData);
        }

        // Table statique 3
        XWPFTable staticTable3 = document.getTables().get(2); // Troisième tableau
        for (int i = 1; i < staticTable3.getRows().size(); i++) {
            XWPFTableRow row = staticTable3.getRow(i);
            Map<String, String> rowData = new HashMap<>();
            rowData.put("{{NOM_PRENOM_DIRECT}}", "SOUMAHORO Moustapha");
            rowData.put("{{ADRESS_DIRECT}}", "0358 pm 455");
            rowData.put("{{CONTACT_DIRECTEUR}}", "0345897564122");
            replacePlaceholdersInTableRow(row, rowData);
        }
    }
    private static void ensureCellCount(XWPFTableRow row, int cellCount) {
        int currentCellCount = row.getTableCells().size();
        for (int i = currentCellCount; i < cellCount; i++) {
            row.addNewTableCell(); // Ajouter une nouvelle cellule si nécessaire
        }
    }
    private static void ajoutInform(List<eleveAffecteParClasseDto> detailsBull,XWPFTable table) {
        for (eleveAffecteParClasseDto eleve : detailsBull) {
            // Créer une nouvelle ligne dans le tableau
            XWPFTableRow row = table.createRow();
            // Assurez-vous que la ligne a suffisamment de cellules
            ensureCellCount(row, 5); // Par exemple, 5 cellules pour Matricule, Nom, Prénom, Sexe, Classe

            // Remplir les cellules de la nouvelle ligne
            row.getCell(0).setText(eleve.getMatricule());
            row.getCell(1).setText(eleve.getNomEleve());
            row.getCell(2).setText(eleve.getPrenomEleve());
            row.getCell(3).setText(eleve.getSexe());
            row.getCell(4).setText(eleve.getClasseLibelle());
        }
    }

    private static void ajoutTableauDynamique(List<ResultatsElevesAffecteDto> detailsBull, XWPFTable table) {
        for (ResultatsElevesAffecteDto classe : detailsBull) {
            long nombMoySup10 =
                    (classe.getNbreMoySup10F() != null ? classe.getNbreMoySup10F() : 0L) +
                            (classe.getNbreMoySup10G() != null ? classe.getNbreMoySup10G() : 0L);

            long nombMoyInf10 =
                    (classe.getNbreMoyInf999F() != null ? classe.getNbreMoyInf999F() : 0L) +
                            (classe.getNbreMoyInf999G() != null ? classe.getNbreMoyInf999G() : 0L);

            long nombMoyInf8_5 =
                    (classe.getNbreMoyInf85F() != null ? classe.getNbreMoyInf85F() : 0L) +
                            (classe.getNbreMoyInf85G() != null ? classe.getNbreMoyInf85G() : 0L);

            long effectifClasse =
                    (classe.getClassF() != null ? classe.getClassF() : 0L) +
                            (classe.getClassG() != null ? classe.getClassG() : 0L);

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

            long effectif =
                    (classe.getEffeF() != null ? classe.getEffeF() : 0L) +
                            (classe.getEffeG() != null ? classe.getEffeG() : 0L);

            long effNonClass =
                    (classe.getNonclassF() != null ? classe.getNonclassF() : 0L) +
                            (classe.getNonclassG() != null ? classe.getNonclassG() : 0L);



            // Créer une ligne principale pour la classe (6ème 1, 6ème 2, etc.)
            //XWPFTableRow classRow = table.createRow();
           // ensureCellCount(classRow, 14); // 14 colonnes comme dans le modèle

// Remplir la ligne pour les filles (F)
            XWPFTableRow fillesRow = table.createRow();
            ensureCellCount(fillesRow, 12);
            fillesRow.getCell(0).setText(classe.getClasse());
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

        }
    }


    private static void resultatsElevesAffectes6E(ResultatsElevesAffecteDto resultatsBull,XWPFTable table){
        if(resultatsBull!=null){
            System.out.println("Objet Trouvé "+resultatsBull.toString());

        for (int i = 1; i < table.getRows().size(); i++) {
            XWPFTableRow row = table.getRow(i);
            Map<String, String> rowData = new HashMap<>();

            long nombMoySup10 =
                    (resultatsBull.getNbreMoySup10F() != null ? resultatsBull.getNbreMoySup10F() : 0L) +
                            (resultatsBull.getNbreMoySup10G() != null ? resultatsBull.getNbreMoySup10G() : 0L);

            long nombMoyInf10 =
                    (resultatsBull.getNbreMoyInf999F() != null ? resultatsBull.getNbreMoyInf999F() : 0L) +
                            (resultatsBull.getNbreMoyInf999G() != null ? resultatsBull.getNbreMoyInf999G() : 0L);

            long nombMoyInf8_5 =
                    (resultatsBull.getNbreMoyInf85F() != null ? resultatsBull.getNbreMoyInf85F() : 0L) +
                            (resultatsBull.getNbreMoyInf85G() != null ? resultatsBull.getNbreMoyInf85G() : 0L);

            long effectifClasse =
                    (resultatsBull.getClassF() != null ? resultatsBull.getClassF() : 0L) +
                            (resultatsBull.getClassG() != null ? resultatsBull.getClassG() : 0L);

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

            long effectif =
                    (resultatsBull.getEffeF() != null ? resultatsBull.getEffeF() : 0L) +
                            (resultatsBull.getEffeG() != null ? resultatsBull.getEffeG() : 0L);

            long effNonClass =
                    (resultatsBull.getNonclassF() != null ? resultatsBull.getNonclassF() : 0L) +
                            (resultatsBull.getNonclassG() != null ? resultatsBull.getNonclassG() : 0L);


            rowData.put("{{NOMBRE_MOY_SUP_10_6E}}", String.valueOf(nombMoySup10));
            rowData.put("{{NOMBRE_MOY_INF_10_6E}}", String.valueOf(nombMoyInf10));
            rowData.put("{{NOMBRE_MOY_INF_08_5_6E}}", String.valueOf(nombMoyInf8_5));
            rowData.put("{{POURC_MOY_SUP_10_6E}}", String.valueOf(pourSup10));
            rowData.put("{{POURC_MOY_INF_10_6E}}", String.valueOf(pourInf10));
            rowData.put("{{POURC_MOY_INF_08_5_6E}}", String.valueOf(pourInf8_5));
            rowData.put("{{NOMBRE_ELEV_6E}}", String.valueOf(effectif));
            rowData.put("{{NOMBRE_ELEV_CLASSES_6E}}", String.valueOf(effectifClasse));
            rowData.put("{{NOMBRE_ELEV_NON_CLASSES_6E}}", String.valueOf(effNonClass));
            replacePlaceholdersInTableRow(row, rowData);

    }
        }

    }

    private static void resultatsElevesAffectes(List<ResultatsElevesAffecteDto> resultatsBull,XWPFTable table){
        for (int i = 1; i < table.getRows().size(); i++) {
            XWPFTableRow row = table.getRow(i);
            Map<String, String> rowData = new HashMap<>();

            rowData.put("{{NOMBRE_MOY_SUP_10_6E}}", "SOUMAHORO Moustapha");
            rowData.put("{{NOMBRE_MOY_INF_10_6E}}", "0358 pm 455");
            rowData.put("{{NOMBRE_MOY_INF_08_5_6E}}", "0345897564122");
            rowData.put("{{POURC_MOY_SUP_10_6E}}", "0345897564122");
            rowData.put("{{POURC_MOY_INF_10_6E}}", "0345897564122");
            rowData.put("{{POURC_MOY_INF_08_5_6E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_6E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_CLASSES_6E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_NON_CLASSES_6E}}", "0345897564122");
            //
            rowData.put("{{NOMBRE_MOY_SUP_10_5E}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_INF_10_5E}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_INF_08_5_5E}}", "0345897564122");
            rowData.put("{{POURC_MOY_SUP_10_5E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_5E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_CLASSES_5E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_NON_CLASSES_5E}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_SUP_10_4E}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_INF_10_4E}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_INF_08_5_4E}}", "0345897564122");
            rowData.put("{{POURC_MOY_SUP_10_4E}}", "0345897564122");
            rowData.put("{{POURC_MOY_INF_10_4E}}", "0345897564122");
            rowData.put("{{POURC_MOY_INF_08_5_4E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_4E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_CLASSES_4E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_NON_CLASSES_4E}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_SUP_10_3E}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_INF_10_3E}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_INF_08_5_3E}}", "0345897564122");
            rowData.put("{{POURC_MOY_SUP_10_3E}}", "0345897564122");
            rowData.put("{{POURC_MOY_INF_10_3E}}", "0345897564122");
            rowData.put("{{POURC_MOY_INF_08_5_3E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_3E}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_CLASSES_3E}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_SUP_10_CLASSE}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_INF_10_CLASSE}}", "0345897564122");
            rowData.put("{{NOMBRE_MOY_INF_08_5_CLASSE}}", "0345897564122");
            rowData.put("{{POURC_MOY_SUP_10_CLASSE}}", "0345897564122");
            rowData.put("{{POURC_MOY_INF_10_CLASSE}}", "0345897564122");
            rowData.put("{{POURC_MOY_INF_08_5_CLASSE}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_CLASSE}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_CLASSES_CLASSE}}", "0345897564122");
            rowData.put("{{NOMBRE_ELEV_NON_CLASSES_CLASSE}}", "0345897564122");
            replacePlaceholdersInTableRow(row, rowData);
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

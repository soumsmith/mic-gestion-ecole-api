package com.vieecoles.processors.agboville;

import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.services.etats.appachePoi.resultatsPoiServices;
import com.vieecoles.services.etats.resultatsRecapServices;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
@ApplicationScoped
public class WordTempResultaAffProcessor {
    @Inject
    resultatsPoiServices resultatsServices ;
    @Inject
    resultatsRecapServices resultatsRecapServices ;

      public   void getResultatAffProcessor(XWPFDocument document ,
          Long idEcole ,String libelleAnnee , String libelleTrimetre) {
        List<ResultatsElevesAffecteDto> detailsBull6 = new ArrayList<>();
        System.out.println("classeNiveauDtoList entree");
        try {
          detailsBull6= resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,1)  ;
          System.out.println("classeNiveauDtoList Sortie");
        } catch (Exception e) {
          e.printStackTrace();
        }
        // Cinquième

        List<ResultatsElevesAffecteDto> detailsBull5 = new ArrayList<>();
        try {
          detailsBull5= resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,2)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Quatrieme

        List<ResultatsElevesAffecteDto> detailsBull4 = new ArrayList<>();
        try {
          detailsBull4= resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,3)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }


        // Troixième

        List<ResultatsElevesAffecteDto> detailsBull3 = new ArrayList<>();
        try {
          detailsBull3= resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,4)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }
        // Seconde A
        List<ResultatsElevesAffecteDto> detailsBull2NDA = new ArrayList<>();
        try {
          detailsBull2NDA= resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,5)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Seconde C
        List<ResultatsElevesAffecteDto> detailsBull2NDC = new ArrayList<>();
        try {
          detailsBull2NDC= resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,6)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Premiere A
        List<ResultatsElevesAffecteDto> detailsBull1EREA = new ArrayList<>();
        try {
          detailsBull1EREA= resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,7)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Premiere C
        List<ResultatsElevesAffecteDto> detailsBull1EREC = new ArrayList<>();
        try {
          detailsBull1EREC = resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,8)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Premiere D
        List<ResultatsElevesAffecteDto> detailsBull1ERED = new ArrayList<>();
        try {
          detailsBull1ERED = resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,9)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Terminale A
        List<ResultatsElevesAffecteDto> detailsBullTLEA = new ArrayList<>();
        try {
          detailsBullTLEA = resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,10)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Terminale A1
        List<ResultatsElevesAffecteDto> detailsBullTLEA1 = new ArrayList<>();
        try {
          detailsBullTLEA1 = resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,11)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Terminale A2
        List<ResultatsElevesAffecteDto> detailsBullTLEA2 = new ArrayList<>();
        try {
          detailsBullTLEA2 = resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,12)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Terminale C
        List<ResultatsElevesAffecteDto> detailsBullTLEC = new ArrayList<>();
        try {
          detailsBullTLEC = resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,13)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Terminale D
        List<ResultatsElevesAffecteDto> detailsBullTLED = new ArrayList<>();
        try {
          detailsBullTLED = resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,14)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // 3. Gestion des tableaux dynamiques
        // Sixième
        XWPFTable table = document.getTableArray(6);
        try {
          ajoutTableauDynamique(detailsBull6,table);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }
        // Cinquième
        XWPFTable tableCinquieme = document.getTableArray(7);
        try {
          ajoutTableauDynamique(detailsBull5,tableCinquieme);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Quatrieme
        XWPFTable tableQuatrieme = document.getTableArray(8);
        try {
          ajoutTableauDynamique(detailsBull4,tableQuatrieme);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Troixième
        XWPFTable tableTroixieme = document.getTableArray(9);
        try {
          ajoutTableauDynamique(detailsBull3,tableTroixieme);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // SecondeA
        XWPFTable table2NDA = document.getTableArray(10);
        try {
          ajoutTableauDynamique(detailsBull2NDA,table2NDA);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // SecondeC
        XWPFTable table2NDC = document.getTableArray(11);
        try {
          ajoutTableauDynamique(detailsBull2NDC,table2NDC);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // PremiereA
        XWPFTable table1EREA = document.getTableArray(12);
        try {
          ajoutTableauDynamique(detailsBull1EREA,table1EREA);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // PremiereC
        XWPFTable table1EREC = document.getTableArray(13);
        try {
          ajoutTableauDynamique(detailsBull1EREC,table1EREC);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // PremiereD
        XWPFTable table1ERED = document.getTableArray(14);
        try {
          ajoutTableauDynamique(detailsBull1ERED,table1ERED);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }


        // Terminale A
        XWPFTable tableTLEA = document.getTableArray(15);
        try {
          ajoutTableauDynamique(detailsBullTLEA,tableTLEA);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Terminale A1
        XWPFTable tableTLEA1 = document.getTableArray(16);
        try {
          ajoutTableauDynamique(detailsBullTLEA1,tableTLEA1);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Terminale A2
        XWPFTable tableTLEA2 = document.getTableArray(17);
        try {
          ajoutTableauDynamique(detailsBullTLEA2,tableTLEA2);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Terminale C
        XWPFTable tableTLEC = document.getTableArray(18);
        try {
          ajoutTableauDynamique(detailsBullTLEC,tableTLEC);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Terminale D
        XWPFTable tableTLED = document.getTableArray(19);
        try {
          ajoutTableauDynamique(detailsBullTLED,tableTLED);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

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


    private static void ensureCellCount(XWPFTableRow row, int cellCount) {
        int currentCellCount = row.getTableCells().size();
        for (int i = currentCellCount; i < cellCount; i++) {
            row.addNewTableCell(); // Ajouter une nouvelle cellule si nécessaire
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

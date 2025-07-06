package com.vieecoles.processors.dren4.Annuels;

import static com.vieecoles.processors.yamoussoukro.WordTempResultaAffProcessor.afficherValeurParNiveau;

import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.dto.ResultatsElevesNonAffecteDto;
import com.vieecoles.services.etats.appachePoi.Annuels.resultatsAnnuelsNonAffectePoiServices;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

@ApplicationScoped
public class WordTempResultaNonAffAnnuelsProcessor {
  @Inject
  com.vieecoles.services.etats.resultatsNonAffecteServices resultatsNonAffecteServices ;
    @Inject
    resultatsAnnuelsNonAffectePoiServices resultatsServices ;


      public   void getResultatNonAffProcessor(XWPFDocument document ,
          Long idEcole ,String libelleAnnee , String libelleTrimetre) {
        List<ResultatsElevesNonAffecteDto> detailsBull6 = new ArrayList<>();

        try {
          detailsBull6= resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,1)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }
        // Cinquième

        List<ResultatsElevesNonAffecteDto> detailsBull5 = new ArrayList<>();
        try {
          detailsBull5= resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,2)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Quatrieme

        List<ResultatsElevesNonAffecteDto> detailsBull4 = new ArrayList<>();
        try {
          detailsBull4= resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,3)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }


        // Troixième

        List<ResultatsElevesNonAffecteDto> detailsBull3 = new ArrayList<>();
        try {
          detailsBull3= resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,4)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }
        // Seconde A
        List<ResultatsElevesNonAffecteDto> detailsBull2NDA = new ArrayList<>();
        try {
          detailsBull2NDA= resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,5)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Seconde C
        List<ResultatsElevesNonAffecteDto> detailsBull2NDC = new ArrayList<>();
        try {
          detailsBull2NDC= resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,6)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Premiere A
        List<ResultatsElevesNonAffecteDto> detailsBull1EREA = new ArrayList<>();
        try {
          detailsBull1EREA= resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,7)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Premiere C
        List<ResultatsElevesNonAffecteDto> detailsBull1EREC = new ArrayList<>();
        try {
          detailsBull1EREC = resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,8)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Premiere D
        List<ResultatsElevesNonAffecteDto> detailsBull1ERED = new ArrayList<>();
        try {
          detailsBull1ERED = resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,9)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Terminale A
        List<ResultatsElevesNonAffecteDto> detailsBullTLEA = new ArrayList<>();
        try {
          detailsBullTLEA = resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,10)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Terminale A1
        List<ResultatsElevesNonAffecteDto> detailsBullTLEA1 = new ArrayList<>();
        try {
          detailsBullTLEA1 = resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,11)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Terminale A2
        List<ResultatsElevesNonAffecteDto> detailsBullTLEA2 = new ArrayList<>();
        try {
          detailsBullTLEA2 = resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,12)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Terminale C
        List<ResultatsElevesNonAffecteDto> detailsBullTLEC = new ArrayList<>();
        try {
          detailsBullTLEC = resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,13)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Terminale D
        List<ResultatsElevesNonAffecteDto> detailsBullTLED = new ArrayList<>();
        try {
          detailsBullTLED = resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,14)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // 3. Gestion des tableaux dynamiques
        // Sixième

        XWPFTable table = obtenirUnTableau(document,"CODE_NAFF_6");
        try {
          ajoutTableauDynamique(detailsBull6,table);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }
        // Cinquième
        XWPFTable tableCinquieme = obtenirUnTableau(document,"CODE_NAFF_7");
        try {
          ajoutTableauDynamique(detailsBull5,tableCinquieme);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Quatrieme
        XWPFTable tableQuatrieme = obtenirUnTableau(document,"CODE_NAFF_8");
        try {
          ajoutTableauDynamique(detailsBull4,tableQuatrieme);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Troixième
        XWPFTable tableTroixieme = obtenirUnTableau(document,"CODE_NAFF_9");
        try {
          ajoutTableauDynamique(detailsBull3,tableTroixieme);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // SecondeA
        XWPFTable table2NDA = obtenirUnTableau(document,"CODE_NAFF_10");
        try {
          ajoutTableauDynamique(detailsBull2NDA,table2NDA);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // SecondeC
        XWPFTable table2NDC = obtenirUnTableau(document,"CODE_NAFF_11");
        try {
          ajoutTableauDynamique(detailsBull2NDC,table2NDC);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // PremiereA
        XWPFTable table1EREA = obtenirUnTableau(document,"CODE_NAFF_12");
        try {
          ajoutTableauDynamique(detailsBull1EREA,table1EREA);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // PremiereC
        XWPFTable table1EREC = obtenirUnTableau(document,"CODE_NAFF_13");
        try {
          ajoutTableauDynamique(detailsBull1EREC,table1EREC);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // PremiereD
        XWPFTable table1ERED = obtenirUnTableau(document,"CODE_NAFF_14");
        try {
          ajoutTableauDynamique(detailsBull1ERED,table1ERED);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }


        // Terminale A
        XWPFTable tableTLEA = obtenirUnTableau(document,"CODE_NAFF_15");
        try {
          ajoutTableauDynamique(detailsBullTLEA,tableTLEA);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Terminale A1
        XWPFTable tableTLEA1 = obtenirUnTableau(document,"CODE_NAFF_16");
        try {
          ajoutTableauDynamique(detailsBullTLEA1,tableTLEA1);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Terminale A2
        XWPFTable tableTLEA2 = obtenirUnTableau(document,"CODE_NAFF_17");
        try {
          ajoutTableauDynamique(detailsBullTLEA2,tableTLEA2);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Terminale C
        XWPFTable tableTLEC = obtenirUnTableau(document,"CODE_NAFF_18");
        try {
          ajoutTableauDynamique(detailsBullTLEC,tableTLEC);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Terminale D
        XWPFTable tableTLED = obtenirUnTableau(document,"CODE_NAFF_19");
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


    private static void ajoutTableauDynamique(List<ResultatsElevesNonAffecteDto> detailsBull, XWPFTable table) {

      double pourSup10,pourInf10,pourInf8_5;
      long nombMoySup10Cycle1 =0l ; long nombMoyInf10Cycle1 =0l ;long nombMoyInf8_5Cycle1 =0l;
      long effectifClasseCycle1=0l ;  long effNonClassCycle1=0l;
      long nombMoySup10Cycle1G =0l ; long nombMoyInf10Cycle1G =0l ;long nombMoyInf8_5Cycle1G =0l;
      long effectifClasseCycle1G=0l ;  long effNonClassCycle1G=0l;

      long nombMoySup10Cycle1F =0l ; long nombMoyInf10Cycle1F =0l ;long nombMoyInf8_5Cycle1F =0l;
      long effectifClasseCycle1F=0l ;  long effNonClassCycle1F=0l;

      double pourSup10Cycle1= 0d,pourInf10Cycle1 = 0d,pourInf8_5Cycle1 = 0d;
      double pourSup10Cycle1G= 0d,pourInf10Cycle1G = 0d,pourInf8_5Cycle1G = 0d;
      double pourSup10Cycle1F= 0d,pourInf10Cycle1F = 0d,pourInf8_5Cycle1F = 0d;
      double moyenCycle1F=0d;
      double moyenCycle1G=0d;
      Double moyenCycle1=0d;
      Long effectifCycle1 = 0L; Long effectifCycle1G = 0L; Long effectifCycle1F = 0L;
      Long effectifNonClasseCycle1 = 0L; Long effectifNonClasseCycle1G = 0L; Long effectifNonClasseCycle1F = 0L;

      String libelleNiveau="";
      for (ResultatsElevesNonAffecteDto classe : detailsBull) {
          /*  long nombMoySup10 =
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
                            (classe.getNonclassG() != null ? classe.getNonclassG() : 0L);*/
          Long effectifNiveau1=classe.getEffeG()+classe.getEffeF();
          Long effectifNiveau1G=classe.getEffeG();
          Long effectifNiveau1F=classe.getEffeF();

          Long effectifClasse1 =classe.getClassF()+classe.getClassG();
          Long effectifClasse1G=classe.getClassG();
          Long effectifClasse1F=classe.getClassF();

          Long effectifNonClasse1 =classe.getNonclassF()+classe.getNonclassG();
          Long effectifNonClasse1G=classe.getNonclassG();
          Long effectifNonClasse1F=classe.getNonclassF();

          moyenCycle1F=classe.getMoyClasseF() ;
          moyenCycle1G=classe.getMoyClasseG() ;
          moyenCycle1=classe.getMoyClasse() ;

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
              (classe.getNbreMoySup10F() != null ? classe.getNbreMoySup10F() : 0L) +
                  (classe.getNbreMoySup10G() != null ? classe.getNbreMoySup10G() : 0L);
          nombMoySup10Cycle1=nombMoySup10Cycle1+nombMoySup10;

          long nombMoySup10G =
              (classe.getNbreMoySup10G() != null ? classe.getNbreMoySup10G() : 0L);
          nombMoySup10Cycle1G=nombMoySup10Cycle1G+nombMoySup10G;

          long nombMoySup10F =
              (classe.getNbreMoySup10F() != null ? classe.getNbreMoySup10F() : 0L);
          nombMoySup10Cycle1F=nombMoySup10Cycle1F+nombMoySup10F;

          long nombMoyInf10 =
              (classe.getNbreMoyInf999F() != null ? classe.getNbreMoyInf999F() : 0L) +
                  (classe.getNbreMoyInf999G() != null ? classe.getNbreMoyInf999G() : 0L);
          nombMoyInf10Cycle1=nombMoyInf10Cycle1+nombMoyInf10;

          long nombMoyInf10G =classe.getNbreMoyInf999G() != null ? classe.getNbreMoyInf999G() : 0L;
          nombMoyInf10Cycle1G=nombMoyInf10Cycle1G+nombMoyInf10G;

          long nombMoyInf10F = (classe.getNbreMoyInf999F() != null ? classe.getNbreMoyInf999F() : 0L) ;
          nombMoyInf10Cycle1F=nombMoyInf10Cycle1F+nombMoyInf10F;





          long nombMoyInf8_5 =
              (classe.getNbreMoyInf85F() != null ? classe.getNbreMoyInf85F() : 0L) +
                  (classe.getNbreMoyInf85G() != null ? classe.getNbreMoyInf85G() : 0L);
          nombMoyInf8_5Cycle1=nombMoyInf8_5Cycle1+nombMoyInf8_5;

          long nombMoyInf8_5G =
              (classe.getNbreMoyInf85G() != null ? classe.getNbreMoyInf85G() : 0L);
          nombMoyInf8_5Cycle1G=nombMoyInf8_5Cycle1G+nombMoyInf8_5G;

          long nombMoyInf8_5F =(classe.getNbreMoyInf85F() != null ? classe.getNbreMoyInf85F() : 0L) ;
          nombMoyInf8_5Cycle1F=nombMoyInf8_5Cycle1F+nombMoyInf8_5F;



          long effectifClasse =
              (classe.getClassF() != null ? classe.getClassF() : 0L) +
                  (classe.getClassG() != null ? classe.getClassG() : 0L);


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



          long effNonClass =
              (classe.getNonclassF() != null ? classe.getNonclassF() : 0L) +
                  (classe.getNonclassG() != null ? classe.getNonclassG() : 0L);



          long effectif =
              (classe.getEffeF() != null ? classe.getEffeF() : 0L) +
                  (classe.getEffeG() != null ? classe.getEffeG() : 0L);







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



            // Créer une ligne principale pour la classe (6ème 1, 6ème 2, etc.)
            //XWPFTableRow classRow = table.createRow();
           // ensureCellCount(classRow, 14); // 14 colonnes comme dans le modèle
        libelleNiveau=afficherValeurParNiveau(classe.getNiveau());
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
      XWPFTableRow generalfillesRow = table.createRow();
      ensureCellCount(generalfillesRow, 12);
      generalfillesRow.getCell(0).setText("Total "+libelleNiveau);
      generalfillesRow.getCell(1).setText(" F");
      generalfillesRow.getCell(2).setText(String.valueOf(effectifCycle1F));
      generalfillesRow.getCell(3).setText(String.valueOf(effectifClasseCycle1F));
      generalfillesRow.getCell(4).setText(String.valueOf(effNonClassCycle1F));
      generalfillesRow.getCell(5).setText(String.valueOf(nombMoySup10Cycle1F));
      generalfillesRow.getCell(6).setText(String.valueOf(arrondie(pourSup10Cycle1F)));
      generalfillesRow.getCell(7).setText(String.valueOf(nombMoyInf10Cycle1F));
      generalfillesRow.getCell(8).setText(String.valueOf(arrondie(pourInf10Cycle1F)));
      generalfillesRow.getCell(9).setText(String.valueOf(nombMoyInf8_5Cycle1F));
      generalfillesRow.getCell(10).setText(String.valueOf(arrondie(pourInf8_5Cycle1F)));
      generalfillesRow.getCell(11).setText(String.valueOf(arrondie(moyenCycle1F)));

      XWPFTableRow generalgarconsRow = table.createRow();
      ensureCellCount(generalgarconsRow, 12);
      generalgarconsRow.getCell(1).setText(" G");
      generalgarconsRow.getCell(2).setText(String.valueOf(effectifCycle1G));
      generalgarconsRow.getCell(3).setText(String.valueOf(effectifClasseCycle1G));
      generalgarconsRow.getCell(4).setText(String.valueOf(effNonClassCycle1G));
      generalgarconsRow.getCell(5).setText(String.valueOf(nombMoySup10Cycle1G));
      generalgarconsRow.getCell(6).setText(String.valueOf(arrondie(pourSup10Cycle1G)));
      generalgarconsRow.getCell(7).setText(String.valueOf(nombMoyInf10Cycle1G));
      generalgarconsRow.getCell(8).setText(String.valueOf(arrondie(pourInf10Cycle1G)));
      generalgarconsRow.getCell(9).setText(String.valueOf(nombMoyInf8_5Cycle1G));
      generalgarconsRow.getCell(10).setText(String.valueOf(arrondie(pourInf8_5Cycle1G)));
      generalgarconsRow.getCell(11).setText(String.valueOf(arrondie(moyenCycle1G)));

      // Remplir la ligne pour le total (T)
      XWPFTableRow GeneraltotalRow = table.createRow();
      ensureCellCount(GeneraltotalRow, 12);
      GeneraltotalRow.getCell(1).setText(" T");
      GeneraltotalRow.getCell(2).setText(String.valueOf(effectifCycle1));
      GeneraltotalRow.getCell(3).setText(String.valueOf(effectifClasseCycle1));
      GeneraltotalRow.getCell(4).setText(String.valueOf(effNonClassCycle1));
      GeneraltotalRow.getCell(5).setText(String.valueOf(nombMoySup10Cycle1));
      GeneraltotalRow.getCell(6).setText(String.valueOf(arrondie(pourSup10Cycle1)));
      GeneraltotalRow.getCell(7).setText(String.valueOf(nombMoyInf10Cycle1));
      GeneraltotalRow.getCell(8).setText(String.valueOf(arrondie(pourInf10Cycle1)));
      GeneraltotalRow.getCell(9).setText(String.valueOf(nombMoyInf8_5Cycle1));
      GeneraltotalRow.getCell(10).setText(String.valueOf(arrondie(pourInf8_5Cycle1)));
      GeneraltotalRow.getCell(11).setText(String.valueOf(arrondie(moyenCycle1)));
      mergeCellsVertically(table, 0, table.getNumberOfRows() - 3, table.getNumberOfRows() -1);

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
  public XWPFTable obtenirUnTableau(XWPFDocument document,String tableTitle){

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
    return targetTable;
  }
}

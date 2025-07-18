package com.vieecoles.processors.bouake.annuels;

import static com.vieecoles.processors.yamoussoukro.WordTempResultaAffProcessor.afficherValeurParNiveau;

import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.dto.ResultatsElevesNonAffecteDto;
import com.vieecoles.services.etats.appachePoi.Annuels.resultatsAnnuelsNonAffectePoiServices;
import com.vieecoles.services.etats.appachePoi.resultatsNonAffectePoiServices;
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
public class WordTempResultaNonAffAnnuelProcessor {
  @Inject
  com.vieecoles.services.etats.resultatsNonAffecteServices resultatsNonAffecteServices ;
    @Inject
    resultatsAnnuelsNonAffectePoiServices resultatsServices ;
  @Inject
  com.vieecoles.services.etats.appachePoi.resultatsNonAffectePoiIntervalServices resultatsNonAffectePoiIntervalServices ;


      public   void getResultatNonAffProcessor(XWPFDocument document ,
          Long idEcole ,String libelleAnnee , String libelleTrimetre) {
        List<ResultatsElevesNonAffecteDto> detailsBull6 = new ArrayList<>();
        List<ResultatsElevesNonAffecteDto> totalSeconde = new ArrayList<>();
        List<ResultatsElevesNonAffecteDto> totalPremiere = new ArrayList<>();
        List<ResultatsElevesNonAffecteDto> totalTerminale = new ArrayList<>();
        System.out.println("classeNiveauDtoList entree");
        try {
          detailsBull6= resultatsServices.CalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,1)  ;

          totalSeconde= resultatsNonAffectePoiIntervalServices.CalculResultatsEleveAffecte(idEcole,libelleAnnee,libelleTrimetre,4,7);
          totalPremiere= resultatsNonAffectePoiIntervalServices.CalculResultatsEleveAffecte(idEcole,libelleAnnee,libelleTrimetre,6,10);
          totalTerminale= resultatsNonAffectePoiIntervalServices.CalculResultatsEleveAffecte(idEcole,libelleAnnee,libelleTrimetre,9,15);

          System.out.println("classeNiveauDtoList Sortie");
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

        XWPFTable targetTable = null;
        // 3. Gestion des tableaux dynamiques
        // Sixième
        try{
          String tableTitle = "CODE_RECAP_RESUL_NON_AFFECT_CLASSE_ANNUEL";

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

        // 3. Gestion des tableaux dynamiques
        // Sixième
        try {
          if(!detailsBull6.isEmpty())
            ajoutTableauDynamique(detailsBull6,targetTable);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }
        // Cinquième
        // XWPFTable tableCinquieme = document.getTableArray(6);
        try {
          if(!detailsBull5.isEmpty())
            ajoutTableauDynamique(detailsBull5,targetTable);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Quatrieme

        try {
          if(!detailsBull4.isEmpty())
            ajoutTableauDynamique(detailsBull4,targetTable);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Troixième

        try {
          if(!detailsBull3.isEmpty())
            ajoutTableauDynamique(detailsBull3,targetTable);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // SecondeA
        // XWPFTable table2NDA = document.getTableArray(10);
        try {
          if(!detailsBull2NDA.isEmpty())
            ajoutCyle2TableauDynamique(detailsBull2NDA,targetTable);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // SecondeC

        try {
          if(!detailsBull2NDC.isEmpty())
            ajoutCyle2TableauDynamique(detailsBull2NDC,targetTable);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        //Total seconde

        try {
          if(!totalSeconde.isEmpty())
            ajoutTotalSeconcycleTableauDynamique(totalSeconde,targetTable);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // PremiereA

        try {
          if(!detailsBull1EREA.isEmpty())
            ajoutCyle2TableauDynamique(detailsBull1EREA,targetTable);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // PremiereC

        try {
          if(!detailsBull1EREC.isEmpty())
            ajoutCyle2TableauDynamique(detailsBull1EREC,targetTable);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // PremiereD

        try {
          if(!detailsBull1ERED.isEmpty())
            ajoutCyle2TableauDynamique(detailsBull1ERED,targetTable);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        //Total premiere

        try {
          if(!totalPremiere.isEmpty())
            ajoutTotalSeconcycleTableauDynamique(totalPremiere,targetTable);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Terminale A

        try {
          if(!detailsBullTLEA.isEmpty())
            ajoutCyle2TableauDynamique(detailsBullTLEA,targetTable);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Terminale A1

        try {
          if(!detailsBullTLEA1.isEmpty())
            ajoutCyle2TableauDynamique(detailsBullTLEA1,targetTable);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Terminale A2

        try {
          if(!detailsBullTLEA2.isEmpty())
            ajoutCyle2TableauDynamique(detailsBullTLEA2,targetTable);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Terminale C

        try {
          if(!detailsBullTLEC.isEmpty())
            ajoutCyle2TableauDynamique(detailsBullTLEC,targetTable);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // Terminale D

        try {
          if(!detailsBullTLED.isEmpty())
            ajoutCyle2TableauDynamique(detailsBullTLED,targetTable);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        //Total terminale

        try {
          if(!totalTerminale.isEmpty())
            ajoutTotalSeconcycleTableauDynamique(totalTerminale,targetTable);
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
        libelleNiveau=classe.getNiveau();


// Remplir la ligne pour le total (T)
        XWPFTableRow totalRow = table.createRow();
        ensureCellCount(totalRow, 9);
        totalRow.getCell(0).setText(classe.getClasse());
        totalRow.getCell(1).setText(String.valueOf(effectif));
        totalRow.getCell(2).setText(String.valueOf(effectifClasse));
        totalRow.getCell(3).setText(String.valueOf(nombMoySup10));
        totalRow.getCell(4).setText(String.valueOf(arrondie(pourSup10)));
        totalRow.getCell(5).setText(String.valueOf(nombMoyInf10));
        totalRow.getCell(6).setText(String.valueOf(arrondie(pourInf10)));
        totalRow.getCell(7).setText(String.valueOf(nombMoyInf8_5));
        totalRow.getCell(8).setText(String.valueOf(arrondie(pourInf8_5)));
        }


      // Remplir la ligne pour le total (T)
      XWPFTableRow GeneraltotalRow = table.createRow();
      ensureCellCount(GeneraltotalRow, 9);
      GeneraltotalRow.getCell(0).setText("EFF. TOTAL des "+afficherValeurParNiveau(libelleNiveau));
      GeneraltotalRow.getCell(1).setText(String.valueOf(effectifCycle1));
      GeneraltotalRow.getCell(2).setText(String.valueOf(effectifClasseCycle1));
      GeneraltotalRow.getCell(3).setText(String.valueOf(nombMoySup10Cycle1));
      GeneraltotalRow.getCell(4).setText(String.valueOf(arrondie(pourSup10Cycle1)));
      GeneraltotalRow.getCell(5).setText(String.valueOf(nombMoyInf10Cycle1));
      GeneraltotalRow.getCell(6).setText(String.valueOf(arrondie(pourInf10Cycle1)));
      GeneraltotalRow.getCell(7).setText(String.valueOf(nombMoyInf8_5Cycle1));
      GeneraltotalRow.getCell(8).setText(String.valueOf(arrondie(pourInf8_5Cycle1)));

      for (int i = 0; i <= 8; i++) {
        GeneraltotalRow.getCell(i).setColor("ADD8E6"); // Bleu clair en Hexadécimal
      }
    }
  private static void ajoutTotalSeconcycleTableauDynamique(List<ResultatsElevesNonAffecteDto> detailsBull, XWPFTable table) {

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
      libelleNiveau=classe.getNiveau();

    }


    // Remplir la ligne pour le total (T)
    XWPFTableRow GeneraltotalRow = table.createRow();
    ensureCellCount(GeneraltotalRow, 9);
    GeneraltotalRow.getCell(0).setText("EFF. TOTAL des "+afficherSecondCycle(libelleNiveau));
    GeneraltotalRow.getCell(1).setText(String.valueOf(effectifCycle1));
    GeneraltotalRow.getCell(2).setText(String.valueOf(effectifClasseCycle1));
    GeneraltotalRow.getCell(3).setText(String.valueOf(nombMoySup10Cycle1));
    GeneraltotalRow.getCell(4).setText(String.valueOf(arrondie(pourSup10Cycle1)));
    GeneraltotalRow.getCell(5).setText(String.valueOf(nombMoyInf10Cycle1));
    GeneraltotalRow.getCell(6).setText(String.valueOf(arrondie(pourInf10Cycle1)));
    GeneraltotalRow.getCell(7).setText(String.valueOf(nombMoyInf8_5Cycle1));
    GeneraltotalRow.getCell(8).setText(String.valueOf(arrondie(pourInf8_5Cycle1)));

    for (int i = 0; i <= 8; i++) {
      GeneraltotalRow.getCell(i).setColor("ADD8E6"); // Bleu clair en Hexadécimal
    }
  }
  public static String afficherSecondCycle(String niveau) {
    // Mapping des niveaux avec leurs correspondants
    switch (niveau) {

      case "Seconde C":
        return "2ndes";
      case "Seconde A":
        return "2ndes";
      case "Première A":
        return "1ères";
      case "Première C":
        return "1ères";
      case "Première D":
        return "1ères";
      case "Terminale A":
        return "Tles";
      case "Terminale A1":
        return "Tles";
      case "Terminale A2":
        return "Tles";
      case "Terminale C":
        return "Tles";
      case "Terminale D":
        return "Tles";
      default:
        return niveau;
    }
  }
  private static void ajoutCyle2TableauDynamique(List<ResultatsElevesNonAffecteDto> detailsBull, XWPFTable table) {

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
      libelleNiveau=classe.getNiveau();


// Remplir la ligne pour le total (T)
      XWPFTableRow totalRow = table.createRow();
      ensureCellCount(totalRow, 9);
      totalRow.getCell(0).setText(classe.getClasse());
      totalRow.getCell(1).setText(String.valueOf(effectif));
      totalRow.getCell(2).setText(String.valueOf(effectifClasse));
      totalRow.getCell(3).setText(String.valueOf(nombMoySup10));
      totalRow.getCell(4).setText(String.valueOf(arrondie(pourSup10)));
      totalRow.getCell(5).setText(String.valueOf(nombMoyInf10));
      totalRow.getCell(6).setText(String.valueOf(arrondie(pourInf10)));
      totalRow.getCell(7).setText(String.valueOf(nombMoyInf8_5));
      totalRow.getCell(8).setText(String.valueOf(arrondie(pourInf8_5)));
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

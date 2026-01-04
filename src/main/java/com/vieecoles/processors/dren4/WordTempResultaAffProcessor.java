package com.vieecoles.processors.dren4;

import static org.apache.poi.ss.usermodel.TableStyleType.totalRow;

import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.services.etats.appachePoi.resultatsPoiServices;
import com.vieecoles.services.etats.resultatsRecapServices;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.*;
@ApplicationScoped
public class WordTempResultaAffProcessor {
    @Inject
    resultatsPoiServices resultatsServices ;
    @Inject
    resultatsRecapServices resultatsRecapServices ;
  @Inject
  EntityManager em;

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

        // SecondeA et SecondeC fusionnées
        List<ResultatsElevesAffecteDto> detailsBull2ND = new ArrayList<>();
        detailsBull2ND.addAll(detailsBull2NDA);
        detailsBull2ND.addAll(detailsBull2NDC);
        
        XWPFTable table2NDA = document.getTableArray(10);
        try {
          ajoutTableauDynamique(detailsBull2ND,table2NDA);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

        // PremiereA, PremiereC et PremiereD fusionnées
        List<ResultatsElevesAffecteDto> detailsBull1ERE = new ArrayList<>();
        detailsBull1ERE.addAll(detailsBull1EREA);
        detailsBull1ERE.addAll(detailsBull1EREC);
        detailsBull1ERE.addAll(detailsBull1ERED);
        
        XWPFTable table1EREA = document.getTableArray(11);
        try {
          ajoutTableauDynamique(detailsBull1ERE,table1EREA);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }


        // Terminale A, A1, A2, C et D fusionnées
        List<ResultatsElevesAffecteDto> detailsBullTLE = new ArrayList<>();
        detailsBullTLE.addAll(detailsBullTLEA);
        detailsBullTLE.addAll(detailsBullTLEA1);
        detailsBullTLE.addAll(detailsBullTLEA2);
        detailsBullTLE.addAll(detailsBullTLEC);
        detailsBullTLE.addAll(detailsBullTLED);
        
        XWPFTable tableTLEA = document.getTableArray(12);
        try {
          ajoutTableauDynamique(detailsBullTLE,tableTLEA);
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

        for (ResultatsElevesAffecteDto classe : detailsBull) {
       /*     long nombMoySup10 =
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


          libelleNiveau=afficherValeurParNiveau(classe.getNiveau());


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
      String totalLibelle;
      if (libelleNiveau != null) {
        if (libelleNiveau.startsWith("2nde")) {
          totalLibelle = "Total 2nde";
        } else if (libelleNiveau.startsWith("1ère")) {
          totalLibelle = "Total 1ère";
        } else if (libelleNiveau.startsWith("Tle")) {
          totalLibelle = "Total Tle";
        } else {
          totalLibelle = "Total "+libelleNiveau;
        }
      } else {
        totalLibelle = "Total "+libelleNiveau;
      }
      generalfillesRow.getCell(0).setText(totalLibelle);
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
        return niveau;
    }
  }
}

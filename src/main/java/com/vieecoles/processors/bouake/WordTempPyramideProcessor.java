package com.vieecoles.processors.bouake;

import com.vieecoles.dto.EffectifiConseilClasseDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.services.etats.appachePoi.EffectifConseilClassePoiServices;
import com.vieecoles.services.etats.appachePoi.PyaramideClassePoiServices;
import com.vieecoles.services.etats.resultatsRecapServices;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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
public class WordTempPyramideProcessor {
    @Inject
    PyaramideClassePoiServices resultatsServices ;
    @Inject
    resultatsRecapServices resultatsRecapServices ;
  @Inject
  EntityManager em;

      public   void getNombreClasseProcessor(XWPFDocument document ,
          Long idEcole ,String libelleAnnee , String libelleTrimetre) {

        long effeTotalEtablisseG = 0l;
        long effeTotalEtablisseF = 0l;
        int detailsBull6 = 0;

        try {
          detailsBull6= resultatsServices.getNombreClasse(idEcole ,libelleAnnee,libelleTrimetre,1)  ;
          System.out.println("classeNiveauDtoList Sortie");
        } catch (Exception e) {
          e.printStackTrace();
        }
        // Cinquième

        int detailsBull5 = 0 ;
        try {
          detailsBull5= resultatsServices.getNombreClasse(idEcole ,libelleAnnee,libelleTrimetre,2)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Quatrieme

        int detailsBull4 = 0;
        try {
          detailsBull4= resultatsServices.getNombreClasse(idEcole ,libelleAnnee,libelleTrimetre,3)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }


        // Troixième

        int detailsBull3 = 0;
        try {
          detailsBull3= resultatsServices.getNombreClasse(idEcole ,libelleAnnee,libelleTrimetre,4)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }
        // Seconde A
        int detailsBull2NDA = 0 ;
        try {
         detailsBull2NDA= resultatsServices.getNombreClasse(idEcole ,libelleAnnee,libelleTrimetre,5)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Seconde C
        int detailsBull2NDC = 0 ;
        try {
          detailsBull2NDC= resultatsServices.getNombreClasse(idEcole ,libelleAnnee,libelleTrimetre,6)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Premiere A
        int detailsBull1EREA = 0;
        try {
          detailsBull1EREA= resultatsServices.getNombreClasse(idEcole ,libelleAnnee,libelleTrimetre,7)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Premiere C
        int detailsBull1EREC = 0;
        try {
        detailsBull1EREC = resultatsServices.getNombreClasse(idEcole ,libelleAnnee,libelleTrimetre,8)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Premiere D
        int detailsBull1ERED = 0 ;
        try {
         detailsBull1ERED = resultatsServices.getNombreClasse(idEcole ,libelleAnnee,libelleTrimetre,9)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Terminale A
        int detailsBullTLEA = 0 ;
        try {
          detailsBullTLEA = resultatsServices.getNombreClasse(idEcole ,libelleAnnee,libelleTrimetre,10)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Terminale A1
        int detailsBullTLEA1 = 0 ;
        try {
          detailsBullTLEA1 = resultatsServices.getNombreClasse(idEcole ,libelleAnnee,libelleTrimetre,11)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Terminale A2
        int detailsBullTLEA2 = 0;
        try {
         detailsBullTLEA2 = resultatsServices.getNombreClasse(idEcole ,libelleAnnee,libelleTrimetre,12)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Terminale C
        int detailsBullTLEC = 0;
        try {
        detailsBullTLEC = resultatsServices.getNombreClasse(idEcole ,libelleAnnee,libelleTrimetre,13)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }

        // Terminale D
        int detailsBullTLED = 0 ;
        try {
        detailsBullTLED = resultatsServices.getNombreClasse(idEcole ,libelleAnnee,libelleTrimetre,14)  ;

        } catch (Exception e) {
          e.printStackTrace();
        }
        XWPFTable targetTable = null;
        // 3. Gestion des tableaux dynamiques
        // Sixième
        try{
          String tableTitle = "CODE_PYRAMIDE";

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



        // effectif Total Etablissement
        XWPFTableRow GeneraltotalEtabliRow = targetTable.createRow();
        ensureCellCount(GeneraltotalEtabliRow, 13);
        GeneraltotalEtabliRow.getCell(0).setText("Base");
        GeneraltotalEtabliRow.getCell(1).setText(String.valueOf(detailsBull6));
        GeneraltotalEtabliRow.getCell(2).setText(String.valueOf(detailsBull5));
        GeneraltotalEtabliRow.getCell(3).setText(String.valueOf(detailsBull4));
        GeneraltotalEtabliRow.getCell(4).setText(String.valueOf(detailsBull3));
        GeneraltotalEtabliRow.getCell(5).setText(String.valueOf(detailsBull2NDA));
        GeneraltotalEtabliRow.getCell(6).setText(String.valueOf(detailsBull2NDC));
        GeneraltotalEtabliRow.getCell(7).setText(String.valueOf(detailsBull1EREA));
        GeneraltotalEtabliRow.getCell(8).setText(String.valueOf(detailsBull1EREC));
        GeneraltotalEtabliRow.getCell(9).setText(String.valueOf(detailsBull1ERED));
        GeneraltotalEtabliRow.getCell(10).setText(String.valueOf(detailsBullTLEA+detailsBullTLEA1+detailsBullTLEA2));
        GeneraltotalEtabliRow.getCell(11).setText(String.valueOf(detailsBullTLEC));
        GeneraltotalEtabliRow.getCell(12).setText(String.valueOf(detailsBullTLED));

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


    private static void ajoutTableauDynamique(List<EffectifiConseilClasseDto> detailsBull, XWPFTable table ) {

      String libelleNiveau="";
       long effeTotalG = 0l;
      long effeTotalF = 0l;

        for (EffectifiConseilClasseDto classe : detailsBull) {
          effeTotalG =effeTotalG+classe.getEffeG() ;
          effeTotalF =effeTotalF+classe.getEffeF() ;
          libelleNiveau= classe.getNiveau();

        }


      // Remplir la ligne pour le total (T)
      XWPFTableRow GeneraltotalRow = table.createRow();
      ensureCellCount(GeneraltotalRow, 4);
      GeneraltotalRow.getCell(0).setText(afficherValeurParNiveau(libelleNiveau));
      GeneraltotalRow.getCell(1).setText(String.valueOf(effeTotalG));
      GeneraltotalRow.getCell(2).setText(String.valueOf(effeTotalF));
      GeneraltotalRow.getCell(3).setText(String.valueOf(effeTotalG+effeTotalF));

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

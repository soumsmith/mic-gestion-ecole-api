package com.vieecoles.processors.bouake;

import com.vieecoles.dto.MajorParClasseNiveauDto;
import com.vieecoles.dto.MajorParNiveauDto;
import com.vieecoles.services.etats.appachePoi.EleveAffecteParClassePoiServices;
import com.vieecoles.services.etats.appachePoi.MajorParClasseNiveauPoiServices;
import com.vieecoles.services.etats.appachePoi.MajorParNiveauPoiServices;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class WordTempListMajorProcessor {
    @Inject
    EntityManager em;
    @Inject
    EleveAffecteParClassePoiServices eleveAffecteParClassePoiServices ;
    @Inject
    MajorParNiveauPoiServices majorServices ;
    int LongTableau;

    private static void ensureCellCount(XWPFTableRow row, int cellCount) {
        int currentCellCount = row.getTableCells().size();
        for (int i = currentCellCount; i < cellCount; i++) {
            row.addNewTableCell(); // Ajouter une nouvelle cellule si nécessaire
        }
    }
    public   void getListeMajorParNiveau(XWPFDocument document ,
                                           Long idEcole ,String libelleAnnee , String libelleTrimestre) {


//Get classe

        // Créer une nouvelle ligne dans le tableau

        // Rechercher l'endroit où insérer le tableau
        List<XWPFParagraph> paragraphs = document.getParagraphs();


        String tableTitle = "CODE_LISTE_MAJOR_CLASSE";
        XWPFTable targetTable = null;
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


            List<MajorParNiveauDto>  listeMajors = new ArrayList<>() ;
            listeMajors= majorServices.MajorParNiveauClasse(idEcole ,libelleAnnee,libelleTrimestre) ;



            // Ajouter des lignes au tableau
            for (MajorParNiveauDto eleve : listeMajors) {

                XWPFTableRow row = targetTable.createRow();
                row.getCell(0).setText(afficherValeurParNiveau(eleve.getNiveau()));
                row.getCell(1).setText(eleve.getMatricule());
                row.getCell(2).setText(eleve.getNom()+" "+eleve.getPrenom());
                row.getCell(3).setText(eleve.getSexe());
                row.getCell(4).setText(eleve.getAnneeNaiss());
                row.getCell(5).setText(eleve.getNationalite());
                row.getCell(6).setText(eleve.getRedoublant());
                row.getCell(7).setText(String.valueOf(eleve.getMoyGeneral()));
                row.getCell(8).setText("1er");
                row.getCell(9).setText(eleve.getClasseLibelle());


        }



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

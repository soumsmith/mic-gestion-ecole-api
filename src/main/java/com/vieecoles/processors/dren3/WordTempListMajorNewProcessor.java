package com.vieecoles.processors.dren3;

import com.vieecoles.dto.MajorParClasseNiveauDren3Dto;
import com.vieecoles.dto.MajorParClasseNiveauDto;
import com.vieecoles.dto.NiveauOrderDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;
import com.vieecoles.processors.dren3.services.MajorParClasseDren3Services;
import com.vieecoles.services.etats.appachePoi.EleveAffecteParClassePoiServices;
import com.vieecoles.services.etats.appachePoi.MajorParClasseNiveauPoiServices;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;

@ApplicationScoped
public class WordTempListMajorNewProcessor {
    @Inject
    EntityManager em;
    @Inject
    MajorParClasseDren3Services majorServices  ;
    int LongTableau;

    private static void ensureCellCount(XWPFTableRow row, int cellCount) {
        int currentCellCount = row.getTableCells().size();
        for (int i = currentCellCount; i < cellCount; i++) {
            row.addNewTableCell(); // Ajouter une nouvelle cellule si nécessaire
        }
    }
    public   void getMajorParNiveau(XWPFDocument document ,
                                           Long idEcole ,String libelleAnnee , String libelleTrimestre) {


//Get classe
        List<NiveauOrderDto> classeList = new ArrayList<>() ;
        TypedQuery<NiveauOrderDto> c = em.createQuery( "SELECT new com.vieecoles.dto.NiveauOrderDto(b.niveau,b.ordreNiveau) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee  " +
                "group by b.ordreNiveau,b.niveau order by b.ordreNiveau,b.niveau", NiveauOrderDto.class);
        classeList = c.setParameter("idEcole", idEcole)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .getResultList() ;


        // Créer une nouvelle ligne dans le tableau

        // Rechercher l'endroit où insérer le tableau
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        int indexToInsert = -1;

        for (int i = 0; i < paragraphs.size(); i++) {
            String text = paragraphs.get(i).getText();
            // Identifier l'emplacement où insérer le tableau (par exemple après "Liste des élèves affectés par classe")
            if (text.contains("LISTE DES MAJORS DE CLASSE DU PREMIER TRIMESTRE  (03 par niveau)")) {
                indexToInsert = i + 1; // Ajouter après ce paragraphe

                break;
            }
        }

        for (int k = classeList.size() - 1; k >= 0; k--) {
            List<MajorParClasseNiveauDren3Dto> elevAffectes = new ArrayList<>() ;

            elevAffectes= majorServices.MajorParNiveauClasse(idEcole ,libelleAnnee,libelleTrimestre,classeList.get(k).getNiveau()) ;

        if (indexToInsert != -1) {
           // for (int z=0; z< classeList.size();z++) {

            // Créer un nouveau paragraphe avant d'insérer le tableau
            XWPFParagraph newParagraph = document.insertNewParagraph(paragraphs.get(indexToInsert).getCTP().newCursor());
            XWPFRun run = newParagraph.createRun();
            run.setText("Niveau "+classeList.get(k).getNiveau());
            run.setBold(true);  // Mettre le texte en gras
            newParagraph.setAlignment(ParagraphAlignment.LEFT);

            // Insérer le tableau juste après le paragraphe
            XWPFTable table = document.insertNewTbl(paragraphs.get(indexToInsert+1).getCTP().newCursor());

            // Créer l'en-tête du tableau (1 ligne, 11 colonnes)
            XWPFTableRow headerRow = table.getRow(0);
            setHeaderCell(headerRow.getCell(0), "N°", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "ETABLISSEMENTS", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "N°", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "MATRICULE", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "NOM ET PRENOMS", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "AGE (NE(E)LE)", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "GENRE", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "NAT.", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "RED", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "STATUT AFF /NON AFF", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "N° DECISION D’AFF", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "LV2", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "M/20", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "RANG", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "CLASSE", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "OBSERVATIONS", "D9D9D9");

            // Ajouter des lignes au tableau
            int numerotation = 1;
            for (MajorParClasseNiveauDren3Dto eleve : elevAffectes) {  // Exemple de 3 lignes
                XWPFTableRow row = table.createRow();
                ensureCellCount(row, 16);  // Assurez-vous que chaque ligne a 16 cellules

                // Définir le texte et la taille des cellules de la ligne
                setCellTextAndFontSize(row.getCell(0), String.valueOf(numerotation), 10);
                setCellTextAndFontSize(row.getCell(1), "", 10);
                setCellTextAndFontSize(row.getCell(2), String.valueOf(numerotation), 10);
                setCellTextAndFontSize(row.getCell(3), eleve.getMatricule(), 10);
                setCellTextAndFontSize(row.getCell(4), eleve.getNom() + " " + eleve.getPrenom(), 10);
                setCellTextAndFontSize(row.getCell(5), eleve.getAnneeNaiss(), 10);
                setCellTextAndFontSize(row.getCell(6), eleve.getSexe(), 10);
                setCellTextAndFontSize(row.getCell(7), eleve.getNationalite(), 10);
                setCellTextAndFontSize(row.getCell(8), eleve.getRedoublant(), 10);
                setCellTextAndFontSize(row.getCell(9), eleve.getAffecte(), 10);
                setCellTextAndFontSize(row.getCell(10), eleve.getDecisionAff(), 10);
                setCellTextAndFontSize(row.getCell(11), eleve.getLv2(), 10);
                setCellTextAndFontSize(row.getCell(12), String.valueOf(eleve.getMoyGeneral()), 10);
                setCellTextAndFontSize(row.getCell(13), String.valueOf(eleve.getRang()), 10);
                setCellTextAndFontSize(row.getCell(14), String.valueOf(eleve.getClasseLibelle()), 10);
                setCellTextAndFontSize(row.getCell(15), eleve.getAppreciation(), 10);

                numerotation++;
            }
        //}
        }
    }
    }
    public static void setHeaderCell(XWPFTableCell cell, String text, String bgColor) {
        // Définir le texte de la cellule
        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        XWPFRun run = paragraph.createRun();
        run.setText(text);

        // Définir la taille de la police (10 points)
        run.setFontSize(10);

        // Centrer le texte dans la cellule
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        paragraph.setVerticalAlignment(TextAlignment.CENTER);

        // Définir la couleur de fond de la cellule (bgColor doit être en format hexadécimal)
        cell.setColor(bgColor);

        // Vérifier si les bordures existent, sinon les ajouter
        if (cell.getCTTc().getTcPr().getTcBorders() == null) {
            cell.getCTTc().getTcPr().addNewTcBorders();
        }

        // Définir un style de bordure (facultatif)
        cell.getCTTc().getTcPr().getTcBorders()
            .addNewTop().setVal(STBorder.SINGLE);
        cell.getCTTc().getTcPr().getTcBorders()
            .addNewBottom().setVal(STBorder.SINGLE);
        cell.getCTTc().getTcPr().getTcBorders()
            .addNewLeft().setVal(STBorder.SINGLE);
        cell.getCTTc().getTcPr().getTcBorders()
            .addNewRight().setVal(STBorder.SINGLE);
    }


    public static void setCellTextAndFontSize(XWPFTableCell cell, String text, int fontSize) {
        // Obtenir ou créer un paragraphe dans la cellule
        XWPFParagraph paragraph = cell.getParagraphs().get(0);

        // Créer un nouveau run ou obtenir celui existant pour modifier le texte
        XWPFRun run = paragraph.createRun();

        // Définir le texte de la cellule
        run.setText(text);

        // Définir la taille de la police à 10 points
        run.setFontSize(fontSize);

        // Centrer le texte dans la cellule (facultatif, selon votre mise en forme)
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        paragraph.setVerticalAlignment(TextAlignment.CENTER);
    }

}

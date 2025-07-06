package com.vieecoles.processors.dren3.annuels;

import static com.vieecoles.processors.dren3.WordTempListAffectesProcessor.setCellTextAndFontSize;
import static com.vieecoles.processors.dren3.WordTempListAffectesProcessor.setHeaderCell;

import com.vieecoles.dto.NiveauOrderDto;
import com.vieecoles.dto.eleveAffecteParClasseDtoAvecTousTrimestres;
import com.vieecoles.services.etats.appachePoi.Annuels.EleveAffecteAnnuelsParClassePoiServices;
import com.vieecoles.services.etats.appachePoi.EleveAffecteParClassePoiServices;
import com.vieecoles.steph.entities.Ecole;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

@ApplicationScoped
public class WordTempListAffectesAnnuelProcessor {
    @Inject
    EntityManager em;
    @Inject
    EleveAffecteAnnuelsParClassePoiServices eleveAffecteParClassePoiServices ;
    int LongTableau;

    private static void ensureCellCount(XWPFTableRow row, int cellCount) {
        int currentCellCount = row.getTableCells().size();
        for (int i = currentCellCount; i < cellCount; i++) {
            row.addNewTableCell(); // Ajouter une nouvelle cellule si nécessaire
        }
    }
    public   void getEleveAffecteParClasse(XWPFDocument document ,
                                           Long idEcole ,String libelleAnnee , String libelleTrimestre) {


//Get classe
        List<NiveauOrderDto> classeList = new ArrayList<>() ;
        TypedQuery<NiveauOrderDto> c = em.createQuery( "SELECT new com.vieecoles.dto.NiveauOrderDto(b.libelleClasse,b.ordreNiveau) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee  " +
                "group by b.ordreNiveau,b.libelleClasse order by b.ordreNiveau,b.libelleClasse", NiveauOrderDto.class);
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
            if (text.contains("ELEVES AFFECTES(ANNUELS)")) {
                indexToInsert = i + 1; // Ajouter après ce paragraphe

                break;
            }
        }

        for (int k = classeList.size() - 1; k >= 0; k--) {
            List<eleveAffecteParClasseDtoAvecTousTrimestres>  elevAffectes = new ArrayList<>() ;
            elevAffectes= eleveAffecteParClassePoiServices.eleveAffecteParClasse(idEcole,libelleAnnee,libelleTrimestre,classeList.get(k).getNiveau());

        if (indexToInsert != -1) {
           // for (int z=0; z< classeList.size();z++) {

            // Créer un nouveau paragraphe avant d'insérer le tableau
            XWPFParagraph newParagraph = document.insertNewParagraph(paragraphs.get(indexToInsert).getCTP().newCursor());
            XWPFRun run = newParagraph.createRun();
            if(!elevAffectes.isEmpty())
                run.setText(classeList.get(k).getNiveau()+" Professeur Principal: "+elevAffectes.get(0).getProfesseurPrincipal() +" Educateur: "+elevAffectes.get(0).getNomEducateur());
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
            setHeaderCell(headerRow.addNewTableCell(), "MOY T1", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "MOY T2", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "MOY T3", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "MOY AN", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "RANG AN", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "CLASSE", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "OBSERVATIONS", "D9D9D9");

            // Ajouter des lignes au tableau
            int numerotation = 1;
            String currentValue = "";
            Ecole MyEcole;
            MyEcole= Ecole.findById(idEcole);
            currentValue = MyEcole.getLibelle();
            for (eleveAffecteParClasseDtoAvecTousTrimestres eleve : elevAffectes) {  // Exemple de 3 lignes
                XWPFTableRow row = table.createRow();
                if (row == null) {
                    row = table.insertNewTableRow(table.getNumberOfRows());
                }
                ensureCellCount(row, 19);  // Assurez-vous que chaque ligne a 16 cellules

                // Définir le texte et la taille des cellules de la ligne
                setCellTextAndFontSize(row.getCell(0), String.valueOf(numerotation), 10);
                //setCellTextAndFontSize(row.getCell(1), "", 10);
                setCellTextAndFontSize(row.getCell(2), String.valueOf(numerotation), 10);
                setCellTextAndFontSize(row.getCell(3), eleve.getMatricule(), 10);
                setCellTextAndFontSize(row.getCell(4), eleve.getNomEleve() + " " + eleve.getPrenomEleve(), 10);
                setCellTextAndFontSize(row.getCell(5), eleve.getAnneeNaissance(), 10);
                setCellTextAndFontSize(row.getCell(6), eleve.getSexe(), 10);
                setCellTextAndFontSize(row.getCell(7), eleve.getNationnalite(), 10);
                setCellTextAndFontSize(row.getCell(8), eleve.getRedoublan(), 10);
                setCellTextAndFontSize(row.getCell(9), eleve.getAffecte(), 10);
                setCellTextAndFontSize(row.getCell(10), eleve.getNumDecisionAffecte(), 10);
                setCellTextAndFontSize(row.getCell(11), eleve.getLv2(), 10);
                setCellTextAndFontSize(row.getCell(12), String.valueOf(eleve.getMoyeGeneralTrim1()), 10);
                setCellTextAndFontSize(row.getCell(13), String.valueOf(eleve.getMoyeGeneralTrim2()), 10);
                setCellTextAndFontSize(row.getCell(14), String.valueOf(eleve.getMoyeGeneralTrim3()), 10);
                setCellTextAndFontSize(row.getCell(15), String.valueOf(eleve.getMoyenAnnuel()), 10);
                setCellTextAndFontSize(row.getCell(16), String.valueOf(eleve.getRangAn()), 10);
                setCellTextAndFontSize(row.getCell(17), String.valueOf(eleve.getClasseLibelle()), 10);
                setCellTextAndFontSize(row.getCell(18), eleve.getObservat(), 10);
                mergeCellsVertically(table, 1, 1, table.getNumberOfRows()-1 );

                numerotation++;
            }
            //table.getRow(1).getCell(1).setText(currentValue);
            XWPFTableRow specificRow = table.getRow(1);
            if (specificRow != null && specificRow.getCell(1) != null) {
                specificRow.getCell(1).setText(currentValue);
            }


        //}
        }

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

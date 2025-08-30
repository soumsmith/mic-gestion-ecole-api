package com.vieecoles.processors.yamoussoukro.Annuels;

import com.vieecoles.dto.BoursierDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.services.etats.BoursiersServices;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

@ApplicationScoped
public class WordTempListBoursiersAnnuelsProcessor {
    @Inject
    EntityManager em;
    @Inject
    BoursiersServices boursiersServices ;
    int LongTableau;

    private static void ensureCellCount(XWPFTableRow row, int cellCount) {
        int currentCellCount = row.getTableCells().size();
        for (int i = currentCellCount; i < cellCount; i++) {
            row.addNewTableCell(); // Ajouter une nouvelle cellule si nécessaire
        }
    }
    public   void getListeBoursierClasse(XWPFDocument document ,
                                           Long idEcole ,String libelleAnnee , String libelleTrimestre) {


//Get classe

        // Créer une nouvelle ligne dans le tableau

        // Rechercher l'endroit où insérer le tableau
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        int indexToInsert = -1;

        for (int i = 0; i < paragraphs.size(); i++) {
            String text = paragraphs.get(i).getText();
            // Identifier l'emplacement où insérer le tableau (par exemple après "Liste des élèves affectés par classe")
            if (text.toLowerCase().contains("LISTE DES BOURSIERS ET DEMI BOURSIERS".toLowerCase())) {
                indexToInsert = i + 1; // Ajouter après ce paragraphe

                break;
            }
        }

        List<NiveauDto> niveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.niveau) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee " +
                "group by b.niveau ", NiveauDto.class);
        niveauDtoList = q.setParameter("idEcole", idEcole)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .getResultList() ;

        List<BoursierDto> boursierDtoList = new ArrayList<>() ;
        boursierDtoList = boursiersServices.boursier(idEcole ,libelleAnnee,libelleTrimestre) ;

        if (indexToInsert != -1) {
            XWPFTable table = document.insertNewTbl(paragraphs.get(indexToInsert).getCTP().newCursor());

            // Créer la première ligne d'en-tête avec les titres des colonnes
            XWPFTableRow headerRow = table.getRow(0); // Utilise la première ligne déjà existante sans ajouter de nouvelle cellule
            if (headerRow == null) {
                headerRow = table.createRow();
            }

            // Ajouter explicitement chaque cellule d'en-tête
            headerRow.getCell(0).setText("N°");
            headerRow.addNewTableCell().setText("Matricule");
            headerRow.addNewTableCell().setText("Nom et Prénoms");
            headerRow.addNewTableCell().setText("Sexe");
            headerRow.addNewTableCell().setText("Date de naissance");
            headerRow.addNewTableCell().setText("Lieu de naissance");

            String currentNiveau = "";
            int studentIndex = 1;

            for (BoursierDto eleve : boursierDtoList) {
                // Si le niveau change, ajouter une ligne pour le libellé du niveau
                if (!eleve.getNiveau().equals(currentNiveau)) {
                    currentNiveau = eleve.getNiveau();

                    // Ajouter une ligne pour le niveau
                    XWPFTableRow niveauRow = table.createRow();

                    // Fusionner manuellement les cellules pour le niveau
                    XWPFTableCell cell = niveauRow.getCell(0);
                    if (cell == null) {
                        cell = niveauRow.createCell();
                    }
                    cell.setText(currentNiveau.toUpperCase());

                    // Ajouter les autres cellules sans texte pour donner l'apparence de fusion

                }

                // Ajouter une ligne pour chaque élève
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(String.valueOf(studentIndex++));
                row.getCell(1).setText(eleve.getMatricule());
                row.getCell(2).setText(eleve.getNom() + " " + eleve.getPrenoms());
                row.getCell(3).setText(eleve.getSexe());
                row.getCell(4).setText(eleve.getDateNaiss());
                row.getCell(5).setText(eleve.getLieuNaiss());
            }
        }

    }

    private void mergeVerticalCells(XWPFTable table, int startRow, int endRow, int col) {
        for (int i = startRow; i <= endRow; i++) {
            XWPFTableCell cell = table.getRow(i).getCell(col);
            CTTcPr tcPr = cell.getCTTc().getTcPr();
            if (tcPr == null) {
                tcPr = cell.getCTTc().addNewTcPr();
            }
            CTVMerge vMerge = tcPr.isSetVMerge() ? tcPr.getVMerge() : tcPr.addNewVMerge();
            if (i == startRow) {
                vMerge.setVal(STMerge.RESTART);  // Démarre la fusion
            } else {
                vMerge.setVal(STMerge.CONTINUE);  // Continue la fusion
            }
        }
    }


}

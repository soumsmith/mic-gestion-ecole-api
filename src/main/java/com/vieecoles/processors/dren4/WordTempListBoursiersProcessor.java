package com.vieecoles.processors.dren4;

import com.vieecoles.dto.BoursierDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.services.etats.BoursiersServices;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class WordTempListBoursiersProcessor {
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

    private void mergeCellsHorizontally(XWPFTable table, int row, int fromCell, int toCell) {
        XWPFTableRow tableRow = table.getRow(row);
        if (tableRow != null) {
            ensureCellCount(tableRow, toCell + 1); // S'assurer que toutes les cellules nécessaires sont présentes
            for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
                XWPFTableCell cell = tableRow.getCell(cellIndex);
                if (cell != null) {
                    CTTcPr tcPr = cell.getCTTc().getTcPr();
                    if (tcPr == null) {
                        tcPr = cell.getCTTc().addNewTcPr();
                    }
                    if (cellIndex == fromCell) {
                        if (tcPr.getHMerge() == null) {
                            tcPr.addNewHMerge().setVal(STMerge.RESTART);
                        } else {
                            tcPr.getHMerge().setVal(STMerge.RESTART);
                        }
                    } else {
                        if (tcPr.getHMerge() == null) {
                            tcPr.addNewHMerge().setVal(STMerge.CONTINUE);
                        } else {
                            tcPr.getHMerge().setVal(STMerge.CONTINUE);
                        }
                    }
                }
            }
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
                    
                    // S'assurer que la ligne a 6 cellules (N°, Matricule, Nom et Prénoms, Sexe, Date de naissance, Lieu de naissance)
                    ensureCellCount(niveauRow, 6);
                    
                    // Mettre le texte du niveau dans la première cellule
                    XWPFTableCell cell = niveauRow.getCell(0);
                    cell.setText("NIVEAU " + currentNiveau.toUpperCase());
                    
                    // Fusionner toutes les cellules (de 0 à 5) pour créer une seule cellule qui s'étend sur toute la largeur
                    int rowIndex = table.getRows().indexOf(niveauRow);
                    mergeCellsHorizontally(table, rowIndex, 0, 5);
                }

                // Ajouter une ligne pour chaque élève
                XWPFTableRow row = table.createRow();
                ensureCellCount(row, 6); // S'assurer que la ligne a 6 cellules
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

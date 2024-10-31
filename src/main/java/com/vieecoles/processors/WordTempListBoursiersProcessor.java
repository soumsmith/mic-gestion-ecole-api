package com.vieecoles.processors;

import com.vieecoles.dto.BoursierDto;
import com.vieecoles.dto.MajorParClasseNiveauDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.services.etats.BoursiersServices;
import com.vieecoles.services.etats.appachePoi.EleveAffecteParClassePoiServices;
import com.vieecoles.services.etats.appachePoi.MajorParClasseNiveauPoiServices;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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

            // Créer l'en-tête du tableau (1 ligne, 11 colonnes)
            XWPFTableRow headerRow = table.getRow(0);
            headerRow.getCell(0).setText("N°");
            headerRow.addNewTableCell().setText("Matricule");
            headerRow.addNewTableCell().setText("Nom et Prénoms");
            headerRow.addNewTableCell().setText("Sexe");
            headerRow.addNewTableCell().setText("Date de naissance");
            headerRow.addNewTableCell().setText("Lieu de naissance");
            for (NiveauDto niveauDto : niveauDtoList) {
                XWPFTableRow niveau = table.createRow();
            for (BoursierDto eleve : boursierDtoList) {

                niveau.getCell(0).setText(eleve.getNiveau());
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText("");
                row.getCell(1).setText(eleve.getMatricule());
                row.getCell(2).setText(eleve.getNom()+" "+eleve.getPrenoms());
                row.getCell(3).setText(eleve.getSexe());
                row.getCell(4).setText(eleve.getDateNaiss());
                row.getCell(5).setText(eleve.getLieuNaiss());

            }
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

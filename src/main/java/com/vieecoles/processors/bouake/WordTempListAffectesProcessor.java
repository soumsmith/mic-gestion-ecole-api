package com.vieecoles.processors.bouake;

import static com.vieecoles.processors.dren3.WordTempListAffectesProcessor.setHeaderCell;

import com.vieecoles.dto.NiveauOrderDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;
import com.vieecoles.dto.eleveAffecteParClasseDtoAvecTousTrimestres;
import com.vieecoles.services.etats.appachePoi.EleveAffecteParClassePoiServices;
import org.apache.poi.xwpf.usermodel.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class WordTempListAffectesProcessor {
    @Inject
    EntityManager em;
    @Inject
    EleveAffecteParClassePoiServices eleveAffecteParClassePoiServices ;
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
            if (text.contains("Liste des élèves affectés et résultats par niveau")) {
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
            run.setText(classeList.get(k).getNiveau());
            run.setBold(true);  // Mettre le texte en gras
            newParagraph.setAlignment(ParagraphAlignment.LEFT);

            // Insérer le tableau juste après le paragraphe
            XWPFTable table = document.insertNewTbl(paragraphs.get(indexToInsert+1).getCTP().newCursor());

            // Créer l'en-tête du tableau (1 ligne, 11 colonnes)
            XWPFTableRow headerRow = table.getRow(0);
            headerRow.getCell(0).setText("N°");
            setHeaderCell(headerRow.addNewTableCell(), "Matricule", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "Noms et Prénoms", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "Sexe", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "Date de naissance", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "Nationalité", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "Qualité(Red ou NRed)", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "Statut(Aff/NAff)", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "N°déc d'Aff", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "Moy Trim", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "Rang", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "Observations", "D9D9D9");

            // Ajouter des lignes au tableau
            int numerotation=1;
            for (eleveAffecteParClasseDtoAvecTousTrimestres eleve : elevAffectes) {  // Exemple de 3 lignes
                XWPFTableRow row = table.createRow();
                if (row == null) {
                    row = table.insertNewTableRow(table.getNumberOfRows());
                }
                ensureCellCount(row, 12);
                row.getCell(0).setText(numerotation+"");
                row.getCell(1).setText(eleve.getMatricule());
                row.getCell(2).setText(eleve.getNomEleve()+" "+eleve.getPrenomEleve());
                row.getCell(3).setText(eleve.getSexe());
                row.getCell(4).setText(eleve.getAnneeNaissance());
                row.getCell(5).setText(eleve.getNationnalite());
                row.getCell(6).setText(eleve.getRedoublan());
                row.getCell(7).setText(eleve.getAffecte());
                row.getCell(8).setText(eleve.getNumDecisionAffecte());
                row.getCell(9).setText(String.valueOf(eleve.getMoyeGeneral()));
                row.getCell(10).setText(String.valueOf(eleve.getRang()));
                row.getCell(11).setText(eleve.getObservat());
                numerotation++;
            }
        //}
        }
    }
    }


}

package com.vieecoles.processors.dren4;

import static com.vieecoles.processors.dren3.WordTempListAffectesProcessor.setHeaderCell;

import com.vieecoles.dto.NiveauOrderDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;
import com.vieecoles.dto.eleveAffecteParClasseDtoAvecTousTrimestres;
import com.vieecoles.services.etats.appachePoi.EleveAffecteParClassePoiServices;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

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
            if (text.contains("Liste des élèves affectés par classe")) {
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
            run.setText(classeList.get(k).getNiveau()+" \t\tProfesseur Principal:\t\tEducateur:");
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
            setHeaderCell(headerRow.addNewTableCell(), "RANG", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "CLASSE", "D9D9D9");
            setHeaderCell(headerRow.addNewTableCell(), "OBSERVATIONS", "D9D9D9");

            // Ajouter des lignes au tableau
            for (eleveAffecteParClasseDtoAvecTousTrimestres eleve : elevAffectes) {  // Exemple de 3 lignes
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(eleve.getMatricule());
                row.getCell(1).setText(eleve.getNomEleve()+" "+eleve.getPrenomEleve());
                row.getCell(2).setText(eleve.getSexe());
                row.getCell(3).setText("");
                row.getCell(4).setText(eleve.getNationnalite());
                row.getCell(5).setText(eleve.getRedoublan());
                row.getCell(6).setText(eleve.getAffecte());
                row.getCell(7).setText(eleve.getNumDecisionAffecte());
                row.getCell(8).setText(String.valueOf(eleve.getMoyeGeneral()));
                row.getCell(9).setText(String.valueOf(eleve.getRang()));
                row.getCell(10).setText(eleve.getObservat());
            }
        //}
        }
    }
    }


}

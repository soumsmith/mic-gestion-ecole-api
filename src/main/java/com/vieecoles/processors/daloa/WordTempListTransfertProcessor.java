package com.vieecoles.processors.daloa;

import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.TransfertsDto;
import com.vieecoles.services.etats.appachePoi.TransfertsPoiServices;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

@ApplicationScoped
public class WordTempListTransfertProcessor {
    @Inject
    EntityManager em;
    @Inject
    TransfertsPoiServices transfertsServices ;
    int LongTableau;

    private static void ensureCellCount(XWPFTableRow row, int cellCount) {
        int currentCellCount = row.getTableCells().size();
        for (int i = currentCellCount; i < cellCount; i++) {
            row.addNewTableCell(); // Ajouter une nouvelle cellule si nécessaire
        }
    }
    public   void getListTransfert(XWPFDocument document ,
                                           Long idEcole ) {


//Get classe


        List<NiveauDto> classeList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.niveau) from Transferts b  where b.idEcole =:idEcole  " +
                "group by b.niveau ", NiveauDto.class);
        classeList = q.setParameter("idEcole", idEcole)
                .getResultList() ;

        // Rechercher l'endroit où insérer le tableau
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        int indexToInsert = -1;

        for (int i = 0; i < paragraphs.size(); i++) {
            String text = paragraphs.get(i).getText();
            // Identifier l'emplacement où insérer le tableau (par exemple après "Liste des élèves affectés par classe")
            if (text.toLowerCase().contains("liste des transferts".toLowerCase())) {
                indexToInsert = i + 1; // Ajouter après ce paragraphe

                break;
            }
        }

        for (int k = classeList.size() - 1; k >= 0; k--) {
            List<TransfertsDto>  elevTransferes = new ArrayList<>() ;
            elevTransferes= transfertsServices.transferts(idEcole,classeList.get(k).getNiveau());
            System.out.println("Liste des transfert: "+elevTransferes.toString());

        if (indexToInsert != -1) {
           // for (int z=0; z< classeList.size();z++) {

            // Créer un nouveau paragraphe avant d'insérer le tableau
            XWPFParagraph newParagraph = document.insertNewParagraph(paragraphs.get(indexToInsert).getCTP().newCursor());
            XWPFRun run = newParagraph.createRun();
            run.setText(classeList.get(k).getNiveau());
            run.setBold(true);  // Mettre le texte en gras
            newParagraph.setAlignment(ParagraphAlignment.LEFT);

            // Insérer le tableau juste après le paragraphe
            XWPFTable table = document.insertNewTbl(paragraphs.get(indexToInsert+1).getCTP().newCursor());

            // Créer l'en-tête du tableau (1 ligne, 11 colonnes)
            XWPFTableRow headerRow = table.getRow(0);
            headerRow.getCell(0).setText("N°");
            headerRow.addNewTableCell().setText("NOM ET PRENOMS");
            headerRow.addNewTableCell().setText("MATRICULE");
            headerRow.addNewTableCell().setText("CLASSE");
            headerRow.addNewTableCell().setText("Nat");
            headerRow.addNewTableCell().setText("R");
            headerRow.addNewTableCell().setText("DATE NAISS");
            headerRow.addNewTableCell().setText("N");
            headerRow.addNewTableCell().setText("DECISION");
            headerRow.addNewTableCell().setText("ETABLISSEMENT D'ORIGINE");

            // Ajouter des lignes au tableau
            for (TransfertsDto eleve : elevTransferes) {  // Exemple de 3 lignes
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(String.valueOf(k+1));
                row.getCell(1).setText(eleve.getNom()+" "+eleve.getPrenoms());
                row.getCell(2).setText(eleve.getMatricule());
                row.getCell(3).setText(eleve.getClasse());
                row.getCell(4).setText(eleve.getRedoublant());
                row.getCell(5).setText(eleve.getDateNaissance());
                row.getCell(6).setText("");
                row.getCell(7).setText(eleve.getDecision());
                row.getCell(8).setText(eleve.getEtablissementOrigine());
            }
        //}
        }
    }
    }


}

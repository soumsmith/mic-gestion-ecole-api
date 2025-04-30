package com.vieecoles.processors.bouake;

import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.TransfertsDto;
import com.vieecoles.services.etats.appachePoi.TransfertsPoiServices;
import org.apache.poi.xwpf.usermodel.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

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
        String tableTitle = "CODE_LISTE_TRANSFERT";
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

        for (int k = classeList.size() - 1; k >= 0; k--) {
            List<TransfertsDto>  elevTransferes = new ArrayList<>() ;
            elevTransferes= transfertsServices.transferts(idEcole,classeList.get(k).getNiveau());

            // Ajouter des lignes au tableau
            for (TransfertsDto eleve : elevTransferes) {  // Exemple de 3 lignes
                XWPFTableRow row = targetTable.createRow();
                row.getCell(0).setText(String.valueOf(k+1));
                row.getCell(1).setText(eleve.getNom()+" "+eleve.getPrenoms());
                row.getCell(2).setText(eleve.getEtablissementOrigine());
                row.getCell(3).setText(eleve.getClasse());
                row.getCell(4).setText(eleve.getDecision());

            }

    }
    }


}

package com.vieecoles.processors.dren3;

import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.TransfertsDto;
import com.vieecoles.processors.dren3.services.TransfertsDren3Services;
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
    TransfertsDren3Services transfertsServices ;
    int LongTableau;

    private static void ensureCellCount(XWPFTableRow row, int cellCount) {
        int currentCellCount = row.getTableCells().size();
        for (int i = currentCellCount; i < cellCount; i++) {
            row.addNewTableCell(); // Ajouter une nouvelle cellule si nécessaire
        }
    }
    public   void getListTransfert(XWPFDocument document ,
                                           Long idEcole ) {

        String tableTitle = "CODE_LISTE_TRANSFERT";
        XWPFTable targetTable = null;
        for (int i = 0; i < document.getBodyElements().size(); i++) {
            IBodyElement element = document.getBodyElements().get(i);
            if (element.getElementType() == BodyElementType.PARAGRAPH) {
                XWPFParagraph paragraph = (XWPFParagraph) element;
                if (paragraph.getText().trim().equalsIgnoreCase(tableTitle)) {
                    paragraph.removeRun(0); // Supprimer le texte du titre

                    // Utiliser un curseur pour trouver le tableau
                    for (int j = i + 1; j < document.getBodyElements().size(); j++) {
                        IBodyElement nextElement = document.getBodyElements().get(j);
                        if (nextElement.getElementType() == BodyElementType.TABLE) {
                            targetTable = (XWPFTable) nextElement;
                            break;
                        }
                    }
                    break;
                }
            }
        }


//Get classe


            List<TransfertsDto>  elevTransferes = new ArrayList<>() ;
            elevTransferes= transfertsServices.transferts(idEcole);
            System.out.println("Liste des transfert: "+elevTransferes.toString());

        int numerotation = 1;
            for (TransfertsDto eleve : elevTransferes) {  // Exemple de 3 lignes
                XWPFTableRow nbreClasseRow = targetTable.createRow();
                ensureCellCount(nbreClasseRow, 5);
                nbreClasseRow.getCell(0).setText(String.valueOf(numerotation));
                nbreClasseRow.getCell(1).setText(eleve.getMatricule());
                nbreClasseRow.getCell(2).setText(eleve.getNom()+" "+eleve.getPrenoms());
                nbreClasseRow.getCell(3).setText(eleve.getEtablissementOrigine());
                nbreClasseRow.getCell(4).setText((eleve.getClasse()));
                numerotation++;
            }

    }


}

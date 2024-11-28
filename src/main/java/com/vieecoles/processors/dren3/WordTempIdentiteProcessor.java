package com.vieecoles.processors.dren3;

import com.vieecoles.dto.IdentiteEtatDto;
import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;
import com.vieecoles.services.etats.IdentiteEtatService;
import com.vieecoles.services.etats.appachePoi.resultatsPoiServices;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

@ApplicationScoped
public class WordTempIdentiteProcessor {
    @Inject
    resultatsPoiServices resultatsServices ;
    @Inject
    IdentiteEtatService identiteEtatService ;
    public   void getIdentiteProcessor(XWPFDocument document ,
                                           Long idEcole ,String libelleAnnee , String libelleTrimestre) {
        List<IdentiteEtatDto>  identiteEtatDto = new ArrayList<>() ;
        identiteEtatDto= identiteEtatService.getIdentiteDto(idEcole) ;
        // Map pour les paragraphes
        // Parcourir tous les paragraphes du document
        String textToReplace ="NOM_ECOLE";
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            // Rechercher le texte cible
            if (paragraph.getText().contains(textToReplace)) {
                // Modifier le texte tout en conservant la mise en forme
                replaceTextInParagraph(paragraph, textToReplace, identiteEtatDto.get(0).getDenominEtabli());
            }
        }
    }


    private static void replaceTextInParagraph(XWPFParagraph paragraph, String textToReplace, String replacementText) {
        // Reconstruire le texte complet du paragraphe à partir des runs
        StringBuilder paragraphText = new StringBuilder();
        for (XWPFRun run : paragraph.getRuns()) {
            String runText = run.getText(0);
            if (runText != null) {
                paragraphText.append(runText);
            }
        }

        // Vérifier si le texte à remplacer est présent
        String fullText = paragraphText.toString();
        if (fullText.contains(textToReplace)) {
            // Remplacer le texte cible
            String updatedText = fullText.replace(textToReplace, replacementText);

            // Supprimer tous les runs existants
            for (int i = paragraph.getRuns().size() - 1; i >= 0; i--) {
                paragraph.removeRun(i);
            }

            // Ajouter le texte mis à jour en un seul run
            XWPFRun newRun = paragraph.createRun();
            newRun.setText(updatedText);
        }
    }

}

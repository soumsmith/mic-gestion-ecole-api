package com.vieecoles.processors.daloa;

import com.vieecoles.dto.NiveauOrderDto;
import com.vieecoles.dto.eleveAffecteParClasseDtoAvecTousTrimestres;
import com.vieecoles.dto.eleveNonAffecteParClasseDto;
import com.vieecoles.services.etats.appachePoi.EleveNonAffecteParClassePoiServices;
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
public class WordTempListNonAffectesProcessor {
    @Inject
    EntityManager em;
    @Inject
    EleveNonAffecteParClassePoiServices eleveAffecteParClassePoiServices ;

    public   void getEleveNosAffecteParClasse(XWPFDocument document ,
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
            if (text.contains("Liste des élèves non affectés par classe")) {
                indexToInsert = i + 1; // Ajouter après ce paragraphe

                break;
            }
        }

        for (int k = classeList.size() - 1; k >= 0; k--) {
            List<eleveAffecteParClasseDtoAvecTousTrimestres>  elevAffectes = new ArrayList<>() ;
            elevAffectes= eleveAffecteParClassePoiServices.eleveNonAffecteParClasse(idEcole,libelleAnnee,libelleTrimestre,classeList.get(k).getNiveau());

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
            headerRow.getCell(0).setText("Matricule");
            headerRow.addNewTableCell().setText("Nom et prénoms");
            headerRow.addNewTableCell().setText("Sexe");
            headerRow.addNewTableCell().setText("AN");
            headerRow.addNewTableCell().setText("Nat");
            headerRow.addNewTableCell().setText("R");
            headerRow.addNewTableCell().setText("Statut");
            headerRow.addNewTableCell().setText("N°DEC AFF");
            headerRow.addNewTableCell().setText("MOY T1");
            headerRow.addNewTableCell().setText("MOY T2");
            headerRow.addNewTableCell().setText("MOY T3");
            headerRow.addNewTableCell().setText("RANG");
            headerRow.addNewTableCell().setText("OBSERVATION");

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
                row.getCell(8).setText(String.valueOf(eleve.getMoyeGeneralTrim1()));
                row.getCell(9).setText(String.valueOf(eleve.getMoyeGeneralTrim2()));
                row.getCell(10).setText(String.valueOf(eleve.getMoyeGeneralTrim3()));
                row.getCell(11).setText(String.valueOf(eleve.getRang()));
                row.getCell(12).setText(eleve.getObservat());
            }
        //}
        }
    }
    }


}

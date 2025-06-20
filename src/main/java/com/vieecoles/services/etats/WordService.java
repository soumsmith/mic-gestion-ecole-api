package com.vieecoles.services.etats;
import com.vieecoles.dto.eleveAffecteParClasseDto;
import org.apache.poi.xwpf.usermodel.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class WordService {
    @Inject
    EleveAffecteParClasseServices resultatsServices ;
    public byte[] generateWordFile(Long idEcole,String libelleAnnee ,String  libelleTrimetre) throws IOException {
        // Charger le modèle de fichier Word existant
        FileInputStream fis = new FileInputStream("src/main/resources/etats/apochePoi/listeClasse.docx");
        XWPFDocument document = new XWPFDocument(fis);

        // Récupérer les données depuis la vue SQL
        List<eleveAffecteParClasseDto> detailsBull = new ArrayList<>() ;
        System.out.println("classeNiveauDtoList entree");
        detailsBull= resultatsServices.eleveAffecteParClasse(idEcole ,libelleAnnee,libelleTrimetre)  ;

    System.out.println("Longuer :"+detailsBull.size());

        // Supposons que le tableau que vous souhaitez remplir est le premier tableau du document
        XWPFTable table = document.getTableArray(0); // Récupérer le premier tableau

        // Parcourir la liste et ajouter une nouvelle ligne pour chaque élève
        for (eleveAffecteParClasseDto eleve : detailsBull) {
            // Créer une nouvelle ligne dans le tableau
            XWPFTableRow row = table.createRow();
            // Assurez-vous que la ligne a suffisamment de cellules
            ensureCellCount(row, 5); // Par exemple, 5 cellules pour Matricule, Nom, Prénom, Sexe, Classe

            // Remplir les cellules de la nouvelle ligne
            row.getCell(0).setText(eleve.getMatricule());
            row.getCell(1).setText(eleve.getNomEleve());
            row.getCell(2).setText(eleve.getPrenomEleve());
            row.getCell(3).setText(eleve.getSexe());
            row.getCell(4).setText(eleve.getClasseLibelle());
        }

        // Sauvegarder le document modifié
        try (FileOutputStream fos = new FileOutputStream("src/main/resources/output.docx")) {
            document.write(fos);
        }



        // Sauvegarder le document dans un tableau de bytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.write(outputStream);
        document.close();
        fis.close();

        return outputStream.toByteArray();
    }

    // Méthode pour s'assurer que chaque ligne a suffisamment de cellules
    private static void ensureCellCount(XWPFTableRow row, int cellCount) {
        int currentCellCount = row.getTableCells().size();
        for (int i = currentCellCount; i < cellCount; i++) {
            row.addNewTableCell(); // Ajouter une nouvelle cellule si nécessaire
        }
    }



}

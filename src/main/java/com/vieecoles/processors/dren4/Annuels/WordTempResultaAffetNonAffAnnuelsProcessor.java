package com.vieecoles.processors.dren4.Annuels;

import com.vieecoles.dto.ResultatsElevesAffecteDto;
import com.vieecoles.processors.dren4.Services.resultatsAnnuelsPoiServices;
import com.vieecoles.processors.dren4.Services.resultatsPoiServices;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

@ApplicationScoped
public class WordTempResultaAffetNonAffAnnuelsProcessor {
    @Inject
    resultatsAnnuelsPoiServices resultatsServices;

    public void getResultatAffProcessor(XWPFDocument document,
                                        Long idEcole, String libelleAnnee, String libelleTrimetre) {
        List<ResultatsElevesAffecteDto> detailsBull6 = new ArrayList<>();
        System.out.println("classeNiveauDtoList entree");
        try {
            detailsBull6 = resultatsServices.CalculResultatsEleveAffecte(idEcole, libelleAnnee, libelleTrimetre);

            System.out.println("classeNiveauDtoList Sortie");
        } catch (Exception e) {
            e.printStackTrace();
        }

        XWPFTable table = obtenirUnTableau(document, "CODE_STAT_RESULTATS_ANNUELS");
        try {
            // SUPPRIMER le tri pour respecter l'ordre par défaut
            // detailsBull6.sort(Comparator.comparing(ResultatsElevesAffecteDto::getOrdre_niveau));
            ajoutTableauDynamiqueAmeliore(detailsBull6, table);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public XWPFTable obtenirUnTableau(XWPFDocument document, String tableTitle) {
        XWPFTable targetTable = null;
        for (int i = 0; i < document.getBodyElements().size(); i++) {
            IBodyElement element = document.getBodyElements().get(i);
            if (element.getElementType() == BodyElementType.PARAGRAPH) {
                XWPFParagraph paragraph = (XWPFParagraph) element;
                if (paragraph.getText().trim().equalsIgnoreCase(tableTitle)) {
                    paragraph.removeRun(0);

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
        return targetTable;
    }

    private static void ajoutTableauDynamiqueAmeliore(List<ResultatsElevesAffecteDto> detailsBull, XWPFTable table) {
        int rowIndex = 1; // Commencer après l'en-tête

        // Variables pour calculer les totaux par niveau et cycles
        Map<String, TotauxNiveau> totauxParNiveau = new HashMap<>();
        TotauxNiveau totauxGeneraux = new TotauxNiveau();
        TotauxNiveau totaux1erCycle = new TotauxNiveau();
        TotauxNiveau totaux2ndCycle = new TotauxNiveau();
        TotauxNiveau totaux2nde = new TotauxNiveau();
        TotauxNiveau totaux1ere = new TotauxNiveau();
        TotauxNiveau totauxTerminale = new TotauxNiveau();

        String niveauPrecedent = null;
        boolean total2ndCycleAjoute = false; // Flag pour éviter la duplication

        // Traiter les données dans l'ordre par défaut
        for (int i = 0; i < detailsBull.size(); i++) {
            ResultatsElevesAffecteDto classe = detailsBull.get(i);
            String niveauActuel = obtenirNiveau(classe);
            int ordreNiveau = classe.getOrdre_niveau();

            // Calculer les valeurs pour cette classe
            long nombMoySup10 = (classe.getNbreMoySup10F() != null ? classe.getNbreMoySup10F() : 0L) +
                (classe.getNbreMoySup10G() != null ? classe.getNbreMoySup10G() : 0L);

            long nombMoyInf10 = (classe.getNbreMoyInf999F() != null ? classe.getNbreMoyInf999F() : 0L) +
                (classe.getNbreMoyInf999G() != null ? classe.getNbreMoyInf999G() : 0L);

            long nombMoyInf8_5 = (classe.getNbreMoyInf85F() != null ? classe.getNbreMoyInf85F() : 0L) +
                (classe.getNbreMoyInf85G() != null ? classe.getNbreMoyInf85G() : 0L);

            long effectifClasse = (classe.getClassF() != null ? classe.getClassF() : 0L) +
                (classe.getClassG() != null ? classe.getClassG() : 0L);

            long effectif = (classe.getEffeF() != null ? classe.getEffeF() : 0L) +
                (classe.getEffeG() != null ? classe.getEffeG() : 0L);

            long effNonClass = (classe.getNonclassF() != null ? classe.getNonclassF() : 0L) +
                (classe.getNonclassG() != null ? classe.getNonclassG() : 0L);

            // Calculer les pourcentages
            double pourSup10 = effectifClasse > 0 ? (nombMoySup10 * 100.0) / effectifClasse : 0.0;
            double pourInf10 = effectifClasse > 0 ? (nombMoyInf10 * 100.0) / effectifClasse : 0.0;
            double pourInf8_5 = effectifClasse > 0 ? (nombMoyInf8_5 * 100.0) / effectifClasse : 0.0;

            // Vérifier si on change de niveau et afficher le total du niveau précédent
            if (niveauPrecedent != null && !niveauActuel.equals(niveauPrecedent)) {
                ajouterLigneTotalNiveau(table, niveauPrecedent, totauxParNiveau.get(niveauPrecedent));
                rowIndex++;

                // Ajouter les totaux spéciaux (2nde, 1ère, Terminale)
                if (niveauPrecedent.equals("2nde C")) {
                    ajouterLigneTotalNiveau(table, "Total 2nde", totaux2nde);
                    rowIndex++;
                } else if (niveauPrecedent.equals("1ère D")) {
                    ajouterLigneTotalNiveau(table, "Total 1ère", totaux1ere);
                    rowIndex++;
                } else if (niveauPrecedent.equals("Tle D")) {
                    ajouterLigneTotalNiveau(table, "Total Terminale", totauxTerminale);
                    rowIndex++;
                }

                // Ajouter total de cycle si nécessaire (SEULEMENT UNE FOIS)
                if (niveauPrecedent.equals("3ème")) {
                    ajouterLigneTotalCycle(table, "Total 1er Cycle", totaux1erCycle);
                    rowIndex++;
                }
                // SUPPRIMER la condition isLastOf2ndCycle ici car elle cause la duplication
            }

            // Créer une nouvelle ligne pour cette classe
            XWPFTableRow row = table.createRow();
            ensureCellCount(row, 11);

            // Remplir les données de la classe - UTILISER classe.getClasse() au lieu de nomClasse générique
            row.getCell(0).setText(classe.getClasse());
            row.getCell(1).setText(String.valueOf(effectif));
            row.getCell(2).setText(String.valueOf(effectifClasse));
            row.getCell(3).setText(String.valueOf(effNonClass));
            row.getCell(4).setText(String.valueOf(nombMoySup10));
            row.getCell(5).setText(String.valueOf(arrondie(pourSup10)));
            row.getCell(6).setText(String.valueOf(nombMoyInf10));
            row.getCell(7).setText(String.valueOf(arrondie(pourInf10)));
            row.getCell(8).setText(String.valueOf(nombMoyInf8_5));
            row.getCell(9).setText(String.valueOf(arrondie(pourInf8_5)));
            row.getCell(10).setText(String.valueOf(arrondie(classe.getMoyClasse())));

            // Ajouter les bordures
            ajouterBordures(row);

            // Accumuler les totaux pour ce niveau
            TotauxNiveau totaux = totauxParNiveau.computeIfAbsent(niveauActuel, k -> new TotauxNiveau());
            accummulerTotaux(totaux, effectif, effectifClasse, effNonClass, nombMoySup10, nombMoyInf10, nombMoyInf8_5, classe.getMoyClasse());

            // Accumuler pour les totaux généraux
            accummulerTotaux(totauxGeneraux, effectif, effectifClasse, effNonClass, nombMoySup10, nombMoyInf10, nombMoyInf8_5, classe.getMoyClasse());

            // Accumuler pour les cycles
            if (ordreNiveau <= 4) { // 1er cycle (6ème à 3ème)
                accummulerTotaux(totaux1erCycle, effectif, effectifClasse, effNonClass, nombMoySup10, nombMoyInf10, nombMoyInf8_5, classe.getMoyClasse());
            } else { // 2nd cycle
                accummulerTotaux(totaux2ndCycle, effectif, effectifClasse, effNonClass, nombMoySup10, nombMoyInf10, nombMoyInf8_5, classe.getMoyClasse());

                // Accumuler pour les sous-totaux du 2nd cycle
                if (ordreNiveau == 5 || ordreNiveau == 6) { // 2nde A et C
                    accummulerTotaux(totaux2nde, effectif, effectifClasse, effNonClass, nombMoySup10, nombMoyInf10, nombMoyInf8_5, classe.getMoyClasse());
                } else if (ordreNiveau >= 7 && ordreNiveau <= 9) { // 1ère
                    accummulerTotaux(totaux1ere, effectif, effectifClasse, effNonClass, nombMoySup10, nombMoyInf10, nombMoyInf8_5, classe.getMoyClasse());
                } else if (ordreNiveau >= 10) { // Terminales
                    accummulerTotaux(totauxTerminale, effectif, effectifClasse, effNonClass, nombMoySup10, nombMoyInf10, nombMoyInf8_5, classe.getMoyClasse());
                }
            }

            niveauPrecedent = niveauActuel;
            rowIndex++;
        }

        // Ajouter le total du dernier niveau
        if (niveauPrecedent != null && totauxParNiveau.containsKey(niveauPrecedent)) {
            ajouterLigneTotalNiveau(table, niveauPrecedent, totauxParNiveau.get(niveauPrecedent));

            // Ajouter les totaux de fin selon le dernier niveau
            if (niveauPrecedent.equals("2nde C")) {
                ajouterLigneTotalNiveau(table, "Total 2nde", totaux2nde);
            } else if (niveauPrecedent.equals("1ère D")) {
                ajouterLigneTotalNiveau(table, "Total 1ère", totaux1ere);
            } else if (niveauPrecedent.contains("Tle")) {
                ajouterLigneTotalNiveau(table, "Total Terminale", totauxTerminale);
            }

            // Ajouter le Total 2nd Cycle SEULEMENT à la fin (UNE SEULE FOIS)
            if (niveauPrecedent.equals("3ème")) {
                ajouterLigneTotalCycle(table, "Total 1er Cycle", totaux1erCycle);
            } else if (!total2ndCycleAjoute) {
                // Ajouter le Total 2nd Cycle seulement si on est dans le 2nd cycle et qu'il n'a pas encore été ajouté
                ajouterLigneTotalCycle(table, "Total 2nd Cycle", totaux2ndCycle);
                total2ndCycleAjoute = true;
            }
        }

        // Ajouter le TOTAL GÉNÉRAL de l'établissement
        ajouterLigneTotalGeneral(table, "TOTAL GÉNÉRAL", totauxGeneraux);
    }

    // Classe helper pour stocker les totaux par niveau
    private static class TotauxNiveau {
        long totalEffectif = 0;
        long totalEffectifClasse = 0;
        long totalEffNonClass = 0;
        long totalNombMoySup10 = 0;
        long totalNombMoyInf10 = 0;
        long totalNombMoyInf8_5 = 0;
        double sommeMoyClasse = 0;
        int nombreClasses = 0;
    }

    private static void accummulerTotaux(TotauxNiveau totaux, long effectif, long effectifClasse, long effNonClass,
                                         long nombMoySup10, long nombMoyInf10, long nombMoyInf8_5, double moyClasse) {
        totaux.totalEffectif += effectif;
        totaux.totalEffectifClasse += effectifClasse;
        totaux.totalEffNonClass += effNonClass;
        totaux.totalNombMoySup10 += nombMoySup10;
        totaux.totalNombMoyInf10 += nombMoyInf10;
        totaux.totalNombMoyInf8_5 += nombMoyInf8_5;
        totaux.sommeMoyClasse += moyClasse;
        totaux.nombreClasses++;
    }

    // Méthode pour vérifier si c'est le dernier niveau du 2nd cycle
    private static boolean isLastOf2ndCycle(int ordreNiveau) {
        // Retourne true si c'est le dernier niveau traité du 2nd cycle
        // Cette logique dépend de votre structure de données
        return ordreNiveau == 14; // Tle D est généralement le dernier
    }

    // Méthode pour ajouter une ligne de total de cycle
    private static void ajouterLigneTotalCycle(XWPFTable table, String libelleCycle, TotauxNiveau totaux) {
        XWPFTableRow totalRow = table.createRow();
        ensureCellCount(totalRow, 11);

        // Calculer les pourcentages totaux
        double totalPourSup10 = totaux.totalEffectifClasse > 0 ? (totaux.totalNombMoySup10 * 100.0) / totaux.totalEffectifClasse : 0.0;
        double totalPourInf10 = totaux.totalEffectifClasse > 0 ? (totaux.totalNombMoyInf10 * 100.0) / totaux.totalEffectifClasse : 0.0;
        double totalPourInf8_5 = totaux.totalEffectifClasse > 0 ? (totaux.totalNombMoyInf8_5 * 100.0) / totaux.totalEffectifClasse : 0.0;
        double moyenneCycle = totaux.nombreClasses > 0 ? totaux.sommeMoyClasse / totaux.nombreClasses : 0.0;

        // Remplir la ligne de total de cycle
        totalRow.getCell(0).setText(libelleCycle);
        totalRow.getCell(1).setText(String.valueOf(totaux.totalEffectif));
        totalRow.getCell(2).setText(String.valueOf(totaux.totalEffectifClasse));
        totalRow.getCell(3).setText(String.valueOf(totaux.totalEffNonClass));
        totalRow.getCell(4).setText(String.valueOf(totaux.totalNombMoySup10));
        totalRow.getCell(5).setText(String.valueOf(arrondie(totalPourSup10)));
        totalRow.getCell(6).setText(String.valueOf(totaux.totalNombMoyInf10));
        totalRow.getCell(7).setText(String.valueOf(arrondie(totalPourInf10)));
        totalRow.getCell(8).setText(String.valueOf(totaux.totalNombMoyInf8_5));
        totalRow.getCell(9).setText(String.valueOf(arrondie(totalPourInf8_5)));
        totalRow.getCell(10).setText(String.valueOf(arrondie(moyenneCycle)));

        // Style spécial pour les totaux de cycle (plus visible)
        mettreEnGrasEtColorerCycle(totalRow);
        ajouterBordures(totalRow);
    }

    // Méthode pour ajouter le total général de l'établissement
    private static void ajouterLigneTotalGeneral(XWPFTable table, String libelle, TotauxNiveau totaux) {
        XWPFTableRow totalRow = table.createRow();
        ensureCellCount(totalRow, 11);

        // Calculer les pourcentages totaux
        double totalPourSup10 = totaux.totalEffectifClasse > 0 ? (totaux.totalNombMoySup10 * 100.0) / totaux.totalEffectifClasse : 0.0;
        double totalPourInf10 = totaux.totalEffectifClasse > 0 ? (totaux.totalNombMoyInf10 * 100.0) / totaux.totalEffectifClasse : 0.0;
        double totalPourInf8_5 = totaux.totalEffectifClasse > 0 ? (totaux.totalNombMoyInf8_5 * 100.0) / totaux.totalEffectifClasse : 0.0;
        double moyenneGenerale = totaux.nombreClasses > 0 ? totaux.sommeMoyClasse / totaux.nombreClasses : 0.0;

        // Remplir la ligne de total général
        totalRow.getCell(0).setText(libelle);
        totalRow.getCell(1).setText(String.valueOf(totaux.totalEffectif));
        totalRow.getCell(2).setText(String.valueOf(totaux.totalEffectifClasse));
        totalRow.getCell(3).setText(String.valueOf(totaux.totalEffNonClass));
        totalRow.getCell(4).setText(String.valueOf(totaux.totalNombMoySup10));
        totalRow.getCell(5).setText(String.valueOf(arrondie(totalPourSup10)));
        totalRow.getCell(6).setText(String.valueOf(totaux.totalNombMoyInf10));
        totalRow.getCell(7).setText(String.valueOf(arrondie(totalPourInf10)));
        totalRow.getCell(8).setText(String.valueOf(totaux.totalNombMoyInf8_5));
        totalRow.getCell(9).setText(String.valueOf(arrondie(totalPourInf8_5)));
        totalRow.getCell(10).setText(String.valueOf(arrondie(moyenneGenerale)));

        // Style spécial pour le total général (le plus visible)
        mettreEnGrasEtColorerTotalGeneral(totalRow);
        ajouterBordures(totalRow);
    }

    // Style pour les totaux de cycle
    private static void mettreEnGrasEtColorerCycle(XWPFTableRow row) {
        for (XWPFTableCell cell : row.getTableCells()) {
            CTTcPr tcPr = cell.getCTTc().getTcPr();
            if (tcPr == null) {
                tcPr = cell.getCTTc().addNewTcPr();
            }

            CTShd shd = tcPr.getShd();
            if (shd == null) {
                shd = tcPr.addNewShd();
            }
            shd.setFill("CCCCCC"); // Couleur gris moyen pour les cycles

            // Mettre le texte en gras
            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    run.setBold(true);
                }
            }
        }
    }

    // Méthode pour ajouter une ligne de total pour un niveau
    private static void ajouterLigneTotalNiveau(XWPFTable table, String niveau, TotauxNiveau totaux) {
        XWPFTableRow totalRow = table.createRow();
        ensureCellCount(totalRow, 11);

        // Calculer les pourcentages totaux
        double totalPourSup10 = totaux.totalEffectifClasse > 0 ? (totaux.totalNombMoySup10 * 100.0) / totaux.totalEffectifClasse : 0.0;
        double totalPourInf10 = totaux.totalEffectifClasse > 0 ? (totaux.totalNombMoyInf10 * 100.0) / totaux.totalEffectifClasse : 0.0;
        double totalPourInf8_5 = totaux.totalEffectifClasse > 0 ? (totaux.totalNombMoyInf8_5 * 100.0) / totaux.totalEffectifClasse : 0.0;
        double moyenneNiveau = totaux.nombreClasses > 0 ? totaux.sommeMoyClasse / totaux.nombreClasses : 0.0;

        // Remplir la ligne de total
        totalRow.getCell(0).setText("Total " + niveau);
        totalRow.getCell(1).setText(String.valueOf(totaux.totalEffectif));
        totalRow.getCell(2).setText(String.valueOf(totaux.totalEffectifClasse));
        totalRow.getCell(3).setText(String.valueOf(totaux.totalEffNonClass));
        totalRow.getCell(4).setText(String.valueOf(totaux.totalNombMoySup10));
        totalRow.getCell(5).setText(String.valueOf(arrondie(totalPourSup10)));
        totalRow.getCell(6).setText(String.valueOf(totaux.totalNombMoyInf10));
        totalRow.getCell(7).setText(String.valueOf(arrondie(totalPourInf10)));
        totalRow.getCell(8).setText(String.valueOf(totaux.totalNombMoyInf8_5));
        totalRow.getCell(9).setText(String.valueOf(arrondie(totalPourInf8_5)));
        totalRow.getCell(10).setText(String.valueOf(arrondie(moyenneNiveau)));

        // Mettre en gras la ligne de total et ajouter une couleur de fond
        mettreEnGrasEtColorer(totalRow);
        ajouterBordures(totalRow);
    }

    // SUPPRIMER ou commenter la méthode grouperParNiveau() car elle n'est plus utilisée
    /*
    private static Map<String, List<ResultatsElevesAffecteDto>> grouperParNiveau(List<ResultatsElevesAffecteDto> detailsBull) {
        // Cette méthode n'est plus nécessaire
    }
    */

    private static String obtenirNiveau(ResultatsElevesAffecteDto classe) {
        if (classe.getOrdre_niveau() == null) return "";

        switch (classe.getOrdre_niveau()) {
            case 1: return "6ème";
            case 2: return "5ème";
            case 3: return "4ème";
            case 4: return "3ème";
            case 5: return "2nde A";
            case 6: return "2nde C";
            case 7: return "1ère A";
            case 8: return "1ère C";
            case 9: return "1ère D";
            case 10: return "Tle A";
            case 11: return "Tle A1";
            case 12: return "Tle A2";
            case 13: return "Tle C";
            case 14: return "Tle D";
            default: return String.valueOf(classe.getOrdre_niveau());
        }
    }

    private static void ajouterBordures(XWPFTableRow row) {
        for (XWPFTableCell cell : row.getTableCells()) {
            CTTcPr tcPr = cell.getCTTc().getTcPr();
            if (tcPr == null) {
                tcPr = cell.getCTTc().addNewTcPr();
            }

            CTTcBorders borders = tcPr.getTcBorders();
            if (borders == null) {
                borders = tcPr.addNewTcBorders();
            }

            // Ajouter des bordures de tous les côtés
            CTBorder topBorder = borders.addNewTop();
            topBorder.setVal(STBorder.SINGLE);
            topBorder.setSz(BigInteger.valueOf(4));
            topBorder.setColor("000000");

            CTBorder bottomBorder = borders.addNewBottom();
            bottomBorder.setVal(STBorder.SINGLE);
            bottomBorder.setSz(BigInteger.valueOf(4));
            bottomBorder.setColor("000000");

            CTBorder leftBorder = borders.addNewLeft();
            leftBorder.setVal(STBorder.SINGLE);
            leftBorder.setSz(BigInteger.valueOf(4));
            leftBorder.setColor("000000");

            CTBorder rightBorder = borders.addNewRight();
            rightBorder.setVal(STBorder.SINGLE);
            rightBorder.setSz(BigInteger.valueOf(4));
            rightBorder.setColor("000000");
        }
    }

    private static void mettreEnGrasEtColorer(XWPFTableRow row) {
        for (XWPFTableCell cell : row.getTableCells()) {
            // Colorer le fond de la cellule
            CTTcPr tcPr = cell.getCTTc().getTcPr();
            if (tcPr == null) {
                tcPr = cell.getCTTc().addNewTcPr();
            }

            CTShd shd = tcPr.getShd();
            if (shd == null) {
                shd = tcPr.addNewShd();
            }
            shd.setFill("E6E6E6"); // Couleur gris clair

            // Mettre le texte en gras
            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    run.setBold(true);
                }
            }
        }
    }

    private static void ensureCellCount(XWPFTableRow row, int cellCount) {
        int currentCellCount = row.getTableCells().size();
        for (int i = currentCellCount; i < cellCount; i++) {
            row.addNewTableCell();
        }
    }

    public static double arrondie(double d) {
        return Double.parseDouble(String.format("%.2f", d));
    }

    private static void mettreEnGrasEtColorerTotalGeneral(XWPFTableRow row) {
        for (XWPFTableCell cell : row.getTableCells()) {
            // Colorer le fond de la cellule avec une couleur plus foncée pour le total général
            CTTcPr tcPr = cell.getCTTc().getTcPr();
            if (tcPr == null) {
                tcPr = cell.getCTTc().addNewTcPr();
            }

            CTShd shd = tcPr.getShd();
            if (shd == null) {
                shd = tcPr.addNewShd();
            }
            shd.setFill("D0D0D0"); // Couleur gris plus foncé pour le total général

            // Mettre le texte en gras
            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    run.setBold(true);
                }
            }
        }
    }

    // Méthodes existantes conservées pour compatibilité
    private static void replacePlaceholdersInTableRow(XWPFTableRow row, Map<String, String> dataMap) {
        for (XWPFTableCell cell : row.getTableCells()) {
            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                replacePlaceholdersInParagraph(paragraph, dataMap);
            }
        }
    }

    private static void replacePlaceholdersInParagraph(XWPFParagraph paragraph, Map<String, String> dataMap) {
        StringBuilder fullText = new StringBuilder();
        List<XWPFRun> runs = paragraph.getRuns();

        for (XWPFRun run : runs) {
            String text = run.getText(0);
            if (text != null) {
                fullText.append(text);
            }
        }

        String paragraphText = fullText.toString();

        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            paragraphText = paragraphText.replace(entry.getKey(), entry.getValue());
        }

        if (!fullText.toString().equals(paragraphText)) {
            int runIndex = 0;
            for (XWPFRun run : runs) {
                String runText = run.getText(0);
                if (runText != null) {
                    String newText = paragraphText.substring(0, Math.min(runText.length(), paragraphText.length()));
                    run.setText(newText, 0);
                    paragraphText = paragraphText.substring(newText.length());
                }
                runIndex++;
            }

            if (!paragraphText.isEmpty()) {
                XWPFRun newRun = paragraph.createRun();
                newRun.setText(paragraphText);
            }
        }
    }

    private static void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            if (rowIndex == fromRow) {
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
}

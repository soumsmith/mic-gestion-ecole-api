package com.vieecoles.processors.dren3;

import java.util.Locale;

/**
 * D�tection du titre "liste des majors" dans le mod�le Word DRENA 3, quel que soit le trimestre
 * (le mod�le change le libell� du trimestre ; l'ancien code ne cherchait que "PREMIER TRIMESTRE",
 * ce qui emp�chait d'ins�rer le tableau dynamique pour le 2e / 3e trimestre).
 */
public final class Dren3WordMajorAnchor {

    private Dren3WordMajorAnchor() {
    }

    /** Ancre trimestrielle : "LISTE DES MAJORS DE CLASSE DU � (03 par niveau)" */
    public static boolean matchesListeMajorsTrimestre(String text) {
        if (text == null) {
            return false;
        }
        String n = normalize(text);
        return n.contains("LISTE DES MAJORS DE CLASSE DU ") && n.contains("(03 PAR NIVEAU)")
                && !n.contains("(ANNUELS)");
    }

    /** Variante annuelle : m�me titre + "(ANNUELS)" */
    public static boolean matchesListeMajorsAnnuel(String text) {
        if (text == null) {
            return false;
        }
        String n = normalize(text);
        return n.contains("LISTE DES MAJORS DE CLASSE DU ") && n.contains("(03 PAR NIVEAU)")
                && n.contains("(ANNUELS)");
    }

    private static String normalize(String text) {
        return text.replace('\u00a0', ' ')
                .toUpperCase(Locale.ROOT)
                .replace('�', 'E')
                .replace('�', 'E')
                .replace('�', 'E')
                .replace('�', 'A')
                .replace('�', 'C');
    }
}

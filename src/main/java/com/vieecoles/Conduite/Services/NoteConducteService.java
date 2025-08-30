package com.vieecoles.Conduite.Services;

import com.vieecoles.Conduite.NoteConducte;
import com.vieecoles.Conduite.entites.Infraction;
import com.vieecoles.steph.entities.Eleve;
import java.util.List;

public class NoteConducteService {
  /**
   * Calcule la note de conduite en fonction des infractions d'un élève
   *
   * @param infractions Liste des infractions de l'élève
   * @return La note de conduite calculée
   */
  public NoteConducte calculerNoteConducte(List<Infraction> infractions) {
    float pointsAssiduite = 6;
    float pointsTenue = 3;
    float pointsMoralite = 4;
    float pointsDiscipline = 7;

    // Parcourir les infractions et déduire les points correspondants
    for (Infraction infraction : infractions) {
      switch (infraction.getCategorie()) {
        case ASSIDUITE:
          pointsAssiduite -= infraction.getPointsRetires();
          if (pointsAssiduite < 0) pointsAssiduite = 0;
          break;
        case TENUE:
          pointsTenue -= infraction.getPointsRetires();
          if (pointsTenue < 0) pointsTenue = 0;
          break;
        case MORALITE:
          pointsMoralite -= infraction.getPointsRetires();
          if (pointsMoralite < 0) pointsMoralite = 0;
          break;
        case DISCIPLINE:
          pointsDiscipline -= infraction.getPointsRetires();
          if (pointsDiscipline < 0) pointsDiscipline = 0;
          break;
      }
    }

    // Calculer la note totale
    float noteTotal = pointsAssiduite + pointsTenue + pointsMoralite + pointsDiscipline;

    // Déterminer l'appréciation
    String appreciation;
    if (noteTotal <= 5) {
      appreciation = "Blâme conduite";
    } else if (noteTotal < 10) {
      appreciation = "Mauvaise conduite";
    } else if (noteTotal >= 10 && noteTotal <= 12) {
      appreciation = "Conduite passable";
    } else if (noteTotal >= 13 && noteTotal <= 15) {
      appreciation = "Bonne conduite";
    } else {
      appreciation = "Très bonne conduite";
    }

    boolean eligibleDistinction = noteTotal > 10;

    return new NoteConducte(
        pointsAssiduite,
        pointsTenue,
        pointsMoralite,
        pointsDiscipline,
        noteTotal,
        appreciation,
        eligibleDistinction
    );
  }

  /**
   * Surcharge qui permet de calculer directement la note à partir d'un élève
   */
  public NoteConducte calculerNoteConductePourEleve(Eleve eleve) {
    //return calculerNoteConducte(eleve.getInfractions());
    return null ;
  }
}

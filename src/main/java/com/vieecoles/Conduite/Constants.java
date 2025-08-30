package com.vieecoles.Conduite;

public class Constants {
  public enum CategorieInfraction {
    ASSIDUITE, // Retards, absences injustifiées
    TENUE,     // Tenue vestimentaire, propreté
    MORALITE,  // Fraude, tricherie, vol, etc.
    DISCIPLINE // Non-respect des règles, perturbations, etc.
  }



  public enum TypeInfraction {
    // Assiduité
    RETARD,
    ABSENCE_INJUSTIFIEE,

    // Tenue
    TENUE_INCORRECTE,
    MANQUE_PROPRETE,

    // Moralité
    FRAUDE,
    TRICHERIE,
    VOL,
    EXTORSION_FONDS,
    ATTOUCHEMENT_SEXUEL,
    PORT_ARME,
    CONSOMMATION_ALCOOL_TABAC,

    // Discipline
    NON_RESPECT_INSTRUCTIONS,
    NON_RESPECT_REGLEMENT,
    ATTROUPEMENT,
    SORTIE_NON_AUTORISEE,
    PERTURBATION_COURS,
    DESOBEISSANCE,
    DEGRADATION_INFRASTRUCTURE,
    AGRESSION_VIOLENCE,
    DESTRUCTION_AUXILIAIRES_PEDAGOGIQUES,
    SUBTILISATION_AUXILIAIRES_PEDAGOGIQUES
  }
}

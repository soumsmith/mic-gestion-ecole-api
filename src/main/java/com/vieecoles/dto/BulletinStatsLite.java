package com.vieecoles.dto;

/**
 * Projection légère pour agrégations statistiques sur {@code Bulletins} sans charger le LOB {@code photo_eleve}.
 * Utilisée dans une requête JPQL {@code SELECT new ...}.
 */
public class BulletinStatsLite {

    private final String libelleClasse;
    private final String niveau;
    private final Integer ordreNiveau;
    private final Integer effectif;
    private final String sexe;
    private final String isClassed;
    private final Double moyGeneral;

    public BulletinStatsLite(String libelleClasse, String niveau, Integer ordreNiveau, Integer effectif,
            String sexe, String isClassed, Double moyGeneral) {
        this.libelleClasse = libelleClasse;
        this.niveau = niveau;
        this.ordreNiveau = ordreNiveau;
        this.effectif = effectif;
        this.sexe = sexe;
        this.isClassed = isClassed;
        this.moyGeneral = moyGeneral;
    }

    public String getLibelleClasse() {
        return libelleClasse;
    }

    public String getNiveau() {
        return niveau;
    }

    public Integer getOrdreNiveau() {
        return ordreNiveau;
    }

    public Integer getEffectif() {
        return effectif;
    }

    public String getSexe() {
        return sexe;
    }

    public String getIsClassed() {
        return isClassed;
    }

    public Double getMoyGeneral() {
        return moyGeneral;
    }
}

package com.vieecoles.dto;

public class matiereMoyenneDto {
    private String libelleMatiere ;
    private Double moyMatiere ;
    private String matricule ;
    private Integer numOrdre ;

    public Integer getNumOrdre() {
        return numOrdre;
    }

    public void setNumOrdre(Integer numOrdre) {
        this.numOrdre = numOrdre;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public matiereMoyenneDto() {
    }

    public String getLibelleMatiere() {
        return libelleMatiere;
    }

    public void setLibelleMatiere(String libelleMatiere) {
        this.libelleMatiere = libelleMatiere;
    }

    public Double getMoyMatiere() {
        return moyMatiere;
    }

    public void setMoyMatiere(Double moyMatiere) {
        this.moyMatiere = moyMatiere;
    }
}

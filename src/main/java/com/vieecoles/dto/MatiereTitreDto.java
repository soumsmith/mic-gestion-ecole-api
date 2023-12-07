package com.vieecoles.dto;

public class MatiereTitreDto {
    private String libelleMatiere ;
    private Double titre ;
    private String ecoleId ;
    private Integer numOrdre ;

    public Integer getNumOrdre() {
        return numOrdre;
    }

    public void setNumOrdre(Integer numOrdre) {
        this.numOrdre = numOrdre;
    }

    public String getLibelleMatiere() {
        return libelleMatiere;
    }

    public void setLibelleMatiere(String libelleMatiere) {
        this.libelleMatiere = libelleMatiere;
    }

    public Double getTitre() {
        return titre;
    }

    public void setTitre(Double titre) {
        this.titre = titre;
    }

    public String getEcoleId() {
        return ecoleId;
    }

    public void setEcoleId(String ecoleId) {
        this.ecoleId = ecoleId;
    }
}

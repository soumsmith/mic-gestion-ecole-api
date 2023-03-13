package com.vieecoles.dto;

public class NbreAnneeDto
{
    private Long nbre;
    private  String annee;

    public NbreAnneeDto() {
    }

    public NbreAnneeDto(Long nbre, String annee) {
        this.nbre = nbre;
        this.annee = annee;
    }

    @Override
    public String toString() {
        return "NbreAnneeDto{" +
                "nbre=" + nbre +
                ", annee='" + annee + '\'' +
                '}';
    }

    public Long getNbre() {
        return nbre;
    }

    public void setNbre(Long nbre) {
        this.nbre = nbre;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }
}

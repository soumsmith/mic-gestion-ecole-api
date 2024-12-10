package com.vieecoles.dto;

public class MatriculeMoyenneDto {
    String matricule;
    Long idMatiere;
    Integer numordre ;

    public MatriculeMoyenneDto(Long idMatiere , Integer numordre , String matricule) {
        this.idMatiere = idMatiere;
        this.numordre=  numordre ;
        this.matricule = matricule ;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Long getIdMatiere() {
        return idMatiere;
    }

    public void setIdMatiere(Long idMatiere) {
        this.idMatiere = idMatiere;
    }

    public Integer getNumordre() {
        return numordre;
    }

    public void setNumordre(Integer numordre) {
        this.numordre = numordre;
    }

    public MatriculeMoyenneDto(Long idMatiere) {
        this.idMatiere = idMatiere;
    }

    @Override
    public String toString() {
        return "NiveauDto{" +
                "idMatiere='" + idMatiere + '\'' +
                '}';
    }
}

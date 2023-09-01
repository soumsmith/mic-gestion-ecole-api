package com.vieecoles.dto;

public class MatProf {
    private String matiere ;
    private String nomProfess ;

    public MatProf() {
    }

    @Override
    public String toString() {
        return "MatProf{" +
                "matiere='" + matiere + '\'' +
                ", nomProfess='" + nomProfess + '\'' +
                '}';
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getNomProfess() {
        return nomProfess;
    }

    public void setNomProfess(String nomProfess) {
        this.nomProfess = nomProfess;
    }
}

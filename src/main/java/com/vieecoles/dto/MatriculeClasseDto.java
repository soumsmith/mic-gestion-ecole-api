package com.vieecoles.dto;

public class MatriculeClasseDto {
    String matricule;
    String classe ;

    public MatriculeClasseDto() {
    }

    public MatriculeClasseDto(String matricule, String classe) {
        this.matricule = matricule;
        this.classe = classe;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    @Override
    public String toString() {
        return "MatriculeClasseDto{" +
                "matricule='" + matricule + '\'' +
                ", classe='" + classe + '\'' +
                '}';
    }
}

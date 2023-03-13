package com.vieecoles.dto;

public class ClasseNiveauDto {
    private String classe ;
    private String Niveau;

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getNiveau() {
        return Niveau;
    }

    public void setNiveau(String niveau) {
        Niveau = niveau;
    }

    public ClasseNiveauDto() {
    }

    public ClasseNiveauDto(String classe, String niveau) {
        this.classe = classe;
        Niveau = niveau;
    }

    @Override
    public String toString() {
        return "classeNiveauDto{" +
                "classe='" + classe + '\'' +
                ", Niveau='" + Niveau + '\'' +
                '}';
    }
}

package com.vieecoles.dto;

public class NiveauDto3 {
    Long idMatiere;
    Integer numordre ;

    public NiveauDto3(Long idMatiere , Integer numordre ) {
        this.idMatiere = idMatiere;
        this.numordre=  numordre ;
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

    public NiveauDto3(Long idMatiere) {
        this.idMatiere = idMatiere;
    }

    @Override
    public String toString() {
        return "NiveauDto{" +
                "idMatiere='" + idMatiere + '\'' +
                '}';
    }
}

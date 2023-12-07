package com.vieecoles.dto;

public class NiveauDto2 {
    String niveau;
    Integer numordre ;

    public NiveauDto2(String niveau ,Integer numordre ) {
        this.niveau = niveau;
        this.numordre=  numordre ;
    }

    public String getNiveau() {
        return niveau;
    }


    public Integer getNumordre() {
        return numordre;
    }

    public void setNumordre(Integer numordre) {
        this.numordre = numordre;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return "NiveauDto{" +
                "niveau='" + niveau + '\'' +
                '}';
    }
}

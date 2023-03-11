package com.vieecoles.dto;

public class ApprocheParNiveauParGenre {
    private String niveau ;
    private int base ;
    private int redouF ;
    private int redouG;
    private int nonRedouF;
    private int nonRedouG;

    public ApprocheParNiveauParGenre(String niveau,
                                     int base,
                                     int redouF,
                                     int redouG,
                                     int nonRedouF,
                                     int nonRedouG) {
        this.niveau = niveau;
        this.base = base;
        this.redouF = redouF;
        this.redouG = redouG;
        this.nonRedouF = nonRedouF;
        this.nonRedouG = nonRedouG;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getRedouF() {
        return redouF;
    }

    public void setRedouF(int redouF) {
        this.redouF = redouF;
    }

    public int getRedouG() {
        return redouG;
    }

    public void setRedouG(int redouG) {
        this.redouG = redouG;
    }

    public int getNonRedouF() {
        return nonRedouF;
    }

    public void setNonRedouF(int nonRedouF) {
        this.nonRedouF = nonRedouF;
    }

    public int getNonRedouG() {
        return nonRedouG;
    }

    public void setNonRedouG(int nonRedouG) {
        this.nonRedouG = nonRedouG;
    }
}

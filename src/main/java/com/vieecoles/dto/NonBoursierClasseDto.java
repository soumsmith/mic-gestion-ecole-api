package com.vieecoles.dto;

public class NonBoursierClasseDto {
    private String classe ;
    private Integer nonbournbreG ;
    private Integer nonbournbreF ;

    public NonBoursierClasseDto() {
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Integer getNonbournbreG() {
        return nonbournbreG;
    }

    public void setNonbournbreG(Integer nonbournbreG) {
        this.nonbournbreG = nonbournbreG;
    }

    public Integer getNonbournbreF() {
        return nonbournbreF;
    }

    public void setNonbournbreF(Integer nonbournbreF) {
        this.nonbournbreF = nonbournbreF;
    }
}

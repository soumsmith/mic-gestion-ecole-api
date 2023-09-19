package com.vieecoles.dto;

public class DemiBoursierClasseDto {
    private String classe ;
    private Integer demibournbreG ;
    private Integer demibournbreF ;

    public DemiBoursierClasseDto() {
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Integer getDemibournbreG() {
        return demibournbreG;
    }

    public void setDemibournbreG(Integer demibournbreG) {
        this.demibournbreG = demibournbreG;
    }

    public Integer getDemibournbreF() {
        return demibournbreF;
    }

    public void setDemibournbreF(Integer demibournbreF) {
        this.demibournbreF = demibournbreF;
    }
}

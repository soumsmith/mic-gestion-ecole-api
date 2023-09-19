package com.vieecoles.dto;

public class BoursierClasseDto {
    private String classe ;
    private Integer bournbreG ;
    private Integer bournbreF ;

    public BoursierClasseDto() {
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Integer getBournbreG() {
        return bournbreG;
    }

    public void setBournbreG(Integer bournbreG) {
        this.bournbreG = bournbreG;
    }

    public Integer getBournbreF() {
        return bournbreF;
    }

    public void setBournbreF(Integer bournbreF) {
        this.bournbreF = bournbreF;
    }
}

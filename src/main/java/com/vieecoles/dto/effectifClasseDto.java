package com.vieecoles.dto;

public class effectifClasseDto {
    private String classe ;
    private Long nbreG ;
    private Long nbreF ;
    private  Long nbreTotal ;

    public Long getNbreTotal() {
        return nbreTotal;
    }

    public void setNbreTotal(Long nbreTotal) {
        this.nbreTotal = nbreTotal;
    }

    public effectifClasseDto() {
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Long getNbreG() {
        return nbreG;
    }

    public void setNbreG(Long nbreG) {
        this.nbreG = nbreG;
    }

    public Long getNbreF() {
        return nbreF;
    }

    public void setNbreF(Long nbreF) {
        this.nbreF = nbreF;
    }
}

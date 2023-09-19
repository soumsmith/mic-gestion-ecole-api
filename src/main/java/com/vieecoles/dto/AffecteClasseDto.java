package com.vieecoles.dto;

public class AffecteClasseDto {
    private String classe ;
    private Long affnbreG ;
    private Long affnbreF ;
    private  Long nbreTotal ;

    public Long getNbreTotal() {
        return nbreTotal;
    }

    public void setNbreTotal(Long nbreTotal) {
        this.nbreTotal = nbreTotal;
    }

    public AffecteClasseDto() {
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Long getAffnbreG() {
        return affnbreG;
    }

    public void setAffnbreG(Long affnbreG) {
        this.affnbreG = affnbreG;
    }

    public Long getAffnbreF() {
        return affnbreF;
    }

    public void setAffnbreF(Long affnbreF) {
        this.affnbreF = affnbreF;
    }
}

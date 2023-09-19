package com.vieecoles.dto;

public class RedoublantAffClasseDto {
    private String classe ;
    private Long rednbreG ;
    private Long rednbreF ;
    private  Long nbreTotal ;

    public Long getNbreTotal() {
        return nbreTotal;
    }

    public void setNbreTotal(Long nbreTotal) {
        this.nbreTotal = nbreTotal;
    }

    public RedoublantAffClasseDto() {
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Long getRednbreG() {
        return rednbreG;
    }

    public void setRednbreG(Long rednbreG) {
        this.rednbreG = rednbreG;
    }

    public Long getRednbreF() {
        return rednbreF;
    }

    public void setRednbreF(Long rednbreF) {
        this.rednbreF = rednbreF;
    }
}

package com.vieecoles.dto;

public class parcourDto {
    private  String anneeScol ;
    private String etabliLibe ;
    private String classeLibell ;
    private Double moyGenAnnuelle;
    private String rangAn ;

    public parcourDto() {
    }

    public parcourDto(String anneeScol, String etabliLibe, String classeLibell, Double moyGenAnnuelle, String rangAn) {
        this.anneeScol = anneeScol;
        this.etabliLibe = etabliLibe;
        this.classeLibell = classeLibell;
        this.moyGenAnnuelle = moyGenAnnuelle;
        this.rangAn = rangAn;
    }

    public String getAnneeScol() {
        return anneeScol;
    }

    public void setAnneeScol(String anneeScol) {
        this.anneeScol = anneeScol;
    }

    public String getEtabliLibe() {
        return etabliLibe;
    }

    public void setEtabliLibe(String etabliLibe) {
        this.etabliLibe = etabliLibe;
    }

    public String getClasseLibell() {
        return classeLibell;
    }

    public void setClasseLibell(String classeLibell) {
        this.classeLibell = classeLibell;
    }

    public Double getMoyGenAnnuelle() {
        return moyGenAnnuelle;
    }

    public void setMoyGenAnnuelle(Double moyGenAnnuelle) {
        this.moyGenAnnuelle = moyGenAnnuelle;
    }

    public String getRangAn() {
        return rangAn;
    }

    public void setRangAn(String rangAn) {
        this.rangAn = rangAn;
    }
}

package com.vieecoles.dto;

public class matriceAnnuelleDto {
    private String nomEleve ;
    private String prenomEleve ;
    private String libelleClasse ;
    private Double moy1 ;
    private int rang1 ;
    private Double moy2 ;
    private int rang2 ;
    private Double moy3 ;
    private int rang3 ;
    private Double moyAnn ;
    private  int rangAn ;
    private  String appreciation ;

    public matriceAnnuelleDto() {
    }

    public String getNomEleve() {
        return nomEleve;
    }

    public void setNomEleve(String nomEleve) {
        this.nomEleve = nomEleve;
    }

    public String getPrenomEleve() {
        return prenomEleve;
    }

    public void setPrenomEleve(String prenomEleve) {
        this.prenomEleve = prenomEleve;
    }

    public String getLibelleClasse() {
        return libelleClasse;
    }

    public void setLibelleClasse(String libelleClasse) {
        this.libelleClasse = libelleClasse;
    }

    public Double getMoy1() {
        return moy1;
    }

    public void setMoy1(Double moy1) {
        this.moy1 = moy1;
    }

    public int getRang1() {
        return rang1;
    }

    public void setRang1(int rang1) {
        this.rang1 = rang1;
    }

    public Double getMoy2() {
        return moy2;
    }

    public void setMoy2(Double moy2) {
        this.moy2 = moy2;
    }

    public int getRang2() {
        return rang2;
    }

    public void setRang2(int rang2) {
        this.rang2 = rang2;
    }

    public Double getMoy3() {
        return moy3;
    }

    public void setMoy3(Double moy3) {
        this.moy3 = moy3;
    }

    public int getRang3() {
        return rang3;
    }

    public void setRang3(int rang3) {
        this.rang3 = rang3;
    }

    public Double getMoyAnn() {
        return moyAnn;
    }

    public void setMoyAnn(Double moyAnn) {
        this.moyAnn = moyAnn;
    }

    public int getRangAn() {
        return rangAn;
    }

    public void setRangAn(int rangAn) {
        this.rangAn = rangAn;
    }

    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }
}

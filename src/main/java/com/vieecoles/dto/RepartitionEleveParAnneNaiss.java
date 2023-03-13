package com.vieecoles.dto;

public class RepartitionEleveParAnneNaiss {
    private String dateNaissance;
    private String sexe;
    private String niveau ;
    private  int nbre;

    public RepartitionEleveParAnneNaiss(String dateNaissance, String sexe, String niveau,Integer nbre) {
        this.dateNaissance = dateNaissance;
        this.sexe = sexe;
        this.niveau = niveau;
        this.nbre =nbre ;
    }

    public RepartitionEleveParAnneNaiss() {
    }

    public Integer getNbre() {
        return nbre;
    }

    public void setNbre(Integer nbre) {
        this.nbre = nbre;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
}

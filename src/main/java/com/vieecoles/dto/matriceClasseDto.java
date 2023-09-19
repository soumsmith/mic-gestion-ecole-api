package com.vieecoles.dto;

import java.util.List;

public class matriceClasseDto {
   private  Long ideleve ;
   private  String matricule ;
    private  String nom ;
    private  String prenoms;
    private  int rang ;
    private  String appreciation ;
    private String periode ;
    private String anneScolaire ;
    private  Double moyenTrimes ;

    public Double getMoyenTrimes() {
        return moyenTrimes;
    }

    public void setMoyenTrimes(Double moyenTrimes) {
        this.moyenTrimes = moyenTrimes;
    }

    private List<matiereMoyenneDto>  matiereMoyenneDto ;

    public Long getIdeleve() {
        return ideleve;
    }

    public void setIdeleve(Long ideleve) {
        this.ideleve = ideleve;
    }



    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getAnneScolaire() {
        return anneScolaire;
    }

    public void setAnneScolaire(String anneScolaire) {
        this.anneScolaire = anneScolaire;
    }



    public matriceClasseDto() {
    }

    public Long getIdEleve() {
        return ideleve;
    }

    public void setIdEleve(Long idEleve) {
        this.ideleve = idEleve;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    public List<com.vieecoles.dto.matiereMoyenneDto> getMatiereMoyenneDto() {
        return matiereMoyenneDto;
    }

    public void setMatiereMoyenneDto(List<com.vieecoles.dto.matiereMoyenneDto> matiereMoyenneDto) {
        this.matiereMoyenneDto = matiereMoyenneDto;
    }

    @Override
    public String toString() {
        return "matriceClasseDto{" +
                "idEleve=" + ideleve +
                ", matricule='" + matricule + '\'' +
                ", nom='" + nom + '\'' +
                ", prenoms='" + prenoms + '\'' +
                ", rang=" + rang +
                ", appreciation='" + appreciation + '\'' +
                ", periode='" + periode + '\'' +
                ", anneScolaire='" + anneScolaire + '\'' +
                ", matiereMoyenneDto=" + matiereMoyenneDto +
                '}';
    }
}

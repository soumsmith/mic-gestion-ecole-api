package com.vieecoles.dto;

import java.util.List;

public class matriceClasseEtMoyenneDto {
   private  Long ideleve ;
   private  String matricule ;
    private  String classe ;
    private  String nom ;
    private  String prenoms;
    private  int rang ;
    private  String appreciation ;
    private String periode ;
    private String anneScolaire ;
    private  Double moyenTrimes ;
    private String libelleMatiere ;
    private Double moyMatiere ;
  private Integer numOrdre ;

  public String getLibelleMatiere() {
    return libelleMatiere;
  }

  public void setLibelleMatiere(String libelleMatiere) {
    this.libelleMatiere = libelleMatiere;
  }

  public Double getMoyMatiere() {
    return moyMatiere;
  }

  public void setMoyMatiere(Double moyMatiere) {
    this.moyMatiere = moyMatiere;
  }

  public Integer getNumOrdre() {
    return numOrdre;
  }

  public void setNumOrdre(Integer numOrdre) {
    this.numOrdre = numOrdre;
  }

  public Double getMoyenTrimes() {
        return moyenTrimes;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setMoyenTrimes(Double moyenTrimes) {
        this.moyenTrimes = moyenTrimes;
    }



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



    public matriceClasseEtMoyenneDto() {
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
                '}';
    }
}

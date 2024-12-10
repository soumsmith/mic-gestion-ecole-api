package com.vieecoles.dto;

public class InfosEleveDto {
  String nom;
  String prenom;
  String matricule;

  public InfosEleveDto(String nom, String prenom, String matricule) {
    this.nom = nom;
    this.prenom = prenom;
    this.matricule = matricule;
  }

  public InfosEleveDto() {
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public String getMatricule() {
    return matricule;
  }

  public void setMatricule(String matricule) {
    this.matricule = matricule;
  }
}

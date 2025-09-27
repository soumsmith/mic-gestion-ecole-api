package com.vieecoles.dto;


import java.time.LocalDate;


public class PersonnelVieEcoleDto {

   private  String sous_attent_personn_nom;
    private  String sous_attent_personn_prenom;
    private  String sous_attent_personn_email;
    private String  sous_attent_personn_sexe;
    private LocalDate sous_attent_personn_date_naissance;
    private String sous_attent_personn_login ;
    private String sous_attent_personn_password ;
    private  String sous_attent_personn_contact ;
    private String typecompte ;
  public String getSous_attent_personn_nom() {
    return sous_attent_personn_nom;
  }

  public String getTypecompte() {
    return typecompte;
  }

  public void setTypecompte(String typecompte) {
    this.typecompte = typecompte;
  }

  public void setSous_attent_personn_nom(String sous_attent_personn_nom) {
    this.sous_attent_personn_nom = sous_attent_personn_nom;
  }

  public String getSous_attent_personn_prenom() {
    return sous_attent_personn_prenom;
  }

  public void setSous_attent_personn_prenom(String sous_attent_personn_prenom) {
    this.sous_attent_personn_prenom = sous_attent_personn_prenom;
  }

  public String getSous_attent_personn_email() {
    return sous_attent_personn_email;
  }

  public void setSous_attent_personn_email(String sous_attent_personn_email) {
    this.sous_attent_personn_email = sous_attent_personn_email;
  }

  public String getSous_attent_personn_sexe() {
    return sous_attent_personn_sexe;
  }

  public void setSous_attent_personn_sexe(String sous_attent_personn_sexe) {
    this.sous_attent_personn_sexe = sous_attent_personn_sexe;
  }

  public LocalDate getSous_attent_personn_date_naissance() {
    return sous_attent_personn_date_naissance;
  }

  public void setSous_attent_personn_date_naissance(LocalDate sous_attent_personn_date_naissance) {
    this.sous_attent_personn_date_naissance = sous_attent_personn_date_naissance;
  }

  public String getSous_attent_personn_login() {
    return sous_attent_personn_login;
  }

  public void setSous_attent_personn_login(String sous_attent_personn_login) {
    this.sous_attent_personn_login = sous_attent_personn_login;
  }

  public String getSous_attent_personn_password() {
    return sous_attent_personn_password;
  }

  public void setSous_attent_personn_password(String sous_attent_personn_password) {
    this.sous_attent_personn_password = sous_attent_personn_password;
  }

  public String getSous_attent_personn_contact() {
    return sous_attent_personn_contact;
  }

  public void setSous_attent_personn_contact(String sous_attent_personn_contact) {
    this.sous_attent_personn_contact = sous_attent_personn_contact;
  }
}

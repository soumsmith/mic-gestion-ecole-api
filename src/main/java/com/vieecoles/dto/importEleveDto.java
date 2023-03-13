package com.vieecoles.dto;

import java.sql.Timestamp;
import java.time.LocalDate;

public class importEleveDto {
    private  String ID ;
    private  String matricule ;
    private  String nom;
    private  String prenoms ;
    private  String statut;
    private  String red;
    private  String nationalite;
    private  String classe;
    private  String lv2;
    private  String regime;
    private String dateNaissance ;
    private String lieuNaissance ;
    private  String sexe;
    private  String dateInscription ;
    private  String contact;
    private  Long identifiantEcole;
    private  String niveau ;
    private  Long identifiantBranche;


    public importEleveDto() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getLv2() {
        return lv2;
    }

    public void setLv2(String lv2) {
        this.lv2 = lv2;
    }


    public String getRegime() {
        return regime;
    }

    public void setRegime(String regime) {
        this.regime = regime;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(String dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Long getIdentifiantEcole() {
        return identifiantEcole;
    }

    public void setIdentifiantEcole(Long identifiantEcole) {
        this.identifiantEcole = identifiantEcole;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Long getIdentifiantBranche() {
        return identifiantBranche;
    }

    public void setIdentifiantBranche(Long identifiantBranche) {
        this.identifiantBranche = identifiantBranche;
    }

    @Override
    public String toString() {
        return "importEleveDto{" +
                "ID='" + ID + '\'' +
                ", matricule='" + matricule + '\'' +
                ", nom='" + nom + '\'' +
                ", prenoms='" + prenoms + '\'' +
                ", statut='" + statut + '\'' +
                ", red='" + red + '\'' +
                ", nationalite='" + nationalite + '\'' +
                ", classe='" + classe + '\'' +
                ", lv2='" + lv2 + '\'' +
                ", regime='" + regime + '\'' +
                ", dateNaissance='" + dateNaissance + '\'' +
                ", lieuNaissance='" + lieuNaissance + '\'' +
                ", sexe='" + sexe + '\'' +
                ", dateInscription='" + dateInscription + '\'' +
                ", contact='" + contact + '\'' +
                ", identifiantEcole=" + identifiantEcole +
                ", niveau='" + niveau + '\'' +
                ", identifiantBranche=" + identifiantBranche +
                '}';
    }
}

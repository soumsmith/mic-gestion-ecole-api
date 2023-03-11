package com.vieecoles.dto;

import java.sql.Timestamp;
import java.time.LocalDate;

public class importEleveDto {
    private  String code_interne ;
    private  String matricule ;
    private  String nom;
    private  String prenoms ;
    private  String statut;
    private  String contact1;
    private  String contact2;
    private  String classe;
    private  String lv2;
    private  Long identifiant_classe;
    private LocalDate date_naissance ;
    private String lieu_naissance ;
    private  String sexe;
    private  Long identifiantEcole;
    private  String libelleBranche ;
    private  Long identifiantBranche;


    public importEleveDto() {
    }

    public String getCode_interne() {
        return code_interne;
    }

    public void setCode_interne(String code_interne) {
        this.code_interne = code_interne;
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

    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact2() {
        return contact2;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Long getIdentifiant_classe() {
        return identifiant_classe;
    }

    public void setIdentifiant_classe(Long identifiant_classe) {
        this.identifiant_classe = identifiant_classe;
    }



    public String getLv2() {
        return lv2;
    }

    public void setLv2(String lv2) {
        this.lv2 = lv2;
    }


    public LocalDate getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getLieu_naissance() {
        return lieu_naissance;
    }

    public void setLieu_naissance(String lieu_naissance) {
        this.lieu_naissance = lieu_naissance;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public Long getIdentifiantEcole() {
        return identifiantEcole;
    }

    public void setIdentifiantEcole(Long identifiantEcole) {
        this.identifiantEcole = identifiantEcole;
    }

    public String getLibelleBranche() {
        return libelleBranche;
    }

    public void setLibelleBranche(String libelleBranche) {
        this.libelleBranche = libelleBranche;
    }

    public Long getIdentifiantBranche() {
        return identifiantBranche;
    }

    public void setIdentifiantBranche(Long identifiantBranche) {
        this.identifiantBranche = identifiantBranche;
    }
}

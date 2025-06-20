package com.vieecoles.dto;

import java.util.List;

public class MajorParClasseNiveauDto {
    private String niveau;
    private String classeLibelle;
    private String matricule ;
    private String nom;
    private String  prenom;
    private String anneeNaiss;
    private String sexe;
    private String nature;
    private String redoublant;
    private Double moyGeneral;
    private String lv2 ;
    private  Integer ordre_niveau ;
    private  String nationalite ;
    private  String affecte ;

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getAffecte() {
        return affecte;
    }

    public void setAffecte(String affecte) {
        this.affecte = affecte;
    }

    public MajorParClasseNiveauDto(List<MajorParClasseNiveauDto> majorParClasseNiveauDtos) {
    }

    public Integer getOrdre_niveau() {
        return ordre_niveau;
    }

    public void setOrdre_niveau(Integer ordre_niveau) {
        this.ordre_niveau = ordre_niveau;
    }

    public MajorParClasseNiveauDto() {
    }

    @Override
    public String toString() {
        return "MajorParClasseNiveauDto{" +
                "niveau='" + niveau + '\'' +
                ", classeLibelle='" + classeLibelle + '\'' +
                ", matricule='" + matricule + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", anneeNaiss='" + anneeNaiss + '\'' +
                ", sexe='" + sexe + '\'' +
                ", Nature='" + nature + '\'' +
                ", redoublant='" + redoublant + '\'' +
                ", moyGeneral=" + moyGeneral +
                ", lv2='" + lv2 + '\'' +
                '}';
    }

    public MajorParClasseNiveauDto(String niveau,
                                   String classeLibelle,
                                   String matricule,
                                   String nom,
                                   String prenom,
                                   String anneeNaiss,
                                   String sexe,
                                   String nature,
                                   String redoublant,
                                   Double moyGeneral,
                                   String lv2,
                                   Integer ordre_niveau) {
        this.niveau = niveau;
        this.classeLibelle = classeLibelle;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom=prenom;
        this.anneeNaiss = anneeNaiss;
        this.sexe = sexe;
        nature = nature;
        this.redoublant = redoublant;
        this.moyGeneral = moyGeneral;
        this.lv2 = lv2;
        this.ordre_niveau = ordre_niveau ;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getClasseLibelle() {
        return classeLibelle;
    }

    public void setClasseLibelle(String classeLibelle) {
        this.classeLibelle = classeLibelle;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAnneeNaiss() {
        return anneeNaiss;
    }

    public void setAnneeNaiss(String anneeNaiss) {
        this.anneeNaiss = anneeNaiss;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        nature = nature;
    }

    public String getRedoublant() {
        return redoublant;
    }

    public void setRedoublant(String redoublant) {
        this.redoublant = redoublant;
    }

    public Double getMoyGeneral() {
        return moyGeneral;
    }

    public void setMoyGeneral(Double moyGeneral) {
        this.moyGeneral = moyGeneral;
    }

    public String getLv2() {
        return lv2;
    }

    public void setLv2(String lv2) {
        this.lv2 = lv2;
    }
}

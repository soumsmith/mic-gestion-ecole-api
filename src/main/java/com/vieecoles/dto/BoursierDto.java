package com.vieecoles.dto;

import java.util.List;

public class BoursierDto {
    private String matricule ;
    private String nom ;
    private String prenoms;
    private String sexe ;
    private String dateNaiss;
    private String lieuNaiss;
    private String niveau ;
    private  Integer ordre_niveau ;

    public BoursierDto(List<BoursierDto> boursier) {
    }

    public Integer getOrdre_niveau() {
        return ordre_niveau;
    }

    public void setOrdre_niveau(Integer ordre_niveau) {
        this.ordre_niveau = ordre_niveau;
    }

    public BoursierDto() {
    }

    public BoursierDto(String matricule,
                       String nom,
                       String prenoms,
                       String sexe,
                       String dateNaiss,
                       String lieuNaiss,
                       String niveau,
                        Integer  ordre_niveau) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenoms = prenoms;
        this.sexe = sexe;
        this.dateNaiss = dateNaiss;
        this.lieuNaiss = lieuNaiss;
        this.niveau = niveau;
        this.ordre_niveau = ordre_niveau ;
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

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(String dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getLieuNaiss() {
        return lieuNaiss;
    }

    public void setLieuNaiss(String lieuNaiss) {
        this.lieuNaiss = lieuNaiss;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
}

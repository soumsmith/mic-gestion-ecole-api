package com.vieecoles.dto;

import java.util.List;

public class EtatNominatifEnseignatDto {
    private String nom ;
    private String prenoms ;
    private String dateNaiss ;
    private String diplome ;
    private String statutVacPer ;
    private String contact ;
    private List<ClassesTenuesDto> classesTenuesDto ;

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

    public String getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(String dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public String getStatutVacPer() {
        return statutVacPer;
    }

    public void setStatutVacPer(String statutVacPer) {
        this.statutVacPer = statutVacPer;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<ClassesTenuesDto> getClassesTenuesDto() {
        return classesTenuesDto;
    }

    public void setClassesTenuesDto(List<ClassesTenuesDto> classesTenuesDto) {
        this.classesTenuesDto = classesTenuesDto;
    }
}

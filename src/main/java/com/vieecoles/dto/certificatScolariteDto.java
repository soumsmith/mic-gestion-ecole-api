package com.vieecoles.dto;

import java.util.List;

public class certificatScolariteDto {
    private String nomPrenomEleve ;
    private String dateNaiss;
    private String lieuNaiss ;
    private String matricule;
    private String nationalite ;
    private String dateDebutFrequen ;
    private String dateFinFrequen ;
    private List<parcourDto> parcourDto ;

    public String getNomPrenomEleve() {
        return nomPrenomEleve;
    }

    public void setNomPrenomEleve(String nomPrenomEleve) {
        this.nomPrenomEleve = nomPrenomEleve;
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

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getDateDebutFrequen() {
        return dateDebutFrequen;
    }

    public void setDateDebutFrequen(String dateDebutFrequen) {
        this.dateDebutFrequen = dateDebutFrequen;
    }

    public String getDateFinFrequen() {
        return dateFinFrequen;
    }

    public void setDateFinFrequen(String dateFinFrequen) {
        this.dateFinFrequen = dateFinFrequen;
    }

    public List<com.vieecoles.dto.parcourDto> getParcourDto() {
        return parcourDto;
    }

    public void setParcourDto(List<com.vieecoles.dto.parcourDto> parcourDto) {
        this.parcourDto = parcourDto;
    }
}

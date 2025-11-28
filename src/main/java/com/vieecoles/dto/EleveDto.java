package com.vieecoles.dto;

import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EleveDto {
    private  Long    eleveid ;
    private  String  elevecode ;
    private  String  elevenom ;
    private  String eleveprenom ;
    private LocalDate elevedate_naissance ;
    private  String  elevelieu_naissance ;
    private  String eleve_numero_extrait_naiss ;
    private  LocalDate  elevedate_etabli_extrait_naiss ;
    private  String  elevelieu_etabliss_etrait_naissance ;
    private  String eleveadresse ;
    private  String  elevecellulaire ;
    private  String  eleve_mail ;
    private  String eleve_nationalite ;
    private  String elevephoto ;
    public String getElevephoto() {
        return elevephoto;
    }

    public void setElevephoto(String elevephoto) {
        this.elevephoto = elevephoto;
    }

    private  String elevematricule_national ;
    private  String eleveSexe ;
    private List<Long> parentList = new ArrayList<>() ;

    public List<Long> getParentList() {
        return parentList;
    }

    public String getEleveSexe() {
        return eleveSexe;
    }

    public void setEleveSexe(String eleveSexe) {
        this.eleveSexe = eleveSexe;
    }

    public void setParentList(List<Long> parentList) {
        this.parentList = parentList;
    }

    public String getElevematricule_national() {
        return elevematricule_national;
    }

    public void setElevematricule_national(String elevematricule_national) {
        this.elevematricule_national = elevematricule_national;
    }

    public EleveDto() {
    }

    @Override
    public String toString() {
        return "EleveDto{" +
                "eleveid=" + eleveid +
                ", elevecode='" + elevecode + '\'' +
                ", elevenom='" + elevenom + '\'' +
                ", eleveprenom='" + eleveprenom + '\'' +
                ", elevedate_naissance=" + elevedate_naissance +
                ", elevelieu_naissance='" + elevelieu_naissance + '\'' +
                ", eleve_numero_extrait_naiss=" + eleve_numero_extrait_naiss +
                ", elevedate_etabli_extrait_naiss=" + elevedate_etabli_extrait_naiss +
                ", elevelieu_etabliss_etrait_naissance='" + elevelieu_etabliss_etrait_naissance + '\'' +
                ", eleveadresse='" + eleveadresse + '\'' +
                ", elevecellulaire='" + elevecellulaire + '\'' +
                ", eleve_mail='" + eleve_mail + '\'' +
                   '}';
    }

    public String getEleve_nationalite() {
        return eleve_nationalite;
    }

    public void setEleve_nationalite(String eleve_nationalite) {
        this.eleve_nationalite = eleve_nationalite;
    }

    public EleveDto(Long eleveid, String elevecode, String elevenom, String eleveprenom, LocalDate elevedate_naissance, String elevelieu_naissance, String eleve_numero_extrait_naiss, LocalDate elevedate_etabli_extrait_naiss, String elevelieu_etabliss_etrait_naissance, String eleveadresse, String elevecellulaire, String eleve_mail) {
        this.eleveid = eleveid;
        this.elevecode = elevecode;
        this.elevenom = elevenom;
        this.eleveprenom = eleveprenom;
        this.elevedate_naissance = elevedate_naissance;
        this.elevelieu_naissance = elevelieu_naissance;
        this.eleve_numero_extrait_naiss = eleve_numero_extrait_naiss;
        this.elevedate_etabli_extrait_naiss = elevedate_etabli_extrait_naiss;
        this.elevelieu_etabliss_etrait_naissance = elevelieu_etabliss_etrait_naissance;
        this.eleveadresse = eleveadresse;
        this.elevecellulaire = elevecellulaire;
        this.eleve_mail = eleve_mail;
            }


    public Long getEleveid() {
        return eleveid;
    }

    public void setEleveid(Long eleveid) {
        this.eleveid = eleveid;
    }

    public String getElevecode() {
        return elevecode;
    }

    public void setElevecode(String elevecode) {
        this.elevecode = elevecode;
    }

    public String getElevenom() {
        return elevenom;
    }

    public void setElevenom(String elevenom) {
        this.elevenom = elevenom;
    }

    public String getEleveprenom() {
        return eleveprenom;
    }

    public void setEleveprenom(String eleveprenom) {
        this.eleveprenom = eleveprenom;
    }

    public LocalDate getElevedate_naissance() {
        return elevedate_naissance;
    }

    public void setElevedate_naissance(LocalDate elevedate_naissance) {
        this.elevedate_naissance = elevedate_naissance;
    }

    public String getElevelieu_naissance() {
        return elevelieu_naissance;
    }

    public void setElevelieu_naissance(String elevelieu_naissance) {
        this.elevelieu_naissance = elevelieu_naissance;
    }

    public String getEleve_numero_extrait_naiss() {
        return eleve_numero_extrait_naiss;
    }

    public void setEleve_numero_extrait_naiss(String eleve_numero_extrait_naiss) {
        this.eleve_numero_extrait_naiss = eleve_numero_extrait_naiss;
    }

    public LocalDate getElevedate_etabli_extrait_naiss() {
        return elevedate_etabli_extrait_naiss;
    }

    public void setElevedate_etabli_extrait_naiss(LocalDate elevedate_etabli_extrait_naiss) {
        this.elevedate_etabli_extrait_naiss = elevedate_etabli_extrait_naiss;
    }

    public String getElevelieu_etabliss_etrait_naissance() {
        return elevelieu_etabliss_etrait_naissance;
    }

    public void setElevelieu_etabliss_etrait_naissance(String elevelieu_etabliss_etrait_naissance) {
        this.elevelieu_etabliss_etrait_naissance = elevelieu_etabliss_etrait_naissance;
    }

    public String getEleveadresse() {
        return eleveadresse;
    }

    public void setEleveadresse(String eleveadresse) {
        this.eleveadresse = eleveadresse;
    }

    public String getElevecellulaire() {
        return elevecellulaire;
    }

    public void setElevecellulaire(String elevecellulaire) {
        this.elevecellulaire = elevecellulaire;
    }

    public String getEleve_mail() {
        return eleve_mail;
    }

    public void setEleve_mail(String eleve_mail) {
        this.eleve_mail = eleve_mail;
    }
}

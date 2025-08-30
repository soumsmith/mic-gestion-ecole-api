package com.vieecoles.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;


public class utilisateur_has_personnelDto {

    private  Long   personnel_personnelid ;
    private  Long   utilisateur_has_personid ;
    private LocalDate utilisateur_has_person_date_creation ;
    private LocalDate utilisateur_has_person_modif ;
    private  int utilisateur_has_person_active ;
    private LocalDate utilisateur_has_person_date_debut ;
    private  LocalDate utilisateur_has_person_date_fin ;
    private Long  ecole_ecoleid ;
    private  Long profilid ;
    private List<Long> listProfil ;
    private  Long utilisateurid  ;
    private String emailUtilisateur ;
    private String login ;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getPersonnel_personnelid() {
        return personnel_personnelid;
    }

    public List<Long> getListProfil() {
        return listProfil;
    }

    public void setListProfil(List<Long> listProfil) {
        this.listProfil = listProfil;
    }

    public void setPersonnel_personnelid(Long personnel_personnelid) {
        this.personnel_personnelid = personnel_personnelid;
    }

    public LocalDate getUtilisateur_has_person_date_creation() {
        return utilisateur_has_person_date_creation;
    }

    public void setUtilisateur_has_person_date_creation(LocalDate utilisateur_has_person_date_creation) {
        this.utilisateur_has_person_date_creation = utilisateur_has_person_date_creation;
    }

    public String getEmailUtilisateur() {
        return emailUtilisateur;
    }

    public void setEmailUtilisateur(String emailUtilisateur) {
        this.emailUtilisateur = emailUtilisateur;
    }

    public LocalDate getUtilisateur_has_person_modif() {
        return utilisateur_has_person_modif;
    }

    public void setUtilisateur_has_person_modif(LocalDate utilisateur_has_person_modif) {
        this.utilisateur_has_person_modif = utilisateur_has_person_modif;
    }

    public LocalDate getUtilisateur_has_person_date_debut() {
        return utilisateur_has_person_date_debut;
    }

    public void setUtilisateur_has_person_date_debut(LocalDate utilisateur_has_person_date_debut) {
        this.utilisateur_has_person_date_debut = utilisateur_has_person_date_debut;
    }

    public LocalDate getUtilisateur_has_person_date_fin() {
        return utilisateur_has_person_date_fin;
    }

    public void setUtilisateur_has_person_date_fin(LocalDate utilisateur_has_person_date_fin) {
        this.utilisateur_has_person_date_fin = utilisateur_has_person_date_fin;
    }

    public int getUtilisateur_has_person_active() {
        return utilisateur_has_person_active;
    }

    public void setUtilisateur_has_person_active(int utilisateur_has_person_active) {
        this.utilisateur_has_person_active = utilisateur_has_person_active;
    }

    public Long getEcole_ecoleid() {
        return ecole_ecoleid;
    }

    public void setEcole_ecoleid(Long ecole_ecoleid) {
        this.ecole_ecoleid = ecole_ecoleid;
    }

    public Long getProfilid() {
        return profilid;
    }

    public void setProfilid(Long profilid) {
        this.profilid = profilid;
    }

    public Long getUtilisateurid() {
        return utilisateurid;
    }

    public void setUtilisateurid(Long utilisateurid) {
        this.utilisateurid = utilisateurid;
    }

    public Long getUtilisateur_has_personid() {
        return utilisateur_has_personid;
    }

    public void setUtilisateur_has_personid(Long utilisateur_has_personid) {
        this.utilisateur_has_personid = utilisateur_has_personid;
    }
}

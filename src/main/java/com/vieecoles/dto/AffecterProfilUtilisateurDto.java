package com.vieecoles.dto;

import com.vieecoles.dao.entities.profil;

import java.time.LocalDate;
import java.util.List;


public class AffecterProfilUtilisateurDto {

    private  Long   personnel_personnelid ;
    private LocalDate utilisateur_has_person_modif;
    private  int utilisateur_has_person_active ;
    private  LocalDate utilisateur_has_person_date_fin ;
    private Long  ecole_ecoleid ;
    private  Long profilid ;
    private List<profil> listProfil ;


    public Long getPersonnel_personnelid() {
        return personnel_personnelid;
    }

    public List<profil> getListProfil() {
        return listProfil;
    }

    public void setListProfil(List<profil> listProfil) {
        this.listProfil = listProfil;
    }

    public void setPersonnel_personnelid(Long personnel_personnelid) {
        this.personnel_personnelid = personnel_personnelid;
    }

    public LocalDate getUtilisateur_has_person_modif() {
        return utilisateur_has_person_modif;
    }

    public void setUtilisateur_has_person_modif(LocalDate utilisateur_has_person_modif) {
        this.utilisateur_has_person_modif = utilisateur_has_person_modif;
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


}

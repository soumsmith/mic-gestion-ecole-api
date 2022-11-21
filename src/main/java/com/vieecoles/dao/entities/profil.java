package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class profil extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  profilid ;
    private  String profilcode;
    private  String profil_libelle;


    public Long getProfilid() {
        return profilid;
    }

    public void setProfilid(Long profilid) {
        this.profilid = profilid;
    }

    public String getProfilcode() {
        return profilcode;
    }

    public void setProfilcode(String profilcode) {
        this.profilcode = profilcode;
    }

    public String getProfil_libelle() {
        return profil_libelle;
    }

    public void setProfil_libelle(String profil_libelle) {
        this.profil_libelle = profil_libelle;
    }
}

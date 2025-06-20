package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "groupe_ecole")
public class groupe_ecole extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  groupe_ecoleid ;
    private  String groupe_ecolecode;
    private  String groupe_ecolelibelle;


    public Long getGroupe_ecoleeid() {
        return groupe_ecoleid;
    }

    public void setGroupe_ecoleeid(Long groupe_ecoleeid) {
        this.groupe_ecoleid = groupe_ecoleeid;
    }

    public String getGroupe_ecolecode() {
        return groupe_ecolecode;
    }

    public void setGroupe_ecolecode(String groupe_ecolecode) {
        this.groupe_ecolecode = groupe_ecolecode;
    }

    public String getGroupe_ecolelibelle() {
        return groupe_ecolelibelle;
    }

    public void setGroupe_ecolelibelle(String groupe_ecolelibelle) {
        this.groupe_ecolelibelle = groupe_ecolelibelle;
    }
}

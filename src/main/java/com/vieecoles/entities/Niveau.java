package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "niveau")
public class Niveau extends PanacheEntityBase {
    @Id @GeneratedValue
    private String  niveauid ;
    private  String niveaucode;
    private  String niveaulibelle;


    public String  getNiveauid() {
        return niveauid;
    }

    public void setNiveauid(String niveauid) {
        this.niveauid = niveauid;
    }

    public String getNiveaucode() {
        return niveaucode;
    }

    public void setNiveaucode(String niveaucode) {
        this.niveaucode = niveaucode;
    }

    public String getNiveaulibelle() {
        return niveaulibelle;
    }

    public void setNiveaulibelle(String niveaulibelle) {
        this.niveaulibelle = niveaulibelle;
    }
}

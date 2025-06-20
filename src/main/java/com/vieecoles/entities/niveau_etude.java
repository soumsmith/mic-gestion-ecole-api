package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "niveau_etude")
public class niveau_etude extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  niveau_etudeid ;
    private  String niveau_etude_code;
    private  String niveau_etude_libelle;

    public Long getNiveau_etudeid() {
        return niveau_etudeid;
    }

    public void setNiveau_etudeid(Long niveau_etudeid) {
        this.niveau_etudeid = niveau_etudeid;
    }

    public String getNiveau_etude_code() {
        return niveau_etude_code;
    }

    public void setNiveau_etude_code(String niveau_etude_code) {
        this.niveau_etude_code = niveau_etude_code;
    }

    public String getNiveau_etude_libelle() {
        return niveau_etude_libelle;
    }

    public void setNiveau_etude_libelle(String niveau_etude_libelle) {
        this.niveau_etude_libelle = niveau_etude_libelle;
    }

    @Override
    public String toString() {
        return "niveau_etude{" +
                "niveau_etudeid=" + niveau_etudeid +
                ", niveau_etude_code='" + niveau_etude_code + '\'' +
                ", niveau_etude_libelle='" + niveau_etude_libelle + '\'' +
                '}';
    }
}

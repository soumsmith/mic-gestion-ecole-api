package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "annee_scolaire")
public class AnneeScolaire extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 private  Long   annee_scolaireid ;
  private  String  annee_scolaire_libelle ;
 private  String   annee_scolaire_code ;
 private  String annee_scolaire_visible ;

    public AnneeScolaire() {
    }

    @Override
    public String toString() {
        return "Annee_scolaire{" +
                "annee_scolaireid=" + annee_scolaireid +
                ", annee_scolaire_libelle='" + annee_scolaire_libelle + '\'' +
                ", annee_scolaire_code='" + annee_scolaire_code + '\'' +
                ", annee_scolaire_visible='" + annee_scolaire_visible + '\'' +
                '}';
    }

    public Long getAnnee_scolaireid() {
        return annee_scolaireid;
    }

    public void setAnnee_scolaireid(Long annee_scolaireid) {
        this.annee_scolaireid = annee_scolaireid;
    }

    public String getAnnee_scolaire_visible() {
        return annee_scolaire_visible;
    }

    public void setAnnee_scolaire_visible(String annee_scolaire_visible) {
        this.annee_scolaire_visible = annee_scolaire_visible;
    }

    public String getAnnee_scolaire_libelle() {
        return annee_scolaire_libelle;
    }

    public void setAnnee_scolaire_libelle(String annee_scolaire_libelle) {
        this.annee_scolaire_libelle = annee_scolaire_libelle;
    }

    public String getAnnee_scolaire_code() {
        return annee_scolaire_code;
    }

    public void setAnnee_scolaire_code(String annee_scolaire_code) {
        this.annee_scolaire_code = annee_scolaire_code;
    }
}

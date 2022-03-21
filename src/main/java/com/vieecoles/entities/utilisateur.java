package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class utilisateur extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  utilisateurid ;
    private  String utilisateurcode;
    private  String utilisateurlibelle;

    public Long getUtilisateurid() {
        return utilisateurid;
    }

    public void setUtilisateurid(Long utilisateurid) {
        this.utilisateurid = utilisateurid;
    }

    public String getUtilisateurcode() {
        return utilisateurcode;
    }

    public void setUtilisateurcode(String utilisateurcode) {
        this.utilisateurcode = utilisateurcode;
    }

    public String getUtilisateurlibelle() {
        return utilisateurlibelle;
    }

    public void setUtilisateurlibelle(String utilisateurlibelle) {
        this.utilisateurlibelle = utilisateurlibelle;
    }
}

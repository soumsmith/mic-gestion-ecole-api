package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class domaine_formation extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long  domaine_formationid ;
    private  String domaine_formation_code;
    private  String domaine_formation_libelle;

    public Long getDomaine_formationid() {
        return domaine_formationid;
    }

    public void setDomaine_formationid(Long domaine_formationid) {
        this.domaine_formationid = domaine_formationid;
    }

    public String getDomaine_formation_code() {
        return domaine_formation_code;
    }

    public void setDomaine_formation_code(String domaine_formation_code) {
        this.domaine_formation_code = domaine_formation_code;
    }

    public String getDomaine_formation_libelle() {
        return domaine_formation_libelle;
    }

    public void setDomaine_formation_libelle(String domaine_formation_libelle) {
        this.domaine_formation_libelle = domaine_formation_libelle;
    }
}

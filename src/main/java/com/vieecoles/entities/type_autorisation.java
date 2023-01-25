package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class type_autorisation extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  idtype_autorisationid ;
    private  String type_autorisation_code;
    private  String type_autorisation_libelle;

    public Long getIdtype_autorisationid() {
        return idtype_autorisationid;
    }

    public void setIdtype_autorisationid(Long idtype_autorisationid) {
        this.idtype_autorisationid = idtype_autorisationid;
    }

    public String getType_autorisation_code() {
        return type_autorisation_code;
    }

    public void setType_autorisation_code(String type_autorisation_code) {
        this.type_autorisation_code = type_autorisation_code;
    }

    public String getType_autorisation_libelle() {
        return type_autorisation_libelle;
    }

    public void setType_autorisation_libelle(String type_autorisation_libelle) {
        this.type_autorisation_libelle = type_autorisation_libelle;
    }
}

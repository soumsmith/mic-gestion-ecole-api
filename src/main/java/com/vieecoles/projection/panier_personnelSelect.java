package com.vieecoles.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class panier_personnelDto extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  idpanier_personnel_id ;
   private  String personnelcode;
    private  LocalDate  panier_personnel_date_creation;
    private  LocalDate  panier_personnel_date_modifier;

    private Long identifiant_ecole ;
    private Long identifiant_personnel;


    public Long getIdpanier_personnel_id() {
        return idpanier_personnel_id;
    }

    public void setIdpanier_personnel_id(Long idpanier_personnel_id) {
        this.idpanier_personnel_id = idpanier_personnel_id;
    }

    public String getPersonnelcode() {
        return personnelcode;
    }

    public void setPersonnelcode(String personnelcode) {
        this.personnelcode = personnelcode;
    }

    public LocalDate getPanier_personnel_date_creation() {
        return panier_personnel_date_creation;
    }

    public void setPanier_personnel_date_creation(LocalDate panier_personnel_date_creation) {
        this.panier_personnel_date_creation = panier_personnel_date_creation;
    }

    public LocalDate getPanier_personnel_date_modifier() {
        return panier_personnel_date_modifier;
    }

    public void setPanier_personnel_date_modifier(LocalDate panier_personnel_date_modifier) {
        this.panier_personnel_date_modifier = panier_personnel_date_modifier;
    }



    public Long getIdentifiant_ecole() {
        return identifiant_ecole;
    }

    public void setIdentifiant_ecole(Long identifiant_ecole) {
        this.identifiant_ecole = identifiant_ecole;
    }

    public Long getIdentifiant_personnel() {
        return identifiant_personnel;
    }

    public void setIdentifiant_personnel(Long identifiant_personnel) {
        this.identifiant_personnel = identifiant_personnel;
    }

    public panier_personnelDto() {
    }

    @Override
    public String toString() {
        return "panier_personnelDto{" +
                "idpanier_personnel_id=" + idpanier_personnel_id +
                ", personnelcode='" + personnelcode + '\'' +
                ", panier_personnel_date_creation=" + panier_personnel_date_creation +
                ", panier_personnel_date_modifier=" + panier_personnel_date_modifier +
                ", identifiant_ecole=" + identifiant_ecole +
                ", identifiant_personnel=" + identifiant_personnel +
                '}';
    }
}

package com.vieecoles.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import java.time.LocalDate;


@ApplicationScoped
public class panier_personnelDto {
    private  LocalDate  panier_personnel_date_creation;
    private Long identifiant_ecole ;
    private Long identifiant_personnel;



    public LocalDate getPanier_personnel_date_creation() {
        return panier_personnel_date_creation;
    }

    public void setPanier_personnel_date_creation(LocalDate panier_personnel_date_creation) {
        this.panier_personnel_date_creation = panier_personnel_date_creation;
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
                "panier_personnel_date_creation=" + panier_personnel_date_creation +
                ", identifiant_ecole=" + identifiant_ecole +
                ", identifiant_personnel=" + identifiant_personnel +
                '}';
    }
}

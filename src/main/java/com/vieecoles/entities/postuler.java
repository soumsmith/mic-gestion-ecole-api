package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class postuler extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id ;
    private  String code;
    private LocalDate date_creation;
    private  Long sous_attent_personn_sous_attent_personnid ;
    private  Long publication_id ;
    public postuler() {
    }

    public Long getSous_attent_personn_sous_attent_personnid() {
        return sous_attent_personn_sous_attent_personnid;
    }

    public void setSous_attent_personn_sous_attent_personnid(Long sous_attent_personn_sous_attent_personnid) {
        this.sous_attent_personn_sous_attent_personnid = sous_attent_personn_sous_attent_personnid;
    }

    public Long getPublication_id() {
        return publication_id;
    }

    public void setPublication_id(Long publication_id) {
        this.publication_id = publication_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public LocalDate getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(LocalDate date_creation) {
        this.date_creation = date_creation;
    }



}

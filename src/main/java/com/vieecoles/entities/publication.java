package com.vieecoles.entities;

import com.vieecoles.entities.operations.ville;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class publication extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id ;
    private  String code;
    private  String libelle;
    private LocalDate date_creation;
    private  LocalDate date_modification;
    private Long ecole_ecoleid ;
    private Long type_offre_id ;
    private String experience ;
    private String lieu ;
    private  LocalDate date_limite ;
    private  Long fonction_fonctionid ;
    private String statut ;

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getLieu() {
        return lieu;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public LocalDate getDate_limite() {
        return date_limite;
    }

    public void setDate_limite(LocalDate date_limite) {
        this.date_limite = date_limite;
    }

    public Long getFonction_fonctionid() {
        return fonction_fonctionid;
    }

    public void setFonction_fonctionid(Long fonction_fonctionid) {
        this.fonction_fonctionid = fonction_fonctionid;
    }

    public publication() {
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

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public LocalDate getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(LocalDate date_creation) {
        this.date_creation = date_creation;
    }

    public LocalDate getDate_modification() {
        return date_modification;
    }

    public void setDate_modification(LocalDate date_modification) {
        this.date_modification = date_modification;
    }

    public Long getEcole_ecoleid() {
        return ecole_ecoleid;
    }

    public void setEcole_ecoleid(Long ecole_ecoleid) {
        this.ecole_ecoleid = ecole_ecoleid;
    }

    public Long getType_offre_id() {
        return type_offre_id;
    }

    public void setType_offre_id(Long type_offre_id) {
        this.type_offre_id = type_offre_id;
    }
}

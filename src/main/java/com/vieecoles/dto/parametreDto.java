package com.vieecoles.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.awt.image.BufferedImage;


public class parametreDto {

    private Long  idparametre ;


    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length=100000)
    private byte[] filigramme ;
    private  String libelle;

    public byte[] getFiligramme() {
        return filigramme;
    }

    public void setFiligramme(byte[] filigramme) {
        this.filigramme = filigramme;
    }

    public Long getIdparametre() {
        return idparametre;
    }

    public void setIdparametre(Long idparametre) {
        this.idparametre = idparametre;
    }


    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}

package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
public class parametre extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  idparametre ;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length=100000)
    private byte[] image ;
    private  String libelle;

    public Long getIdparametre() {
        return idparametre;
    }

    public void setIdparametre(Long idparametre) {
        this.idparametre = idparametre;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}

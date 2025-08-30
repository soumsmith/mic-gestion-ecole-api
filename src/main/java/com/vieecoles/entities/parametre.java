package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.*;

@Entity
@Table(name = "parametre")
public class parametre extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  idparametre ;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length=100000)
    private byte[] image ;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length=100000)
    private byte[] filigramme ;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length=100000)
    private byte[] cadre_tableau_honneur ;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length=100000)
    private byte[] image_test ;
    private  String libelle;

    public byte[] getCadre_tableau_honneur() {
        return cadre_tableau_honneur;
    }

    public byte[] getImage_test() {
        return image_test;
    }

    public void setImage_test(byte[] image_test) {
        this.image_test = image_test;
    }

    public void setCadre_tableau_honneur(byte[] cadre_tableau_honneur) {
        this.cadre_tableau_honneur = cadre_tableau_honneur;
    }

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

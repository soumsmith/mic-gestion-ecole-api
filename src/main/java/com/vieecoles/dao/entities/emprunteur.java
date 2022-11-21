package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class emprunteur extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  emprunteurid ;
    private  String emprunteurcode;
    private  String emprunteurnom;
    private  String emprunteurprenom;
    private LocalDate emprunteurdatenaissance;


    public Long getEmprunteurid() {
        return emprunteurid;
    }

    public void setEmprunteurid(Long emprunteurid) {
        this.emprunteurid = emprunteurid;
    }

    public String getEmprunteurcode() {
        return emprunteurcode;
    }

    public void setEmprunteurcode(String emprunteurcode) {
        this.emprunteurcode = emprunteurcode;
    }

    public String getEmprunteurnom() {
        return emprunteurnom;
    }

    public void setEmprunteurnom(String emprunteurnom) {
        this.emprunteurnom = emprunteurnom;
    }

    public String getEmprunteurprenom() {
        return emprunteurprenom;
    }

    public void setEmprunteurprenom(String emprunteurprenom) {
        this.emprunteurprenom = emprunteurprenom;
    }

    public LocalDate getEmprunteurdatenaissance() {
        return emprunteurdatenaissance;
    }

    public void setEmprunteurdatenaissance(LocalDate emprunteurdatenaissance) {
        this.emprunteurdatenaissance = emprunteurdatenaissance;
    }
}

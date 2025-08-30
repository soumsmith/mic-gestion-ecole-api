package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "emprunteur")
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

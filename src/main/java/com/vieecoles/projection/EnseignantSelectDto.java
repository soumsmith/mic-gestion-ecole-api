package com.vieecoles.projection;

import java.time.LocalDate;

public class EnseignantSelectDto {
    private Long id ;
    private String nom ;
    private String prenoms ;
    private LocalDate dateNaiss ;
    private String diplome ;
    private String contact ;
    private String statutVacPer ;

    public EnseignantSelectDto() {
    }

    public EnseignantSelectDto(Long id, String nom, String prenoms, LocalDate dateNaiss, String diplome, String contact, String statutVacPer) {
        this.id = id;
        this.nom = nom;
        this.prenoms = prenoms;
        this.dateNaiss = dateNaiss;
        this.diplome = diplome;
        this.contact = contact;
        this.statutVacPer = statutVacPer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public LocalDate getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(LocalDate dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getStatutVacPer() {
        return statutVacPer;
    }

    public void setStatutVacPer(String statutVacPer) {
        this.statutVacPer = statutVacPer;
    }
}

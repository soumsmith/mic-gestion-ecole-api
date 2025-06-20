package com.vieecoles.dto;

import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;

@ApplicationScoped
public class EleveInscritClasseDto {
    String eleve_matricule ;
    String elevenom;
    String eleveprenom ;
    String  eleve_sexe ;
    String elevecellulaire ;
    LocalDate elevedate_naissance ;
    String classelibelle ;
    String inscriptions_statut_eleve ;


    public EleveInscritClasseDto(String eleve_matricule, String elevenom, String eleveprenom,
                                 String eleve_sexe, String elevecellulaire,
                                 LocalDate elevedate_naissance, String classelibelle,
                                 String inscriptions_statut_eleve) {
        this.eleve_matricule = eleve_matricule;
        this.elevenom = elevenom;
        this.eleveprenom = eleveprenom;
        this.eleve_sexe = eleve_sexe;
        this.elevecellulaire = elevecellulaire;
        this.elevedate_naissance = elevedate_naissance;
        this.classelibelle = classelibelle;
        this.inscriptions_statut_eleve = inscriptions_statut_eleve;
    }

    public EleveInscritClasseDto() {
    }

    public String getEleve_matricule() {
        return eleve_matricule;
    }

    public void setEleve_matricule(String eleve_matricule) {
        this.eleve_matricule = eleve_matricule;
    }

    public String getElevenom() {
        return elevenom;
    }

    public void setElevenom(String elevenom) {
        this.elevenom = elevenom;
    }

    public String getEleveprenom() {
        return eleveprenom;
    }

    public void setEleveprenom(String eleveprenom) {
        this.eleveprenom = eleveprenom;
    }

    public String getEleve_sexe() {
        return eleve_sexe;
    }

    public void setEleve_sexe(String eleve_sexe) {
        this.eleve_sexe = eleve_sexe;
    }

    public String getElevecellulaire() {
        return elevecellulaire;
    }

    public void setElevecellulaire(String elevecellulaire) {
        this.elevecellulaire = elevecellulaire;
    }

    public LocalDate getElevedate_naissance() {
        return elevedate_naissance;
    }

    public void setElevedate_naissance(LocalDate elevedate_naissance) {
        this.elevedate_naissance = elevedate_naissance;
    }

    public String getClasselibelle() {
        return classelibelle;
    }

    public void setClasselibelle(String classelibelle) {
        this.classelibelle = classelibelle;
    }

    public String getInscriptions_statut_eleve() {
        return inscriptions_statut_eleve;
    }

    public void setInscriptions_statut_eleve(String inscriptions_statut_eleve) {
        this.inscriptions_statut_eleve = inscriptions_statut_eleve;
    }
}

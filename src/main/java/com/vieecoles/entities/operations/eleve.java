package com.vieecoles.entities.operations;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

import com.vieecoles.entities.Parent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class eleve extends PanacheEntityBase {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private  Long    eleveid ;
  private  String  elevecode ;
    private  String  elevenom ;
    private  String eleveprenom ;
    private LocalDate elevedate_naissance ;
    private  String  elevelieu_naissance ;
    private  String eleve_numero_extrait_naiss ;
    private  LocalDate  elevedate_etabli_extrait_naiss ;
    private  String  elevelieu_etabliss_etrait_naissance ;
    private  String eleveadresse ;
    private  String  elevecellulaire ;
    private  String  eleve_mail ;
    private  String eleve_sexe ;
    private  String eleve_matricule ;
    private  String eleve_nationalite ;

   // @JsonbTransient



   // @JsonbTransient
    @ManyToMany
    @JoinTable( name = "eleve_parent",
            joinColumns = @JoinColumn( name = "eleve_eleveid" ),
            inverseJoinColumns = @JoinColumn( name = "parent_parentid" ) )
    private List<Parent> parents = new ArrayList<>();






 // @JsonbTransient
@ManyToMany
  @JoinTable( name = "eleve_appel_numerique",
          joinColumns = @JoinColumn( name = "eleve_eleveid" ),
          inverseJoinColumns = @JoinColumn( name = "appel_numerique_appel_numeriqueid" ) )
  private List<appel_numerique> appel_numerique = new ArrayList<>();
    public enum sexeEleve{
        MASCULIN, FEMININ
    }
    @Override
    public String toString() {
        return "eleve{" +
                "eleveid=" + eleveid +
                ", elevecode='" + elevecode + '\'' +
                ", elevenom='" + elevenom + '\'' +
                ", eleveprenom='" + eleveprenom + '\'' +
                ", elevedate_naissance=" + elevedate_naissance +
                ", elevelieu_naissance='" + elevelieu_naissance + '\'' +
                ", eleve_numero_extrait_naiss=" + eleve_numero_extrait_naiss +
                ", elevedate_etabli_extrait_naiss=" + elevedate_etabli_extrait_naiss +
                ", elevelieu_etabliss_etrait_naissance='" + elevelieu_etabliss_etrait_naissance + '\'' +
                ", eleveadresse='" + eleveadresse + '\'' +
                ", elevecellulaire='" + elevecellulaire + '\'' +
                ", eleve_mail='" + eleve_mail + '\'' +
                 ", parents=" + parents +
                ", appel_numerique=" + appel_numerique +
                '}';
    }

    public String getEleve_sexe() {
        return eleve_sexe;
    }

    public void setEleve_sexe(String eleve_sexe) {
        this.eleve_sexe = eleve_sexe;
    }


    public List<com.vieecoles.entities.operations.appel_numerique> getAppel_numerique() {
        return appel_numerique;
    }

    public void setAppel_numerique(List<com.vieecoles.entities.operations.appel_numerique> appel_numerique) {
        this.appel_numerique = appel_numerique;
    }

    public eleve() {
    }

    public String getEleve_nationalite() {
        return eleve_nationalite;
    }

    public void setEleve_nationalite(String eleve_nationalite) {
        this.eleve_nationalite = eleve_nationalite;
    }

    public String getEleve_matricule() {
        return eleve_matricule;
    }

    public void setEleve_matricule(String eleve_matricule) {
        this.eleve_matricule = eleve_matricule;
    }

    public Long getEleveid() {
        return eleveid;
    }

    public void setEleveid(Long eleveid) {
        this.eleveid = eleveid;
    }

    public String getElevecode() {
        return elevecode;
    }

    public void setElevecode(String elevecode) {
        this.elevecode = elevecode;
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

    public LocalDate getElevedate_naissance() {
        return elevedate_naissance;
    }

    public void setElevedate_naissance(LocalDate elevedate_naissance) {
        this.elevedate_naissance = elevedate_naissance;
    }

    public String getElevelieu_naissance() {
        return elevelieu_naissance;
    }

    public void setElevelieu_naissance(String elevelieu_naissance) {
        this.elevelieu_naissance = elevelieu_naissance;
    }

    public String getEleve_numero_extrait_naiss() {
        return eleve_numero_extrait_naiss;
    }

    public void setEleve_numero_extrait_naiss(String eleve_numero_extrait_naiss) {
        this.eleve_numero_extrait_naiss = eleve_numero_extrait_naiss;
    }

    public LocalDate getElevedate_etabli_extrait_naiss() {
        return elevedate_etabli_extrait_naiss;
    }

    public void setElevedate_etabli_extrait_naiss(LocalDate elevedate_etabli_extrait_naiss) {
        this.elevedate_etabli_extrait_naiss = elevedate_etabli_extrait_naiss;
    }

    public String getElevelieu_etabliss_etrait_naissance() {
        return elevelieu_etabliss_etrait_naissance;
    }

    public void setElevelieu_etabliss_etrait_naissance(String elevelieu_etabliss_etrait_naissance) {
        this.elevelieu_etabliss_etrait_naissance = elevelieu_etabliss_etrait_naissance;
    }

    public String getEleveadresse() {
        return eleveadresse;
    }

    public void setEleveadresse(String eleveadresse) {
        this.eleveadresse = eleveadresse;
    }

    public String getElevecellulaire() {
        return elevecellulaire;
    }

    public void setElevecellulaire(String elevecellulaire) {
        this.elevecellulaire = elevecellulaire;
    }

    public String getEleve_mail() {
        return eleve_mail;
    }

    public void setEleve_mail(String eleve_mail) {
        this.eleve_mail = eleve_mail;
    }


    public List<Parent> getParents() {
        return parents;
    }

    public void setParents(List<Parent> parents) {
        this.parents = parents;
    }
}

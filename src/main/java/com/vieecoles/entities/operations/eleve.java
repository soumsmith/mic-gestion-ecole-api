package com.vieecoles.entities.operations;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class eleve extends PanacheEntityBase {
 @Id @GeneratedValue
 private  Integer    eleveid ;
  private  String  elevecode ;
    private  String  elevenom ;
    private  String eleveprenom ;
    private LocalDate elevedate_naissance ;
    private  String  elevelieu_naissance ;
    private  Integer eleve_numero_extrait_naiss ;
    private  LocalDate  elevedate_etabli_extrait_naiss ;
    private  String  elevelieu_etabliss_etrait_naissance ;
    private  String eleveadresse ;
    private  String  elevecellulaire ;
    private  String  eleve_mail ;

    @JsonbTransient
    @ManyToMany
    @JoinTable( name = "eleve_classe",
            joinColumns = @JoinColumn( name = "eleve_eleveid" ),
            inverseJoinColumns = @JoinColumn( name = "classe_classeid" ) )
    private List<classe> classe = new ArrayList<>();


    @JsonbTransient
    @ManyToMany
    @JoinTable( name = "eleve_parent",
            joinColumns = @JoinColumn( name = "eleve_eleveid" ),
            inverseJoinColumns = @JoinColumn( name = "parent_parentid" ) )
    private List<parent> parents = new ArrayList<>();


  @JsonbTransient
  @ManyToMany
  @JoinTable( name = "eleve_appel_numerique",
          joinColumns = @JoinColumn( name = "eleve_eleveid" ),
          inverseJoinColumns = @JoinColumn( name = "appel_numerique_appel_numeriqueid" ) )
  private List<appel_numerique> appel_numerique = new ArrayList<>();

    @OneToMany
    private List<details_infos_eleves> details_infos_eleves = new ArrayList<>();

    public eleve() {
    }

    public Integer getEleveid() {
        return eleveid;
    }

    public void setEleveid(Integer eleveid) {
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

    public Integer getEleve_numero_extrait_naiss() {
        return eleve_numero_extrait_naiss;
    }

    public void setEleve_numero_extrait_naiss(Integer eleve_numero_extrait_naiss) {
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

    public List<com.vieecoles.entities.operations.classe> getClasse() {
        return classe;
    }

    public void setClasse(List<com.vieecoles.entities.operations.classe> classe) {
        this.classe = classe;
    }

    public List<parent> getParents() {
        return parents;
    }

    public void setParents(List<parent> parents) {
        this.parents = parents;
    }

    public List<com.vieecoles.entities.operations.appel_numerique> getAppel_numerique() {
        return appel_numerique;
    }

    public void setAppel_numerique(List<com.vieecoles.entities.operations.appel_numerique> appel_numerique) {
        this.appel_numerique = appel_numerique;
    }

    public List<com.vieecoles.entities.operations.details_infos_eleves> getDetails_infos_eleves() {
        return details_infos_eleves;
    }

    public void setDetails_infos_eleves(List<com.vieecoles.entities.operations.details_infos_eleves> details_infos_eleves) {
        this.details_infos_eleves = details_infos_eleves;
    }
}

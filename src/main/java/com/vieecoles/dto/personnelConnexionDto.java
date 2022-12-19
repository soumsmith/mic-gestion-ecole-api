package com.vieecoles.dto;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import java.time.LocalDate;


public class personnelDto  {

    private Long  personnelid ;
   private  String personnelcode;
    private  String personnelnom;
    private  String personnelprenom;
    private LocalDate personneldatenaissance;
    private  String personnel_lieunaissance;
    private  Long identifiant_type_personnel ;
    private  Long identifiant_personnelStatut ;
    private  Long identifiant_fonction ;

    public Long getIdentifiant_fonction() {
        return identifiant_fonction;
    }

    public void setIdentifiant_fonction(Long identifiant_fonction) {
        this.identifiant_fonction = identifiant_fonction;
    }

    public Long getPersonnelid() {
        return personnelid;
    }

    public void setPersonnelid(Long personnelid) {
        this.personnelid = personnelid;
    }

    public String getPersonnelcode() {
        return personnelcode;
    }

    public void setPersonnelcode(String personnelcode) {
        this.personnelcode = personnelcode;
    }

    public String getPersonnelnom() {
        return personnelnom;
    }

    public void setPersonnelnom(String personnelnom) {
        this.personnelnom = personnelnom;
    }

    public String getPersonnelprenom() {
        return personnelprenom;
    }

    public void setPersonnelprenom(String personnelprenom) {
        this.personnelprenom = personnelprenom;
    }

    public LocalDate getPersonneldatenaissance() {
        return personneldatenaissance;
    }

    public void setPersonneldatenaissance(LocalDate personneldatenaissance) {
        this.personneldatenaissance = personneldatenaissance;
    }

    public String getPersonnel_lieunaissance() {
        return personnel_lieunaissance;
    }

    public void setPersonnel_lieunaissance(String personnel_lieunaissance) {
        this.personnel_lieunaissance = personnel_lieunaissance;
    }

    public Long getIdentifiant_type_personnel() {
        return identifiant_type_personnel;
    }

    public void setIdentifiant_type_personnel(Long identifiant_type_personnel) {
        this.identifiant_type_personnel = identifiant_type_personnel;
    }

    public Long getIdentifiant_personnelStatut() {
        return identifiant_personnelStatut;
    }

    public void setIdentifiant_personnelStatut(Long identifiant_personnelStatut) {
        this.identifiant_personnelStatut = identifiant_personnelStatut;
    }
}

package com.vieecoles.projection;


import java.time.LocalDate;


public class personnelSelect {

    private Long  personnelid ;
   private  String personnelcode;
    private  String personnelnom;
    private  String personnelprenom;
    private LocalDate personneldatenaissance;
    private  String personnel_lieunaissance;
    private  String type_personnel_liblle ;
    private  String personnelStatut_liblle ;
    private  String fonction_liblle ;



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

    public String getType_personnel_liblle() {
        return type_personnel_liblle;
    }

    public void setType_personnel_liblle(String type_personnel_liblle) {
        this.type_personnel_liblle = type_personnel_liblle;
    }

    public String getPersonnelStatut_liblle() {
        return personnelStatut_liblle;
    }

    public void setPersonnelStatut_liblle(String personnelStatut_liblle) {
        this.personnelStatut_liblle = personnelStatut_liblle;
    }

    public String getFonction_liblle() {
        return fonction_liblle;
    }

    public void setFonction_liblle(String fonction_liblle) {
        this.fonction_liblle = fonction_liblle;
    }

    public personnelSelect(Long personnelid, String personnelcode, String personnelnom, String personnelprenom, LocalDate personneldatenaissance, String personnel_lieunaissance, String type_personnel_liblle, String personnelStatut_liblle, String fonction_liblle) {
        this.personnelid = personnelid;
        this.personnelcode = personnelcode;
        this.personnelnom = personnelnom;
        this.personnelprenom = personnelprenom;
        this.personneldatenaissance = personneldatenaissance;
        this.personnel_lieunaissance = personnel_lieunaissance;
        this.type_personnel_liblle = type_personnel_liblle;
        this.personnelStatut_liblle = personnelStatut_liblle;
        this.fonction_liblle = fonction_liblle;
    }
}

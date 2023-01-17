package com.vieecoles.projection;


import java.time.LocalDate;


public class compteUtilisateurSelect {

    private  Long   personnel_personnelid ;
    private  Long   utilisateur_has_personid ;
    private LocalDate utilisateur_has_person_date_creation ;
    private LocalDate utilisateur_has_person_modif ;
    private  int utilisateur_has_person_active ;
    private LocalDate utilisateur_has_person_date_debut ;
    private  LocalDate utilisateur_has_person_date_fin ;
    private Long  ecole_ecoleid ;
    private  String libelleEcole ;
    private  String libelleProfiil ;
    private  String nomPersonnel ;
    private  String nomPrenomPersonnel ;

    public compteUtilisateurSelect(LocalDate utilisateur_has_person_date_creation,
                                   LocalDate utilisateur_has_person_modif,
                                   int utilisateur_has_person_active,
                                   LocalDate utilisateur_has_person_date_debut,
                                   LocalDate utilisateur_has_person_date_fin,
                                   String libelleEcole,
                                   String libelleProfiil,
                                   String nomPersonnel,
                                   String nomPrenomPersonnel) {
        this.utilisateur_has_person_date_creation = utilisateur_has_person_date_creation;
        this.utilisateur_has_person_modif = utilisateur_has_person_modif;
        this.utilisateur_has_person_active = utilisateur_has_person_active;
        this.utilisateur_has_person_date_debut = utilisateur_has_person_date_debut;
        this.utilisateur_has_person_date_fin = utilisateur_has_person_date_fin;
        this.libelleEcole = libelleEcole;
        this.libelleProfiil = libelleProfiil;
        this.nomPersonnel = nomPersonnel;
        this.nomPrenomPersonnel = nomPrenomPersonnel;
    }

    public Long getPersonnel_personnelid() {
        return personnel_personnelid;
    }

    public void setPersonnel_personnelid(Long personnel_personnelid) {
        this.personnel_personnelid = personnel_personnelid;
    }

    public Long getUtilisateur_has_personid() {
        return utilisateur_has_personid;
    }

    public void setUtilisateur_has_personid(Long utilisateur_has_personid) {
        this.utilisateur_has_personid = utilisateur_has_personid;
    }

    public LocalDate getUtilisateur_has_person_date_creation() {
        return utilisateur_has_person_date_creation;
    }

    public void setUtilisateur_has_person_date_creation(LocalDate utilisateur_has_person_date_creation) {
        this.utilisateur_has_person_date_creation = utilisateur_has_person_date_creation;
    }

    public LocalDate getUtilisateur_has_person_modif() {
        return utilisateur_has_person_modif;
    }

    public void setUtilisateur_has_person_modif(LocalDate utilisateur_has_person_modif) {
        this.utilisateur_has_person_modif = utilisateur_has_person_modif;
    }

    public int getUtilisateur_has_person_active() {
        return utilisateur_has_person_active;
    }

    public void setUtilisateur_has_person_active(int utilisateur_has_person_active) {
        this.utilisateur_has_person_active = utilisateur_has_person_active;
    }

    public LocalDate getUtilisateur_has_person_date_debut() {
        return utilisateur_has_person_date_debut;
    }

    public void setUtilisateur_has_person_date_debut(LocalDate utilisateur_has_person_date_debut) {
        this.utilisateur_has_person_date_debut = utilisateur_has_person_date_debut;
    }

    public LocalDate getUtilisateur_has_person_date_fin() {
        return utilisateur_has_person_date_fin;
    }

    public void setUtilisateur_has_person_date_fin(LocalDate utilisateur_has_person_date_fin) {
        this.utilisateur_has_person_date_fin = utilisateur_has_person_date_fin;
    }

    public Long getEcole_ecoleid() {
        return ecole_ecoleid;
    }

    public void setEcole_ecoleid(Long ecole_ecoleid) {
        this.ecole_ecoleid = ecole_ecoleid;
    }

    public String getLibelleEcole() {
        return libelleEcole;
    }

    public void setLibelleEcole(String libelleEcole) {
        this.libelleEcole = libelleEcole;
    }

    public String getLibelleProfiil() {
        return libelleProfiil;
    }

    public void setLibelleProfiil(String libelleProfiil) {
        this.libelleProfiil = libelleProfiil;
    }

    public String getNomPersonnel() {
        return nomPersonnel;
    }

    public void setNomPersonnel(String nomPersonnel) {
        this.nomPersonnel = nomPersonnel;
    }

    public String getNomPrenomPersonnel() {
        return nomPrenomPersonnel;
    }

    public void setNomPrenomPersonnel(String nomPrenomPersonnel) {
        this.nomPrenomPersonnel = nomPrenomPersonnel;
    }
}

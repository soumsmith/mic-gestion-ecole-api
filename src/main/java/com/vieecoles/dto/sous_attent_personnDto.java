package com.vieecoles.dto;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDate;


public class sous_attent_personnDto  {

    private Long  sous_attent_personnid ;
   private  String sous_attent_personn_nom;
    private  String sous_attent_personn_prenom;
    private  String sous_attent_personn_email;
    private String  sous_attent_personn_sexe;
    private LocalDate sous_attent_personn_date_naissance;
    private  String sous_attent_personn_diplome_recent ;
    private  Integer  sous_attent_personn_nbre_annee_experience ;
    private  String sous_attent_personn_lien_cv  ;
    private  String sous_attent_personncode ;
    Long identifiantdomaine_formation ;

    Long  niveau_etudeIdentifiant;
   Long   fonctionidentifiant ;
   Long type_autorisation_idtype_autorisationid ;
   String  sous_attent_personn_donnee ;

    private  String sous_attent_personn_contact ;
    private  String sous_attent_personn_lien_piece ;
    private  String sous_attent_personn_lien_autorisation ;
    private  String sous_attent_personn_statut ;

    public Long getSous_attent_personnid() {
        return sous_attent_personnid;
    }

    public void setSous_attent_personnid(Long sous_attent_personnid) {
        this.sous_attent_personnid = sous_attent_personnid;
    }

    public Long getType_autorisation_idtype_autorisationid() {
        return type_autorisation_idtype_autorisationid;
    }

    public void setType_autorisation_idtype_autorisationid(Long type_autorisation_idtype_autorisationid) {
        this.type_autorisation_idtype_autorisationid = type_autorisation_idtype_autorisationid;
    }

    public Long getFonctionidentifiant() {
        return fonctionidentifiant;
    }

    public void setFonctionidentifiant(Long fonctionidentifiant) {
        this.fonctionidentifiant = fonctionidentifiant;
    }

    public LocalDate getSous_attent_personn_date_naissance() {
        return sous_attent_personn_date_naissance;
    }

    public void setSous_attent_personn_date_naissance(LocalDate sous_attent_personn_date_naissance) {
        this.sous_attent_personn_date_naissance = sous_attent_personn_date_naissance;
    }

    public String getSous_attent_personn_nom() {
        return sous_attent_personn_nom;
    }

    public String getSous_attent_personncode() {
        return sous_attent_personncode;
    }

    public void setSous_attent_personncode(String sous_attent_personncode) {
        this.sous_attent_personncode = sous_attent_personncode;
    }

    public String getSous_attent_personn_statut() {
        return sous_attent_personn_statut;
    }

    public void setSous_attent_personn_statut(String sous_attent_personn_statut) {
        this.sous_attent_personn_statut = sous_attent_personn_statut;
    }

    public void setSous_attent_personn_nom(String sous_attent_personn_nom) {
        this.sous_attent_personn_nom = sous_attent_personn_nom;
    }

    public String getSous_attent_personn_prenom() {
        return sous_attent_personn_prenom;
    }

    public void setSous_attent_personn_prenom(String sous_attent_personn_prenom) {
        this.sous_attent_personn_prenom = sous_attent_personn_prenom;
    }

    public String getSous_attent_personn_email() {
        return sous_attent_personn_email;
    }

    public void setSous_attent_personn_email(String sous_attent_personn_email) {
        this.sous_attent_personn_email = sous_attent_personn_email;
    }

    public String getSous_attent_personn_sexe() {
        return sous_attent_personn_sexe;
    }

    public void setSous_attent_personn_sexe(String sous_attent_personn_sexe) {
        this.sous_attent_personn_sexe = sous_attent_personn_sexe;
    }



    public String getSous_attent_personn_diplome_recent() {
        return sous_attent_personn_diplome_recent;
    }

    public void setSous_attent_personn_diplome_recent(String sous_attent_personn_diplome_recent) {
        this.sous_attent_personn_diplome_recent = sous_attent_personn_diplome_recent;
    }

    public Integer getSous_attent_personn_nbre_annee_experience() {
        return sous_attent_personn_nbre_annee_experience;
    }

    public void setSous_attent_personn_nbre_annee_experience(Integer sous_attent_personn_nbre_annee_experience) {
        this.sous_attent_personn_nbre_annee_experience = sous_attent_personn_nbre_annee_experience;
    }

    public String getSous_attent_personn_lien_cv() {
        return sous_attent_personn_lien_cv;
    }

    public void setSous_attent_personn_lien_cv(String sous_attent_personn_lien_cv) {
        this.sous_attent_personn_lien_cv = sous_attent_personn_lien_cv;
    }

    public Long getIdentifiantdomaine_formation() {
        return identifiantdomaine_formation;
    }

    public void setIdentifiantdomaine_formation(Long identifiantdomaine_formation) {
        this.identifiantdomaine_formation = identifiantdomaine_formation;
    }

    public Long getNiveau_etudeIdentifiant() {
        return niveau_etudeIdentifiant;
    }

    public void setNiveau_etudeIdentifiant(Long niveau_etudeIdentifiant) {
        this.niveau_etudeIdentifiant = niveau_etudeIdentifiant;
    }

    public String getSous_attent_personn_donnee() {
        return sous_attent_personn_donnee;
    }

    public void setSous_attent_personn_donnee(String sous_attent_personn_donnee) {
        this.sous_attent_personn_donnee = sous_attent_personn_donnee;
    }

    public String getSous_attent_personn_contact() {
        return sous_attent_personn_contact;
    }

    public void setSous_attent_personn_contact(String sous_attent_personn_contact) {
        this.sous_attent_personn_contact = sous_attent_personn_contact;
    }

    public String getSous_attent_personn_lien_piece() {
        return sous_attent_personn_lien_piece;
    }

    public void setSous_attent_personn_lien_piece(String sous_attent_personn_lien_piece) {
        this.sous_attent_personn_lien_piece = sous_attent_personn_lien_piece;
    }

    public String getSous_attent_personn_lien_autorisation() {
        return sous_attent_personn_lien_autorisation;
    }

    public void setSous_attent_personn_lien_autorisation(String sous_attent_personn_lien_autorisation) {
        this.sous_attent_personn_lien_autorisation = sous_attent_personn_lien_autorisation;
    }
}

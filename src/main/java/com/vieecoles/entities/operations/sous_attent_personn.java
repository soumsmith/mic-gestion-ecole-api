package com.vieecoles.entities.operations;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class sous_attent_personn extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private  String sous_attent_personn_contact ;
    private  String sous_attent_personn_contact2 ;
    private  String sous_attent_personn_lien_piece ;
    private  String sous_attent_personn_lien_autorisation ;
   private  String sous_attent_personn_statut ;
    private  String sous_attent_personn_motifrefus ;
    private LocalDateTime sous_attent_personn_date_creation ;
    private LocalDateTime sous_attent_personn_date_traitement ;
Long type_autorisation_idtype_autorisationid ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domaine_formation_domaine_formationid")
    private com.vieecoles.entities.domaine_formation domaine_formation ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fonction_fonctionid")
    private com.vieecoles.entities.fonction fonction ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "niveau_etude_niveau_etudeid")
    private com.vieecoles.entities.niveau_etude niveau_etude;

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

    public com.vieecoles.entities.fonction getFonction() {
        return fonction;
    }

    public void setFonction(com.vieecoles.entities.fonction fonction) {
        this.fonction = fonction;
    }

    public LocalDate getSous_attent_personn_date_naissance() {
        return sous_attent_personn_date_naissance;
    }

    public String getSous_attent_personn_contact2() {
        return sous_attent_personn_contact2;
    }

    public void setSous_attent_personn_contact2(String sous_attent_personn_contact2) {
        this.sous_attent_personn_contact2 = sous_attent_personn_contact2;
    }

    public void setSous_attent_personn_date_naissance(LocalDate sous_attent_personn_date_naissance) {
        this.sous_attent_personn_date_naissance = sous_attent_personn_date_naissance;
    }

    public String getSous_attent_personn_motifrefus() {
        return sous_attent_personn_motifrefus;
    }

    public void setSous_attent_personn_motifrefus(String sous_attent_personn_motifrefus) {
        this.sous_attent_personn_motifrefus = sous_attent_personn_motifrefus;
    }

    public LocalDateTime getSous_attent_personn_date_creation() {
        return sous_attent_personn_date_creation;
    }

    public void setSous_attent_personn_date_creation(LocalDateTime sous_attent_personn_date_creation) {
        this.sous_attent_personn_date_creation = sous_attent_personn_date_creation;
    }

    public LocalDateTime getSous_attent_personn_date_traitement() {
        return sous_attent_personn_date_traitement;
    }

    public void setSous_attent_personn_date_traitement(LocalDateTime sous_attent_personn_date_traitement) {
        this.sous_attent_personn_date_traitement = sous_attent_personn_date_traitement;
    }

    public String getSous_attent_personn_statut() {
        return sous_attent_personn_statut;
    }

    public void setSous_attent_personn_statut(String sous_attent_personn_statut) {
        this.sous_attent_personn_statut = sous_attent_personn_statut;
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

    public void setSous_attent_personn_nom(String sous_attent_personn_nom) {
        this.sous_attent_personn_nom = sous_attent_personn_nom;
    }

    public String getSous_attent_personn_prenom() {
        return sous_attent_personn_prenom;
    }

    public void setSous_attent_personn_prenom(String sous_attent_personn_prenom) {
        this.sous_attent_personn_prenom = sous_attent_personn_prenom;
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

    public com.vieecoles.entities.domaine_formation getDomaine_formation() {
        return domaine_formation;
    }

    public void setDomaine_formation(com.vieecoles.entities.domaine_formation domaine_formation) {
        this.domaine_formation = domaine_formation;
    }

    public com.vieecoles.entities.niveau_etude getNiveau_etude() {
        return niveau_etude;
    }

    public void setNiveau_etude(com.vieecoles.entities.niveau_etude niveau_etude) {
        this.niveau_etude = niveau_etude;
    }
}

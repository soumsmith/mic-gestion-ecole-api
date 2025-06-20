package com.vieecoles.projection;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

public class panier_personnelSelect  {
    private Long  idpanier_personnel_id ;
      private  LocalDate  panier_personnel_date_creation;
     private String libelleEcole ;
    private  String nomPersonnel ;
    private  String prenomPersonnel ;
    private  Long idPersonnel ;
    private  String diplome_recent;
    private  String contact;
    private  LocalDate dateNaissance;
    private String lien_piece ;
    private String lien_autorisation  ;
    private String domaine_formation;
    private String libelle_fonction;
    private String email ;
    private int  nombreAnneeExperience ;


    public String getDiplome_recent() {
        return diplome_recent;
    }

    public void setDiplome_recent(String diplome_recent) {
        this.diplome_recent = diplome_recent;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLien_piece() {
        return lien_piece;
    }

    public void setLien_piece(String lien_piece) {
        this.lien_piece = lien_piece;
    }

    public String getLien_autorisation() {
        return lien_autorisation;
    }

    public void setLien_autorisation(String lien_autorisation) {
        this.lien_autorisation = lien_autorisation;
    }

    public Long getIdpanier_personnel_id() {
        return idpanier_personnel_id;
    }

    public void setIdpanier_personnel_id(Long idpanier_personnel_id) {
        this.idpanier_personnel_id = idpanier_personnel_id;
    }

    public LocalDate getPanier_personnel_date_creation() {
        return panier_personnel_date_creation;
    }

    public void setPanier_personnel_date_creation(LocalDate panier_personnel_date_creation) {
        this.panier_personnel_date_creation = panier_personnel_date_creation;
    }

    public String getDomaine_formation() {
        return domaine_formation;
    }

    public void setDomaine_formation(String domaine_formation) {
        this.domaine_formation = domaine_formation;
    }

    public String getLibelle_fonction() {
        return libelle_fonction;
    }

    public void setLibelle_fonction(String libelle_fonction) {
        this.libelle_fonction = libelle_fonction;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNombreAnneeExperience() {
        return nombreAnneeExperience;
    }

    public void setNombreAnneeExperience(int nombreAnneeExperience) {
        this.nombreAnneeExperience = nombreAnneeExperience;
    }

    public Long getIdPersonnel() {
        return idPersonnel;
    }

    public void setIdPersonnel(Long idPersonnel) {
        this.idPersonnel = idPersonnel;
    }

    public String getLibelleEcole() {
        return libelleEcole;
    }

    public void setLibelleEcole(String libelleEcole) {
        this.libelleEcole = libelleEcole;
    }

    public String getNomPersonnel() {
        return nomPersonnel;
    }

    public void setNomPersonnel(String nomPersonnel) {
        this.nomPersonnel = nomPersonnel;
    }

    public String getPrenomPersonnel() {
        return prenomPersonnel;
    }

    public void setPrenomPersonnel(String prenomPersonnel) {
        this.prenomPersonnel = prenomPersonnel;
    }

    public panier_personnelSelect(Long idpanier_personnel_id,
                                  LocalDate panier_personnel_date_creation,
                                  String libelleEcole,
                                  String nomPersonnel,
                                  String prenomPersonnel,
                                  Long idPersonnel,
                                  String diplome_recent,
                                  String contact,
                                  LocalDate dateNaissance,
                                  String lien_piece,
                                  String lien_autorisation ,
                                  String domaine_formation,
                                  String libelle_fonction,
                                  String email,
                                  int nombreAnneeExperience) {
        this.idpanier_personnel_id = idpanier_personnel_id;
        this.panier_personnel_date_creation = panier_personnel_date_creation;
        this.libelleEcole = libelleEcole;
        this.nomPersonnel = nomPersonnel;
        this.prenomPersonnel = prenomPersonnel;
        this.idPersonnel = idPersonnel ;
        this.diplome_recent= diplome_recent ;
        this.contact= contact;
        this.dateNaissance= dateNaissance ;
        this.lien_piece= lien_piece ;
        this.lien_autorisation=lien_autorisation;
        this.domaine_formation=domaine_formation;
        this.libelle_fonction=libelle_fonction ;
        this.email=email ;
        this.nombreAnneeExperience=nombreAnneeExperience ;

    }

    public panier_personnelSelect() {
    }

    @Override
    public String toString() {
        return "panier_personnelSelect{" +
                "idpanier_personnel_id=" + idpanier_personnel_id +
                ", panier_personnel_date_creation=" + panier_personnel_date_creation +
                ", libelleEcole='" + libelleEcole + '\'' +
                ", nomPersonnel='" + nomPersonnel + '\'' +
                ", prenomPersonnel='" + prenomPersonnel + '\'' +
                '}';
    }
}

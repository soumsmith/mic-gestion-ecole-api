package com.vieecoles.projection;

import jakarta.persistence.*;
import java.time.LocalDate;

public class personnel_matiere_classeSelect  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private  Long   Personnel_matiere_classeid ;
   private LocalDate  Personnel_matiere_classe_date_creation ;
   private  String libelleMatiere ;
    private  String libelleClasse ;
    private String nomPersonnel ;
    private  String prenomPersonnel ;


    public personnel_matiere_classeSelect(Long personnel_matiere_classeid, LocalDate personnel_matiere_classe_date_creation, String libelleMatiere, String libelleClasse, String nomPersonnel, String prenomPersonnel) {
        Personnel_matiere_classeid = personnel_matiere_classeid;
        Personnel_matiere_classe_date_creation = personnel_matiere_classe_date_creation;
        this.libelleMatiere = libelleMatiere;
        this.libelleClasse = libelleClasse;
        this.nomPersonnel = nomPersonnel;
        this.prenomPersonnel = prenomPersonnel;
    }

    public Long getPersonnel_matiere_classeid() {
        return Personnel_matiere_classeid;
    }

    public void setPersonnel_matiere_classeid(Long personnel_matiere_classeid) {
        Personnel_matiere_classeid = personnel_matiere_classeid;
    }

    public LocalDate getPersonnel_matiere_classe_date_creation() {
        return Personnel_matiere_classe_date_creation;
    }

    public void setPersonnel_matiere_classe_date_creation(LocalDate personnel_matiere_classe_date_creation) {
        Personnel_matiere_classe_date_creation = personnel_matiere_classe_date_creation;
    }

    public String getLibelleMatiere() {
        return libelleMatiere;
    }

    public void setLibelleMatiere(String libelleMatiere) {
        this.libelleMatiere = libelleMatiere;
    }

    public String getLibelleClasse() {
        return libelleClasse;
    }

    public void setLibelleClasse(String libelleClasse) {
        this.libelleClasse = libelleClasse;
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
}

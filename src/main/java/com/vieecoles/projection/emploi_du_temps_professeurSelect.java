package com.vieecoles.projection;

import javax.persistence.*;

public class emploi_du_temps_professeurSelect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long   emploi_du_temps_professeurid ;
    private String  emploi_du_temps_professeur_jour;
    private String  emploi_du_temps_professeur_heure_debut ;
    private String  emploi_du_temps_professeur_heure_fin ;
    private String  libelleMatiere;
    private String  libelleClasse ;
    private String   nomPersonnel;
    private String  prenomPersonnel ;

    public emploi_du_temps_professeurSelect(Long emploi_du_temps_professeurid, String emploi_du_temps_professeur_jour, String emploi_du_temps_professeur_heure_debut, String emploi_du_temps_professeur_heure_fin, String libelleMatiere, String libelleClasse, String nomPersonnel, String prenomPersonnel) {
        this.emploi_du_temps_professeurid = emploi_du_temps_professeurid;
        this.emploi_du_temps_professeur_jour = emploi_du_temps_professeur_jour;
        this.emploi_du_temps_professeur_heure_debut = emploi_du_temps_professeur_heure_debut;
        this.emploi_du_temps_professeur_heure_fin = emploi_du_temps_professeur_heure_fin;
        this.libelleMatiere = libelleMatiere;
        this.libelleClasse = libelleClasse;
        this.nomPersonnel = nomPersonnel;
        this.prenomPersonnel = prenomPersonnel;
    }

    @Override
    public String toString() {
        return "emploi_du_temps_professeurSelect{" +
                "emploi_du_temps_professeurid=" + emploi_du_temps_professeurid +
                ", emploi_du_temps_professeur_jour='" + emploi_du_temps_professeur_jour + '\'' +
                ", emploi_du_temps_professeur_heure_debut='" + emploi_du_temps_professeur_heure_debut + '\'' +
                ", emploi_du_temps_professeur_heure_fin='" + emploi_du_temps_professeur_heure_fin + '\'' +
                ", libelleMatiere='" + libelleMatiere + '\'' +
                ", libelleClasse='" + libelleClasse + '\'' +
                ", nomPersonnel='" + nomPersonnel + '\'' +
                ", prenomPersonnel='" + prenomPersonnel + '\'' +
                '}';
    }

    public Long getEmploi_du_temps_professeurid() {
        return emploi_du_temps_professeurid;
    }

    public void setEmploi_du_temps_professeurid(Long emploi_du_temps_professeurid) {
        this.emploi_du_temps_professeurid = emploi_du_temps_professeurid;
    }

    public String getEmploi_du_temps_professeur_jour() {
        return emploi_du_temps_professeur_jour;
    }

    public void setEmploi_du_temps_professeur_jour(String emploi_du_temps_professeur_jour) {
        this.emploi_du_temps_professeur_jour = emploi_du_temps_professeur_jour;
    }

    public String getEmploi_du_temps_professeur_heure_debut() {
        return emploi_du_temps_professeur_heure_debut;
    }

    public void setEmploi_du_temps_professeur_heure_debut(String emploi_du_temps_professeur_heure_debut) {
        this.emploi_du_temps_professeur_heure_debut = emploi_du_temps_professeur_heure_debut;
    }

    public String getEmploi_du_temps_professeur_heure_fin() {
        return emploi_du_temps_professeur_heure_fin;
    }

    public void setEmploi_du_temps_professeur_heure_fin(String emploi_du_temps_professeur_heure_fin) {
        this.emploi_du_temps_professeur_heure_fin = emploi_du_temps_professeur_heure_fin;
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

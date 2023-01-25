package com.vieecoles.entities.operations;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
public class emploi_du_temps_professeur extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long   emploi_du_temps_professeurid ;
    private String  emploi_du_temps_professeur_jour;
    private String  emploi_du_temps_professeur_heure_debut ;
    private String  emploi_du_temps_professeur_heure_fin ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matiere_matiereid")
    private com.vieecoles.entities.matiere matiere ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_classeid")
    private com.vieecoles.entities.operations.classe classe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnel_personnelid")
    private personnel personnel ;


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

    public com.vieecoles.entities.matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(com.vieecoles.entities.matiere matiere) {
        this.matiere = matiere;
    }

    public com.vieecoles.entities.operations.classe getClasse() {
        return classe;
    }

    public void setClasse(com.vieecoles.entities.operations.classe classe) {
        this.classe = classe;
    }

    public com.vieecoles.entities.operations.personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(com.vieecoles.entities.operations.personnel personnel) {
        this.personnel = personnel;
    }

    @Override
    public String toString() {
        return "emploi_du_temps_professeur{" +
                "emploi_du_temps_professeurid=" + emploi_du_temps_professeurid +
                ", emploi_du_temps_professeur_jour='" + emploi_du_temps_professeur_jour + '\'' +
                ", emploi_du_temps_professeur_heure_debut='" + emploi_du_temps_professeur_heure_debut + '\'' +
                ", emploi_du_temps_professeur_heure_fin='" + emploi_du_temps_professeur_heure_fin + '\'' +
                ", matiere=" + matiere +
                ", classe=" + classe +
                ", personnel=" + personnel +
                '}';
    }
}

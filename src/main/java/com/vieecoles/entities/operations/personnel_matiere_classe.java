package com.vieecoles.entities.operations;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

import com.vieecoles.entities.Annee_Scolaire;

@Entity(name = "Personnel_matiere_classe")
public class personnel_matiere_classe extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private  Long   Personnel_matiere_classeid ;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matiere_matiereid")
    private com.vieecoles.entities.matiere matiere ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_classeid")
    private com.vieecoles.entities.operations.classe classe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnel_personnelid")
    private personnel personnel ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annee_scolaire_annee_scolaireid")
    private Annee_Scolaire annee_scolaire ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_tenantid")
    private com.vieecoles.entities.tenant tenant ;
    public Long getPersonnel_matiere_classeid() {
        return Personnel_matiere_classeid;
    }

    public void setPersonnel_matiere_classeid(Long personnel_matiere_classeid) {
        Personnel_matiere_classeid = personnel_matiere_classeid;
    }



    public com.vieecoles.entities.operations.classe getClasse() {
        return classe;
    }

    public void setClasse(com.vieecoles.entities.operations.classe classe) {
        this.classe = classe;
    }

    public com.vieecoles.entities.matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(com.vieecoles.entities.matiere matiere) {
        this.matiere = matiere;
    }

    public com.vieecoles.entities.operations.personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(com.vieecoles.entities.operations.personnel personnel) {
        this.personnel = personnel;
    }

    public Annee_Scolaire getAnnee_scolaire() {
        return annee_scolaire;
    }

    public void setAnnee_scolaire(Annee_Scolaire annee_scolaire) {
        this.annee_scolaire = annee_scolaire;
    }

    public com.vieecoles.entities.tenant getTenant() {
        return tenant;
    }

    public void setTenant(com.vieecoles.entities.tenant tenant) {
        this.tenant = tenant;
    }

    @Override
    public String toString() {
        return "personnel_matiere_classe{" +
                "Personnel_matiere_classeid=" + Personnel_matiere_classeid +
                ", matiere=" + matiere +
                ", classe=" + classe +
                ", personnel=" + personnel +
                ", annee_scolaire=" + annee_scolaire +
                ", tenant=" + tenant +
                '}';
    }
}

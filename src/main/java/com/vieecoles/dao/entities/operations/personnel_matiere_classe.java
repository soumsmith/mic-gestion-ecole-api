package com.vieecoles.dao.entities.operations;

import com.vieecoles.dao.entities.Annee_scolaire;
import com.vieecoles.entities.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity(name = "Personnel_matiere_classe")
public class personnel_matiere_classe extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private  Long   Personnel_matiere_classeid ;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matiere_matiereid")
    private com.vieecoles.dao.entities.matiere matiere ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_classeid")
    private com.vieecoles.dao.entities.operations.classe classe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnel_personnelid")
    private personnel personnel ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annee_scolaire_annee_scolaireid")
    private Annee_scolaire annee_scolaire ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_tenantid")
    private com.vieecoles.dao.entities.tenant tenant ;
    public Long getPersonnel_matiere_classeid() {
        return Personnel_matiere_classeid;
    }

    public void setPersonnel_matiere_classeid(Long personnel_matiere_classeid) {
        Personnel_matiere_classeid = personnel_matiere_classeid;
    }



    public com.vieecoles.dao.entities.operations.classe getClasse() {
        return classe;
    }

    public void setClasse(com.vieecoles.dao.entities.operations.classe classe) {
        this.classe = classe;
    }

    public com.vieecoles.dao.entities.matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(com.vieecoles.dao.entities.matiere matiere) {
        this.matiere = matiere;
    }

    public com.vieecoles.dao.entities.operations.personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(com.vieecoles.dao.entities.operations.personnel personnel) {
        this.personnel = personnel;
    }

    public Annee_scolaire getAnnee_scolaire() {
        return annee_scolaire;
    }

    public void setAnnee_scolaire(Annee_scolaire annee_scolaire) {
        this.annee_scolaire = annee_scolaire;
    }

    public com.vieecoles.dao.entities.tenant getTenant() {
        return tenant;
    }

    public void setTenant(com.vieecoles.dao.entities.tenant tenant) {
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

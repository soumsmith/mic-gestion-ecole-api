package com.vieecoles.entities.operations;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

import com.vieecoles.entities.matiere;

@Entity
public class personnel_matiere extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private  Long   personnel_matiereid ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matiere_matiereid")
    private matiere matiere ;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnel_personnelid")
    private personnel personnel ;

    public Long getPersonnel_matiereid() {
        return personnel_matiereid;
    }

    public void setPersonnel_matiereid(Long personnel_matiereid) {
        this.personnel_matiereid = personnel_matiereid;
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

    @Override
    public String toString() {
        return "personnel_matiere{" +
                "personnel_matiereid=" + personnel_matiereid +
                ", matiere=" + matiere +
                ", personnel=" + personnel +
                '}';
    }
}

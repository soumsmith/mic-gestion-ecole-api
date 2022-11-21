package com.vieecoles.entities.operations;

import com.vieecoles.entities.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class personnel_matiere_classe extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private  Long   Personnel_matiere_classeid ;
   private LocalDate  Personnel_matiere_classe_date_creation ;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matiere_matiereid")
    private matiere matiere ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_classeid")
    private com.vieecoles.entities.operations.classe classe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnel_personnelid")
    private personnel personnel ;

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

    @Override
    public String toString() {
        return "personnel_matiere_classe{" +
                "Personnel_matiere_classeid=" + Personnel_matiere_classeid +
                ", Personnel_matiere_classe_date_creation=" + Personnel_matiere_classe_date_creation +
                ", classe=" + classe +
                ", matiere=" + matiere +
                ", personnel=" + personnel +
                '}';
    }
}

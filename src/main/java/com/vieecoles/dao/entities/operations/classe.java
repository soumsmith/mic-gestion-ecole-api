package com.vieecoles.dao.entities.operations;

import com.vieecoles.dao.entities.Annee_scolaire;
import com.vieecoles.dao.entities.Branche;
import com.vieecoles.dao.entities.LangueVivante;
import com.vieecoles.dao.entities.Niveau;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "classe")
@Data
public class classe extends PanacheEntityBase {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  classeid ;
    private  String classecode;
    private  String classelibelle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "niveau_niveauid")
    private Niveau niveau ;

    @ManyToOne
    private LangueVivante langueVivante;
    @Transient
   /* @ManyToOne
    @JoinColumn(name = "personnel_personnelid")*/
    private personnel  profPrincipal;
    @ManyToOne
    private Branche branche;
    @ManyToOne
    @JoinColumn(name = "annee_scolaire_annee_scolaireid")
    private Annee_scolaire annee;



    public classe() {
    }

    @Override
    public String toString() {
        return "classe{" +
                "classeid=" + classeid +
                ", classecode='" + classecode + '\'' +
                ", classelibelle='" + classelibelle + '\'' +
                ", niveau=" + niveau +
                ", langueVivante=" + langueVivante +
                ", profPrincipal=" + profPrincipal +
                ", branche=" + branche +
                ", annee=" + annee +
                '}';
    }

    public LangueVivante getLangueVivante() {
        return langueVivante;
    }

    public void setLangueVivante(LangueVivante langueVivante) {
        this.langueVivante = langueVivante;
    }

    public personnel getProfPrincipal() {
        return profPrincipal;
    }

    public void setProfPrincipal(personnel profPrincipal) {
        this.profPrincipal = profPrincipal;
    }

    public Branche getBranche() {
        return branche;
    }

    public void setBranche(Branche branche) {
        this.branche = branche;
    }

    public Annee_scolaire getAnnee() {
        return annee;
    }

    public void setAnnee(Annee_scolaire annee) {
        this.annee = annee;
    }

    public Long getClasseid() {
        return classeid;
    }

    public void setClasseid(Long classeid) {
        this.classeid = classeid;
    }

    public String getClassecode() {
        return classecode;
    }

    public void setClassecode(String classecode) {
        this.classecode = classecode;
    }

    public String getClasselibelle() {
        return classelibelle;
    }

    public void setClasselibelle(String classelibelle) {
        this.classelibelle = classelibelle;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }
}

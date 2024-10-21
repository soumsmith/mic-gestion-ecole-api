package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ve_insert_inscription_arabe")
public class EleveInscriptionArabePaid extends PanacheEntityBase {
    @Id
    private String ELEVE;

    private String statut_affectation;
    private String anciennete;
    private String scolarite_arabe;
    private String classe_arabe;

    public String getELEVE() {
        return ELEVE;
    }

    public void setELEVE(String ELEVE) {
        this.ELEVE = ELEVE;
    }

    public String getStatut_affectation() {
        return statut_affectation;
    }

    public void setStatut_affectation(String statut_affectation) {
        this.statut_affectation = statut_affectation;
    }

    public String getAnciennete() {
        return anciennete;
    }

    public void setAnciennete(String anciennete) {
        this.anciennete = anciennete;
    }

    public String getScolarite_arabe() {
        return scolarite_arabe;
    }

    public void setScolarite_arabe(String scolarite_arabe) {
        this.scolarite_arabe = scolarite_arabe;
    }

    public String getClasse_arabe() {
        return classe_arabe;
    }

    public void setClasse_arabe(String classe_arabe) {
        this.classe_arabe = classe_arabe;
    }

    // Getters et setters
}

package com.vieecoles.dao.entities.operations;

import com.vieecoles.dao.entities.Civilite;
import com.vieecoles.dao.entities.domaine_formation;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class panier_personnel extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  idpanier_personnel_id ;
   private  String personnelcode;
    private  LocalDate  panier_personnel_date_creation;
    private  LocalDate  panier_personnel_date_modifier;
       @ManyToOne(fetch = FetchType.LAZY)
       @JoinColumn(name = "ecole_ecoleid")
    private com.vieecoles.dao.entities.operations.ecole ecole ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sous_attent_personn_sous_attent_personnid")
    private com.vieecoles.dao.entities.operations.sous_attent_personn sous_attent_personn ;


    public Long getIdpanier_personnel_id() {
        return idpanier_personnel_id;
    }

    public void setIdpanier_personnel_id(Long idpanier_personnel_id) {
        this.idpanier_personnel_id = idpanier_personnel_id;
    }

    public String getPersonnelcode() {
        return personnelcode;
    }

    public void setPersonnelcode(String personnelcode) {
        this.personnelcode = personnelcode;
    }

    public LocalDate getPanier_personnel_date_creation() {
        return panier_personnel_date_creation;
    }

    public void setPanier_personnel_date_creation(LocalDate panier_personnel_date_creation) {
        this.panier_personnel_date_creation = panier_personnel_date_creation;
    }

    public LocalDate getPanier_personnel_date_modifier() {
        return panier_personnel_date_modifier;
    }

    public void setPanier_personnel_date_modifier(LocalDate panier_personnel_date_modifier) {
        this.panier_personnel_date_modifier = panier_personnel_date_modifier;
    }

    public com.vieecoles.dao.entities.operations.ecole getEcole() {
        return ecole;
    }

    public void setEcole(com.vieecoles.dao.entities.operations.ecole ecole) {
        this.ecole = ecole;
    }

    public com.vieecoles.dao.entities.operations.sous_attent_personn getSous_attent_personn() {
        return sous_attent_personn;
    }

    public void setSous_attent_personn(com.vieecoles.dao.entities.operations.sous_attent_personn sous_attent_personn) {
        this.sous_attent_personn = sous_attent_personn;
    }
}

package com.vieecoles.dao.entities.operations;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class message_personnel extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  message_personnel_id ;
   private  String message_personnel_emetteur;
    private  String message_personnel_sujet;
    private  String message_personnel_message;
    private  LocalDate message_personnel_date ;
    private Long ecole_ecoleid ;
    private Long administrateur_gain_idadministrateur_gain ;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sous_attent_personn_sous_attent_personnid")
    private com.vieecoles.dao.entities.operations.sous_attent_personn sous_attent_personn ;

    public Long getMessage_personnel_id() {
        return message_personnel_id;
    }

    public void setMessage_personnel_id(Long message_personnel_id) {
        this.message_personnel_id = message_personnel_id;
    }


    public String getMessage_personnel_sujet() {
        return message_personnel_sujet;
    }

    public void setMessage_personnel_sujet(String message_personnel_sujet) {
        this.message_personnel_sujet = message_personnel_sujet;
    }

    public String getMessage_personnel_message() {
        return message_personnel_message;
    }

    public void setMessage_personnel_message(String message_personnel_message) {
        this.message_personnel_message = message_personnel_message;
    }

    public LocalDate getMessage_personnel_date() {
        return message_personnel_date;
    }

    public Long getEcole_ecoleid() {
        return ecole_ecoleid;
    }

    public void setEcole_ecoleid(Long ecole_ecoleid) {
        this.ecole_ecoleid = ecole_ecoleid;
    }

    public Long getAdministrateur_gain_idadministrateur_gain() {
        return administrateur_gain_idadministrateur_gain;
    }

    public void setAdministrateur_gain_idadministrateur_gain(Long administrateur_gain_idadministrateur_gain) {
        this.administrateur_gain_idadministrateur_gain = administrateur_gain_idadministrateur_gain;
    }

    public void setMessage_personnel_date(LocalDate message_personnel_date) {
        this.message_personnel_date = message_personnel_date;
    }

    public String getMessage_personnel_emetteur() {
        return message_personnel_emetteur;
    }

    public void setMessage_personnel_emetteur(String message_personnel_emetteur) {
        this.message_personnel_emetteur = message_personnel_emetteur;
    }

    public com.vieecoles.dao.entities.operations.sous_attent_personn getSous_attent_personn() {
        return sous_attent_personn;
    }

    public void setSous_attent_personn(com.vieecoles.dao.entities.operations.sous_attent_personn sous_attent_personn) {
        this.sous_attent_personn = sous_attent_personn;
    }

    @Override
    public String toString() {
        return "message_personnel{" +
                "message_personnel_id=" + message_personnel_id +
                ", message_personnel_emetteur='" + message_personnel_emetteur + '\'' +
                ", message_personnel_sujet='" + message_personnel_sujet + '\'' +
                ", message_personnel_message='" + message_personnel_message + '\'' +
                ", message_personnel_date=" + message_personnel_date +
                ", ecole_ecoleid=" + ecole_ecoleid +
                ", administrateur_gain_idadministrateur_gain=" + administrateur_gain_idadministrateur_gain +
                ", sous_attent_personn=" + sous_attent_personn +
                '}';
    }
}

package com.vieecoles.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class message_personnelDto extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  message_personnel_id ;
   private  String message_personnel_emetteur;
    private  String message_personnel_sujet;
    private  String message_personnel_message;
    private  LocalDate message_personnel_date ;
    private Long ecole_ecoleid ;
    private Long administrateur_gain_idadministrateur_gain ;

private  Long identifiant_personnel ;

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

    public void setMessage_personnel_date(LocalDate message_personnel_date) {
        this.message_personnel_date = message_personnel_date;
    }

    public String getMessage_personnel_emetteur() {
        return message_personnel_emetteur;
    }

    public void setMessage_personnel_emetteur(String message_personnel_emetteur) {
        this.message_personnel_emetteur = message_personnel_emetteur;
    }

    public Long getIdentifiant_personnel() {
        return identifiant_personnel;
    }

    public void setIdentifiant_personnel(Long identifiant_personnel) {
        this.identifiant_personnel = identifiant_personnel;
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
}

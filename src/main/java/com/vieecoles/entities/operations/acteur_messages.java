package com.vieecoles.entities.operations;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class acteur_messages extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  idacteur_messages ;
   private  String type_acteur;
    private  String fonction_acteur;
    private  Long  administrateur_gain_idadministrateur_gain;

    private Long sous_attent_personn_sous_attent_personnid ;
    private  Long ecole_ecoleid ;
    private Long message_personnel_message_personnel_id;

    public Long getMessage_personnel_message_personnel_id() {
        return message_personnel_message_personnel_id;
    }

    public void setMessage_personnel_message_personnel_id(Long message_personnel_message_personnel_id) {
        this.message_personnel_message_personnel_id = message_personnel_message_personnel_id;
    }

    public enum type_acteur{
        EMETTEUR, DESTINATAIRE
    }

    public Long getEcole_ecoleid() {
        return ecole_ecoleid;
    }

    public void setEcole_ecoleid(Long ecole_ecoleid) {
        this.ecole_ecoleid = ecole_ecoleid;
    }

    public Long getIdacteur_messages() {
        return idacteur_messages;
    }

    public void setIdacteur_messages(Long idacteur_messages) {
        this.idacteur_messages = idacteur_messages;
    }

    public String getType_acteur() {
        return type_acteur;
    }

    public void setType_acteur(String type_acteur) {
        this.type_acteur = type_acteur;
    }

    public String getFonction_acteur() {
        return fonction_acteur;
    }

    public void setFonction_acteur(String fonction_acteur) {
        this.fonction_acteur = fonction_acteur;
    }

    public Long getAdministrateur_gain_idadministrateur_gain() {
        return administrateur_gain_idadministrateur_gain;
    }

    public void setAdministrateur_gain_idadministrateur_gain(Long administrateur_gain_idadministrateur_gain) {
        this.administrateur_gain_idadministrateur_gain = administrateur_gain_idadministrateur_gain;
    }

    public Long getSous_attent_personn_sous_attent_personnid() {
        return sous_attent_personn_sous_attent_personnid;
    }

    public void setSous_attent_personn_sous_attent_personnid(Long sous_attent_personn_sous_attent_personnid) {
        this.sous_attent_personn_sous_attent_personnid = sous_attent_personn_sous_attent_personnid;
    }
}

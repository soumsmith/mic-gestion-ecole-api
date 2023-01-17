package com.vieecoles.projection;


import java.time.LocalDate;


public class MessageSelect {

    private Long  message_personnel_id ;
    private  String message_personnel_emetteur;
    private  String message_personnel_sujet;
    private  String message_personnel_message;
    private  LocalDate message_personnel_date ;
    private String sous_attent_personn_nom  ;
    private String sous_attent_personn_prenom ;
    private String fullName ;

    public MessageSelect() {
    }

    public MessageSelect(Long message_personnel_id,
                         String message_personnel_emetteur,
                         String message_personnel_sujet,
                         String message_personnel_message,
                         LocalDate message_personnel_date,
                         String sous_attent_personn_nom,
                         String sous_attent_personn_prenom,
                         String fullName) {
        this.message_personnel_id = message_personnel_id;
        this.message_personnel_emetteur = message_personnel_emetteur;
        this.message_personnel_sujet = message_personnel_sujet;
        this.message_personnel_message = message_personnel_message;
        this.message_personnel_date = message_personnel_date;
        this.sous_attent_personn_nom = sous_attent_personn_nom;
        this.sous_attent_personn_prenom = sous_attent_personn_prenom;
        this.fullName=fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getMessage_personnel_id() {
        return message_personnel_id;
    }

    public void setMessage_personnel_id(Long message_personnel_id) {
        this.message_personnel_id = message_personnel_id;
    }

    public String getMessage_personnel_emetteur() {
        return message_personnel_emetteur;
    }

    public void setMessage_personnel_emetteur(String message_personnel_emetteur) {
        this.message_personnel_emetteur = message_personnel_emetteur;
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

    public String getSous_attent_personn_nom() {
        return sous_attent_personn_nom;
    }

    public void setSous_attent_personn_nom(String sous_attent_personn_nom) {
        this.sous_attent_personn_nom = sous_attent_personn_nom;
    }

    public String getSous_attent_personn_prenom() {
        return sous_attent_personn_prenom;
    }

    public void setSous_attent_personn_prenom(String sous_attent_personn_prenom) {
        this.sous_attent_personn_prenom = sous_attent_personn_prenom;
    }

    @Override
    public String toString() {
        return "MessageSelect{" +
                "message_personnel_id=" + message_personnel_id +
                ", message_personnel_emetteur='" + message_personnel_emetteur + '\'' +
                ", message_personnel_sujet='" + message_personnel_sujet + '\'' +
                ", message_personnel_message='" + message_personnel_message + '\'' +
                ", message_personnel_date=" + message_personnel_date +
                ", sous_attent_personn_nom='" + sous_attent_personn_nom + '\'' +
                ", sous_attent_personn_prenom='" + sous_attent_personn_prenom + '\'' +
                '}';
    }
}

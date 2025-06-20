package com.vieecoles.entities.operations;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "message_personnel")
public class message_personnel extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  message_personnel_id ;
    private  String message_personnel_sujet;
    private  String message_personnel_message;
    private  LocalDate message_personnel_date ;
    private Long idemetteur ;
    private Long idrecepteur ;

    public Long getIdemetteur() {
        return idemetteur;
    }

    public void setIdemetteur(Long idemetteur) {
        this.idemetteur = idemetteur;
    }

    public Long getIdrecepteur() {
        return idrecepteur;
    }

    public void setIdrecepteur(Long idrecepteur) {
        this.idrecepteur = idrecepteur;
    }

    public void setMessage_personnel_date(LocalDate message_personnel_date) {
        this.message_personnel_date = message_personnel_date;
    }

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


}

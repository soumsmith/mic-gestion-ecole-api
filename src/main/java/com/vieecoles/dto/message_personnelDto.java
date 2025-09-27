package com.vieecoles.dto;

import java.time.LocalDate;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class message_personnelDto extends PanacheEntityBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long message_personnel_id;
	private String message_personnel_sujet;
	private String message_personnel_message;
	private LocalDate message_personnel_date;
	private Long idEmetteur;
	private Long idDestinataire;

	public Long getIdEmetteur() {
		return idEmetteur;
	}

	public void setIdEmetteur(Long idEmetteur) {
		this.idEmetteur = idEmetteur;
	}

	public Long getIdDestinataire() {
		return idDestinataire;
	}

	public void setIdDestinataire(Long idDestinataire) {
		this.idDestinataire = idDestinataire;
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

	public void setMessage_personnel_date(LocalDate message_personnel_date) {
		this.message_personnel_date = message_personnel_date;
	}

}

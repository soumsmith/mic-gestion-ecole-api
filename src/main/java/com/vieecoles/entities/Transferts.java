package com.vieecoles.entities;

import jakarta.persistence.Table;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Transferts")
public class Transferts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idTransferts;
	private String code;
	private String nom;
	private String prenoms;
	private String classe;
	private String redoublant;
	private String dateNaissance;
	private String nredoublant;
	private String decision;

	private String etablissementOrigine;
	private String niveau;
	private Long idEcole;
	private String matricule;


}

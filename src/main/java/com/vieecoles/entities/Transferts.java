package com.vieecoles.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
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


}

package com.vieecoles.steph.entities;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Progression {
	
	private String id;
	private Long niveauEnseignant;
	private Long annee;
	private Long branche;
	private Long matiere;
	private String libelle;
	private int volumeHoraire;
	private Date dateCreation;
	private Date dateUpdate;

}

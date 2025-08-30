package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Data
@Entity
public class Branche extends PanacheEntityBase{

	@Id
	private long id;
	private String libelle;

	//Type d'enseignement (GENERAL - PROFESSIONNEL - TECHNIQUE - ...)
	@ManyToOne
	private Filiere filiere;
	@ManyToOne
	@JoinColumn(name = "niveau_id")
	private Niveau niveau;
	@ManyToOne
	private Serie serie;

	@ManyToOne
	@JoinColumn(name = "niveau_enseignement_id")
	private NiveauEnseignement niveauEnseignement;

	@ManyToOne
	@JoinColumn(name = "fk_programme_id")
	private Programme programme;
}

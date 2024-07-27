package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

package com.vieecoles.steph.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ref_progression")
public class Progression extends PanacheEntityBase{
	@Id
	private String id;
	@ManyToOne
	@JoinColumn(name = "niveau_enseignement_id")
	private NiveauEnseignement niveauEnseignant;
	@ManyToOne
	@JoinColumn(name = "annee_id")
	private AnneeScolaire annee;
	@ManyToOne
	@JoinColumn(name = "branche_id")
	private Branche branche;
	@ManyToOne
	@JoinColumn(name = "matiere_id")
	private Matiere matiere;
	@Column(name = "volume_horaire")
	private Integer volumeHoraire;
	@Column(name = "date_creation")
	private Date dateCreation;
	@Column(name = "date_update")
	private Date dateUpdate;

}

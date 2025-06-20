package com.vieecoles.steph.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Groupe_Evaluations")
@Data
@EqualsAndHashCode(callSuper = false)
public class GroupeEvaluations extends PanacheEntityBase{

	@Id
	private String id;
	private String libelle;

	@ManyToOne
	@JoinColumn(name = "matiere_id")
	private EcoleHasMatiere matiere;
	@ManyToOne
	@JoinColumn(name = "annee_id")
	private AnneeScolaire annee;
	@ManyToOne
	@JoinColumn(name = "periode_id")
	private Periode periode;
	@ManyToOne
	@JoinColumn(name = "niveau")
	private Branche niveau;
	@ManyToOne
	@JoinColumn(name = "ecole_id")
	private Ecole ecole;

	@Transient
	private List<Evaluation> evaluations = new ArrayList<>();

	@Transient
	private Integer nbreEvaluations;

	private String user;
	@Column(name = "date_creation")
	private Date dateCreation;
	@Column(name = "date_update")
	private Date dateUpdate;

}

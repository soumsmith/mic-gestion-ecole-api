package com.vieecoles.steph.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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

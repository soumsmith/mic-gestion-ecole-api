package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "annee_scolaire")
public class AnneeScolaire extends PanacheEntityBase{

	@Id
	@Column(name = "annee_scolaireid")
	private long id;
//	@Column(name = "annee_scolaire_code")
//	private String code;
	@Column(name = "annee_scolaire_libelle")
	private String libelle;
	private String periodicite;
	@Column(name = "nbre_eval")
	private Integer nbreEval;
	@ManyToOne
	@JoinColumn(name = "niveau_enseignement_id")
	private NiveauEnseignement niveauEnseignement;
	private String statut;
	@ManyToOne
	@JoinColumn(name = "ecole_id")
	private Ecole ecole;
	
//	@Transient
//	private List<AnneePeriode> anneePeriodes;
	
}

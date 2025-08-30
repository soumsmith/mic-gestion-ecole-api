package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.vieecoles.steph.pojos.AnneePeriodePojo;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "annee_scolaire")
public class AnneeScolaire extends PanacheEntityBase implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "annee_scolaireid")
	private Long id;
//	@Column(name = "annee_scolaire_code")
//	private String code;
	@Column(name = "annee")
	private Integer anneeDebut;
	@Transient
	private Integer anneeFin;
	@Transient
	private List<AnneePeriodePojo> anneePeriodes;
	@Column(name = "annee_scolaire_libelle")
	private String libelle;
	@ManyToOne
	@JoinColumn(name = "periodicite")
	private Periodicite periodicite;
	@Column(name = "nbre_eval")
	private Integer nbreEval;
	@ManyToOne
	@JoinColumn(name = "niveau_enseignement_id")
	private NiveauEnseignement niveauEnseignement;
	private String statut;
	@ManyToOne
	@JoinColumn(name = "ecole_id")
	private Ecole ecole;
	@Column(name="delai_notes")
	private Integer delaiNotes;
	// Niveau CENTRAL ou ECOLE
	private String niveau;
	private String user;
	private Date dateCreation;
	private Date dateUpdate;

	public String getCustomLibelle() {
		return String.format("Année %s - %s", getAnneeDebut(), getAnneeDebut() != null ? (getAnneeDebut() +1) : null);
	}

//	@Transient
//	private List<AnneePeriode> anneePeriodes;

}

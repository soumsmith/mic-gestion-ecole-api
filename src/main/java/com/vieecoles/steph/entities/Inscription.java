package com.vieecoles.steph.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "inscriptions")
@Data
@EqualsAndHashCode(callSuper = false)
public class Inscription extends PanacheEntityBase{

	@Id
	@Column(name = "inscriptionsid")
	private long id;
	@Column(name = "inscriptionscode")
	private String code;
	@ManyToOne
	@JoinColumn(name = "annee_scolaire_annee_scolaireid")
	private AnneeScolaire annee;
	@ManyToOne
	@JoinColumn(name = "eleve_eleveid")
	private Eleve eleve;
	@ManyToOne
	@JoinColumn(name = "Branche_id")
	private Branche branche;
	@Column(name = "inscriptions_statut_eleve")
	private String statut;
	@Column(name="inscriptions_boursier")
	private String boursier;
	@Column(name="inscriptions_etat_affectation")
	private String afecte;
	@Column(name="inscriptions_redoublant")
	private String redoublant;

}

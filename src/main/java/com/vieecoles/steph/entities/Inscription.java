package com.vieecoles.ressource.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

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

}

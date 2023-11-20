package com.vieecoles.steph.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "absence_eleve")
@Data
@EqualsAndHashCode(callSuper = false)
public class AbsenceEleve extends PanacheEntityBase{

	@Id
	@Column(name = "absence_eleveid")
	private String id;
	@Column(name="abscence_elevecode")
	private String code;
	@Column(name = "abscence_elevestatus")
	private String statut; 
	@ManyToOne
	@JoinColumn(name = "eleve_eleveid")
	private Eleve eleve;
	@ManyToOne
	@JoinColumn(name = "annee_id")
	private AnneeScolaire annee;
	@ManyToOne
	@JoinColumn(name = "periode_id")
	private Periode periode;
	@Transient
	private Classe classe;
	@Column(name = "abs_just")
	private Integer absJustifiee;
	@Column(name = "abs_non_just")
	private Integer absNonJustifiee;
	
}

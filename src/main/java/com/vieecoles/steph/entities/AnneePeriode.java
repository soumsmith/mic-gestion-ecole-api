package com.vieecoles.steph.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnneePeriode {
	@Id
	private long id;
	@ManyToOne
	@JoinColumn(name = "id")
	private Periode periode;
	@ManyToOne
	@JoinColumn(name = "id")
	private AnneeScolaire anneeScolaire;
	@Column(name = "time_debut")
	private String timeStampDebut;
	@Column(name = "time_fin")
	private String timeStampFin;
	@Transient
	private String userId;

}

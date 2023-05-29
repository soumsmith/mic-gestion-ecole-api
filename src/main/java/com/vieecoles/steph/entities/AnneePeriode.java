package com.vieecoles.steph.entities;

import java.time.LocalDateTime;

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
	private LocalDateTime dateDebut;
	@Column(name = "time_fin")
	private LocalDateTime dateFin;
	@Column(name = "date_limite")
	private LocalDateTime dateLimite;
	@Transient
	private String userId;
	@Transient
	private LocalDateTime dateCreation;
	@Transient
	private LocalDateTime dateUpdate;

}

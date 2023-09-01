package com.vieecoles.steph.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Annee_Periode")
public class AnneePeriode extends PanacheEntityBase{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	@JoinColumn(name = "periode_id")
	private Periode periode;
	@ManyToOne
	@JoinColumn(name = " annee_id")
	private AnneeScolaire anneeScolaire;
	@Column(name = "date_debut")
	private LocalDateTime dateDebut;
	@Column(name = "date_fin")
	private LocalDateTime dateFin;
	@Column(name = "date_limite")
	private LocalDateTime dateLimite;
	@Column(name = "nbre_eval")
	private Integer nbreEval;
	@ManyToOne
	@JoinColumn(name = "ecole_id")
	private Ecole ecole;
	private String niveau;
	
	private String user;
	private LocalDateTime dateCreation;
	private LocalDateTime dateUpdate;
	
}

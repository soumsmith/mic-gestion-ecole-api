package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
public class Seances extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String annee;
	@ManyToOne
	@JoinColumn(name = "jour")
	private Jour jour;
	@ManyToOne
	private Activite activite;
	@Column(name = "heure_debut")
	private String heureDeb;
	@Column(name = "heure_Fin")
	private String heureFin;
	@Column(name = "date_seance")
	private Date dateSeance;
	@ManyToOne
	@JoinColumn(name = "professeur_id")
	private Personnel professeur;

	@ManyToOne
	@JoinColumn(name = "surveillant_id")
	private Personnel surveillant;

	@ManyToOne
	@JoinColumn(name = "matiere_id")
	private Matiere matiere;
	@ManyToOne
	@JoinColumn(name = "classe_id")
	private Classe classe;
	@ManyToOne
	@JoinColumn(name = "salle_id")
	private Salle salle;
	private String statut;
	@ManyToOne
	@JoinColumn(name = "type_activite_id")
	private TypeActivite typeActivite;
	@Column(name = "date_creation")
	private Date dateCreation;
	@Column(name = "date_update")
	private Date dateUpdate;
}

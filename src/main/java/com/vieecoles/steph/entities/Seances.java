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
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
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
	@Column(name = "evaluation_indicator")
	private Integer evaluationIndicator;
	@ManyToOne
	@JoinColumn(name = "professeur_id")
	private Personnel professeur;

	@ManyToOne
	@JoinColumn(name = "surveillant_id")
	private Personnel surveillant;

	@ManyToOne
	@JoinColumn(name = "matiere_id")
	private EcoleHasMatiere matiere;
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
	// Pour assurer le traitement automatique des evaluation lors de la generation
	// des séances
	@ManyToOne
	@JoinColumn(name = "evaluation_id")
	private Evaluation evaluation;
	@Column(name = "date_creation")
	private Date dateCreation;
	@Column(name = "date_update")
	private Date dateUpdate;
	@Column(name = "user")
	private String user;
	@Transient
	private Boolean appelAlreadyExist;
	// Indique le positionnement au cas où plusieurs séances par rapport à l'unité de temps
	@Transient
	private int position;
	@Transient
	private Boolean isVerrou;
	@Transient
	private Boolean isEnded;
	@Transient
	private String isClassEnded;
	@Transient
	private int duree;
	@Transient
	private int dureeTotale;
}

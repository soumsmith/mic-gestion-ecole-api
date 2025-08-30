package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
public class Activite extends PanacheEntityBase{

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	private String annee;
	@ManyToOne
	@JoinColumn(name = "ecole_id")
	private Ecole ecole;
	@ManyToOne
	@JoinColumn(name="jour")
	private Jour jour;
//	@ManyToOne
//	@JoinColumn(name="heure")
//	private Heures heure;
	@Column(name = "heure_debut")
	private String heureDeb;
	@Column(name = "heure_Fin")
	private String heureFin;

	@ManyToOne
	@JoinColumn(name = "ecole_matiereid")
	private EcoleHasMatiere matiere;
	@ManyToOne
	@JoinColumn(name = "classe_classeid")
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
	private String user;
}

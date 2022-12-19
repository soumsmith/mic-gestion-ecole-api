package com.vieecoles.ressource.steph.entities;

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
public class Activite extends PanacheEntityBase{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String annee;
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
	@JoinColumn(name = "matiere_matiereid")
	private Matiere matiere;
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
}

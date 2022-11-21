package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "evaluation")
public class Evaluation extends PanacheEntityBase{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "evaluationid")
	private Long id;
	@Column(name = "evaluationcode")
	private String code;
	@Column(name = "evaluationdate")
	private String date;
	private String heure;
	private String duree;
	private String noteSur;
	private String etat;
	@Column(name = "date_limite")
	private String dateLimite;
	@ManyToOne
	@JoinColumn(name = "type_evaluation_type_evaluationid")
	private TypeEvaluation type;
	@ManyToOne
	@JoinColumn(name = "periode_periodeid")
	private Periode periode;
//	@ManyToOne
//	@JoinColumn(name = "classe_annee_id")
//	private ClasseAnnee classeAnnee;
	@ManyToOne
	@JoinColumn(name = "annee_scolaire_annee_scolaireid")
	private Annee_scolaire annee;
	@ManyToOne
	@JoinColumn(name = "classe_classeid")
	private com.vieecoles.dao.entities.operations.classe classe;
	@ManyToOne
	@JoinColumn(name = "matiere_matiereid")
	private matiere matiere;
}

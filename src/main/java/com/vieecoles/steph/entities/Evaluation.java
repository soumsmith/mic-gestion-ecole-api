package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "evaluation")
public class Evaluation extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "evaluationid")
	private Long id;
	@Column(name = "evaluationcode")
	private String code;
	// @JsonbDateFormat(value = "dd/MM/yyyy")
	@Column(name = "evaluationdate")
	private Date date;
	@Transient
	private List<EvaluationLoader> evaLoaders;
	private String heure;
	private String duree;
	private String noteSur;
	private String chapitreDeLecon;
	private String etat;
	@Column(name = "groupe_evaluation_id")
	private String groupeEvaluationId;
	@Column(name = "date_limite")
	private String dateLimite;
	@ManyToOne
	@JoinColumn(name = "type_evaluation_type_evaluationid")
//	private TypeEvaluation type;
	private TypeActivite type;
	@ManyToOne
	@JoinColumn(name = "periode_periodeid")
	private Periode periode;
	private Integer pec;
//	@ManyToOne
//	@JoinColumn(name = "classe_annee_id")
//	private ClasseAnnee classeAnnee;
	@ManyToOne
	@JoinColumn(name = "annee_id")
	private AnneeScolaire annee;
	@ManyToOne
	@JoinColumn(name = "classe_classeid")
	private Classe classe;
	@ManyToOne
	@JoinColumn(name = "matiere_matiereid")
	private EcoleHasMatiere matiereEcole;
	@Transient
	private String dateToFilter;

	@Column(name = "date_creation")
	private Date dateCreation;
	@Column(name = "date_update")
	private Date dateUpdate;
	private String user;

}

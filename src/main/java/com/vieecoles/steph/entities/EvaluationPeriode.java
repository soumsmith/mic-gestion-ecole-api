package com.vieecoles.steph.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Evaluation_periode_primaire")
@Getter
@Setter
public class EvaluationPeriode extends PanacheEntityBase {
	@Id
	private String id;
	@ManyToOne
	@JoinColumn(name = "annee")
	private AnneeScolaire annee;
	@ManyToOne
	@JoinColumn(name = "niveau")
	private Branche niveau;
	@ManyToOne
	@JoinColumn(name = "ecole")
	private Ecole ecole;
	private String numero;
	@ManyToOne
	@JoinColumn(name = "periode")
	private Periode periode;
	@ManyToOne
	@JoinColumn(name = "type_evaluation")
	private TypeActivite typeEvaluation;
	@Column(name = "date_creation")
	private Date dateCreation;
	@Column(name = "date_update")
	private Date dateUpdate;
	private String user;

}

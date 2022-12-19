package com.vieecoles.ressource.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "type_evaluation")
@Data
@EqualsAndHashCode(callSuper = false)
public class TypeEvaluation extends PanacheEntityBase{

	@Id
	@Column(name = "type_evaluationid")
	private long id;
	@Column(name = "type_evaluationcode")
	private String code;
	@Column(name = "type_evaluation_libelle")
	private String libelle;
}

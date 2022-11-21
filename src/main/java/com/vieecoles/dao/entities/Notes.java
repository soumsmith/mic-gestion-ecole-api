package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "note_eleve")
public class Notes extends PanacheEntityBase{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "note_eleveid")
	private long id;
	@ManyToOne
	@JoinColumn(name = "evaluation_evaluationid")
	private Evaluation evaluation;
	@Column(name = "note_elevenote")
	private String note;
	@ManyToOne
	@JoinColumn(name = "eleve_eleveid")
	private com.vieecoles.dao.entities.operations.eleve eleve;
	@Transient
	private String statut;

}

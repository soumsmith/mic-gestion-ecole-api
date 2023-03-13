package com.vieecoles.steph.entities;

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
	private Double note;
	private Integer pec;
	private String motif;
	@ManyToOne
	@JoinColumn(name = "inscription_has_eleve_id")
	private ClasseEleve classeEleve;
	@Transient
	private String statut;
	@ManyToOne
	@JoinColumn(name = "personnel_personnelid")
	private Personnel personnel;

}

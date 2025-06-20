package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "Notes_Loader")
public class NotesLoader extends PanacheEntityBase{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Double note;
	@Column(name = "num_ordre")
	private Integer numOrdre;
	@ManyToOne
	@JoinColumn(name = "evaluation_id")
	private EvaluationLoader evaluationLoader;

}

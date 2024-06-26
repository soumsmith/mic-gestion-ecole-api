package com.vieecoles.steph.entities;

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
@Getter
@Setter
@Table(name = "Classe_Eleve_Periode")
public class ClasseElevePeriode extends PanacheEntityBase {
	@Id
	private String id;
	@ManyToOne
	@JoinColumn(name = "classe_id")
	private Classe classe;
	@ManyToOne
	@JoinColumn(name = "eleve_id")
	private Eleve eleve;
	@ManyToOne
	@JoinColumn(name = "annee_id")
	private AnneeScolaire annee;
	@ManyToOne
	@JoinColumn(name = "periode_id")
	private Periode periode;
	@Column(name = "is_classed")
	private String isClassed;
}

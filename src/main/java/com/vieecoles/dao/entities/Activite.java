package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
public class Activite extends PanacheEntityBase{

	@Id
	private long id;
	private String annee;
	@ManyToOne
	@JoinColumn(name="jour")
	private Jour jour;
	@ManyToOne
	@JoinColumn(name="heure")
	private Heures heure;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "matiere_matiereid")
	private matiere matiere ;

	@ManyToOne
	@JoinColumn(name = "classe_classeid")
	private com.vieecoles.dao.entities.operations.classe classe;
}

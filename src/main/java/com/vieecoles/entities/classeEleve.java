package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;

//@Table(name = "Classe_Eleve")
@Entity
@Table(name = "eleve_classe")
@Data
@EqualsAndHashCode(callSuper = false)
public class classeEleve extends PanacheEntityBase{

	@Id
	@Column(name = "eleve_classeid")
	private long id;

//	@ManyToOne
//	@JoinColumn(name = "classe_annee_id")
//	private ClasseAnnee classeAnnee;
	@ManyToOne
	@JoinColumn(name = "annee_scolaire_annee_scolaireid")
	private Annee_Scolaire annee;

	@ManyToOne
	@JoinColumn(name = "classe_classeid")
	private com.vieecoles.entities.operations.classe classe;

	@ManyToOne
	@JoinColumn(name = "eleve_eleveid")
	private com.vieecoles.entities.operations.eleve eleve;

}

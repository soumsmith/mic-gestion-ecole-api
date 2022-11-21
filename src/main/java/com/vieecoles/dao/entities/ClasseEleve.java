package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

//@Table(name = "Classe_Eleve")
@Entity
@Table(name = "eleve_classe")
@Data
@EqualsAndHashCode(callSuper = false)
public class ClasseEleve extends PanacheEntityBase{

	@Id
	@Column(name = "eleve_classeid")
	private long id;

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
	@JoinColumn(name = "eleve_eleveid")
	private com.vieecoles.dao.entities.operations.eleve eleve;

}

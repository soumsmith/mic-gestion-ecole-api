package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

//@Table(name = "Classe_Eleve")
@Entity
@Table(name = "inscriptions_has_classe")
@Data
@EqualsAndHashCode(callSuper = false)
public class ClasseEleve extends PanacheEntityBase{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

//	@ManyToOne
//	@JoinColumn(name = "classe_annee_id")
//	private ClasseAnnee classeAnnee;
	@ManyToOne
	@JoinColumn(name = "inscriptions_inscriptionsid")
	private Inscription inscription;

	@ManyToOne
	@JoinColumn(name = "classe_classeid")
	private Classe classe;
	@Column(name = "inscription_classestatatut")
	private String statut;
	@Column(name = "inscription_classedate")
	private Date dateCreation;

	@Column(name = "date_update")
	private Date dateUpdate;


}

package com.vieecoles.ressource.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "classe")
@Data
public class Classe extends PanacheEntityBase {

	@Id
	@Column(name = "classeid")
	private long id;
	@Column(name = "classecode")
	private String code;
	@Column(name = "classelibelle")
	private String libelle;
	@ManyToOne
	private LangueVivante langueVivante;
	@Transient
	@ManyToOne
	@JoinColumn(name = "professeur_id")
	private Professeur profPrincipal;
	@ManyToOne
	private Branche branche;
	@ManyToOne
	@JoinColumn(name = "annee_scolaire_annee_scolaireid")
	private AnneeScolaire annee;
	@Transient
	private Date dateCreation;
	@ManyToOne
	private Ecole ecole;
	@Column(name = "effectif")
	private Integer effectif;
//	@Transient
//	@OneToMany
//	private List<Eleve> eleve;
}

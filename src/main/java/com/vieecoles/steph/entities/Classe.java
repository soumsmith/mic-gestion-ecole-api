package com.vieecoles.steph.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;

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
	@JoinColumn(name = "ecole_ecoleid")
	private Ecole ecole;
	@Column(name = "effectif")
	private Integer effectif;
//	@Transient
//	@OneToMany
//	private List<Eleve> eleve;
}

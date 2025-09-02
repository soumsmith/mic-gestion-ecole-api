package com.vieecoles.steph.entities;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "classe")
@Data
@EqualsAndHashCode(callSuper=false)
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
//	@ManyToOne
//	@JoinColumn(name = "annee_scolaire_annee_scolaireid")
//	private AnneeScolaire annee;
//	@Transient
	@Column(name = "date_creation")
	private Date dateCreation;
	@ManyToOne
	@JoinColumn(name = "ecole_ecoleid")
	private Ecole ecole;
	@Column(name = "effectif")
	private Integer effectif;
	private Integer visible;
	@Column(name = "date_update")
	private Date dateUpdate;
//	@Transient
//	@OneToMany
//	private List<Eleve> eleve;
}

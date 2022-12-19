package com.vieecoles.ressource.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "eleve")
public class Eleve extends PanacheEntityBase{

	@Id
	@Column(name = "eleveid")
	private long id;
	@Column(name = "eleve_matricule")
	private String matricule;
	@Column(name = "elevenom")
	private String nom;
	@Column(name = "eleveprenom")
	private String prenom;
	@Column(name = "elevedate_naissance")
	private String dateNaissance;
	@Column(name = "eleve_sexe")
	private String sexe;
}

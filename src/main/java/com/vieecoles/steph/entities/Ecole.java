package com.vieecoles.steph.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;

@Data
@Entity
@Table(name = "ecole")
public class Ecole extends PanacheEntityBase{

	@Id
	@Column(name = "ecoleid")
	private long id;
	@Column(name = "ecolecode")
	private String Code;
	@Column(name= "ecoleclibelle")
	private String libelle;

	@Column(name = "ecole_adresse")
	private String adresse;
	@Column(name = "ecole_telephone")
	private String tel;
	@Column(name = "ecole_statut")
	private String statut;
	@Column(name = "nom_signataire")
	private String nomSignataire;
	@ManyToOne
	@JoinColumn(name = "Niveau_Enseignement_id")
	private NiveauEnseignement niveauEnseignement;
}

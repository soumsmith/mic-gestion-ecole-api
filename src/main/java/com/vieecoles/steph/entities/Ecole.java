package com.vieecoles.steph.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "ecole")
@EqualsAndHashCode(callSuper=false)
public class Ecole extends PanacheEntityBase{

	@Id
	@Column(name = "ecoleid")
	private long id;
	@Column(name = "ecolecode")
	private String Code;
	@Column(name= "ecoleclibelle")
	private String libelle;
	private String numeroIEPP;

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
	@Column(name = "identifiant_vie_ecole ")
	private String identifiantVieEcole ;
	@Column(name = "utilise_formule_confessionnelle_arabe ")
	private Integer utiliseFormuleConfessionnelleArabe;
}

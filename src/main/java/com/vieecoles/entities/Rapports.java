package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "RAPPORTS")
public class Rapports extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Long  id ;
	private  String  code ;
	private  String   nom ;
	private  String   description ;
	private  Long id_Niveau_Enseignement ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId_Niveau_Enseignement() {
		return id_Niveau_Enseignement;
	}

	public void setId_Niveau_Enseignement(Long id_Niveau_Enseignement) {
		this.id_Niveau_Enseignement = id_Niveau_Enseignement;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

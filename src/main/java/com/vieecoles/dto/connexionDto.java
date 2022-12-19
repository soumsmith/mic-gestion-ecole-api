package com.vieecoles.dto;

import com.vieecoles.dao.entities.Filiere;
import com.vieecoles.dao.entities.Niveau;
import com.vieecoles.dao.entities.Serie;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class BrancheDto extends PanacheEntityBase{

	private long id;
	private String libelle;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public BrancheDto(long id, String libelle) {
		this.id = id;
		this.libelle = libelle;
	}
}

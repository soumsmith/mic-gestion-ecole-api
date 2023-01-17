package com.vieecoles.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

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

package com.vieecoles.entities;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class NiveauEtage {

	private long id;
	private String code;
	private String libelle;
	private Batiment batiment;
}

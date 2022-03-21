package com.vieecoles.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Salle {
	@Id
	private long id;
	private String code;
	private String libelle;
	private NiveauEtage niveauEtage;
}

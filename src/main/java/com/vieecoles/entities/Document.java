package com.vieecoles.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Document {

	@Id
	private long id;
	private String code;
	private String libelle;
	private String chemin;
	private Personnel personnel;
}

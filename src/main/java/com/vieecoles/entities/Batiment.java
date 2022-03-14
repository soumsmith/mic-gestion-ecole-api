package com.vieecoles.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Batiment {
	@Id
	private long id;
	private String code;
	private String libelle;
	private Ecole ecole;
}

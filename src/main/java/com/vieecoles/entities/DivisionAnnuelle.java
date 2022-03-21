package com.vieecoles.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class DivisionAnnuelle {
	@Id
	private long id;
	private String code;
	private String libelle;
	private Float coefficient;
}

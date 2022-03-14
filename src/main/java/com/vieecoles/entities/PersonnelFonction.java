package com.vieecoles.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class PersonnelFonction {

	@Id
	private long id;
	private Fonction fonction;
}

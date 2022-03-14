package com.vieecoles.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class ClasseSalle {

	@Id
	private long id;
	private Salle salle;
	
}

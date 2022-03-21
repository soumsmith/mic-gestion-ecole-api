package com.vieecoles.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class NiveauMatiere {

	@Id
	private long id;
	private NiveauMatiere matiere;
}

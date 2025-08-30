package com.vieecoles.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "NiveauMatiere")
public class NiveauMatiere {

	@Id
	private long id;
//	private NiveauMatiere matiere;
}

package com.vieecoles.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Auteur {
	@Id
	private long id;
	private String code;
	private String nom;
	private String prenom;
	private LocalDateTime dateNaissance;
	private String nationalite;
}

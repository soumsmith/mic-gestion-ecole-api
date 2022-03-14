package com.vieecoles.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Livre {
	@Id
	private long id;
	private String code;
	private String titre;
	private String langue;
	private LocalDateTime dateApparution;
	private int nombrePage;
	private String image;
	private Integer nombreExemplaire;
	private Bibliotheque bibliotheque;
	private Domaine domaine;
	private Auteur auteur;
}

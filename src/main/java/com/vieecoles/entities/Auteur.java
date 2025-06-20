package com.vieecoles.entities;

import jakarta.persistence.Table;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;

@Data
@Entity
@Table(name = "Auteur")
public class Auteur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String code;
	private String nom;
	private String prenom;
	private LocalDateTime dateNaissance;
	private String nationalite;
}

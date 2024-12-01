package com.vieecoles.steph.dto.moyennes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonneDto {
	private Long id;
	private String matricule;
	private String nom;
	private String prenom;
	private String sexe;
}

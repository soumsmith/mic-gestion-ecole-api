package com.vieecoles.steph.dto.moyennes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonneDto {
	private Long id;
	private String matricule;
	private String nom;
	private String prenom;
	private String sexe;
	private String urlPhoto;
}

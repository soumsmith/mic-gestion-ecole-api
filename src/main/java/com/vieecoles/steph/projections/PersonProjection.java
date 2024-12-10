package com.vieecoles.steph.projections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PersonProjection {
	private Long id;
	private String matricule;
	private String nom;
	private String prenom;
	private String sexe;
}

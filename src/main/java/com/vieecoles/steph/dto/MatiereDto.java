package com.vieecoles.steph.dto;

import com.vieecoles.steph.entities.CategorieMatiere;
import com.vieecoles.steph.entities.Matiere;
import com.vieecoles.steph.entities.NiveauEnseignement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatiereDto {
	private Long id;
	private String code;
	private String libelle;
	private Integer pec;
	private NiveauEnseignement niveauEnseignement;
	private Double moyenne;
	private String rang;
	private String coef;
	private String appreciation;
	private Matiere matiereParent;
	private CategorieMatiere categorie;
}

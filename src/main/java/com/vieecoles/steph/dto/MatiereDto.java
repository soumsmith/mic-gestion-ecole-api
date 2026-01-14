package com.vieecoles.steph.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vieecoles.steph.entities.CategorieMatiere;
import com.vieecoles.steph.entities.Matiere;
import com.vieecoles.steph.entities.NiveauEnseignement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MatiereDto {
	
	public MatiereDto(Long id, String code, String libelle) {
		super();
		this.id = id;
		this.code = code;
		this.libelle = libelle;
	}
	private Long id;
	private String code;
	private String libelle;
	private Integer pec;
	private Integer bonus;
	private NiveauEnseignement niveauEnseignement;
	private Double moyenne;
	private String rang;
	private String coef;
	private String appreciation;
	private Matiere matiereParent;
	private CategorieMatiere categorie;
	private Integer numOrdre;	
	private String codeVieEcole;

}
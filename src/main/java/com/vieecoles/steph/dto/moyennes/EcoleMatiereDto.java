package com.vieecoles.steph.dto.moyennes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vieecoles.steph.dto.IdLongCodeLibelleDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EcoleMatiereDto {
	private Long id;
	private String code;
	private String libelle;
	private Integer pec;
	private Integer bonus;
	private IdLongCodeLibelleDto niveauEnseignement;
	private Double moyenne;
	private String rang;
	private String coef;
	private String appreciation;
	private IdLongCodeLibelleDto matiereParent;
	private IdLongCodeLibelleDto categorie;
	private Integer numOrdre;
}

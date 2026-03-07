package com.vieecoles.steph.dto;

import com.vieecoles.steph.dto.moyennes.PersonneDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClasseEleveDto {
	private IdLongCodeLibelleDto ecole;
	private IdLongCodeLibelleDto classe;
	private PersonneDto eleve;
	private Integer effectifClasse;
	private String identifiantVieEcole;
}

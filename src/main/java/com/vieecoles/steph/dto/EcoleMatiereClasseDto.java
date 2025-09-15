package com.vieecoles.steph.dto;

import com.vieecoles.steph.dto.moyennes.PersonneDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EcoleMatiereClasseDto {
	private IdLongCodeLibelleDto ecoleMatiere;
	private IdLongCodeLibelleDto classe;
	private PersonneDto enseignant;

}

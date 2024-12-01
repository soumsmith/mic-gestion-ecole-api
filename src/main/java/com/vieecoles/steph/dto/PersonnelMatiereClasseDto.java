package com.vieecoles.steph.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonnelMatiereClasseDto {
	private Long id;
	private IdLongCodeLibelleDto matiere;
	private IdLongCodeLibelleDto annee;
	private IdLongCodeLibelleDto classe;
	private IdLongCodeLibelleDto personel;
}

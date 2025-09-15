package com.vieecoles.steph.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonnelMatiereClasseDto {
	private Long id;
	private IdLongCodeLibelleDto matiere;
	private IdLongCodeLibelleDto annee;
	private IdLongCodeLibelleDto classe;
	private IdLongCodeLibelleDto personel;
}

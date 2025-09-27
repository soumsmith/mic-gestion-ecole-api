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
	
	public PersonnelMatiereClasseDto(Long id, IdLongCodeLibelleDto matiere, IdLongCodeLibelleDto annee,
			IdLongCodeLibelleDto classe, IdLongCodeLibelleDto personel) {
		super();
		this.id = id;
		this.matiere = matiere;
		this.annee = annee;
		this.classe = classe;
		this.personel = personel;
	}
	private Long id;
	private IdLongCodeLibelleDto matiere;
	private IdLongCodeLibelleDto annee;
	private IdLongCodeLibelleDto classe;
	private IdLongCodeLibelleDto personel;
	private Boolean isLocked;
}

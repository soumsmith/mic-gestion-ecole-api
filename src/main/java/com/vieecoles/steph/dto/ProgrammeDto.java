package com.vieecoles.steph.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgrammeDto {
	private String ecole;
	private IdStringCodeLibelleDto niveauEnseignement;
	private int nbreProgramme = 0;
	private List<IdStringCodeLibelleDto> programmes;
}

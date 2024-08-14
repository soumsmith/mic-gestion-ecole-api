package com.vieecoles.steph.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgrammeBrancheDto {
	private String programmeId;
	private String programmeCodeLibelle;
	private String niveauId;
	private String niveauCodeLibelle;
	private Integer nbreBranche = 0;
	private List<IdCodeLibelleDto> branches;

}

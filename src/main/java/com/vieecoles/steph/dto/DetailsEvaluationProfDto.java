package com.vieecoles.steph.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailsEvaluationProfDto {
	private Long matiereId;
	private String matiere;
	private Long classeId;
	private String classe;
	private int nbreDevoirExec;
	private int nbreDevoirNonExec;
	private int nbreInterroExec;
	private int nbreInterroNonExec;
	private int nbreTotalExec;
	private int nbreTotalNonExec;
}

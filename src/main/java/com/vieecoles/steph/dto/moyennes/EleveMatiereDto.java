package com.vieecoles.steph.dto.moyennes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vieecoles.steph.dto.IdLongCodeLibelleDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EleveMatiereDto {
	private PersonneDto eleve;
	private IdLongCodeLibelleDto classe;
	private Double moyenne;
	private String observation;
	private Integer rang;
	
	private Double coef;
	private Double moyenneCoef;
	
	private Double moyenneAnnuelle;
	private String observationAnnuelle;
	private Integer rangAnnuel;
	
	private List<MatiereNotes> matieres;
}

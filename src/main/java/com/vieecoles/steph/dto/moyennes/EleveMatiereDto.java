package com.vieecoles.steph.dto.moyennes;

import java.util.List;

import com.vieecoles.steph.dto.IdLongCodeLibelleDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

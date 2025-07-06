package com.vieecoles.steph.dto.moyennes;

import java.util.List;

import com.vieecoles.steph.dto.IdLongCodeLibelleDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClasseMoyenne {
	private IdLongCodeLibelleDto classe;
	private Integer nombreEleve;
	private Integer nombreEleveClasse;
	private Integer nombreEleveNonClasse;
	private PersonneDto professeurPrincipal;
	private Double moyenneClasse;
	private String observation;
	
	private List<EleveMatiereDto> eleves;
}

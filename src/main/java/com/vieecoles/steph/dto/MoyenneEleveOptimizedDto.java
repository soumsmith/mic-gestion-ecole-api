package com.vieecoles.steph.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vieecoles.steph.dto.NoteDto;
import com.vieecoles.steph.dto.moyennes.EcoleMatiereDto;
import com.vieecoles.steph.dto.moyennes.PersonneDto;
import com.vieecoles.steph.entities.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoyenneEleveOptimizedDto implements Comparable<MoyenneEleveOptimizedDto>, Serializable {

	private static final long serialVersionUID = 1L;
	private PersonneDto eleve;
	private ClasseDto classe;
	private IdLongCodeLibelleDto periode;
	private Double moyenne;
	private Double moyenneMatiereToSort;
	private String rang;
	private String isClassed;
	private String classeMatierePeriodeId;
	private String appreciation;
	private List<NoteDto> notes;
	private Map<EcoleMatiereDto, List<NoteDto>> notesMatiereMap;
	private IdLongCodeLibelleDto annee;
	
	private Double moyenneAnnuelle;
	private Double moyenneInterne;
	private Double moyenneIEPP;
	private Double moyennePassage;
	
	private String rangAnnuel;
	private String apprAnnuelle;
	
	private Integer absJust;
	private Integer absNonJust;
	//Type Activite
	private Long typeEvaluation;
	private String typeEvationLibelle;
	
	private String numeroEvaluation; 
	private String numeroIEPP;
	
	private Double moyFr;
	private Double moyFrIntermediaire;
	private Double CoefFr;
	private Double moyCoefFr;
	private Double moyAnFr;
	private Integer rangAnFr;
	private String appreciationAnFr;
	private String appreciationFr;
	private Integer rangFr;
	private Double moyReli;
	private String appreciationReli;

	@Override
	public int compareTo(MoyenneEleveOptimizedDto o) {
		return o.moyenne.compareTo(this.moyenne);
	}

}

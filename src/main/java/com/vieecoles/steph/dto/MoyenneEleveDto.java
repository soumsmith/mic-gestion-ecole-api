package com.vieecoles.steph.dto;

import com.vieecoles.steph.entities.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MoyenneEleveDto implements Comparable<MoyenneEleveDto> {

	private Eleve eleve;
	private Classe classe;
	private Periode periode;
	private Double moyenne;
	private Double moyenneMatiereToSort;
	private String rang;
	private String isClassed;
	private String classeMatierePeriodeId;
	private String appreciation;
	private List<Notes> notes;
	private Map<EcoleHasMatiere, List<Notes>> notesMatiereMap;
	private AnneeScolaire annee;
	
	private Double moyenneAnnuelle;
	private Double moyenneInterne;
	private Double moyenneIEPP;
	private Double moyennePassage;

	private String rangAnnuel;
	
	private Integer absJust;
	private Integer absNonJust;
	//Type Activite
	private Long typeEvaluation;
	private String typeEvationLibelle;
	
	private String numeroEvaluation; 
	private String numeroIEPP;
	
	private Double moyFr;
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
	public int compareTo(MoyenneEleveDto o) {
		return o.moyenne.compareTo(this.moyenne);
	}

}

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
	private String rangAnnuel;
	
	private Integer absJust;
	private Integer absNonJust;

	@Override
	public int compareTo(MoyenneEleveDto o) {
		return o.moyenne.compareTo(this.moyenne);
	}

}

package com.vieecoles.dto;

import java.util.List;
import java.util.Map;

import com.vieecoles.entities.Classe;
import com.vieecoles.entities.Eleve;
import com.vieecoles.entities.Matiere;
import com.vieecoles.entities.Notes;
import com.vieecoles.entities.Periode;

import lombok.Data;

@Data
public class MoyenneEleveDto implements Comparable<MoyenneEleveDto> {
	
	private Eleve eleve;
	private Classe classe;
	private Periode periode;
	private Double moyenne;
	private Double moyenneMatiereToSort;
	private String rang;
	private String appreciation;
	private List<Notes> notes;
	private Map<Matiere, List<Notes>> notesMatiereMap;
	
	
	@Override
	public int compareTo(MoyenneEleveDto o) {
		return o.moyenne.compareTo(this.moyenne);
	}
	
}

package com.vieecoles.dto;

import com.vieecoles.dao.entities.Notes;
import com.vieecoles.dao.entities.Periode;
import com.vieecoles.dao.entities.matiere;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MoyenneEleveDto implements Comparable<MoyenneEleveDto> {

	private com.vieecoles.dao.entities.operations.eleve eleve;
	private com.vieecoles.dao.entities.operations.classe classe;
	private Periode periode;
	private Double moyenne;
	private String rang;
	private String appreciation;
	private List<Notes> notes;
	private Map<matiere, List<Notes>> notesMatiereMap;


	@Override
	public int compareTo(MoyenneEleveDto o) {
		return o.moyenne.compareTo(this.moyenne);
	}

}

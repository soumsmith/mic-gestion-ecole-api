package com.vieecoles.steph.dto.moyennes;

import java.util.List;

import com.vieecoles.steph.dto.IdLongCodeLibelleDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatiereNotes {
	private String matiereId;
	private String matiereLibelle;
	private Double moyenne;
	private Double coef;
	private Double moyenneCoef;
	private Integer rang;
	private String appreciation;
	
	private Double moyenneAnnuelle;
	private Double coefAnnuel;
	private Integer rangAnnuel;
	private String appreciationAnnuelle;
	
	private IdLongCodeLibelleDto classeEleve;
	private String statut;
	private PersonneDto professeur;

	private List<NoteDto> notes;
}

package com.vieecoles.steph.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeanceDto {
	private String id;
	private String heureDebut;
	private String heureFin;
	private String date;
	private String matiereId;
	private String matiereLibelle;
	private String classeId;
	private String classeLibelle;
	private String profId;
	private String profNomPrenom;
	private String appelId;
	//ct equals Cahier de Texte
	private String ctId;
}

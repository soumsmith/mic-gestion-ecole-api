package com.vieecoles.steph.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
	private Integer position;
	private String appelId;
	//ct equals Cahier de Texte
	private String ctId;
}

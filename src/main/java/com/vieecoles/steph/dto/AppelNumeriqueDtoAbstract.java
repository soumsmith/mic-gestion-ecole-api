package com.vieecoles.steph.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AppelNumeriqueDtoAbstract {

	private Long classeId;
	private String classeCode;
	private String classeLibelle;
	private int effectif;
	private String seanceId;
	private String typeSeance;
	private Date dateSeance;
	private Date dateAppel;
	private String heureDebutSeance;
	private String heureFinSeance;
	private String heureDebutAppel;
	private String personnelId;
	private String enseignantNomPrenom;
	private Integer absenceCmpt;
	private Integer presenceCmpt;
	private Long matiereId;
	private String matiereLibelle;
	private int position;
	private int duree;
	private List<AppelEleveDto> eleves;
	
}

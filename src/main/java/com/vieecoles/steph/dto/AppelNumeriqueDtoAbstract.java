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
	private String seance;
	private Date dateSeance;
	private String heureDebutSeance;
	private String heureFinSeance;
	private String enseignantNomPrenom;
	private Long matiereId;
	private String matiereLibelle;
	private List<AppelEleveDto> eleves;
	
	@Getter
	@Setter
	public class AppelEleveDto {
		private Long inscriptionClasseEleveId;
		private String nom;
		private String prenoms;
		private String matricule;
		private String presence;
	}
}

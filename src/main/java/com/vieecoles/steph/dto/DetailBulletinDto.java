package com.vieecoles.steph.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailBulletinDto {
	private String id;
	private Long matiereId;
	private String matiereCode;
	private String matiereLibelle;
	private String categorie;
	private String categorieMatiere;
	private Double moyenne;
	private Integer rang;
	private Double coef;
	private Double moyCoef;
	private String appreciation;
    private Integer numOrdre;
	private String nomPrenomProfesseur ;
	private Double moyAn;
	private String rangAn;
	private String parentMatiere ;
	private String isRanked;
	private String statut;
	private Double adjustMoyenne;
	private Boolean isChecked;
}

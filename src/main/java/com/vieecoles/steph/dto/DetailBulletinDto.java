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
	private String categorieMatiere;
	private Double moyenne;
	private Integer rang;
	private Double coef;
	private Double moyCoef;
	private String appreciation;
	private String  categorie;
    private Integer  num_ordre;
	private String nom_prenom_professeur ;
	private Double moyAn;
	private String rangAn;
	private String parentMatiere ;
	private String isRanked;
}

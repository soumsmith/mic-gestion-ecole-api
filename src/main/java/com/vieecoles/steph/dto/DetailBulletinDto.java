package com.vieecoles.steph.dto;

import java.util.Date;
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
	private String appreciationAn;
	private Integer pec;
	private Integer bonus;
	private Double testLourdNote;
	private Integer testLourdNoteSur;
	private Double moyenneIntermediaire;
	private String sexeProfesseur;
	private Long matiereRealId;
	private String isAdjustment;
	private Date dateCreation;

}

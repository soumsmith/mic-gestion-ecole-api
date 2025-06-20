package com.vieecoles.dto;

import com.vieecoles.steph.entities.Bulletin;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;

@Getter
@Setter
public class DetailBulletinDto {
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
	private Integer pec;
	private Integer bonus;
	private String parentMatiere ;
	private String isRanked;
	private String idbulletin;
	private Date dateCreation;
}

package com.vieecoles.steph.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BulletinDto {
	private String id;
	private Long ecoleId;
	private String nomEcole;
	private Long anneeId;
	private String anneeLibelle;
	private Long periodeId;
	private String libellePeriode;
	private String matricule;
	private String nom;
	private String prenoms;
	private String sexe;
	private Long classeId;
	private String libelleClasse;
	private Integer effectif;
	private Double moyGeneral;
	private String appreciation;
	private String isClassed;
	private Integer rang;
	private String urlPhotoEleve ;

	private List<DetailBulletinDto> details;
}

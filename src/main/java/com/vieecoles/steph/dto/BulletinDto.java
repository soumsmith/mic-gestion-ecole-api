package com.vieecoles.steph.dto;

import java.util.Date;
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
	private String adresseEcole;
	private String telEcole;
	private String dateNaissance;
	private String lieuNaissance;
	private String nationalite;
	private String redoublant;
	private String boursier;
	private String affecte;
	private Double totalCoef;
	private Double totalMoyCoef;
	private String codeProfPrincipal;
	private String nomPrenomProfPrincipal;
	private String codeEducateur;
	private String nomPrenomEducateur;
	private String heuresAbsJustifiees;
	private String heuresAbsNonJustifiees;
	private Double moyMax;
	private Double moyMin;
	private Double moyAvg;
	private Double moyAn;
	private String rangAn;
	private String apprAn;
	private Date dateCreation;
	private Date dateUpdate;
	private String numDecisionAffecte;
	private String lv2;
	private String nature;
	private String codeQr;
	private String statut;
	private String niveau ;
	private Integer ordreNiveau;
	private String ecoleOrigine;
	private String nomSignataire;
	private byte[] photo_eleve ;
	private String niveauLibelle;
	private String effectifNonClasse;
	private Long typeEvaluation;
	private String typeEvaluationLibelle;
	private Integer numeroEvaluation;
	private Integer numeroIEPP;
	private Double moyEvaluationIEPP;
	private Double moyEvaluationInterne;
	private Double moyEvaluationPassage;
	private Double moyFr;
	private Double CoefFr;
	private Double moyCoefFr;
	private Double moyFrIntermediaire;
	private String appreciationFr;
	private Integer rangFr;
	private Double moyFrAn;
	private Integer rangFrAn;
	private String appreciationFrAn;
	private Double moyReli;
	private String appreciationReli;
	private String urlPhoto;
	private Long niveauEnseignementId;
	private  Boolean transfert ;
	private  Boolean ivoirien ;
	private String urlLogo;
	private List<DetailBulletinDto> details;
}

package com.vieecoles.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
public class BulletinDto {
	private String id ;
	private Long ecoleId;
	private String nomEcole;
	private String statutEcole;
	private String urlLogo;
	private String adresseEcole;
	private String telEcole;
	private Long anneeId;
	private String anneeLibelle;
	private Long periodeId;
	private String libellePeriode;
	private String matricule;
	private String nom;
	private String prenoms;
	private String sexe;
	private String dateNaissance;
	private String lieuNaissance;
	private String nationalite;
	private String redoublant;
	private String boursier;
	private String affecte;
	private Long classeId;
	private String libelleClasse;
	private Integer effectif;
	private Double totalCoef;
	private Double totalMoyCoef;
	private String codeProfPrincipal;
	private String nomPrenomProfPrincipal;
	private String codeEducateur;
	private String nomPrenomEducateur;
	private String heuresAbsJustifiees;
	private String heuresAbsNonJustifiees;
	private Double moyGeneral;
	private Double moyMax;
	private Double moyMin;
	private Double moyAvg;
	private Double moyAn;
	private String rangAn;
	private String appreciation;
	private Date dateCreation;
	private Date dateUpdate;
	private String numDecisionAffecte;
	private String lv2;
	private String nature;
	private String isClassed;
	private String codeQr;
	private String statut;
	private Integer rang;
	private String niveau ;
	private Integer ordreNiveau;
	private String ecoleOrigine;
	private String nomSignataire;
	private String transfert;
	@Lob
	@Basic(fetch = FetchType.LAZY)
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
// Details bulletins
   private String matiereCode;
	private String matiereLibelle;
	private String categorieMatiere;
	private Double moyenne;
	private Integer rangMatiere;
	private Double coef;
	private Double moyCoef;
	private String appreciationMatiere;
	private String  categorie;
	private Integer  num_ordre;
	private String nom_prenom_professeur ;
	private Double moyAnMatiere;
	private String rangAnMatiere;
	private Integer pec;
	private Integer bonus;
	private String parentMatiere ;
	private String isRanked;
	private String idbulletin;
	private Date dateCreationMatiere;
}

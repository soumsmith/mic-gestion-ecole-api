package com.vieecoles.dto;

import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportReportDto {
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
	private Integer rangAn;
	private String ApprAn;
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
	private Boolean transfert;
	private String niveauLibelle;
	private String effectifNonClasse;
	private Long typeEvaluation;
	private String typeEvaluationLibelle;
	private Integer numeroEvaluation;
	private Integer numeroIEPP;
	private Double moyEvaluationIEPP;
	private Double moyEvaluationInterne;
	private Double moyEvaluationPassage;
	private Boolean ivoirien;
	private  Long niveauEnseignementId;
	private Long  brancheId;
	//CompFr
	private Double moyenneCompFr;
	private Integer rangCompFr;
	private String nom_prenom_professeurCompFr ;
	private String sexe_professeurCompFr ;
	private Double moyAnCompFr;
	private Integer rangAnCompFr;
	private String isRankedCompFr;
	private String appreciationCompFr;
	private String appreciationAnCompFr;
	//ExpressOrl
	private Double moyenneExpreOral;
	private Integer rangExpreOral;
	private String nom_prenom_professeurExpreOral ;
	private String sexe_professeurExpreOral ;
	private Double moyAnExpreOral;
	private Integer rangAnExpreOral;
	private String isRankedExpreOral;
	private String appreciationExpreOral;
	private String appreciationAnExpreOral;
	//philoso
	private Double moyennephiloso;
	private Integer rangphiloso;
	private String nom_prenom_professeurphiloso ;
	private String sexe_professeurphiloso;
	private Double moyAnphiloso;
	private Integer rangAnphiloso;
	private String isRankedphiloso;
	private String appreciationphiloso;
	private String appreciationAnphiloso;
	//Anglais
	private Double moyenneAnglais;
	private Integer rangAnglais;
	private String nom_prenom_professeurAnglais ;
	private String sexe_professeurAnglais;
	private Double moyAnAnglais;
	private Integer rangAnAnglais;
	private String isRankedAnglais;
	private String appreciationAnglais;
	private String appreciationAnAnglais;
	//Math
	private Double moyenneMath;
	private Integer rangMath;
	private String nom_prenom_professeurMath ;
	private String sexe_professeurMath;
	private Double moyAnMath;
	private Integer rangAnMath;
	private String isRankedMath;
	private String appreciationMath;
	private String appreciationAnMath;
	//Physiq
	private Double moyennePhysiq;
	private Integer rangPhysiq;
	private String nom_prenom_professeurPhysiq ;
	private String sexe_professeurPhysiq;
	private Double moyAnPhysiq;
	private Integer rangAnPhysiq;
	private String isRankedPhysiq;
	private String appreciationPhysiq;
	private String appreciationAnPhysiq;
	// SVT
	private Double moyenneSVT;
	private Integer rangSVT;
	private String nom_prenom_professeurSVT ;
	private String sexe_professeurSVT;
	private Double moyAnSVT;
	private Integer rangAnSVT;
	private String isRankedSVT;
	private String appreciationSVT;
	private String appreciationAnSVT;
	//Hg
	private Double moyenneHg;
	private Integer rangHg;
	private String nom_prenom_professeurHg ;
	private String sexe_professeurHg;
	private Double moyAnHg;
	private Integer rangAnHg;
	private String isRankedHg;
	private String appreciationHg;
	private String appreciationAnHg;
	//Lv2
	private Double moyenneLv2;
	private Integer rangLv2;
	private String nom_prenom_professeurLv2 ;
	private String sexe_professeurLv2;
	private Double moyAnLv2;
	private Integer rangAnLv2;
	private String isRankedLv2;
	private String appreciationLv2;
	private String appreciationAnLv2;
	//EDHC
	private Double moyenneEDHC;
	private Integer rangEDHC;
	private String nom_prenom_professeurEDHC ;
	private String sexe_professeurEDHC;
	private Double moyAnEDHC;
	private Integer rangAnEDHC;
	private String isRankedEDHC;
	private String appreciationEDHC;
	private String appreciationAnEDHC;
	//Arplat
	private Double moyenneArplat;
	private Integer rangArplat;
	private String nom_prenom_professeurArplat ;
	private String sexe_professeurArplat;
	private Double moyAnArplat;
	private Integer rangAnArplat;
	private String isRankedArplat;
	private String appreciationArplat;
	private String appreciationAnArplat;
	// Tic
	private Double moyenneTic;
	private Integer rangTic;
	private String nom_prenom_professeurTic ;
	private String sexe_professeurTic;
	private Double moyAnTic;
	private Integer rangAnTic;
	private String isRankedTic;
	private String appreciationTic;
	private String appreciationAnTic;

	// Conduite
	private Double moyenneConduite;
	private Integer rangConduite;
	private String nom_prenom_professeurConduite ;
	private String sexe_professeurConduite;
	private Double moyAnConduite;
	private String rangAnConduite;
	private String isRankedConduite;
	private String appreciationConduite;
	private String appreciationAnConduite;
	//Eps
	private Double moyenneEps;
	private Integer rangEps;
	private String nom_prenom_professeurEps ;
	private String sexe_professeurEps;
	private Double moyAnEps;
	private Integer rangAnEps;
	private String isRankedEps;
	private String appreciationEps;
	private String appreciationAnEps;


}

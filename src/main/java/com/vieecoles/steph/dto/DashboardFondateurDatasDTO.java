package com.vieecoles.steph.dto;

import lombok.Data;

@Data
public class DashboardFondateurDatasDTO {

	private long nombreTotalEleve;
	private long nombreTotalEleveFilles;
	private long nombreTotalEleveGarcons;
	private long nombreEleveAff;
	private long nombreEleveAffFilles;
	private long nombreEleveAffGarcons;
	private long nombreEleveNonAff;
	private long nombreEleveNonAffFilles;
	private long nombreEleveNonAffGarcons;

	private long nombreEleveNouv;
	private long nombreEleveNouvAffFilles;
	private long nombreEleveNouvAffGarcons;

	private long nombreEleveNouvNonAffFilles;
	private long nombreEleveNouvNonAffGarcons;

	private long nombreEleveAnc;
	private long nombreEleveAncAffFilles;
	private long nombreEleveAncAffGarcons;

	private long nombreEleveAncNonAffFilles;
	private long nombreEleveAncNonAffGarcons;

	private long effectifMoyenClasse;

	private long nombreClasses;
	private long sallesDispo;
	private long sallesNonDispo;
	private long sallesOperationnelles;
	private long sallesNonOperationnelles;
	private long sallesUtilisees;
	private long sallesNonUtilisees;
	
	private long nombrePersonnel;
	private long personnelAdm;
	private long personnelAdmMasculin;
	private long personnelAdmFeminin;
	private long personnelEducateur;
	private long personnelEducateurMasculin;
	private long personnelEducateurFeminin;
	private long personnelEnseignant;
	private long personnelEnseignantMasculin;
	private long personnelEnseignantFeminin;
	
	private long enseignantPermanent;
	private long enseignantPermanentMasculin;
	private long enseignantPermanentFeminin;
	
	private long enseignantVacataire;
	private long enseignantVacataireMasculin;
	private long enseignantVacataireFeminin;
	
}

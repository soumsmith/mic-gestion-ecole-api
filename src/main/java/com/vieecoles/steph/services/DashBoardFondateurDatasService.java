package com.vieecoles.steph.services;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.vieecoles.steph.dto.DashboardFondateurDatasDTO;
import com.vieecoles.steph.entities.Constants;

@RequestScoped
public class DashBoardFondateurDatasService {
	@Inject
	InscriptionService inscriptionService;
	@Inject
	ClasseService classeService;
	@Inject
	SeanceService seanceService;

	public DashboardFondateurDatasDTO buildDashObject(Long ecoleId, Long anneeId) {
		DashboardFondateurDatasDTO dash = new DashboardFondateurDatasDTO();
		try {
			dash.setNombreTotalEleve(
					inscriptionService.getNbreEleveByEcoleAndAnneeAndStatut(ecoleId, anneeId, Constants.VALIDEE));
			dash.setNombreTotalEleveFilles(inscriptionService.getNbreEleveByEcoleAndAnneeAndStatutAndSexe(ecoleId,
					anneeId, Constants.VALIDEE, Constants.FEMININ));
			dash.setNombreTotalEleveGarcons(inscriptionService.getNbreEleveByEcoleAndAnneeAndStatutAndSexe(ecoleId,
					anneeId, Constants.VALIDEE, Constants.MASCULIN));
			dash.setNombreEleveAff(inscriptionService.getNbreEleveAffecteByEcoleAndAnneeAndStatut(ecoleId, anneeId,
					Constants.VALIDEE, Constants.AFFECTE));
			dash.setNombreEleveAffFilles(inscriptionService.getNbreEleveAffecteByEcoleAndAnneeAndStatutAndSexe(ecoleId,
					anneeId, Constants.VALIDEE, Constants.FEMININ, Constants.AFFECTE));
			dash.setNombreEleveAffGarcons(inscriptionService.getNbreEleveAffecteByEcoleAndAnneeAndStatutAndSexe(ecoleId,
					anneeId, Constants.VALIDEE, Constants.MASCULIN, Constants.AFFECTE));
			dash.setNombreEleveNonAff(inscriptionService.getNbreEleveAffecteByEcoleAndAnneeAndStatut(ecoleId, anneeId,
					Constants.VALIDEE, Constants.NON_AFFECTE));
			dash.setNombreEleveNonAffFilles(inscriptionService.getNbreEleveAffecteByEcoleAndAnneeAndStatutAndSexe(
					ecoleId, anneeId, Constants.VALIDEE, Constants.FEMININ, Constants.NON_AFFECTE));
			dash.setNombreEleveNonAffGarcons(inscriptionService.getNbreEleveAffecteByEcoleAndAnneeAndStatutAndSexe(
					ecoleId, anneeId, Constants.VALIDEE, Constants.MASCULIN, Constants.NON_AFFECTE));
			dash.setNombreEleveNouv(inscriptionService.countNewEleve(ecoleId));

			dash.setNombreEleveNouvAffFilles(
					inscriptionService.getNewEleveAffCountBySexe(ecoleId, Constants.FEMININ, Constants.AFFECTE));
			dash.setNombreEleveNouvAffGarcons(
					inscriptionService.getNewEleveAffCountBySexe(ecoleId, Constants.MASCULIN, Constants.AFFECTE));
			dash.setNombreEleveNouvNonAffFilles(
					inscriptionService.getNewEleveAffCountBySexe(ecoleId, Constants.FEMININ, Constants.NON_AFFECTE));
			dash.setNombreEleveNouvNonAffGarcons(
					inscriptionService.getNewEleveAffCountBySexe(ecoleId, Constants.MASCULIN, Constants.NON_AFFECTE));
			dash.setNombreEleveAnc(inscriptionService.countOldEleve(ecoleId));
			dash.setNombreEleveAncAffFilles(
					inscriptionService.getOldEleveAffCountBySexe(ecoleId, Constants.FEMININ, Constants.AFFECTE));
			dash.setNombreEleveAncAffGarcons(
					inscriptionService.getOldEleveAffCountBySexe(ecoleId, Constants.MASCULIN, Constants.AFFECTE));
			dash.setNombreEleveAncNonAffFilles(
					inscriptionService.getOldEleveAffCountBySexe(ecoleId, Constants.FEMININ, Constants.NON_AFFECTE));
			dash.setNombreEleveAncNonAffGarcons(
					inscriptionService.getOldEleveAffCountBySexe(ecoleId, Constants.MASCULIN, Constants.NON_AFFECTE));

			dash.setEffectifMoyenClasse(inscriptionService.getAvgEffectifbyClasse(ecoleId, anneeId));
			dash.setNombreClasses(classeService.countClassesByEcole(ecoleId));
			dash.setSallesDispo(classeService.countClassesByEcole(ecoleId)); // En attendant les fonctionnalités de
																				// gestion des salles de classe
			dash.setSallesOperationnelles(classeService.countClassesByEcole(ecoleId)); // En attendant les
																						// fonctionnalités de gestion
																						// des salles de classe
			dash.setSallesNonOperationnelles(0);
			dash.setSallesUtilisees(seanceService.countSallesUtiliseInSeanceByEcoleAndDate(ecoleId, new Date()));
			dash.setSallesNonUtilisees(dash.getSallesDispo() - dash.getSallesUtilisees());

			dash.setNombrePersonnel(0);

			dash.setPersonnelAdm(0);
			dash.setPersonnelAdmFeminin(0);
			dash.setPersonnelAdmMasculin(0);
			dash.setPersonnelEducateur(0);
			dash.setPersonnelEducateurFeminin(0);
			dash.setPersonnelEducateurMasculin(0);
			dash.setPersonnelEnseignant(0);
			dash.setPersonnelEnseignantFeminin(0);
			dash.setPersonnelEnseignantMasculin(0);
			
			dash.setEnseignantPermanent(0);
			dash.setEnseignantPermanentFeminin(0);
			dash.setEnseignantPermanentMasculin(0);
			dash.setEnseignantVacataire(0);
			dash.setEnseignantVacataireFeminin(0);
			dash.setEnseignantVacataireMasculin(0);
			
		} catch (RuntimeException r) {
			r.printStackTrace();
		}

		return dash;
	}
}

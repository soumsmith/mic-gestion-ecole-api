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
	@Inject
	PersonnelService personnelService;

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

			dash.setNombrePersonnel(personnelService.countByEcole(ecoleId));

			dash.setPersonnelAdm(personnelService.countByFonction(3, ecoleId)+personnelService.countByFonction(4, ecoleId));
			dash.setPersonnelAdmFeminin(personnelService.countByGenreAndFonction(ecoleId, 3, Constants.FEMININ)+personnelService.countByGenreAndFonction(ecoleId, 4, Constants.FEMININ));
			dash.setPersonnelAdmMasculin(personnelService.countByGenreAndFonction(ecoleId, 3, Constants.MASCULIN)+personnelService.countByGenreAndFonction(ecoleId, 4, Constants.MASCULIN));
			dash.setPersonnelEducateur(personnelService.countByFonction(2, ecoleId));
			dash.setPersonnelEducateurFeminin(personnelService.countByGenreAndFonction(ecoleId, 2, Constants.FEMININ));
			dash.setPersonnelEducateurMasculin(personnelService.countByGenreAndFonction(ecoleId, 2, Constants.MASCULIN));
			dash.setPersonnelEnseignant(personnelService.countByFonction(1, ecoleId));
			dash.setPersonnelEnseignantFeminin(personnelService.countByGenreAndFonction(ecoleId, 1, Constants.FEMININ));
			dash.setPersonnelEnseignantMasculin(personnelService.countByGenreAndFonction(ecoleId, 1, Constants.MASCULIN));
			
			dash.setEnseignantPermanent(personnelService.countByEcoleAndFonctionAndStatut(ecoleId, 1, Constants.PERMANENT));
			dash.setEnseignantPermanentFeminin(personnelService.countByGenreAndFonctionAndStatut(ecoleId, 1, Constants.FEMININ, Constants.PERMANENT));
			dash.setEnseignantPermanentMasculin(personnelService.countByGenreAndFonctionAndStatut(ecoleId, 1, Constants.MASCULIN, Constants.PERMANENT));
			dash.setEnseignantVacataire(personnelService.countByEcoleAndFonctionAndStatut(ecoleId, 1, Constants.VACATAIRE));
			dash.setEnseignantVacataireFeminin(personnelService.countByGenreAndFonctionAndStatut(ecoleId, 1, Constants.FEMININ, Constants.VACATAIRE));
			dash.setEnseignantVacataireMasculin(personnelService.countByGenreAndFonctionAndStatut(ecoleId, 1, Constants.MASCULIN, Constants.VACATAIRE));
			
		} catch (RuntimeException r) {
			r.printStackTrace();
		}

		return dash;
	}
}

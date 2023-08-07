package com.vieecoles.steph.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.vieecoles.steph.dto.DashboardFondateurDatasDTO;
import com.vieecoles.steph.entities.Constants;

@RequestScoped
public class DashBoardFondateurDatasService {
	@Inject
	InscriptionService inscriptionService;
	
	
	public DashboardFondateurDatasDTO buildDashObject(Long ecoleId, Long anneeId) {
		DashboardFondateurDatasDTO dash = new DashboardFondateurDatasDTO();
		try {
		dash.setNombreTotalEleve(inscriptionService.getNbreEleveByEcoleAndAnneeAndStatut(ecoleId, anneeId, Constants.VALIDEE));
		dash.setNombreTotalEleveFilles(inscriptionService.getNbreEleveByEcoleAndAnneeAndStatutAndSexe(ecoleId, anneeId, Constants.VALIDEE, Constants.FEMININ));
		dash.setNombreTotalEleveGarcons(inscriptionService.getNbreEleveByEcoleAndAnneeAndStatutAndSexe(ecoleId, anneeId, Constants.VALIDEE, Constants.MASCULIN));
		dash.setNombreEleveAff(inscriptionService.getNbreEleveAffecteByEcoleAndAnneeAndStatut(ecoleId, anneeId, Constants.VALIDEE, Constants.AFFECTE));
		dash.setNombreEleveAffFilles(inscriptionService.getNbreEleveAffecteByEcoleAndAnneeAndStatutAndSexe(ecoleId, anneeId, Constants.VALIDEE, Constants.FEMININ, Constants.AFFECTE));
		dash.setNombreEleveAffGarcons(inscriptionService.getNbreEleveAffecteByEcoleAndAnneeAndStatutAndSexe(ecoleId, anneeId, Constants.VALIDEE, Constants.MASCULIN, Constants.AFFECTE));
		dash.setNombreEleveNonAff(inscriptionService.getNbreEleveAffecteByEcoleAndAnneeAndStatut(ecoleId, anneeId, Constants.VALIDEE, Constants.NON_AFFECTE));
		dash.setNombreEleveNonAffFilles(inscriptionService.getNbreEleveAffecteByEcoleAndAnneeAndStatutAndSexe(ecoleId, anneeId, Constants.VALIDEE, Constants.FEMININ, Constants.NON_AFFECTE));
		dash.setNombreEleveNonAffGarcons(inscriptionService.getNbreEleveAffecteByEcoleAndAnneeAndStatutAndSexe(ecoleId, anneeId, Constants.VALIDEE, Constants.MASCULIN, Constants.NON_AFFECTE));
		dash.setNombreEleveNouv(inscriptionService.countNewEleve(ecoleId));
		
		dash.setNombreEleveNouvAffFilles(inscriptionService.getNbreEleveAffecteByEcoleAndAnneeAndStatutAndSexe(ecoleId, anneeId, Constants.VALIDEE, Constants.FEMININ, Constants.AFFECTE));
		dash.setNombreEleveNouvAffGarcons(inscriptionService.getNbreEleveAffecteByEcoleAndAnneeAndStatutAndSexe(ecoleId, anneeId, Constants.VALIDEE, Constants.MASCULIN, Constants.AFFECTE));
		dash.setNombreEleveNouvNonAffFilles(inscriptionService.getNbreEleveAffecteByEcoleAndAnneeAndStatutAndSexe(ecoleId, anneeId, Constants.VALIDEE, Constants.FEMININ, Constants.NON_AFFECTE));
		dash.setNombreEleveNouvNonAffGarcons(inscriptionService.getNbreEleveAffecteByEcoleAndAnneeAndStatutAndSexe(ecoleId, anneeId, Constants.VALIDEE, Constants.MASCULIN, Constants.NON_AFFECTE));
		dash.setNombreEleveAnc(0);
		dash.setNombreEleveAncAffFilles(0);
		dash.setNombreEleveAncAffGarcons(0);
		dash.setNombreEleveAncNonAffFilles(0);
		dash.setNombreEleveAncNonAffGarcons(0);
		} catch(RuntimeException r) {
			r.printStackTrace();
		}
		
		return dash;
	}
}

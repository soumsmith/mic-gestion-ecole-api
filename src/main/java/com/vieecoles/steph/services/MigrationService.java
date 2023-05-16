package com.vieecoles.steph.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.vieecoles.steph.entities.Activite;
import com.vieecoles.steph.entities.ClasseMatiere;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.EcoleHasMatiere;
import com.vieecoles.steph.entities.Evaluation;
import com.vieecoles.steph.entities.Matiere;
import com.vieecoles.steph.entities.PersonnelMatiereClasse;
import com.vieecoles.steph.entities.Seances;

@ApplicationScoped
public class MigrationService {

	@Inject
	MatiereService matiereService;
	@Inject
	EcoleService ecoleService;
	@Inject
	EcoleHasMatiereService ecoleHasMatiereService;

	// A tester
	@Transactional
	public void doHandleMigrationMatereEcole() {
		
		System.out.println("****** MIGRATION TO ECOLE MATIERE *******");
		// recuperer les matieres sources dans la table des matieres
		List<Matiere> matieres = matiereService.getList();
		List<Ecole> ecoles = ecoleService.getList();
		Boolean flat = true;

		// pour chaque ecole:
		for (Ecole ecole : ecoles) {
			System.out.println("--> ecole : " + ecole.getLibelle());
			List<EcoleHasMatiere> matieresEcole = ecoleHasMatiereService.getListByEcole(ecole.getId());
			// - verifier si la matiere existe déja dans la table ecoleHasMatiere (id src)
			// par rapport au nveau d'enseignement
			for (Matiere matiere : matieres) {
				flat = true;
				for (EcoleHasMatiere ecoleMat : matieresEcole) {
					if (matiere.getId().equals(ecoleMat.getMatiere().getId())) {
						flat = false;
						break;
					}
				}

				if (flat) {
					// si non la copier
					System.out.println("----> Insertion matiere : " + matiere.getId() + " - " + matiere.getLibelle());
					ecoleHasMatiereService.createMatiereToOneEcole(matiere, ecole);
				}
			}

//			matieresEcole = ecoleHasMatiereService.getListByEcole(ecole.getId());
//			System.out.println("Mise à jour des nouvelles matieres pour l'école ");
//			for (EcoleHasMatiere ehm : matieresEcole) {
//				// - Pour chaque evaluation remplacer l'id de la matiere source par la matiere
//				// ecole
//				System.out.println("ehm matiere : " + ehm.getMatiere().getId());
//
//				System.out.println("Maj new matiere value : " + ehm.getId() + " replaces value matiere "
//						+ ehm.getMatiere().getId() + " " + ehm.getMatiere().getLibelle());
////				System.out.println("nbre datas : "+ Evaluation.find("matiereEcole.id = ?1", ehm.getId()).count());
////				System.out.println("count "+ Evaluation.find("matiereEcole.id = ?1", ehm.getMatiere().getId()).count());
//
//				List<Evaluation> evaluationsEcole = Evaluation
//						.find("classe.ecole.id= ?1 and matiereEcole.id=?2", ecole.getId(), ehm.getMatiere().getId())
//						.list();
//
//				for (Evaluation evaluation : evaluationsEcole) {
//					System.out.println("id :"+evaluation.getId());
//					evaluation.setMatiereEcole(ehm);
//				}
//				System.out.println("-> Evaluation [ok]");
//
//				// - Pour chaque professeur dans personnel_matiere_classe
//				List<PersonnelMatiereClasse> personnelsEcole = PersonnelMatiereClasse
//						.find("classe.ecole.id= ?1 and matiere.id=?2", ecole.getId(), ehm.getMatiere().getId()).list();
//				for (PersonnelMatiereClasse pcm : personnelsEcole) {
//					pcm.setMatiere(ehm);
//				}
//				System.out.println("-> PersonnelMatiereClasse [ok]");
//
//				// Maj des coeficients dans Classe_matiere
//				List<ClasseMatiere> classeMatieresEcole = ClasseMatiere
//						.find("classe.ecole.id= ?1 and matiere.id=?2", ecole.getId(), ehm.getMatiere().getId()).list();
//				for (ClasseMatiere cme : classeMatieresEcole) {
//					cme.setMatiere(ehm);
//				}
//				System.out.println("-> ClasseMatiere [ok]");
//				// Maj dans Activite
//				List<Activite> activitesEcole = Activite
//						.find("classe.ecole.id= ?1 and matiere.id=?2", ecole.getId(), ehm.getMatiere().getId()).list();
//				for(Activite atv : activitesEcole) {
//					atv.setMatiere(ehm);
//				}
//				System.out.println("-> Activite [ok]");
//				// Maj dans Seance
//				List<Seances> seancesEcole = Seances
//						.find("classe.ecole.id= ?1 and matiere.id=?2", ecole.getId(), ehm.getMatiere().getId()).list();
//				
//				for(Seances sc:seancesEcole) {
//					sc.setMatiere(ehm);
//				}
//				System.out.println("-> Seances [ok]");
//
//			}
		}
		System.out.println("****** FIN MIGRATION PROCESS *******");
	}
}

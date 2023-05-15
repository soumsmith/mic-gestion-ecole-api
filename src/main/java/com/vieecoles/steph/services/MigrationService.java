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
		// recuperer les matieres sources dans la table des matieres
		List<Matiere> matieres = matiereService.getList();
		List<Ecole> ecoles = ecoleService.getList();
		Boolean flat = true;

		// pour chaque ecole:
		for (Ecole ecole : ecoles) {
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
					ecoleHasMatiereService.createMatiereToOneEcole(matiere, ecole);
				}
			}

			matieresEcole = ecoleHasMatiereService.getListByEcole(ecole.getId());
			for (EcoleHasMatiere ehm : matieresEcole) {
				// - Pour chaque evaluation remplacer l'id de la matiere source par la matiere
				// ecole
				Evaluation.update(
						"update Evaluation set matiereEcole.id = ?1 where classe.ecole.id= ?2 and matiere.id = ?3",
						ehm.getId(), ecole.getId(), ehm.getMatiere().getId());
				// - Pour chaque professeur dans personnel_matiere_classe
				PersonnelMatiereClasse.update(
						"update PersonnelMatiereClasse set matiere = ?1 where classe.ecole.id= ?2 and matiere=?3 ",
						ehm.getId(), ecole.getId(), ehm.getMatiere().getId());
				// Maj des coeficients dans Classe_matiere
				ClasseMatiere.update("update ClasseMatiere set matiere = ?1 where ecole.id= ?2 and matiere=?3 ",
						ehm.getId(), ecole.getId(), ehm.getMatiere().getId());
				// Maj dans Activite
				
				Activite.update("update Activite set matiere = ?1 where ecole.id= ?2 and matiere=?3 ",ehm.getId(), ecole.getId(), ehm.getMatiere().getId());
				// Maj dans Seance
				Seances.update("update Seances set matiere = ?1 where classe.ecole.id= ?2 and matiere=?3 ",ehm.getId(), ecole.getId(), ehm.getMatiere().getId());

			}
		}
	}
}

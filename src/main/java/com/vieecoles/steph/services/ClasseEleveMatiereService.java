package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import com.vieecoles.steph.entities.ClasseEleveMatiere;
import com.vieecoles.steph.entities.ClasseElevePeriode;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@RequestScoped
public class ClasseEleveMatiereService implements PanacheRepositoryBase<ClasseEleveMatiere, String> {

	Logger logger = Logger.getLogger(ClasseEleveMatiere.class.getName());

	public List<ClasseEleveMatiere> getList() {
		return ClasseEleveMatiere.listAll();
	}

	public ClasseEleveMatiere getById(String id) {
		return ClasseEleveMatiere.findById(id);
	}

	public ClasseEleveMatiere findByClasseAndMatiereAndEleveAndAnneeAndPeriode(long classe, long matiere, long eleve,
			long annee, long periode) {
		ClasseEleveMatiere cem = null;
		try {
			logger.info(String.format("findByClasseAndMatiereAndEleveAndAnneeAndPeriode classe %s - matiere %s - eleve %s - annee %s - periode %s ", classe,matiere, eleve, annee, periode));
			cem = ClasseEleveMatiere
					.find("classe.id =?1 and matiere.id = ?2 and eleve.id = ?3 and annee.id=?4 and periode.id =?5",
							classe, matiere, eleve, annee, periode)
					.singleResult();
		} catch (RuntimeException ex) {
			if(ex.getClass().getName().equals(NoResultException.class.getName()))
//			logger.warning(String.format(
//					"Aucune classe elève matiere trouvée [classe %s] [matiere %s] [eleve %s] [annee %s] [periode %s]",
//					String.valueOf(classe), String.valueOf(matiere), String.valueOf(eleve), String.valueOf(annee),
//					String.valueOf(periode)));
				System.out.print("--- No result ex findByClasseAndMatiereAndEleveAndAnneeAndPeriode");
			else
				ex.printStackTrace();
		}
		return cem;
	}
	
	public List<ClasseEleveMatiere> findByClasseAnneeAndPeriode(long classe, long annee, long periode) {
		List<ClasseEleveMatiere> cemList;
		try {
			cemList = ClasseEleveMatiere
					.find("classe.id =?1 and annee.id=?2 and periode.id =?3",
							classe, annee, periode)
					.list();
		} catch (RuntimeException ex) {
			if(ex.getClass().getName().equals(NoResultException.class.getName()))
				System.out.print("-");
			else
				ex.printStackTrace();
			cemList = new ArrayList<>();
		}
		return cemList;
	}
	
	/**
	 * Savoir les restrictions de classement sur un eleve dans les matieres enseignées.
	 * 
	 * @param classe
	 * @param eleve
	 * @param annee
	 * @param periode
	 * @return
	 */
	public List<ClasseEleveMatiere> findByClasseAndEleveAndAnneeAndPeriode(long classe, long eleve, long annee, long periode) {
		List<ClasseEleveMatiere> cemList;
		try {
			cemList = ClasseEleveMatiere
					.find("classe.id =?1 and eleve.id = ?2 and annee.id=?3 and periode.id =?4",
							classe, eleve, annee, periode)
					.list();
		} catch (RuntimeException ex) {
			if(ex.getClass().getName().equals(NoResultException.class.getName()))
				System.out.print("-");
			else
				ex.printStackTrace();
			cemList = new ArrayList<>();
		}
		return cemList;
	}

	@Transactional
	public void create(ClasseEleveMatiere classeEleveMatiere) {
		classeEleveMatiere.setId(UUID.randomUUID().toString());
		classeEleveMatiere.persist();
	}

	@Transactional
	public ClasseEleveMatiere update(String id, ClasseEleveMatiere classeEleveMatiere) {
		ClasseEleveMatiere entity = ClasseEleveMatiere.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.setIsClassed(classeEleveMatiere.getIsClassed());
		return entity;
	}

	@Transactional
	public void handleMarquageClassement(ClasseEleveMatiere classeEleveMatiere) {

		logger.info(String.format("%s %s %s %s %s", classeEleveMatiere.getClasse().getId(),
				classeEleveMatiere.getMatiere(), classeEleveMatiere.getEleve().getId(),
				classeEleveMatiere.getAnnee().getId(), classeEleveMatiere.getPeriode().getId()));

		ClasseEleveMatiere cem = findByClasseAndMatiereAndEleveAndAnneeAndPeriode(
				classeEleveMatiere.getClasse().getId(), classeEleveMatiere.getMatiere().getId(),
				classeEleveMatiere.getEleve().getId(), classeEleveMatiere.getAnnee().getId(),
				classeEleveMatiere.getPeriode().getId());

		if (cem != null) {
			cem.setIsClassed(classeEleveMatiere.getIsClassed());
		} else {
			create(classeEleveMatiere);
		}
	}

}

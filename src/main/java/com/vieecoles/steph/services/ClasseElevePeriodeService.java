package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import com.vieecoles.steph.entities.ClasseElevePeriode;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@RequestScoped
public class ClasseElevePeriodeService implements PanacheRepositoryBase<ClasseElevePeriode, String> {
	Logger logger = Logger.getLogger(ClasseElevePeriodeService.class.getName());

	public List<ClasseElevePeriode> getList() {
		return ClasseElevePeriode.listAll();
	}

	public ClasseElevePeriode getById(String id) {
		return ClasseElevePeriode.findById(id);
	}

	public ClasseElevePeriode findByClasseAndEleveAndAnneeAndPeriode(long classe, long eleve, long annee,
			long periode) {
		ClasseElevePeriode cep = null;
		try {
			cep = ClasseElevePeriode.find("classe.id =?1 and eleve.id = ?2 and annee.id=?3 and periode.id =?4", classe,
					eleve, annee, periode).singleResult();
		} catch (RuntimeException e) {
			if (e.getClass().getName().equals(NoResultException.class.getName()))
				logger.info("Aucune donnée sur le marquage de non classement d'un élève trouvé");
			else
				e.printStackTrace();
		}

		return cep;
	}

	public List<ClasseElevePeriode> listByClasseAndAnneeAndPeriode(long classe, long annee, long periode) {
		List<ClasseElevePeriode> ceps;
		try {
			ceps = ClasseElevePeriode.find("classe.id =?1 and annee.id=?2 and periode.id =?3", classe, annee, periode)
					.list();
		} catch (RuntimeException e) {
			if (e.getClass().getName().equals(NoResultException.class.getName()))
				logger.info("Aucune donnée sur le marquage de non classement des élèves de la classe [id = " + classe
						+ "] trouvé");
			else {
				e.printStackTrace();
			}
			ceps = new ArrayList<ClasseElevePeriode>();
		}

		return ceps;
	}

	@Transactional
	public void handleMarquageClassement(ClasseElevePeriode classeElevePeriode) {

		ClasseElevePeriode cep = findByClasseAndEleveAndAnneeAndPeriode(classeElevePeriode.getClasse().getId(),
				classeElevePeriode.getEleve().getId(), classeElevePeriode.getAnnee().getId(),
				classeElevePeriode.getPeriode().getId());

		if (cep != null) {
			cep.setIsClassed(classeElevePeriode.getIsClassed());
		} else {
			create(classeElevePeriode);
		}
	}

	@Transactional
	public void create(ClasseElevePeriode classeElevePeriode) {
		logger.info("--> Création de classe eleve periode");
		classeElevePeriode.setId(UUID.randomUUID().toString());
		classeElevePeriode.persist();
	}

	@Transactional
	public ClasseElevePeriode update(String id, ClasseElevePeriode classeElevePeriode) {
		ClasseElevePeriode entity = ClasseElevePeriode.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.setIsClassed(classeElevePeriode.getIsClassed());
		return entity;
	}

}

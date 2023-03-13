package com.vieecoles.steph.services;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import com.vieecoles.steph.entities.ClasseElevePeriode;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@RequestScoped
public class ClasseElevePeriodeService implements PanacheRepositoryBase<ClasseElevePeriode, String> {
	Logger logger  = Logger.getLogger(ClasseElevePeriodeService.class.getName());
	
	public List<ClasseElevePeriode> getList() {
		return ClasseElevePeriode.listAll();
	}

	public ClasseElevePeriode getById(String id) {
		return ClasseElevePeriode.findById(id);
	}

	public ClasseElevePeriode findByClasseAndEleveAndAnneeAndPeriode(long classe, long eleve, long annee, long periode) {
		ClasseElevePeriode cep = null;
		try {
			cep = ClasseElevePeriode.find("classe.id =?1 and eleve.id = ?2 and annee.id=?3 and periode.id =?4", classe, eleve, annee, periode).singleResult();
		}catch(RuntimeException e) {
			if(e.getClass().getName().equals(NoResultException.class.getName()))
				logger.info("Aucune donnée sur le marquage de non classement d'un élève trouvé");
			return cep;
		}
		
		return cep;
	}

	@Transactional
	public void create(ClasseElevePeriode classeElevePeriode) {
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

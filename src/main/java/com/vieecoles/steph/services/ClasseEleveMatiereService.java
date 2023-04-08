package com.vieecoles.steph.services;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import com.vieecoles.steph.entities.ClasseEleveMatiere;

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
		cem =  ClasseEleveMatiere.find("classe.id =?1 and matiere.id = ?2 and eleve.id = ?3 and annee.id=?4 and periode.id =?5", classe,
				matiere, eleve, annee, periode).singleResult();
		}catch(RuntimeException ex) {
			logger.warning(String.format("Aucune classe elève matiere trouvée [classe %s] [matiere %s] [eleve %s] [annee %s] [periode %s]", String.valueOf(classe) , String.valueOf(matiere),String.valueOf(eleve), String.valueOf(annee),String.valueOf(periode)));
		}
		return cem;
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

}

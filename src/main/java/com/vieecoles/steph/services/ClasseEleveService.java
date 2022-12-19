package com.vieecoles.ressource.steph.services;

import com.vieecoles.entities.Classe;
import com.vieecoles.entities.ClasseEleve;
import com.vieecoles.entities.Inscription;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class ClasseEleveService implements PanacheRepositoryBase<ClasseEleve, Long> {

	Logger logger = Logger.getLogger(ClasseEleveService.class.getName());

	public List<ClasseEleve> findByid(Long id) {
		return ClasseEleve.findById(id);
	}

	public List<ClasseEleve> getList() {
		return ClasseEleve.listAll();
	}

	public void create(ClasseEleve ce) {
		ce.persist();
	}

	@Transactional
	public List<ClasseEleve> handleCreate(long classeId, List<Inscription> inscriptions) {
		ClasseEleve classeEleve = new ClasseEleve();
		List<ClasseEleve> classeEleves = new ArrayList<ClasseEleve>();
		Classe classe = new Classe();
		classe.setId(classeId);
		classeEleve.setClasse(classe);
		classeEleve.setDateCreation(new Date());
		classeEleve.setStatut("ACTIF");
		classeEleve.setDateUpdate(new Date());
		for (Inscription ins : inscriptions) {
			classeEleve.setInscription(ins);
			create(classeEleve);
			classeEleves.add(classeEleve);
		}
		return classeEleves;
	}

	@Transactional
	public ClasseEleve update(ClasseEleve classeEleve) {
		ClasseEleve entity = ClasseEleve.findById(classeEleve.getId());
		if (entity == null) {
			throw new NotFoundException();
		}
		// get classe annee by id + eleve by id pour set
//			entity.setClasseAnnee(classeEleve.getClasseAnnee());
		entity.setInscription(classeEleve.getInscription());
		entity.setClasse(classeEleve.getClasse());
		entity.setStatut(classeEleve.getStatut());
		entity.setDateUpdate(classeEleve.getDateUpdate());

		return entity;
	}

	@Transactional
	public void delete(long id) {
		logger.info(String.format("Id to delete %s", id));
		ClasseEleve entity = ClasseEleve.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.delete();
	}

	public List<ClasseEleve> getByIds(List<Long> ids){
		List<ClasseEleve> list = new ArrayList<ClasseEleve>();
		for(Long id : ids) {
			list.add(ClasseEleve.findById(id));
		}
		return list;
	}

	public ClasseEleve updateAndDisplay(ClasseEleve classeEleve) {
		ClasseEleve ce;
		if (update(classeEleve) != null) {
			ce = findById(classeEleve.getId());
			return ce;
		}

		return null;
	}

	public List<ClasseEleve> getByClasseAnnee(Long classeId, Long anneeId) {
		return ClasseEleve.find("classe.id = ?1 and inscription.annee.id = ?2", classeId, anneeId).list();
	}

	public int getCountByClasseAnnee(Long classeId, Long anneeId) {
		return ClasseEleve.find("classe.id = ?1 and inscription.annee.id = ?2", classeId, anneeId).list().size();
	}

	public List<ClasseEleve> getByBrancheAndAnnee(Long brancheId, Long anneeId) {
		return ClasseEleve.find("inscription.branche.id = ?1 and inscription.annee.id = ?2", brancheId, anneeId).list();
	}

	public long count() {
		return ClasseEleve.count();
	}

}

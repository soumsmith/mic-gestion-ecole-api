package com.vieecoles.steph.services;

import com.google.gson.Gson;
import com.vieecoles.steph.entities.Evaluation;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RequestScoped
public class EvaluationService implements PanacheRepositoryBase<Evaluation, Long> {

	Logger logger = Logger.getLogger(EvaluationService.class.getName());

	public List<Evaluation> getList() {
		return Evaluation.listAll();
	}

	public Evaluation findById(Long id) {
		return Evaluation.findById(id);
	}

	public Evaluation findByCode(String code) {
		return Evaluation.find("code", code).singleResult();
	}

	Integer setPecValue() {
		return 0;
	}

	@Transactional
	public Evaluation create(Evaluation ev) {
//		Gson gson = new Gson();
//		logger.info(gson.toJson(ev));
		try {
		UUID uuid = UUID.randomUUID();
		logger.info("Creation de l'evaluation "+uuid.toString());
		ev.setCode(uuid.toString());
		ev.setDateCreation(new Date());
		ev.setPec(setPecValue());
		ev.persist();
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
		logger.info("id creation "+ev.getId());
		return ev;
	}

	@Transactional
	public Evaluation update(Evaluation ev) {
		Evaluation entity = Evaluation.findById(ev.getId());
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.setId(ev.getId());
		entity.setCode(ev.getCode());
		entity.setDate(ev.getDate());
		entity.setDateLimite(ev.getDateLimite());
		entity.setDuree(ev.getDuree());
		entity.setEtat(ev.getEtat());
		entity.setPec(ev.getPec());
		entity.setType(ev.getType());
		//entity.setHeure(ev.getHeure());
		entity.setNoteSur(ev.getNoteSur());
		return entity;
	}

	public Evaluation updateAndDisplay(Evaluation evaluation) {
		Evaluation ev;
		if (update(evaluation) != null) {
			ev = findById(evaluation.getId());
			return ev;
		}

		return null;

	}

	@Transactional
	public void delete(long id) {
		Evaluation entity = Evaluation.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.delete();
	}

	// obténir la liste des évaluations (l évaluation) par le code
	public List<Evaluation> search(String code) {
		return Evaluation.find("code", code).list();
	}

	public List<Evaluation> getByClasseAndMatiere(Long classeId, Long matiereId) {
		return Evaluation.find("annee.id=?1 and classe.id=?2 and matiere.id=?3 ",Long.parseLong("1"), classeId, matiereId).list();
	}


	public List<Evaluation> getByClasseAndMatiereAndPeriode(Long classeId, Long matiereId, Long periodeId, Long anneeId) {
		return Evaluation.find("annee.id=?1 and classe.id=?2 and matiere.id=?3 and periode.id",anneeId, classeId, matiereId, periodeId).list();
	}

	public Long getCountByClasseAndMatiere(Long classeId, Long matiereId) {
		return Evaluation.find("annee.id=?1 and classe.id=?2 and matiere.id=?3",Long.parseLong("1"), classeId, matiereId).count();
	}

	// Faire une recherche via le champ
	public List<Evaluation> search(String query, Parameters params) {
		return Evaluation.find(query,params).list();
	}

	public long count() {
		return Evaluation.count();
	}
}

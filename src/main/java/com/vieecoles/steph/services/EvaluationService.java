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
		Evaluation evaList = null;

		try {
			evaList = Evaluation.find("code", code).singleResult();
		} catch (RuntimeException ex) {
			logger.warning("Probably no result found");
		}
		return  evaList;
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
			logger.info("Creation de l'evaluation " + uuid.toString());
			ev.setCode(uuid.toString());
			ev.setDateCreation(new Date());
			ev.setPec(setPecValue());
			ev.persist();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		logger.info("id creation " + ev.getId());
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
		// entity.setHeure(ev.getHeure());
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
		List<Evaluation> evaList = null;

		try {
			evaList = Evaluation.find("code", code).list();
		} catch (RuntimeException ex) {
			logger.warning("Probably no result found");
		}

		return evaList;
	}

	public List<Evaluation> getByClasseAndMatiere(Long anneeId, Long classeId, Long matiereId) {
		List<Evaluation> evaList = null;

		try {
			evaList = Evaluation
					.find("annee.id=?1 and classe.id=?2 and matiereEcole.id=?3 ", anneeId, classeId, matiereId).list();
		} catch (RuntimeException ex) {
			logger.warning("Probably no result found");
		}
		return evaList;
	}

	public List<Evaluation> getByClasseAndMatiereAndPeriode(Long classeId, Long matiereId, Long periodeId,
			Long anneeId) {
		logger.info(String.format("classse: %s  | annee : %s  | matiere : %s  | periode : %s", classeId, anneeId,
				matiereId, periodeId));
		List<Evaluation> evaList = null;

		try {
			evaList = Evaluation.find("annee.id=?1 and classe.id=?2 and matiereEcole.id=?3 and periode.id=?4", anneeId,
					classeId, matiereId, periodeId).list();
		} catch (RuntimeException ex) {
			logger.warning("Probably no result found");
		}
		return evaList;
	}

	public Long getCountByClasseAndMatiere(Long anneeId, Long classeId, Long matiereId) {
		return Evaluation
				.find("annee.id=?1 and classe.id=?2 and matiereEcole.id=?3", anneeId, classeId, matiereId)
				.count();
	}

	// Faire une recherche via le champ
	public List<Evaluation> search(String query, Parameters params) {
		List<Evaluation> evaList = null;

		try {
			evaList = Evaluation.find(query, params).list();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			logger.warning("Probably no result found");
		}
		return evaList;
	}

	public long count() {
		return Evaluation.count();
	}
}

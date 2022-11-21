package com.vieecoles.services;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.vieecoles.entities.Evaluation;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class EvaluationService implements PanacheRepositoryBase<Evaluation, Long> {

	public List<Evaluation> getList() {
		return Evaluation.listAll();
	}

	public Evaluation findById(Long id) {
		return Evaluation.findById(id);
	}

	public Evaluation findByCode(String code) {
		return Evaluation.find("code", code).singleResult();
	}

	@Transactional
	public Response create(Evaluation ev) {
		UUID uuid = UUID.randomUUID();
		ev.setCode(uuid.toString());
		ev.persist();
		return Response.ok("Evaluation creee").build();
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
		entity.setHeure(ev.getHeure());
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
	
	// Faire une recherche via le champ
	public List<Evaluation> search(String query, Parameters params) {
		return Evaluation.find(query,params).list();
	}

	public long count() {
		return Evaluation.count();
	}
}

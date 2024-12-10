package com.vieecoles.steph.services;

import com.vieecoles.steph.dto.moyennes.PersonneDto;
import com.vieecoles.steph.entities.Eleve;
import com.vieecoles.steph.projections.PersonProjection;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class EleveService implements PanacheRepositoryBase<Eleve, Long> {

	public List<Eleve> getList() {
		return Eleve.listAll();
	}

	public Eleve findById(Long id) {
		return Eleve.findById(id);
	}

	public Eleve findByMatricule(String matricule) {
		return Eleve.find("matricule =?1", matricule).singleResult();
	}

	public Response create(Eleve eleve) {
		eleve.persist();
		return Response.created(URI.create("/eleve/" + eleve.getId())).build();
	}

	public Eleve update(long id, Eleve eleve) {
		Eleve entity = Eleve.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.setNom(eleve.getNom());
		entity.setMatricule(eleve.getMatricule());
		entity.setPrenom(eleve.getPrenom());
		entity.setDateNaissance(eleve.getDateNaissance());
		return entity;
	}

	public void delete(long id) {
		Eleve entity = Eleve.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.delete();
	}

	public List<Eleve> search(String nom) {
		return Eleve.find("nom", nom).list();
	}

	public long count() {
		return Eleve.count();
	}

	public List<PersonProjection> findEleveProjection() {
		List<PersonProjection> projectList = new ArrayList<>();
		try {
			projectList = getEntityManager().createQuery("SELECT "
					+ " new com.vieecoles.steph.projections.PersonProjection(e.id, e.matricule, e.nom, e.prenom, e.sexe) "
					+ " FROM Eleve e").getResultList();
		} catch (Exception e) {
			projectList = new ArrayList<>();
		}
		return projectList;
	}
	
	public PersonProjection findEleveByIdProjection(Long id) {
		PersonProjection project = null;
		try {
			project = (PersonProjection) getEntityManager().createQuery("SELECT "
					+ " new com.vieecoles.steph.projections.PersonProjection(e.id, e.matricule, e.nom, e.prenom, e.sexe) "
					+ " FROM Eleve e "
					+ " WHERE e.id =:id").setParameter("id", id).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return project;
	}
}

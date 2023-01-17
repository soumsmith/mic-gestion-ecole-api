package com.vieecoles.steph.services;

import com.vieecoles.steph.entities.Eleve;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

public class EleveService implements PanacheRepositoryBase<Eleve, Long> {

	public List<Eleve> getList() {
		return Eleve.listAll();
	}

	public Eleve findById(Long id) {
		return Eleve.findById(id);
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
}

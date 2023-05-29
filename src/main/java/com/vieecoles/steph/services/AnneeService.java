package com.vieecoles.steph.services;

import com.vieecoles.steph.entities.AnneeScolaire;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class AnneeService implements PanacheRepositoryBase<AnneeScolaire, Long> {

	public List<AnneeScolaire> getList() {
		return AnneeScolaire.listAll();
	}

	public AnneeScolaire findById(Long id) {
		return AnneeScolaire.findById(id);
	}

	public List<AnneeScolaire> getByStatut(String statut) {

		List<AnneeScolaire> annees = new ArrayList<AnneeScolaire>();
		try {
			annees = AnneeScolaire.find("statut = ?1 ", statut).list();
		} catch (RuntimeException e) {
			e.printStackTrace();

		}
		return annees;
	}

	public List<AnneeScolaire> getByEcoleAndStatut(String ecoleId, String statut) {

		List<AnneeScolaire> annees = new ArrayList<AnneeScolaire>();
		try {
			annees = AnneeScolaire.find("ecole.id = ?1 statut = ?2", ecoleId, statut).list();
		} catch (RuntimeException e) {
			e.printStackTrace();

		}
		return annees;
	}

	public Response create(AnneeScolaire annee) {
		annee.persist();
		return Response.created(URI.create("/annee/" + annee.getId())).build();
	}

	public AnneeScolaire update(long id, AnneeScolaire annee) {
		AnneeScolaire entity = AnneeScolaire.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.setLibelle(annee.getLibelle());
		return entity;
	}

	public void delete(long id) {
		AnneeScolaire entity = AnneeScolaire.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.delete();
	}

	public List<AnneeScolaire> search(String libelle) {
		return AnneeScolaire.find("libelle", libelle).list();
	}

	public long count() {
		return AnneeScolaire.count();
	}
}

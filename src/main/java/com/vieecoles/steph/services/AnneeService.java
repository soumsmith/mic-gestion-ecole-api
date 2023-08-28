package com.vieecoles.steph.services;

import com.vieecoles.steph.entities.AnneePeriode;
import com.vieecoles.steph.entities.AnneeScolaire;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.pojos.AnneePeriodePojo;
import com.vieecoles.steph.util.DateUtils;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RequestScoped
public class AnneeService implements PanacheRepositoryBase<AnneeScolaire, Long> {

	@Inject
	AnneePeriodeService anneePeriodeService;

	public List<AnneeScolaire> getList() {

		List<AnneeScolaire> annees = AnneeScolaire.listAll();
		try {
		if (annees != null && annees.size() > 0)
			for (AnneeScolaire ans : annees)
				populateEntity(ans);
		}catch(RuntimeException r) {
			r.printStackTrace();
		}
		return annees;
	}

	public AnneeScolaire getById(Long id) {
		AnneeScolaire annee = AnneeScolaire.findById(id);
		if (annee != null)
			return populateEntity(annee);
		else
			return annee;
	}

	AnneeScolaire populateEntity(AnneeScolaire anneeScolaire) {
		List<AnneePeriode> apList = anneePeriodeService.listByAnneeAndNiveauEnseignement(anneeScolaire.getId(),
				anneeScolaire.getNiveauEnseignement().getId());
		List<AnneePeriodePojo> anneePojoList = new ArrayList<AnneePeriodePojo>();
		for (AnneePeriode ap : apList) {
			AnneePeriodePojo aPojoDeb = new AnneePeriodePojo();
			AnneePeriodePojo aPojoFin = new AnneePeriodePojo();
			aPojoDeb.setId("deb_" + ap.getPeriode().getId());
			aPojoDeb.setValue(DateUtils.asDate(ap.getDateDebut()));
			anneePojoList.add(aPojoDeb);

			aPojoFin.setId("fin_" + ap.getPeriode().getId());
			aPojoFin.setValue(DateUtils.asDate(ap.getDateFin()));
			anneePojoList.add(aPojoFin);
		}
		System.out.println(String.format("Liste de %s annee-periode-pojos", anneePojoList.size()));
		anneeScolaire.setAnneePeriodes(anneePojoList);
		anneeScolaire.setAnneeFin(anneeScolaire.getAnneeDebut() + 1);
		return anneeScolaire;
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

	@Transactional
	public AnneeScolaire create(AnneeScolaire annee) {
		annee.setDateCreation(new Date());
		annee.setDateUpdate(new Date());
		annee.persist();
		return annee;
	}

	@Transactional
	public AnneeScolaire handleOpenAnnee(AnneeScolaire annee) {

		// save annee
		annee.setLibelle(annee.getCustomLibelle());
		annee.setStatut(Constants.INITIALISE);
		if (annee.getId() != 0)
			update(annee.getId(), annee);
		else
			create(annee);

		// save annee periode
		anneePeriodeService.handleAnneeToPeriode(annee);
		return AnneeScolaire.findById(annee.getId());
	}

	@Transactional
	public AnneeScolaire update(long id, AnneeScolaire annee) {
		AnneeScolaire entity = AnneeScolaire.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.setLibelle(annee.getCustomLibelle());
		entity.setAnneeDebut(annee.getAnneeDebut());
		entity.setDateUpdate(new Date());
		entity.setNbreEval(annee.getNbreEval());
		entity.setNiveauEnseignement(annee.getNiveauEnseignement());
		entity.setPeriodicite(annee.getPeriodicite());
		entity.setStatut(annee.getStatut());
		entity.setEcole(annee.getEcole());
		entity.setUser(annee.getUser());
		return entity;
	}

	@Transactional
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

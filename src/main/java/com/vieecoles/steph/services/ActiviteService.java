package com.vieecoles.ressource.steph.services;

import com.google.gson.Gson;
import com.vieecoles.entities.Activite;
import com.vieecoles.entities.Constants;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class ActiviteService implements PanacheRepositoryBase<Activite, Integer> {

	@Inject
    SalleService salleService;

	Logger logger = Logger.getLogger(ActiviteService.class.getName());

	public List<Activite> getList() {
		try {
			return Activite.list("statut", Constants.ACTIF);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Activite>();
		}
	}

	/**
	 * Renvoie la liste des activités en fonction de l année, la classe et le jour
	 *
	 * @param anneeId
	 * @param classeId
	 * @param jourId
	 * @return
	 */
	public List<Activite> getListByClasseAndJour(long anneeId, long classeId, int jourId) {
		logger.info(String.format("Annee %s - classe %s - jour %s", anneeId, classeId, jourId));
		return Activite.find("classe.id = ?1 and jour.id = ?2 and statut = ?3", classeId, jourId, Constants.ACTIF)
				.list();
	}

	public List<Activite> getListByJour(long anneeId, int jourId) {
		logger.info(String.format("Annee %s - classe %s - jour %s",1, anneeId, jourId));
		return Activite.find("jour.id = ?1 and statut = ?2", jourId, Constants.ACTIF)
				.list();
	}

	/**
	 * Renvoie la disponibilité d'une plage horaire
	 *
	 * @param anneeId
	 * @param classeId
	 * @param jourId
	 * @param heureDeb
	 * @param heureFin
	 * @return
	 */
	public Boolean isPlageHoraireValid(long anneeId, long classeId, int jourId, String heureDeb, String heureFin) {
		List<Activite> activites = getListByClasseAndJour(anneeId, classeId, jourId);
		LocalTime timeDeb = LocalTime.parse(heureDeb);
		LocalTime timeFin = LocalTime.parse(heureFin);
		for (Activite atv : activites) {
			if (!timeDeb.isBefore(LocalTime.parse(atv.getHeureDeb()))
					&& timeDeb.isBefore(LocalTime.parse(atv.getHeureFin()))) {
				return false;
			}
			if (timeFin.isAfter(LocalTime.parse(atv.getHeureDeb()))
					&& !timeFin.isAfter(LocalTime.parse(atv.getHeureFin()))) {
				return false;
			}
			if(!timeDeb.isAfter(LocalTime.parse(atv.getHeureDeb())) && !timeFin.isBefore(LocalTime.parse(atv.getHeureFin()))){
				return false;
			}
		}

		return true;
	}

	public Activite findById(long id) {
		logger.info(String.format("find by id :: %s", id));
		return Activite.findById(id);
	}

	@Transactional
	public Response save(Activite activite) {
		Gson gson = new Gson();
		logger.info("persist activite ...");
		logger.info(gson.toJson(activite));
		activite.setDateCreation(new Date());
		activite.setDateUpdate(new Date());
		activite.setStatut(Constants.ACTIF);
		activite.persist();
		return Response.created(URI.create("/classe/" + activite.getId())).build();
	}

	@Transactional
	public Activite update(Activite activite) {
		logger.info("updating classe ...");
		Activite atv = Activite.findById(activite.getId());
		if (atv != null) {
			atv.setClasse(activite.getClasse());
			atv.setHeureDeb(activite.getHeureDeb());
			atv.setHeureFin(activite.getHeureFin());
			atv.setJour(activite.getJour());
			atv.setMatiere(activite.getMatiere());
			atv.setSalle(activite.getSalle());
			atv.setStatut(activite.getStatut());
			atv.setTypeActivite(activite.getTypeActivite());
			atv.setDateUpdate(new Date());
		}
//		System.out.println(new Gson().toJson(cl));
		return atv;
	}

	public Activite updateAndDisplay(Activite activite) {
		Activite atv;
		if (update(activite) != null) {
			atv = findById(activite.getId());
			return atv;
		}

		return null;

	}

	/**
	 * Vérifie Si l activité n est pas referencée dans une autre table avant
	 * d'effectuer la pseudo-suppression
	 *
	 * @param activite
	 * @return
	 */
	public Boolean isDeletable(Activite activite) {
		// to do lorsqu il existera une contrainte d'integrité referentielle
		return true;
	}

	@Transactional
	public void delete(String id) {

		logger.info("delete activite id " + id);
		Activite atv = findById(Long.parseLong(id));
//			atv.delete();
		if (atv != null && isDeletable(atv)) {
			atv.setStatut(Constants.INACTIF);
			atv.setDateUpdate(new Date());
		}

	}

}

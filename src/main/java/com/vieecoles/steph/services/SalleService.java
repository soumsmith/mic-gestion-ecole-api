package com.vieecoles.steph.services;

import com.google.gson.Gson;
import java.util.logging.Logger;
import com.vieecoles.steph.entities.Activite;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.Salle;
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
import java.util.logging.Level;

@ApplicationScoped
public class SalleService implements PanacheRepositoryBase<Salle, Long> {

	@Inject
	ActiviteService activiteService;

	@Inject
	ClasseService classeService;

	Logger logger = Logger.getLogger(SalleService.class.getName());

	public List<Salle> list() {
		return Salle.findAll().list();
	}

	public List<Salle> getSallesByEcole(Long ecoleId) {
		List<Salle> salles = new ArrayList<Salle>();
		try {
			salles = Salle.find("ecole.id = ?1", ecoleId).list();
		} catch (RuntimeException ex) {
			logger.log(Level.WARNING, " Error getSallesByEcole {0}", ex);
		}

		return salles;
	}
	
	public Long countSallesByEcole(Long ecoleId) {
		try {
			return Salle.find("ecole.id = ?1", ecoleId).count();
		} catch (RuntimeException ex) {
			logger.log(Level.WARNING, " Error getSallesByEcole {0}", ex);
			return (long) 0;
		}

	}
	
	

	public List<Salle> getWithSallesDisponibles(long anneeId, long classeId, int jourId, String heureDeb,
			String heureFin) {
		List<Activite> activites = activiteService.getListByClasseAndJour(classeId, jourId);

		List<Salle> salles = new ArrayList<Salle>();
		Classe classe = classeService.findById(classeId);
		if (classe != null)
			salles = getSallesByEcole(classe.getEcole().getId());
		LocalTime timeDeb = LocalTime.parse(heureDeb);
		LocalTime timeFin = LocalTime.parse(heureFin);
		List<Activite> atvCart = new ArrayList<Activite>();
		List<Salle> salleCopy = new ArrayList<Salle>();
//		Gson gson = new Gson();
//		System.out.println(gson.toJson(salles));
		Boolean flag = true;
		// liste des activités de la plage horaire
		for (Activite atv : activites) {
//			System.out.println("______________");
			flag = true;
			if (!timeDeb.isBefore(LocalTime.parse(atv.getHeureDeb()))
					&& timeDeb.isBefore(LocalTime.parse(atv.getHeureFin())) && flag) {
				System.out.println("in 1---->");
				atvCart.add(atv);
				flag = false;
			}
			if (timeFin.isAfter(LocalTime.parse(atv.getHeureDeb()))
					&& !timeFin.isAfter(LocalTime.parse(atv.getHeureFin())) && flag) {
				System.out.println("in 2---->");
				atvCart.add(atv);
				flag = false;
			}
			if (!timeDeb.isAfter(LocalTime.parse(atv.getHeureDeb()))
					&& !timeFin.isBefore(LocalTime.parse(atv.getHeureFin())) && flag) {
				System.out.println("in 3---->");
				atvCart.add(atv);
			}

		}
		salleCopy.addAll(salles);
		// suppression des activites dont les salles sont occupées
		for (Activite atv : atvCart) {
			for (Salle salle : salles) {
				if (atv.getSalle().getId() == salle.getId()) {
					salleCopy.remove(salle);
				}
			}
		}
		return salleCopy;
	}

	@Transactional
	public Response save(Salle salle) {
//		Gson gson = new Gson();
		// logger.info("persist activite ...");
		// logger.info(gson.toJson(salle));

		salle.persist();
		return Response.created(URI.create("/salle/" + salle.getId())).build();
	}

	@Transactional
	public Salle update(Salle salle) {
		// logger.info("updating salle ...");
		Salle sl = Salle.findById(salle.getId());
		if (salle != null) {
			sl.setCode(salle.getCode());
			sl.setLibelle(salle.getLibelle());
		}
//		System.out.println(new Gson().toJson(cl));
		return sl;
	}

	public Salle updateAndDisplay(Salle salle) {
		Salle sal;
		if (update(salle) != null) {
			sal = findById(salle.getId());
			return sal;
		}

		return null;

	}

	Boolean isDeletable(String id) {

		return true;
	}

	@Transactional
	public void delete(String id) {

		// logger.info("delete Salle id " + id);
		Salle sl = findById(Long.parseLong(id));
		if (isDeletable(id))
			sl.delete();
		else
			new RuntimeException("Enregistrement referencé dans d autre table");

	}
}

package com.vieecoles.steph.services;

import com.vieecoles.steph.entities.*;
import com.vieecoles .steph.util.DateUtils;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class SeanceService implements PanacheRepositoryBase<Seances, Long> {

	@Inject
	ActiviteService activiteService;

	@Inject
    JourService jourService;

	@Inject
	ClasseService classeService;

	@Inject
	PersonnelMatiereClasseService personnelMatiereClasseService;

	//Logger logger = Logger.getLogger(SeanceService.class.getName());

	public List<Seances> getList() {
		try {
			return Seances.listAll();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Seances>();
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
	public List<Seances> getListByClasseAndJour(long anneeId, long classeId, int jourId) {
		//logger.info(String.format("Annee %s - classe %s - jour %s", anneeId, classeId, jourId));
		return Seances.find("classe.id = ?1 and jour.id = ?2", classeId, jourId).list();
	}

	public List<Seances> getListByClasseAndJourAndStatut(long anneeId, long classeId, int jourId, String statut) {
		//logger.info(String.format("Annee %s - classe %s - jour %s - statut %s", anneeId, classeId, jourId, statut));
		return Seances.find("classe.id = ?1 and jour.id = ?2 and statut = ?3", classeId, jourId, statut).list();
	}

	public List<Seances> getListByClasseAndDateAndStatut(long anneeId, long classeId, Date date, String statut) {
		//logger.info(String.format("Annee %s - classe %s - date %s - statut %s", anneeId, classeId, date, statut));
		return Seances.find("classe.id = ?1 and dateSeance = ?2 and statut = ?3", classeId, date, statut).list();
	}

	public List<Seances> getListByDate(long anneeId, Date date) {
		//logger.info(String.format("Annee %s - date %s", anneeId, date));
		return Seances.find("dateSeance = ?1", date).list();
	}

	public List<Seances> getListByDateAndProf(long anneeId, Date date, long profId) {
		//logger.info(String.format("Annee %s - date %s", anneeId, date));
		return Seances.find("dateSeance = ?1 and professeur.id= ?2", date, profId).list();
	}

	public List<Seances> getListByStatut(long anneeId, String statut) {
		//logger.info(String.format("Annee %s - statut %s", anneeId, statut));
		return Seances.find("statut = ?1", statut).list();
	}

	public List<Seances> getDistinctListByDate(Date date) {
		return Seances.find("select DISTINCT s.classe.id,s.classe.libelle, s.statut, s.dateSeance from Seances s where s.dateSeance = ?1", date).list();
	}

	public List<Seances> getDistinctListByDateAndClasse(Date date, long classeId) {
		return Seances.find("select DISTINCT s.classe.id,s.classe.libelle, s.statut, s.dateSeance from Seances s where s.dateSeance = ?1 and s.classe.id =?2", date, classeId).list();
	}

	public List<Seances> getListByDateAndClasse(long anneeId, Date date, Long classeId) {
		//logger.info(String.format("Annee %s - date %s - classe %s", anneeId, date,classeId));
		return Seances.find("dateSeance = ?1 and classe.id = ?2", date, classeId).list();
	}

	public Seances findById(long id) {
	//	logger.info(String.format("find by id :: %s", id));
		return Seances.findById(id);
	}

	@Transactional
	public Response save(Seances seances) {
		// Gson gson = new Gson();
		//logger.info("persist seance ...");
		// logger.info(gson.toJson(seances));
		seances.setDateCreation(new Date());
		seances.setDateUpdate(new Date());
		// seances.setStatut(Constants.ACTIF);
		seances.persist();
		return Response.created(URI.create("/seances/" + seances.getId())).build();
	}

	@Transactional
	public Seances update(Seances seances) {
		//logger.info("updating seance ...");
		Seances seance = Seances.findById(seances.getId());
		if (seance != null) {
			seance.setClasse(seances.getClasse());
			seance.setHeureDeb(seances.getHeureDeb());
			seance.setHeureFin(seances.getHeureFin());
			seance.setJour(seances.getJour());
			seance.setMatiere(seances.getMatiere());
			seance.setSalle(seances.getSalle());
			seance.setStatut(seances.getStatut());
			seance.setTypeActivite(seances.getTypeActivite());
			seance.setAnnee(seances.getAnnee());
			seance.setDateSeance(seances.getDateSeance());
			seance.setProfesseur(seances.getProfesseur());
			seance.setSurveillant(seances.getSurveillant());
			seance.setDateUpdate(new Date());
		}
//		System.out.println(new Gson().toJson(cl));
		return seance;
	}

	@Transactional
	public List<Message> generateSeances(Date date, String classe) {
		// si classe est non nulle verifier existence de la séance, si existe alors
		// passer

		// rechercher le Jour a partir d une date
//		Gson gson = new Gson();
		int jourNum;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		jourNum = calendar.get(Calendar.DAY_OF_WEEK);

		Jour jour = jourService.findByIdSys(jourNum);

//		System.out.println(gson.toJson(jour));
		List<Activite> activites = new ArrayList<Activite>();

		// Recuperer la liste des emploi du temps en fonction du jour et/ou de la classe
		// (si classe non nulle)
		if (classe == null)
			activites = activiteService.getListByJour(1, jour.getId());
		else
			activites = activiteService.getListByClasseAndJour(1, Long.parseLong(classe), jour.getId());

		// inserer pour chaque emploi du temps une seance (en recherchant le professeur
		// de la matiere si existe)
		// avant insertion verifier pour chaque seance l existence, annuler l operation
		// si existant
		Seances seance ;
		PersonnelMatiereClasse pers = new PersonnelMatiereClasse();
		List<Seances> seanceExist = new ArrayList<Seances>();
		List<Long> blackIdsClasses = new ArrayList<Long>();
		List<Message> messages = new ArrayList<Message>();
		List<Seances> seancespersist = new ArrayList<Seances>();
	//	logger.info("Persist seances automatic");
		//logger.info("Activites "+activites.size());
		int cpteSeances = 0;
		int cpteSeancesTot = 0;
		for (Activite atv : activites) {
			cpteSeancesTot ++;

			if (!blackIdsClasses.contains(atv.getClasse().getId())) {
				seanceExist = getListByClasseAndDateAndStatut(1, atv.getClasse().getId(), date, Constants.AUTOMATIQUE);

				if (seanceExist.size()==0) {
					seance = new Seances();
					pers = personnelMatiereClasseService.findByMatiereAndClasse(atv.getMatiere().getId(), 0,atv.getClasse().getId());
					seance.setAnnee(atv.getAnnee());
					seance.setClasse(atv.getClasse());
					seance.setDateCreation(new Date());
					seance.setDateUpdate(new Date());
					seance.setDateSeance(date);
					seance.setHeureDeb(atv.getHeureDeb());
					seance.setHeureFin(atv.getHeureFin());
					seance.setJour(jour);
					seance.setMatiere(atv.getMatiere());
					seance.setProfesseur(pers != null ? pers.getPersonnel() : null);
					seance.setSalle(atv.getSalle());
					seance.setStatut(Constants.PENDING);
					seance.setSurveillant(null);
					seance.setTypeActivite(atv.getTypeActivite());
					seance.setActivite(atv);
					seance.persist();
				//	logger.info("-> id "+seance.getId());
					seancespersist.add(seance);
					cpteSeances ++;
				} else {
					blackIdsClasses.add(atv.getClasse().getId());
					messages.add(new Message("info", "Seance existe deja", String.format("Séance deja créée pour la classe de %s pour le %s %s", atv.getClasse().getLibelle(),jour.getLibelle(), DateUtils.asLocalDate(date).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))));
				}
			}
		}
		messages.add(new Message("info", "Ouverture des séances", String.format("l'opération a genéré %s séances sur %s ligne(s) prévue(s) par l emploi du temps.", cpteSeances,cpteSeancesTot)));
		//logger.info("Mise à jour statut");
		Seances s = new Seances();
		for(Seances sce : seancespersist) {
			//logger.info("-> "+sce.getId());
			s = findById(sce.getId());
			s.setStatut(Constants.AUTOMATIQUE);
		}

		return messages;
	}

	public Seances updateAndDisplay(Seances seances) {
		Seances seance;
		if (update(seances) != null) {
			seance = findById(seances.getId());
			return seance;
		}

		return null;
	}

	/**
	 * Vérifie les conditions de suppression d une seance d'effectuer la
	 * pseudo-suppression
	 *
	 * @param activite
	 * @return
	 */
	public Boolean isDeletable(Seances activite) {
		// to do lorsqu il existera une contrainte d'integrité referentielle
		return true;
	}

	@Transactional
	public void delete(String id) {

		//logger.info("...Delete seance id " + id);
		Seances seance = findById(Long.parseLong(id));
//			atv.delete();
		if (seance != null && isDeletable(seance)) {
			//logger.info("Seance deleted");
			seance.delete();
		}

	}

}

package com.vieecoles.steph.services;

import com.google.gson.Gson;
import com.vieecoles.services.operations.ecoleService;
import com.vieecoles.steph.entities.*;
import com.vieecoles.steph.projections.GenericProjectionLongId;
import com.vieecoles.steph.util.DateUtils;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.scheduler.Scheduled;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
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

	@Inject
	EvaluationService evaluationService;

	@Inject
	EcoleService ecoleService;

	@Inject
	AnneeService anneeService;

	@Inject
	AppelNumeriqueService appelNumeriqueService;

	Logger logger = Logger.getLogger(SeanceService.class.getName());

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
		// logger.info(String.format("Annee %s - classe %s - jour %s", anneeId,
		// classeId, jourId));
		return Seances.find("classe.id = ?1 and jour.id = ?2", classeId, jourId).list();
	}

	public List<Seances> getListByClasseAndJourAndStatut(long anneeId, long classeId, int jourId, String statut) {
		// logger.info(String.format("Annee %s - classe %s - jour %s - statut %s",
		// anneeId, classeId, jourId, statut));
		return Seances.find("classe.id = ?1 and jour.id = ?2 and statut = ?3", classeId, jourId, statut).list();
	}

	public List<Seances> getListByClasseAndDateAndStatut(long classeId, Date date, String statut) {
		// logger.info(String.format("Annee %s - classe %s - date %s - statut %s",
		// anneeId, classeId, date, statut));
		return Seances.find("classe.id = ?1 and dateSeance = ?2 and statut = ?3", classeId, date, statut).list();
	}

	public List<Seances> getListByDate(long anneeId, Date date) {
		// logger.info(String.format("Annee %s - date %s", anneeId, date));
		return Seances.find("dateSeance = ?1", date).list();
	}

	List<Seances> destructSeanceByTimeUnit(Seances seances, int minutes) {
		if (minutes == 0)
			minutes = Constants.DEFAULT_DUREE_SEANCE_MINUTES;
		int duree = DateUtils.calculerDuree(seances.getHeureDeb(), seances.getHeureFin());
		int nbreSeances = duree / minutes;
		if (duree % minutes > 0)
			nbreSeances++;
		String dateDebNew = seances.getHeureDeb();
		System.out.println(String.format("Nombre de de seances pour la séance %s = %s", seances.getId(), nbreSeances));
		List<Seances> list = new ArrayList<>();
		if (nbreSeances > 1) {
			for (int i = 0; i < nbreSeances; i++) {
				Seances seance = new Seances();
				AppelNumerique ap = new AppelNumerique();
				ap = appelNumeriqueService.getBySeance(seances.getId(), i);
				seance.setAppelAlreadyExist(ap.getId() != null);
				seance.setId(seances.getId());
				seance.setAnnee(seances.getAnnee());
				seance.setClasse(seances.getClasse());
				seance.setDateCreation(seances.getDateCreation());
				seance.setDateUpdate(seances.getDateUpdate());
				seance.setDateSeance(seances.getDateSeance());
				seance.setHeureDeb(dateDebNew);
				seance.setHeureFin(DateUtils.ajouterMinutes(dateDebNew, minutes));
				seance.setJour(seances.getJour());
				seance.setMatiere(seances.getMatiere());
				seance.setProfesseur(seances.getProfesseur());
				seance.setSalle(seances.getSalle());
				seance.setStatut(seances.getStatut());
				seance.setSurveillant(seances.getSurveillant());
				seance.setTypeActivite(seances.getTypeActivite());
				seance.setActivite(seances.getActivite());
				seance.setPosition(i);
				seance.setIsVerrou(DateUtils.verifierHeureDansMarge(dateDebNew, Constants.DEFAULT_DELAI_AVANT_DESACTIVE_APPEL_MINUTES, Constants.DEFAULT_DELAI_APRES_DESACTIVE_APPEL_MINUTES));
				seance.setIsEnded(verifySeanceEnded(seance.getHeureFin()));
				seance.setIsClassEnded(verifySeanceEnded(seance.getHeureFin()) ? "surface-300" : "");
				seance.setDuree(duree);
				list.add(seance);
				dateDebNew = DateUtils.ajouterMinutes(dateDebNew, minutes);
				System.out.println(
						String.format("Heure deb: %s - heure fin: %s ", seance.getHeureDeb(), seance.getHeureFin()));
			}
		}

		return list;
	}
	
	public boolean verifySeanceEnded(String heure) {
		LocalTime heureActuelle = LocalTime.now();
		LocalTime heureParametre = LocalTime.parse(heure, DateTimeFormatter.ofPattern("HH:mm"));
//		System.out.println("Seance Terminée : "+heureParametre.isBefore(heureActuelle));
//		System.out.println(heureParametre +" "+heureActuelle);
		//la seance est terminée si l'heure de fin est avant l'heure actuelle 
		return heureParametre.isBefore(heureActuelle);
	}

	// La signature doit changer vu que l'année n'est plus utiliser
	public List<Seances> getListByDateAndProf(long anneeId, Date date, long profId) {
		logger.info(String.format("prof %s - date %s", profId, date));
		List<Seances> list = Seances.find("dateSeance = ?1 and professeur.id= ?2 order by heureDeb", date, profId)
				.list();
		List<Seances> listWithDestructSeances = new ArrayList<Seances>();
		// Décomposer la liste afin de s'assurer que chaque séance tient sur une unité
		// de temps (1h par defaut)
		// indicateur pour savoir si un appel a eu lieu pour la seance
		if (list != null) {
			for (Seances s : list) {
				List<Seances> seanceDestruct = destructSeanceByTimeUnit(s, 0);
				if (seanceDestruct.size() > 0) {
					listWithDestructSeances.addAll(seanceDestruct);

				} else {
					AppelNumerique ap = new AppelNumerique();
					ap = appelNumeriqueService.getBySeance(s.getId());
					s.setAppelAlreadyExist(ap.getId() != null);
					s.setIsVerrou(DateUtils.verifierHeureDansMarge(s.getHeureDeb(), Constants.DEFAULT_DELAI_AVANT_DESACTIVE_APPEL_MINUTES, Constants.DEFAULT_DELAI_APRES_DESACTIVE_APPEL_MINUTES));
					s.setIsEnded(verifySeanceEnded(s.getHeureFin()));
					s.setIsClassEnded(verifySeanceEnded(s.getHeureFin()) ? "surface-300" : "");
					s.setDuree(Constants.DEFAULT_DUREE_SEANCE_MINUTES);
					listWithDestructSeances.add(s);
				}

			}
		}
		return listWithDestructSeances;
	}

	public List<Seances> getListByStatut(String anneeId, String statut, long ecoleId) {
		// logger.info(String.format("Annee %s - statut %s", anneeId, statut));
		try {
			return Seances.find("statut = ?1 and classe.ecole.id=?2 and annee =?3", statut, ecoleId, anneeId).list();

		} catch (RuntimeException r) {
			r.printStackTrace();
			return null;
		}
	}

	public List<Seances> getListByEcoleAndDateAndStatut(Date date, String statut, long ecoleId) {
		// logger.info(String.format("Annee %s - statut %s", anneeId, statut));
		try {
			return Seances.find("statut=?1 and classe.ecole.id=?2 and dateSeance =?3", statut, ecoleId, date).list();

		} catch (RuntimeException r) {
			r.printStackTrace();
			return null;
		}
	}

	public Boolean isPlageHoraireValid(long anneeId, long classeId, int jourId, Date date, String heureDeb,
			String heureFin) {
		List<Activite> activites = activiteService.getListByClasseAndJour(classeId, jourId);
		List<Seances> seances = getListByDateAndClasse(anneeId, date, classeId);
		LocalTime timeDeb = LocalTime.parse(heureDeb);
		LocalTime timeFin = LocalTime.parse(heureFin);
		// Vérification avec l emploi du temps
		for (Activite atv : activites) {
			if (!timeDeb.isBefore(LocalTime.parse(atv.getHeureDeb()))
					&& timeDeb.isBefore(LocalTime.parse(atv.getHeureFin()))) {
				return false;
			}
			if (timeFin.isAfter(LocalTime.parse(atv.getHeureDeb()))
					&& !timeFin.isAfter(LocalTime.parse(atv.getHeureFin()))) {
				return false;
			}
			if (!timeDeb.isAfter(LocalTime.parse(atv.getHeureDeb()))
					&& !timeFin.isBefore(LocalTime.parse(atv.getHeureFin()))) {
				return false;
			}
		}
		// Vérification avec eventuelles séances saisies
		for (Seances s : seances) {
			if (!timeDeb.isBefore(LocalTime.parse(s.getHeureDeb()))
					&& timeDeb.isBefore(LocalTime.parse(s.getHeureFin()))) {
				return false;
			}
			if (timeFin.isAfter(LocalTime.parse(s.getHeureDeb()))
					&& !timeFin.isAfter(LocalTime.parse(s.getHeureFin()))) {
				return false;
			}
			if (!timeDeb.isAfter(LocalTime.parse(s.getHeureDeb()))
					&& !timeFin.isBefore(LocalTime.parse(s.getHeureFin()))) {
				return false;
			}
		}

		return true;
	}

	public List<Seances> getDistinctListByDate(Date date, Long ecoleId) {
		return Seances.find(
				"select DISTINCT s.classe.id,s.classe.libelle, s.statut, s.dateSeance from Seances s where s.dateSeance = ?1 and s.classe.ecole.id = ?2 ",
				date, ecoleId).list();
	}

	public List<Seances> getDistinctListByDateAndClasse(Date date, long classeId) {
		return Seances.find(
				"select DISTINCT s.classe.id,s.classe.libelle, s.statut, s.dateSeance from Seances s where s.dateSeance = ?1 and s.classe.id =?2",
				date, classeId).list();
	}

	public List<Seances> getListByDateAndClasse(long anneeId, Date date, Long classeId) {
		// logger.info(String.format("Annee %s - date %s - classe %s", anneeId,
		// date,classeId));
		List<Seances> seances = new ArrayList<Seances>();
		try {
			seances = Seances.find("dateSeance = ?1 and classe.id = ?2", date, classeId).list();
		} catch (RuntimeException ex) {
			logger.log(Level.WARNING, "Error ::: {0} ", ex);
		}
		return seances;
	}

	public Seances findById(String id) {
		// logger.info(String.format("find by id :: %s", id));
		return Seances.findById(id);
	}

	public Long countSallesUtiliseInSeanceByEcoleAndDate(Long ecoleId, Date date) {
		try {
			return Seances
					.find("select distinct s.salle.id from Seances s where s.classe.ecole.id = ?1 and s.dateSeance =?2",
							ecoleId, date)
					.count();
		} catch (RuntimeException ex) {
			logger.log(Level.WARNING, " Error getSallesByEcole {0}", ex);
			return (long) 0;
		}

	}

	@Transactional
	public Response save(Seances seances) {
		Gson gson = new Gson();
		logger.info("persist seance ...");
		UUID uuid = UUID.randomUUID();
		seances.setId(uuid.toString());
		if (seances.getSurveillant().getId() == 0)
			seances.setSurveillant(null);
		seances.setDateCreation(new Date());
		seances.setDateUpdate(new Date());
		// seances.setStatut(Constants.ACTIF);
		if (seances.getEvaluationIndicator() == 1) {
			AnneeScolaire annee = new AnneeScolaire();
			annee.setId(Long.parseLong(seances.getAnnee()));
//			TypeActivite type = new TypeActivite();
//			type.setId(seances.getTypeActivite().getId());
			seances.getEvaluation().setMatiereEcole(seances.getMatiere());
			seances.getEvaluation().setAnnee(annee);
			seances.getEvaluation().setType(seances.getTypeActivite());
			// Ajouter l heure et la minutes à la date de l evaluation
			LocalDateTime ldt = DateUtils.asLocalDateTime(seances.getDateSeance());
			Long heureD = seances.getHeureDeb() != null ? Long.parseLong(seances.getHeureDeb().split(":")[0]) : 0;
			Long minD = seances.getHeureDeb() != null ? Long.parseLong(seances.getHeureDeb().split(":")[1]) : 0;
			logger.info(DateUtils.asDate(ldt.plusHours(heureD).plusMinutes(minD)).toString());
			seances.getEvaluation().setDate(DateUtils.asDate(ldt.plusHours(heureD).plusMinutes(minD)));

			seances.getEvaluation().setClasse(seances.getClasse());
			seances.getEvaluation().setUser(seances.getUser());
			logger.info(gson.toJson(seances.getEvaluation()));
			evaluationService.create(seances.getEvaluation());
		} else {
			seances.setEvaluation(null);
		}
		seances.persist();
		return Response.created(URI.create("/seances/" + seances.getId())).build();
	}

	@Transactional
	public Seances update(Seances seances) {
		// logger.info("updating seance ...");
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
			seance.setSurveillant(seances.getSurveillant().getId() == 0 ? null : seances.getSurveillant());
			seance.setDateUpdate(new Date());
		}
//		System.out.println(new Gson().toJson(cl));
		return seance;
	}

	@Transactional
	public List<Message> generateSeances(Date date, String classe, Long ecoleId, Long anneeId) {
		// si classe est non nulle verifier existence de la séance, si existe alors
		// passer rechercher le Jour a partir d une date
//		Gson gson = new Gson();
		int jourNum;
		jourNum = DateUtils.getNumDay(date);
		System.out.println(date);
		System.out.println(jourNum);
		System.out.println("anneeid ::: "+anneeId);
		System.out.println("ecoleid :::" +ecoleId);
		Jour jour = jourService.findByIdSys(jourNum);
		List<Activite> activites = new ArrayList<Activite>();

		// Recuperer la liste des emploi du temps en fonction du jour et/ou de la classe
		// (si classe non nulle)
		if (classe == null) {
			activites = activiteService.getListByJourAndEcole(jour.getId(), ecoleId);
		} else
			activites = activiteService.getListByClasseAndJour(Long.parseLong(classe), jour.getId());

		// inserer pour chaque emploi du temps une seance (en recherchant le professeur
		// de la matiere si existe)
		// avant insertion verifier pour chaque seance l existence, annuler l operation
		// si existant
		Seances seance;
		PersonnelMatiereClasse pers = new PersonnelMatiereClasse();
		List<Seances> seanceExist = new ArrayList<Seances>();
		List<Long> blackIdsClasses = new ArrayList<Long>();
		List<Message> messages = new ArrayList<Message>();
		List<Seances> seancespersist = new ArrayList<Seances>();
		// logger.info("Persist seances automatic");
		// logger.info("Activites "+activites.size());
		int cpteSeances = 0;
		int cpteSeancesTot = 0;
		for (Activite atv : activites) {
			cpteSeancesTot++;

			if (!blackIdsClasses.contains(atv.getClasse().getId())) {
				seanceExist = getListByClasseAndDateAndStatut(atv.getClasse().getId(), date, Constants.AUTOMATIQUE);

				if (seanceExist.size() == 0) {
					seance = new Seances();
					UUID uuid = UUID.randomUUID();
					pers = personnelMatiereClasseService.findByMatiereAndClasse(atv.getMatiere().getId(),
							anneeId, atv.getClasse().getId());
					seance.setId(uuid.toString());
					seance.setAnnee(String.valueOf(anneeId));
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
					seancespersist.add(seance);
					logger.info("-> id " + seance.getId());
					cpteSeances++;
				} else {
					blackIdsClasses.add(atv.getClasse().getId());
					messages.add(new Message("info", "Seance existe deja",
							String.format("Séance deja créée pour la classe de %s pour le %s %s",
									atv.getClasse().getLibelle(), jour.getLibelle(),
									DateUtils.asLocalDate(date).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))));
				}
			}
		}
		messages.add(new Message("info", "Ouverture des séances",
				String.format("l'opération a genéré %s séances sur %s ligne(s) prévue(s) par l emploi du temps.",
						cpteSeances, cpteSeancesTot)));
		// logger.info("Mise à jour statut");
		Seances s = new Seances();
		for (Seances sce : seancespersist) {
			// logger.info("-> "+sce.getId());
			s = findById(sce.getId());
			s.setStatut(Constants.AUTOMATIQUE);
		}

		return messages;
	}

	@Scheduled(cron = "0 20 9 * * ?")
	public void generatorSeanceScheduler() {
		List<Ecole> ecoles = ecoleService.getList();
//		int jourNum;
		LocalDate tomorrow = LocalDate.now();
//		jourNum = DateUtils.getNumDay(DateUtils.asDate(tomorrow));
//		Jour jour = jourService.findByIdSys(jourNum);
		logger.info("*** GENERATION AUTOMATIQUE DES EMPLOI DU TEMPS ***");
		logger.info(String.format("Date de generation des bulletins %s", DateUtils.asDate(tomorrow)));

		for (Ecole ecole : ecoles) {
			System.out.println(String.format("Ecole %s", ecole.getLibelle()));
//			handlePersist(tomorrow, jour, ecole);
			GenericProjectionLongId anneeCentrale = anneeService.findMainAnneeWithProjectionByEcole(ecole);
			try {
			generateSeances(DateUtils.asDate(tomorrow), null, ecole.getId(), anneeCentrale.getId());
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("*** FIN GENERATION ***");
	}

	/**
	 * 
	 * @param dateSeance
	 * @param ecoleId
	 * @return true si une generation automatique a eu deja lieu pour une ecole
	 *         sinon false
	 * 
	 */
	public Boolean checkIfSeancesGenerate(Date dateSeance, Long ecoleId) {
		List<Seances> seances = getListByEcoleAndDateAndStatut(dateSeance, Constants.AUTOMATIQUE, ecoleId);
		if (seances != null && seances.size() > 0) {
			logger.info("-> Séances generées détectées");
			return true;
		}
		return false;
	}

	@Transactional
	public void handlePersist(LocalDate tomorrow, Jour jour, Ecole ecole) {
		try {
			if (!checkIfSeancesGenerate(DateUtils.asDate(tomorrow), ecole.getId())) {
				List<Activite> activites = activiteService.getListByEcole(ecole.getId());
				GenericProjectionLongId anneeCentrale = anneeService.findMainAnneeWithProjectionByEcole(ecole);
				for (Activite atv : activites) {
					Seances seance = new Seances();
					UUID uuid = UUID.randomUUID();
//					GenericProjectionLongId persGeneric = personnelMatiereClasseService.findPersonnelProjectionByMatiereAndClasse(atv.getMatiere().getId(), anneeCentrale.getId(),
//							atv.getClasse().getId());
					PersonnelMatiereClasse pers = personnelMatiereClasseService.findByMatiereAndClasse(atv.getMatiere().getId(),
							anneeCentrale.getId(), atv.getClasse().getId());
					Gson g = new Gson();
//					System.out.println(g.toJson(persGeneric));
//					if(persGeneric!=null) {
//						pers = new Personnel();
//						pers.setId(persGeneric.getId());
//					}
					seance.setId(uuid.toString());
					seance.setAnnee(String.valueOf(anneeCentrale.getId()));
					seance.setClasse(atv.getClasse());
					seance.setDateCreation(new Date());
					seance.setDateUpdate(new Date());
					seance.setDateSeance(DateUtils.asDate(tomorrow));
					seance.setHeureDeb(atv.getHeureDeb());
					seance.setHeureFin(atv.getHeureFin());
					seance.setJour(jour);
					seance.setMatiere(atv.getMatiere());
					seance.setProfesseur(pers != null ? pers.getPersonnel() : null);
					seance.setSalle(atv.getSalle());
					seance.setStatut(Constants.AUTOMATIQUE);
					seance.setSurveillant(null);
					seance.setTypeActivite(atv.getTypeActivite());
					seance.setActivite(atv);
					seance.persist();
//					System.out.println("---> "+seance.getId());
				}
				logger.info("--> " + activites.size() + " enregistrement(s)");
			}
		} catch (RuntimeException r) {
			r.printStackTrace();
		}
	}

	public Seances updateAndDisplay(Seances seances) {
		Seances seance;
		try {
			if (update(seances) != null) {
				seance = findById(seances.getId());
				return seance;
			}
		} catch (Exception e) {
			e.printStackTrace();
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

		// logger.info("...Delete seance id " + id);
		Seances seance = findById(Long.parseLong(id));
//			atv.delete();
		if (seance != null && isDeletable(seance)) {
			// logger.info("Seance deleted");
			seance.delete();
		}

	}

}

package com.vieecoles.steph.services;

import com.google.gson.Gson;
import com.vieecoles.services.operations.ecoleService;
import com.vieecoles.steph.dto.AnneeDto;
import com.vieecoles.steph.dto.SeanceDto;
import com.vieecoles.steph.dto.SeanceSearchResponseDto;
import com.vieecoles.steph.dto.SeancesStatDto;
import com.vieecoles.steph.entities.*;
import com.vieecoles.steph.enumerations.SearchLevelSeanceEnum;
import com.vieecoles.steph.projections.GenericProjectionLongId;
import com.vieecoles.steph.util.CommonUtils;
import com.vieecoles.steph.util.DateUtils;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.scheduler.Scheduled;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

	@Inject
	ProgressionSeanceService progressionSeanceService;
	
	@Inject
	LockService lockService;

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

	public List<Integer> dureePerSeance(int dureeTotale, int nbreSeance) {
		int d = dureeTotale / nbreSeance;
		int r = dureeTotale % nbreSeance;
		List<Integer> t = new ArrayList<Integer>();
		if (d >= 1) {
			for (int i = 0; i <= nbreSeance; i++) {
				t.add(d);
			}
		}
		if (r > 0) {
			t.add(r);
		}
		return t;
	}

	List<Seances> destructSeanceByTimeUnit(Seances seances, int minutes) {
		if (minutes == 0) {
			minutes = Constants.DEFAULT_DUREE_SEANCE_MINUTES;
		}
		int duree = DateUtils.calculerDuree(seances.getHeureDeb(), seances.getHeureFin());
		int nbreSeances = duree / minutes;
		if (duree % minutes > 0) {
			nbreSeances++;
		}
		String dateDebNew = seances.getHeureDeb();
		System.out.println(String.format("Nombre de de seances pour la séance %s = %s", seances.getId(), nbreSeances));
		// L'identifiant du cahier de texte est composé de l'id de la classe et de l'id de la matière
		String idtextBookConcept = String.format("%s-%s", seances.getClasse().getId(), seances.getMatiere().getId());
		System.out.println(String.format("idtextBookConcept %s", idtextBookConcept));
		// Vérifier si le cahier de texte est verrouillé
		Boolean isTextBookLocked = lockService.isLocked(idtextBookConcept, Constants.CONCEPT_TYPE_TEXT_BOOK);
		List<Seances> list = new ArrayList<>();
		if (nbreSeances > 1) {
//			List<Integer> dureePerSeanceList = dureePerSeance(duree, nbreSeances);
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
				seance.setIsTextBookLocked(isTextBookLocked);
				seance.setIsVerrou(DateUtils.verifierHeureDansMarge(dateDebNew,
						Constants.DEFAULT_DELAI_AVANT_DESACTIVE_APPEL_MINUTES,
						Constants.DEFAULT_DELAI_APRES_DESACTIVE_APPEL_MINUTES));
				seance.setIsEnded(verifySeanceEnded(seance.getHeureFin()));
				seance.setIsClassEnded(verifySeanceEnded(seance.getHeureFin()) ? "surface-300" : "");
				seance.setDuree(DateUtils.calculerDuree(seance.getHeureDeb(), seance.getHeureFin()));
				seance.setDureeTotale(duree);
				list.add(seance);
				dateDebNew = DateUtils.ajouterMinutes(dateDebNew, minutes);
				System.out.println(
						String.format("Heure deb: %s - heure fin: %s ", seance.getHeureDeb(), seance.getHeureFin()));
			}
		}

		return list;
	}

	/**
	 * Retourne la liste de decomposition d'une seance par heure si celle ci faisait plus d'une heure.
	 * 
	 * @param seanceId
	 * @return
	 */
	public List<SeanceDto> getListSeanceDestructuredById(String seanceId) {
		Seances seance = findById(seanceId);
		List<SeanceDto> dtos = new ArrayList<SeanceDto>();
		if (seance != null) {
			List<Seances> seances = new ArrayList<Seances>();
			seances = destructSeanceByTimeUnit(seance, 0);
			System.out.println("position " + seances.size());
			if (seances.size() == 0) {
				System.out.println("Ouye");
				dtos.add(
						new SeanceDto(seance.getId(), seance.getHeureDeb(), seance.getHeureFin(), seance.getDateSeance()
								.toString(), seance.getMatiere().getId().toString(), seance.getMatiere().getLibelle(),
								String.valueOf(seance.getClasse().getId()), seance.getClasse().getLibelle(),
								String.valueOf(seance.getProfesseur().getId()), String.format("%seance %seance",
										seance.getProfesseur().getNom(), seance.getProfesseur().getPrenom()),seance.getPosition(),
								null, null));
			} else {
				System.out.println("Aie");
				dtos = seances.stream()
						.map(s -> new SeanceDto(s.getId(), s.getHeureDeb(), s.getHeureFin(),
								s.getDateSeance().toString(), s.getMatiere().getId().toString(),
								s.getMatiere().getLibelle(), String.valueOf(s.getClasse().getId()),
								s.getClasse().getLibelle(), String.valueOf(s.getProfesseur().getId()),
								String.format("%s %s", s.getProfesseur().getNom(), s.getProfesseur().getPrenom()),s.getPosition(), null,
								null))
						.collect(Collectors.toList());
			}
		} else {
			System.out.println("Yo");
		}
		return dtos;
	}

	/**
	 * Permet d'obténir le nombre de seance issues d'une séance de plusieurs heures
	 * par rapport à l'unité horaire.
	 *
	 * @param seances
	 * @param minutes
	 * @return le nombre de séances
	 */
	Integer countDestructSeanceByTimeUnit(Seances seances, int minutes) {
		if (minutes == 0)
			minutes = Constants.DEFAULT_DUREE_SEANCE_MINUTES;
		int duree = DateUtils.calculerDuree(seances.getHeureDeb(), seances.getHeureFin());
		int nbreSeances = duree / minutes;
		if (duree % minutes > 0)
			nbreSeances++;

		return nbreSeances;
	}

	public boolean verifySeanceEnded(String heure) {
		LocalTime heureActuelle = LocalTime.now();
		LocalTime heureParametre = LocalTime.parse(heure, DateTimeFormatter.ofPattern("HH:mm"));
//		System.out.println("Seance Terminée : "+heureParametre.isBefore(heureActuelle));
//		System.out.println(heureParametre +" "+heureActuelle);
		// la seance est terminée si l'heure de fin est avant l'heure actuelle
		return heureParametre.isBefore(heureActuelle);
	}

	public PanacheQuery<Seances> getListByEcoleAndDate(Long ecoleId, Date date, Integer page, Integer rows) {
		LocalDate dateToLocalDate = DateUtils.asLocalDate(date);
		date = java.sql.Date.valueOf(dateToLocalDate);
		PanacheQuery<Seances> query = Seances
				.find("classe.ecole.id = ?1 and dateSeance = ?2 order by dateSeance, heureDeb, classeLibelle desc",
						ecoleId, date)
				.page(Page.of(page != null ? page : 0, rows != null ? rows : 1000));

		return query;
	}

	public PanacheQuery<Seances> getListByEcoleAndCriteria(Long ecoleId, Long matiere, Long classe, Date dateDebut,
			Date dateFin, Integer page, Integer rows) {

		List<String> criteria = new ArrayList<>();
		Map<String, Object> params = new HashMap<>();
		params.put("ecole", ecoleId);
		StringBuffer textQuery = new StringBuffer();
		criteria.add("classe.ecole.id = :ecole");
		if (matiere != null) {
			criteria.add("matiere.id = :matiere");
			params.put("matiere", matiere);
		}
		if (classe != null) {
			criteria.add("classe.id = :classe");
			params.put("classe", classe);
		}
		if (dateDebut != null) {
			LocalDate dateDebutToLocalDate = DateUtils.asLocalDate(dateDebut);
			dateDebut = java.sql.Date.valueOf(dateDebutToLocalDate);
			criteria.add("dateSeance >= :dateDebut");
			params.put("dateDebut", dateDebut);
		}
		if (dateFin != null) {
			LocalDate dateFinToLocalDate = DateUtils.asLocalDate(dateFin);
			dateFin = java.sql.Date.valueOf(dateFinToLocalDate);
			criteria.add("dateSeance <= :dateFin");
			params.put("dateFin", dateFin);
		}
		System.out.println("size criteria " + criteria.size());
		for (int i = 0; i < criteria.size(); i++) {
			if (i > 0) {
				textQuery.append(" AND ");
			}
			textQuery.append(criteria.get(i));
		}
		String queryStringified = textQuery + " order by dateSeance, heureDeb, classeLibelle desc";

		PanacheQuery<Seances> query = Seances.find(queryStringified, params)
				.page(Page.of(page != null ? page : 0, rows != null ? rows : 1000));

		return query;

	}

	public SeanceSearchResponseDto getListDtoByEcoleAndDate(Long ecoleId, Date date, Integer page, Integer rows) {
		SeanceSearchResponseDto response = new SeanceSearchResponseDto();
		PanacheQuery<Seances> query = getListByEcoleAndDate(ecoleId, date, page, rows);
		List<SeanceDto> dtos = new ArrayList<>();
		try {
			dtos = query.list().stream().map(s -> convertToDto(s)).collect(Collectors.toList());
			response.setList(dtos);
			response.setPage(page);
			response.setRows(rows);
			response.setTotal(query.count());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return response;
	}

	public SeanceSearchResponseDto getListDtoByEcoleAndCriteria(Long ecoleId, Long matiere, Long classe, Date dateDebut,
			Date dateFin, Integer page, Integer rows) {
		SeanceSearchResponseDto response = new SeanceSearchResponseDto();
		PanacheQuery<Seances> query = getListByEcoleAndCriteria(ecoleId, matiere, classe, dateDebut, dateFin, page,
				rows);
		List<SeanceDto> dtos = new ArrayList<>();
		try {
			dtos = query.list().stream().map(s -> convertToDto(s)).collect(Collectors.toList());
			response.setList(dtos);
			response.setPage(page);
			response.setRows(rows);
			response.setTotal(query.count());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return response;
	}

	public SeanceDto convertToDto(Seances seance) {
		SeanceDto dto = new SeanceDto();
		AppelNumerique an = new AppelNumerique();
		an = appelNumeriqueService.getBySeance(seance.getId());
		ProgressionSeance ps = new ProgressionSeance();
		ps = progressionSeanceService.getBySeanceAndPosition(seance.getId(), 0);

		dto.setId(seance.getId());
		dto.setAppelId(an.getId() != null ? an.getId() : null);
		dto.setClasseId(String.valueOf(seance.getClasse().getId()));
		dto.setClasseLibelle(seance.getClasse().getLibelle());
		dto.setCtId(ps != null ? ps.getId() : null);
		dto.setHeureDebut(seance.getHeureDeb());
		dto.setHeureFin(seance.getHeureFin());
		dto.setDate(DateUtils.toStringDate(seance.getDateSeance()));
		dto.setMatiereId(seance.getMatiere().getId().toString());
		dto.setMatiereLibelle(seance.getMatiere().getLibelle());
		dto.setProfId(seance.getProfesseur() != null ? String.valueOf(seance.getProfesseur().getId()) : null);
		dto.setProfNomPrenom(seance.getProfesseur() != null
				? String.format("%s %s", seance.getProfesseur().getNom(), seance.getProfesseur().getPrenom())
				: null);
		return dto;
	}

	// La signature doit changer vu que l'année n'est plus utiliser
	public List<Seances> getListByDateAndProf(Long anneeId, Date date, Long profId) {
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
					// L'identifiant du cahier de texte est composé de l'id de la classe et de l'id de la matière
					String idtextBookConcept = String.format("%s-%s", s.getClasse().getId(), s.getMatiere().getId());
					System.out.println(String.format("idtextBookConcept %s", idtextBookConcept));
					// Vérifier si le cahier de texte est verrouillé
					Boolean isTextBookLocked = lockService.isLocked(idtextBookConcept, Constants.CONCEPT_TYPE_TEXT_BOOK);
					AppelNumerique ap = new AppelNumerique();
					System.out.println("SEANCE ID " + s.getId());
					ap = appelNumeriqueService.getBySeance(s.getId());
					s.setAppelAlreadyExist(ap.getId() != null);
					s.setIsVerrou(DateUtils.verifierHeureDansMarge(s.getHeureDeb(),
							Constants.DEFAULT_DELAI_AVANT_DESACTIVE_APPEL_MINUTES,
							Constants.DEFAULT_DELAI_APRES_DESACTIVE_APPEL_MINUTES));
					s.setIsEnded(verifySeanceEnded(s.getHeureFin()));
					s.setIsClassEnded(verifySeanceEnded(s.getHeureFin()) ? "surface-300" : "");
					s.setDuree(DateUtils.calculerDuree(s.getHeureDeb(), s.getHeureFin()));
					s.setDureeTotale(DateUtils.calculerDuree(s.getHeureDeb(), s.getHeureFin()));
					s.setIsTextBookLocked(isTextBookLocked);
					listWithDestructSeances.add(s);
				}

			}
		}
		return listWithDestructSeances;
	}

	public List<Seances> getListByStatut(String anneeId, String statut, Long ecoleId) {
		// logger.info(String.format("Annee %s - statut %s", anneeId, statut));
		try {
			return Seances.find("statut = ?1 and classe.ecole.id=?2 and annee =?3", statut, ecoleId, anneeId).list();

		} catch (RuntimeException r) {
			r.printStackTrace();
			return null;
		}
	}

	public List<Seances> getListByEcoleAndDateAndStatut(Date date, String statut, Long ecoleId) {
		// logger.info(String.format("Annee %s - statut %s", anneeId, statut));
		try {
			return Seances.find("statut=?1 and classe.ecole.id=?2 and dateSeance =?3", statut, ecoleId, date).list();

		} catch (RuntimeException r) {
			r.printStackTrace();
			return null;
		}
	}

	public List<Seances> getListByEcoleAndDate(Date date, long ecoleId) {
		// logger.info(String.format("Annee %s - statut %s", anneeId, statut));
		try {
			return Seances.find("classe.ecole.id=?1 and dateSeance =?2", ecoleId, date).list();

		} catch (RuntimeException r) {
			r.printStackTrace();
			return null;
		}
	}

	/**
	 * Permet d'obténir la liste des séances par ecole et pour une année.
	 *
	 * @param annee
	 * @param ecoleId
	 * @return
	 */
	public List<Seances> getListByEcoleAndAnnee(Long annee, long ecoleId) {
		// logger.info(String.format("Annee %s - statut %s", anneeId, statut));
		try {
			return Seances.find("classe.ecole.id=?1 and annee =?2", ecoleId, annee.toString()).list();

		} catch (RuntimeException r) {
			r.printStackTrace();
			return null;
		}
	}

	/**
	 * Retourne le nombre de séance par année et par ecole.
	 *
	 * @param annee
	 * @param ecoleId
	 * @return
	 */
	public Long countListByEcoleAndAnnee(Long annee, Long ecoleId) {
		// logger.info(String.format("Annee %s - statut %s", anneeId, statut));
		try {
			return Seances.find("classe.ecole.id=?1 and annee =?2", ecoleId, annee.toString()).count();

		} catch (RuntimeException r) {
			r.printStackTrace();
			return null;
		}
	}

	public Long countListByEcoleAndDate(Date date, long ecoleId) {
		// Obtenir le bon format de date de type Date
		LocalDate dateToLocalDate = DateUtils.asLocalDate(date);
		date = java.sql.Date.valueOf(dateToLocalDate);
		try {
			return Seances.find("classe.ecole.id=?1 and dateSeance =?2", ecoleId, date).count();

		} catch (RuntimeException r) {
			r.printStackTrace();
			return 0L;
		}
	}

	public Long countHoraireUnitByEcoleAndDate(Date date, long ecoleId) {
		// Obtenir le bon format de date de type Date
		LocalDate dateToLocalDate = DateUtils.asLocalDate(date);
		date = java.sql.Date.valueOf(dateToLocalDate);
		List<Seances> seances = getListByEcoleAndDate(date, ecoleId);
		Long nbreSeances = 0L;
		if (seances != null) {
			for (Seances s : seances) {
				nbreSeances += countDestructSeanceByTimeUnit(s, 0);
			}
		}
		return nbreSeances;
	}

	/**
	 * Cette méthode permet d'obténir le nombre de séances par unité horaire par
	 * année et par ecole.
	 *
	 * @param anneeId
	 * @param ecoleId
	 * @return
	 */
	public Long countHoraireUnitByEcoleAndAnnee(Long anneeId, long ecoleId) {
		List<Seances> seances = getListByEcoleAndAnnee(anneeId, ecoleId);
		Long nbreSeances = 0L;
		if (seances != null) {
			for (Seances s : seances) {
				nbreSeances += countDestructSeanceByTimeUnit(s, 0);
			}
		}
		return nbreSeances;
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
//		System.out.println(date);
//		System.out.println(jourNum);
//		System.out.println("anneeid ::: " + anneeId);
//		System.out.println("ecoleid :::" + ecoleId);
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
					pers = personnelMatiereClasseService.findByMatiereAndClasse(atv.getMatiere().getId(), anneeId,
							atv.getClasse().getId());
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

	@Scheduled(cron = "0 00 23 * * ?")
	public void generatorSeanceScheduler() {
		List<Ecole> ecoles = ecoleService.getList();
//		int jourNum;
		LocalDate tomorrow = LocalDate.now().plusDays(1);
//		jourNum = DateUtils.getNumDay(DateUtils.asDate(tomorrow));
//		Jour jour = jourService.findByIdSys(jourNum);
		logger.info("*** GENERATION AUTOMATIQUE DES EMPLOI DU TEMPS ***");
		System.out.println(String.format("*** GENERATION AUTOMATIQUE DES EMPLOI DU TEMPS ( %s ) ***", LocalDateTime.now()));
		logger.info(String.format("Date de generation des bulletins %s", DateUtils.asDate(tomorrow)));

		for (Ecole ecole : ecoles) {

			System.out.print(String.format("Ecole %s", ecole.getLibelle()));
			AnneeDto annee = anneeService.getOpenAnneeByEcoleDto(ecole.getId());
			if (annee.getAnneeEcoleList() != null && annee.getAnneeEcoleList().size() > 0) {
				AnneePeriode ap = AnneePeriode.find("anneeScolaire.id =?1 and ecole.id=?2 order by dateFin desc",
						annee.getAnneeEcoleList().get(0).getAnneeId(), ecole.getId()).firstResult();
//				System.out.println(String.format(" tomorrow %s compareTo dateFin %s is before ? %s", tomorrow,ap.getDateFin().toLocalDate(),!tomorrow.isAfter(ap.getDateFin().toLocalDate())));
				if (!tomorrow.isAfter(ap.getDateFin().toLocalDate())) {
					GenericProjectionLongId anneeCentrale = anneeService.findMainAnneeWithProjectionByEcole(ecole);
					try {
						generateSeances(DateUtils.asDate(tomorrow), null, ecole.getId(), anneeCentrale.getId());
						System.out.print(" --> ok");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("");
		}
		logger.info("*** FIN GENERATION ***");
		System.out.println(String.format("*** FIN DE GENERATION ( %s )***",  LocalDateTime.now()) );
		
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
					PersonnelMatiereClasse pers = personnelMatiereClasseService.findByMatiereAndClasse(
							atv.getMatiere().getId(), anneeCentrale.getId(), atv.getClasse().getId());
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

	/**
	 * Permet d'obténir les statistiques des séances pour une école
	 *
	 * @param annee
	 * @param classe
	 * @param matiere
	 */
	public SeancesStatDto getListStatSeanceByAnneeAndEcole(Long anneeId, Long ecoleId) {
		SeancesStatDto stat = new SeancesStatDto();
		Ecole ecole = Ecole.findById(ecoleId);
		// Ecole
		stat.setEcoleId(ecoleId.toString());
		stat.setEcoleLibelle(ecole.getLibelle());

		stat.setSearchLevel(SearchLevelSeanceEnum.ECOLE);
		stat.setTotalGeneral(countListByEcoleAndAnnee(anneeId, ecoleId));
		stat.setTotalJour(countListByEcoleAndDate(new Date(), ecoleId));

		Long countTodaySeanceByHoraireUnit = countHoraireUnitByEcoleAndDate(new Date(), ecoleId);
		Long countSeanceByHoraireUnit = countHoraireUnitByEcoleAndAnnee(anneeId, ecoleId);

		stat.setTotalAppelGeneral(countSeanceByHoraireUnit);
		stat.setTotalAppelJour(countTodaySeanceByHoraireUnit);
		stat.setTotalCTGeneral(countSeanceByHoraireUnit);
		stat.setTotalCTJour(countTodaySeanceByHoraireUnit);

		stat.setTotalAppelGeneralEffectue(appelNumeriqueService.countByEcoleAndAnnee(ecoleId, anneeId));
		stat.setTotalAppelJourEffectue(appelNumeriqueService.countByEcoleAndDate(ecoleId, new Date()));

		stat.setTotalCTGeneralEffectue(progressionSeanceService.countByEcoleAndAnnee(ecoleId, anneeId));
		stat.setTotalCTJourEffectue(progressionSeanceService.countByEcoleAndDate(ecoleId, new Date()));

		// stat.setListSeances(getListDtoByEcoleAndDate(ecoleId, new Date()));
		return stat;
	}

}

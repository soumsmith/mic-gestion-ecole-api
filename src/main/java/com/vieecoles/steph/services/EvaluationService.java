package com.vieecoles.steph.services;

import com.google.gson.Gson;
import com.vieecoles.entities.operations.classe;
import com.vieecoles.services.operations.classeService;
import com.vieecoles.steph.dto.DetailsEvaluationProfDto;
import com.vieecoles.steph.dto.EvaluationsProfStatDto;
import com.vieecoles.steph.dto.LockedDto;
import com.vieecoles.steph.entities.AnneePeriode;
import com.vieecoles.steph.entities.AnneeScolaire;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.Evaluation;
import com.vieecoles.steph.entities.LoggerAudit;
import com.vieecoles.steph.entities.Notes;
import com.vieecoles.steph.entities.PersonnelMatiereClasse;
import com.vieecoles.steph.util.DateUtils;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RequestScoped
public class EvaluationService implements PanacheRepositoryBase<Evaluation, Long> {

	@Inject
	AnneeService anneeService;

	@Inject
	NoteService noteService;

	@Inject
	AnneePeriodeService anneePeriodeService;

	@Inject
	PersonnelMatiereClasseService pcmService;

	@Inject
	ClasseService classeService;

	Logger logger = Logger.getLogger(EvaluationService.class.getName());

	public List<Evaluation> getList() {
		return Evaluation.listAll();
	}

	public Evaluation findById(Long id) {
		return Evaluation.findById(id);
	}

	/**
	 * Cette méthode permet d'obténir le dernier numéro des évaluations par école.
	 *
	 * @param ecoleId
	 * @return
	 */
	public Long getMaxNumeroByEcole(Long ecoleId) {
		Long max = 1L;
		Ecole ecole = Ecole.findById(ecoleId);
		if (ecole != null) {
			System.out.println("Ecole existante");
			Object maxObj = Evaluation
					.find("SELECT MAX(e.numero) FROM Evaluation e WHERE e.classe.ecole.id = ?1", ecoleId).firstResult();
			if (maxObj != null) {
				max = (Long) maxObj;
			}
		} else {
			System.out.println("Ecole inexistante");
		}
		return max;
	}

	public Evaluation findByCode(String code) {
		Evaluation evaList = null;

		try {
			evaList = Evaluation.find("code", code).singleResult();
		} catch (RuntimeException ex) {
			logger.warning("Probably no result found");
		}
		return evaList;
	}

	Integer setPecValue() {
		return 0;
	}

	@Transactional
	public Evaluation create(Evaluation ev) {
//		Gson gson = new Gson();
//		logger.info(gson.toJson(ev));
		try {
			UUID uuid = UUID.randomUUID();
			logger.info("Creation de l'evaluation " + uuid.toString());
			Long numero = 1L;
			// Obtenir la l ecole via la classe
			Classe classe = Classe.findById(ev.getClasse().getId());
			// Obetnir le dernier numéro
			numero = getMaxNumeroByEcole(classe.getEcole().getId());
			ev.setNumero(++numero);
			ev.setCode(uuid.toString());
			ev.setDateCreation(new Date());
			ev.setDateUpdate(new Date());
			ev.setPec(setPecValue());
			ev.persist();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		logger.info("id creation " + ev.getId());
		return ev;
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
		entity.setPec(ev.getPec());
		entity.setType(ev.getType());
		entity.setDateUpdate(new Date());
		entity.setNumero(ev.getNumero());
		entity.setUser(ev.getUser());
		entity.setGroupeEvaluationId(ev.getGroupeEvaluationId());
		entity.setMatiereEcole(ev.getMatiereEcole());
		entity.setPeriode(ev.getPeriode());
		// entity.setHeure(ev.getHeure());
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
	public void deleteHandle(long id, String user) {
		Evaluation entity = Evaluation.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}
		try {
			List<Notes> notesByEvaluation = noteService.getByEvaluation(id);
			if (notesByEvaluation.size() != 0) {
				for (Notes note : notesByEvaluation) {
					noteService.delete(note.getId());
				}
			}
			LoggerAudit audit = new LoggerAudit();
			UUID uuid = UUID.randomUUID();
			audit.setId(uuid.toString());
			audit.setMessage(formatMessageAudit(entity.getId().toString(), String.valueOf(notesByEvaluation.size()),
					String.valueOf(entity.getClasse().getId())));
			audit.setModule(Constants.MODULE_EVALUATION);
			audit.setTypeAction(Constants.ACTION_DELETE);
			audit.setUser(user);
			audit.setDateCreation(new Date());

			audit.persist();

			entity.delete();
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	String formatMessageAudit(String idEvaluation, String nombreNotes, String classe) {
		return (String.format("l évaluation %s a été supprimée avec %s notes (identifiant de la classe concernée: %s)",
				idEvaluation, nombreNotes, classe));
	}

	// obténir la liste des évaluations (l évaluation) par le code
	public List<Evaluation> search(String code) {
		List<Evaluation> evaList = null;

		try {
			evaList = Evaluation.find("code", code).list();
		} catch (RuntimeException ex) {
			logger.warning("Probably no result found");
		}

		return evaList;
	}

	public List<Evaluation> getByClasseAndMatiere(Long anneeId, Long classeId, Long matiereId) {
		List<Evaluation> evaList = null;

		try {
			evaList = Evaluation
					.find("annee.id=?1 and classe.id=?2 and matiereEcole.id=?3 ", anneeId, classeId, matiereId).list();
		} catch (RuntimeException ex) {
			logger.warning("Probably no result found");
		}
		return evaList;
	}

	public List<Evaluation> getNonGroupe(Long anneeId, Long ecoleId, Long niveauId, Long matiereId, Long periodeId) {
		List<Evaluation> evals = new ArrayList<>();
		if (anneeId != null && ecoleId != null && niveauId != null && matiereId != null && periodeId != null) {
			try {
				evals = Evaluation.find(
						"annee.id = ?1 and classe.ecole.id = ?2 and classe.branche.id=?3 and matiereEcole.id = ?4 and periode.id = ?5 and groupeEvaluationId is null order by date",
						anneeId, ecoleId, niveauId, matiereId, periodeId).list();
			} catch (RuntimeException r) {
				r.printStackTrace();
				logger.warning(r.getMessage());
			}
		}
		return evals;
	}

	public List<Evaluation> getByGroupe(String groupeId) {
		List<Evaluation> evals = new ArrayList<>();
		if (groupeId != null) {
			try {
				evals = Evaluation.find("groupeEvaluationId =?1 order by date", groupeId).list();
			} catch (RuntimeException r) {
				r.printStackTrace();
				logger.warning(r.getMessage());
			}
		}

		return evals;
	}

	public List<Evaluation> getByClasseAndMatiereAndPeriode(Long classeId, Long matiereId, Long periodeId,
			Long anneeId) {
		logger.info(String.format("classse: %s  | annee : %s  | matiere : %s  | periode : %s", classeId, anneeId,
				matiereId, periodeId));
		List<Evaluation> evaList = null;

		try {
			evaList = Evaluation.find("annee.id=?1 and classe.id=?2 and matiereEcole.id=?3 and periode.id=?4", anneeId,
					classeId, matiereId, periodeId).list();
			if (evaList != null) {
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				for (Evaluation ev : evaList) {
//					System.out.println(dateFormat.format(ev.getDate()));
					ev.setDateToFilter(dateFormat.format(ev.getDate()));
				}
			}
		} catch (RuntimeException ex) {
			logger.warning("Probably no result found");
		}
		return evaList;
	}

	public Long getCountByClasseAndMatiere(Long anneeId, Long classeId, Long matiereId) {
		return Evaluation.find("annee.id=?1 and classe.id=?2 and matiereEcole.id=?3", anneeId, classeId, matiereId)
				.count();
	}

	// Faire une recherche via le champ
	public List<Evaluation> search(String query, Parameters params) {
		List<Evaluation> evaList = null;

		try {
			evaList = Evaluation.find(query, params).list();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			logger.warning("Probably no result found");
		}
		return evaList;
	}

	public long count() {
		return Evaluation.count();
	}

	public LockedDto isLockable(Long evaluationId) {
		Evaluation ev = findById(evaluationId);
		LockedDto dto = new LockedDto();
		int flat = 1;
		if (ev != null) {
			AnneeScolaire annee = anneeService.getById(ev.getAnnee().getId());
			AnneeScolaire anneeEcole = anneeService.getByEcoleAndAnneeDebut(ev.getClasse().getEcole().getId(),
					annee.getAnneeDebut());
			AnneePeriode anneePeriode = anneePeriodeService.getByAnneeAndEcoleAndPeriode(anneeEcole.getId(),
					ev.getClasse().getEcole().getId(), ev.getPeriode().getId());
			Date dateEvaluation = ev.getDate();
			Integer nombreJoursDelai = anneeEcole.getDelaiNotes() != null ? anneeEcole.getDelaiNotes() : 0;
//			System.out.println("nombreJoursDelai : " + nombreJoursDelai);
			Date dateLimiteSaisieAutorise = DateUtils.addDays(dateEvaluation, nombreJoursDelai);
			Date today = new Date();
			today = DateUtils.getDateAtStartDay(today);
			System.out.println(
					String.format("Date evaluation: %s - jours délai %s - Date limite: %s (is blocked? %s - today %s)",
							dateEvaluation, nombreJoursDelai, dateLimiteSaisieAutorise, flat, today));
			// Si la date délai est postérieure à la date de fin de la période retourner la
			// date de fin de période
//			System.out.println(DateUtils.asDate(anneePeriode.getDateLimite()));
//			System.out.println(dateLimiteSaisieAutorise);
			if (DateUtils.asDate(anneePeriode.getDateLimite()).compareTo(dateLimiteSaisieAutorise) >= 0)
				dto.setDateLimite(dateLimiteSaisieAutorise);
			else
				dto.setDateLimite(DateUtils.asDate(anneePeriode.getDateLimite()));

			flat = today.compareTo(dto.getDateLimite());
			if (flat <= 0)
				dto.setIsLocked(false);
		}
		return dto;
	}

	/**
	 * Cette méthode permet d'obtenir les statistiques sur les évaluations d'un
	 * professeur.
	 * 
	 * @param personnelId
	 * @param anneeId
	 * @param ecoleId
	 * @param periodeId
	 * 
	 * @return statistiques des evaluations exécutées d'un professeur dans une école
	 *         pour une année et une période définies.
	 */
	public EvaluationsProfStatDto getEvaluationStatByProf(Long personnelId, Long anneeId, Long ecoleId,
			Long periodeId) {
		List<PersonnelMatiereClasse> matieresProfList = new ArrayList<PersonnelMatiereClasse>();
		try {
			matieresProfList = pcmService.findByProfesseur(personnelId, anneeId, ecoleId);
		} catch (RuntimeException e) {
			logger.warning("-----> " + e.getMessage());
		}
		List<AnneeScolaire> anneeOuverte = anneeService.getByEcoleAndStatut(ecoleId, Constants.OUVERT);
		AnneePeriode ap = new AnneePeriode();
		if (anneeOuverte.size() > 0) {
			ap = anneePeriodeService.getByAnneeAndEcoleAndPeriode(anneeOuverte.get(0).getId(), ecoleId, periodeId);
		}
		EvaluationsProfStatDto dto = new EvaluationsProfStatDto();
		if (matieresProfList.size() > 0) {
			dto.setNomPrenoms(matieresProfList.get(0).getPersonnel().getNom() + " "
					+ matieresProfList.get(0).getPersonnel().getPrenom());
			dto.setProfPersonnelId(personnelId);
			dto.setMax(ap.getNbreEval());
		}
		List<DetailsEvaluationProfDto> listDetails = new ArrayList<>();
		for (PersonnelMatiereClasse pmc : matieresProfList) {
			Integer devExec = 0;
			Integer devNonExec = 0;
			Integer interroExec = 0;
			Integer interroNonExec = 0;
			DetailsEvaluationProfDto detail = new DetailsEvaluationProfDto();
			detail.setClasseId(pmc.getClasse().getId());
			detail.setClasse(pmc.getClasse().getLibelle());
			detail.setMatiereId(pmc.getMatiere().getId());
			detail.setMatiere(pmc.getMatiere().getLibelle());
			System.out.println("MAX ::: " + ap.getNbreEval());
			List<Evaluation> evaluations = getByClasseAndMatiereAndPeriode(pmc.getClasse().getId(),
					pmc.getMatiere().getId(), periodeId, anneeId);
			System.out.println("Nombre d évaluations : " + evaluations.size());
			if (evaluations != null) {
				for (Evaluation ev : evaluations) {
					int noteSize = noteService.getByEvaluation(ev.getId()).size();

					if (ev.getType().getLibelle().contains("Devoir")) {
						if (noteSize > 0) {
							devExec++;
						} else {
							devNonExec++;
						}
					} else if (ev.getType().getLibelle().contains("Interro")) {
						if (noteSize > 0) {
							interroExec++;
						} else {
							interroNonExec++;
						}
					}
				}
			}
			detail.setNbreDevoirExec(devExec);
			detail.setNbreDevoirNonExec(devNonExec);
			detail.setNbreInterroExec(interroExec);
			detail.setNbreInterroNonExec(interroNonExec);
			detail.setNbreTotalExec(devExec + interroExec);
			detail.setNbreTotalNonExec(devNonExec + interroNonExec);

			listDetails.add(detail);
		}
		dto.setDetails(listDetails);
		return dto;
	}
}

package com.vieecoles.steph.services;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.google.gson.Gson;
import com.vieecoles.entities.operations.personnel;
import com.vieecoles.services.utilisateurService;
import com.vieecoles.services.personnels.PersonnelService;
import com.vieecoles.steph.dto.ImportEvaluationDto;
import com.vieecoles.steph.dto.NotesLoaderDto;
import com.vieecoles.steph.entities.AnneeScolaire;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.ClasseEleve;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.EcoleHasMatiere;
import com.vieecoles.steph.entities.Eleve;
import com.vieecoles.steph.entities.Evaluation;
import com.vieecoles.steph.entities.EvaluationLoader;
import com.vieecoles.steph.entities.Matiere;
import com.vieecoles.steph.entities.Notes;
import com.vieecoles.steph.entities.NotesLoader;
import com.vieecoles.steph.entities.Periode;
import com.vieecoles.steph.entities.TypeActivite;
import com.vieecoles.steph.entities.TypeEvaluation;
import com.vieecoles.steph.util.CommonUtils;
import com.vieecoles.steph.util.DateUtils;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class EvaluationLoaderService implements PanacheRepositoryBase<EvaluationLoader, Long> {

	@Inject
	PersonnelService personnelService;

	@Inject
	EleveService eleveService;

	@Inject
	ClasseEleveService classeEleveService;

	@Inject
	ClasseService classeService;

	@Inject
	NotesLoaderService notesLoaderService;

	@Inject
	EvaluationService evaluationService;

	@Inject
	NoteService noteService;

	Logger logger = Logger.getLogger(EvaluationLoaderService.class.getName());

	public List<EvaluationLoader> getList() {
		return EvaluationLoader.listAll();
	}

	public EvaluationLoader findById(Long id) {
		return EvaluationLoader.findById(id);
	}

	public List<EvaluationLoader> findByCode(String code) {
		List<EvaluationLoader> list = EvaluationLoader.find("code", code).list();
		try {
			for (EvaluationLoader ev : list) {
				List<NotesLoader> notes = notesLoaderService.findByEvaluation(ev.getId());
				System.out.println(notes);
				for (NotesLoader note : notes) {
					NotesLoaderDto noteDto = new NotesLoaderDto();
					noteDto.setId(note.getId());
					noteDto.setNote(note.getNote());
					noteDto.setNumOrdre(note.getNumOrdre());
					ev.getNoteloaders().add(noteDto);
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return list;
	}

	public EvaluationLoader control(EvaluationLoader ev, Long classeId, Long anneeId) {
		Eleve eleve = null;
		ClasseEleve classeEleve = null;
		try {
			eleve = eleveService.findByMatricule(ev.getMatricule());
			classeEleve = classeEleveService.getByClasseAndEleveAndAnnee(classeId, eleve.getId(), anneeId);

		} catch (RuntimeException e) {
//			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		if (eleve != null) {
			ev.setNomPrenom(eleve.getNom() + " " + eleve.getPrenom());

			if (classeEleve != null) {
				ev.setApplicable(Constants.OUI);
			} else {
				ev.setApplicable(Constants.NON);
				ev.setObservation("L'élève n'appartient  pas à la classe");
			}
		} else {
			ev.setApplicable(Constants.NON);
			ev.setObservation("L'élève n'existe pas");
		}
		return ev;
	}

	@Transactional
	public void appliquerChargement(List<EvaluationLoader> evals, Long matiere, Long periode, Long annee, String noteSur, String date, Long type, String user) {

		logger.info(String.format("Matiere %s , periode %s, annee %s , noteSur %s, date %s, type %s, user %s", matiere,periode,annee,noteSur,date,type, user));

		TypeActivite typeActivite = new TypeActivite();
		typeActivite.setId(type);
//		evaluation.setAnnee(null);
		EcoleHasMatiere mat = new EcoleHasMatiere();
		mat.setId(matiere);
		Periode per = new Periode();
		per.setId(periode);
		AnneeScolaire ann = new AnneeScolaire();
		ann.setId(annee);
		Classe classe = classeService.findById(evals.get(0).getClasseId());

		// Obtenir le nombre d'evaluations à creer
		int nbreEval = 0;
		int maxi = 0;
		int nbreEleves = evals.size();
		int compteurEleve = 0;
		for (EvaluationLoader ev : evals) {
			nbreEval = 0;
			for (NotesLoaderDto note : ev.getNoteloaders()) {
				nbreEval++;
			}
			if (nbreEval > maxi) {
				maxi = nbreEval;
			}
		}
		logger.info("maxi = " + maxi);

		for (int i = 0; i < maxi; i++) {
			try {
				Evaluation evaluation = new Evaluation();
				evaluation.setMatiereEcole(mat);
				evaluation.setPeriode(per);
				evaluation.setAnnee(ann);
				evaluation.setType(typeActivite);
				evaluation.setClasse(classe);
				evaluation.setNoteSur(noteSur);
				evaluation.setUser(user);
				String dateStringFromUrl = date;
				SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss", Locale.ENGLISH);
				 Date _date;
				try {
					_date = dateFormat.parse(dateStringFromUrl);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					_date = new Date();
					e.printStackTrace();
				}
				evaluation.setDate(_date);
				evaluationService.create(evaluation);

				for (EvaluationLoader ev : evals) {
//					if (compteurEleve < nbreEleves) {
//						System.out.println(compteurEleve+" sur "+nbreEleves);
					if (ev.getApplicable().equals(Constants.OUI)) {
						Notes notes = new Notes();
						ClasseEleve classeEleve = classeEleveService.getByMatriculeAndAnnee(ev.getMatricule(),
								classe.getEcole().getId(), annee,ev.getClasseId());
						notes.setEvaluation(evaluation);
						notes.setNote(ev.getNoteloaders().get(i) != null
								? CommonUtils.roundDouble(ev.getNoteloaders().get(i).getNote(), 2)
								: null);
						notes.setClasseEleve(classeEleve);
						notes.setPec(1);
						noteService.create(notes);
//					}
					}
				}
//				compteurEleve++;
			} catch (RuntimeException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		// Mettre a jour les statuts des lignes chargées
		for (EvaluationLoader evl : evals) {
			if (evl.getApplicable().equals(Constants.OUI))
				evl.setStatut(Constants.ARCHIVE);

			update(evl);
		}

	}

	@Transactional
	public EvaluationLoader create(EvaluationLoader ev) {
		Gson gson = new Gson();
		Date date = new Date();
//		personnel pers = personnelService.findById(Long.parseLong(ev.getUser()));
//		if (pers != null)
//			ev.setNomPrenom(pers.getPersonnelnom() + " " + pers.getPersonnelprenom());
		ev.setStatut(Constants.LOADED);
		ev.setDateCreation(Timestamp.from(date.toInstant()).toString());
		ev.setDateUpdate(Timestamp.from(date.toInstant()).toString());
		logger.info(gson.toJson(ev));
		ev.persist();
		return ev;
	}

	public String handleLoading(List<EvaluationLoader> evals, Long classeId, Long anneeId) {

		return createMany(evals, classeId, anneeId);

	}

	@Transactional
	public String createMany(List<EvaluationLoader> evaluationLoaders, Long classeId, Long anneeId) {
		System.out.println("classe recu ::::: " + classeId);
		Classe classe = classeService.findById(classeId);
		UUID uuid = UUID.randomUUID();
		int i = 1;
		for (EvaluationLoader el : evaluationLoaders) {
			List<NotesLoader> noteList = new ArrayList<NotesLoader>();
			el.setCode(uuid.toString());
			el.setClasseCode(classe != null ? classe.getCode() : null);
			el.setClasseLibelle(classe != null ? classe.getLibelle() : null);
			el.setClasseId(classe != null ? classe.getId() : null);
			create(control(el, classeId, anneeId));
			System.out.println("id eval cree ::: " + el.getId());
			i = 1;
			for (String note : el.getNotes()) {
				NotesLoader noteToLoad = new NotesLoader();
				noteToLoad.setNote(Double.valueOf(note));
				noteToLoad.setEvaluationLoader(el);
				noteToLoad.setNumOrdre(i);
				noteList.add(noteToLoad);
				i++;
			}
			notesLoaderService.manyCreate(noteList);
		}
		return uuid.toString();
	}

	@Transactional
	public EvaluationLoader update(EvaluationLoader ev) {
		EvaluationLoader entity = EvaluationLoader.findById(ev.getId());
		if (entity == null) {
			throw new NotFoundException();
		}
//		entity.setId(ev.getId());
		entity.setCode(ev.getCode());
		entity.setStatut(ev.getStatut());
		entity.setDateUpdate(DateUtils.asDate(LocalDate.now()).toString());

		return entity;
	}

	public EvaluationLoader updateAndDisplay(EvaluationLoader evaluation) {
		EvaluationLoader ev;
		if (update(evaluation) != null) {
			ev = findById(evaluation.getId());
			return ev;
		}
		return null;
	}

	@Transactional
	public void delete(long id) {
		EvaluationLoader entity = EvaluationLoader.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.delete();
	}

	// obténir la liste des évaluations (l évaluation) par le code
	public List<EvaluationLoader> search(String code) {
		return EvaluationLoader.find("code", code).list();
	}

	public List<EvaluationLoader> getByStatut(String statut) {
		return EvaluationLoader.find("statut", statut).list();
	}

	public List<EvaluationLoader> getByClasseAndMatiere(Long anneeId, Long classeId, Long matiereId) {
		List<EvaluationLoader> evaList = null;
		try {
			evaList = EvaluationLoader
					.find("annee.id=?1 and classe.id=?2 and matiere.id=?3 ", anneeId, classeId, matiereId).list();
		} catch (RuntimeException ex) {
			logger.warning("Probably no result found");
		}
		return evaList;
	}

	public List<EvaluationLoader> getByClasseAndMatiereAndPeriode(Long classeId, Long matiereId, Long periodeId,
			Long anneeId) {

		List<EvaluationLoader> evaList = null;
		try {
			evaList = EvaluationLoader.find("annee.id=?1 and classe.id=?2 and matiere.id=?3 and periode.id", anneeId,
					classeId, matiereId, periodeId).list();
		} catch (RuntimeException ex) {
			logger.warning("Probably no result found");
		}
		return evaList;
	}

	public Long getCountByClasseAndMatiere(Long anneeId, Long classeId, Long matiereId) {
		return EvaluationLoader.find("annee.id=?1 and classe.id=?2 and matiere.id=?3", anneeId, classeId, matiereId)
				.count();
	}

	// Faire une recherche via le champ
	public List<EvaluationLoader> search(String query, Parameters params) {
		List<EvaluationLoader> evaList = null;
		try {
			evaList = EvaluationLoader.find(query, params).list();
		} catch (RuntimeException ex) {
			logger.warning("Probably no result found");
		}
		return evaList;
	}

	public long count() {
		return EvaluationLoader.count();
	}
}

package com.vieecoles.steph.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import com.google.gson.Gson;
import com.vieecoles.steph.dto.ClasseDto;
import com.vieecoles.steph.dto.EvaluationDto;
import com.vieecoles.steph.dto.IdLongCodeLibelleDto;
import com.vieecoles.steph.dto.MatiereNotesEleveDto;
import com.vieecoles.steph.dto.MoyenneEleveDto;
import com.vieecoles.steph.dto.MoyenneEleveOptimizedDto;
import com.vieecoles.steph.dto.NoteDto;
import com.vieecoles.steph.dto.NotesEleveDto;
import com.vieecoles.steph.dto.moyennes.EcoleMatiereDto;
import com.vieecoles.steph.dto.moyennes.PersonneDto;
import com.vieecoles.steph.entities.AbsenceEleve;
import com.vieecoles.steph.entities.AnneeScolaire;
import com.vieecoles.steph.entities.Bulletin;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.ClasseEleve;
import com.vieecoles.steph.entities.ClasseEleveMatiere;
import com.vieecoles.steph.entities.ClasseElevePeriode;
import com.vieecoles.steph.entities.ClasseMatiere;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.DetailBulletin;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.EcoleHasMatiere;
import com.vieecoles.steph.entities.Eleve;
import com.vieecoles.steph.entities.Evaluation;
import com.vieecoles.steph.entities.EvaluationPeriode;
import com.vieecoles.steph.entities.MoyenneAdjustment;
import com.vieecoles.steph.entities.Notes;
import com.vieecoles.steph.entities.Periode;
import com.vieecoles.steph.pojos.InfoCalculMoyennePojo;
import com.vieecoles.steph.pojos.MoyenneCoefPojo;
import com.vieecoles.steph.util.CommonUtils;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

@RequestScoped
public class NoteService implements PanacheRepositoryBase<Notes, Long> {

	@Inject
	ClasseEleveService classeEleveService;

	@Inject
	ClasseService classeService;

	@Inject
	EvaluationService evaluationService;

	@Inject
	ClasseEleveMatiereService classeEleveMatiereService;
	@Inject
	ClasseElevePeriodeService classeElevePeriodeService;

	@Inject
	BulletinService bulletinService;

	@Inject
	DetailBulletinService detailBulletinService;

	@Inject
	AbsenceService absenceService;

	@Inject
	EcoleService ecoleService;

	@Inject
	EvaluationPeriodeService evaluationPeriodeService;

	@Inject
	MoyenneAdjustmentService adjustmentService;

	@Inject
	EcoleHasMatiereService hasMatiereService;

	@Inject
	ClasseMatiereService classeMatiereService;

	@Inject
	EleveService eleveService;

	Logger logger = Logger.getLogger(NoteService.class.getName());

	Gson gson = new Gson();

	public List<Notes> getList() {
		return Notes.listAll();
	}

	public Notes findById(Long id) {
		return Notes.findById(id);
	}

	@Transactional
	public void create(Notes notes) {
		logger.info("--> Création de note pour " + notes.getClasseEleve().getInscription().getEleve().getNom());
		logger.info(gson.toJson(notes));
		// pec dans le calcul de moyenne par defaut
		notes.setDateCreation(new Date());
		notes.setDateUpdate(new Date());
		notes.setPec(notes.getPec() != null ? notes.getPec() : 0);
		notes.setNote(notes.getNote() != null ? notes.getNote() : 0);
		notes.persist();
	}

	@Transactional
	public Notes update(Notes notes) {
		Notes entity = Notes.findById(notes.getId());
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.setClasseEleve(notes.getClasseEleve());
		entity.setEvaluation(notes.getEvaluation());
		entity.setDateUpdate(new Date());
//		System.out.println("Note --> " + notes.getNote());
//		System.out.println("Note --> " + notes.getPec());
		entity.setNote(notes.getNote());
		entity.setPec(notes.getPec());
		return entity;
	}

	@Transactional
	public void delete(long id) {
		Notes entity = Notes.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}
		logger.info("---> Suppression de note " + id);
		entity.delete();
	}

	@Transactional
	public Notes updateAndDisplay(Notes notes) {
		Notes note;
		if (update(notes) != null) {
			note = findById(notes.getId());
//				logger.info("classe apres le get");;
//				logger.info(new Gson().toJson(cl));
			return note;
		}

		return null;
	}

	@Transactional
	public void createMany(List<Notes> notesList) {
		for (Notes note : notesList) {
			create(note);
		}
	}

	@Transactional
	public void handleNotes(List<Notes> noteList) {
		Gson gson = new Gson();
		for (Notes note : noteList) {
			if (note.getId() == 0 && note.getStatut() != null && note.getStatut().equals("M")) {
//	    			logger.info(gson.toJson(note));
				logger.info("--> Creation de note ...");
				create(note);

			} else if (note.getId() != 0 && note.getStatut() != null && note.getStatut().equals("M")) {
				logger.info(gson.toJson(note));
				logger.info("--> Maj de note ...");
				update(note);
			}
		}
	}

	public List<Notes> getByEvaluation(long evaluationId) {
		List<Notes> notes = new ArrayList<>();
		try {
			notes = Notes.find("evaluation.id = ?1", evaluationId).list();
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return notes;
	}

	public List<Notes> getByEvaluationAndPec(long evaluationId, Integer pec) {

		List<Notes> notes = new ArrayList<>();
		try {
			notes = Notes.find("evaluation.id = ?1 and pec = ?2", evaluationId, pec).list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return notes;
	}

	public List<Notes> getNotesClasse(String evalCode) {
//	    	logger.info("++++++++++"+evalCode);
		try {
			Evaluation evaluation = evaluationService.findByCode(evalCode);
			// Gson gson = new Gson();
			// obtenir la liste des eleves d une classe
			List<ClasseEleve> classeEleves = classeEleveService.getByClasseAnnee(evaluation.getClasse().getId(),
					evaluation.getAnnee().getId());
			// logger.info(gson.toJson(classeEleves));
			// obtenir la liste des notes pour une évaluation
			// getByEvaluationAndPec anciennement utilisé avec pec = 1
			List<Notes> notesList = getByEvaluation(evaluation.getId());
			List<Notes> noteListTemp = new ArrayList<Notes>();
			Boolean flat = true;
			Notes notemp;
			for (ClasseEleve ce : classeEleves) {
				flat = true;
				for (Notes note : notesList) {
					// nous avons supprimer note.getPec() == 1 dans la condition ci apres
					if (ce.getInscription().getEleve().getMatricule()
							.equals(note.getClasseEleve().getInscription().getEleve().getMatricule())
							&& note.getPec() != null) {
						noteListTemp.add(note);
						flat = false;
						break;
					}
				}
				if (flat) {
					notemp = new Notes();
					notemp.setClasseEleve(ce);
					notemp.setEvaluation(evaluation);
					noteListTemp.add(notemp);
				}
			}

//		logger.info("noteListTemp");
//		logger.info(gson.toJson(noteListTemp));
			return noteListTemp;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return new ArrayList<Notes>();
		}
	}

	public List<Notes> getNotesClasseWithPec(String evalCode, Integer pec) {
//    	logger.info("++++++++++"+evalCode);
		try {
			Evaluation evaluation = evaluationService.findByCode(evalCode);
			// Gson gson = new Gson();
			// obtenir la liste des eleves d une classe
			List<ClasseEleve> classeEleves = classeEleveService.getByClasseAnnee(evaluation.getClasse().getId(),
					evaluation.getAnnee().getId());
			// logger.info(gson.toJson(classeEleves));
			// obtenir la liste des notes pour une évaluation
			List<Notes> notesList = getByEvaluationAndPec(evaluation.getId(), pec);
			List<Notes> noteListTemp = new ArrayList<Notes>();
			Boolean flat = true;
			Notes notemp;
			for (ClasseEleve ce : classeEleves) {
				flat = true;
				for (Notes note : notesList) {
					if (ce.getInscription().getEleve().getMatricule()
							.equals(note.getClasseEleve().getInscription().getEleve().getMatricule())
							&& note.getPec() != null && note.getPec() == pec) {
//							System.out.println("$$$$$$$  "+note.getEvaluation().getMatiereEcole().getMatiere().getId());
						noteListTemp.add(note);
						flat = false;
						break;
					}
				}
				if (flat) {
					notemp = new Notes();
					notemp.setClasseEleve(ce);
					notemp.setEvaluation(evaluation);
					noteListTemp.add(notemp);
//						System.out.println("$$$$$$$000000  "+notemp.getEvaluation().getMatiereEcole().getMatiere().getId());
				}
			}

			return noteListTemp;
		} catch (RuntimeException r) {
			r.printStackTrace();
			return new ArrayList<Notes>();
		}
	}

	/**
	 * Cette méthode permet de calculer les moyennes des élèves d'une classe.
	 *
	 * @param classeId
	 * @param anneeId
	 * @param periodeId
	 * @return Liste des élèves et leurs moyennes de la période
	 */
	public List<MoyenneEleveDto> moyennesAndNotesHandle(String classeId, String anneeId, String periodeId) {
		// Obtenir la liste des evaluations dans une classe au cours de l année pour une
		// période
		logger.info("---> Processus de Calcul des moyennes des éleves d une classe");
		try {
//			System.out.println(String.format("%s %s %s", classeId, anneeId, periodeId));
			Map<Eleve, List<Notes>> noteGroup = new HashMap<Eleve, List<Notes>>();
			Parameters params = Parameters.with("classeId", Long.parseLong(classeId))
					.and("anneeId", Long.parseLong(anneeId)).and("periodeId", Long.parseLong(periodeId))
					.and("pok", Constants.PEC_1);
			String criteria = "classe.id = :classeId and annee.id = :anneeId and periode.id = :periodeId and pec = :pok";
			List<Evaluation> evalList = evaluationService.search(criteria, params);
			List<Notes> noteList = new ArrayList<Notes>();
			List<Notes> notesTemp;
			List<MoyenneEleveDto> moyenneList = new ArrayList<MoyenneEleveDto>();
			Map<EcoleHasMatiere, List<Notes>> notesMatiereGroup;
			Classe classe;
			MoyenneEleveDto moyenneEleveDto;
			EvaluationPeriode evaluationPeriode = null;
			Gson g = new Gson();
			// pour chaque évaluation avoir la liste des notes des élèves
//			System.out.println("ealist "+evalList);

			List<Evaluation> _iterateur = new ArrayList<Evaluation>();
			if (evalList != null)
				_iterateur.addAll(evalList);
//			long startTime = System.nanoTime();
			_iterateur.stream().forEach(x -> collectNotesPec(noteList, x));
//
//			long endTime = System.nanoTime();
//			long durationInSeconds = (endTime - startTime) / 1000000000;
//			System.out.println("Temps d'exécution NOte _iterateur: " + durationInSeconds + " secondes");
//		logger.info("note size " + noteList.size());
//		logger.info(gson.toJson(noteList));
			// Regroupement des notes par élève
//			long startTime2 = System.nanoTime();
			for (Notes note : noteList) {
//				System.out.println(note.getEvaluation().getMatiereEcole().getLibelle()+" "+note.getNote());
//			logger.info("note.getClasseEleve().getInscription().getEleve()");
//			logger.info(gson.toJson(note.getClasseEleve().getInscription().getEleve()));
				if (noteGroup.containsKey(note.getClasseEleve().getInscription().getEleve())) {
					logger.info("**** upd |||****>");
					noteGroup.get(note.getClasseEleve().getInscription().getEleve()).add(note);
//					System.out.println(">>>>>> udp"+note.getEvaluation().getMatiereEcole().getMatiere().getId());
//					System.out.println(note.getClasseEleve().getInscription().getEleve().getNom()+" "+note.getEvaluation().getMatiereEcole().getLibelle()+" "+ noteGroup.get(note.getClasseEleve().getInscription().getEleve()).size());
				} else {
					logger.info("<****||| new ****");
					notesTemp = new ArrayList<Notes>();
					notesTemp.add(note);
					noteGroup.put(note.getClasseEleve().getInscription().getEleve(), notesTemp);
//					System.out.println(">>>>>> new"+note.getEvaluation().getMatiereEcole().getMatiere().getId());
				}
			}
//			long endTime2 = System.nanoTime();
//			long durationInSeconds2 = (endTime2 - startTime2) / 1000000000;
//			System.out.println(
//					"Temps d'exécution Note Regroupement des notes par élève: " + durationInSeconds2 + " secondes");
			classe = classeService.findById(Long.parseLong(classeId));
//		logger.info(g.toJson(classe));listNotesByEvaluation

			// Formatage des dto
			AnneeScolaire anneeScolaire = new AnneeScolaire();
			Periode periode = new Periode();
			anneeScolaire.setId(Long.parseLong(anneeId));
			periode.setId(Long.parseLong(periodeId));

//			System.out.println("Niveau ens ::: " + classe.getEcole().getNiveauEnseignement().getId());
			if (classe.getEcole().getNiveauEnseignement().getId() == 1L) {
				evaluationPeriode = evaluationPeriodeService.findByAnneeAndEcoleAndPeriodeAndNiveau(
						Long.parseLong(anneeId), classe.getEcole().getId(), Long.parseLong(periodeId),
						classe.getBranche().getId());

			}
//			long startTime3 = System.nanoTime();
			for (Map.Entry<Eleve, List<Notes>> entry : noteGroup.entrySet()) {
				moyenneEleveDto = new MoyenneEleveDto();
				moyenneEleveDto.setEleve(entry.getKey());
				moyenneEleveDto.setClasse(classe);
				moyenneEleveDto.setAnnee(anneeScolaire);
				moyenneEleveDto.setPeriode(periode);
				if (evaluationPeriode != null) {
					moyenneEleveDto.setTypeEvaluation(evaluationPeriode.getTypeEvaluation().getId());
					moyenneEleveDto.setTypeEvationLibelle(evaluationPeriode.getTypeEvaluation().getLibelle());
					moyenneEleveDto.setNumeroEvaluation(evaluationPeriode.getNumero());
				}
				moyenneEleveDto.setNumeroIEPP(classe.getEcole().getNumeroIEPP());
				notesMatiereGroup = new HashMap<EcoleHasMatiere, List<Notes>>();
				EcoleHasMatiere matiereTemp;
				List<String> filter = new ArrayList<>();
//				Gson g = new Gson();
//			logger.info(String.format("Eleve - %s - %s", entry.getKey().getMatricule(),entry.getKey().getNom()));
				for (Notes note : entry.getValue()) {
					logger.info("note id --->" + note.getId());
					if (note.getId() != 0) {
						if (filter.contains(note.getEvaluation().getMatiereEcole().getId().toString())) {
//					if (notesMatiereGroup.containsKey(note.getEvaluation().getMatiere())) {
							logger.info("**** upd ****>" + note.getEvaluation().getMatiereEcole().getCode());
//						logger.info(g.toJson(notesMatiereGroup));
							logger.info("----------");
							logger.info(note.getEvaluation().getMatiereEcole().getCode());
							notesMatiereGroup.get(note.getEvaluation().getMatiereEcole()).add(note);
//						System.out.println("----------------------------");
//						System.out.println(g.toJson(note.getEvaluation().getMatiere()));
//						logger.info(String.format("%s - %s - %s", entry.getKey().getMatricule(),note.getEvaluation().getMatiere(),note.getNote()));
//						System.out.println(String.format("%s - %s - %s", entry.getKey().getMatricule(),note.getEvaluation().getMatiereEcole(),note.getNote()));;
						} else {
							logger.info("<*** new *****" + note.getEvaluation().getMatiereEcole().getCode());
							notesTemp = new ArrayList<Notes>();
							notesTemp.add(note);
							filter.add(note.getEvaluation().getMatiereEcole().getId().toString());
//						logger.info("ref mtiere"+note.getEvaluation().getMatiere().hashCode());
							/*
							 * Mettre a jour matiereTemp si la structure de EcoleHasMatiere évolue
							 */

							matiereTemp = new EcoleHasMatiere();
							matiereTemp.setId(note.getEvaluation().getMatiereEcole().getId());
							matiereTemp.setCode(note.getEvaluation().getMatiereEcole().getCode());
							matiereTemp.setPec(note.getEvaluation().getMatiereEcole().getPec());
							matiereTemp.setLibelle(note.getEvaluation().getMatiereEcole().getLibelle());
							matiereTemp.setMoyenne(note.getEvaluation().getMatiereEcole().getMoyenne());
							matiereTemp.setNiveauEnseignement(
									note.getEvaluation().getMatiereEcole().getNiveauEnseignement());
							matiereTemp.setCategorie(note.getEvaluation().getMatiereEcole().getCategorie());
							matiereTemp.setNumOrdre(note.getEvaluation().getMatiereEcole().getNumOrdre());
							matiereTemp.setMatiereParent(note.getEvaluation().getMatiereEcole().getMatiereParent());
							matiereTemp.setBonus(note.getEvaluation().getMatiereEcole().getBonus());
							matiereTemp.setParentMatiereLibelle(
									note.getEvaluation().getMatiereEcole().getParentMatiereLibelle());
//							System.out.println("##############"+note.getEvaluation().getMatiereEcole().getMatiere().getId());
							matiereTemp.setMatiere(note.getEvaluation().getMatiereEcole().getMatiere());
							matiereTemp.setEcole(note.getClasseEleve().getClasse().getEcole());
//						logger.info(g.toJson(matiereTemp));
//						System.out.println("----------------------");
//						System.out.println(g.toJson(note.getEvaluation().getMatiere()));
							/**
							 * ***********************************************
							 */

							notesMatiereGroup.put(matiereTemp, notesTemp);
							logger.info(String.format("%s - %s - %s", entry.getKey().getMatricule(),
									note.getEvaluation().getMatiereEcole().getCode(), note.getNote()));

						}
//					logger.info(g.toJson(notesMatiereGroup));
					}
				}
				moyenneEleveDto.setNotesMatiereMap(notesMatiereGroup);
//			logger.info(g.toJson(moyenneEleveDto));
//			moyenneEleveDto.setMatiere(null);file:///home/stephane/Documents/projets/my_Eclipse_Ws/.metadata/.plugins/org.eclipse.jdt.ui/jdt-images/0.png
//			moyenneEleveDto.setNotes(entry.getValue());
				moyenneList.add(moyenneEleveDto);
			}
//			long endTime3 = System.nanoTime();
//			long durationInSeconds3 = (endTime3 - startTime3) / 1000000000;
//			System.out.println("Temps d'exécution NOte build moyenneDto: " + durationInSeconds3 + " secondes");
			// Code pour visualiser les niveaux des matieres utilisées crant souvent des
			// bugs
//			System.out.println("Matiere et niveau");
//			for(MoyenneEleveDto m : moyenneList) {
//				for(Map.Entry<EcoleHasMatiere,List<Notes>>  n : m.getNotesMatiereMap().entrySet()) {
//					if(n.getKey().getId().equals(2044L))
//						System.out.println("PPPPPPPPPPP");
//				}
//			}
//			periode.getCode().equals("1");

//		calculMoyenneMatiere(moyenneList);
//		logger.info(moyenneList.toString());
//		logger.info("-------------------------------------------");
//			startTime = System.nanoTime();
			classementEleveParMatiere(calculMoyenneMatiere(moyenneList), classe.getBranche().getId(),
					classe.getEcole().getId());
			calculMoyenneGeneralEleve(moyenneList);
//			endTime = System.nanoTime();
//			durationInSeconds = (endTime - startTime) / 1000000000;
//			System.out.println("Temps d'exécution NOte Calculs des moyennes: " + durationInSeconds + " secondes");

			// Vérifie si la période est la denière. si oui calcul des moyennes et rang
			// annuels
			Periode periodeCtrl = Periode.findById(Long.parseLong(periodeId));
			if (periodeCtrl != null) {
				if (periodeCtrl.getIsfinal() != null && periodeCtrl.getIsfinal().equals(Constants.OUI)) {
//					System.out.println("CALCUL MOYENNE ANNUELLE");
					try {
						List<ClasseMatiere> classeMatList = ClasseMatiere.find("branche.id = ?1 and ecole.id =?2",
								classe.getBranche().getId(), classe.getEcole().getId()).list();
						classementAnnuelEleveParMatiere(moyenneList, classe.getBranche().getId(),
								classe.getEcole().getId(), periodeCtrl, classeMatList);
					} catch (Exception e) {
						e.printStackTrace();
						logger.warning("Aucune matiere defini pour la branche");
					}
				}
			}
//			Gson g = new Gson();
			Collections.sort(moyenneList);
			return moyenneList;
		} catch (RuntimeException r) {
			r.printStackTrace();
			throw r;
		}
	}

	public List<MoyenneEleveOptimizedDto> buildListMoyenneEleve(List<MoyenneEleveDto> list) {
		System.out.println("*********** DATA OPTIMIZING ***********");
		List<MoyenneEleveOptimizedDto> dtos = new ArrayList<>();
		list.stream().parallel().forEachOrdered(m -> {
			synchronized (dtos) {
				buildList(dtos, m);
			}
		});
		return dtos;
	}

	void buildList(List<MoyenneEleveOptimizedDto> listDto, MoyenneEleveDto dto) {
		listDto.add(convertOptimized(dto));
	}

	MoyenneEleveOptimizedDto convertOptimized(MoyenneEleveDto dto) {
		MoyenneEleveOptimizedDto optimized = new MoyenneEleveOptimizedDto();
		try {
			optimized.setAbsJust(dto.getAbsJust());
			optimized.setAbsNonJust(dto.getAbsNonJust());
			optimized.setAnnee(new IdLongCodeLibelleDto(dto.getAnnee().getId(), null, dto.getAnnee().getLibelle()));
			optimized.setApprAnnuelle(dto.getApprAnnuelle());
			optimized.setAppreciation(dto.getAppreciation());
			optimized.setAppreciationAnFr(dto.getAppreciationAnFr());
			optimized.setAppreciationFr(dto.getAppreciationFr());
			optimized.setAppreciationReli(dto.getAppreciationReli());
			optimized.setClasse(new ClasseDto(dto.getClasse().getEcole().getId(), dto.getClasse().getId(),
					dto.getClasse().getLibelle()));
			optimized.setClasseMatierePeriodeId(dto.getClasseMatierePeriodeId());
			optimized.setCoefFr(dto.getCoefFr());
			optimized.setEleve(new PersonneDto(dto.getEleve().getId(), dto.getEleve().getMatricule(),
					dto.getEleve().getNom(), dto.getEleve().getPrenom(), dto.getEleve().getSexe(),dto.getEleve().getUrlPhoto()));
			optimized.setIsClassed(dto.getIsClassed());
			optimized.setMoyAnFr(dto.getMoyAnFr());
			optimized.setMoyCoefFr(dto.getMoyCoefFr());
			optimized.setMoyenne(dto.getMoyenne());
			optimized.setMoyenneAnnuelle(dto.getMoyenneAnnuelle());
			optimized.setMoyenneIEPP(dto.getMoyenneIEPP());
			optimized.setMoyenneInterne(dto.getMoyenneInterne());
			optimized.setMoyenneMatiereToSort(dto.getMoyenneMatiereToSort());
			optimized.setMoyennePassage(dto.getMoyennePassage());
			optimized.setMoyFr(dto.getMoyFr());
			optimized.setMoyFrIntermediaire(dto.getMoyFrIntermediaire());
			optimized.setMoyReli(dto.getMoyReli());
			optimized.setNotes(builtNotesList(dto.getNotes()));
			optimized.setNotesMatiereMap(buildMapDto(dto.getNotesMatiereMap()));
			optimized.setNumeroEvaluation(dto.getNumeroEvaluation());
			optimized.setNumeroIEPP(dto.getNumeroIEPP());
			optimized.setPeriode(
					new IdLongCodeLibelleDto(dto.getPeriode().getId(), null, dto.getPeriode().getLibelle()));
			optimized.setRang(dto.getRang());
			optimized.setRangAnFr(dto.getRangAnFr());
			optimized.setRangAnnuel(dto.getRangAnnuel());
			optimized.setRangFr(dto.getRangFr());
			optimized.setTypeEvaluation(dto.getTypeEvaluation());
			optimized.setTypeEvationLibelle(dto.getTypeEvationLibelle());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

		return optimized;
	}

	List<NoteDto> builtNotesList(List<Notes> notes) {
		List<NoteDto> list = new ArrayList<>();
		if (notes != null) {
			notes.stream().forEach(n -> list.add(convertToNoteDto(n)));
		}
		return list;
	}

	NoteDto convertToNoteDto(Notes note) {
		NoteDto dto = new NoteDto();
		dto.setId(note.getId());
		dto.setPec(note.getPec());
		dto.setAppreciation(note.getAppreciation());
		dto.setNote(note.getNote());
		dto.setStatut(note.getStatut());
		EvaluationDto evaluationDto = new EvaluationDto(note.getEvaluation().getId(), note.getEvaluation().getCode(),
				note.getEvaluation().getDuree(), note.getEvaluation().getNoteSur(), note.getEvaluation().getPec());
		evaluationDto.setAnnee(new IdLongCodeLibelleDto(note.getEvaluation().getAnnee().getId(), null,
				note.getEvaluation().getAnnee().getLibelle()));
		evaluationDto.setClasse(new IdLongCodeLibelleDto(note.getEvaluation().getClasse().getId(), null,
				note.getEvaluation().getClasse().getLibelle()));
		evaluationDto.setMatiereEcole(new IdLongCodeLibelleDto(note.getEvaluation().getMatiereEcole().getId(), null,
				note.getEvaluation().getMatiereEcole().getLibelle()));
		evaluationDto.setDateLimite(note.getEvaluation().getDateLimite());
		dto.setEvaluation(evaluationDto);
		if (note.getPersonnel() != null) {
			dto.setPersonnel(new PersonneDto(note.getPersonnel().getId(), note.getPersonnel().getCode(),
					note.getPersonnel().getNom(), note.getPersonnel().getPrenom(), note.getPersonnel().getSexe(),null));
		}
		dto.setClasseEleve(new IdLongCodeLibelleDto(note.getClasseEleve().getId(), null, null));

		return dto;
	}

	Map<EcoleMatiereDto, List<NoteDto>> buildMapDto(Map<EcoleHasMatiere, List<Notes>> map) {
		Map<EcoleMatiereDto, List<NoteDto>> mapDto = new LinkedHashMap<>();
		if (map != null) {
			for (Map.Entry<EcoleHasMatiere, List<Notes>> entry : map.entrySet()) {
				mapDto.put(convertToMatiereDto(entry.getKey()), builtNotesList(entry.getValue()));
			}
		}
		return mapDto;
	}

	EcoleMatiereDto convertToMatiereDto(EcoleHasMatiere ecoleMatiere) {
		EcoleMatiereDto dto = new EcoleMatiereDto();
		dto.setId(ecoleMatiere.getId());
		dto.setCode(ecoleMatiere.getCode());
		dto.setLibelle(ecoleMatiere.getLibelle());
		dto.setMoyenne(ecoleMatiere.getMoyenne());
		dto.setCoef(ecoleMatiere.getCoef());
		dto.setRang(ecoleMatiere.getRang());
		dto.setPec(ecoleMatiere.getPec());
		if (ecoleMatiere.getMatiereParent() != null) {
			dto.setMatiereParent(new IdLongCodeLibelleDto(ecoleMatiere.getMatiereParent().getId(),
					ecoleMatiere.getMatiereParent().getCode(), ecoleMatiere.getMatiereParent().getLibelle()));
		}
		dto.setAppreciation(ecoleMatiere.getAppreciation());
		dto.setCategorie(new IdLongCodeLibelleDto());
		dto.setBonus(ecoleMatiere.getBonus());
		dto.setNumOrdre(ecoleMatiere.getNumOrdre());

		return dto;
	}

	public synchronized void collectNotesPec(List<Notes> noteList, Evaluation ev) {
		List<Notes> listNotesByEvaluation = new ArrayList<Notes>();
//				System.out.println(ev.getMatiereEcole().getLibelle()+" :: pec ->"+ ev.getPec().toString());
		logger.info(ev.getPec().toString());
		if (ev.getPec() != null && ev.getPec() == Constants.PEC_1) {
			listNotesByEvaluation = getNotesClasseWithPec(ev.getCode(), Constants.PEC_1);
			noteList.addAll(listNotesByEvaluation);
		}
	}

	public MoyenneEleveDto moyennesAndMatiereAndNotesByMatriculeHandle(String matricule, String matiereId,
			String anneeId, String periodeId) {

		ClasseEleve ce = classeEleveService.getByMatriculeAndAnnee(matricule, Long.parseLong(anneeId));
		MoyenneEleveDto mdto = new MoyenneEleveDto();
		if (ce != null) {
			List<MoyenneEleveDto> listMoyenne = moyennesAndMatiereAndNotesHandle(String.valueOf(ce.getClasse().getId()),
					matiereId, anneeId, periodeId);

			for (MoyenneEleveDto moy : listMoyenne) {
				if (moy.getEleve().getMatricule().equals(matricule)) {
					mdto = moy;
					break;
				}
			}
		}
		return mdto;
	}

	public MoyenneEleveDto moyennesAndNotesByMatriculeHandle(String matricule, String anneeId, String periodeId) {

		ClasseEleve ce = classeEleveService.getByMatriculeAndAnnee(matricule, Long.parseLong(anneeId));
		MoyenneEleveDto mdto = new MoyenneEleveDto();
		if (ce != null) {
			List<MoyenneEleveDto> listMoyenne = moyennesAndNotesHandle(String.valueOf(ce.getClasse().getId()), anneeId,
					periodeId);

			for (MoyenneEleveDto moy : listMoyenne) {
				if (moy.getEleve().getMatricule().equals(matricule)) {
					mdto = moy;
					break;
				}
			}
		}
		return mdto;
	}

	public List<MoyenneEleveDto> moyennesAndMatiereAndNotesHandle(String classeId, String matiereId, String anneeId,
			String periodeId) {
		// Obtenir la liste des evaluations dans une classe et une matiere au cours de l
		// année pour une
		// période
		logger.info("---> Processus de Calcul des moyennes des éleves d une matiere");
		try {
			Map<Eleve, List<Notes>> noteGroup = new HashMap<Eleve, List<Notes>>();
			Parameters params = Parameters.with("classeId", Long.parseLong(classeId))
					.and("matiereId", Long.parseLong(matiereId)).and("anneeId", Long.parseLong(anneeId))
					.and("periodeId", Long.parseLong(periodeId)).and("pok", 1);
			String criteria = "classe.id = :classeId and matiereEcole.id= :matiereId and annee.id = :anneeId and periode.id = :periodeId and pec = :pok";
			List<Evaluation> evalList = evaluationService.search(criteria, params);
			List<Notes> noteList = new ArrayList<Notes>();
			List<Notes> notesTemp;
			List<MoyenneEleveDto> moyenneList = new ArrayList<MoyenneEleveDto>();
			Map<EcoleHasMatiere, List<Notes>> notesMatiereGroup;
			Classe classe;
			MoyenneEleveDto moyenneEleveDto;
//			Gson g = new Gson();
			// pour chaque évaluation avoir la liste des notes des élèves
			for (Evaluation ev : evalList) {
				logger.info(ev.getMatiereEcole().getLibelle() + "->" + ev.getPec().toString());
				if (ev.getPec() != null && ev.getPec() == 1)
					noteList.addAll(getNotesClasseWithPec(ev.getCode(), Constants.PEC_1));
			}
//		logger.info("note size " + noteList.size());
//		logger.info(gson.toJson(noteList));
			// Regroupement des notes par élève
			for (Notes note : noteList) {
//			logger.info("note.getClasseEleve().getInscription().getEleve()");
//			logger.info(gson.toJson(note.getClasseEleve().getInscription().getEleve()));
				if (noteGroup.containsKey(note.getClasseEleve().getInscription().getEleve())) {
					logger.info("**** upd |||****>");
					noteGroup.get(note.getClasseEleve().getInscription().getEleve()).add(note);
				} else {
					logger.info("<****||| new ****");
					notesTemp = new ArrayList<Notes>();
					notesTemp.add(note);
					noteGroup.put(note.getClasseEleve().getInscription().getEleve(), notesTemp);
				}
			}
//			System.out.println("---> " + classeId);
			classe = classeService.findById(Long.parseLong(classeId));

//			Gson g = new Gson();
//			logger.info(g.toJson(classe));
			// Formatage des dto
			AnneeScolaire anneeScolaire = new AnneeScolaire();
			Periode periode = new Periode();
			anneeScolaire.setId(Long.parseLong(anneeId));
			periode.setId(Long.parseLong(periodeId));
			for (Map.Entry<Eleve, List<Notes>> entry : noteGroup.entrySet()) {
				moyenneEleveDto = new MoyenneEleveDto();
				moyenneEleveDto.setEleve(entry.getKey());
				moyenneEleveDto.setClasse(classe);
				moyenneEleveDto.setAnnee(anneeScolaire);
				moyenneEleveDto.setPeriode(periode);
				notesMatiereGroup = new HashMap<EcoleHasMatiere, List<Notes>>();
				EcoleHasMatiere matiereTemp;
				List<String> filter = new ArrayList<>();
//			logger.info(String.format("Eleve - %s - %s", entry.getKey().getMatricule(),entry.getKey().getNom()));
				for (Notes note : entry.getValue()) {
					logger.info("note id --->" + note.getId());
					if (note.getId() != 0) {
						if (filter.contains(note.getEvaluation().getMatiereEcole().getCode())) {
//					if (notesMatiereGroup.containsKey(note.getEvaluation().getMatiere())) {
							logger.info("**** upd ****>" + note.getEvaluation().getMatiereEcole().getCode());
//						logger.info(g.toJson(notesMatiereGroup));
//						logger.info("----------");
//						logger.info(g.toJson(note.getEvaluation().getMatiereEcole()));
							notesMatiereGroup.get(note.getEvaluation().getMatiereEcole()).add(note);
//						System.out.println("----------------------------");
//						System.out.println(g.toJson(note.getEvaluation().getMatiere()));
//						logger.info(String.format("%s - %s - %s", entry.getKey().getMatricule(),note.getEvaluation().getMatiere(),note.getNote()));
						} else {
							logger.info("<*** new *****" + note.getEvaluation().getMatiereEcole().getCode());
							notesTemp = new ArrayList<Notes>();
							notesTemp.add(note);
							filter.add(note.getEvaluation().getMatiereEcole().getCode());
//						logger.info("ref mtiere"+note.getEvaluation().getMatiere().hashCode());
							/*
							 * Mettre a jour matiereTemp si la structure de EcoleHasMatiere évolue
							 */

							matiereTemp = new EcoleHasMatiere();
							matiereTemp.setId(note.getEvaluation().getMatiereEcole().getId());
							matiereTemp.setCode(note.getEvaluation().getMatiereEcole().getCode());
							matiereTemp.setPec(note.getEvaluation().getMatiereEcole().getPec());
							matiereTemp.setLibelle(note.getEvaluation().getMatiereEcole().getLibelle());
							matiereTemp.setMoyenne(note.getEvaluation().getMatiereEcole().getMoyenne());
							matiereTemp.setNiveauEnseignement(
									note.getEvaluation().getMatiereEcole().getNiveauEnseignement());
							matiereTemp.setCategorie(note.getEvaluation().getMatiereEcole().getCategorie());
							matiereTemp.setNumOrdre(note.getEvaluation().getMatiereEcole().getNumOrdre());
							matiereTemp.setMatiereParent(note.getEvaluation().getMatiereEcole().getMatiereParent());
							matiereTemp.setBonus(note.getEvaluation().getMatiereEcole().getBonus());
							matiereTemp.setParentMatiereLibelle(
									note.getEvaluation().getMatiereEcole().getParentMatiereLibelle());
							matiereTemp.setMatiere(note.getEvaluation().getMatiereEcole().getMatiere());
							matiereTemp.setEcole(note.getClasseEleve().getClasse().getEcole());
//						logger.info(g.toJson(matiereTemp));
//						System.out.println("----------------------");
//						System.out.println(g.toJson(note.getEvaluation().getMatiere()));
							/**
							 * ***********************************************
							 */

							notesMatiereGroup.put(matiereTemp, notesTemp);
							logger.info(String.format("%s - %s - %s", entry.getKey().getMatricule(),
									note.getEvaluation().getMatiereEcole().getCode(), note.getNote()));

						}
//					logger.info(g.toJson(notesMatiereGroup));
					}
				}
				moyenneEleveDto.setNotesMatiereMap(notesMatiereGroup);
//			logger.info(g.toJson(moyenneEleveDto));
//			moyenneEleveDto.setMatiere(null);
//			moyenneEleveDto.setNotes(entry.getValue());
				moyenneList.add(moyenneEleveDto);
			}
//		calculMoyenneMatiere(moyenneList);
//			logger.info(g.toJson(classe));
//		logger.info("-------------------------------------------");
			classementEleveParMatiere(calculMoyenneMatiere(moyenneList), classe.getBranche().getId(),
					classe.getEcole().getId());

			for (MoyenneEleveDto eleve : moyenneList) {

				ClasseEleveMatiere cep = classeEleveMatiereService.findByClasseAndMatiereAndEleveAndAnneeAndPeriode(
						eleve.getClasse().getId(), Long.parseLong(matiereId), eleve.getEleve().getId(),
						eleve.getAnnee().getId(), eleve.getPeriode().getId());
				if (cep == null)
					eleve.setIsClassed(Constants.OUI);
				else
					eleve.setIsClassed(cep.getIsClassed());
			}
//	    logger.info(g.toJson(moyenneList));
			return moyenneList;
		} catch (RuntimeException r) {
			r.printStackTrace();
			return new ArrayList<MoyenneEleveDto>();
		}
	}

	/**
	 * Cette méthode renvoie le classement des eleves d'une classe dans une matière
	 * et pour une période sans ténir compte de la logique des matières EMR.
	 *
	 * @param classeId
	 * @param matiereId
	 * @param anneeId
	 * @param periodeId
	 * @return la liste du classement des élèves
	 */
	public List<MoyenneEleveDto> moyennesAndMatiereAndNotesWithoutEMRHandle(String classeId, String matiereId,
			String anneeId, String periodeId) {
		logger.info("---> Processus de Calcul des moyennes des éleves d une matiere sans logique EMR");
		try {
			Map<Eleve, List<Notes>> noteGroup = new HashMap<Eleve, List<Notes>>();
			Parameters params = Parameters.with("classeId", Long.parseLong(classeId))
					.and("matiereId", Long.parseLong(matiereId)).and("anneeId", Long.parseLong(anneeId))
					.and("periodeId", Long.parseLong(periodeId)).and("pok", 1);
			String criteria = "classe.id = :classeId and matiereEcole.id= :matiereId and annee.id = :anneeId and periode.id = :periodeId and pec = :pok";
			List<Evaluation> evalList = evaluationService.search(criteria, params);
			List<Notes> noteList = new ArrayList<Notes>();
			List<Notes> notesTemp;
			List<MoyenneEleveDto> moyenneList = new ArrayList<MoyenneEleveDto>();
			Map<EcoleHasMatiere, List<Notes>> notesMatiereGroup;
			Classe classe;
			MoyenneEleveDto moyenneEleveDto;
			for (Evaluation ev : evalList) {
				logger.info(ev.getMatiereEcole().getLibelle() + "->" + ev.getPec().toString());
				if (ev.getPec() != null && ev.getPec() == 1)
					noteList.addAll(getNotesClasseWithPec(ev.getCode(), Constants.PEC_1));
			}
			for (Notes note : noteList) {
				if (noteGroup.containsKey(note.getClasseEleve().getInscription().getEleve())) {
					logger.info("**** upd |||****>");
					noteGroup.get(note.getClasseEleve().getInscription().getEleve()).add(note);
				} else {
					logger.info("<****||| new ****");
					notesTemp = new ArrayList<Notes>();
					notesTemp.add(note);
					noteGroup.put(note.getClasseEleve().getInscription().getEleve(), notesTemp);
				}
			}
			classe = classeService.findById(Long.parseLong(classeId));
			AnneeScolaire anneeScolaire = new AnneeScolaire();
			Periode periode = new Periode();
			anneeScolaire.setId(Long.parseLong(anneeId));
			periode.setId(Long.parseLong(periodeId));
			for (Map.Entry<Eleve, List<Notes>> entry : noteGroup.entrySet()) {
				moyenneEleveDto = new MoyenneEleveDto();
				moyenneEleveDto.setEleve(entry.getKey());
				moyenneEleveDto.setClasse(classe);
				moyenneEleveDto.setAnnee(anneeScolaire);
				moyenneEleveDto.setPeriode(periode);
				notesMatiereGroup = new HashMap<EcoleHasMatiere, List<Notes>>();
				EcoleHasMatiere matiereTemp;
				List<String> filter = new ArrayList<>();
				for (Notes note : entry.getValue()) {
					logger.info("note id --->" + note.getId());
					if (note.getId() != 0) {
						if (filter.contains(note.getEvaluation().getMatiereEcole().getCode())) {
							logger.info("**** upd ****>" + note.getEvaluation().getMatiereEcole().getCode());
							notesMatiereGroup.get(note.getEvaluation().getMatiereEcole()).add(note);
						} else {
							logger.info("<*** new *****" + note.getEvaluation().getMatiereEcole().getCode());
							notesTemp = new ArrayList<Notes>();
							notesTemp.add(note);
							filter.add(note.getEvaluation().getMatiereEcole().getCode());
							/*
							 * Mettre a jour matiereTemp si la structure de EcoleHasMatiere évolue
							 */

							matiereTemp = new EcoleHasMatiere();
							matiereTemp.setId(note.getEvaluation().getMatiereEcole().getId());
							matiereTemp.setCode(note.getEvaluation().getMatiereEcole().getCode());
							matiereTemp.setPec(note.getEvaluation().getMatiereEcole().getPec());
							matiereTemp.setLibelle(note.getEvaluation().getMatiereEcole().getLibelle());
							matiereTemp.setMoyenne(note.getEvaluation().getMatiereEcole().getMoyenne());
							matiereTemp.setNiveauEnseignement(
									note.getEvaluation().getMatiereEcole().getNiveauEnseignement());
							matiereTemp.setCategorie(note.getEvaluation().getMatiereEcole().getCategorie());
							matiereTemp.setNumOrdre(note.getEvaluation().getMatiereEcole().getNumOrdre());
							matiereTemp.setMatiereParent(note.getEvaluation().getMatiereEcole().getMatiereParent());
							matiereTemp.setBonus(note.getEvaluation().getMatiereEcole().getBonus());
							matiereTemp.setParentMatiereLibelle(
									note.getEvaluation().getMatiereEcole().getParentMatiereLibelle());
							matiereTemp.setMatiere(note.getEvaluation().getMatiereEcole().getMatiere());
							matiereTemp.setEcole(note.getClasseEleve().getClasse().getEcole());

							notesMatiereGroup.put(matiereTemp, notesTemp);
							logger.info(String.format("%s - %s - %s", entry.getKey().getMatricule(),
									note.getEvaluation().getMatiereEcole().getCode(), note.getNote()));

						}
					}
				}
				moyenneEleveDto.setNotesMatiereMap(notesMatiereGroup);
				moyenneList.add(moyenneEleveDto);
			}
			classementEleveParMatiere(calculMoyenneMatiereWithoutEMR(moyenneList), classe.getBranche().getId(),
					classe.getEcole().getId());

			for (MoyenneEleveDto eleve : moyenneList) {

				ClasseEleveMatiere cep = classeEleveMatiereService.findByClasseAndMatiereAndEleveAndAnneeAndPeriode(
						eleve.getClasse().getId(), Long.parseLong(matiereId), eleve.getEleve().getId(),
						eleve.getAnnee().getId(), eleve.getPeriode().getId());
				if (cep == null)
					eleve.setIsClassed(Constants.OUI);
				else
					eleve.setIsClassed(cep.getIsClassed());
			}
			return moyenneList;
		} catch (RuntimeException r) {
			r.printStackTrace();
			return new ArrayList<MoyenneEleveDto>();
		}
	}

	List<MoyenneEleveDto> calculMoyenneMatiere(List<MoyenneEleveDto> moyEleve) {
		logger.info("---> Calcul des moyennes par matiere");
		Double moyenne;
		Double moyenneEMR;
		Double moyenneEMRIntermediaire;
		List<Double> noteList;
		List<Notes> moyenneEMRList;

		List<Double> moyennesFrExcpt = new ArrayList<Double>();

		Double diviser;
		Double somme;

		Double diviserEMR;
		Double sommeEMR;
		Double sommeEMRIntermediaire;

		Gson g = new Gson();

//		System.out.println(g.toJson(moyEleve));
		for (MoyenneEleveDto me : moyEleve) {
			EcoleHasMatiere ehm = new EcoleHasMatiere();
			sommeEMR = 0.0;
			sommeEMRIntermediaire = 0.0;
			moyenneEMRIntermediaire = 0.0;
			diviserEMR = 0.0;
			moyenneEMR = 0.0;
			moyenneEMRList = new ArrayList<>();
			List<MoyenneCoefPojo> moyennesSousMatieresFrancais = new ArrayList<MoyenneCoefPojo>();
			List<Double> moyennesMatieresReligion = new ArrayList<Double>();
			Boolean EMRFlat = false;
			Boolean CheckEMRCalculFlat = false;
			Boolean calculExcpFrFlat = false;
			Boolean calculExcpReligionFlat = false;
//			Map<EcoleHasMatiere, List<Notes>> matiereNoteEMRMap = new HashMap<EcoleHasMatiere, List<Notes>>();
			for (Map.Entry<EcoleHasMatiere, List<Notes>> entry : me.getNotesMatiereMap().entrySet()) {
				moyenne = 0.0;
				noteList = new ArrayList<Double>();
				List<Double> noteTestLourdList = new ArrayList<Double>();
//				System.out.println(entry.getKey().getLibelle());
//				System.out.println("Taille notes "+entry.getValue().size());
				diviser = 0.0;
				somme = 0.0;
				Double diviserTestLourd = 0.0;
				// Vérifier l'existence de modification de moyenne
				MoyenneAdjustment moyenneAdjustment = adjustmentService.getByAnneePeriodeMatriculeAndMatiereAndStatut(
						me.getAnnee().getId(), me.getPeriode().getId(), me.getEleve().getMatricule(),
						entry.getKey().getId(), Constants.VALID);
				String isAdjustment = Constants.NON;
				if (moyenneAdjustment.getId() == null) {
					for (Notes note : entry.getValue()) {
// On vérifie que l'evaluation et la note sont prises en compte dans le calcul de moyenne
						if (note.getEvaluation().getPec() == Constants.PEC_1 && note.getPec() != null
								&& note.getPec() == Constants.PEC_1) {
							if (note.getEvaluation().getType() != null
									&& note.getEvaluation().getType().getCode() != null
									&& note.getEvaluation().getType().getCode().equals(Constants.CODE_TEST_LOURD)) {
								noteTestLourdList.add(note.getNote());
								diviserTestLourd = diviserTestLourd
										+ (Double.parseDouble(note.getEvaluation().getNoteSur())
												/ Double.parseDouble(Constants.DEFAULT_NOTE_SUR));
								entry.getKey().setTestLourdNoteSur(Integer.parseInt(note.getEvaluation().getNoteSur()));
//								System.out.println("TEST LOURD DETECTE note :: "+note.getNote());
							} else {
								noteList.add(note.getNote());
//						System.out.println(String.format("PEC value >>> %s", note.getEvaluation().getPec()));
								diviser = diviser + (Double.parseDouble(note.getEvaluation().getNoteSur())
										/ Double.parseDouble(Constants.DEFAULT_NOTE_SUR));
							}
						}
					}

//				entry.getValue().clear();
//				entry.getValue().

					for (Double note : noteList) {
//					System.out.println(note);
						somme += note;
					}

					moyenne = somme / (diviser.equals(Double.parseDouble("0")) ? Double.parseDouble("1") : diviser);

					entry.getKey().setMoyenneIntermediaire(CommonUtils.roundDouble(
							somme / (diviser.equals(Double.parseDouble("0")) ? Double.parseDouble("1") : diviser), 2));
					if (noteTestLourdList.size() > 0) {
						Double moyenneTstLourd = 0.0;
						Double sommeTstLourd = 0.0;
						for (Double noteTestLrd : noteTestLourdList) {
							sommeTstLourd = sommeTstLourd + noteTestLrd;
						}
						moyenneTstLourd = sommeTstLourd / diviserTestLourd;
//						System.out.println("Moyenne test lourd = "+moyenneTstLourd);
//						System.out.println("Moyenne intermediaire  = "+entry.getKey().getMoyenneIntermediaire());
						entry.getKey().setTestLourdNote(sommeTstLourd);
						moyenne = (moyenne + moyenneTstLourd * 2) / 3;
//						System.out.println("Moyenne finale = "+ moyenne);
//						System.out.println("Moyenne finale = "+ moyenne);
					}

					logger.info("Moyenne = " + somme + " / " + diviser + " = " + CommonUtils.roundDouble(moyenne, 2));
				} else {
					isAdjustment = Constants.OUI;
					moyenne = moyenneAdjustment.getMoyenne();
					entry.getKey().setMoyenneIntermediaire(moyenne);
					logger.info("Moyenne repêchage trouvée = " + CommonUtils.roundDouble(moyenne, 2));
				}
				entry.getKey().setMoyenne(CommonUtils.roundDouble(moyenne, 2));
				entry.getKey().setAppreciation(CommonUtils.appreciation(moyenne));
				entry.getKey().setIsAdjustment(isAdjustment);

				// Calcul de la matiere Francais pour les Rapports de bulletin (les données ne
				// figurent pas dans la table DetailBulletin)
				if (entry.getKey().getNiveauEnseignement().getCode().equals(Constants.CODE_NIVEAU_ENS_SECONDAIRE)
						&& entry.getKey().getMatiereParent() != null
						&& entry.getKey().getMatiereParent().getMatiere() != null
						&& entry.getKey().getMatiereParent().getMatiere().getMatiereParent() != null
						&& entry.getKey().getMatiereParent().getMatiere().getMatiereParent()
								.equals(Constants.ID_MATIERE_FRANCAIS_CENTRAL)) {
					MoyenneCoefPojo mc = new MoyenneCoefPojo();
					ClasseMatiere cm = new ClasseMatiere();
					cm = classeMatiereService.getByMatiereAndBranche(entry.getKey().getId(),
							me.getClasse().getBranche().getId(), me.getClasse().getEcole().getId());
					mc.setCoef(cm != null ? Double.valueOf(cm.getCoef()) : null);
					mc.setMoyenne(CommonUtils.roundDouble(moyenne, 2));
					mc.setMoyenneIntermediaire(entry.getKey().getMoyenneIntermediaire());
					moyennesSousMatieresFrancais.add(mc);
					calculExcpFrFlat = true;
				}

				// Traitement cas des sous matières EMR
				if (entry.getKey().getMatiereParent() != null && entry.getKey().getMatiereParent().getIsEMR() != null
						&& entry.getKey().getMatiereParent().getIsEMR().equals(Constants.OUI)) {
					CheckEMRCalculFlat = true;
					if (diviserEMR == 0.0) {

						// Construction d'un Map
//						matiereNoteEMRMap = new HashMap<EcoleHasMatiere, List<Notes>>();
						Map<EcoleHasMatiere, List<Notes>> matiereNoteEMRMap = new HashMap<EcoleHasMatiere, List<Notes>>();
						matiereNoteEMRMap.put(entry.getKey().getMatiereParent(), new ArrayList<Notes>());
						ehm = entry.getKey().getMatiereParent();
						EMRFlat = true;
					}

					sommeEMR += moyenne;
					sommeEMRIntermediaire += entry.getKey().getMoyenneIntermediaire();
					diviserEMR++;
					Evaluation evalEMR = new Evaluation();
					evalEMR.setAnnee(me.getAnnee());
					evalEMR.setClasse(me.getClasse());
					evalEMR.setPec(Constants.PEC_1);
					evalEMR.setCode(me.getNumeroEvaluation() + "_1");
					evalEMR.setId(new Random().nextLong());
					evalEMR.setNoteSur(Constants.DEFAULT_NOTE_SUR);
					evalEMR.setMatiereEcole(entry.getKey().getMatiereParent());
					evalEMR.setPeriode(me.getPeriode());

					Notes noteEMR = new Notes();
					noteEMR.setClasseEleve(entry.getValue().get(0).getClasseEleve());
					noteEMR.setEvaluation(evalEMR);
					noteEMR.setId(new Random().nextLong());
					noteEMR.setNote(CommonUtils.roundDouble(moyenne, 2));
					noteEMR.setPec(Constants.PEC_1);
//
					moyenneEMRList.add(noteEMR);

				}

				if (!CheckEMRCalculFlat
						&& entry.getKey().getNiveauEnseignement().getCode().equals(Constants.CODE_NIVEAU_ENS_SECONDAIRE)
						&& entry.getKey().getCategorie().getCode().equals(Constants.CODE_CATEGORIE_RELIGION)) {
					moyennesMatieresReligion.add(CommonUtils.roundDouble(moyenne, 2));
					calculExcpReligionFlat = true;
					CheckEMRCalculFlat = false;
				}

//				logger.info("++++> "+g.toJson(me.getNotesMatiereMap()));
			}
			if (calculExcpFrFlat) {
				Double sumMoyFr = 0.0;
				Double sumMoyFrIntrmd = 0.0;
				Double moyFr = 0.0;
				Double moyFrIntrmd = 0.0;
				Double moyCoefFr = 0.0;
				Double coefFr = 0.0;
//				CommonUtils
//						.roundDouble(moyennesSousMatieresFrancais.stream().mapToDouble(a -> a).average().orElse(0), 2);
				for (MoyenneCoefPojo msmf : moyennesSousMatieresFrancais) {
					sumMoyFr = sumMoyFr + msmf.getMoyenne() * msmf.getCoef();
					coefFr = coefFr + msmf.getCoef();
					sumMoyFrIntrmd = sumMoyFrIntrmd + msmf.getMoyenneIntermediaire() * msmf.getCoef();

				}
				moyFr = CommonUtils.roundDouble(sumMoyFr / (coefFr != 0.0 ? coefFr : 1), 2);
				moyFrIntrmd = CommonUtils.roundDouble(sumMoyFrIntrmd / (coefFr != 0.0 ? coefFr : 1), 2);
				moyCoefFr = moyFr * coefFr;
//						moyennesSousMatieresFrancais.stream().mapToDouble(a -> a).sum();
//				System.out
//						.println(String.format("Matricule [%s] Moyenne Interm. français = %s - Coef =%s", me.getEleve().getMatricule(),
//								moyFrIntrmd, moyCoefFr));
				me.setMoyFr(moyFr);
				me.setMoyFrIntermediaire(moyFrIntrmd);
				me.setCoefFr(coefFr);
				me.setMoyCoefFr(moyCoefFr);
				me.setAppreciationFr(CommonUtils.appreciation(moyFr));
//				System.out.println(String.format("MATRICULE %s MOYENNE %s cCOEF %s MOY COEF %s APPPR %s", me.getEleve().getMatricule(), moyFr,coefFr,moyCoefFr, CommonUtils.appreciation(moyFr)));

				moyennesFrExcpt.add(moyFr);
			}

			if (EMRFlat) {
				// Rechercher l 'id de la matiere emr de l'ecole
				EcoleHasMatiere hasMatiere = hasMatiereService.getEMRByEcole(me.getClasse().getEcole().getId());
				// verifier qu'il existe ou non un repechage et impacter la moyenEMR
				MoyenneAdjustment moyenneAdjustment = adjustmentService.getByAnneePeriodeMatriculeAndMatiereAndStatut(
						me.getAnnee().getId(), me.getPeriode().getId(), me.getEleve().getMatricule(),
						hasMatiere.getId(), Constants.VALID);
				String isAdjustment = Constants.NON;
				if (moyenneAdjustment.getId() == null) {
					moyenneEMR = sommeEMR / (diviserEMR == 0.0 ? 1.0 : diviserEMR);
					moyenneEMRIntermediaire = sommeEMRIntermediaire / (diviserEMR == 0.0 ? 1.0 : diviserEMR);
				} else {
					isAdjustment = Constants.OUI;
					moyenneEMR = moyenneAdjustment.getMoyenne();
				}
				// Pour le calcul spécifique des moyennes religieuses
				moyennesMatieresReligion.add(CommonUtils.roundDouble(moyenneEMR, 2));
				calculExcpReligionFlat = true;
//				// pour eviter de partager le meme objet avec les autres eleves
				EcoleHasMatiere ehm_ = new EcoleHasMatiere();
				ehm_.setId(ehm.getId());
				ehm_.setPec(ehm.getPec());
				ehm_.setCoef(ehm.getCoef());
				ehm_.setMatiereParent(ehm.getMatiereParent());
				ehm_.setCode(ehm.getCode());
				ehm_.setLibelle(ehm.getLibelle());
				ehm_.setCategorie(ehm.getCategorie());
				ehm_.setMoyenne(CommonUtils.roundDouble(moyenneEMR, 2));
				ehm_.setAppreciation(CommonUtils.appreciation(moyenneEMR));
				ehm_.setMoyenneIntermediaire(CommonUtils.roundDouble(moyenneEMRIntermediaire, 2));
				ehm_.setBonus(ehm.getBonus());
				ehm_.setEcole(ehm.getEcole());
				ehm_.setParentMatiereLibelle(ehm.getParentMatiereLibelle());
				ehm_.setNumOrdre(ehm.getNumOrdre());
				ehm_.setMatiere(ehm.getMatiere());
				ehm_.setIsAdjustment(isAdjustment);
				me.getNotesMatiereMap().put(ehm_, moyenneEMRList);
			}

			if (calculExcpReligionFlat) {
//				System.out.println(g.toJson(moyennesMatieresReligion));
				Double moyReli = CommonUtils
						.roundDouble(moyennesMatieresReligion.stream().mapToDouble(a -> a).average().orElse(0), 2);
//				System.out
//						.println(String.format("Matricule [%s] Moyenne Religion = %s ", me.getEleve().getMatricule(),
//								moyReli));
				me.setMoyReli(moyReli);
				me.setAppreciationReli(CommonUtils.appreciation(moyReli));
			}
//			me.setMoyenne(calculMoyenneGeneralWithCoef(moyenneList));
		}
//		logger.info("++++> "+g.toJson(moyEleve));
		// Déterminer le rang de la matière enveloppe Français
		if (moyennesFrExcpt.size() > 0) {
			moyennesFrExcpt = moyennesFrExcpt.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

			for (MoyenneEleveDto m : moyEleve) {
				if (m.getMoyFr() != null) {
					m.setRangFr(moyennesFrExcpt.indexOf(m.getMoyFr())+1);
				} else {
					m.setRangFr(moyennesFrExcpt.size());
				}
			}
//			moyEleve.set
		}
		return moyEleve;
	}

	List<MoyenneEleveDto> calculMoyenneMatiereWithoutEMR(List<MoyenneEleveDto> moyEleve) {
		logger.info("---> Calcul des moyennes par matiere");
		Double moyenne;
		List<Double> noteList;

		Double diviser;
		Double somme;

//		Gson g = new Gson();

		for (MoyenneEleveDto me : moyEleve) {
			for (Map.Entry<EcoleHasMatiere, List<Notes>> entry : me.getNotesMatiereMap().entrySet()) {
				moyenne = 0.0;
				noteList = new ArrayList<Double>();
				List<Double> noteTestLourdList = new ArrayList<Double>();
				Double diviserTestLourd = 0.0;
				// System.out.println(entry.getKey().getLibelle());
//				System.out.println("Taille notes "+entry.getValue().size());
				diviser = 0.0;
				somme = 0.0;
				for (Notes note : entry.getValue()) {
// On vérifie que l'evaluation et la note sont prises en compte dans le calcul de moyenne
					if (note.getEvaluation().getPec() == Constants.PEC_1 && note.getPec() != null
							&& note.getPec() == Constants.PEC_1) {
						if (note.getEvaluation().getType() != null && note.getEvaluation().getType().getCode() != null
								&& note.getEvaluation().getType().getCode().equals(Constants.CODE_TEST_LOURD)) {
							noteTestLourdList.add(note.getNote());
							diviserTestLourd = diviserTestLourd + (Double.parseDouble(note.getEvaluation().getNoteSur())
									/ Double.parseDouble(Constants.DEFAULT_NOTE_SUR));
							entry.getKey().setTestLourdNoteSur(Integer.parseInt(note.getEvaluation().getNoteSur()));
//						System.out.println("TEST LOURD DETECTE note :: "+note.getNote());
						} else {
//					if (note.getEvaluation().getPec() == 1 && note.getPec() != null && note.getPec() == 1) {
							noteList.add(note.getNote());
//						System.out.println(String.format("PEC value >>> %s", note.getEvaluation().getPec()));
							diviser = diviser + (Double.parseDouble(note.getEvaluation().getNoteSur())
									/ Double.parseDouble(Constants.DEFAULT_NOTE_SUR));
						}
					}
				}

				for (Double note : noteList) {
					somme += note;
				}

				moyenne = somme / (diviser.equals(Double.parseDouble("0")) ? Double.parseDouble("1") : diviser);
				logger.info("Moyenne = " + somme + " / " + diviser + " = " + CommonUtils.roundDouble(moyenne, 2));

				if (noteTestLourdList.size() > 0) {
					Double moyenneTstLourd = 0.0;
					Double sommeTstLourd = 0.0;
					for (Double noteTestLrd : noteTestLourdList) {
						sommeTstLourd = sommeTstLourd + noteTestLrd;
					}
					moyenneTstLourd = sommeTstLourd / diviserTestLourd;
					entry.getKey().setTestLourdNote(sommeTstLourd);
					moyenne = (moyenne + moyenneTstLourd * 2) / 3;
				}

				entry.getKey().setMoyenne(CommonUtils.roundDouble(moyenne, 2));
				entry.getKey().setAppreciation(CommonUtils.appreciation(moyenne));

			}

//			me.setMoyenne(calculMoyenneGeneralWithCoef(moyenneList));
		}
//		logger.info("++++> "+g.toJson(moyEleve));
		return moyEleve;
	}

	Double calculMoyenneGeneralWithCoef(Map<Double, String> moyennes) {
		logger.info("---> Calcul de la moyenne generale avec les coeficients");
		Double moyPond = 0.0;
		Double coefTot = 0.0;
		Double moyGenPond = 0.0;
		for (Map.Entry<Double, String> entry : moyennes.entrySet()) {
			logger.info(String.format("moy %s --- coef %s", entry.getKey(), entry.getValue()));
			moyPond += entry.getKey();
			coefTot += Double.parseDouble(entry.getValue());
		}
		moyGenPond = moyPond / coefTot;
		logger.info(String.format("Moyenne Generale = %s / %s => %s", moyPond, coefTot, moyPond / coefTot));
		return moyGenPond;
	}

	/**
	 * Calcul de moyenne generale de l eleve et calcul de rang
	 *
	 * @param moyEleve
	 */
	void calculMoyenneGeneralEleve(List<MoyenneEleveDto> moyEleve) {
		logger.info("---> Calcul de moyenne generale de l eleve et calcul de rang");
		try {
			Double moy = 0.0;
			Double moyPond = 0.0;
			Double moyTotPond = 0.0;
			Double coef = 0.0;
			Double coefTot = 0.0;
			Double moyGenPond = 0.0;
			BigDecimal moyBD;
//			Map<Double, MoyenneEleveDto> classementMap = new HashMap<Double, MoyenneEleveDto>();
			List<MoyenneEleveDto> classement = new ArrayList<MoyenneEleveDto>();
//		Gson g = new Gson();
			// Calcul des moyennes generales par eleves
			for (MoyenneEleveDto eleve : moyEleve) {
//				System.out.println(eleve.getEleve().getMatricule() + " " + eleve.getEleve().getNom());
				ClasseElevePeriode cep = classeElevePeriodeService.findByClasseAndEleveAndAnneeAndPeriode(
						eleve.getClasse().getId(), eleve.getEleve().getId(), eleve.getAnnee().getId(),
						eleve.getPeriode().getId());
				if (cep == null)
					eleve.setIsClassed(Constants.OUI);
				else
					eleve.setIsClassed(cep.getIsClassed());

				// Absence des eleves
				AbsenceEleve abs = absenceService.getByAnneeAndEleveAndPeriode(eleve.getAnnee().getId(),
						eleve.getEleve().getId(), eleve.getPeriode().getId());
				if (abs == null) {
					eleve.setAbsJust(0);
					eleve.setAbsNonJust(0);
				} else {
					eleve.setAbsJust(abs.getAbsJustifiee());
					eleve.setAbsNonJust(abs.getAbsNonJustifiee());
				}

				for (EcoleHasMatiere matiere : eleve.getNotesMatiereMap().keySet()) {
//			logger.info(g.toJson(matiere));
//					System.out.println(matiere.getLibelle());
					// Vérifier si la note doit être prise en compte et si l'élève est classé dans
					// la matière concernée ou même pour la période
//				logger.info(matiere + "+++");
					if (matiere.getEleveMatiereIsClassed() == null) {
						throw new RuntimeException(String.format(
								"Veuillez vérifier que le coeficient de la matiere \" %s \" est défini pour la branche \" %s \"",
								matiere.getLibelle(), eleve.getClasse().getBranche().getLibelle()));
					}
					if (matiere.getPec() == 1 && !matiere.getEleveMatiereIsClassed().equals(Constants.NON)
							&& !eleve.getIsClassed().equals(Constants.NON)) {
						moy = matiere.getMoyenne();
						coef = Double.parseDouble(matiere.getCoef() == null ? "1" : matiere.getCoef());
						if (matiere.getCoef() == null) {
							matiere.setCoef("1");
							logger.warning(
									String.format("Veuillez définir un coefficient pour la matiere %s de la branche %s",
											matiere.getLibelle(), eleve.getClasse().getBranche().getLibelle()));
						}
						moyPond = (moy * coef);
						moyTotPond += moyPond;
						coefTot += Double.parseDouble(matiere.getCoef() == null ? "1" : matiere.getCoef());
						logger.info(String.format("moy %s --- coef %s ---- moy pondere --> %s", moy, coef, moyPond));
					} else if (matiere.getBonus() != null && matiere.getBonus() == 1
							&& !matiere.getEleveMatiereIsClassed().equals("N") && !eleve.getIsClassed().equals("N")) {
						moy = matiere.getMoyenne();
//					logger.info(matiere + "+++ is matiere bonus");
						coef = Double.parseDouble(matiere.getCoef() == null ? "1" : matiere.getCoef());
						if (matiere.getCoef() == null) {
							matiere.setCoef("1");
							logger.warning(
									String.format("Veuillez définir un coefficient pour la matiere %s de la branche %s",
											matiere.getLibelle(), eleve.getClasse().getBranche().getLibelle()));
						}
						if (moy > 10)
							moyPond = moy - 10;
						else
							moyPond = 0.0;
						matiere.setMoyenne(CommonUtils.roundDouble(moyPond, 2));
						moyTotPond += moyPond;
//					coefTot += Double.parseDouble(matiere.getCoef() == null ? "1" : matiere.getCoef());
					} else {
						moy = matiere.getMoyenne();
						logger.info("+++ eleve non classe dans cette matiere :" + matiere.getLibelle());
						coef = Double.parseDouble(matiere.getCoef() == null ? "1" : matiere.getCoef());
						if (matiere.getCoef() == null) {
							matiere.setCoef("1");
						}
						if (matiere.getBonus() != null && matiere.getBonus() == 1) {
							if (moy > 10)
								moyPond = moy - 10;
							else
								moyPond = 0.0;
						} else
							moyPond = (moy * coef);
						matiere.setMoyenne(CommonUtils.roundDouble(moyPond, 2));
					}
				}
				if (coefTot == 0.0)
					coefTot = 1.0;

				logger.info(String.format("moy Tot pondere   %s ---- coefTot %s  ", moyTotPond, coefTot));
//				System.out.println(String.format("moy Tot pondere   %s ---- coefTot %s  ", moyTotPond, coefTot));
				moyGenPond = moyTotPond / coefTot;
				moyBD = new BigDecimal(moyGenPond).setScale(2, RoundingMode.HALF_UP);
				eleve.setMoyenne(moyBD.doubleValue());
				logger.info("Moyenne Generale :" + eleve.getMoyenne());
//				System.out.println("Moyenne Generale :" + eleve.getMoyenne());
				eleve.setAppreciation(CommonUtils.appreciation(moyGenPond));
//				classementMap.put(moyGenPond, eleve);
				if (eleve.getIsClassed() != null && eleve.getIsClassed().equals(Constants.OUI))
					classement.add(eleve);
				moy = 0.0;
				moyPond = 0.0;
				moyTotPond = 0.0;
				coef = 0.0;
				coefTot = 0.0;
				moyGenPond = 0.0;
			}

			// Classement des eleves par moyenne
			classementElevePeriode(classement);
		} catch (RuntimeException r) {
			r.printStackTrace();
			throw r;
		}
	}

	void classementEleveParMatiere(List<MoyenneEleveDto> moyEleve, Long brancheId, Long ecoleId) {
		logger.info("---> Classement des eleves par matiere");
		List<ClasseMatiere> classeMatList = ClasseMatiere.find("branche.id = ?1 and ecole.id =?2", brancheId, ecoleId)
				.list();
		Map<Double, MoyenneEleveDto> mapt;
		Gson g = new Gson();
		int i = 0;
		for (ClasseMatiere cm : classeMatList) {
			logger.info("---> " + cm.getMatiere().getId() + " " + cm.getMatiere().getLibelle());
			mapt = new HashMap<Double, MoyenneEleveDto>();
			for (MoyenneEleveDto me : moyEleve) {
				logger.info("eleve - " + me.getEleve().getNom());
				// logger.info(".getNotesMatiereMap() - " + me.getNotesMatiereMap());
				if (me.getNotesMatiereMap() != null) {
					for (Map.Entry<EcoleHasMatiere, List<Notes>> map : me.getNotesMatiereMap().entrySet()) {
						if (map.getKey().getId().equals(cm.getMatiere().getId())) {
							mapt.put(map.getKey().getMoyenne(), me);

							logger.info(String.format("--- %s - %s - %s - %s - %s ---", me.getClasse().getId(),
									cm.getMatiere().getId(), me.getEleve().getId(), me.getAnnee().getId(),
									me.getPeriode().getId()));

							ClasseEleveMatiere cem = classeEleveMatiereService
									.findByClasseAndMatiereAndEleveAndAnneeAndPeriode(me.getClasse().getId(),
											cm.getMatiere().getId(), me.getEleve().getId(), me.getAnnee().getId(),
											me.getPeriode().getId());
							if (cem != null && cem.getIsClassed().equals(Constants.NON)) {
								logger.info("---> cem = " + cem.getIsClassed());
								me.setMoyenneMatiereToSort(Constants.MOYENNE_ELEVE_NON_CLASSE_MATIERE);
								map.getKey().setEleveMatiereIsClassed(Constants.NON);
							} else {
								me.setMoyenneMatiereToSort(map.getKey().getMoyenne());
								map.getKey().setEleveMatiereIsClassed(Constants.OUI);
								logger.info("---> cem is null ");
							}

							logger.info("Matiere : " + map.getKey().getLibelle() + " " + map.getKey().getMoyenne() + " "
									+ map.getKey().getEleveMatiereIsClassed());
//							logger.info(g.toJson(map.getKey()));

							break;
						}
					}

				}
			}
// Tri de la liste de eleve par rapport à la moyenne
			Collections.sort(moyEleve, new Comparator<MoyenneEleveDto>() {
				@Override
				public int compare(MoyenneEleveDto u1, MoyenneEleveDto u2) {
//					System.out.println("u1 : " + u1.getMoyenneMatiereToSort());
//					System.out.println("u2 : " + u2.getMoyenneMatiereToSort());
					if (u1.getMoyenneMatiereToSort() == null && u2.getMoyenneMatiereToSort() != null) {
//						System.out.println(" ------ -1");
						return -1;
					} else if (u1.getMoyenneMatiereToSort() != null && u2.getMoyenneMatiereToSort() == null) {
						return 1;
					} else if (u1.getMoyenneMatiereToSort() == null && u2.getMoyenneMatiereToSort() == null) {
						return 0;
					} else {
						return u2.getMoyenneMatiereToSort().compareTo(u1.getMoyenneMatiereToSort());
					}
				}
			});
// variable pour la gestion des rang et prise encompte des exécos
			i = 0;
			int j = 0;
			Double moyennePrecedente = (double) -1;
			for (MoyenneEleveDto me : moyEleve) {

				for (Map.Entry<EcoleHasMatiere, List<Notes>> map : me.getNotesMatiereMap().entrySet()) {
					if (map.getKey().getId().equals(cm.getMatiere().getId())) {
						logger.info("matiere id - " + map.getKey().getId() + " --- " + cm.getCoef());
						// maj du rang par matiere et du coef de la matiere par rapport a la classe
						i++;
						if (moyennePrecedente.equals(map.getKey().getMoyenne())) {
							map.getKey().setRang(String.valueOf(j));
						} else {
							j = i;
							map.getKey().setRang(String.valueOf(j));
						}
						map.getKey().setCoef(cm.getCoef() != null ? cm.getCoef() : "1");
						moyennePrecedente = map.getKey().getMoyenne();
						logger.info("Matiere : " + map.getKey().getLibelle() + " " + map.getKey().getMoyenne()
								+ " Rang " + map.getKey().getRang() + " coef :" + map.getKey().getCoef());
						break;
					}
				}
			}
		}

	}

	void classementAnnuelEleveParMatiere(List<MoyenneEleveDto> moyEleve, Long brancheId, Long ecoleId, Periode periode,
			List<ClasseMatiere> cmList) {
		logger.info("---> Classement des eleves par matiere");
//		Gson g = new Gson();
//		int i = 0;
		// recuperer et classer les moyennes pour les classements annuels
		List<Double> classeurAnnuelList = new ArrayList<Double>();
		List<Double> classeurAnnuelSuperFrList = new ArrayList<Double>();
		// recuperer et classer les moyennes pour les classements annuels au niveau des
		// matieres
		Map<Long, List<Double>> classeurAnnuelMatiereMap = new HashMap<Long, List<Double>>();
//---- WARNING mettre un try catch
		Periode per = new Periode();
		try {
			per = Periode.find("periodicite.id=?1 and final = 'O'", periode.getPeriodicite().getId()).singleResult();
//			System.out.println("PERIODICITE :: "+periode.getPeriodicite().getId()+" "+per.getLibelle());
		} catch (RuntimeException e) {
			logger.warning(e.getMessage());
		}
		List<Periode> periodes = Periode.find("niveau <= ?1 and periodicite.id=?2 order by niveau", per.getNiveau(),
				periode.getPeriodicite().getId()).list();

		for (MoyenneEleveDto me : moyEleve) {
			// recup la moyenne de la matiere dans les details bulletins (par classe,
			// matiere et periode)
//			System.out.println("*********> " + me.getEleve().getMatricule());
			// recuperer la liste des bulletins de l'élève
			List<Bulletin> bulletinsElevesList = bulletinService.getBulletinsEleveByAnnee(me.getAnnee().getId(),
					me.getEleve().getMatricule(), me.getClasse().getId());
			Ecole ecole = ecoleService.findById(ecoleId);
//			Integer rangAn = 0;
			Double coef;
			Double moyAn = 0.0;
			Double moyAnFr = null;
			InfoCalculMoyennePojo infoCalcul = new InfoCalculMoyennePojo();
			List<Double> moyAnInterne = new ArrayList<>();
			List<Double> moyAnIEPP = new ArrayList<>();
			List<Double> moyAnPassage = new ArrayList<>();
			Periode finalPeriode = periodes.stream().filter(p -> (p.getIsfinal() != null && p.getIsfinal().equals("O")))
					.findAny().orElse(new Periode());
//			System.out.println("FINAL PERIODE "+finalPeriode.getCoef()+" "+finalPeriode.getLibelle());
			if (ecole.getNiveauEnseignement().getId() == Constants.NIVEAU_ENSEIGNEMENT_PRIMAIRE) {
				logger.info("ENS PRIMAIRE");
				handleMoyenneAnnuelleEnsPrimaire(periodes,
						Double.parseDouble(per.getCoef().equals("") ? "1" : finalPeriode.getCoef()), me,
						bulletinsElevesList, moyAn, moyAnInterne, moyAnIEPP, moyAnPassage);
			} else {
				logger.info("ENS SECONDAIRE ET AUTRES");
				infoCalcul = handleMoyenneAnnuelleEnsSecondaire(periodes,
						Double.parseDouble(per.getCoef().equals("") ? "1" : finalPeriode.getCoef()), me,
						bulletinsElevesList, moyAnFr);
				if (infoCalcul.getMoyenneAnSuperFrancais() != null)
					classeurAnnuelSuperFrList.add(infoCalcul.getMoyenneAnSuperFrancais());
//				System.out.println("**********>>>>>>Moy an fr ::: " + infoCalcul.getMoyenneAnSuperFrancais());
			}
			logger.info(String.format("Eleve %s > Moyenne Annuelle = %s - Interne = %s - IEPP = %s - passage = %s ",
					me.getEleve().getMatricule(), infoCalcul.getMoyenneAnnuelle(),
					moyAnInterne.stream().mapToDouble(Double::doubleValue).average().orElse(0.0),
					moyAnIEPP.stream().mapToDouble(Double::doubleValue).average().orElse(0.0),
					moyAnPassage.stream().mapToDouble(Double::doubleValue).average().orElse(0.0)));

			me.setMoyenneAnnuelle(infoCalcul.getMoyenneAnnuelle());
			if (infoCalcul.getMoyenneAnnuelle() != null) {
				me.setApprAnnuelle(CommonUtils.appreciation(infoCalcul.getMoyenneAnnuelle()));
			}

			me.setMoyAnFr(infoCalcul.getMoyenneAnSuperFrancais());
			if (infoCalcul.getMoyenneAnSuperFrancais() != null) {
//				System.out.println("moyenneAnSuperFrencais ::: " + infoCalcul.getMoyenneAnSuperFrancais());
				me.setAppreciationAnFr(CommonUtils.appreciation(infoCalcul.getMoyenneAnSuperFrancais()));
			}
			me.setMoyenneIEPP(moyAnIEPP.stream().mapToDouble(Double::doubleValue).average().orElse(0.0));
			me.setMoyenneInterne(moyAnInterne.stream().mapToDouble(Double::doubleValue).average().orElse(0.0));
			me.setMoyennePassage(moyAnPassage.stream().mapToDouble(Double::doubleValue).average().orElse(0.0));

			classeurAnnuelList.add(infoCalcul.getMoyenneAnnuelle());
			// calcul moyenne annuelle pour chaque eleve et dans chaque matiere
			for (ClasseMatiere cml : cmList) {
				coef = 0.0;
				Double moyMatiereAnnuelle = 0.0;
//Calcul de la moyenne annuelle de la matiere en tenant compte de chaque periode où l'élève a été classé
				List<DetailBulletin> detailsBulletinsElevesList = detailBulletinService
						.getDetailBulletinsEleveByAnneeAndClasseAndMatiere(me.getAnnee().getId(),
								me.getClasse().getId(), me.getEleve().getMatricule(), cml.getMatiere().getId());
				for (DetailBulletin dtb : detailsBulletinsElevesList) {
					for (Periode p : periodes) {
						if (dtb.getBulletin().getPeriodeId().equals(p.getId())) {
							if (dtb.getIsRanked() != null && dtb.getIsRanked().equals(Constants.OUI)) {
								if (p.getIsfinal() != null && p.getIsfinal().equals("O")) {
									for (Map.Entry<EcoleHasMatiere, List<Notes>> entry : me.getNotesMatiereMap()
											.entrySet()) {
										if (entry.getKey().getId().equals(cml.getMatiere().getId())) {
											moyMatiereAnnuelle = moyMatiereAnnuelle
													+ entry.getKey().getMoyenne() * Double.parseDouble(p.getCoef());

//										System.out.println("getMoyenne : " + entry.getKey().getMoyenne());
										}
									}
								} else {
									moyMatiereAnnuelle = moyMatiereAnnuelle
											+ dtb.getMoyenne() * Double.parseDouble(p.getCoef());
								}
								coef = coef + Double.parseDouble(p.getCoef());
//							System.out.println("moy : " + moyMatiereAnnuelle + " coef : " + coef);
							}
							break;
						}
					}
				}
				// Le if evite le risque de division par 0
				if (detailsBulletinsElevesList != null && detailsBulletinsElevesList.size() != 0) {
//					System.out.println("___>" + (moyMatiereAnnuelle / coef));
					moyMatiereAnnuelle = CommonUtils.roundDouble(moyMatiereAnnuelle / (coef == 0.0 ? 1.0 : coef), 2);
					for (Map.Entry<EcoleHasMatiere, List<Notes>> entry : me.getNotesMatiereMap().entrySet()) {
						logger.info("---------> " + entry.getKey().getCode() + "  " + entry.getKey().getLibelle());
//						System.out.println("---------> " + entry.getKey().getId() + "  " + entry.getKey().getLibelle());
						if (entry.getKey().getId().equals(cml.getMatiere().getId())) {
							logger.info("-----> " + entry.getKey().getLibelle() + " ok");
							entry.getKey().setMoyenneAnnuelle(moyMatiereAnnuelle);

							if (classeurAnnuelMatiereMap.containsKey(cml.getMatiere().getId())) {
								classeurAnnuelMatiereMap.get(cml.getMatiere().getId()).add(moyMatiereAnnuelle);
							} else {
								List<Double> classeurMatiereTemp = new ArrayList<Double>();
								classeurMatiereTemp.add(moyMatiereAnnuelle);
								classeurAnnuelMatiereMap.put(cml.getMatiere().getId(), classeurMatiereTemp);
							}
						}
					}
				}
			}
		}
		// Tri descendant de chaque liste de moyenne des matieres
		for (Map.Entry<Long, List<Double>> entry : classeurAnnuelMatiereMap.entrySet()) {
			Collections.sort(entry.getValue());
			Collections.reverse(entry.getValue());
		}
		// Tri descendant de chaque liste de moyenne annuelle Super francais
		if (classeurAnnuelSuperFrList.size() > 0) {
			classeurAnnuelSuperFrList = classeurAnnuelSuperFrList.stream().sorted(Comparator.reverseOrder())
					.collect(Collectors.toList());
		}
		// classement annuel des eleves par matiere
		for (MoyenneEleveDto me : moyEleve) {
//			System.out.println("+ Eleve: " + me.getEleve().getNom() + " Matricule : " + me.getEleve().getMatricule());
			for (EcoleHasMatiere matEleve : me.getNotesMatiereMap().keySet()) {
//				System.out.println("++ Matiere: " + matEleve.getLibelle());
//				System.out.println("_____>"+matEleve.getId());
				if (classeurAnnuelMatiereMap.containsKey(matEleve.getId())) {
//					System.out.println("mat id " + matEleve.getId() + "0000 " + matEleve.getMoyenneAnnuelle()
//							+ matEleve.getMatiere().getLibelle());
					// System.out.println(
//							"+++ Moy ann :" + matEleve.getMoyenneAnnuelle() + " Rang :" + matEleve.getRangAnnuel());
					matEleve.setRangAnnuel(String.valueOf(getRangByValue(classeurAnnuelMatiereMap.get(matEleve.getId()),
							matEleve.getMoyenneAnnuelle())));
				}
			}
			if (classeurAnnuelSuperFrList.size() > 0) {
				me.setRangAnFr(classeurAnnuelSuperFrList.indexOf(me.getMoyAnFr()) + 1);
			}
		}
//System.out.println("classeurAnuelleList size "+classeurAnnuelList.size()+" NULL INDEX = "+classeurAnnuelList.indexOf(null));
		Collections.sort(classeurAnnuelList);
		Collections.reverse(classeurAnnuelList);

//		Classsement Annuel des élèves
		for (MoyenneEleveDto eleveObj : moyEleve) {
			// System.out.println("rang moy an " + eleveObj.getMoyenne() + " = "
//					+ getIndexByValue(classeurAnnuelList, eleveObj.getMoyenneAnnuelle()));
//			System.out.println("0000 " + eleveObj.getMoyenneAnnuelle());
			eleveObj.setRangAnnuel(String.valueOf(getRangByValue(classeurAnnuelList, eleveObj.getMoyenneAnnuelle())));
		}

	}

	public InfoCalculMoyennePojo handleMoyenneAnnuelleEnsSecondaire(List<Periode> periodes, Double coefFinalPeriode,
			MoyenneEleveDto me, List<Bulletin> bulletinsElevesList, Double moyenneAnSuperFrencais) {
		Double moyAn = 0.0;
		Double coef = 0.0;
		Double coefAnFr = 0.0;

		for (Bulletin bul : bulletinsElevesList) {
//			System.out.println("--------------------- matricule : " + bul.getMatricule());
			for (Periode p : periodes) {
				if (bul.getPeriodeId().equals(p.getId())) {
					if (bul.getIsClassed() != null && bul.getIsClassed().equals(Constants.OUI)) {
						if (p.getIsfinal() == null) {
							moyAn = moyAn + bul.getMoyGeneral() * Double.parseDouble(p.getCoef());
							coef = coef + Double.parseDouble(p.getCoef());
//							System.out.println("############################################1");
//							System.out.println(
//									String.format("Moy periode %s : %s et coef : %s et moy total : %s et coef tot : %s",
//											p.getId(), bul.getMoyGeneral(), p.getCoef(), moyAn, coef));
							if (bul.getMoyFr() != null) {
//								System.out.println("############################################2");
								if (coefAnFr == 0.0) {
//									System.out.println("############################################3");
									moyenneAnSuperFrencais = 0.0;
								}
								moyenneAnSuperFrencais = moyenneAnSuperFrencais
										+ bul.getMoyFr() * Double.parseDouble(p.getCoef());
								coefAnFr = coefAnFr + Double.parseDouble(p.getCoef());
							}
						}
					}
					break;
				}
			}
//			System.out.println("---------------------");
		}
		// Ajout des moyennes de la dernière période

		if (me.getIsClassed() != null && me.getIsClassed().equals(Constants.OUI)) {
			moyAn = moyAn + me.getMoyenne() * coefFinalPeriode;
			coef = coef + coefFinalPeriode;

//		System.out.println(String.format("Moy periode final: moy %s et coef : %s et moy total : %s et coef tot : %s", me.getMoyenne(),coefFinalPeriode, moyAn, coef));

			moyAn = CommonUtils.roundDouble(moyAn / (coef == 0.0 ? 1.0 : coef), 2);
			// Calcul moyenne annuelle super français
//		if (coefAnFr > 0.0) {
			if (me.getMoyFr() != null) {
				if (coefAnFr == 0.0) {
					moyenneAnSuperFrencais = 0.0;
				}
//			System.out.println("####################################***4" + moyenneAnSuperFrencais);
				moyenneAnSuperFrencais = moyenneAnSuperFrencais + me.getMoyFr() * coefFinalPeriode;
				coefAnFr = coefAnFr + coefFinalPeriode;
				moyenneAnSuperFrencais = CommonUtils
						.roundDouble(moyenneAnSuperFrencais / (coefAnFr == 0.0 ? 1.0 : coefAnFr), 2);
			}
		} else {
			moyAn = CommonUtils.roundDouble(moyAn / (coef == 0.0 ? 1.0 : coef), 2);
		}
//		System.out.println("Moy an Super Francais ::: " + moyenneAnSuperFrencais);
//		}
//		System.out.println("Moy an ::: " + moyAn);
//		System.out.println("Coef ::: " + coef);
//		System.out.println("Moy O ::: " + me.getMoyenne());
		InfoCalculMoyennePojo infoCalcul = new InfoCalculMoyennePojo();
		infoCalcul.setMoyenneAnnuelle(moyAn);
		infoCalcul.setCoeficient(coef);
		infoCalcul.setCoeficientAnSuperFrançais(coefAnFr);
		infoCalcul.setMoyenneAnSuperFrancais(moyenneAnSuperFrencais);
		return infoCalcul;
	}

	public void handleMoyenneAnnuelleEnsPrimaire(List<Periode> periodes, Double coefFinalPeriode, MoyenneEleveDto me,
			List<Bulletin> bulletinsElevesList, Double moyAn, List<Double> moyAnInterne, List<Double> moyAnIEPP,
			List<Double> moyAnPassage) {
		Double coef = 0.0;
		for (Bulletin bul : bulletinsElevesList) {
			for (Periode p : periodes) {
				if (bul.getPeriodeId().equals(p.getId())) {
					if (bul.getIsClassed() != null && bul.getIsClassed().equals(Constants.OUI)) {
						if (p.getIsfinal() == null) {
							moyAn = moyAn + bul.getMoyGeneral() * Double.parseDouble(p.getCoef());
							coef = coef + Double.parseDouble(p.getCoef());
							if (bul.getTypeEvaluation() == 7) {
								// composition de passage
								moyAnPassage.add(bul.getMoyGeneral());
							} else if (bul.getTypeEvaluation() == 15) {
								// composition interne
								moyAnInterne.add(bul.getMoyGeneral());
							} else if (bul.getTypeEvaluation() == 16) {
								// composition IEPP
								moyAnIEPP.add(bul.getMoyGeneral());
							}
						}
					}
					break;
				}
			}
		}
		if (me.getTypeEvaluation() == 7) {
			// composition de passage
			moyAnPassage.add(me.getMoyenne());
		} else if (me.getTypeEvaluation() == 15) {
			// composition interne
			moyAnInterne.add(me.getMoyenne());
		} else if (me.getTypeEvaluation() == 16) {
			// composition IEPP
			moyAnIEPP.add(me.getMoyenne());
		}
		moyAn = moyAn + me.getMoyenne() * coefFinalPeriode;
		coef = coef + coefFinalPeriode;

		moyAn = CommonUtils.roundDouble(moyAn / (coef == 0.0 ? 1.0 : coef), 2);
	}

	public Integer getRangByValue(List<Double> list, Double value) {
		int i = 1;
		if (list != null) {
			for (Double elmt : list) {
				// La conversion en String car remarque que l'égalité entre de Double arrondis
				// de même valeur ne se fait pas correctement
//				System.out.println("++++ ::: " + elmt + "value : " + value);
				if (value != null && elmt.toString().equals(value.toString())) {
					return i; // Retourne le rang si la valeur est trouvée
				}
				i++;
			}
		}
		return -1; // Retourne -1 si la valeur n'est pas trouvée dans le tableau
	}

	void classementElevePeriode(List<MoyenneEleveDto> eleves) {

		Collections.sort(eleves, new Comparator<MoyenneEleveDto>() {
			@Override
			public int compare(MoyenneEleveDto u1, MoyenneEleveDto u2) {
//				System.out.println("u1 :: " + u1.getEleve().getMatricule() + " u2 ::: " + u2.getEleve().getMatricule());
//				System.out.println(u1.getMoyenne());
//				System.out.println(u2.getMoyenne());
				if (u1.getMoyenne() == null || u2.getMoyenne() == null)
					return 0;
				return u2.getMoyenne().compareTo(u1.getMoyenne());
			}
		});

		int i = 0;
		Double moyennePrecedente = (double) -1;
		int rang = 0;
		int j = 0;
		for (MoyenneEleveDto eleve : eleves) {
			i++;
			if (eleve.getMoyenne().equals(moyennePrecedente)) {
				rang = j;
				eleve.setRang(String.valueOf(rang));
			} else {
				eleve.setRang(String.valueOf(i));
				j = i;
			}
			moyennePrecedente = eleve.getMoyenne();

		}

	}

	public Boolean haveNotesByEleveAndClasseAndAnnee(String matricule, Long classeId, Long anneeId) {

		Long noteCounter = 0L;
		try {
			noteCounter = Notes.find(
					"classeEleve.inscription.eleve.matricule = ?1 and classeEleve.classe.id = ?2 and evaluation.annee.id =?3",
					matricule, classeId, anneeId).count();
		} catch (RuntimeException e) {
			logger.warning("Erreur ::: " + e.getMessage());
		}
		return noteCounter == 0 ? false : true;
	}

	public List<Notes> getListNotesByEleveAndClasseAndAnneeAndPeriode(String matricule, Long classeId, Long anneeId,
			Long periodeId) {

		List<Notes> notesByEleve = new ArrayList<Notes>();
		try {
			logger.info(matricule + " " + classeId + " " + anneeId + " " + periodeId);
			notesByEleve = Notes.find(
					"classeEleve.inscription.eleve.matricule = ?1 and classeEleve.classe.id = ?2 and evaluation.annee.id =?3 and evaluation.periode.id = ?4 and pec = 1",
					matricule, classeId, anneeId, periodeId).list();
		} catch (RuntimeException e) {
			logger.warning("Erreur ::: " + e.getMessage());
		}
		return notesByEleve;
	}

//	void buildEleveNoteDto(Notes note, NotesEleveDto notesEleveDto) {
//		try {
//		System.out.println("buildEleveNoteDto");
//		if (notesEleveDto.getList().size() > 0) {
//			ListIterator<MatiereNotesEleveDto> iterator = notesEleveDto.getList().listIterator();
//			while(iterator.hasNext()) {
//				System.out.println("iiiii");
//				if (iterator.next().getMatiereId() == note.getEvaluation().getMatiereEcole().getId()) {
//					iterator.next().getNotes().add(String.format("%s/%s", note.getNote(), note.getEvaluation().getNoteSur()));
//				}else {
//					MatiereNotesEleveDto matiereNotesEleveDto = new MatiereNotesEleveDto();
//					matiereNotesEleveDto.setMatiereId(note.getEvaluation().getMatiereEcole().getId());
//					matiereNotesEleveDto.setMatiereLibelle(note.getEvaluation().getMatiereEcole().getLibelle());
//					matiereNotesEleveDto.getNotes()
//							.add(String.format("%s/%s", note.getNote(), note.getEvaluation().getNoteSur()));
//					notesEleveDto.getList().add(matiereNotesEleveDto);
//				}
//			}
//
//		} else {
//			System.out.println("NEW");
//			MatiereNotesEleveDto matiereNotesEleveDto = new MatiereNotesEleveDto();
//			matiereNotesEleveDto.setMatiereId(note.getEvaluation().getMatiereEcole().getId());
//			matiereNotesEleveDto.setMatiereLibelle(note.getEvaluation().getMatiereEcole().getLibelle());
//			matiereNotesEleveDto.getNotes()
//					.add(String.format("%s/%s", note.getNote(), note.getEvaluation().getNoteSur()));
//			notesEleveDto.getList().add(matiereNotesEleveDto);
//		}
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
	void buildEleveNoteDto(Notes note, NotesEleveDto notesEleveDto) {
		try {
//			System.out.println("buildEleveNoteDto");
			boolean found = false; // Drapeau pour vérifier si la matière existe déjà
			if (notesEleveDto.getList().size() > 0) {
				ListIterator<MatiereNotesEleveDto> iterator = notesEleveDto.getList().listIterator();
				while (iterator.hasNext()) {
					MatiereNotesEleveDto matiereNotesEleveDto = iterator.next();
					if (matiereNotesEleveDto.getMatiereId() == note.getEvaluation().getMatiereEcole().getId()) {
						// Si la matière est trouvée, ajouter la note et passer le drapeau à true
						matiereNotesEleveDto.getNotes()
								.add(String.format("%s/%s", note.getNote(), note.getEvaluation().getNoteSur()));
						found = true;
						break;
					}
				}
			}

			// Si la matière n'a pas été trouvée, on l'ajoute après la boucle
			if (!found) {
//				System.out.println("NEW");
				MatiereNotesEleveDto matiereNotesEleveDto = new MatiereNotesEleveDto();
				matiereNotesEleveDto.setMatiereId(note.getEvaluation().getMatiereEcole().getId());
				matiereNotesEleveDto.setMatiereLibelle(note.getEvaluation().getMatiereEcole().getLibelle());
				matiereNotesEleveDto.getNotes()
						.add(String.format("%s/%s", note.getNote(), note.getEvaluation().getNoteSur()));
				notesEleveDto.getList().add(matiereNotesEleveDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void builder(Notes note, NotesEleveDto notesEleveDto, MatiereNotesEleveDto dto) {

	}

	public NotesEleveDto getNotesEleveByPeriode(String matricule, Long classeId, Long anneeId, Long periodeId) {
		List<Notes> list = getListNotesByEleveAndClasseAndAnneeAndPeriode(matricule, classeId, anneeId, periodeId);
//		System.out.println(list.size());
		NotesEleveDto notesEleve = new NotesEleveDto();
		if (list != null && list.size() > 0) {
//			System.out.println(list.size());
			notesEleve.setMatricule(list.get(0).getClasseEleve().getInscription().getEleve().getMatricule());
			notesEleve.setNom(list.get(0).getClasseEleve().getInscription().getEleve().getNom());
			notesEleve.setPrenom(list.get(0).getClasseEleve().getInscription().getEleve().getPrenom());
			list.stream().forEachOrdered(n -> buildEleveNoteDto(n, notesEleve));
		}
		return notesEleve;
	}

	public List<Notes> getNotesByInscriptionHasClasse(Long inscriptionHasClasseId) {

		List<Notes> notesEleve = new ArrayList<Notes>();
		try {
			logger.info("Inscr_has_classe_id = " + inscriptionHasClasseId);
			notesEleve = Notes.find("classeEleve.id = ?1 and pec = 1", inscriptionHasClasseId).list();
		} catch (RuntimeException e) {
			logger.warning("Erreur ::: " + e.getMessage());
		}
		return notesEleve;
	}

	public List<Notes> getListNotesByEleveAndClasseAndAnneeAndPeriodeAndMatiere(String matricule, Long classeId,
			Long anneeId, Long periodeId, Long matiereId) {

		List<Notes> notesByEleve = new ArrayList<Notes>();
		try {
			logger.info(matricule + " " + classeId + " " + anneeId + " " + periodeId);
			notesByEleve = Notes.find(
					"classeEleve.inscription.eleve.matricule = ?1 and classeEleve.classe.id = ?2 and evaluation.annee.id =?3 and evaluation.periode.id = ?4 and evaluation.matiereEcole.id = ?5 and pec = 1",
					matricule, classeId, anneeId, periodeId, matiereId).list();
		} catch (RuntimeException e) {
			logger.warning("Erreur ::: " + e.getMessage());
		}
		return notesByEleve;
	}
}

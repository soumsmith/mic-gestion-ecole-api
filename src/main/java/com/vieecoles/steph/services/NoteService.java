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
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
import com.vieecoles.steph.dto.moyennes.EleveDto;
import com.vieecoles.steph.dto.moyennes.EleveMatiereDto;
import com.vieecoles.steph.dto.moyennes.MatiereNotes;
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
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

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
		//logger.info(gson.toJson(notes));
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
//		Gson gson = new Gson();
		for (Notes note : noteList) {
			if (note.getId() == 0 && note.getStatut() != null && note.getStatut().equals("M")) {
//	    			logger.info(gson.toJson(note));
				logger.info("--> Creation de note ...");
				create(note);

			} else if (note.getId() != 0 && note.getStatut() != null && note.getStatut().equals("M")) {
//				logger.info(gson.toJson(note));
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
			System.out.println(evaluation.getClasse().getId()+" "+evaluation.getAnnee().getId());
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

	public List<Notes> getNotesClasseWithPec_V2(String evalCode, Integer pec) {
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
			long start = System.nanoTime();
			Map<Eleve, List<Notes>> noteGroup = new HashMap<Eleve, List<Notes>>();
			Parameters params = Parameters.with("classeId", Long.parseLong(classeId))
					.and("anneeId", Long.parseLong(anneeId)).and("periodeId", Long.parseLong(periodeId))
					.and("pok", Constants.PEC_1);
			String criteria = "classe.id = :classeId and annee.id = :anneeId and periode.id = :periodeId and pec = :pok";
			List<Evaluation> evalList = evaluationService.search(criteria, params);
			
			long end = System.nanoTime();
			long duration = (end - start) / 1000000000;
			System.out.println("Temps d'exécution liste initialisation notes: " + duration + " secondes");
			
			List<Notes> noteList = new ArrayList<Notes>();
			List<Notes> notesTemp;
			List<MoyenneEleveDto> moyenneList = new ArrayList<MoyenneEleveDto>();
			Map<EcoleHasMatiere, List<Notes>> notesMatiereGroup;
			Classe classe;
			MoyenneEleveDto moyenneEleveDto;
			EvaluationPeriode evaluationPeriode = null;
//			Gson g = new Gson();
			// pour chaque évaluation avoir la liste des notes des élèves
//			System.out.println("ealist "+evalList);

			long startTime = System.nanoTime();
			collectNotesPec_v2(noteList, evalList, classeId, anneeId, periodeId);
//

			long endTime = System.nanoTime();
			long durationInSeconds = (endTime - startTime); // / 1000000000;
			System.out.println("Temps d'exécution NOte _iterateur process de collecte des notes avec pec à 1: "
					+ durationInSeconds + " secondes");
//		logger.info("note size " + noteList.size());
//		logger.info(gson.toJson(noteList));
			// Regroupement des notes par élève

			long startTime2 = System.nanoTime();
			for (Notes note : noteList) {
//				System.out.println(note.getEvaluation().getMatiereEcole().getLibelle()+" "+note.getNote());
//			logger.info("note.getClasseEleve().getInscription().getEleve()");
//			logger.info(gson.toJson(note.getClasseEleve().getInscription().getEleve()));
				if (noteGroup.containsKey(note.getClasseEleve().getInscription().getEleve())) {
//					logger.info("**** upd |||****>");
					noteGroup.get(note.getClasseEleve().getInscription().getEleve()).add(note);
//					System.out.println(">>>>>> udp"+note.getEvaluation().getMatiereEcole().getMatiere().getId());
//					System.out.println(note.getClasseEleve().getInscription().getEleve().getNom()+" "+note.getEvaluation().getMatiereEcole().getLibelle()+" "+ noteGroup.get(note.getClasseEleve().getInscription().getEleve()).size());
				} else {
//					logger.info("<****||| new ****");
					notesTemp = new ArrayList<Notes>();
					notesTemp.add(note);
					noteGroup.put(note.getClasseEleve().getInscription().getEleve(), notesTemp);
//					System.out.println(">>>>>> new"+note.getEvaluation().getMatiereEcole().getMatiere().getId());
				}
			}

			long endTime2 = System.nanoTime();
			long durationInSeconds2 = (endTime2 - startTime2); // / 1000000000;
			System.out.println(
					"Temps d'exécution Note Regroupement des notes par élève: " + durationInSeconds2 + " secondes");
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
			long startTime3 = System.nanoTime();
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
//					logger.info("note id --->" + note.getId());
					if (note.getId() != 0) {
						if (filter.contains(note.getEvaluation().getMatiereEcole().getId().toString())) {
//					if (notesMatiereGroup.containsKey(note.getEvaluation().getMatiere())) {
//							logger.info("**** upd ****>" + note.getEvaluation().getMatiereEcole().getCode());
//						logger.info(g.toJson(notesMatiereGroup));
//							logger.info("----------");
//							logger.info(note.getEvaluation().getMatiereEcole().getCode());
							notesMatiereGroup.get(note.getEvaluation().getMatiereEcole()).add(note);
//						System.out.println("----------------------------");
//						System.out.println(g.toJson(note.getEvaluation().getMatiere()));
//						logger.info(String.format("%s - %s - %s", entry.getKey().getMatricule(),note.getEvaluation().getMatiere(),note.getNote()));
//						System.out.println(String.format("%s - %s - %s", entry.getKey().getMatricule(),note.getEvaluation().getMatiereEcole(),note.getNote()));;
						} else {
//							logger.info("<*** new *****" + note.getEvaluation().getMatiereEcole().getCode());
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
//							logger.info(String.format("%s - %s - %s", entry.getKey().getMatricule(),
//									note.getEvaluation().getMatiereEcole().getCode(), note.getNote()));

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

			long endTime3 = System.nanoTime();
			long durationInSeconds3 = (endTime3 - startTime3); // / 1000000000;
			System.out
					.println("Temps d'exécution NOte build moyenneDto regroupement des notes par matiere et par eleve: "
							+ durationInSeconds3 + " secondes");
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

			startTime = System.nanoTime();
			classementEleveParMatiereV2(calculMoyenneMatiere(moyenneList), classe.getBranche().getId(),
					classe.getEcole().getId());
			calculMoyenneGeneralEleve(moyenneList);

			endTime = System.nanoTime();
			durationInSeconds = (endTime - startTime); // / 1000000000;
			System.out.println("Temps d'exécution NOte Calculs des moyennes et rang par matiere: " + durationInSeconds
					+ " secondes");

			// Vérifie si la période est la denière. si oui calcul des moyennes et rang
			// annuels
			Periode periodeCtrl = Periode.findById(Long.parseLong(periodeId));
			if (periodeCtrl != null) {
				if (periodeCtrl.getIsfinal() != null && periodeCtrl.getIsfinal().equals(Constants.OUI)) {
//					System.out.println("CALCUL MOYENNE ANNUELLE");
					try {
						List<ClasseMatiere> classeMatList = ClasseMatiere.find("branche.id = ?1 and ecole.id =?2",
								classe.getBranche().getId(), classe.getEcole().getId()).list();
						classementAnnuelEleveParMatiere_v2(moyenneList, classe.getBranche().getId(),
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
			optimized.setEleve(
					new PersonneDto(dto.getEleve().getId(), dto.getEleve().getMatricule(), dto.getEleve().getNom(),
							dto.getEleve().getPrenom(), dto.getEleve().getSexe(), dto.getEleve().getUrlPhoto()));
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
					note.getPersonnel().getNom(), note.getPersonnel().getPrenom(), note.getPersonnel().getSexe(),
					null));
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
//		logger.info(ev.getPec().toString());
		if (ev.getPec() != null && ev.getPec() == Constants.PEC_1) {
			listNotesByEvaluation = getNotesClasseWithPec(ev.getCode(), Constants.PEC_1);
			noteList.addAll(listNotesByEvaluation);
		}
	}

	public synchronized void collectNotesPec_v2(List<Notes> noteList, List<Evaluation> evalList, String classeId,
			String anneeId, String periodeId) {
		List<Notes> listNotesByClasse;
		Map<String, Map<Long, Notes>> notesByEleveAndEval = new HashMap<String, Map<Long, Notes>>();
		try {
			// liste des notes valides de la classe
			listNotesByClasse = Notes.find(
					"evaluation.classe.id = ?1 AND evaluation.annee.id = ?2 AND evaluation.periode.id = ?3 AND pec = ?4",
					Long.valueOf(classeId), Long.valueOf(anneeId), Long.valueOf(periodeId), Constants.PEC_1).list();
// Regroupement par  matricule (clé).
			// chaque matricule a comme valeur un map evaluation - notes
			notesByEleveAndEval = listNotesByClasse.stream().parallel()
					.collect(Collectors.groupingBy(
							note -> note.getClasseEleve().getInscription().getEleve().getMatricule(),
							Collectors.toMap(note -> note.getEvaluation().getId(), note -> note)));
		} catch (RuntimeException e) {
			e.printStackTrace();
			listNotesByClasse = new ArrayList<>();
		}
		List<Notes> noteListTemp = new ArrayList<>();

		List<ClasseEleve> classeEleves = classeEleveService.getByClasseAnnee(Long.valueOf(classeId),
				Long.valueOf(anneeId));

		for (ClasseEleve ce : classeEleves) {
			String matricule = ce.getInscription().getEleve().getMatricule();
			Map<Long, Notes> notesEval = notesByEleveAndEval.getOrDefault(matricule, Collections.emptyMap());

			for (Evaluation eval : evalList) {
				Notes note = notesEval.get(eval.getId());
				if (note != null) {
					noteListTemp.add(note);
				} else {
					Notes noteVide = new Notes();
					noteVide.setClasseEleve(ce);
					noteVide.setEvaluation(eval);
					noteListTemp.add(noteVide);
				}
			}
		}

		noteList.addAll(noteListTemp);

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
			classementEleveParMatiere(calculMoyenneMatierev2(moyenneList), classe.getBranche().getId(),
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
		Map<Long, MoyenneAdjustment> moyenneAdjustmentByEleveMap = new HashMap<Long, MoyenneAdjustment>();
		Map<Long, String> coefParMatiereMap = new HashMap<Long, String>();

//		Gson g = new Gson();

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
			// Ce map contient les ajustements de moyenne d'un eleve
			moyenneAdjustmentByEleveMap = adjustmentService
					.getByAnneePeriodeMatriculeAndStatut(me.getAnnee().getId(), me.getPeriode().getId(),
							me.getEleve().getMatricule(), Constants.VALID)
					.stream().collect(Collectors.toMap(adj -> adj.getMatiere(), adj -> adj));
			if (coefParMatiereMap.size() <= 0) {
				System.out.println("NoteService.calculMoyenneMatiere()");
				System.out.println("---> Chargement des coeficients par matiere");
				coefParMatiereMap = classeMatiereService
						.getByBranche(me.getClasse().getBranche().getId(), me.getClasse().getEcole().getId()).stream()
						.collect(Collectors.toMap(cm -> cm.getMatiere().getId(), cm -> cm.getCoef()));
			}
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
				MoyenneAdjustment moyenneAdjustment = moyenneAdjustmentByEleveMap.getOrDefault(entry.getKey().getId(),
						new MoyenneAdjustment());
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
//					System.out.println(note);---> Calcul des moyennes par matiere
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

//					logger.info("Moyenne = " + somme + " / " + diviser + " = " + CommonUtils.roundDouble(moyenne, 2));
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

//					ClasseMatiere cm = new ClasseMatiere();
//					cm = classeMatiereService.getByMatiereAndBranche(entry.getKey().getId(),
//							me.getClasse().getBranche().getId(), me.getClasse().getEcole().getId());
					mc.setCoef(Double.valueOf(
							coefParMatiereMap.getOrDefault(entry.getKey().getId(), Constants.DEFAULT_COEFFICIENT)));
//					mc.setCoef(cm != null ? Double.valueOf(cm.getCoef()) : null);
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
//				MoyenneAdjustment moyenneAdjustment = adjustmentService.getByAnneePeriodeMatriculeAndMatiereAndStatut(
//						me.getAnnee().getId(), me.getPeriode().getId(), me.getEleve().getMatricule(),
//						hasMatiere.getId(), Constants.VALID);

				MoyenneAdjustment moyenneAdjustment = moyenneAdjustmentByEleveMap.getOrDefault(hasMatiere.getId(),
						new MoyenneAdjustment());
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
					m.setRangFr(moyennesFrExcpt.indexOf(m.getMoyFr()) + 1);
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
//				logger.info("Moyenne = " + somme + " / " + diviser + " = " + CommonUtils.roundDouble(moyenne, 2));

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
//						logger.info("+++ eleve non classe dans cette matiere :" + matiere.getLibelle());
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
//				logger.info("Moyenne Generale :" + eleve.getMoyenne());
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
//		Gson g = new Gson();
		int i = 0;
		for (ClasseMatiere cm : classeMatList) {
//			logger.info("---> " + cm.getMatiere().getId() + " " + cm.getMatiere().getLibelle());
			mapt = new HashMap<Double, MoyenneEleveDto>();
			for (MoyenneEleveDto me : moyEleve) {
				// Liste des restrictions d'un eleve sur les matieres
				List<ClasseEleveMatiere> listCm = classeEleveMatiereService.findByClasseAndEleveAndAnneeAndPeriode(
						me.getClasse().getId(), me.getEleve().getId(), me.getAnnee().getId(), me.getPeriode().getId());
//				logger.info("eleve - " + me.getEleve().getNom());
				// logger.info(".getNotesMatiereMap() - " + me.getNotesMatiereMap());
				if (me.getNotesMatiereMap() != null) {
					for (Map.Entry<EcoleHasMatiere, List<Notes>> map : me.getNotesMatiereMap().entrySet()) {
						if (map.getKey().getId().equals(cm.getMatiere().getId())) {
							mapt.put(map.getKey().getMoyenne(), me);

//							logger.info(String.format("--- %s - %s - %s - %s - %s ---", me.getClasse().getId(),
//									cm.getMatiere().getId(), me.getEleve().getId(), me.getAnnee().getId(),
//									me.getPeriode().getId()));

//							ClasseEleveMatiere cem = classeEleveMatiereService
//									.findByClasseAndMatiereAndEleveAndAnneeAndPeriode(me.getClasse().getId(),
//											cm.getMatiere().getId(), me.getEleve().getId(), me.getAnnee().getId(),
//											me.getPeriode().getId());
							Optional<ClasseEleveMatiere> cemOpt = listCm.stream()
									.filter(c -> c.getMatiere().getId() == cm.getMatiere().getId()).findFirst();
							ClasseEleveMatiere cem = cemOpt.orElse(null);
							if (cem != null && cem.getIsClassed().equals(Constants.NON)) {
//								logger.info("---> cem = " + cem.getIsClassed());
								me.setMoyenneMatiereToSort(Constants.MOYENNE_ELEVE_NON_CLASSE_MATIERE);
								map.getKey().setEleveMatiereIsClassed(Constants.NON);
							} else {
								me.setMoyenneMatiereToSort(map.getKey().getMoyenne());
								map.getKey().setEleveMatiereIsClassed(Constants.OUI);
//								logger.info("---> cem is null ");
							}

//							logger.info("Matiere : " + map.getKey().getLibelle() + " " + map.getKey().getMoyenne() + " "
//									+ map.getKey().getEleveMatiereIsClassed());
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
//						logger.info("matiere id - " + map.getKey().getId() + " --- " + cm.getCoef());
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
//						logger.info("Matiere : " + map.getKey().getLibelle() + " " + map.getKey().getMoyenne()
//								+ " Rang " + map.getKey().getRang() + " coef :" + map.getKey().getCoef());
						break;
					}
				}
			}
		}

	}

	void classementAnnuelEleveParMatiere(List<MoyenneEleveDto> moyEleve, Long brancheId, Long ecoleId, Periode periode,

			List<ClasseMatiere> cmList) {
		System.out.println("periode "+periode);
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
			//System.out.println("Periodicité :"+periode.getPeriodicite().getId());7
			System.out.println("Entree>>> 1");
			System.out.println("Entree>>> 1+++++ "+periode.getPeriodicite().getId());
			try {
				per = Periode.getEntityManager()
						.createQuery("SELECT p FROM Periode p WHERE p.periodicite.id = :periodiciteId AND p.isfinal = :finalValue", Periode.class)
						.setParameter("periodiciteId", periode.getPeriodicite().getId())
						.setParameter("finalValue", "O")
						.getSingleResult();

			} catch (RuntimeException e) {
				e.printStackTrace();
			}


		} catch (RuntimeException e) {
			//System.out.println("Entree>>> 2");
			logger.warning(e.getMessage());
		}
		//System.out.println("Entree>>> 3");
		List<Periode> periodes = Periode.find("niveau <= ?1 and periodicite.id=?2 order by niveau", per.getNiveau(),
				periode.getPeriodicite().getId()).list();
		//System.out.println("Entree>>> 3 "+periodes.size());

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
				System.out.println("per.getId()>>>>>"+per.getId());
				//System.out.println("PERIODE>>>>> "+per);
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
//						logger.info("---------> " + entry.getKey().getCode() + "  " + entry.getKey().getLibelle());
//						System.out.println("---------> " + entry.getKey().getId() + "  " + entry.getKey().getLibelle());
						if (entry.getKey().getId().equals(cml.getMatiere().getId())) {
//							logger.info("-----> " + entry.getKey().getLibelle() + " ok");
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
					"classeEleve.inscription.eleve.matricule = ?1 and classeEleve.classe.id = ?2 and evaluation.annee.id =?3 and evaluation.periode.id = ?4 "
					+ " and evaluation.matiereEcole.id = ?5 and pec = 1",
					matricule, classeId, anneeId, periodeId, matiereId).list();
		} catch (RuntimeException e) {
			logger.warning("Erreur ::: " + e.getMessage());
		}
		return notesByEleve;
	}
	// New calculate average note process
	
	final static String QUERY_GET_NOTES_BY_CLASSE = "Select new com.vieecoles.steph.dto.moyennes.NoteDto("
			+ "		   n.id, "
			+ "        n.evaluation.id, "
			+ "		   n.evaluation.numero,"
			+ "		   n.evaluation.type.libelle,"
			+ "		   n.evaluation.matiereEcole.id, "
			+ "		   n.evaluation.matiereEcole.libelle, "
			+ "        n.note, "
			+ "		   n.evaluation.noteSur, "
			+ "        n.classeEleve.id,"
			+ "		   case when n.evaluation.type.code = '" + Constants.CODE_TEST_LOURD + "' then true else false end "
			+ " ) "
			+ " From Notes n "
			+ " Where "
			+ " n.evaluation.classe.id = :classeId and n.evaluation.annee.id = :anneeId and n.evaluation.periode.id = :periodeId and n.pec = 1";
	
	final static String QUERY_GET_DATA_ELEVE_AND_CLASSE = "SELECT new com.vieecoles.steph.dto.moyennes.EleveDto(ce.inscription.eleve.id, ce.inscription.eleve.matricule, "
			+ "	ce.inscription.eleve.nom, ce.inscription.eleve.prenom,ce.inscription.eleve.sexe, ce.inscription.urlPhoto, ce.classe.id, ce.classe.libelle ) "
			+ " FROM ClasseEleve ce "
			+ " WHERE ce.id = :classeEleveId ";
	
	/**
	 * Obténir la liste des notes regroupées par élève, par matiere et par note  en fonction de l'année, la classe et la période.
	 */
	public List<com.vieecoles.steph.dto.moyennes.NoteDto> getNotesByEleveAndAnneeAndClasseAndPeriode(String classeId, String anneeId, String periodeId) {
//		System.out.println("In method");
		List<com.vieecoles.steph.dto.moyennes.NoteDto> notes;
		try {
		notes = getEntityManager().createQuery(QUERY_GET_NOTES_BY_CLASSE, com.vieecoles.steph.dto.moyennes.NoteDto.class)
			.setParameter("classeId", Long.valueOf(classeId))
			.setParameter("anneeId",  Long.valueOf(anneeId))
			.setParameter("periodeId",  Long.valueOf(periodeId))
			.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			notes = new ArrayList<com.vieecoles.steph.dto.moyennes.NoteDto>();
		}
		return notes;
	}
	
	/**
	 * Obténir la liste des eleves d'une classe.
	 * A déplacer vers le referentiel des eleves.
	 */
	public EleveDto getEleveByClasseEleve(Long classeEleveId) {
		System.out.println("In method");
		EleveDto eleve;
		try {
			eleve = getEntityManager().createQuery(QUERY_GET_DATA_ELEVE_AND_CLASSE, EleveDto.class)
				.setParameter("classeEleveId", classeEleveId)
				.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			eleve = new EleveDto();
		}
		return eleve;
	}
	
	public Map<Long, Map<Long, Map<Long, com.vieecoles.steph.dto.moyennes.NoteDto>>> displayer(String classeId, String anneeId, String periodeId) {
		return groupNotesByEleveAndMatiere(getNotesByEleveAndAnneeAndClasseAndPeriode(classeId, anneeId, periodeId));
		
	}
	
	/**
	 * Grouper les notes par eleve, matiere.
	 *
	 * @param notes
	 * @return
	 */
	public Map<Long, Map<Long, Map<Long, com.vieecoles.steph.dto.moyennes.NoteDto>>> groupNotesByEleveAndMatiere(List<com.vieecoles.steph.dto.moyennes.NoteDto> notes) {
	    return notes.stream()
	        .collect(Collectors.groupingBy(
	            note -> note.getClasseEleveId(),
	            Collectors.groupingBy(
	                    note -> note.getMatiereEcoleId(),
	                    Collectors.toMap(
	                    		note -> note.getId(),
	                    		note -> note
	                    		)
	                    )
	        ));
	}
	
	
	public EleveDto populateEleve() {
		return null;
	}
	
	public EleveDto populateMatiere() {
		return null;
	}
	
	public EleveDto populateNotes() {
		return null;
	}
	public Map<Long, Map<Long, Map<Long, com.vieecoles.steph.dto.moyennes.NoteDto>>> formatDataByGroupingNotes(List<com.vieecoles.steph.dto.moyennes.NoteDto> notes) {
		return groupNotesByEleveAndMatiere(notes);
	}
	/**
	 * Cette méthode permet d'obtenir et structurer les infos de base des élèves d'une classe pour le calcul de moyenne
	 * @param classeId
	 * @param anneeId
	 * @param periodeId
	 * @return
	 */
	public List<EleveMatiereDto> initialiseStructure(String classeId, String anneeId, String periodeId) {
		List<EleveMatiereDto> eleves = new ArrayList<EleveMatiereDto>();
		
		// Obténir toutes les notes des élèves d'une classe
		List<com.vieecoles.steph.dto.moyennes.NoteDto> notes = getNotesByEleveAndAnneeAndClasseAndPeriode(classeId, anneeId, periodeId);
		
		// Structurer les données par eleve, matiere et note
		Map<Long, Map<Long, Map<Long, com.vieecoles.steph.dto.moyennes.NoteDto>>> groupingNotes = groupNotesByEleveAndMatiere(notes);
		
		// Traiter chaque eleve
		for (Map.Entry<Long, Map<Long, Map<Long, com.vieecoles.steph.dto.moyennes.NoteDto>>> eleveEntry : groupingNotes.entrySet()) {
		    Long classeEleveId = eleveEntry.getKey();
		    System.out.println("Classe Élève ID: " + classeEleveId);
		    
		    // Renseigner les infos supplémentaires sur l'élève
		    EleveDto eleve = getEleveByClasseEleve(classeEleveId);
		    PersonneDto personneDto = new PersonneDto(eleve.getId(), eleve.getMatricule(), eleve.getNom(), eleve.getPrenom(), eleve.getSexe(), eleve.getUrlPhoto());
		    
		    EleveMatiereDto eleveMatiereDto = new EleveMatiereDto();
		    eleveMatiereDto.setEleve(personneDto);
		    eleveMatiereDto.setClasse(new IdLongCodeLibelleDto(eleve.getClasseId(), null, eleve.getClasseLibelle()));
		    
		    List<MatiereNotes> listMatiereNotes = new ArrayList<MatiereNotes>();

		    Map<Long, Map<Long, com.vieecoles.steph.dto.moyennes.NoteDto>> matieres = eleveEntry.getValue();
		    // Traiter chaque matiere d'un eleve
		    for (Map.Entry<Long, Map<Long, com.vieecoles.steph.dto.moyennes.NoteDto>> matiereEntry : matieres.entrySet()) {
		        Long matiereId = matiereEntry.getKey();
		        System.out.println("  Matière ID: " + matiereId);
		        String matiereLibelle = "";
		        
		        MatiereNotes matiereNotes = new MatiereNotes();
		        matiereNotes.setMatiereId(matiereId.toString());
		        Map<Long, com.vieecoles.steph.dto.moyennes.NoteDto> notesParMatiere = matiereEntry.getValue();
		        List<com.vieecoles.steph.dto.moyennes.NoteDto> notesDto = new ArrayList<>();
		        // Traiter les notes
		        for (Map.Entry<Long, com.vieecoles.steph.dto.moyennes.NoteDto> noteEntry : notesParMatiere.entrySet()) {
		        	com.vieecoles.steph.dto.moyennes.NoteDto note = noteEntry.getValue();
		            System.out.println("    Note ID: " + note.getId() + ", valeur: " + note.getNote());
		            matiereLibelle = note.getMatiereEcoleLibelle();
		            String noteSur = Evaluation.find("select e.noteSur from Evaluation e where e.id = ?1 ", note.getEvaluationId()).project(String.class).singleResult();
		            notesDto.add(new com.vieecoles.steph.dto.moyennes.NoteDto(note.getId(),note.getEvaluationId(),note.getEvaluationNumero(), note.getEvaluationType(), 
		            		null, null, note.getNote(), noteSur, null, note.getIsTestLourd()));
		        }
		        matiereNotes.setMatiereLibelle(matiereLibelle);
		        matiereNotes.setNotes(notesDto);
		        listMatiereNotes.add(matiereNotes);
		    }
		    eleveMatiereDto.setMatieres(listMatiereNotes);
		    eleves.add(eleveMatiereDto);
		}
		
		return eleves;
	}
	
	/* METHODES DE CALCUL DE MOYENNES A DEPLACER AU BON ENDROIT */
	
	Map<String, String> matiereCoefMap = new HashMap<String, String>();
	
	public void calculateMoyenneMatiereClassicVersion(List<com.vieecoles.steph.dto.moyennes.NoteDto> notes) {

	}
	
	/**
	 * Cette méthode permet de calculer la moyenne d'une liste de notes.
	 * 
	 * @param notes
	 * @return
	 */
	private static double calculerMoyenne(List<com.vieecoles.steph.dto.moyennes.NoteDto> notes) {
		var normales = notes.stream().filter(n -> !n.getIsTestLourd()).collect(Collectors.toList());
        var testsLourds = notes.stream().filter(com.vieecoles.steph.dto.moyennes.NoteDto::getIsTestLourd).collect(Collectors.toList());

        // Calcul des valeurs normales
        double sommeNormale = normales.stream().mapToDouble(com.vieecoles.steph.dto.moyennes.NoteDto::getNote).sum();
        double diviseurNormale = normales.stream()
                .mapToDouble(n -> n.getNoteSur() / 20.0)
                .sum();
        double moyenneNormale = diviseurNormale > 0 ? sommeNormale / diviseurNormale : 0;

        // Calcul des valeurs test lourd
        double sommeTestLourd = testsLourds.stream().mapToDouble(com.vieecoles.steph.dto.moyennes.NoteDto::getNote).sum();
        double diviseurTestLourd = testsLourds.stream()
                .mapToDouble(n -> n.getNoteSur() / 20.0)
                .sum();
        double moyenneTestLourd = diviseurTestLourd > 0 ? sommeTestLourd / diviseurTestLourd : 0;

        // Moyenne finale pondérée : poids = 1 pour normal, 2 pour test lourd
        int poidsNormale = normales.isEmpty() ? 0 : 1;
        int poidsTestLourd = testsLourds.isEmpty() ? 0 : 2;
        int totalPoids = poidsNormale + poidsTestLourd;

        if (totalPoids == 0) return 0;
        
        double moyenne = (moyenneNormale * poidsNormale + moyenneTestLourd * poidsTestLourd) / totalPoids; 
        
        return Math.round(moyenne * 100) /100;
    }
	
	
	private EleveMatiereDto calculerMoyenneEleve(EleveMatiereDto eleve) {
		
		Double sommeCoef = eleve.getMatieres().stream().mapToDouble(MatiereNotes::getCoef).sum();
		Double sommeMoyenneCoef = eleve.getMatieres().stream().mapToDouble(MatiereNotes::getMoyenneCoef).sum();
		Double moyenne = sommeCoef != 0 ? sommeMoyenneCoef/sommeCoef : 0.0;
		System.out.println(String.format("Moyenne generale eleve %s somme moyenne %s / somme coef %s = %s", eleve.getEleve().getMatricule(), sommeMoyenneCoef, sommeCoef, moyenne));
		eleve.setMoyenne(moyenne);
		eleve.setObservation(CommonUtils.appreciation(moyenne));
		return eleve; 
		
	}

	private Double getCoefficient(String matiereId) {
		return Double.valueOf(matiereCoefMap.getOrDefault(matiereId.toString(), "1.0"));
	}
	
	private void loadCoefficientsMatiere(Long classeId) {
		matiereCoefMap.putAll(classeMatiereService.getMapCoefByClasse(Long.valueOf(classeId)));
	}
	
	public List<EleveMatiereDto> process(String classeId, String anneeId, String periodeId) {
		loadCoefficientsMatiere(Long.valueOf(classeId));
		
		// structurer les informations avec les informations de base telles que les notes, coeficient, notesur, ... ok
		
		// calculer les moyennes par matieres ok
		
		List<EleveMatiereDto> moyenneParMatiere = initialiseStructure(classeId, anneeId, periodeId).stream().parallel()
			.peek(e -> e.getMatieres().forEach(m -> {
				m.setMoyenne(calculerMoyenne(m.getNotes()));
				m.setAppreciation(CommonUtils.appreciation(m.getMoyenne()));
				m.setCoef(getCoefficient(m.getMatiereId()));
				m.setMoyenneCoef((double) (Math.round(m.getMoyenne() * m.getCoef() * 100) /100));
			}
			)).collect(Collectors.toList());
		
		// calculer les moyennes par eleves
		
		List<EleveMatiereDto> moyenneParEleve = moyenneParMatiere.stream().parallel().peek(e -> calculerMoyenneEleve(e))
				.collect(Collectors.toList());
		
		// calculer les moyennes annuelles si derniere période
		
		// calculer les rangs matiere
		
		// calculer les rangs par eleve
		
		// calculer les rangs annuels si derniere periode 
		
		return moyenneParEleve;
		
	}
	
	

	/**
	 * NOUVELLE STRUCTURATION DU CALCUL DES MOYENNES PAR MATIERE
	 */

	// Constantes pour améliorer la lisibilité
	private static final double ZERO_THRESHOLD = 0.0;
	private static final double DEFAULT_DIVISOR = 1.0;
	private static final int TEST_LOURD_MULTIPLIER = 2;
	private static final int MOYENNE_DIVISOR = 3;
	private static final int PRECISION = 2;

	/**
	 * Calcule les moyennes par matière pour une liste d'élèves
	 */
	public List<MoyenneEleveDto> calculMoyenneMatierev2(List<MoyenneEleveDto> moyEleve) {
		logger.info("---> Calcul des moyennes par matiere");

		List<Double> moyennesFrExcpt = new ArrayList<>();

		// Cache global des coefficients par matière (optimisation du code original)
		Map<Long, String> coefParMatiereMap = new HashMap<>();

		for (MoyenneEleveDto me : moyEleve) {
			// Initialisation des variables locales
			MatiereCalculContext context = new MatiereCalculContext();

			// Chargement des ajustements et coefficients (une seule fois par élève)
			Map<Long, MoyenneAdjustment> moyenneAdjustmentMap = loadMoyenneAdjustments(me);
			coefParMatiereMap = loadCoefficientsIfNeeded(coefParMatiereMap, me);

			// Traitement de chaque matière
			for (Map.Entry<EcoleHasMatiere, List<Notes>> entry : me.getNotesMatiereMap().entrySet()) {
				EcoleHasMatiere matiere = entry.getKey();
				List<Notes> notes = entry.getValue();

				// Calcul de la moyenne pour cette matière
				MoyenneResult moyenneResult = calculerMoyenneMatiere(matiere, notes, moyenneAdjustmentMap);

				// Application du résultat à la matière
				appliqueMoyenneAMatiere(matiere, moyenneResult);

				// Gestion des cas spéciaux (Français, EMR, Religion)
				handleCasSpeciaux(matiere, moyenneResult, context, coefParMatiereMap, me);
			}

			// Finalisation des calculs spéciaux
			finaliserCalculsSpeciaux(me, context, moyennesFrExcpt);
		}

		// Calcul du rang français
		calculerRangFrancais(moyEleve, moyennesFrExcpt);

		return moyEleve;
	}

	/**
	 * Charge les ajustements de moyenne pour un élève
	 */
	private Map<Long, MoyenneAdjustment> loadMoyenneAdjustments(MoyenneEleveDto me) {
		return adjustmentService
				.getByAnneePeriodeMatriculeAndStatut(me.getAnnee().getId(), me.getPeriode().getId(),
						me.getEleve().getMatricule(), Constants.VALID)
				.stream().collect(Collectors.toMap(adj -> adj.getMatiere(), adj -> adj));
	}

	/**
	 * Charge les coefficients par matière si nécessaire (optimisation du code
	 * original)
	 */
	private Map<Long, String> loadCoefficientsIfNeeded(Map<Long, String> existingMap, MoyenneEleveDto me) {
		if (existingMap.isEmpty()) {
			logger.info("---> Chargement des coefficients par matière");
			return classeMatiereService
					.getByBranche(me.getClasse().getBranche().getId(), me.getClasse().getEcole().getId()).stream()
					.collect(Collectors.toMap(cm -> cm.getMatiere().getId(), cm -> cm.getCoef()));
		}
		return existingMap;
	}

	/**
	 * Calcule la moyenne d'une matière
	 */
	private MoyenneResult calculerMoyenneMatiere(EcoleHasMatiere matiere, List<Notes> notes,
			Map<Long, MoyenneAdjustment> adjustmentMap) {

		MoyenneAdjustment adjustment = adjustmentMap.getOrDefault(matiere.getId(), new MoyenneAdjustment());

		// Si ajustement existe, retourner directement
		if (adjustment.getId() != null) {
			logger.info("Moyenne repêchage trouvée = " + CommonUtils.roundDouble(adjustment.getMoyenne(), PRECISION));
			return new MoyenneResult(adjustment.getMoyenne(), adjustment.getMoyenne(), Constants.OUI);
		}

		// Séparation des notes normales et tests lourds
		NotesClassification classification = classifierNotes(notes);

		// Calcul moyenne normale
		double moyenneNormale = calculerMoyenneNormale(classification.getNotesNormales(),
				classification.getDiviseurNormal());

		// Ajout de la note sur pour les tests lourds
		if (!classification.getNotesTestLourd().isEmpty()) {
			matiere.setTestLourdNoteSur(classification.getTestLourdNoteSur());
		}

		// Calcul moyenne finale avec tests lourds
		double moyenneFinale = calculerMoyenneAvecTestsLourds(moyenneNormale, classification.getNotesTestLourd(),
				classification.getDiviseurTestLourd(), matiere);

		return new MoyenneResult(moyenneFinale, moyenneNormale, Constants.NON);
	}

	/**
	 * Classifie les notes entre normales et tests lourds
	 */
	private NotesClassification classifierNotes(List<Notes> notes) {
		List<Double> notesNormales = new ArrayList<>();
		List<Double> notesTestLourd = new ArrayList<>();
		double diviseurNormal = ZERO_THRESHOLD;
		double diviseurTestLourd = ZERO_THRESHOLD;
		int testLourdNoteSur = 0;

		for (Notes note : notes) {
			if (isNotePriseEnCompte(note)) {
				if (isTestLourd(note)) {
					notesTestLourd.add(note.getNote());
					diviseurTestLourd += Double.parseDouble(note.getEvaluation().getNoteSur())
							/ Double.parseDouble(Constants.DEFAULT_NOTE_SUR);
					testLourdNoteSur = Integer.parseInt(note.getEvaluation().getNoteSur());
				} else {
					notesNormales.add(note.getNote());
					diviseurNormal += Double.parseDouble(note.getEvaluation().getNoteSur())
							/ Double.parseDouble(Constants.DEFAULT_NOTE_SUR);
				}
			}
		}

		return new NotesClassification(notesNormales, notesTestLourd, diviseurNormal, diviseurTestLourd,
				testLourdNoteSur);
	}

	/**
	 * Vérifie si une note est prise en compte dans le calcul
	 */
	private boolean isNotePriseEnCompte(Notes note) {
		return note.getEvaluation().getPec() == Constants.PEC_1 && note.getPec() != null
				&& note.getPec() == Constants.PEC_1;
	}

	/**
	 * Vérifie si une note est un test lourd
	 */
	private boolean isTestLourd(Notes note) {
		return note.getEvaluation().getType() != null && note.getEvaluation().getType().getCode() != null
				&& note.getEvaluation().getType().getCode().equals(Constants.CODE_TEST_LOURD);
	}

	/**
	 * Calcule la moyenne normale
	 */
	private double calculerMoyenneNormale(List<Double> notes, double diviseur) {
		if (notes.isEmpty())
			return ZERO_THRESHOLD;

		double somme = notes.stream().mapToDouble(Double::doubleValue).sum();
		return somme / (diviseur == ZERO_THRESHOLD ? DEFAULT_DIVISOR : diviseur);
	}

	/**
	 * Calcule la moyenne finale en intégrant les tests lourds
	 */
	private double calculerMoyenneAvecTestsLourds(double moyenneNormale, List<Double> notesTestLourd,
			double diviseurTestLourd, EcoleHasMatiere matiere) {
		if (notesTestLourd.isEmpty()) {
			return moyenneNormale;
		}

		double sommeTestLourd = notesTestLourd.stream().mapToDouble(Double::doubleValue).sum();
		double moyenneTestLourd = sommeTestLourd
				/ (diviseurTestLourd == ZERO_THRESHOLD ? DEFAULT_DIVISOR : diviseurTestLourd);

		matiere.setTestLourdNote(sommeTestLourd);

		return (moyenneNormale + moyenneTestLourd * TEST_LOURD_MULTIPLIER) / MOYENNE_DIVISOR;
	}

	/**
	 * Applique le résultat de moyenne à la matière
	 */
	private void appliqueMoyenneAMatiere(EcoleHasMatiere matiere, MoyenneResult result) {
		matiere.setMoyenne(CommonUtils.roundDouble(result.getMoyenneFinale(), PRECISION));
		matiere.setMoyenneIntermediaire(CommonUtils.roundDouble(result.getMoyenneIntermediaire(), PRECISION));
		matiere.setAppreciation(CommonUtils.appreciation(result.getMoyenneFinale()));
		matiere.setIsAdjustment(result.getIsAdjustment());
	}

	/**
	 * Gère les cas spéciaux (Français, EMR, Religion)
	 */
	private void handleCasSpeciaux(EcoleHasMatiere matiere, MoyenneResult moyenneResult, MatiereCalculContext context,
			Map<Long, String> coefMap, MoyenneEleveDto me) {

		// Cas matière Français
		if (isMatiereFrancais(matiere)) {
			handleMatiereFrancais(matiere, moyenneResult, context, coefMap);
		}

		// Cas sous-matières EMR
		if (isSousMatiereEMR(matiere)) {
			handleSousMatiereEMR(matiere, moyenneResult, context, me);
		}

		// Cas matières religion (hors EMR)
		if (!context.isCheckEMRCalculFlat() && isMatiereReligion(matiere)) {
			context.addMoyenneReligion(CommonUtils.roundDouble(moyenneResult.getMoyenneFinale(), PRECISION));
			context.setCalculExcpReligionFlat(true);
		}
	}

	/**
	 * Vérifie si c'est une matière français
	 */
	private boolean isMatiereFrancais(EcoleHasMatiere matiere) {
		return Constants.CODE_NIVEAU_ENS_SECONDAIRE.equals(matiere.getNiveauEnseignement().getCode())
				&& matiere.getMatiereParent() != null && matiere.getMatiereParent().getMatiere() != null
				&& matiere.getMatiereParent().getMatiere().getMatiereParent() != null && matiere.getMatiereParent()
						.getMatiere().getMatiereParent().equals(Constants.ID_MATIERE_FRANCAIS_CENTRAL);
	}

	/**
	 * Vérifie si c'est une sous-matière EMR
	 */
	private boolean isSousMatiereEMR(EcoleHasMatiere matiere) {
		return matiere.getMatiereParent() != null && matiere.getMatiereParent().getIsEMR() != null
				&& matiere.getMatiereParent().getIsEMR().equals(Constants.OUI);
	}

	/**
	 * Vérifie si c'est une matière religion
	 */
	private boolean isMatiereReligion(EcoleHasMatiere matiere) {
		return Constants.CODE_NIVEAU_ENS_SECONDAIRE.equals(matiere.getNiveauEnseignement().getCode())
				&& Constants.CODE_CATEGORIE_RELIGION.equals(matiere.getCategorie().getCode());
	}

	/**
	 * Gère le cas spécial des matières français
	 */
	private void handleMatiereFrancais(EcoleHasMatiere matiere, MoyenneResult moyenneResult,
			MatiereCalculContext context, Map<Long, String> coefMap) {
		MoyenneCoefPojo mc = new MoyenneCoefPojo();
		mc.setCoef(Double.valueOf(coefMap.getOrDefault(matiere.getId(), Constants.DEFAULT_COEFFICIENT)));
		mc.setMoyenne(CommonUtils.roundDouble(moyenneResult.getMoyenneFinale(), PRECISION));
		mc.setMoyenneIntermediaire(moyenneResult.getMoyenneIntermediaire());

		context.addMoyenneSousMatieresFrancais(mc);
		context.setCalculExcpFrFlat(true);
	}

	/**
	 * Gère le cas spécial des sous-matières EMR
	 */
	private void handleSousMatiereEMR(EcoleHasMatiere matiere, MoyenneResult moyenneResult,
			MatiereCalculContext context, MoyenneEleveDto me) {
		context.setCheckEMRCalculFlat(true);

		if (context.getDiviserEMR() == ZERO_THRESHOLD) {
			context.setEhmEMR(matiere.getMatiereParent());
			context.setEMRFlat(true);
		}

		context.addSommeEMR(moyenneResult.getMoyenneFinale());
		context.addSommeEMRIntermediaire(moyenneResult.getMoyenneIntermediaire());
		context.incrementDiviserEMR();

		// Création de la note EMR
		Notes noteEMR = creerNoteEMR(matiere, moyenneResult, me);
		context.addMoyenneEMRList(noteEMR);
	}

	/**
	 * Crée une note EMR
	 */
	private Notes creerNoteEMR(EcoleHasMatiere matiere, MoyenneResult moyenneResult, MoyenneEleveDto me) {
		Evaluation evalEMR = new Evaluation();
		evalEMR.setAnnee(me.getAnnee());
		evalEMR.setClasse(me.getClasse());
		evalEMR.setPec(Constants.PEC_1);
		evalEMR.setCode(me.getNumeroEvaluation() + "_1");
		evalEMR.setId(new Random().nextLong());
		evalEMR.setNoteSur(Constants.DEFAULT_NOTE_SUR);
		evalEMR.setMatiereEcole(matiere.getMatiereParent());
		evalEMR.setPeriode(me.getPeriode());

		Notes noteEMR = new Notes();
		noteEMR.setClasseEleve(me.getNotesMatiereMap().values().iterator().next().get(0).getClasseEleve());
		noteEMR.setEvaluation(evalEMR);
		noteEMR.setId(new Random().nextLong());
		noteEMR.setNote(CommonUtils.roundDouble(moyenneResult.getMoyenneFinale(), PRECISION));
		noteEMR.setPec(Constants.PEC_1);

		return noteEMR;
	}

	/**
	 * Finalise les calculs spéciaux (Français, Religion, EMR)
	 */
	private void finaliserCalculsSpeciaux(MoyenneEleveDto me, MatiereCalculContext context,
			List<Double> moyennesFrExcpt) {
		// Calcul français global
		if (context.isCalculExcpFrFlat()) {
			finaliserCalculFrancais(me, context, moyennesFrExcpt);
		}

		// Calcul EMR global
		if (context.isEMRFlat()) {
			finaliserCalculEMR(me, context);
		}

		// Calcul religion global
		if (context.isCalculExcpReligionFlat()) {
			finaliserCalculReligion(me, context);
		}
	}

	/**
	 * Finalise le calcul du français
	 */
	private void finaliserCalculFrancais(MoyenneEleveDto me, MatiereCalculContext context,
			List<Double> moyennesFrExcpt) {
		List<MoyenneCoefPojo> moyennesSousMatieres = context.getMoyennesSousMatieresFrancais();

		double coefFr = moyennesSousMatieres.stream().mapToDouble(MoyenneCoefPojo::getCoef).sum();
		double sumMoyFr = moyennesSousMatieres.stream().mapToDouble(mc -> mc.getMoyenne() * mc.getCoef()).sum();
		double sumMoyFrIntrmd = moyennesSousMatieres.stream()
				.mapToDouble(mc -> mc.getMoyenneIntermediaire() * mc.getCoef()).sum();

		double moyFr = CommonUtils.roundDouble(sumMoyFr / (coefFr != ZERO_THRESHOLD ? coefFr : DEFAULT_DIVISOR),
				PRECISION);
		double moyFrIntrmd = CommonUtils
				.roundDouble(sumMoyFrIntrmd / (coefFr != ZERO_THRESHOLD ? coefFr : DEFAULT_DIVISOR), PRECISION);

		me.setMoyFr(moyFr);
		me.setMoyFrIntermediaire(moyFrIntrmd);
		me.setCoefFr(coefFr);
		me.setMoyCoefFr(moyFr * coefFr);
		me.setAppreciationFr(CommonUtils.appreciation(moyFr));

		moyennesFrExcpt.add(moyFr);
	}

	/**
	 * Finalise le calcul EMR
	 */
	private void finaliserCalculEMR(MoyenneEleveDto me, MatiereCalculContext context) {
		EcoleHasMatiere hasMatiere = hasMatiereService.getEMRByEcole(me.getClasse().getEcole().getId());

		// Vérification repêchage EMR
		Map<Long, MoyenneAdjustment> adjustmentMap = loadMoyenneAdjustments(me);
		MoyenneAdjustment moyenneAdjustment = adjustmentMap.getOrDefault(hasMatiere.getId(), new MoyenneAdjustment());

		double moyenneEMR;
		double moyenneEMRIntermediaire;
		String isAdjustment = Constants.NON;

		if (moyenneAdjustment.getId() == null) {
			moyenneEMR = context.getSommeEMR()
					/ (context.getDiviserEMR() == ZERO_THRESHOLD ? DEFAULT_DIVISOR : context.getDiviserEMR());
			moyenneEMRIntermediaire = context.getSommeEMRIntermediaire()
					/ (context.getDiviserEMR() == ZERO_THRESHOLD ? DEFAULT_DIVISOR : context.getDiviserEMR());
		} else {
			isAdjustment = Constants.OUI;
			moyenneEMR = moyenneAdjustment.getMoyenne();
			moyenneEMRIntermediaire = moyenneEMR;
		}

		// Ajout aux moyennes religion
		context.addMoyenneReligion(CommonUtils.roundDouble(moyenneEMR, PRECISION));
		context.setCalculExcpReligionFlat(true);

		// Copie defensive de l'objet EMR
		EcoleHasMatiere ehm_ = cloneEcoleHasMatiere(context.getEhmEMR());
		ehm_.setMoyenne(CommonUtils.roundDouble(moyenneEMR, PRECISION));
		ehm_.setAppreciation(CommonUtils.appreciation(moyenneEMR));
		ehm_.setMoyenneIntermediaire(CommonUtils.roundDouble(moyenneEMRIntermediaire, PRECISION));
		ehm_.setIsAdjustment(isAdjustment);

		me.getNotesMatiereMap().put(ehm_, context.getMoyenneEMRList());
	}

	/**
	 * Finalise le calcul religion
	 */
	private void finaliserCalculReligion(MoyenneEleveDto me, MatiereCalculContext context) {
		double moyReli = CommonUtils.roundDouble(context.getMoyennesMatieresReligion().stream()
				.mapToDouble(Double::doubleValue).average().orElse(ZERO_THRESHOLD), PRECISION);
		me.setMoyReli(moyReli);
		me.setAppreciationReli(CommonUtils.appreciation(moyReli));
	}

	/**
	 * Clone un objet EcoleHasMatiere (copie defensive)
	 */
	private EcoleHasMatiere cloneEcoleHasMatiere(EcoleHasMatiere source) {
		EcoleHasMatiere clone = new EcoleHasMatiere();
		clone.setId(source.getId());
		clone.setPec(source.getPec());
		clone.setCoef(source.getCoef());
		clone.setMatiereParent(source.getMatiereParent());
		clone.setCode(source.getCode());
		clone.setLibelle(source.getLibelle());
		clone.setCategorie(source.getCategorie());
		clone.setBonus(source.getBonus());
		clone.setEcole(source.getEcole());
		clone.setParentMatiereLibelle(source.getParentMatiereLibelle());
		clone.setNumOrdre(source.getNumOrdre());
		clone.setMatiere(source.getMatiere());
		return clone;
	}

	/**
	 * Calcule le rang pour le français
	 */
	private void calculerRangFrancais(List<MoyenneEleveDto> moyEleve, List<Double> moyennesFrExcpt) {
		if (moyennesFrExcpt.isEmpty())
			return;

		List<Double> moyennesSorted = moyennesFrExcpt.stream().sorted(Comparator.reverseOrder())
				.collect(Collectors.toList());

		for (MoyenneEleveDto m : moyEleve) {
			if (m.getMoyFr() != null) {
				m.setRangFr(moyennesSorted.indexOf(m.getMoyFr()) + 1);
			} else {
				m.setRangFr(moyennesSorted.size());
			}
		}
	}

	// Classes internes pour encapsuler les données

	/**
	 * Classe pour encapsuler le résultat d'un calcul de moyenne
	 */
	private static class MoyenneResult {
		private final double moyenneFinale;
		private final double moyenneIntermediaire;
		private final String isAdjustment;

		public MoyenneResult(double moyenneFinale, double moyenneIntermediaire, String isAdjustment) {
			this.moyenneFinale = moyenneFinale;
			this.moyenneIntermediaire = moyenneIntermediaire;
			this.isAdjustment = isAdjustment;
		}

		// Getters
		public double getMoyenneFinale() {
			return moyenneFinale;
		}

		public double getMoyenneIntermediaire() {
			return moyenneIntermediaire;
		}

		public String getIsAdjustment() {
			return isAdjustment;
		}
	}

	/**
	 * Classe pour classifier les notes
	 */
	private static class NotesClassification {
		private final List<Double> notesNormales;
		private final List<Double> notesTestLourd;
		private final double diviseurNormal;
		private final double diviseurTestLourd;
		private final int testLourdNoteSur;

		public NotesClassification(List<Double> notesNormales, List<Double> notesTestLourd, double diviseurNormal,
				double diviseurTestLourd, int testLourdNoteSur) {
			this.notesNormales = notesNormales;
			this.notesTestLourd = notesTestLourd;
			this.diviseurNormal = diviseurNormal;
			this.diviseurTestLourd = diviseurTestLourd;
			this.testLourdNoteSur = testLourdNoteSur;
		}

		// Getters
		public List<Double> getNotesNormales() {
			return notesNormales;
		}

		public List<Double> getNotesTestLourd() {
			return notesTestLourd;
		}

		public double getDiviseurNormal() {
			return diviseurNormal;
		}

		public double getDiviseurTestLourd() {
			return diviseurTestLourd;
		}

		public int getTestLourdNoteSur() {
			return testLourdNoteSur;
		}
	}

	/**
	 * Classe pour maintenir le contexte des calculs spéciaux
	 */
	private static class MatiereCalculContext {
		private double sommeEMR = ZERO_THRESHOLD;
		private double sommeEMRIntermediaire = ZERO_THRESHOLD;
		private double diviserEMR = ZERO_THRESHOLD;
		private List<Notes> moyenneEMRList = new ArrayList<>();
		private List<MoyenneCoefPojo> moyennesSousMatieresFrancais = new ArrayList<>();
		private List<Double> moyennesMatieresReligion = new ArrayList<>();
		private boolean EMRFlat = false;
		private boolean CheckEMRCalculFlat = false;
		private boolean calculExcpFrFlat = false;
		private boolean calculExcpReligionFlat = false;
		private EcoleHasMatiere ehmEMR;

		// Getters et Setters
		public double getSommeEMR() {
			return sommeEMR;
		}

		public void addSommeEMR(double value) {
			this.sommeEMR += value;
		}

		public double getSommeEMRIntermediaire() {
			return sommeEMRIntermediaire;
		}

		public void addSommeEMRIntermediaire(double value) {
			this.sommeEMRIntermediaire += value;
		}

		public double getDiviserEMR() {
			return diviserEMR;
		}

		public void incrementDiviserEMR() {
			this.diviserEMR++;
		}

		public List<Notes> getMoyenneEMRList() {
			return moyenneEMRList;
		}

		public void addMoyenneEMRList(Notes note) {
			this.moyenneEMRList.add(note);
		}

		public List<MoyenneCoefPojo> getMoyennesSousMatieresFrancais() {
			return moyennesSousMatieresFrancais;
		}

		public void addMoyenneSousMatieresFrancais(MoyenneCoefPojo mc) {
			this.moyennesSousMatieresFrancais.add(mc);
		}

		public List<Double> getMoyennesMatieresReligion() {
			return moyennesMatieresReligion;
		}

		public void addMoyenneReligion(double moyenne) {
			this.moyennesMatieresReligion.add(moyenne);
		}

		public boolean isEMRFlat() {
			return EMRFlat;
		}

		public void setEMRFlat(boolean EMRFlat) {
			this.EMRFlat = EMRFlat;
		}

		public boolean isCheckEMRCalculFlat() {
			return CheckEMRCalculFlat;
		}

		public void setCheckEMRCalculFlat(boolean CheckEMRCalculFlat) {
			this.CheckEMRCalculFlat = CheckEMRCalculFlat;
		}

		public boolean isCalculExcpFrFlat() {
			return calculExcpFrFlat;
		}

		public void setCalculExcpFrFlat(boolean calculExcpFrFlat) {
			this.calculExcpFrFlat = calculExcpFrFlat;
		}

		public boolean isCalculExcpReligionFlat() {
			return calculExcpReligionFlat;
		}

		public void setCalculExcpReligionFlat(boolean calculExcpReligionFlat) {
			this.calculExcpReligionFlat = calculExcpReligionFlat;
		}

		public EcoleHasMatiere getEhmEMR() {
			return ehmEMR;
		}

		public void setEhmEMR(EcoleHasMatiere ehmEMR) {
			this.ehmEMR = ehmEMR;
		}
	}

	/**
	 * TRAITEMENT DES CLASSEMENT PAR MATIERE
	 */

	private static final int RANG_PAR_DEFAUT = 1;

	/**
	 * Effectue le classement des élèves par matière de manière optimisée
	 */
	public void classementEleveParMatiereV2(List<MoyenneEleveDto> moyEleve, Long brancheId, Long ecoleId) {
		logger.info("---> Classement des eleves par matiere");

		// Chargement unique des matières de la classe
		List<ClasseMatiere> classeMatList = ClasseMatiere.find("branche.id = ?1 and ecole.id = ?2", brancheId, ecoleId)
				.list();

		// Cache des restrictions par élève (chargement unique)
		Map<Long, List<ClasseEleveMatiere>> restrictionsCache = chargerRestrictionsParEleve(moyEleve);

		// Traitement parallèle pour chaque matière (si liste importante)
		for (ClasseMatiere classeMatiere : classeMatList) {
			traiterClassementPourMatiere(moyEleve, classeMatiere, restrictionsCache);
		}

	}

	/**
	 * Charge toutes les restrictions des élèves en une seule fois (optimisation
	 * majeure)
	 */
	private Map<Long, List<ClasseEleveMatiere>> chargerRestrictionsParEleve(List<MoyenneEleveDto> moyEleve) {
		Map<Long, List<ClasseEleveMatiere>> restrictionsCache = new HashMap<>();

		for (MoyenneEleveDto me : moyEleve) {
			List<ClasseEleveMatiere> restrictions = classeEleveMatiereService.findByClasseAnneeAndPeriode(
					me.getClasse().getId(), me.getAnnee().getId(), me.getPeriode().getId());
			restrictionsCache.put(me.getEleve().getId(), restrictions);

			break;
		}

		return restrictionsCache;
	}

	/**
	 * Traite le classement pour une matière spécifique
	 */
	private void traiterClassementPourMatiere(List<MoyenneEleveDto> moyEleve, ClasseMatiere classeMatiere,
			Map<Long, List<ClasseEleveMatiere>> restrictionsCache) {

		// Étape 1: Préparation des données de classement
		List<EleveClassementData> donneesClassement = preparerDonneesClassement(moyEleve, classeMatiere,
				restrictionsCache);

		// Étape 2: Tri des élèves par moyenne (avec gestion des non-classés)
		trierElevesParMoyenne(donneesClassement);

		// Étape 3: Attribution des rangs avec gestion des ex-aequo
		attribuerRangs(donneesClassement, classeMatiere);

		// Étape 4: Application des résultats aux objets originaux
		appliquerResultats(donneesClassement);
	}

	/**
	 * Prépare les données nécessaires au classement pour une matière
	 */
	private List<EleveClassementData> preparerDonneesClassement(List<MoyenneEleveDto> moyEleve,
			ClasseMatiere classeMatiere, Map<Long, List<ClasseEleveMatiere>> restrictionsCache) {

		List<EleveClassementData> donneesClassement = new ArrayList<>();

		for (MoyenneEleveDto me : moyEleve) {
			if (me.getNotesMatiereMap() == null)
				continue;

			// Recherche de la matière dans les notes de l'élève
			EcoleHasMatiere matiereEleve = trouverMatiereEleve(me, classeMatiere.getMatiere().getId());
			if (matiereEleve == null)
				continue;

			// Vérification des restrictions pour cet élève et cette matière
			boolean estClasse = verifierSiEleveEstClasse(me.getEleve().getId(), classeMatiere.getMatiere().getId(),
					restrictionsCache);

			// Création des données de classement
			EleveClassementData donnees = new EleveClassementData(me, matiereEleve, estClasse);
			donneesClassement.add(donnees);
		}

		return donneesClassement;
	}

	/**
	 * Trouve la matière spécifique dans les notes d'un élève
	 */
	private EcoleHasMatiere trouverMatiereEleve(MoyenneEleveDto eleve, Long matiereId) {
		return eleve.getNotesMatiereMap().keySet().stream().filter(matiere -> matiere.getId().equals(matiereId))
				.findFirst().orElse(null);
	}

	/**
	 * Vérifie si un élève est classé pour une matière donnée
	 */
	private boolean verifierSiEleveEstClasse(Long eleveId, Long matiereId,
			Map<Long, List<ClasseEleveMatiere>> restrictionsCache) {

		List<ClasseEleveMatiere> restrictions = restrictionsCache.get(eleveId);
		if (restrictions == null)
			return true; // Par défaut, l'élève est classé

		Optional<ClasseEleveMatiere> restriction = restrictions.stream()
				.filter(cem -> cem.getMatiere().getId().equals(matiereId)).findFirst();

		return restriction.map(cem -> Constants.OUI.equals(cem.getIsClassed())).orElse(true);
	}

	/**
	 * Trie les élèves par moyenne avec gestion des non-classés
	 */
	private void trierElevesParMoyenne(List<EleveClassementData> donneesClassement) {
		donneesClassement.sort(new ClassementComparator());
	}

	/**
	 * Attribue les rangs avec gestion des ex-aequo
	 */
	private void attribuerRangs(List<EleveClassementData> donneesClassement, ClasseMatiere classeMatiere) {
		int position = 0;
		int rang = RANG_PAR_DEFAUT;
		Double moyennePrecedente = null;

		for (EleveClassementData donnees : donneesClassement) {
			position++;

			// Gestion des ex-aequo : même rang si même moyenne
			if (moyennePrecedente != null && !moyennePrecedente.equals(donnees.getMoyenneEffective())) {
				rang = position;
			}

			// Attribution du rang et du coefficient
			donnees.setRang(rang);
			donnees.setCoefficient(classeMatiere.getCoef() != null ? classeMatiere.getCoef() : "1");

			moyennePrecedente = donnees.getMoyenneEffective();
		}
	}

	/**
	 * Applique les résultats du classement aux objets originaux
	 */
	private void appliquerResultats(List<EleveClassementData> donneesClassement) {
		for (EleveClassementData donnees : donneesClassement) {
			// Mise à jour de l'élève
			donnees.getEleve().setMoyenneMatiereToSort(donnees.getMoyennePourTri());

			// Mise à jour de la matière
			EcoleHasMatiere matiere = donnees.getMatiere();
			matiere.setRang(String.valueOf(donnees.getRang()));
			matiere.setCoef(donnees.getCoefficient());
			matiere.setEleveMatiereIsClassed(donnees.isEstClasse() ? Constants.OUI : Constants.NON);
		}
	}

	/**
	 * Comparateur optimisé pour le tri par moyenne
	 */
	private static class ClassementComparator implements Comparator<EleveClassementData> {
		@Override
		public int compare(EleveClassementData d1, EleveClassementData d2) {
			Double moyenne1 = d1.getMoyennePourTri();
			Double moyenne2 = d2.getMoyennePourTri();

			// Gestion des valeurs nulles
			if (moyenne1 == null && moyenne2 == null)
				return 0;
			if (moyenne1 == null)
				return 1; // Les non-classés vont à la fin
			if (moyenne2 == null)
				return -1;

			// Tri décroissant (meilleure moyenne en premier)
			return moyenne2.compareTo(moyenne1);
		}
	}

	/**
	 * Classe pour encapsuler les données de classement d'un élève
	 */
	private static class EleveClassementData {
		private final MoyenneEleveDto eleve;
		private final EcoleHasMatiere matiere;
		private final boolean estClasse;
		private int rang;
		private String coefficient;

		public EleveClassementData(MoyenneEleveDto eleve, EcoleHasMatiere matiere, boolean estClasse) {
			this.eleve = eleve;
			this.matiere = matiere;
			this.estClasse = estClasse;
		}

		/**
		 * Retourne la moyenne effective (réelle si classé, sinon celle de la matière)
		 */
		public Double getMoyenneEffective() {
			return matiere.getMoyenne();
		}

		/**
		 * Retourne la moyenne à utiliser pour le tri (constante si non classé)
		 */
		public Double getMoyennePourTri() {
			return estClasse ? matiere.getMoyenne() : Constants.MOYENNE_ELEVE_NON_CLASSE_MATIERE;
		}

		// Getters et Setters
		public MoyenneEleveDto getEleve() {
			return eleve;
		}

		public EcoleHasMatiere getMatiere() {
			return matiere;
		}

		public boolean isEstClasse() {
			return estClasse;
		}

		public int getRang() {
			return rang;
		}

		public void setRang(int rang) {
			this.rang = rang;
		}

		public String getCoefficient() {
			return coefficient;
		}

		public void setCoefficient(String coefficient) {
			this.coefficient = coefficient;
		}
	}

	/**
	 * CALCUL MOYENNE ET CLASSEMENT ANNUEL
	 */

	void classementAnnuelEleveParMatiere_v2(List<MoyenneEleveDto> moyEleve, Long brancheId, Long ecoleId,
			Periode periode, List<ClasseMatiere> cmList) {
		logger.info("---> Classement des eleves par matiere");

		// Validation stricte des paramètres d'entrée
		if (moyEleve == null || moyEleve.isEmpty()) {
			logger.warning("Liste des moyennes d'élèves vide ou nulle");
			return;
		}

		if (cmList == null) {
			logger.warning("Liste des classes matières est nulle");
			return;
		}

		// Cache pour éviter les appels répétés à la base de données
		final Ecole ecole = ecoleService.findById(ecoleId);
		if (ecole == null) {
			logger.warning("Ecole introuvable avec l'ID: " + ecoleId);
			return;
		}

		// Récupération optimisée des périodes avec gestion d'erreur complète
		final List<Periode> periodes = getPeriodes(periode);
		if (periodes.isEmpty()) {
			logger.warning("Aucune période trouvée");
			return;
		}

		final Periode finalPeriode = periodes.stream().filter(p -> p != null && "O".equals(p.getIsfinal())).findFirst()
				.orElse(new Periode());

		final double coefFinal = parseCoef(finalPeriode.getCoef());
		final boolean isEnseignementPrimaire = ecole.getNiveauEnseignement() != null
				&& Constants.NIVEAU_ENSEIGNEMENT_PRIMAIRE.equals(ecole.getNiveauEnseignement().getId());

		// Collections pour le classement - taille pré-allouée pour optimiser la mémoire
		final List<Double> classeurAnnuelList = new ArrayList<>(moyEleve.size());
		final List<Double> classeurAnnuelSuperFrList = new ArrayList<>();
		final Map<Long, List<Double>> classeurAnnuelMatiereMap = new HashMap<>();

		// Pré-chargement des bulletins pour tous les élèves
		final Map<String, List<Bulletin>> bulletinsCache = preloadBulletins(moyEleve);

		// Traitement principal des élèves
		for (MoyenneEleveDto me : moyEleve) {
			if (me == null || me.getEleve() == null) {
				logger.warning("Élève ou MoyenneEleveDto null détecté, passage au suivant");
				continue;
			}

			processEleveData(me, ecole, periodes, finalPeriode, coefFinal, isEnseignementPrimaire, classeurAnnuelList,
					classeurAnnuelSuperFrList, classeurAnnuelMatiereMap, bulletinsCache, cmList);
		}

		// Classement final avec gestion robuste des collections
		performFinalRanking(moyEleve, classeurAnnuelList, classeurAnnuelSuperFrList, classeurAnnuelMatiereMap);
	}

	/**
	 * Récupération sécurisée des périodes avec gestion d'erreur complète
	 */
	private List<Periode> getPeriodes(Periode periode) {
		if (periode == null || periode.getPeriodicite() == null) {
			logger.warning("Période ou périodicité nulle");
			return new ArrayList<>();
		}

		try {
			Periode per = Periode.find("periodicite.id=?1 and final = 'O'", periode.getPeriodicite().getId())
					.singleResult();
			if (per == null) {
				logger.warning("Aucune période finale trouvée");
				return new ArrayList<>();
			}

			List<Periode> periodes = Periode.find("niveau <= ?1 and periodicite.id=?2 order by niveau", per.getNiveau(),
					periode.getPeriodicite().getId()).list();

			return periodes != null ? periodes : new ArrayList<>();

		} catch (RuntimeException e) {
			logger.warning("Erreur lors de la récupération des périodes: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Parse sécurisé du coefficient
	 */
	private double parseCoef(String coef) {
		try {
			return Double.parseDouble(coef == null || coef.trim().isEmpty() ? "1" : coef);
		} catch (NumberFormatException e) {
			logger.warning("Coefficient invalide: " + coef + ", utilisation de 1.0 par défaut");
			return 1.0;
		}
	}

	/**
	 * Pré-chargement sécurisé des bulletins avec gestion des nulls
	 */
	private Map<String, List<Bulletin>> preloadBulletins(List<MoyenneEleveDto> moyEleve) {
		Map<String, List<Bulletin>> cache = new HashMap<>(moyEleve.size());

		for (MoyenneEleveDto me : moyEleve) {
			if (me == null || me.getAnnee() == null || me.getEleve() == null || me.getClasse() == null) {
				logger.warning("Données élève incomplètes pour le pré-chargement des bulletins");
				continue;
			}

			String key = me.getAnnee().getId() + "_" + me.getEleve().getMatricule() + "_" + me.getClasse().getId();
			try {
				List<Bulletin> bulletins = bulletinService.getBulletinsEleveByAnnee(me.getAnnee().getId(),
						me.getEleve().getMatricule(), me.getClasse().getId());
				// Protection contre les retours null du service
				cache.put(key, bulletins != null ? bulletins : new ArrayList<>());
			} catch (Exception e) {
				logger.warning("Erreur lors du chargement des bulletins pour l'élève " + me.getEleve().getMatricule()
						+ ": " + e.getMessage());
				cache.put(key, new ArrayList<>());
			}
		}
		return cache;
	}

	/**
	 * Traitement principal des données élève avec gestion complète des erreurs
	 */
	private void processEleveData(MoyenneEleveDto me, Ecole ecole, List<Periode> periodes, Periode finalPeriode,
			double coefFinal, boolean isEnseignementPrimaire, List<Double> classeurAnnuelList,
			List<Double> classeurAnnuelSuperFrList, Map<Long, List<Double>> classeurAnnuelMatiereMap,
			Map<String, List<Bulletin>> bulletinsCache, List<ClasseMatiere> cmList) {

		String bulletinKey = me.getAnnee().getId() + "_" + me.getEleve().getMatricule() + "_" + me.getClasse().getId();
		List<Bulletin> bulletinsElevesList = bulletinsCache.getOrDefault(bulletinKey, new ArrayList<>());

		InfoCalculMoyennePojo infoCalcul = new InfoCalculMoyennePojo();
		List<Double> moyAnInterne = new ArrayList<>();
		List<Double> moyAnIEPP = new ArrayList<>();
		List<Double> moyAnPassage = new ArrayList<>();
		Double moyAnFr = null;

		try {
			if (isEnseignementPrimaire) {
				logger.info("ENS PRIMAIRE");
				handleMoyenneAnnuelleEnsPrimaire(periodes, coefFinal, me, bulletinsElevesList, 0.0, moyAnInterne,
						moyAnIEPP, moyAnPassage);
			} else {
				logger.info("ENS SECONDAIRE ET AUTRES");
				infoCalcul = handleMoyenneAnnuelleEnsSecondaire(periodes, coefFinal, me, bulletinsElevesList, moyAnFr);
				if (infoCalcul != null && infoCalcul.getMoyenneAnSuperFrancais() != null) {
					classeurAnnuelSuperFrList.add(infoCalcul.getMoyenneAnSuperFrancais());
				}
			}
		} catch (Exception e) {
			logger.warning("Erreur lors du calcul des moyennes pour l'élève " + me.getEleve().getMatricule() + ": "
					+ e.getMessage());
			infoCalcul = new InfoCalculMoyennePojo(); // Objet par défaut pour éviter les NPE
		}

		// Logging conditionnel pour optimiser les performances
		if (logger.isLoggable(Level.INFO)) {
			logger.info(String.format("Eleve %s > Moyenne Annuelle = %s - Interne = %s - IEPP = %s - passage = %s",
					me.getEleve().getMatricule(), infoCalcul != null ? infoCalcul.getMoyenneAnnuelle() : "null",
					moyAnInterne.stream().mapToDouble(Double::doubleValue).average().orElse(0.0),
					moyAnIEPP.stream().mapToDouble(Double::doubleValue).average().orElse(0.0),
					moyAnPassage.stream().mapToDouble(Double::doubleValue).average().orElse(0.0)));
		}

		// Mise à jour sécurisée des données élève
		updateEleveData(me, infoCalcul, moyAnInterne, moyAnIEPP, moyAnPassage, classeurAnnuelList);

		// Traitement des matières avec protection contre les nulls
		processMatieres(me, cmList, periodes, classeurAnnuelMatiereMap);
	}

	/**
	 * Mise à jour sécurisée des données élève
	 */
	private void updateEleveData(MoyenneEleveDto me, InfoCalculMoyennePojo infoCalcul, List<Double> moyAnInterne,
			List<Double> moyAnIEPP, List<Double> moyAnPassage, List<Double> classeurAnnuelList) {

		if (infoCalcul == null) {
			logger.warning("InfoCalculMoyennePojo null pour l'élève " + me.getEleve().getMatricule());
			return;
		}

		me.setMoyenneAnnuelle(infoCalcul.getMoyenneAnnuelle());
		if (infoCalcul.getMoyenneAnnuelle() != null) {
			me.setApprAnnuelle(CommonUtils.appreciation(infoCalcul.getMoyenneAnnuelle()));
			classeurAnnuelList.add(infoCalcul.getMoyenneAnnuelle());
		}

		me.setMoyAnFr(infoCalcul.getMoyenneAnSuperFrancais());
		if (infoCalcul.getMoyenneAnSuperFrancais() != null) {
			me.setAppreciationAnFr(CommonUtils.appreciation(infoCalcul.getMoyenneAnSuperFrancais()));
		}

		// Calculs sécurisés des moyennes avec protection contre les listes nulles
		me.setMoyenneIEPP(
				moyAnIEPP != null ? moyAnIEPP.stream().mapToDouble(Double::doubleValue).average().orElse(0.0) : 0.0);
		me.setMoyenneInterne(
				moyAnInterne != null ? moyAnInterne.stream().mapToDouble(Double::doubleValue).average().orElse(0.0)
						: 0.0);
		me.setMoyennePassage(
				moyAnPassage != null ? moyAnPassage.stream().mapToDouble(Double::doubleValue).average().orElse(0.0)
						: 0.0);
	}

	/**
	 * Traitement sécurisé des matières avec pré-chargement optimisé
	 */
	private void processMatieres(MoyenneEleveDto me, List<ClasseMatiere> cmList, List<Periode> periodes,
			Map<Long, List<Double>> classeurAnnuelMatiereMap) {

		if (cmList == null || cmList.isEmpty()) {
			logger.warning("Liste des matières vide pour l'élève " + me.getEleve().getMatricule());
			return;
		}

		if (me.getNotesMatiereMap() == null) {
			logger.warning("Notes matière map null pour l'élève " + me.getEleve().getMatricule());
			return;
		}

		// Pré-chargement sécurisé des détails bulletins pour toutes les matières de
		// l'élève
		Map<Long, List<DetailBulletin>> detailsCache = new HashMap<>();
		for (ClasseMatiere cm : cmList) {
			if (cm == null || cm.getMatiere() == null) {
				logger.warning("ClasseMatiere ou Matiere null détecté");
				continue;
			}

			try {
				List<DetailBulletin> details = detailBulletinService.getDetailBulletinsEleveByAnneeAndClasseAndMatiere(
						me.getAnnee().getId(), me.getClasse().getId(), me.getEleve().getMatricule(),
						cm.getMatiere().getId());
				detailsCache.put(cm.getMatiere().getId(), details != null ? details : new ArrayList<>());
			} catch (Exception e) {
				logger.warning("Erreur lors du chargement des détails bulletins pour la matière "
						+ cm.getMatiere().getId() + ": " + e.getMessage());
				detailsCache.put(cm.getMatiere().getId(), new ArrayList<>());
			}
		}

		// Traitement de chaque matière
		for (ClasseMatiere cml : cmList) {
			if (cml == null || cml.getMatiere() == null)
				continue;

			processMatiere(me, cml, periodes, detailsCache, classeurAnnuelMatiereMap);
		}
	}

	/**
	 * Traitement d'une matière spécifique avec la logique exacte de l'original
	 */
	private void processMatiere(MoyenneEleveDto me, ClasseMatiere cml, List<Periode> periodes,
			Map<Long, List<DetailBulletin>> detailsCache, Map<Long, List<Double>> classeurAnnuelMatiereMap) {

		double coef = 0.0;
		double moyMatiereAnnuelle = 0.0;

		List<DetailBulletin> detailsBulletinsElevesList = detailsCache.getOrDefault(cml.getMatiere().getId(),
				new ArrayList<>());

		// Calcul de la moyenne annuelle de la matière - LOGIQUE EXACTE DE L'ORIGINAL
		for (DetailBulletin dtb : detailsBulletinsElevesList) {
			if (dtb == null || dtb.getBulletin() == null)
				continue;

			for (Periode p : periodes) {
				if (p == null || dtb.getBulletin().getPeriodeId() == null)
					continue;

				if (dtb.getBulletin().getPeriodeId().equals(p.getId())) {
					if (dtb.getIsRanked() != null && dtb.getIsRanked().equals(Constants.OUI)) {
						double coefPeriode = parseCoef(p.getCoef());

						if (p.getIsfinal() != null && p.getIsfinal().equals("O")) {
							// COPIE EXACTE DE LA LOGIQUE ORIGINALE
							for (Map.Entry<EcoleHasMatiere, List<Notes>> entry : me.getNotesMatiereMap().entrySet()) {
								if (entry.getKey().getId().equals(cml.getMatiere().getId())) {
									moyMatiereAnnuelle = moyMatiereAnnuelle + entry.getKey().getMoyenne() * coefPeriode;
								}
							}
						} else {
							moyMatiereAnnuelle = moyMatiereAnnuelle + dtb.getMoyenne() * coefPeriode;
						}
						coef = coef + coefPeriode;
					}
					break;
				}
			}
		}

		// Mise à jour de la moyenne matière - LOGIQUE EXACTE DE L'ORIGINAL
		if (detailsBulletinsElevesList != null && !detailsBulletinsElevesList.isEmpty()) {
			moyMatiereAnnuelle = CommonUtils.roundDouble(moyMatiereAnnuelle / (coef == 0.0 ? 1.0 : coef), 2);

			// COPIE EXACTE DE LA LOGIQUE ORIGINALE
			for (Map.Entry<EcoleHasMatiere, List<Notes>> entry : me.getNotesMatiereMap().entrySet()) {
				if (entry.getKey().getId().equals(cml.getMatiere().getId())) {
					entry.getKey().setMoyenneAnnuelle(moyMatiereAnnuelle);

					if (classeurAnnuelMatiereMap.containsKey(cml.getMatiere().getId())) {
						classeurAnnuelMatiereMap.get(cml.getMatiere().getId()).add(moyMatiereAnnuelle);
					} else {
						List<Double> classeurMatiereTemp = new ArrayList<>();
						classeurMatiereTemp.add(moyMatiereAnnuelle);
						classeurAnnuelMatiereMap.put(cml.getMatiere().getId(), classeurMatiereTemp);
					}
				}
			}
		}
	}

	/**
	 * Classement final avec gestion robuste et méthode getRangByValue pour le super
	 * français
	 */
	private void performFinalRanking(List<MoyenneEleveDto> moyEleve, List<Double> classeurAnnuelList,
			List<Double> classeurAnnuelSuperFrList, Map<Long, List<Double>> classeurAnnuelMatiereMap) {

		// Tri des listes de classement par matière
		for (Map.Entry<Long, List<Double>> entry : classeurAnnuelMatiereMap.entrySet()) {
			if (entry.getValue() != null) {
				Collections.sort(entry.getValue());
				Collections.reverse(entry.getValue());
			}
		}

		// Tri descendant de la liste super français
		if (!classeurAnnuelSuperFrList.isEmpty()) {
			classeurAnnuelSuperFrList = classeurAnnuelSuperFrList.stream().sorted(Comparator.reverseOrder())
					.collect(Collectors.toList());
		}

		// Tri de la liste principale
		Collections.sort(classeurAnnuelList);
		Collections.reverse(classeurAnnuelList);

		// Attribution des rangs
		for (MoyenneEleveDto me : moyEleve) {
			if (me == null || me.getNotesMatiereMap() == null)
				continue;

			// Classement annuel des élèves par matière
			for (EcoleHasMatiere matEleve : me.getNotesMatiereMap().keySet()) {
				if (matEleve != null && classeurAnnuelMatiereMap.containsKey(matEleve.getId())) {
					List<Double> classeurMatiere = classeurAnnuelMatiereMap.get(matEleve.getId());
					if (classeurMatiere != null && matEleve.getMoyenneAnnuelle() != null) {
						matEleve.setRangAnnuel(
								String.valueOf(getRangByValue(classeurMatiere, matEleve.getMoyenneAnnuelle())));
					}
				}
			}

			// Rang super français - CORRECTION: utilisation de getRangByValue au lieu
			// d'indexOf
			if (!classeurAnnuelSuperFrList.isEmpty() && me.getMoyAnFr() != null) {
				me.setRangAnFr(getRangByValue(classeurAnnuelSuperFrList, me.getMoyAnFr()));
			}

			// Rang annuel général
			if (me.getMoyenneAnnuelle() != null) {
				me.setRangAnnuel(String.valueOf(getRangByValue(classeurAnnuelList, me.getMoyenneAnnuelle())));
			}
		}
	}

	/**
	 * Méthode utilitaire pour obtenir le rang basé sur la valeur Gère correctement
	 * les ex-aequo (A revoir by steph)
	 */
	private int getRangByValue_(List<Double> classeurList, Double valeur) {
		if (classeurList == null || classeurList.isEmpty() || valeur == null) {
			return 1;
		}

		int rang = 1;
		for (Double val : classeurList) {
			if (val == null)
				continue;

			if (val.compareTo(valeur) > 0) {
				rang++;
			} else if (val.compareTo(valeur) == 0) {
				return rang;
			}
		}

		return rang;
	}

	/**
	 * METHODE D'OPTIMISATION A INTEGRER AU CALCUL ANNUEL POUR OPTIMISER LES BOUCLES
	 * (A VOIR)
	 */

	private void processMatieresOptimized(MoyenneEleveDto me, List<ClasseMatiere> cmList, List<Periode> periodes,
			Map<Long, List<Double>> classeurAnnuelMatiereMap) {
		if (cmList == null || cmList.isEmpty() || me.getNotesMatiereMap() == null)
			return;

// Pré-chargement optimisé : groupement par matière puis par période
		Map<Long, Map<Long, List<DetailBulletin>>> detailsGrouped = new HashMap<>();

		for (ClasseMatiere cm : cmList) {
			if (cm == null || cm.getMatiere() == null)
				continue;

			try {
				List<DetailBulletin> details = detailBulletinService.getDetailBulletinsEleveByAnneeAndClasseAndMatiere(
						me.getAnnee().getId(), me.getClasse().getId(), me.getEleve().getMatricule(),
						cm.getMatiere().getId());

				Map<Long, List<DetailBulletin>> byPeriode = details != null ? details.stream()
						.filter(d -> d != null && d.getBulletin() != null && d.getBulletin().getPeriodeId() != null)
						.collect(Collectors.groupingBy(d -> d.getBulletin().getPeriodeId())) : new HashMap<>();

				detailsGrouped.put(cm.getMatiere().getId(), byPeriode);
			} catch (Exception e) {
				logger.warning("Erreur lors du chargement des détails bulletins pour la matière "
						+ cm.getMatiere().getId() + ": " + e.getMessage());
				detailsGrouped.put(cm.getMatiere().getId(), new HashMap<>());
			}
		}

// Calcul des moyennes par matière avec logique exacte de l'original
		for (ClasseMatiere cml : cmList) {
			if (cml == null || cml.getMatiere() == null)
				continue;

			double coefTotal = 0.0;
			double sommePonderee = 0.0;

			Map<Long, List<DetailBulletin>> detailsPeriode = detailsGrouped.getOrDefault(cml.getMatiere().getId(),
					new HashMap<>());

// Parcours optimisé avec accès direct par période
			for (Periode p : periodes) {
				if (p == null)
					continue;

				List<DetailBulletin> dtbs = detailsPeriode.getOrDefault(p.getId(), new ArrayList<>());
				double coefP = parseCoef(p.getCoef());

				for (DetailBulletin dtb : dtbs) {
					if (Constants.OUI.equals(dtb.getIsRanked())) {

						if (p.getIsfinal() != null && p.getIsfinal().equals("O")) {
// LOGIQUE EXACTE DE L'ORIGINAL - recherche dans getNotesMatiereMap
							for (Map.Entry<EcoleHasMatiere, List<Notes>> entry : me.getNotesMatiereMap().entrySet()) {
								if (entry.getKey().getId().equals(cml.getMatiere().getId())) {
									sommePonderee += entry.getKey().getMoyenne() * coefP;
									coefTotal += coefP;
									break; // Important : sortir après avoir trouvé
								}
							}
						} else {
							sommePonderee += dtb.getMoyenne() * coefP;
							coefTotal += coefP;
						}
					}
				}
			}

// Mise à jour uniquement si on a des données
			if (coefTotal > 0) {
				double moyenneAnnuelle = CommonUtils.roundDouble(sommePonderee / coefTotal, 2);

// LOGIQUE EXACTE DE L'ORIGINAL - mise à jour dans getNotesMatiereMap
				for (Map.Entry<EcoleHasMatiere, List<Notes>> entry : me.getNotesMatiereMap().entrySet()) {
					if (entry.getKey().getId().equals(cml.getMatiere().getId())) {
						entry.getKey().setMoyenneAnnuelle(moyenneAnnuelle);

// Mise à jour du classeur avec la même logique que l'original
						if (classeurAnnuelMatiereMap.containsKey(cml.getMatiere().getId())) {
							classeurAnnuelMatiereMap.get(cml.getMatiere().getId()).add(moyenneAnnuelle);
						} else {
							List<Double> classeurMatiereTemp = new ArrayList<>();
							classeurMatiereTemp.add(moyenneAnnuelle);
							classeurAnnuelMatiereMap.put(cml.getMatiere().getId(), classeurMatiereTemp);
						}
						break; // Important : sortir après avoir trouvé
					}
				}
			}
		}
	}

	/**
	 * Version alternative encore plus optimisée avec pré-indexation
	 */
	private void processMatieresOptimizedV2(MoyenneEleveDto me, List<ClasseMatiere> cmList, List<Periode> periodes,
			Map<Long, List<Double>> classeurAnnuelMatiereMap) {
		if (cmList == null || cmList.isEmpty() || me.getNotesMatiereMap() == null)
			return;

// Pré-indexation des matières de l'élève pour éviter les boucles répétées
		Map<Long, EcoleHasMatiere> matiereIndex = me.getNotesMatiereMap().keySet().stream()
				.collect(Collectors.toMap(EcoleHasMatiere::getId, Function.identity()));

// Pré-chargement groupé par matière et période
		Map<Long, Map<Long, List<DetailBulletin>>> detailsGrouped = cmList.stream()
				.filter(cm -> cm != null && cm.getMatiere() != null)
				.collect(Collectors.toMap(cm -> cm.getMatiere().getId(), cm -> {
					try {
						List<DetailBulletin> details = detailBulletinService
								.getDetailBulletinsEleveByAnneeAndClasseAndMatiere(me.getAnnee().getId(),
										me.getClasse().getId(), me.getEleve().getMatricule(), cm.getMatiere().getId());

						return details != null
								? details.stream().filter(d -> d != null && d.getBulletin() != null)
										.collect(Collectors.groupingBy(d -> d.getBulletin().getPeriodeId()))
								: new HashMap<Long, List<DetailBulletin>>();
					} catch (Exception e) {
						logger.warning("Erreur chargement détails matière " + cm.getMatiere().getId());
						return new HashMap<Long, List<DetailBulletin>>();
					}
				}));

// Traitement optimisé avec accès direct par index
		for (ClasseMatiere cml : cmList) {
			if (cml == null || cml.getMatiere() == null)
				continue;

			Long matiereId = cml.getMatiere().getId();
			EcoleHasMatiere ecoleMatiere = matiereIndex.get(matiereId);
			if (ecoleMatiere == null)
				continue;

			double coefTotal = 0.0;
			double sommePonderee = 0.0;

			Map<Long, List<DetailBulletin>> detailsPeriode = detailsGrouped.getOrDefault(matiereId, new HashMap<>());

			for (Periode p : periodes) {
				if (p == null)
					continue;

				List<DetailBulletin> dtbs = detailsPeriode.getOrDefault(p.getId(), new ArrayList<>());
				double coefP = parseCoef(p.getCoef());

				for (DetailBulletin dtb : dtbs) {
					if (Constants.OUI.equals(dtb.getIsRanked())) {
						double note = (p.getIsfinal() != null && p.getIsfinal().equals("O")) ? ecoleMatiere.getMoyenne()
								: dtb.getMoyenne();
						sommePonderee += note * coefP;
						coefTotal += coefP;
					}
				}
			}

			if (coefTotal > 0) {
				double moyenneAnnuelle = CommonUtils.roundDouble(sommePonderee / coefTotal, 2);
				ecoleMatiere.setMoyenneAnnuelle(moyenneAnnuelle);
				classeurAnnuelMatiereMap.computeIfAbsent(matiereId, k -> new ArrayList<>()).add(moyenneAnnuelle);
			}
		}
	}

}

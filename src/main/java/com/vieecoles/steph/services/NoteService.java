package com.vieecoles.steph.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.vieecoles.steph.dto.MoyenneEleveDto;
import com.vieecoles.steph.entities.AnneeScolaire;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.ClasseEleve;
import com.vieecoles.steph.entities.ClasseEleveMatiere;
import com.vieecoles.steph.entities.ClasseElevePeriode;
import com.vieecoles.steph.entities.ClasseMatiere;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.Eleve;
import com.vieecoles.steph.entities.Evaluation;
import com.vieecoles.steph.entities.Matiere;
import com.vieecoles.steph.entities.Notes;
import com.vieecoles.steph.entities.Periode;
import com.vieecoles.steph.util.CommonUtils;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
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

	Logger logger = Logger.getLogger(NoteService.class.getName());

	Gson gson = new Gson();

	public List<Notes> getList() {
		return Notes.listAll();
	}

	public Notes findById(Long id) {
		return Notes.findById(id);
	}

	@Transactional
	public Response create(Notes notes) {
		logger.info("--> Création de note pour " + notes.getClasseEleve().getInscription().getEleve().getNom());
		// pec dans le calcul de moyenne par defaut
		notes.setPec(1);
		notes.persist();
		return Response.created(URI.create("/notes/" + notes.getId())).build();
	}

	@Transactional
	public Notes update(Notes notes) {
		Notes entity = Notes.findById(notes.getId());
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.setClasseEleve(notes.getClasseEleve());
		entity.setEvaluation(notes.getEvaluation());
		System.out.println("Note --> " + notes.getNote());
		System.out.println("Note --> " + notes.getNote().toString());
		entity.setNote(notes.getNote());
		return entity;
	}

	@Transactional
	public void delete(long id) {
		Notes entity = Notes.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}
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

			}
			if (note.getId() != 0 && note.getStatut() != null && note.getStatut().equals("M")) {
				logger.info(gson.toJson(note));
				logger.info("--> Maj de note ...");
				update(note);
			}
		}
	}

	public List<Notes> getByEvaluation(long evaluationId) {
		return Notes.find("evaluation.id = ?1", evaluationId).list();
	}

	public List<Notes> getNotesClasse(String evalCode) {
//	    	logger.info("++++++++++"+evalCode);
		Evaluation evaluation = evaluationService.findByCode(evalCode);
		// Gson gson = new Gson();
		// obtenir la liste des eleves d une classe
		List<ClasseEleve> classeEleves = classeEleveService.getByClasseAnnee(evaluation.getClasse().getId(),
				evaluation.getAnnee().getId());
		// logger.info(gson.toJson(classeEleves));
		// obtenir la liste des notes pour une évaluation
		List<Notes> notesList = getByEvaluation(evaluation.getId());
		List<Notes> noteListTemp = new ArrayList<Notes>();
		Boolean flat = true;
		Notes notemp;
		for (ClasseEleve ce : classeEleves) {
			flat = true;
			for (Notes note : notesList) {
				if (ce.getInscription().getEleve().getMatricule()
						.equals(note.getClasseEleve().getInscription().getEleve().getMatricule())) {
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
	}

	List<MoyenneEleveDto> formatMoyenneMatieres(Map<Eleve, List<Notes>> param) {

		return null;
	}

	void classement() {

	}

	public List<MoyenneEleveDto> moyennesAndNotesHandle(String classeId, String anneeId, String periodeId) {
		// Obtenir la liste des evaluations dans une classe au cours de l année pour une
		// période
		logger.info("---> Processus de Calcul des moyennes des éleves d une classe");
		try {
			Map<Eleve, List<Notes>> noteGroup = new HashMap<Eleve, List<Notes>>();
			Parameters params = Parameters.with("classeId", Long.parseLong(classeId))
					.and("anneeId", Long.parseLong(anneeId)).and("periodeId", Long.parseLong(periodeId));
			String criteria = "classe.id = :classeId and annee.id = :anneeId and periode.id = :periodeId";
			List<Evaluation> evalList = evaluationService.search(criteria, params);
			List<Notes> noteList = new ArrayList<Notes>();
			List<Notes> notesTemp;
			List<MoyenneEleveDto> moyenneList = new ArrayList<MoyenneEleveDto>();
			Map<Matiere, List<Notes>> notesMatiereGroup;
			Classe classe;
			MoyenneEleveDto moyenneEleveDto;
			Gson g = new Gson();
			// pour chaque évaluation avoir la liste des notes des élèves
			for (Evaluation ev : evalList) {
				logger.info(ev.getPec().toString());
				noteList.addAll(getNotesClasse(ev.getCode()));
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
			classe = classeService.findById(Long.parseLong(classeId));
//		logger.info(g.toJson(classe));

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
				notesMatiereGroup = new HashMap<Matiere, List<Notes>>();
				Matiere matiereTemp;
				List<String> filter = new ArrayList<>();
//			Gson g = new Gson();
//			logger.info(String.format("Eleve - %s - %s", entry.getKey().getMatricule(),entry.getKey().getNom()));
				for (Notes note : entry.getValue()) {
					logger.info("note id --->" + note.getId());
					if (note.getId() != 0) {
						if (filter.contains(note.getEvaluation().getMatiere().getCode())) {
//					if (notesMatiereGroup.containsKey(note.getEvaluation().getMatiere())) {
							logger.info("**** upd ****>" + note.getEvaluation().getMatiere().getCode());
//						logger.info(g.toJson(notesMatiereGroup));
//						logger.info("----------");
//						logger.info(g.toJson(filter));
							notesMatiereGroup.get(note.getEvaluation().getMatiere()).add(note);
//						System.out.println("----------------------------");
//						System.out.println(g.toJson(note.getEvaluation().getMatiere()));
//						logger.info(String.format("%s - %s - %s", entry.getKey().getMatricule(),note.getEvaluation().getMatiere(),note.getNote()));
						} else {
							logger.info("<*** new *****" + note.getEvaluation().getMatiere().getCode());
							notesTemp = new ArrayList<Notes>();
							notesTemp.add(note);
							filter.add(note.getEvaluation().getMatiere().getCode());
//						logger.info("ref mtiere"+note.getEvaluation().getMatiere().hashCode());
							/*
							 * Mettre a jour matiereTemp si la structure de Matiere évolue
							 */

							matiereTemp = new Matiere();
							matiereTemp.setId(note.getEvaluation().getMatiere().getId());
							matiereTemp.setCode(note.getEvaluation().getMatiere().getCode());
							matiereTemp.setPec(note.getEvaluation().getMatiere().getPec());
							matiereTemp.setLibelle(note.getEvaluation().getMatiere().getLibelle());
							matiereTemp.setMoyenne(note.getEvaluation().getMatiere().getMoyenne());
							matiereTemp
									.setNiveauEnseignement(note.getEvaluation().getMatiere().getNiveauEnseignement());
							matiereTemp.setCategorie(note.getEvaluation().getMatiere().getCategorie());
							matiereTemp.setNumOrdre(note.getEvaluation().getMatiere().getNumOrdre());
							matiereTemp.setMatiereParent(note.getEvaluation().getMatiere().getMatiereParent());
							matiereTemp.setBonus(note.getEvaluation().getMatiere().getBonus());
//						System.out.println(g.toJson(matiereTemp));
//						System.out.println("----------------------");
//						System.out.println(g.toJson(note.getEvaluation().getMatiere()));
							/**
							 * ***********************************************
							 */

							notesMatiereGroup.put(matiereTemp, notesTemp);
							logger.info(String.format("%s - %s - %s", entry.getKey().getMatricule(),
									note.getEvaluation().getMatiere(), note.getNote()));
							;

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
//		logger.info(moyenneList.toString());
//		logger.info("-------------------------------------------");
			classementEleveParMatiere(calculMoyenneMatiere(moyenneList), classe.getBranche().getId());
			calculMoyenneGeneralEleve(moyenneList);
			Collections.sort(moyenneList);
//	    logger.info(g.toJson(moyenneList));
			return moyenneList;
		} catch (RuntimeException r) {
			r.printStackTrace();
			return new ArrayList<MoyenneEleveDto>();
		}
	}

	List<MoyenneEleveDto> calculMoyenneMatiere(List<MoyenneEleveDto> moyEleve) {
		logger.info("---> Calcul des moyennes par matiere");
		Double moyenne;
		List<Double> noteList;

		Double diviser;
		Double somme;

		Gson g = new Gson();

//		System.out.println(g.toJson(moyEleve));
		for (MoyenneEleveDto me : moyEleve) {

			for (Map.Entry<Matiere, List<Notes>> entry : me.getNotesMatiereMap().entrySet()) {
				moyenne = 0.0;
				noteList = new ArrayList<Double>();

				diviser = 0.0;
				somme = 0.0;
				for (Notes note : entry.getValue()) {
// On vérifie que l'evaluation et la note sont prises en compte dans le calcul de moyenne
					if (note.getEvaluation().getPec() == 1 && note.getPec() != null && note.getPec() == 1) {
						noteList.add(note.getNote());
//						System.out.println(String.format("PEC value >>> %s", note.getEvaluation().getPec()));
						diviser = diviser
								+ (Double.parseDouble(note.getEvaluation().getNoteSur()) / Double.parseDouble("20"));
					}
				}

//				entry.getValue().clear();
//				entry.getValue().

				for (Double note : noteList)
					somme += note;

				moyenne = somme / (diviser.equals(Double.parseDouble("0")) ? Double.parseDouble("1") : diviser);
				logger.info("Moyenne = " + somme + " / " + diviser + " = " + CommonUtils.roundDouble(moyenne, 2));
				entry.getKey().setMoyenne(CommonUtils.roundDouble(moyenne, 2));
				entry.getKey().setAppreciation(appreciation(moyenne));
//				logger.info("++++> "+g.toJson(me.getNotesMatiereMap()));
			}

//			me.setMoyenne(calculMoyenneGeneralWithCoef(moyenneList));
		}
//		logger.info("++++> "+g.toJson(moyEleve));
		return moyEleve;
	}

	String appreciation(Double moyenne) {
		String apprec = "";
		if (moyenne >= 17.0)
			apprec = "Excellent";
		else if (moyenne >= 16.0)
			apprec = "Très Bien";
		else if (moyenne >= 14.0)
			apprec = "Bien";
		else if (moyenne >= 12.0)
			apprec = "Assez Bien";
		else if (moyenne >= 10.0)
			apprec = "Passable";
		else if (moyenne >= 8.0)
			apprec = "Insuffisance";
		else if (moyenne >= 6.0)
			apprec = "Faible";
		else
			apprec = "Très Faible";

		return apprec;
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

	// Calcul de moyenne generale de l eleve et calcul de rang
	void calculMoyenneGeneralEleve(List<MoyenneEleveDto> moyEleve) {
		logger.info("---> Calcul de moyenne generale de l eleve et calcul de rang");
		Double moy = 0.0;
		Double moyPond = 0.0;
		Double moyTotPond = 0.0;
		Double coef = 0.0;
		Double coefTot = 0.0;
		Double moyGenPond = 0.0;
		BigDecimal moyBD;
		Map<Double, MoyenneEleveDto> classementMap = new HashMap<Double, MoyenneEleveDto>();
		List<MoyenneEleveDto> classement = new ArrayList<MoyenneEleveDto>();
		// Calcul des moyennes generales par eleves
		for (MoyenneEleveDto eleve : moyEleve) {
			
			ClasseElevePeriode cep = classeElevePeriodeService.findByClasseAndEleveAndAnneeAndPeriode(
					eleve.getClasse().getId(), eleve.getEleve().getId(), eleve.getAnnee().getId(), eleve.getPeriode().getId());
			if (cep == null)
				eleve.setIsClassed(Constants.OUI);
			else
				eleve.setIsClassed(cep.getIsClassed());
			
			for (Matiere matiere : eleve.getNotesMatiereMap().keySet()) {
//				System.out.println(matiere);
				// Vérifier si la note doit être prise en compte et si l'élève est classé dans la matière concernée ou même pour la période
				if (matiere.getPec() == 1 && !matiere.getEleveMatiereIsClassed().equals(Constants.NON) && !eleve.getIsClassed().equals(Constants.NON)) {
					moy = matiere.getMoyenne();
					logger.info(matiere + "+++");
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
				} else if (matiere.getBonus() != null && matiere.getBonus() == 1 && !matiere.getEleveMatiereIsClassed().equals("N") && !eleve.getIsClassed().equals("N")) {
					moy = matiere.getMoyenne();
					logger.info(matiere + "+++ is matiere bonus");
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
				}else {
					moy = matiere.getMoyenne();
					logger.info("+++ eleve non classe dans cette matiere :"+matiere.getLibelle());
					coef = Double.parseDouble(matiere.getCoef() == null ? "1" : matiere.getCoef());
					if (matiere.getCoef() == null) {
						matiere.setCoef("1");
					}	
					if(matiere.getBonus() != null && matiere.getBonus() == 1) {
						if (moy > 10)
							moyPond = moy - 10;
						else
							moyPond = 0.0;
					}else
						moyPond = (moy * coef);
					matiere.setMoyenne(CommonUtils.roundDouble(moyPond, 2));
				}
			}
			if (coefTot == 0.0)
				coefTot = 1.0;

			logger.info(String.format("moy Tot pondere   %s ---- coefTot %s  ", moyTotPond, coefTot));
			moyGenPond = moyTotPond / coefTot;
			moyBD = new BigDecimal(moyGenPond).setScale(2, RoundingMode.HALF_UP);
			eleve.setMoyenne(moyBD.doubleValue());
			logger.info("Moyenne Generale :" + eleve.getMoyenne());
			eleve.setAppreciation(appreciation(moyGenPond));
			classementMap.put(moyGenPond, eleve);
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
	}

	void classementEleveParMatiere(List<MoyenneEleveDto> moyEleve, Long brancheId) {
		logger.info("---> Classement des eleves par matiere");
		List<ClasseMatiere> classeMatList = ClasseMatiere.find("branche.id = ?1", brancheId).list();
		Map<Double, MoyenneEleveDto> mapt;

		int i = 0;
		for (ClasseMatiere cm : classeMatList) {
			mapt = new HashMap<Double, MoyenneEleveDto>();
			for (MoyenneEleveDto me : moyEleve) {
				logger.info("eleve - " + me.getEleve().getNom());
				if (me.getNotesMatiereMap() != null) {
					for (Map.Entry<Matiere, List<Notes>> map : me.getNotesMatiereMap().entrySet()) {
						if (map.getKey().getCode().equals(cm.getMatiere().getCode())) {
							mapt.put(map.getKey().getMoyenne(), me);
							
							logger.info(String.format("--- %s - %s - %s - %s - %s ---", me.getClasse().getId(), cm.getMatiere().getId(), me.getEleve().getId(),
									me.getAnnee().getId(), me.getPeriode().getId()));
							
							ClasseEleveMatiere cem = classeEleveMatiereService.findByClasseAndMatiereAndEleveAndAnneeAndPeriode(
									me.getClasse().getId(), cm.getMatiere().getId(), me.getEleve().getId(),
									me.getAnnee().getId(), me.getPeriode().getId());
							if (cem != null && cem.getIsClassed().equals(Constants.NON)) {
								logger.info("---> cem = "+cem.getIsClassed());
								me.setMoyenneMatiereToSort(Constants.MOYENNE_ELEVE_NON_CLASSE_MATIERE);
								map.getKey().setEleveMatiereIsClassed(Constants.NON);
							}else {
								me.setMoyenneMatiereToSort(map.getKey().getMoyenne());
								map.getKey().setEleveMatiereIsClassed(Constants.OUI);
							}
							
							logger.info("Matiere : " + map.getKey().getLibelle() + " " + map.getKey().getMoyenne());
							break;
						}
					}

				}
			}
// Tri de la liste de eleve par rapport à la moyenne
			Collections.sort(moyEleve, new Comparator<MoyenneEleveDto>() {
				@Override
				public int compare(MoyenneEleveDto u1, MoyenneEleveDto u2) {
					if (u1.getMoyenneMatiereToSort() == null || u2.getMoyenneMatiereToSort() == null)
						return 0;
					return u2.getMoyenneMatiereToSort().compareTo(u1.getMoyenneMatiereToSort());
				}
			});
// variable pour la gestion des rang et prise encompte des exécos
			i = 0;
			int j = 0;
			Double moyennePrecedente = (double) -1;
			for (MoyenneEleveDto me : moyEleve) {

				for (Map.Entry<Matiere, List<Notes>> map : me.getNotesMatiereMap().entrySet()) {
					if (map.getKey().getCode().equals(cm.getMatiere().getCode())) {
						logger.info("matiere code - " + map.getKey().getCode() + " --- " + cm.getCoef());
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

	void classementElevePeriode(List<MoyenneEleveDto> eleves) {

		Collections.sort(eleves, new Comparator<MoyenneEleveDto>() {
			@Override
			public int compare(MoyenneEleveDto u1, MoyenneEleveDto u2) {
				if (u1.getMoyenne() == null || u2.getMoyenne() == null)
					return 0;
				return u2.getMoyenne().compareTo(u1.getMoyenne());
			}
		});

		int i = 0;
		Double moyennePrecedente = (double) -1;
		for (MoyenneEleveDto eleve : eleves) {
			i++;
			if (eleve.getMoyenne().equals(moyennePrecedente))
				eleve.setRang(String.valueOf(i - 1));
			else
				eleve.setRang(String.valueOf(i));
		}

	}
}

package com.vieecoles.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
//import com.google.gson.Gson;
import com.vieecoles.comparators.ValueEleveComparator;
import com.vieecoles.dto.MoyenneEleveDto;
import com.vieecoles.entities.Classe;
import com.vieecoles.entities.ClasseEleve;
import com.vieecoles.entities.ClasseMatiere;
import com.vieecoles.entities.Eleve;
import com.vieecoles.entities.Evaluation;
import com.vieecoles.entities.Matiere;
import com.vieecoles.entities.Notes;

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

	public List<Notes> getList() {
		return Notes.listAll();
	}

	public Notes findById(Long id) {
		return Notes.findById(id);
	}

	@Transactional
	public Response create(Notes notes) {
		notes.persist();
		return Response.created(URI.create("/notes/" + notes.getId())).build();
	}

	@Transactional
	public Notes update(Notes notes) {
		Notes entity = Notes.findById(notes.getId());
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.setEleve(notes.getEleve());
		entity.setEvaluation(notes.getEvaluation());
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
//				System.out.println("classe apres le get");;
//				System.out.println(new Gson().toJson(cl));
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
//	    	Gson gson = new Gson();
		for (Notes note : noteList) {
			if (note.getId() == 0 && note.getStatut() != null && note.getStatut().equals("M")) {
//	    			System.out.println(gson.toJson(note));
				System.out.println("--> Creation de note ...");
				create(note);

			}
			if (note.getId() != 0 && note.getStatut() != null && note.getStatut().equals("M")) {
//	    			System.out.println(gson.toJson(note));
				System.out.println("--> Maj de note ...");
				update(note);
			}
		}
	}

	public List<Notes> getByEvaluation(long evaluationId) {
		return Notes.find("evaluation.id = ?1", evaluationId).list();
	}

	public List<Notes> getNotesClasse(String evalCode) {
//	    	System.out.println("++++++++++"+evalCode);
		Evaluation evaluation = evaluationService.findByCode(evalCode);
		// Gson gson = new Gson();
		// obtenir la liste des eleves d une classe
		List<ClasseEleve> classeEleves = classeEleveService.getByClasseAnnee(evaluation.getClasse().getId(), evaluation.getAnnee().getId());
		// System.out.println(gson.toJson(classeEleves));
		// obtenir la liste des notes pour une évaluation
		List<Notes> notesList = getByEvaluation(evaluation.getId());
		List<Notes> noteListTemp = new ArrayList<Notes>();
		Boolean flat = true;
		Notes notemp;
		for (ClasseEleve ce : classeEleves) {
			flat = true;
			for (Notes note : notesList) {
				if (ce.getEleve().getMatricule().equals(note.getEleve().getMatricule())) {
					noteListTemp.add(note);
					flat = false;
					break;
				}
			}
			if (flat) {
				notemp = new Notes();
				notemp.setEleve(ce.getEleve());
				notemp.setEvaluation(evaluation);
				noteListTemp.add(notemp);
			}
		}

		return noteListTemp;
	}

	List<MoyenneEleveDto> formatMoyenneMatieres(Map<Eleve, List<Notes>> param) {

		return null;
	}

	void classement() {

	}

	public List<MoyenneEleveDto> moyennesAndNotesHandle(String classeId,String anneeId, String periodeId) {
		// Obtenir la liste des evaluations dans une classe au cours de l année pour une
		// période
		System.out.println("---> Processus de Calcul des myennes des éleves d une classe");

		Map<Eleve, List<Notes>> noteGroup = new HashMap<Eleve, List<Notes>>();
		Parameters params = Parameters.with("classeId", Long.parseLong(classeId)).and("anneeId",
				Long.parseLong(anneeId)).and("periodeId", Long.parseLong(periodeId));
		String query = "classe.id = :classeId and annee.id = :anneeId and periode.id = :periodeId";
		List<Evaluation> evalList = evaluationService.search(query, params);
		List<Notes> noteList = new ArrayList<Notes>();
		List<Notes> notesTemp;
		List<MoyenneEleveDto> moyenneList = new ArrayList<MoyenneEleveDto>();
		Map<Matiere, List<Notes>> notesMatiereGroup;
		Classe classe;
		MoyenneEleveDto moyenneEleveDto;
//		Gson g = new Gson();
		// pour chaque évaluation avoir la liste des notes des élèves
		for (Evaluation ev : evalList) {
//			System.out.println(ev.getCode());
			noteList.addAll(getNotesClasse(ev.getCode()));
		}
//		System.out.println("note size " + noteList.size());
		for (Notes note : noteList) {
			if (noteGroup.containsKey(note.getEleve())) {
//				System.out.println("**** upd ****>");
				noteGroup.get(note.getEleve()).add(note);
			} else {
//				System.out.println("<**** new ****");
				notesTemp = new ArrayList<Notes>();
				notesTemp.add(note);
				noteGroup.put(note.getEleve(), notesTemp);
			}
		}
		classe = classeService.findById(Long.parseLong(classeId));
//		System.out.println(g.toJson(classe));

		// Formatage des dto
		for (Map.Entry<Eleve, List<Notes>> entry : noteGroup.entrySet()) {
			moyenneEleveDto = new MoyenneEleveDto();
			moyenneEleveDto.setEleve(entry.getKey());
			moyenneEleveDto.setClasse(classe);
			notesMatiereGroup = new HashMap<Matiere, List<Notes>>();
			Matiere matiereTemp;
//			System.out.println(String.format("Eleve - %s - %s", entry.getKey().getMatricule(),entry.getKey().getNom()));
			for (Notes note : entry.getValue()) {
//				System.out.println("note id --->"+note.getId());
				if (note.getId() != 0) {
					if (notesMatiereGroup.containsKey(note.getEvaluation().getMatiere())) {
//						System.out.println("**** upd ****>"+note.getEvaluation().getMatiere().getCode());
						notesMatiereGroup.get(note.getEvaluation().getMatiere()).add(note);
//						System.out.println(String.format("%s - %s - %s", entry.getKey().getMatricule(),note.getEvaluation().getMatiere(),note.getNote()));
//						System.out.println(g.toJson(notesMatiereGroup));
					} else {
//						System.out.println("<*** new *****"+note.getEvaluation().getMatiere().getCode());
						notesTemp = new ArrayList<Notes>();
						notesTemp.add(note);
//						System.out.println("ref mtiere"+note.getEvaluation().getMatiere().hashCode());
						matiereTemp = new Matiere();
						matiereTemp.setId(note.getEvaluation().getMatiere().getId());
						matiereTemp.setCode(note.getEvaluation().getMatiere().getCode());
//						matiereTemp.setCoef(note.getEvaluation().getMatiere().getCoef());
						matiereTemp.setLibelle(note.getEvaluation().getMatiere().getLibelle());
						matiereTemp.setMoyenne(note.getEvaluation().getMatiere().getMoyenne());

						notesMatiereGroup.put(matiereTemp, notesTemp);
						System.out.println(String.format("%s - %s - %s", entry.getKey().getMatricule(),note.getEvaluation().getMatiere(),note.getNote()));;

//						System.out.println(g.toJson(notesMatiereGroup));
					}
				}
			}
			moyenneEleveDto.setNotesMatiereMap(notesMatiereGroup);
//			moyenneEleveDto.setMatiere(null);
//			moyenneEleveDto.setNotes(entry.getValue());
			moyenneList.add(moyenneEleveDto);
		}
//		calculMoyenneMatiere(moyenneList);
//		System.out.println(g.toJson(""));
//		System.out.println("-------------------------------------------");
		classementEleveParMatiere(calculMoyenneMatiere(moyenneList), classe.getBranche().getId());
		calculMoyenneGeneralEleve(moyenneList);
		Collections.sort(moyenneList);
//	    System.out.println(g.toJson(moyenneList));
		return moyenneList;
	}

	List<MoyenneEleveDto> calculMoyenneMatiere(List<MoyenneEleveDto> moyEleve) {
		System.out.println("---> Calcul des moyennes par matiere");
		Double moyenne;
		Gson g = new Gson();
		List<Double> noteList;

		Double diviser;
		Double somme;
		for (MoyenneEleveDto me : moyEleve) {

			for (Map.Entry<Matiere, List<Notes>> entry : me.getNotesMatiereMap().entrySet()) {
				moyenne = 0.0;
				noteList = new ArrayList<Double>();
				diviser = 0.0;
				somme = 0.0;
				for (Notes note : entry.getValue()) {
					noteList.add(Double.parseDouble(note.getNote()));
					diviser = diviser
							+ (Double.parseDouble(note.getEvaluation().getNoteSur()) / Double.parseDouble("20"));
				}

				for (Double note : noteList)
					somme += note;

				moyenne = somme / diviser;
				System.out.println("Moyenne = " + somme + " / " + diviser + " = " + moyenne);
				entry.getKey().setMoyenne(moyenne);
				entry.getKey().setAppreciation(appreciation(moyenne));

			}

//			me.setMoyenne(calculMoyenneGeneralWithCoef(moyenneList));
		}
//		System.out.println("++++> "+g.toJson(moyEleve));
		return moyEleve;
	}

	String appreciation(Double moyenne) {
		String apprec = "";
		if (moyenne >= 17.0)
			apprec = "TB"; // Très Bien
		else if (moyenne >= 13)
			apprec = "B"; // Bien
		else if (moyenne >= 9)
			apprec = "M"; // Moyen
		else if (moyenne >= 6)
			apprec = "I"; // Insuffisant
		else
			apprec = "TI"; // Très Insuffisant

		return apprec;
	}

	Double calculMoyenneGeneralWithCoef(Map<Double, String> moyennes) {
		System.out.println("---> Calcul de la moyenne generale avec les coeficients");
		Double moyPond = 0.0;
		Double coefTot = 0.0;
		Double moyGenPond = 0.0;
		for (Map.Entry<Double, String> entry : moyennes.entrySet()) {
			System.out.println(String.format("moy %s --- coef %s", entry.getKey(), entry.getValue()));
			moyPond += entry.getKey();
			coefTot += Double.parseDouble(entry.getValue());
		}
		moyGenPond = moyPond / coefTot;
		System.out.println(String.format("Moyenne Generale = %s / %s => %s", moyPond, coefTot, moyPond / coefTot));
		return moyGenPond;
	}

	// Calcul de moyenne generale de l eleve et calcul de rang
	void calculMoyenneGeneralEleve(List<MoyenneEleveDto> moyEleve) {
		System.out.println("---> Calcul de moyenne generale de l eleve et calcul de rang");
		Double moy = 0.0;
		Double moyPond = 0.0;
		Double moyTotPond = 0.0;
		Double coef = 0.0;
		Double coefTot = 0.0;
		Double moyGenPond = 0.0;
		BigDecimal moyBD ;
//		Gson g = new Gson();
		Map<Double, MoyenneEleveDto> classementMap = new HashMap<Double, MoyenneEleveDto>();
		// Calcul des moyennes generales par eleves
//		System.out.println(g.toJson(moyEleve));
		for (MoyenneEleveDto eleve : moyEleve) {
			for (Matiere matiere : eleve.getNotesMatiereMap().keySet()) {
				moy = matiere.getMoyenne();
//				System.out.println(matiere+"+++");
				coef = Double.parseDouble(matiere.getCoef());
				moyPond = (moy * coef);
				moyTotPond += moyPond;
				coefTot += Double.parseDouble(matiere.getCoef());
				System.out.println(
						String.format("moy %s --- coef %s ---- moy pondere --> %s", moy, coef, moyPond));
			}
			if(coefTot == 0.0)
				coefTot = 1.0;
			
			System.out.println(String.format("moy Tot pondere   %s ---- coefTot %s  ", moyTotPond, coefTot));
			moyGenPond = moyTotPond / coefTot;
			moyBD = new BigDecimal(moyGenPond).setScale(2, RoundingMode.HALF_UP);
			eleve.setMoyenne(moyBD.doubleValue());
			System.out.println("Moyenne Generale :" + eleve.getMoyenne());
			eleve.setAppreciation(appreciation(moyGenPond));
			classementMap.put(moyGenPond, eleve);
			moy = 0.0;
			moyPond = 0.0;
			moyTotPond = 0.0;
			coef = 0.0;
			coefTot = 0.0;
			moyGenPond = 0.0;
		}

		// Classement des eleves par moyenne
		classementElevePeriode(classementMap);
//		System.out.println(g.toJson(moyEleve));
	}

	void classementEleveParMatiere(List<MoyenneEleveDto> moyEleve, Long brancheId) {
		System.out.println("---> Classement des eleves par matiere");
		List<ClasseMatiere> classeMatList = ClasseMatiere.find("branche.id = ?1", brancheId).list();
		Map<Double, MoyenneEleveDto> mapt;
		ValueEleveComparator vc;
		TreeMap<Double, MoyenneEleveDto> sorted_map;
		int i = 0;
		for (ClasseMatiere cm : classeMatList) {
			mapt = new HashMap<Double, MoyenneEleveDto>();
			vc = new ValueEleveComparator(mapt);
			sorted_map = new TreeMap<Double, MoyenneEleveDto>(vc);
			for (MoyenneEleveDto me : moyEleve) {
				System.out.println("eleve - " + me.getEleve().getNom());
//			System.out.println(me.getNotesMatiereMap());
				if (me.getNotesMatiereMap() != null) {
					for (Map.Entry<Matiere, List<Notes>> map : me.getNotesMatiereMap().entrySet()) {
						if (map.getKey().getCode().equals(cm.getMatiere().getCode())) {
							mapt.put(map.getKey().getMoyenne(), me);
							System.out.println(
									"Matiere : " + map.getKey().getLibelle() + " " + map.getKey().getMoyenne());
							break;
						}
					}

				}
			}
//			System.out.println(mapt);
			sorted_map.putAll(mapt);
			System.out.println("---------------<");
//			System.out.println(sorted_map);

			for (MoyenneEleveDto me : moyEleve) {
				i = 0;
				for (Map.Entry<Double, MoyenneEleveDto> srt : sorted_map.entrySet()) {
					i++;
					if (me.equals(srt.getValue())) {
						for (Map.Entry<Matiere, List<Notes>> map : me.getNotesMatiereMap().entrySet()) {
							if (map.getKey().getCode().equals(cm.getMatiere().getCode())) {
								// maj du rang par matiere et du coef de la matiere par rapport a la classe
								map.getKey().setRang(String.valueOf(i));
								map.getKey().setCoef(cm.getCoef() != null ? cm.getCoef() : "1");
								System.out.println("Matiere : " + map.getKey().getLibelle() + " "
										+ map.getKey().getMoyenne() + " Rang " + i +" coef :"+map.getKey().getCoef());
								break;
							}
						}
					}
				}
			}
		}

	}

	// Calcul de rang des eleves par periode
	void classementElevePeriode(Map<Double, MoyenneEleveDto> eleveMap) {
//		Gson g = new Gson();
		ValueEleveComparator vc = new ValueEleveComparator(eleveMap);
		TreeMap<Double, MoyenneEleveDto> sorted_map = new TreeMap<Double, MoyenneEleveDto>(vc);
		sorted_map.putAll(eleveMap);

		int i = 0;

		for (Map.Entry<Double, MoyenneEleveDto> moy : sorted_map.entrySet()) {
			i++;
			moy.getValue().setRang(String.valueOf(i));
		}

	}
	
	
	
/*
 * CODE SAVE
 */
	
//	public List<Notes> getNotesClasse(String evalCode) {
//	Evaluation evaluation = evaluationService.findByCode(evalCode);
//	List<ClasseEleve> classeEleves = classeEleveService.getByClasseAnnee(evaluation.getClasseAnnee().getId());
//	List<Notes> notesList = getByEvaluation(evaluation.getId());
//	List<Notes> noteListTemp = new ArrayList<Notes>();
//	Boolean flat = true;
//	Notes notemp;
//	for (ClasseEleve ce : classeEleves) {
//		flat = true;
//		for (Notes note : notesList) {
//			if (ce.getEleve().getMatricule().equals(note.getEleve().getMatricule())) {
//				noteListTemp.add(note);
//				flat = false;
//				break;
//			}
//		}
//		if (flat) {
//			notemp = new Notes();
//			notemp.setEleve(ce.getEleve());
//			notemp.setEvaluation(evaluation);
//			noteListTemp.add(notemp);
//		}
//	}
//
//	return noteListTemp;
//}
//
//List<MoyenneEleveDto> formatMoyenneMatieres(Map<Eleve, List<Notes>> param) {
//
//	return null;
//}
//
//void classement() {
//
//}
//
//public List<MoyenneEleveDto> moyennesAndNotesHandle(String classeAnneeId, String periodeId) {
//
//	Map<Eleve, List<Notes>> noteGroup = new HashMap<Eleve, List<Notes>>();
//	Parameters params = Parameters.with("classeAnneeId", Long.parseLong(classeAnneeId)).and("periodeId",
//			Long.parseLong(periodeId));
//	String query = "classeAnnee.id = :classeAnneeId and periode.id = :periodeId";
//	List<Evaluation> evalList = evaluationService.search(query, params);
//	List<Notes> noteList = new ArrayList<Notes>();
//	List<Notes> notesTemp;
//	List<MoyenneEleveDto> moyenneList = new ArrayList<MoyenneEleveDto>();
//	Map<Matiere, List<Notes>> notesMatiereGroup;
//	ClasseAnnee classeAnnee;
//	MoyenneEleveDto moyenneEleveDto;
//	Gson g = new Gson();
//	for (Evaluation ev : evalList) {
//		noteList.addAll(getNotesClasse(ev.getCode()));
//	}
//	for (Notes note : noteList) {
//		if (noteGroup.containsKey(note.getEleve())) {
//			noteGroup.get(note.getEleve()).add(note);
//		} else {
//			notesTemp = new ArrayList<Notes>();
//			notesTemp.add(note);
//			noteGroup.put(note.getEleve(), notesTemp);
//		}
//	}
//	classeAnnee = classeAnneeService.findById(Long.parseLong(classeAnneeId));
//	System.out.println(g.toJson(classeAnnee));
//
//	// Formatage des dto
//	for (Map.Entry<Eleve, List<Notes>> entry : noteGroup.entrySet()) {
//		moyenneEleveDto = new MoyenneEleveDto();
//		moyenneEleveDto.setEleve(entry.getKey());
//		moyenneEleveDto.setClasse(classeAnnee.getClasse());
//		notesMatiereGroup = new HashMap<Matiere, List<Notes>>();
//		Matiere matiereTemp;
//		for (Notes note : entry.getValue()) {
//			if (note.getId() != 0) {
//				if (notesMatiereGroup.containsKey(note.getEvaluation().getMatiere())) {
//					notesMatiereGroup.get(note.getEvaluation().getMatiere()).add(note);
//				} else {
//					notesTemp = new ArrayList<Notes>();
//					notesTemp.add(note);
//					matiereTemp = new Matiere();
//					matiereTemp.setId(note.getEvaluation().getMatiere().getId());
//					matiereTemp.setCode(note.getEvaluation().getMatiere().getCode());
//					matiereTemp.setLibelle(note.getEvaluation().getMatiere().getLibelle());
//					matiereTemp.setMoyenne(note.getEvaluation().getMatiere().getMoyenne());
//
//					notesMatiereGroup.put(matiereTemp, notesTemp);
//				}
//			}
//		}
//		moyenneEleveDto.setNotesMatiereMap(notesMatiereGroup);
//		moyenneList.add(moyenneEleveDto);
//	}
//	classementEleveParMatiere(calculMoyenneMatiere(moyenneList), classeAnnee.getClasse().getBranche().getId());
//	calculMoyenneGeneralEleve(moyenneList);
//	Collections.sort(moyenneList);
//	return moyenneList;
//}

}

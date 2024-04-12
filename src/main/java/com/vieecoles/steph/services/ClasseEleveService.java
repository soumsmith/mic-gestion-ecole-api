package com.vieecoles.steph.services;

import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.ClasseEleve;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.Inscription;
import com.vieecoles.steph.entities.Notes;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class ClasseEleveService implements PanacheRepositoryBase<ClasseEleve, Long> {

	Logger logger = Logger.getLogger(ClasseEleveService.class.getName());
	@Inject
	NoteService noteService;

	public List<ClasseEleve> findByid(Long id) {
		return ClasseEleve.findById(id);
	}

	public List<ClasseEleve> getList() {
		return ClasseEleve.listAll();
	}

	@Transactional
	public void create(ClasseEleve ce) {
		ce.persist();
	}

	@Transactional
	public List<ClasseEleve> handleCreate(long classeId, List<Inscription> inscriptions) {
		List<ClasseEleve> classeEleves = new ArrayList<ClasseEleve>();
		for (Inscription ins : inscriptions) {
			ClasseEleve ceExistant = new ClasseEleve();
			try {
				ceExistant = getByInscription(ins.getId());
			} catch (RuntimeException e) {
				e.printStackTrace();
				ceExistant = null;
			}

			Classe classe = new Classe();
			classe.setId(classeId);

			if (ceExistant != null) {
				ceExistant.setClasse(classe);
				ceExistant.setDateUpdate(new Date());
				ceExistant.setStatut("ACTIF");
				classeEleves.add(ceExistant);
				System.out.println(
						"----> Ré Affectation de " + ins.getEleve().getMatricule() + " en classe de " + classe.getId());
			} else {
				ClasseEleve classeEleve = new ClasseEleve();
				classeEleve.setClasse(classe);
				classeEleve.setDateCreation(new Date());
				classeEleve.setStatut("ACTIF");
				classeEleve.setDateUpdate(new Date());
				classeEleve.setInscription(ins);
				System.out.println(
						"----> Affectation de " + ins.getEleve().getMatricule() + " en classe de " + classe.getId());
				create(classeEleve);
				classeEleves.add(classeEleve);
			}
		}
		return classeEleves;
	}

	@Transactional
	public ClasseEleve update(ClasseEleve classeEleve) {
		ClasseEleve entity = ClasseEleve.findById(classeEleve.getId());
		if (entity == null) {
			throw new NotFoundException();
		}
		// get classe annee by id + eleve by id pour set
//			entity.setClasseAnnee(classeEleve.getClasseAnnee());
		entity.setInscription(classeEleve.getInscription());
		entity.setClasse(classeEleve.getClasse());
		entity.setStatut(classeEleve.getStatut());
		entity.setDateUpdate(classeEleve.getDateUpdate());

		return entity;
	}

	@Transactional
	public void deleteHandle(long id) {
		logger.info(String.format("Id to delete %s", id));

		List<Notes> notes = new ArrayList<>();
		ClasseEleve entity = ClasseEleve.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}

		notes = noteService.getNotesByInscriptionHasClasse(id);
		// désactivation du pec des notes de l'élève à retirer
		logger.info("Notes trouvées : " + notes.size());
		for (Notes note : notes) {
			note.setPec(0);
			noteService.update(note);
		}

//		Classe classe = new Classe();
		entity.setClasseRetireeId(entity.getClasse().getId());
//		classe.setId(0);
//		entity.setClasse(classe);
		entity.setStatut(Constants.STATUT_ELEVE_RETIRE_CLASSE);
		entity.setDateUpdate(new Date());
		update(entity);

		logger.info("Retrait effectué");
	}

	public List<ClasseEleve> getByIds(List<Long> ids) {
		List<ClasseEleve> list = new ArrayList<ClasseEleve>();
		for (Long id : ids) {
			list.add(ClasseEleve.findById(id));
		}
		return list;
	}

	public ClasseEleve updateAndDisplay(ClasseEleve classeEleve) {
		ClasseEleve ce;
		if (update(classeEleve) != null) {
			ce = findById(classeEleve.getId());
			return ce;
		}

		return null;
	}

	public List<ClasseEleve> getByClasseAnnee(Long classeId, Long anneeId) {

		List<ClasseEleve> ces = new ArrayList<>();
		try {
			ces = ClasseEleve.find(
					"classe.id = ?1 and inscription.annee.id = ?2 and (statut is null or statut <> 'RETIRE') order by inscription.eleve.nom, inscription.eleve.prenom",
					classeId, anneeId).list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ces;
	}

	public int getCountByClasseAnnee(Long classeId, Long anneeId) {
		int ce = 0;
		try {
			ce = ClasseEleve
					.find("classe.id = ?1 and inscription.annee.id = ?2 and (statut is null or statut <> 'RETIRE')",
							classeId, anneeId)
					.list().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ce;
	}

	public List<ClasseEleve> getByBrancheAndAnnee(Long brancheId, Long anneeId, Long ecoleId) {
		System.out.println(brancheId + " " + anneeId + " " + ecoleId);

		List<ClasseEleve> ces = new ArrayList<>();
		try {
			ces = ClasseEleve.find(
					"inscription.branche.id = ?1 and inscription.annee.id = ?2 and inscription.ecole.id = ?3 and (statut is null or statut <> 'RETIRE')",
					brancheId, anneeId, ecoleId).list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ces;
	}

	public List<ClasseEleve> getByBrancheAndAnneeAndStatut(Long brancheId, Long anneeId, Long ecoleId, String statut) {
//		System.out.println(brancheId + " " + anneeId + " " + ecoleId + " " + statut);
		List<ClasseEleve> ces = new ArrayList<>();
		try {
			ces = ClasseEleve.find(
					"inscription.branche.id = ?1 and inscription.annee.id = ?2 and inscription.ecole.id = ?3 and (statut is null or statut != ?4)",
					brancheId, anneeId, ecoleId, Constants.STATUT_ELEVE_RETIRE_CLASSE).list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ces;
	}

	public ClasseEleve getByClasseAndEleveAndAnnee(Long classeId, Long eleveId, Long anneeId) {

		ClasseEleve ce = new ClasseEleve();
		try {
			ce = ClasseEleve.find(
					"classe.id = ?1 and inscription.annee.id = ?2 and inscription.eleve.id = ?3 and (statut is null or statut <> 'RETIRE')",
					classeId, anneeId, eleveId).singleResult();
		} catch (Exception e) {
			e.printStackTrace();
			ce = null;
		}

		return ce;
	}

	public ClasseEleve getByInscription(Long inscriptionId) {

		ClasseEleve ce = new ClasseEleve();
		try {
			logger.info("Inscription id = " + inscriptionId);
			ce = ClasseEleve.find("inscription.id = ?1", inscriptionId).singleResult();
		} catch (Exception e) {
			e.printStackTrace();
			ce = null;
		}

		return ce;
	}

	public ClasseEleve getByMatriculeAndAnnee(String matricule, Long ecoleId, Long anneeId) {
		System.out.println("mat :" + matricule + " ecole : " + ecoleId + " annee :" + anneeId);

		ClasseEleve ce = new ClasseEleve();
		try {
			ce = ClasseEleve.find(
					"inscription.eleve.matricule = ?1 and classe.ecole.id = ?2 and inscription.annee.id = ?3 and (statut is null or statut <> 'RETIRE')",
					matricule, ecoleId, anneeId).singleResult();
		} catch (Exception e) {
			e.printStackTrace();
			ce = null;
		}
		return ce;
	}

	public ClasseEleve getByMatriculeAndAnnee(String matricule, Long anneeId) {
		System.out.println("mat :" + matricule + " annee :" + anneeId);

		ClasseEleve ce = new ClasseEleve();
		try {
			ce = ClasseEleve.find(
					"inscription.eleve.matricule = ?1 and inscription.annee.id = ?2 and (statut is null or statut <> 'RETIRE')",
					matricule, anneeId).singleResult();
		} catch (Exception e) {
			e.printStackTrace();
			ce = null;
		}

		return ce;
	}

	public long count() {
		return ClasseEleve.count();
	}

}

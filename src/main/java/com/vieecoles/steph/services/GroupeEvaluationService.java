package com.vieecoles.steph.services;

import com.vieecoles.steph.entities.Evaluation;
import com.vieecoles.steph.entities.GroupeEvaluations;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RequestScoped
public class GroupeEvaluationService implements PanacheRepositoryBase<GroupeEvaluations, String> {
	@Inject
	EvaluationService evaluationService;

	Logger log = Logger.getLogger(this.getClass().getName());

	public List<GroupeEvaluations> getList() {

		return GroupeEvaluations.listAll();
	}

	public List<GroupeEvaluations> getListByEcole(Long ecoleId) {

		List<GroupeEvaluations> groupes = new ArrayList<>();

		try {
			groupes = GroupeEvaluations.find("ecole.id = ?1", ecoleId).list();
		} catch (RuntimeException r) {
			r.printStackTrace();
		}
		return groupes;
	}

	public GroupeEvaluations getById(String id) {
		GroupeEvaluations grp = GroupeEvaluations.findById(id);

		return populateGroupe(grp);
	}

	private GroupeEvaluations populateGroupe(GroupeEvaluations groupe) {
		List<Evaluation> evaluations = new ArrayList<>();
		evaluations = evaluationService.getByGroupe(groupe.getId());
		groupe.setEvaluations(evaluations);
		groupe.setNbreEvaluations(evaluations.size());
//		System.out.println(groupe.getId() + " -> " + evaluations.size());
		return groupe;
	}

	public List<GroupeEvaluations> getByEcoleAndAnnee(Long ecole, Long annee) {
		List<GroupeEvaluations> groupes = new ArrayList<>();
		try {
			groupes = GroupeEvaluations.find("ecole.id=?1 and annee.id =?2", ecole, annee).list();
			for (GroupeEvaluations grp : groupes) {
//				System.out.println("groupe id ---> " + grp.getId());
				populateGroupe(grp);
			}
		} catch (RuntimeException e) {
			log.info(e.getMessage());
		}
//		Gson g = new Gson();

		System.out.println(groupes.get(0).getNbreEvaluations());
		return groupes;
	}

	@Transactional
	public GroupeEvaluations create(GroupeEvaluations groupe) {
		UUID uuid = UUID.randomUUID();
		groupe.setId(uuid.toString());
		groupe.setDateCreation(new Date());
		groupe.setDateUpdate(new Date());
		groupe.persist();
		return groupe;
	}

	@Transactional
	public GroupeEvaluations update(String id, GroupeEvaluations annee) {
		GroupeEvaluations entity = GroupeEvaluations.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}
		entity.setLibelle(annee.getLibelle());
		entity.setAnnee(annee.getAnnee());
		entity.setDateUpdate(new Date());
//		entity.setNbreEvaluations(annee.getNbreEval());
		entity.setNiveau(annee.getNiveau());
		entity.setPeriode(annee.getPeriode());
		entity.setEcole(annee.getEcole());
		entity.setUser(annee.getUser());
		return entity;
	}

	@Transactional
	public void delete(String id) {
		GroupeEvaluations entity = GroupeEvaluations.findById(id);
		if (entity == null) {
			throw new NotFoundException(String.format("[id : %s] non trouvé", id));
		}
		entity.delete();
	}

	@Transactional
	public void deleteHandle(String id) {
		try {
			GroupeEvaluations groupe = getById(id);
			if (groupe != null) {
				System.out.println("---> Suppression du groupe " + groupe.getId());
				List<Evaluation> recordedList = evaluationService.getByGroupe(groupe.getId());
				// Suppression de l'id de groupe dans les enregistrements d'évaluation courants
				// de la bd
				for (Evaluation ev : recordedList) {
					ev.setGroupeEvaluationId(null);
					evaluationService.update(ev);
				}
				delete(groupe);
			}

		} catch (RuntimeException r) {
			r.printStackTrace();
			throw new RuntimeException(r.getMessage());
		}
	}

	@Transactional
	public GroupeEvaluations saveHandle(GroupeEvaluations groupe) {
		List<Evaluation> listEval = new ArrayList<>();
		listEval.addAll(groupe.getEvaluations());
		try {
			if (groupe.getId() == null) {
				System.out.println("---> Création du groupe");
				groupe = create(groupe);
			} else {
				System.out.println("---> Modification du groupe " + groupe.getId());
				groupe = update(groupe.getId(), groupe);
				List<Evaluation> recordedList = evaluationService.getByGroupe(groupe.getId());
				// Suppression de l'id de groupe dans les enregistrements d'évaluation courants
				// de la bd
				for (Evaluation ev : recordedList) {
					ev.setGroupeEvaluationId(null);
					evaluationService.update(ev);
				}

			}
			// Mise à jour de l'id de groupe
			if (listEval != null && listEval.size() > 0) {
				for (Evaluation ev : listEval) {
					ev.setGroupeEvaluationId(groupe.getId());
					ev.setUser(groupe.getUser());
					evaluationService.update(ev);
				}
			}

		} catch (RuntimeException r) {
			r.printStackTrace();
			throw new RuntimeException(r.getMessage());
		}
		System.out.println("HHHH " + groupe.getId());
		return populateGroupe(groupe);
	}

}

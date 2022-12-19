package com.vieecoles.ressource.steph.services;

import com.vieecoles.entities.Ecole;
import com.vieecoles.entities.PersonnelMatiereClasse;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class PersonnelMatiereClasseService implements PanacheRepositoryBase<PersonnelMatiereClasse, Long> {

	Logger logger = Logger.getLogger(PersonnelMatiereClasseService.class.getName());
	// A modifer avec le bon parametre de session
	private Long getAnneeScolaire = Long.parseLong("1");

	public List<PersonnelMatiereClasse> getList() {
		try {
			logger.info("........ in list <<<<>>>>>");
			return PersonnelMatiereClasse.listAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public PersonnelMatiereClasse findById(long id) {
		logger.info(String.format("find by id :: %s", id));
		return PersonnelMatiereClasse.findById(id);
	}

	// modifier l annee avec le parametre quand disponible
	public List<PersonnelMatiereClasse> findByBranche(long brancheId, long annee) {
		logger.info(String.format("find by Branche id :: %s", brancheId));
		return PersonnelMatiereClasse.find("classe.branche.id = ?1 and annee.id = ?2", brancheId, getAnneeScolaire)
				.list();
	}

	// modifier l annee avec le parametre quand disponible
	public List<PersonnelMatiereClasse> findByMatiere(long matiereId, long annee) {
		logger.info(String.format("find by Matiere id :: %s and annee :: %s", matiereId, getAnneeScolaire));
		return PersonnelMatiereClasse.find("matiere.id = ?1 and annee.id = ?2", matiereId, getAnneeScolaire).list();
	}

	public PersonnelMatiereClasse findByMatiereAndClasse(long matiereId, long annee, long classeId) {
		logger.info(String.format("find by Matiere id :: %s and annee :: %s and classe :: %s", matiereId,
				getAnneeScolaire, classeId));
		PersonnelMatiereClasse pmc;
		try {
			pmc = PersonnelMatiereClasse
					.find("matiere.id = ?1 and annee.id = ?2 and classe.id = ?3", matiereId, getAnneeScolaire, classeId)
					.singleResult();
		} catch (RuntimeException re) {
			logger.log(Level.WARNING, "Aucun PersonnelMatiereClasse trouve");
			return null;
		}
		return pmc;
	}

	public List<PersonnelMatiereClasse> findByProfesseur(long profId, long annee) {
		logger.info(String.format("find by Prof id :: %s and annee :: %s", profId, getAnneeScolaire));
		return PersonnelMatiereClasse.find("matiere.id = ?1 and personnel.id = ?2", profId, getAnneeScolaire).list();
	}

	@Transactional
	public void save(PersonnelMatiereClasse persMatClasse) {
		logger.info("persist persMatClasse ...");
		Ecole ecole;
		// A supprimer lorsque le credential contenant l ecole sera disponible
		// Par defaut pour tout enregistrement on set l ecole id à 1
		if (persMatClasse.getPersonnel() != null && persMatClasse.getPersonnel().getEcole() == null) {
			ecole = new Ecole();
			ecole.setId(1);
			persMatClasse.getPersonnel().setEcole(ecole);
		}
		persMatClasse.setDateCreation(new Date());
		if (getIfExist(persMatClasse) != null && getIfExist(persMatClasse).size() != 0)
			throw new RuntimeException("Enregistrement existant déjà");

		persMatClasse.persist();
	}

	public List<PersonnelMatiereClasse> getIfExist(PersonnelMatiereClasse persMatClasse) {
		return PersonnelMatiereClasse.find("classe.id = ?1 and personnel.id =?2 and matiere.id =?3 and annee.id= ?4",
				persMatClasse.getClasse().getId(), persMatClasse.getPersonnel().getId(),
				persMatClasse.getMatiere().getId(), persMatClasse.getAnnee().getId()).list();
	}

	@Transactional
	public PersonnelMatiereClasse update(PersonnelMatiereClasse persMatClasse) {
		logger.info("updating persMatClasse ...");
		PersonnelMatiereClasse cl = PersonnelMatiereClasse.findById(persMatClasse.getId());
		if (cl != null) {
			cl.setAnnee(persMatClasse.getAnnee());
			cl.setClasse(persMatClasse.getClasse());
			cl.setMatiere(persMatClasse.getMatiere());
			cl.setPersonnel(persMatClasse.getPersonnel());
		}
//		logger.info(new Gson().toJson(cl));
		return cl;
	}

	public PersonnelMatiereClasse updateAndDisplay(PersonnelMatiereClasse persMatClasse) {
		PersonnelMatiereClasse cl;
		if (update(persMatClasse) != null) {
			cl = findById(persMatClasse.getId());
//			logger.info("persMatClasse apres le get");;
//			logger.info(new Gson().toJson(cl));
			return cl;
		}

		return null;

	}

	@Transactional
	public void delete(String id) {

		logger.info("delete persMatClasse id " + id);
		PersonnelMatiereClasse persMatClasse = findById(Long.parseLong(id));
		persMatClasse.delete();

	}

}

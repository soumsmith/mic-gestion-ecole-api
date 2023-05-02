package com.vieecoles.steph.services;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.hibernate.internal.build.AllowSysOut;

import com.vieecoles.steph.entities.PersonnelMatiereClasse;
import com.vieecoles.steph.dto.ProfEducDto;
import com.vieecoles.steph.entities.Ecole;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
/*
 * REMPLACER ANNEE
 * voir aussi methode save
 */

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

	// Obtenir le prof princ ou l'éducateur d' une classe
	public PersonnelMatiereClasse getPersonnelByClasseAndAnneeAndFonction(Long classe, Long annee, int fonction) {
		PersonnelMatiereClasse pmc = null;
		try {
			pmc = PersonnelMatiereClasse
					.find("classe.id = ?1 and annee.id= ?2 and personnel.fonction.id =?3 and matiere is null", classe,
							annee, fonction)
					.singleResult();
		} catch (RuntimeException e) {
			if (e.getClass().getName().equals(NoResultException.class.getName())) {
				logger.info(String.format("Aucun personnel educateur ou professeur principal [fonction : %s] trouvé ",
						fonction));
			} else {
				e.printStackTrace();
			}
			return pmc;
		}
		return pmc;
	}

	public PersonnelMatiereClasse findProfesseurByMatiereAndClasse(Long annee, Long classe, Long matiere) {
		PersonnelMatiereClasse pmc = null;
		try {
			pmc = PersonnelMatiereClasse
					.find("classe.id = ?1 and annee.id= ?2 and matiere.id = ?3", classe, annee, matiere).singleResult();
		} catch (RuntimeException e) {
			logger.warning("Erreur de type : " + e.getClass().getName());
		}
		return pmc;
	}

	// modifier l annee avec le parametre quand disponible
	public List<PersonnelMatiereClasse> findByBranche(long brancheId, long annee) {
		logger.info(String.format("find by Branche id :: %s", brancheId));
		return PersonnelMatiereClasse
				.find("classe.branche.id = ?1 and annee.id = ?2 and matiere is not null", brancheId, getAnneeScolaire)
				.list();
	}

	// modifier l annee avec le parametre quand disponible
	public List<PersonnelMatiereClasse> findByMatiere(long matiereId, long annee, long ecole) {
		logger.info(
				String.format("find by Matiere id :: %s and annee :: %s and ecole ::: %s", matiereId, annee, ecole));
		return PersonnelMatiereClasse
				.find("matiere.id = ?1 and annee.id = ?2 and classe.ecole.id = ?3", matiereId, annee, ecole).list();
	}

	public PersonnelMatiereClasse findByMatiereAndClasse(long matiereId, long annee, long classeId) {
		logger.info(
				String.format("find by Matiere id :: %s and annee :: %s and classe :: %s", matiereId, annee, classeId));
		PersonnelMatiereClasse pmc;
		try {
			pmc = PersonnelMatiereClasse
					.find("matiere.id = ?1 and annee.id = ?2 and classe.id = ?3", matiereId, annee, classeId)
					.singleResult();
		} catch (RuntimeException re) {
			logger.log(Level.WARNING, "Aucun PersonnelMatiereClasse trouve");
			return null;
		}
		return pmc;
	}

	public List<PersonnelMatiereClasse> findByProfesseur(long profId, long annee, long ecole) {
		logger.info(
				String.format("find by Prof id :: %s and annee :: %s", profId, annee != 0 ? annee : getAnneeScolaire));
		return PersonnelMatiereClasse
				.find("personnel.id = ?1 and annee.id = ?2  and classe.ecole.id=?3 and matiere is not null", profId,
						annee != 0 ? annee : getAnneeScolaire, ecole)
				.list();
	}

	public List<PersonnelMatiereClasse> findByProfesseurAndClasse(long profId, long classe, long annee) {
		logger.info(String.format("find by Prof id :: %s and annee :: %s", profId, annee));
		return PersonnelMatiereClasse
				.find("personnel.id = ?1 and annee.id = ?2 and classe.id = ?3 and matiere is not null", profId,
						annee != 0 ? annee : getAnneeScolaire, classe)
				.list();
	}

	public List<PersonnelMatiereClasse> findListByClasse(long annee, long classe) {
		return PersonnelMatiereClasse.find("annee.id = ?1 and classe.id =?2 and matiere is not null", annee, classe)
				.list();
	}

// Attention ne pas utiliser pour determiner les matieres enseignees par un professeur
	public List<PersonnelMatiereClasse> findListByFonction(long annee, long ecole, int fonctionId) {
		return PersonnelMatiereClasse
				.find("annee.id = ?1 and classe.ecole.id=?2 and personnel.fonction.id =?3 and matiere is null", annee,
						ecole, fonctionId)
				.list();
	}

	// Attention ne pas utiliser pour determiner les matieres enseignees par un
	// professeur
	public List<PersonnelMatiereClasse> findListByPersonnel(Long annee, long ecole, long classe) {
		return PersonnelMatiereClasse.find("annee.id = ?1 and classe.ecole.id=?2 and classe.id =?3 and matiere is null",
				annee, ecole, classe).list();
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
		else if (getByMatiereAndClasseDispo(persMatClasse).size() > 0) {
			List<PersonnelMatiereClasse> psList = getByMatiereAndClasseDispo(persMatClasse);
			PersonnelMatiereClasse.update("personnel.id = ?1 where id = ?2", persMatClasse.getPersonnel().getId(),
					psList.get(0).getId());
		} else
			persMatClasse.persist();
	}

	@Transactional
	public void saveForEducAndProf(PersonnelMatiereClasse persMatClasse) {
		logger.info("persist persMatClasse ...");
		List<PersonnelMatiereClasse> profOrEduclist;
		ProfEducDto profEducDto = new ProfEducDto();
		Ecole ecole;
		Boolean flat = true;
		// A supprimer lorsque le credential contenant l ecole sera disponible
		// Par defaut pour tout enregistrement on set l ecole id à 1
		if (persMatClasse.getPersonnel() != null && persMatClasse.getPersonnel().getEcole() == null) {
			ecole = new Ecole();
			ecole.setId(1);
			persMatClasse.getPersonnel().setEcole(ecole);
		}
		persMatClasse.setDateCreation(new Date());
		profEducDto.setClasse(persMatClasse.getClasse());
		profOrEduclist = getIfExistProfOrEduc(persMatClasse);
		System.out.println("PersonnelMatiereClasseService.saveForEducAndProf() " + profOrEduclist.size());
		if (profOrEduclist != null && profOrEduclist.size() != 0) {
			for (PersonnelMatiereClasse profOrEduc : profOrEduclist) {
				System.out.println("PersonnelMatiereClasseService.saveForEducAndProf() "
						+ profOrEduc.getPersonnel().getFonction().getCode() + " - "
						+ persMatClasse.getPersonnel().getFonction().getCode());
				if (profOrEduc.getPersonnel().getFonction().getCode()
						.equals(persMatClasse.getPersonnel().getFonction().getCode())) {
					flat = false;
					if (persMatClasse.getPersonnel().getId() == 0)
						profOrEduc.delete();
					else {
						// penser à creer et mettre à jour le champ date_update
						PersonnelMatiereClasse.update("personnel.id = ?1 where id = ?2",
								persMatClasse.getPersonnel().getId(), profOrEduc.getId());
					}
				}

			}
			if (flat)
				persMatClasse.persist();
		} else {
			System.out.println(persMatClasse);
			persMatClasse.persist();
		}

	}

	public List<PersonnelMatiereClasse> getListProfOrEducByAnneeAndClasse(long annee, long classe) {
		return PersonnelMatiereClasse.find("classe.id = ?1  and annee.id= ?2 and matiere.id is null", classe, annee)
				.list();
	}

	public List<PersonnelMatiereClasse> getIfExist(PersonnelMatiereClasse persMatClasse) {
		return PersonnelMatiereClasse.find("classe.id = ?1 and personnel.id =?2 and matiere.id =?3 and annee.id= ?4",
				persMatClasse.getClasse().getId(), persMatClasse.getPersonnel().getId(),
				persMatClasse.getMatiere().getId(), persMatClasse.getAnnee().getId()).list();
	}

	public List<PersonnelMatiereClasse> getByMatiereAndClasseDispo(PersonnelMatiereClasse persMatClasse) {
		return PersonnelMatiereClasse.find("classe.id = ?1 and matiere.id =?2 and annee.id= ?3",
				persMatClasse.getClasse().getId(), persMatClasse.getMatiere().getId(), persMatClasse.getAnnee().getId())
				.list();
	}

	public List<PersonnelMatiereClasse> getIfExistProfOrEduc(PersonnelMatiereClasse persMatClasse) {

		System.out.println("candidat pour supression");
		return PersonnelMatiereClasse.find("classe.id = ?1 and matiere.id is null and annee.id= ?2",
				persMatClasse.getClasse().getId(), persMatClasse.getAnnee().getId()).list();

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

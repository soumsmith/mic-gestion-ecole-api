package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

import org.hibernate.internal.build.AllowSysOut;

import com.vieecoles.steph.entities.PersonnelMatiereClasse;
import com.vieecoles.steph.projections.GenericPersonelProjectionLongIdFonctionEcole;
import com.vieecoles.steph.projections.GenericProjectionLongId;
import com.vieecoles.steph.dto.IdLongCodeLibelleDto;
import com.vieecoles.steph.dto.PersonnelMatiereClasseDto;
import com.vieecoles.steph.dto.ProfEducDto;
import com.vieecoles.steph.entities.ClasseMatiere;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.Ecole;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
/*
 * REMPLACER ANNEE
 * voir aussi methode save
 */

@ApplicationScoped
public class PersonnelMatiereClasseService implements PanacheRepositoryBase<PersonnelMatiereClasse, Long> {

	@Inject
	ClasseMatiereService cmService;

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

		if(fonction == 1) {
			pmc = findProfPrinc(annee, classe);
		}else if (fonction == 2) {
			pmc = findEducateurClasse(annee, classe);
		}
		return pmc;
	}

	public PersonnelMatiereClasse findProfesseurByMatiereAndClasse(Long annee, Long classe, Long matiere) {
		PersonnelMatiereClasse pmc = null;
		logger.info(String.format("Annee: %s  Classe: %s Matiere: %s", annee, classe, matiere));
		try {
			pmc = PersonnelMatiereClasse
					.find("classe.id = ?1 and annee.id= ?2 and matiere.id = ?3 and (statut is null or statut <> 'DELETED') ", classe, annee, matiere).singleResult();
		} catch (RuntimeException e) {
//			e.printStackTrace();
			if(e.getClass().getName().equals(NoResultException.class.getName()))
				System.out.println("Aucune donnee trouvee");
			else
				e.printStackTrace();
			logger.warning("Erreur de type : " + e.getClass().getName());
			return null;
		}
//		System.out.println(pmc == null);
		return pmc;
	}
	
	/**
	 * Retourne la liste des professeurs par classe.
	 * @param annee
	 * @param classe
	 * @return
	 */
	public List<PersonnelMatiereClasse> findProfesseursByClasse(Long annee, Long classe) {
		List<PersonnelMatiereClasse> pmc;
		logger.info(String.format("findProfesseursByClasse Annee: %s  Classe: %s ", annee, classe));
		try {
			pmc = PersonnelMatiereClasse
					.find("classe.id = ?1 and annee.id= ?2 and (statut is null or statut <> 'DELETED') ", classe, annee).list();
		} catch (RuntimeException e) {
//			e.printStackTrace();
			if(e.getClass().getName().equals(NoResultException.class.getName()))
				System.out.println("Aucune donnee trouvee");
			else 
				e.printStackTrace();
			logger.warning("Erreur de type : " + e.getClass().getName());
			pmc = new ArrayList<PersonnelMatiereClasse>();
		}
//		System.out.println(pmc == null);
		return pmc;
	}

	// modifier l annee avec le parametre quand disponible
	public List<PersonnelMatiereClasse> findByBranche(long brancheId, long annee) {
		logger.info(String.format("find by Branche id :: %s", brancheId));
		return PersonnelMatiereClasse
				.find("classe.branche.id = ?1 and annee.id = ?2 and matiere is not null and (statut is null or statut <> 'DELETED') ", brancheId, annee).list();
	}

	// modifier l annee avec le parametre quand disponible
	public List<PersonnelMatiereClasse> findByMatiere(long matiereId, long annee, long ecole) {
		logger.info(
				String.format("find by Matiere id :: %s and annee :: %s and ecole ::: %s", matiereId, annee, ecole));
		return PersonnelMatiereClasse
				.find("matiere.id = ?1 and annee.id = ?2 and classe.ecole.id = ?3 and (statut is null or statut <> 'DELETED') ", matiereId, annee, ecole).list();
	}

	public PersonnelMatiereClasse findByMatiereAndClasse(long matiereId, long annee, long classeId) {
		logger.info(
				String.format("find by Matiere id :: %s and annee :: %s and classe :: %s", matiereId, annee, classeId));
		PersonnelMatiereClasse pmc;
		try {
			pmc = PersonnelMatiereClasse
					.find("matiere.id = ?1 and annee.id = ?2 and classe.id = ?3 and (statut is null or statut <> 'DELETED') ", matiereId, annee, classeId)
					.singleResult();
		} catch (RuntimeException re) {
			logger.log(Level.WARNING, "Aucun PersonnelMatiereClasse trouve");
			return null;
		}
		return pmc;
	}

	public GenericProjectionLongId findPersonnelProjectionByMatiereAndClasse(long matiereId, long annee, long classeId) {
		logger.info(
				String.format("find by Matiere id :: %s and annee :: %s and classe :: %s", matiereId, annee, classeId));
		System.out.println(String.format("find by Matiere id :: %s and annee :: %s and classe :: %s", matiereId, annee, classeId));
		GenericProjectionLongId obj = null;
		try {
			 obj = PersonnelMatiereClasse
					.find("matiere.id = ?1 and annee.id = ?2 and classe.id = ?3 and (statut is null or statut <> 'DELETED') ", matiereId, annee, classeId)
					.project(GenericProjectionLongId.class).singleResult();
			 System.out.println(obj);
			 return obj;
		} catch (RuntimeException re) {
			System.out.println(obj);
			re.printStackTrace();
			logger.log(Level.WARNING, "Aucun PersonnelMatiereClasse trouve");
			return null;
		}
	}

	public GenericPersonelProjectionLongIdFonctionEcole findPersonnelProjectionIdFonctionEcoleByMatiereAndClasse(long matiereId, long annee, long classeId) {
		logger.info(
				String.format("find by Matiere id :: %s and annee :: %s and classe :: %s", matiereId, annee, classeId));
		System.out.println(String.format("find by Matiere id :: %s and annee :: %s and classe :: %s", matiereId, annee, classeId));
		GenericPersonelProjectionLongIdFonctionEcole obj = null;
		try {
			 obj = PersonnelMatiereClasse
					.find("matiere.id = ?1 and annee.id = ?2 and classe.id = ?3 and (statut is null or statut <> 'DELETED') ", matiereId, annee, classeId)
					.project(GenericPersonelProjectionLongIdFonctionEcole.class).singleResult();
			 System.out.println(obj);
			 return obj;
		} catch (RuntimeException re) {
			System.out.println(obj);
			re.printStackTrace();
			logger.log(Level.WARNING, "Aucun PersonnelMatiereClasse trouve");
			return null;
		}
	}

	public List<PersonnelMatiereClasse> findByProfesseur(long profId, long annee, long ecole) {
		logger.info(
				String.format("find by Prof id :: %s and annee :: %s", profId, annee));
		return PersonnelMatiereClasse
				.find("personnel.id = ?1 and annee.id = ?2  and classe.ecole.id=?3 and matiere is not null and (statut is null or statut <> 'DELETED') ", profId,
						annee , ecole)
				.list();
	}

	public List<PersonnelMatiereClasse> findByProfesseurAndClasse(long profId, long classe, long annee) {
		logger.info(String.format("find by Prof id :: %s and annee :: %s", profId, annee));
		return PersonnelMatiereClasse
				.find("personnel.id = ?1 and annee.id = ?2 and classe.id = ?3 and matiere is not null and (statut is null or statut <> 'DELETED') ", profId,
						annee != 0 ? annee : getAnneeScolaire, classe)
				.list();
	}

	public List<PersonnelMatiereClasse> findByProfesseurAndClasseWhereCoefDefine(long profId, long classe, long annee) {
		logger.info(String.format("find by Prof id :: %s and annee :: %s", profId, annee));
		List<PersonnelMatiereClasse> personnelMatiereClasseList = null;
		List<PersonnelMatiereClasse> pmcListTmp = null;
		try {
		personnelMatiereClasseList =  PersonnelMatiereClasse
				.find("personnel.id = ?1 and annee.id = ?2 and classe.id = ?3 and matiere is not null and (statut is null or statut <> 'DELETED') ", profId,
						annee != 0 ? annee : getAnneeScolaire, classe)
				.list();
		}catch (Exception e) {
			logger.warning("Erreur [List<PersonnelMatiereClasse> findByProfesseurAndClasseWhereCoefDefine] ::: "+e.getMessage());
		}
		if(personnelMatiereClasseList != null) {
			System.out.println("PersonnelMatiereClasseService.findByProfesseurAndClasseWhereCoefDefine()");
			System.out.println(personnelMatiereClasseList.size());
			List<ClasseMatiere> matiereCoefDefineList = cmService.getByBrancheViaClasse(classe);
			if(matiereCoefDefineList != null && matiereCoefDefineList.size()>0)
				pmcListTmp = new ArrayList<PersonnelMatiereClasse>();
			for(PersonnelMatiereClasse pmc:personnelMatiereClasseList) {
				for(ClasseMatiere cm: matiereCoefDefineList) {
					if(pmc.getMatiere().getId() == cm.getMatiere().getId()) {
						pmcListTmp.add(pmc);
					}
				}
			}
		}
		return pmcListTmp;
	}

	public List<PersonnelMatiereClasseDto> findDtoByProfesseurAndClasseWhereCoefDefine(List<PersonnelMatiereClasse> list){
		List<PersonnelMatiereClasseDto> dtos = new ArrayList<PersonnelMatiereClasseDto>();
		for(PersonnelMatiereClasse  pmc : list) {
			dtos.add(buildToDto(pmc));
		}
		return dtos;
	}

	PersonnelMatiereClasseDto buildToDto(PersonnelMatiereClasse pmc) {
		PersonnelMatiereClasseDto pmcDto = new PersonnelMatiereClasseDto();
		pmcDto.setAnnee(new IdLongCodeLibelleDto(pmc.getAnnee().getId(), null, pmc.getAnnee().getLibelle()));
		pmcDto.setClasse(new IdLongCodeLibelleDto(pmc.getClasse().getId(), null, pmc.getClasse().getLibelle()));
		pmcDto.setId(pmc.getId());
		pmcDto.setMatiere(new IdLongCodeLibelleDto(pmc.getMatiere().getId(), null, pmc.getMatiere().getLibelle()));
		pmcDto.setPersonel(new IdLongCodeLibelleDto(pmc.getPersonnel().getId(), null, null));
		return pmcDto;
	}



	public List<PersonnelMatiereClasse> findListByClasse(long annee, long classe) {
		List<PersonnelMatiereClasse> list = new ArrayList<PersonnelMatiereClasse>();
		try {
			list = PersonnelMatiereClasse.find("annee.id = ?1 and classe.id =?2 and matiere is not null and (statut is null or statut <> 'DELETED') ", annee, classe)
					.list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return addCoefficientMatiere(list);
	}

	List<PersonnelMatiereClasse> addCoefficientMatiere(List<PersonnelMatiereClasse> listPersonnels) {
		for(PersonnelMatiereClasse ps : listPersonnels) {
			ClasseMatiere cm = cmService.getByMatiereAndBranche(ps.getMatiere().getId(), ps.getClasse().getBranche().getId(), ps.getClasse().getEcole().getId());
			if(cm != null) {
				ps.getMatiere().setCoef(cm.getCoef());
			}
		}
		return listPersonnels;
	}

// Attention ne pas utiliser pour determiner les matieres enseignees par un professeur
	public List<PersonnelMatiereClasse> findListByFonction(long annee, long ecole, int fonctionId) {
		return PersonnelMatiereClasse
				.find("annee.id = ?1 and classe.ecole.id=?2 and personnel.fonction.id =?3 and matiere is null and (statut is null or statut <> 'DELETED') ", annee,
						ecole, fonctionId)
				.list();
	}

	// Attention ne pas utiliser pour determiner les matieres enseignees par un
	// professeur
	public List<PersonnelMatiereClasse> findListByPersonnel(Long annee, long ecole, long classe) {
		 List<PersonnelMatiereClasse> pmList = new ArrayList<>();

		 try {

			 pmList = PersonnelMatiereClasse.find("annee.id = ?1 and classe.ecole.id=?2 and classe.id =?3 and matiere is null and (statut is null or statut <> 'DELETED') ",
					 annee, ecole, classe).list();
		 }catch (RuntimeException e) {
			e.printStackTrace();
		}
		return pmList;
	}

	public ProfEducDto findListByPersonnel_v2(Long annee, long ecole, long classe) {
		List<PersonnelMatiereClasse> pmcList = new ArrayList<>();
		ProfEducDto peDto = new ProfEducDto();

		try {
			pmcList = PersonnelMatiereClasse.find(
					"annee.id = ?1 and classe.ecole.id=?2 and classe.id =?3 and matiere is null and (statut is null or statut <> 'DELETED') ",
					annee, ecole, classe).list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		for (PersonnelMatiereClasse p : pmcList) {
			if (p.getTypeFonction() != null && p.getTypeFonction().equals(Constants.PROFESSEUR_PRINCIPAL)) {
				peDto.setClasse(p.getClasse());
				peDto.setProf(p.getPersonnel());
			} else if (p.getTypeFonction() != null && p.getTypeFonction().equals(Constants.EDUCATEUR_CLASSE)) {
				peDto.setClasse(p.getClasse());
				peDto.setEducateur(p.getPersonnel());
			}
		}
		return peDto;
	}

	//Obtenir le prof princ

		public PersonnelMatiereClasse findProfPrinc(Long annee, long classe) {
			List<PersonnelMatiereClasse> pmcList = new ArrayList<>();
			PersonnelMatiereClasse pp = new PersonnelMatiereClasse();

			try {
				pmcList = PersonnelMatiereClasse.find(
						"annee.id = ?1 and classe.id =?2 and matiere is null and (statut is null or statut <> 'DELETED') ",
						annee, classe).list();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
			for (PersonnelMatiereClasse p : pmcList) {
				if (p.getTypeFonction() != null && p.getTypeFonction().equals(Constants.PROFESSEUR_PRINCIPAL)) {
					pp = p;
				}
			}
			return pp;
		}

		//Obtenir educateur

		public PersonnelMatiereClasse findEducateurClasse(Long annee, long classe) {
			List<PersonnelMatiereClasse> pmcList = new ArrayList<>();
			PersonnelMatiereClasse educ = new PersonnelMatiereClasse();

			try {
				pmcList = PersonnelMatiereClasse.find(
						"annee.id = ?1 and classe.id =?2 and matiere is null and (statut is null or statut <> 'DELETED') ",
						annee, classe).list();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
			for (PersonnelMatiereClasse p : pmcList) {
				if (p.getTypeFonction() != null && p.getTypeFonction().equals(Constants.EDUCATEUR_CLASSE)) {
					educ = p;
				}
			}
			return educ;
		}

	@Transactional
	public void save(PersonnelMatiereClasse persMatClasse) {
		logger.info("persist persMatClasse ...");

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
//		Ecole ecole;
		Boolean flat = true;
		// A supprimer lorsque le credential contenant l ecole sera disponible
		// Par defaut pour tout enregistrement on set l ecole id à 1
//		if (persMatClasse.getPersonnel() != null && persMatClasse.getPersonnel().getEcole() == null) {
//			ecole = new Ecole();
//			ecole.setId(1);
//			persMatClasse.getPersonnel().setEcole(ecole);
//		}
		persMatClasse.setDateCreation(new Date());
		profEducDto.setClasse(persMatClasse.getClasse());
		profOrEduclist = getIfExistProfOrEduc(persMatClasse);
		System.out.println("PersonnelMatiereClasseService.saveForEducAndProf() " + profOrEduclist.size());
		if (profOrEduclist != null && profOrEduclist.size() != 0) {
			for (PersonnelMatiereClasse profOrEduc : profOrEduclist) {
				System.out.println("PersonnelMatiereClasseService.saveForEducAndProf() "
						+ profOrEduc.getPersonnel().getFonction().getCode() + " - "
						+ persMatClasse.getPersonnel().getFonction().getCode());
				if (profOrEduc.getTypeFonction()!=null && persMatClasse.getTypeFonction()!=null &&
						profOrEduc.getTypeFonction().equals(persMatClasse.getTypeFonction())) {
					flat = false;
					if (persMatClasse.getPersonnel().getId() == 0)
						profOrEduc.delete();
					else {
						// penser à creer et mettre à jour le champ date_update
						PersonnelMatiereClasse.update("personnel.id = ?1, dateUpdate= ?2, user=?3  where id = ?4",
								persMatClasse.getPersonnel().getId(), new Date(), persMatClasse.getUser(), profOrEduc.getId());
					}
				}

			}
			if (flat)
				persMatClasse.persist();
		} else {
//			System.out.println(persMatClasse);
			persMatClasse.persist();
		}

	}

	public List<PersonnelMatiereClasse> getListProfOrEducByAnneeAndClasse(long annee, long classe) {
		List<PersonnelMatiereClasse> list = new ArrayList<>();
		try {
				list = PersonnelMatiereClasse.find("classe.id = ?1  and annee.id= ?2 and matiere is null and (statut is null or statut <> 'DELETED') ", classe, annee)
				.list();
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PersonnelMatiereClasse> getIfExist(PersonnelMatiereClasse persMatClasse) {
		return PersonnelMatiereClasse.find("classe.id = ?1 and personnel.id =?2 and matiere.id =?3 and annee.id= ?4 and (statut is null or statut <> 'DELETED') ",
				persMatClasse.getClasse().getId(), persMatClasse.getPersonnel().getId(),
				persMatClasse.getMatiere().getId(), persMatClasse.getAnnee().getId()).list();
	}

	public List<PersonnelMatiereClasse> getByMatiereAndClasseDispo(PersonnelMatiereClasse persMatClasse) {
		return PersonnelMatiereClasse.find("classe.id = ?1 and matiere.id =?2 and annee.id= ?3 and (statut is null or statut <> 'DELETED') ",
				persMatClasse.getClasse().getId(), persMatClasse.getMatiere().getId(), persMatClasse.getAnnee().getId())
				.list();
	}

	public List<PersonnelMatiereClasse> getIfExistProfOrEduc(PersonnelMatiereClasse persMatClasse) {

		System.out.println(String.format("candidat pour supression - classe %s | annee %s ", persMatClasse.getClasse().getId(),persMatClasse.getAnnee().getId()) );
		return PersonnelMatiereClasse.find("classe.id = ?1 and matiere is null and annee.id= ?2 and (statut is null or statut <> 'DELETED') ",
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

	@Transactional
	public void deleteByStatus(PersonnelMatiereClasse persMatClasse) {

		logger.info("delete by status persMatClasse id " + persMatClasse.getId());
		PersonnelMatiereClasse pmc= findById(persMatClasse.getId());
		pmc.setStatut(Constants.DELETED);
		pmc.setDateUpdate(new Date());
		pmc.setUser(persMatClasse.getUser());

	}

	public long countProfByMatiereAndEcole(Long ecoleId, Long matiereId, Long anneeId) {
		try {
		 return PersonnelMatiereClasse.find("select distinct p.personnel from PersonnelMatiereClasse p where p.classe.ecole.id = ?1 and p.matiere.id =?2 and p.annee.id= ?3 and (statut is null or statut <> 'DELETED') ",
				ecoleId, matiereId, anneeId)
				.count();
		 }catch(RuntimeException r) {
			 r.printStackTrace();
			 return 0;
		 }
	}

}

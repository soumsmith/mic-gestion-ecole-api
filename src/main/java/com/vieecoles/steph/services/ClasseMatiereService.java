package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.vieecoles.steph.entities.Branche;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.ClasseMatiere;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.EcoleHasMatiere;
import com.vieecoles.steph.entities.Matiere;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class ClasseMatiereService implements PanacheRepositoryBase<ClasseMatiere, Long> {

	@Inject
	ClasseService classeService;

	@Inject
	EcoleService ecoleService;

	@Inject
	BrancheService brancheService;

	@Inject
	MatiereService matiereService;

	@Inject
	EcoleHasMatiereService ecoleHasMatiereService;

	Logger logger = Logger.getLogger(ClasseMatiereService.class.getName());

	public List<ClasseMatiere> getById(Long id) {
		return ClasseMatiere.findById(id);
	}

	public List<ClasseMatiere> list() {
		return ClasseMatiere.findAll().list();
	}

	/*
	 * Retourne l'ensemble des matieres liées à une branche avec le coeficient
	 * defini ou non
	 */
	public List<ClasseMatiere> getAllMatieresByBrancheViaClasse(Long brancheId, Long ecoleId) {
		List<EcoleHasMatiere> ehmList = ecoleHasMatiereService.getListByEcole(ecoleId);
		List<ClasseMatiere> cmList = getByBranche(brancheId, ecoleId);
		List<ClasseMatiere> cmToReturn = new ArrayList<ClasseMatiere>();
		Boolean isDefined = false;
		Branche branche = new Branche();
		Ecole ecole = new Ecole();
		branche = brancheService.findById(brancheId);
		ecole = ecoleService.findById(ecoleId);
		//liste des matiere à coef non defini
		for(EcoleHasMatiere ehm : ehmList) {
			for(ClasseMatiere cm : cmList) {
//				System.out.println(String.format("%s %s  --- %s %s", ehm.getLibelle(),ehm.getId(),cm.getMatiere().getId(),cm.getMatiere().getLibelle()));
				if(ehm.getId() == cm.getMatiere().getId()) {
					isDefined = true;
					break;
				}
			}
			if(!isDefined) {
				ClasseMatiere cmat = new ClasseMatiere();

				cmat.setBranche(branche);
//				cmat.setCoef(null);
				cmat.setEcole(ecole);
				cmat.setMatiere(ehm);
				cmToReturn.add(cmat);
			}
			isDefined = false;
		}
		//Ajout des matiere à coef defini
		cmToReturn.addAll(cmList);
		System.out.println(cmToReturn.size());
		List<ClasseMatiere> cmTmpWithCoef = sortedClasseMatiereList(cmToReturn);
		return cmTmpWithCoef;
	}

//		Tri pour afficher les matieres à coef non defini en premier dans la liste
	public List<ClasseMatiere> sortedClasseMatiereList(List<ClasseMatiere> cmToReturn) {
		List<ClasseMatiere> cmTmp = new ArrayList<ClasseMatiere>();
		List<ClasseMatiere> cmTmpWithCoef = new ArrayList<ClasseMatiere>();

		for(ClasseMatiere cm: cmToReturn) {
			if(cm.getCoef()!=null)
				cmTmpWithCoef.add(cm);
			else
				cmTmp.add(cm);
		}
		cmTmpWithCoef.addAll(cmTmp);
		return cmTmpWithCoef;
	}

	public List<ClasseMatiere> getByEcole(long ecoleId) {
		System.out.println("Ecole ::: " + ecoleId);
		return ClasseMatiere.find("select distinct m.matiere.libelle from ClasseMatiere m where m.matiere.ecole.id = ?1", ecoleId).list();
	}

	public List<ClasseMatiere> getByBranche(long brancheId, long ecoleId) {
		System.out.println("branche :: " + brancheId + " ecole ::: " + ecoleId);
		return ClasseMatiere.find("branche.id = ?1 and ecole.id=?2", brancheId, ecoleId).list();
	}

	public ClasseMatiere getByMatiereAndBranche(long matiereId, long brancheId, long ecoleId) {
		ClasseMatiere cm = null;

		try {
			cm = ClasseMatiere
					.find("branche.id = ?1 and ecole.id=?2 and matiere.id = ?3", brancheId, ecoleId, matiereId)
					.singleResult();
		} catch (RuntimeException ex) {
			return null;
		}
		return cm;
	}

	public List<ClasseMatiere> getByBrancheViaClasse(long classeId) {
		Classe classe = classeService.findById(classeId);
		System.out.println(classe);
		return getByBranche(classe.getBranche().getId(), classe.getEcole().getId());
	}

	@Transactional
	public void create(ClasseMatiere classeMatiere) {
		classeMatiere.persist();
	}

	/*
	 * Creéer les coefficients de chaque matières pour chaque école avec la valeur 1
	 * par defaut OBSOLETE
	 */
	@Transactional
	public void generateDefaultCoeficientsByEcole(Long ecoleId) {
		// Obtenir le niveau de l'école
		Ecole ecole = ecoleService.getById(ecoleId);

		List<Matiere> matieres = matiereService.getByNiveauEnseignement(ecole.getNiveauEnseignement().getId());

		List<Branche> branches = brancheService.findByNiveauEnseignementViaEcole(ecoleId);
		for (Branche b : branches) {
			for (Matiere mat : matieres) {
				if (null == getByMatiereAndBranche(mat.getId(), b.getId(), ecoleId)) {
					ClasseMatiere cm = new ClasseMatiere();
					cm.setBranche(b);
					cm.setEcole(ecole);
					// cm.setMatiere(mat);
					cm.setCoef("1");
					cm.persist();
					System.out.println(String.format("---> Création ecole %s ; matiere %s ; branche %s ; coef 1",
							ecole.getLibelle(), mat.getLibelle(), b.getLibelle()));
				}
			}
		}

	}

	/*
	 * Créer pour chaque ecole les coeficients de la matière et pour chaque branche
	 * lors de la création d'une matière en centrale
	 */
	@Transactional
	public void handleCreateMatiereToBranche(ClasseMatiere classeMatiere) {
		logger.info("-> Création des coefficients pour la matière : " + classeMatiere.getMatiere().getLibelle());
		List<Branche> branches = brancheService.findByNiveauEnseignementViaEcole(classeMatiere.getEcole().getId());
		for (Branche branche : branches) {
			ClasseMatiere cm = new ClasseMatiere();
			cm.setEcole(classeMatiere.getEcole());
			cm.setMatiere(classeMatiere.getMatiere());
			cm.setCoef(classeMatiere.getCoef());
			cm.setBranche(branche);
			create(cm);
			logger.info("-> Branche : " + cm.getBranche().getLibelle() + " [ok]");
		}
	}

	@Transactional
	public void handleSaveOrUpdate(List<ClasseMatiere> classeMatieres) {
		for (ClasseMatiere cl : classeMatieres) {
			if (cl.getCoef() == null)
				throw new RuntimeException("Un coefficient ne peut etre vide");
			else if (cl.getCoef().equals(0))
				throw new RuntimeException("Un coefficient ne peut etre nul");
			if (cl.getId() != 0) {
				ClasseMatiere.update("coef = ?1, matiere.id= ?2, branche.id=?3, ecole.id=?4 where id = ?5",
						cl.getCoef(), cl.getMatiere().getId(), cl.getBranche().getId(), cl.getEcole().getId(),
						cl.getId());
				logger.info("Mise à jour matiere - branche id [" + cl.getId() + "]");
			} else {
				ClasseMatiere classeMatiere = null;
				try {
					classeMatiere = ClasseMatiere.find("matiere.id =?1 and branche.id=?2 and ecole.id =?3",
							cl.getMatiere().getId(), cl.getBranche().getId(), cl.getEcole().getId()).singleResult();
					System.out.println(classeMatiere);
				} catch (RuntimeException ex) {
					logger.info(ex.getMessage());
					if (!ex.getClass().getName().equals(NoResultException.class.getName()))
						throw new RuntimeException(ex.getMessage());
				}
				if (classeMatiere != null) {
					throw new RuntimeException("Cet enregistrement existe déjà!");

				} else {
					ClasseMatiere.persist(cl);
					logger.info("Creation matiere - branche new Id [" + cl.getId() + "]");
				}
			}
		}

	}
}

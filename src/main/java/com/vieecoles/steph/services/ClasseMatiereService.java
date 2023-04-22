package com.vieecoles.steph.services;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.vieecoles.steph.entities.Branche;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.ClasseMatiere;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class ClasseMatiereService implements PanacheRepositoryBase<ClasseMatiere, Long> {

	@Inject
	ClasseService classeService;
	
	@Inject
	BrancheService brancheService;

	Logger logger = Logger.getLogger(ClasseMatiereService.class.getName());

	public List<ClasseMatiere> getById(Long id) {
		return ClasseMatiere.findById(id);
	}

	public List<ClasseMatiere> list() {
		return ClasseMatiere.findAll().list();
	}

	public List<ClasseMatiere> getByBranche(long brancheId, long anneeId, long ecoleId) {
		return ClasseMatiere.find("branche.id = ?1 and ecole.id=?2", brancheId, ecoleId).list();
	}

	public List<ClasseMatiere> getByBrancheViaClasse(long classeId, long anneeId, long ecoleId) {
		Classe classe = classeService.findById(classeId);
		return getByBranche(classe.getBranche().getId(), anneeId, ecoleId);
	}
	
	@Transactional
	public void create(ClasseMatiere classeMatiere) {
		 classeMatiere.persist();
	}
	/*
	 * Créer pour chaque ecole les coeficients de la matière et pour chaque branche
	 * lors de la création d'une matière en centrale
	 */
	@Transactional
	public void handleCreateMatiereToBranche(ClasseMatiere classeMatiere) {
		logger.info("-> Création des coefficients pour la matière : "+classeMatiere.getMatiere().getLibelle());
		List<Branche> branches = brancheService.findByNiveauEnseignementViaEcole(classeMatiere.getEcole().getId());
		for(Branche branche : branches) {
			ClasseMatiere cm = new ClasseMatiere();
			cm.setEcole(classeMatiere.getEcole());
			cm.setMatiere(classeMatiere.getMatiere());
			cm.setCoef(classeMatiere.getCoef());
			cm.setBranche(branche);
			create(cm);
			logger.info("-> Branche : "+cm.getBranche().getLibelle()+" [ok]");
		}
	}

	@Transactional
	public void handleSaveOrUpdate(List<ClasseMatiere> classeMatieres) {
		for (ClasseMatiere cl : classeMatieres) {
			if(cl.getCoef() == null)
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
					
				}else {
					ClasseMatiere.persist(cl);
					logger.info("Creation matiere - branche new Id [" + cl.getId() + "]");
				}
			}
		}

	}
}

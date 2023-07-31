package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.vieecoles.entities.utilisateur;
import com.vieecoles.services.utilisateurService;
import com.vieecoles.steph.entities.Personnel;

@ApplicationScoped
public class PersonnelService {
	
	@Inject
	utilisateurService utilisateurService;
	
	Logger logger = Logger.getLogger(Personnel.class.getName());
	
	public Personnel getById(Long id) {
		logger.info("find by id ::: "+id);
		return Personnel.findById(id);
	}

	public List<Personnel> getList() {
		try {
			return Personnel.listAll();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Personnel>();
		}
	}

	
	
	public List<Personnel> getListByFonction(int fonctionId, Long ecole) {
		logger.info("---> list by fonction id ::: "+fonctionId);
		return Personnel.find("fonction.id = ?1 and ecole.id=?2", fonctionId, ecole)
				.list();
	}	
	public List<Personnel> getListByFonctionAndClasse(Long fonctionId, Long classeId) {
		return Personnel.find("fonction.id = ?1 and classe.id=?2", fonctionId, classeId)
				.list();
	}
	
	public Personnel getByUserId(Long id) {
		logger.info("find by user id ::: "+id);
		utilisateur user = utilisateur.findById(id);
		if(user != null) {
			try {
				return Personnel.find("sous_attent_personn_sous_attent_personnid = ?1", user.getSous_attent_personn_sous_attent_personnid()).singleResult();
			}catch (RuntimeException e) {
				logger.log(Level.WARNING, "Erreur getByUserId {0}", e.getMessage());
			}
		}
		return null;
	}

}

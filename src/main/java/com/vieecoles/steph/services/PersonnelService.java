package com.vieecoles.steph.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.vieecoles.entities.utilisateur;
import com.vieecoles.services.utilisateurService;
import com.vieecoles.steph.entities.Personnel;

@ApplicationScoped
public class PersonnelService {

	@Inject
	utilisateurService utilisateurService;

	@Inject
	EntityManager em;

	Logger logger = Logger.getLogger(Personnel.class.getName());

	public Personnel getById(Long id) {
		logger.info("find by id ::: " + id);
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
		logger.info("---> list by fonction id ::: " + fonctionId + " Ecole ::: " + ecole);
		return Personnel.find("fonction.id = ?1 and ecole.id=?2", fonctionId, ecole).list();
	}

	public Long countByFonction(int fonctionId, Long ecole) {
		logger.info("---> list by fonction id ::: " + fonctionId + " Ecole ::: " + ecole);
		return Personnel.find("fonction.id = ?1 and ecole.id=?2", fonctionId, ecole).count();
	}

	public List<Personnel> getListByFonctionAndClasse(Long fonctionId, Long classeId) {
		return Personnel.find("fonction.id = ?1 and classe.id=?2", fonctionId, classeId).list();
	}

	public Personnel getByUserId(Long id) {
		logger.info("find by user id ::: " + id);
		utilisateur user = utilisateur.findById(id);
		if (user != null) {
			try {
				return Personnel.find("sous_attent_personn_sous_attent_personnid = ?1",
						user.getSous_attent_personn_sous_attent_personnid()).firstResult();
			} catch (RuntimeException e) {
				logger.log(Level.WARNING, "Erreur getByUserId {0}", e.getMessage());
			}
		}
		return null;
	}

	public Personnel getByUserAndEcole(Long userId, Long ecoleId) {
		logger.info("find by user id ::: " + userId + " and ecole id ::: " + ecoleId);
		utilisateur user = utilisateur.findById(userId);
		if (user != null) {
			try {
				return Personnel.find("sous_attent_personn_sous_attent_personnid = ?1 and ecole.ecoleid = ?2",
						user.getSous_attent_personn_sous_attent_personnid(), ecoleId).firstResult();
			} catch (RuntimeException e) {
				logger.log(Level.WARNING, "Erreur getByUserId {0}", e.getMessage());
			}
		}
		return null;
	}

	public List<Personnel> getByEcole(Long ecoleId) {
		logger.info("find by ecole id ::: " + ecoleId);
		try {
			return Personnel.find("ecole.id = ?1", ecoleId).list();
		} catch (RuntimeException e) {
			logger.log(Level.WARNING, "Erreur getByUserId {0}", e.getMessage());
		}
		return null;
	}

	public Long countByEcole(Long ecoleId) {
		logger.info("countByEcole ecole id ::: " + ecoleId);
		try {
			return Personnel.find("ecole.id = ?1", ecoleId).count();
		} catch (RuntimeException e) {
			logger.log(Level.WARNING, "Erreur getByUserId {0}", e.getMessage());
		}
		return (long) 0;
	}
	
	public Long countByEcoleAndFonctionAndStatut(Long ecoleId, int fonction, int statut) {
		logger.info("countByEcoleAndFonctionAndStatut ecole id ::: " + ecoleId + " "+fonction+" "+statut);
		try {
			return Personnel.find("ecole.id = ?1 and fonction.id=?2 and statut=?3", ecoleId,fonction, statut).count();
		} catch (RuntimeException e) {
			logger.log(Level.WARNING, "Erreur countByEcoleAndFonctionAndStatut {0}", e.getMessage());
		}
		return (long) 0;
	}

	public Long countByGenreAndFonction(Long ecole, Integer fonction, String genre) {
		Query query = em.createNamedQuery("Personnel.countbyEcoleAndGenreAndFonction");
		query.setParameter("ecoleId", ecole);
		query.setParameter("fonctionId", fonction);
		query.setParameter("sexeId", genre);
		try {
			BigInteger count = (BigInteger) query.getSingleResult();
			return Long.parseLong(count.toString());
		} catch (NoResultException ex) {
			ex.getMessage();
			return (long) 0;
		}
	}
	
	public List<Personnel> getByEcoleAndProfil(Long ecole, Integer profil) {
		TypedQuery<Personnel> query = em.createNamedQuery("Personnel.getByEcoleAndProfil", Personnel.class);
		query.setParameter("ecoleId", ecole);
		query.setParameter("profilId", profil);
		try {
			List<Personnel> personnels = query.getResultList();
			System.out.println(personnels);
			return  personnels;
		} catch (NoResultException ex) {
			ex.getMessage();
			return new ArrayList<Personnel>();
		}
	}
	
	public List<Personnel> getByEcoleAndProfil_v2(Long ecole, Integer profil) {
		try {
			Long profilId = Long.parseLong(profil.toString());
			List<Personnel> personnels =Personnel.find("select p from Personnel p left join utilisateur_has_personnel up on p.id = up.personnel_personnelid where "
					+ " p.ecole.id = ?1 and up.profil.profilid = ?2 ", ecole, profilId).list();
			System.out.println(personnels);
			return  personnels;
		} catch (NoResultException ex) {
			ex.getMessage();
			return new ArrayList<Personnel>();
		}
	}

	public Long countByGenreAndFonctionAndStatut(Long ecole, Integer fonction, String genre, int statut) {
		Query query = em.createNamedQuery("Personnel.countbyEcoleAndGenreAndFonctionAndStatut");
		query.setParameter("ecoleId", ecole);
		query.setParameter("fonctionId", fonction);
		query.setParameter("sexeId", genre);
		query.setParameter("statut", statut);
		try {
			BigInteger count = (BigInteger) query.getSingleResult();
			return Long.parseLong(count.toString());
		} catch (NoResultException ex) {
			ex.getMessage();
			return (long) 0;
		}
	}
}

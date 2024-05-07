package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;

import com.vieecoles.steph.entities.Bulletin;
import com.vieecoles.steph.entities.DetailBulletin;

@RequestScoped
public class DetailBulletinService {

	Logger logger = Logger.getLogger(DetailBulletinService.class.getName());

	public DetailBulletin getDetailBulletinsEleveByBulletinAndMatiere(Long bulletinId, String matiereCode) {

		DetailBulletin myDetail = new DetailBulletin();

		try {
			myDetail = DetailBulletin.find("bulletin.id = ?1 and matiereCode = ?2", bulletinId, matiereCode)
					.singleResult();
			logger.info(" Detail trouvé pour l'élève de matricule " + myDetail.getBulletin().getMatricule());
		} catch (RuntimeException ex) {
			if (ex.getClass().getName().equals(NoResultException.class.getName()))
				logger.info("Aucun Detail bulletin trouvé ");
			else
				ex.printStackTrace();
		}

		return myDetail;
	}

	public List<DetailBulletin> getByBulletin(String bullrtinId) {
		List<DetailBulletin> list = new ArrayList<DetailBulletin>();
		try {
			list = DetailBulletin.find("bulletin.id = ?1 ", bullrtinId).list();
		} catch (Exception e) {
			if (e.getClass().getName().equals(NoResultException.class.getName())) {
				logger.warning("DetailBulletinService - getByBulletin -Aucune donnée trouvée pour l'id bulletin ::: "
						+ bullrtinId);
			} else {
				e.printStackTrace();
			}
		}
		return list;
	}

	public List<DetailBulletin> getDetailBulletinsEleveByAnneeAndClasseAndMatiere(Long anneeId, Long ClasseId,
			String matricule, Long matiereId) {

		List<DetailBulletin> myDetails = new ArrayList<DetailBulletin>();
//		System.out.println(anneeId + " " + ClasseId + " " + matricule + " " + matiereId);

		try {
			myDetails = DetailBulletin.find(
					"bulletin.anneeId = ?1 and bulletin.classeId = ?2 and bulletin.matricule=?3 and matiereRealId = ?4",
					anneeId, ClasseId, matricule, matiereId).list();
			logger.info(myDetails.size() + " Detail(s) trouvé(s) pour l'élève de matricule : " + matricule);
		} catch (RuntimeException ex) {
			if (ex.getClass().getName().equals(NoResultException.class.getName()))
				logger.info("Aucun Detail bulletin trouvé pour l'élève de matricule : " + matricule);
			else
				ex.printStackTrace();
		}
//		System.out.println("SIZE :::::: "+myDetails.size());
		return myDetails;
	}
}

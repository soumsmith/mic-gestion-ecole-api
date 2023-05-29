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

	public List<DetailBulletin> getDetailBulletinsEleveByAnneeAndClasseAndMatiere(Long anneeId, Long ClasseId,String matricule, String matiereCode) {

		List<DetailBulletin> myDetails = new ArrayList<DetailBulletin>();
//		System.out.println(anneeId+" "+ClasseId+" "+matricule+" "+matiereCode);

		try {
			myDetails = DetailBulletin.find("bulletin.anneeId = ?1 and bulletin.classeId = ?2 and bulletin.matricule=?3 and matiereCode = ?4", anneeId,ClasseId,matricule, matiereCode)
					.list();
			logger.info(myDetails.size()+" Detail(s) trouvé(s) pour l'élève de matricule : " + matricule);
		} catch (RuntimeException ex) {
			if (ex.getClass().getName().equals(NoResultException.class.getName()))
				logger.info("Aucun Detail bulletin trouvé pour l'élève de matricule : " + matricule);
			else
				ex.printStackTrace();
		}

		return myDetails;
	}
}

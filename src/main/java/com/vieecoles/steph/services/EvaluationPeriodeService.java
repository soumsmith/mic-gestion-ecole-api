package com.vieecoles.steph.services;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;

import com.vieecoles.steph.entities.EvaluationPeriode;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@RequestScoped
public class EvaluationPeriodeService implements PanacheRepositoryBase<EvaluationPeriode, String> {

	Logger logger = Logger.getLogger(this.getClass().getName());

	public EvaluationPeriode findByAnneeAndEcoleAndPeriodeAndNiveau(Long anneeId, Long ecoleId, Long periodeId,
			Long niveauId) {
		EvaluationPeriode obj = null;
		try {
			obj = EvaluationPeriode.find("annee.id = ?1 and ecole.id =?2 and periode.id = ?3 and niveau.id=?4", anneeId,
					ecoleId, periodeId, niveauId).singleResult();
			logger.info(String.format("Année = %s , ecole= %s , periode = %s , niveau = %s", anneeId, ecoleId, periodeId, niveauId));
			logger.info(String.format(">>>>>  Evaluation par periode ::: %s  <<<<<", obj.getTypeEvaluation().getLibelle()));
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info(">>>>>  Evaluation par periode non definie  <<<<<");
		}
		return obj;
	}
}

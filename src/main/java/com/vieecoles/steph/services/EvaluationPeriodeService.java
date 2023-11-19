package com.vieecoles.steph.services;

import javax.enterprise.context.RequestScoped;

import com.vieecoles.steph.entities.EvaluationPeriode;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@RequestScoped
public class EvaluationPeriodeService implements PanacheRepositoryBase<EvaluationPeriode, String> {

	public EvaluationPeriode findByAnneeAndEcoleAndPeriodeAndNiveau(Long anneeId, Long ecoleId, Long periodeId,
			Long niveauId) {
		EvaluationPeriode obj = null;
		try {
			obj = EvaluationPeriode.find("annee.id = ?1 and ecole.id =?2 and periode.id = ?3 and niveau=?4", anneeId,
					ecoleId, periodeId, niveauId).singleResult();
		} catch (RuntimeException e) {
			e.getMessage();
		}
		return obj;
	}
}

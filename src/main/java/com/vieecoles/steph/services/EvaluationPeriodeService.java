package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;

import org.hibernate.internal.build.AllowSysOut;

import com.vieecoles.steph.entities.EvaluationPeriode;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@RequestScoped
public class EvaluationPeriodeService implements PanacheRepositoryBase<EvaluationPeriode, String> {

	Logger logger = Logger.getLogger(this.getClass().getName());

	public EvaluationPeriode findByAnneeAndEcoleAndPeriodeAndNiveau(Long anneeId, Long ecoleId, Long periodeId,
			Long niveauId) {
		EvaluationPeriode obj = null;
		try {
			obj = EvaluationPeriode.find("annee.id = ?1 and ecole.id =?2 and periode.id = ?3 and niveau.id=?4 order by niveau.id, periode.niveau", anneeId,
					ecoleId, periodeId, niveauId).singleResult();
			logger.info(String.format("Année = %s , ecole= %s , periode = %s , niveau = %s", anneeId, ecoleId,
					periodeId, niveauId));
			logger.info(
					String.format(">>>>>  Evaluation par periode ::: %s  <<<<<", obj.getTypeEvaluation().getLibelle()));
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info(">>>>>  Evaluation par periode non definie  <<<<<");
		}
		return obj;
	}

	public List<EvaluationPeriode> findByAnneeAndEcoleAndNiveau(Long anneeId, Long ecoleId, Long niveauId) {
		List<EvaluationPeriode> list = new ArrayList<>();
		try {
			list = EvaluationPeriode.find("annee.id = ?1 and ecole.id =?2 and niveau.id=?3 order by niveau.id, periode.niveau", anneeId, ecoleId, niveauId)
					.list();
			logger.info(String.format("Année = %s , ecole= %s , niveau = %s", anneeId, ecoleId, niveauId));
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info(">>>>>  Evaluation par periode non definie  <<<<<");
		}
		return list;
	}

	public List<EvaluationPeriode> findByAnneeAndEcole(Long anneeId, Long ecoleId) {
		List<EvaluationPeriode> list = new ArrayList<>();
		try {
			list = EvaluationPeriode.find("annee.id = ?1 and ecole.id =?2 order by niveau.id, periode.niveau", anneeId, ecoleId).list();
			logger.info(String.format("Année = %s , ecole= %s ", anneeId, ecoleId));
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info(">>>>>  Evaluation par periode non definie  <<<<<");
		}
		return list;
	}
	@Transactional
	public void create(EvaluationPeriode evaluationPeriode) {
		UUID uuid = UUID.randomUUID();
		evaluationPeriode.setDateCreation(new Date());
		evaluationPeriode.setDateUpdate(new Date());
		evaluationPeriode.setId(uuid.toString());
		evaluationPeriode.persist();
	}
	@Transactional
	public void update(EvaluationPeriode evaluationPeriode, String id) {
		EvaluationPeriode ev = EvaluationPeriode.findById(id);
		if (ev != null) {
			ev.setEcole(evaluationPeriode.getEcole());
			ev.setAnnee(evaluationPeriode.getAnnee());
			ev.setNiveau(evaluationPeriode.getNiveau());
			ev.setPeriode(evaluationPeriode.getPeriode());
			ev.setTypeEvaluation(evaluationPeriode.getTypeEvaluation());
			ev.setNumero(evaluationPeriode.getNumero());
			ev.setUser(evaluationPeriode.getUser());
			ev.setDateUpdate(new Date());
		}
	}

	public EvaluationPeriode getById(String id) {
		return EvaluationPeriode.findById(id);
	}

	@Transactional
	public String saveHandle(EvaluationPeriode evaluationPeriode) {
		EvaluationPeriode eval = findByAnneeAndEcoleAndPeriodeAndNiveau(evaluationPeriode.getAnnee().getId(),
				evaluationPeriode.getEcole().getId(), evaluationPeriode.getPeriode().getId(),
				evaluationPeriode.getNiveau().getId());
		if (eval == null) {
			logger.info("--> Enregistrement evaluation periode ecole ::: " + evaluationPeriode.getEcole().getId());
			create(evaluationPeriode);
			System.out.println("Id ::: "+evaluationPeriode.getId());
			evaluationPeriode = findById(evaluationPeriode.getId());
		} else {
			logger.info("--> Modification evaluation periode ecole ::: " + evaluationPeriode.getEcole().getId());
			update(evaluationPeriode, eval.getId());
			System.out.println("Id ::: "+eval.getId());
			evaluationPeriode = findById(eval.getId());
		}

		return eval != null ? eval.getId() : evaluationPeriode.getId();
	}

	@Transactional
	public void delete(String id) {
		EvaluationPeriode.deleteById(id);
	}
}

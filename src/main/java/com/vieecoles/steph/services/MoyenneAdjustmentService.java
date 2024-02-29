package com.vieecoles.steph.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import com.vieecoles.steph.entities.MoyenneAdjustment;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@RequestScoped
public class MoyenneAdjustmentService implements PanacheRepositoryBase<MoyenneAdjustment, String> {

	Logger logger = Logger.getLogger(this.getClass().getName());

	public MoyenneAdjustment getByAnneePeriodeMatriculeAndMatiere(Long annee, Long periode, String matricule, Long matiere) {
		MoyenneAdjustment moyAdj = new MoyenneAdjustment();
		try {
			moyAdj = MoyenneAdjustment.find("annee = ?1 and periode = ?2 and matricule = ?3 and matiere = ?4", annee, periode, matricule, matiere)
					.singleResult();
		} catch (RuntimeException e) {
			logger.warning(e.getMessage());
		}
		return moyAdj;
	}

	public MoyenneAdjustment getById(String id) {
		MoyenneAdjustment moyAdj = new MoyenneAdjustment();
		try {
			moyAdj = MoyenneAdjustment.findById(id);
		} catch (RuntimeException e) {
			logger.warning(e.getMessage());
		}
		return moyAdj;
	}

	@Transactional
	public void save(MoyenneAdjustment moyenneAdjustment) {
		try {
			System.out.println("Annee : "+moyenneAdjustment.getAnnee());
			UUID uuid = UUID.randomUUID();
			moyenneAdjustment.setId(uuid.toString());
			moyenneAdjustment.setDateCreation(new Date());
			moyenneAdjustment.setDateUpdate(new Date());
			moyenneAdjustment.persist();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void update(MoyenneAdjustment moyenneAdjustment) {
		try {
			MoyenneAdjustment moyenneAdjust = findById(moyenneAdjustment.getId());
			moyenneAdjust.setMoyenne(moyenneAdjustment.getMoyenne());
			moyenneAdjust.setStatut(moyenneAdjustment.getStatut());
			moyenneAdjust.setDateUpdate(new Date());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void handleSave(List<MoyenneAdjustment> list) {
		if (list != null) {
			for (MoyenneAdjustment ma : list) {
				if (ma.getMoyenne()!= null && ma.getMoyenne() != 0.0) {
					MoyenneAdjustment moyenneAdjustment = getByAnneePeriodeMatriculeAndMatiere(ma.getAnnee(), ma.getPeriode(),
							ma.getMatricule(), ma.getMatiere());
					if (moyenneAdjustment.getId() != null) {
						System.out.println("Update");
						ma.setId(moyenneAdjustment.getId());
						update(ma);
					} else {
						System.out.println("save");
						save(ma);
					}
				}
			}
		} else {
			throw new RuntimeException("Opération non effectuée - aucune donnée reçue");
		}
	}
}

package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.jboss.logmanager.Level;

import com.vieecoles.steph.entities.AbsenceEleve;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@RequestScoped
public class AbsenceService implements PanacheRepositoryBase<AbsenceEleve, String> {

	Logger logger = Logger.getLogger(this.getClass().getName());

	public AbsenceEleve getById(String id) {
		return findById(id);
	}

	public AbsenceEleve getByAnneeAndEleveAndPeriode(Long annee, Long eleve, Long periode) {
		AbsenceEleve abs = null;
		try {
			abs = AbsenceEleve.find("annee.id=?1 and eleve.id=?2 and periode.id=?3", annee, eleve, periode)
					.singleResult();
		} catch (RuntimeException ex) {
			if (ex.getClass().getName().equals(NoResultException.class.getName()))
				logger.warning("No Result Exception Found");
			else
				ex.printStackTrace();
		}
		return abs;
	}
	
	public List<AbsenceEleve> getListByClasseAndAnneeAndPeriode(Long annee, Long periode) {
		List<AbsenceEleve> list;
		try {
			list = AbsenceEleve.find("annee.id=?1 and periode.id=?2", annee, periode)
					.list();
		} catch (RuntimeException ex) {
			if (ex.getClass().getName().equals(NoResultException.class.getName())) {
				logger.warning("No Result Exception Found");
			} else {
				ex.printStackTrace();
			}
			list = new ArrayList<AbsenceEleve>();
		}
		return list;
	}

	@Transactional
	public void create(AbsenceEleve abs) {
		try {
			String uuid = UUID.randomUUID().toString();
			abs.setId(uuid);
			abs.persist();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			logger.log(Level.ERROR, ex.getMessage());
		}
	}

	@Transactional
	public void update(AbsenceEleve abs) {
		try {
			AbsenceEleve obj = getById(abs.getId());
			if (obj != null) {
				obj.setAbsJustifiee(abs.getAbsJustifiee());
				obj.setAbsNonJustifiee(abs.getAbsNonJustifiee());
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			logger.log(Level.ERROR, ex.getMessage());
		}
	}

	public String handleUpdateOrCreate(List<AbsenceEleve> absList) {
//		Gson g = new Gson();
//		System.out.println(g.toJson(absList));
		for (AbsenceEleve abs : absList) {
			AbsenceEleve absence = getByAnneeAndEleveAndPeriode(abs.getAnnee().getId(), abs.getEleve().getId(),
					abs.getPeriode().getId());
			if (absence != null) {
				abs.setId(absence.getId());
				if(abs.getAbsJustifiee() == null)
					abs.setAbsJustifiee(absence.getAbsJustifiee() == null ? 0 : absence.getAbsJustifiee());
				if(abs.getAbsNonJustifiee() == null)
					abs.setAbsNonJustifiee(absence.getAbsNonJustifiee() == null ? 0 : absence.getAbsNonJustifiee());
				update(abs);
			} else {
				if(abs.getAbsJustifiee() == null)
					abs.setAbsJustifiee(0);
				if(abs.getAbsNonJustifiee() == null)
					abs.setAbsNonJustifiee(0);
				create(abs);
			}
		}

		return String.format("%s lignes sauvegardée(s)", absList != null ? absList.size() : 0);
	}
}

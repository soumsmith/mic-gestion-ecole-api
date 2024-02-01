package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;

import com.vieecoles.steph.entities.DetailAppelNumerique;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@RequestScoped
public class DetailAppelNumeriqueService implements PanacheRepositoryBase<DetailAppelNumerique, String> {

	Logger logger = Logger.getLogger(this.getClass().getName());

	public List<DetailAppelNumerique> getByAppel(String appelId) {
		List<DetailAppelNumerique> details = new ArrayList<>();
		try {
			details = DetailAppelNumerique.find("appelNumerique.id = ?1 ", appelId).list();
		} catch (RuntimeException e) {
			logger.warning(e.getMessage());
		}
		return details;
	}
}

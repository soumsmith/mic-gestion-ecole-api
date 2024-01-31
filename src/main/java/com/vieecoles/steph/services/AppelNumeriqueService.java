package com.vieecoles.steph.services;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;


import com.vieecoles.steph.entities.AppelNumerique;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@RequestScoped
public class AppelNumeriqueService implements PanacheRepositoryBase<AppelNumerique, UUID>{

	public List<AppelNumerique> getAll() {
		return AppelNumerique.listAll();
	}
	
	@Transactional
	public UUID save(AppelNumerique appel) {
		appel.persist();
		return appel.getId();
	}
	
	public AppelNumerique getById(UUID uuid) {
		return AppelNumerique.findById(uuid);
	}
	@Transactional
	public void update(AppelNumerique appel) {
		AppelNumerique appelDb = findById(appel.getId());
		appelDb.setCode(null);
		appelDb.setDate(null);
		appelDb.setHeureDebut(null);
		appelDb.setHeureFin(null);
		appelDb.setPersonnel(null);
		appelDb.setProgression(null);
		appelDb.setSeance(null);
		appelDb.setEcole(null);
		appelDb.setCommentaire(null);
	}
}

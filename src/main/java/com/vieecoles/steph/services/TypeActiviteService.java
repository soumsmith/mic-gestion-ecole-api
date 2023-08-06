package com.vieecoles.steph.services;

import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.TypeActivite;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class TypeActiviteService implements PanacheRepositoryBase<TypeActivite, Long>{

	@Inject
	EcoleService ecoleService;
	
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	public List<TypeActivite> getAll() {
		return TypeActivite.listAll();
	}
	
	public List<TypeActivite> getByEcole(Long ecoleId) {
		Ecole ecole = ecoleService.getById(ecoleId);
		List<TypeActivite> types = new ArrayList<TypeActivite>();
		try {
			types = TypeActivite.find("niveauEnseignement = ?1", ecole.getNiveauEnseignement().getId()).list();
		}catch (RuntimeException e) {
			logger.log(Level.WARNING, "Erreur ::: {0}", e.getCause());
		}
		return types;
	}
	
	public List<TypeActivite> getByEcoleAndTypeSeane(Long ecoleId, String typeSeance) {
		Ecole ecole = ecoleService.getById(ecoleId);
		List<TypeActivite> types = new ArrayList<TypeActivite>();
		try {
			types = TypeActivite.find("niveauEnseignement = ?1 and typeSeance = ?2", ecole.getNiveauEnseignement().getId(), typeSeance).list();
		}catch (RuntimeException e) {
			logger.log(Level.WARNING, "Erreur ::: {0}", e.getCause());
		}
		return types;
	}
}

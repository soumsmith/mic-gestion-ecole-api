package com.vieecoles.ressource.steph.services;

import com.vieecoles.entities.NiveauEnseignement;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class NiveauEnseignementService implements PanacheRepositoryBase<NiveauEnseignement, Long>{

	public List<NiveauEnseignement> getList(){
		return listAll();
	}
}

package com.vieecoles.steph.services;

import com.vieecoles.steph.entities.NiveauEnseignement;
import com.vieecoles.steph.projections.GenericBasicProjectionLongId;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class NiveauEnseignementService implements PanacheRepositoryBase<NiveauEnseignement, Long>{

	public List<NiveauEnseignement> getList(){
		return listAll();
	}
	
	public List<GenericBasicProjectionLongId> getListProjection(){
		return NiveauEnseignement.findAll().project(GenericBasicProjectionLongId.class).list() ;
	}
}

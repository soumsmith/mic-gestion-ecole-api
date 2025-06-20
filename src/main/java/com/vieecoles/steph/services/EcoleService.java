package com.vieecoles.steph.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import com.vieecoles.steph.entities.Ecole;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class EcoleService implements PanacheRepositoryBase<Ecole, Long>{

	public List<Ecole> getList(){
		return Ecole.listAll();
	}

	public Ecole getById(Long id) {
		return Ecole.findById(id);
	}

	public List<Ecole> getByNiveauEnseignement(Long id) {
		return Ecole.find("niveauEnseignement.id = ?1",id).list();
	}
}

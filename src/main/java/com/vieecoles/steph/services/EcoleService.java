package com.vieecoles.services;

import java.util.List;

import com.vieecoles.entities.Ecole;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

public class EcoleService implements PanacheRepositoryBase<Ecole, Long>{

	public List<Ecole> getList(){
		return Ecole.listAll();
	}
	
	public Ecole getById(Long id) {
		return Ecole.findById(id);
	}
}

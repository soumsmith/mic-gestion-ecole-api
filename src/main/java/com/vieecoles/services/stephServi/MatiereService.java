package com.vieecoles.services;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.vieecoles.entities.Matiere;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class MatiereService implements PanacheRepositoryBase<Matiere, Long>{
	
	public List<Matiere> getList() {
		try {
			return Matiere.listAll();
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<Matiere>() ;
		}
	}
}

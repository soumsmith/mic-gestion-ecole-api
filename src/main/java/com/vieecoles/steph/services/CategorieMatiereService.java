package com.vieecoles.steph.services;

import com.vieecoles.steph.entities.CategorieMatiere;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CategorieMatiereService implements PanacheRepositoryBase<CategorieMatiere, Long>{

	public List<CategorieMatiere> getList(){
		return listAll();
	}
}

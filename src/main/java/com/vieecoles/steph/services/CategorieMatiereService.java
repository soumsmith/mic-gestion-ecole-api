package com.vieecoles.ressource.steph.services;

import com.vieecoles.entities.CategorieMatiere;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CategorieMatiereService implements PanacheRepositoryBase<CategorieMatiere, Long>{

	public List<CategorieMatiere> getList(){
		return listAll();
	}
}

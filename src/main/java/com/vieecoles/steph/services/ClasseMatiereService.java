package com.vieecoles.ressource.steph.services;

import com.vieecoles.entities.Classe;
import com.vieecoles.entities.ClasseMatiere;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ClasseMatiereService implements PanacheRepositoryBase<ClasseMatiere, Long>{

	@Inject
    ClasseService classeService;

	public List<ClasseMatiere> getById(Long id){
		return ClasseMatiere.findById(id);
	}

	public List<ClasseMatiere> list(){
		return ClasseMatiere.findAll().list();
	}

	public List<ClasseMatiere> getByBranche(long brancheId, long anneeId, long ecoleId){
		return ClasseMatiere.find("branche.id = ?1", brancheId).list();
	}

	public List<ClasseMatiere> getByBrancheViaClasse(long classeId, long anneeId, long ecoleId){
		Classe classe = classeService.findById(classeId);
		return getByBranche(classe.getBranche().getId(), anneeId, ecoleId);
	}
}

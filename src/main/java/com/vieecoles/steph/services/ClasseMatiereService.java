package com.vieecoles.steph.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.vieecoles.steph.entities.Branche;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.ClasseMatiere;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

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
	
	@Transactional
	public void handleSaveOrUpdate(List<ClasseMatiere> classeMatieres){
		for(ClasseMatiere cl : classeMatieres) {
			ClasseMatiere.update("coef = ?1 where id = ?2", cl.getCoef(), cl.getId());
		}
		
	}
}

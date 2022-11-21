package com.vieecoles.services;

import java.net.URI;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.vieecoles.entities.Classe;
import com.vieecoles.entities.LangueVivante;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class ClasseService implements PanacheRepositoryBase<Classe,Integer> {

	public List<Classe> getListClasse() {
		try {
			System.out.println("........ in list <<<<>>>>>");
			return Classe.listAll();
		}catch(Exception e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Classe> getListClasseAllFields() {
		try {
			System.out.println("........ in list <<<<>>>>>");
			return populateNullFieldClasse(Classe.listAll());
		}catch(Exception e) {
			e.printStackTrace();
			return null ;
		}
	}

	public Classe findById(long id) {
		System.out.println(String.format("find by id :: %s", id));
		return Classe.findById(id);
	}
	@Transactional
	public Response save(Classe classe) {
		System.out.println("persist classe ...");
		classe.persist();
		return Response.created(URI.create("/classe/" + classe.getId())).build();
	}
	
	@Transactional
	public Classe update(Classe classe) {
		System.out.println("updating classe ...");
		Classe cl = Classe.findById(classe.getId());
		if(cl != null) {
			cl.setAnnee(classe.getAnnee());
			cl.setBranche(classe.getBranche());
			cl.setLangueVivante(classe.getLangueVivante());
			cl.setCode(classe.getCode());
			cl.setLibelle(classe.getLibelle());
		}
//		System.out.println(new Gson().toJson(cl));
		return cl;
	}
	
	
	public Classe updateAndDisplay(Classe classe) {
		Classe cl ;
		if(update(classe)!=null) {
			cl = findById(classe.getId());
//			System.out.println("classe apres le get");;
//			System.out.println(new Gson().toJson(cl));
			return cl;
		}
			
		return null;
			
		
	}
	
	@Transactional
	public void delete(String id) {
		
			System.out.println("delete classe id "+id);
			Classe classe = findById(Long.parseLong(id));
			classe.delete();
		
	}
	
	public List<Classe> populateNullFieldClasse(List<Classe> classes){
		for(Classe classe : classes) {
			if(classe.getLangueVivante()==null) {
				classe.setLangueVivante(new LangueVivante());
//				classe.getLangueVivante().setId(3);
//				classe.getLangueVivante().setCode("");
//				classe.getLangueVivante().setLibelle("");
			}
//			System.out.println(new Gson().toJson(classe));
		}
		return classes;
	}

}

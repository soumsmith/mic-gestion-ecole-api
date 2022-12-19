package com.vieecoles.ressource.steph.services;

import com.vieecoles.entities.LangueVivante;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@RequestScoped
public class LangueService implements PanacheRepositoryBase<LangueVivante, Long>{
	 public List<LangueVivante> getList(){
	       return  LangueVivante.listAll();
	   }
	   public  LangueVivante findById(Long id){
	       return LangueVivante.findById(id);
	   }

	   public Response create(LangueVivante langue) {
		   langue.persist();
	       return Response.created(URI.create("/branche/" + langue.getId())).build();
	   }

	   public  LangueVivante update(long id, LangueVivante langue){
		   LangueVivante entity = LangueVivante.findById(id);
	       if(entity == null) {
	           throw new NotFoundException();
	       }
	       entity.setId(langue.getId());
	       entity.setLibelle(langue.getLibelle());
	       return  entity;
	   }

	    public void  delete(long id){
	    	LangueVivante entity = LangueVivante.findById(id);
	        if(entity == null) {
	            throw new NotFoundException();
	        }
	        entity.delete();
	    }

	   public  List<LangueVivante> search(String libelle){
	       return  LangueVivante.find("libelle",libelle).list() ;
	   }

	    public  long count(){
	        return  LangueVivante.count();
	    }
}

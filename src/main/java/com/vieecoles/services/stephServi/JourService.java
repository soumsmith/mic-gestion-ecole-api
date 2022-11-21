package com.vieecoles.services;

import java.net.URI;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.vieecoles.entities.Jour;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class JourService implements PanacheRepositoryBase<Jour, Long> {
	public List<Jour> getList(){
	       return  Jour.listAll();
	   }
	   public  Jour findById(Long id){
	       return Jour.findById(id);
	   }

	   public Response create(Jour jour) {
	       jour.persist();
	       return Response.created(URI.create("/jour/" + jour.getId())).build();
	   }

	   public  Jour update(long id, Jour jour){
		   Jour entity = Jour.findById(id);
	       if(entity == null) {
	           throw new NotFoundException();
	       }
	       entity.setCode(jour.getCode());
	       entity.setLibelle(jour.getLibelle());
	       
	       return  entity;
	   }

	    public void  delete(long id){
	    	Jour entity = Jour.findById(id);
	        if(entity == null) {
	            throw new NotFoundException();
	        }
	        entity.delete();
	    }

	   public  List<Jour> search(String libelle){
	       return  Jour.find("libelle",libelle).list() ;
	   }

	    public  long count(){
	        return  Jour.count();
	    }
}

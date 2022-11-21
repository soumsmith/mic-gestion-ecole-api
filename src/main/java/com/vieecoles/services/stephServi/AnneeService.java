package com.vieecoles.services;

import java.net.URI;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.vieecoles.entities.AnneeScolaire;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@RequestScoped
public class AnneeService implements PanacheRepositoryBase<AnneeScolaire, Long>{
	 
	public List<AnneeScolaire> getList(){
	       return  AnneeScolaire.listAll();
	   }
	   public  AnneeScolaire findById(Long id){
	       return AnneeScolaire.findById(id);
	   }

	   public Response create(AnneeScolaire annee) {
	       annee.persist();
	       return Response.created(URI.create("/annee/" + annee.getId())).build();
	   }

	   public  AnneeScolaire update(long id, AnneeScolaire annee){
		   AnneeScolaire entity = AnneeScolaire.findById(id);
	       if(entity == null) {
	           throw new NotFoundException();
	       }
	       entity.setCode(annee.getCode());
	       entity.setLibelle(annee.getLibelle());
	       return  entity;
	   }

	    public void  delete(long id){
	    	AnneeScolaire entity = AnneeScolaire.findById(id);
	        if(entity == null) {
	            throw new NotFoundException();
	        }
	        entity.delete();
	    }

	   public  List<AnneeScolaire> search(String libelle){
	       return  AnneeScolaire.find("libelle",libelle).list() ;
	   }

	    public  long count(){
	        return  AnneeScolaire.count();
	    }
}

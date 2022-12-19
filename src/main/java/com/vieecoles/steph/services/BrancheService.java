package com.vieecoles.ressource.steph.services;

import com.vieecoles.entities.Branche;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@RequestScoped
public class BrancheService implements PanacheRepositoryBase<Branche, Long>{
	 public List<Branche> getList(){
	       return  Branche.listAll();
	   }
	   public  Branche findById(Long id){
	       return Branche.findById(id);
	   }

	   public Response create(Branche branche) {
	       branche.persist();
	       return Response.created(URI.create("/branche/" + branche.getId())).build();
	   }

	   public  Branche update(long id, Branche branche){
		   Branche entity = Branche.findById(id);
	       if(entity == null) {
	           throw new NotFoundException();
	       }
	       entity.setId(branche.getId());
	       entity.setLibelle(branche.getLibelle());
	       return  entity;
	   }

	    public void  delete(long id){
	    	Branche entity = Branche.findById(id);
	        if(entity == null) {
	            throw new NotFoundException();
	        }
	        entity.delete();
	    }

	   public  List<Branche> search(String libelle){
	       return  Branche.find("libelle",libelle).list() ;
	   }

	    public  long count(){
	        return  Branche.count();
	    }
}

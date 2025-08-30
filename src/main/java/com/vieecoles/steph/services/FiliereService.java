package com.vieecoles.steph.services;

import com.vieecoles.steph.entities.Filiere;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@RequestScoped
public class FiliereService implements PanacheRepositoryBase<Filiere, Long>{

	 public List<Filiere> getList(){
		 System.out.println("FiliereService.getList()");
	       return  Filiere.listAll();
	   }
	   public  Filiere findById(Long id){
	       return Filiere.findById(id);
	   }

	   public Response create(Filiere filiere) {
	       filiere.persist();
	       return Response.created(URI.create("/filiere/" + filiere.getId())).build();
	   }

	   public  Filiere update(long id, Filiere filiere){
		   Filiere entity = Filiere.findById(id);
	       if(entity == null) {
	           throw new NotFoundException();
	       }
	       entity.setCode(filiere.getCode());
	       entity.setLibelle(filiere.getLibelle());
	       return  entity;
	   }

	    public void  delete(long id){
	    	Filiere entity = Filiere.findById(id);
	        if(entity == null) {
	            throw new NotFoundException();
	        }
	        entity.delete();
	    }

	   public  List<Filiere> search(String libelle){
	       return  Filiere.find("libelle",libelle).list() ;
	   }

	    public  long count(){
	        return  Filiere.count();
	    }

}

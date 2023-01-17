package com.vieecoles.steph.services;

import com.vieecoles.steph.entities.Serie;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@RequestScoped
public class SerieService implements PanacheRepositoryBase<Serie, Long>{
	 public List<Serie> getList(){
		 System.out.println("SerieService.getList()");
	       return  Serie.listAll();
	   }
	   public  Serie findById(Long id){
	       return Serie.findById(id);
	   }

	   public Response create(Serie serie) {
	       serie.persist();
	       return Response.created(URI.create("/serie/" + serie.getId())).build();
	   }

	   public  Serie update(long id, Serie serie){
		   Serie entity = Serie.findById(id);
	       if(entity == null) {
	           throw new NotFoundException();
	       }
	       entity.setId(serie.getId());
	       entity.setLibelle(serie.getLibelle());
	       return  entity;
	   }

	    public void  delete(long id){
	    	Serie entity = Serie.findById(id);
	        if(entity == null) {
	            throw new NotFoundException();
	        }
	        entity.delete();
	    }

	   public  List<Serie> search(String libelle){
	       return  Serie.find("libelle",libelle).list() ;
	   }

	    public  long count(){
	        return  Serie.count();
	    }

}

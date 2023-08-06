package com.vieecoles.steph.services;

import com.vieecoles.steph.entities.Branche;
import com.vieecoles.steph.entities.Ecole;

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

	   public  List<Branche> findByNiveauEnseignementViaEcole(Long id){
		   Ecole ecole = Ecole.findById(id);
		   System.out.println(ecole);
	       return Branche.find("niveauEnseignement.id =?1 order by libelle desc", ecole.getNiveauEnseignement().getId()).list();
	   }

	public  List<Branche> findByNiveauEnseignement(Long id){

		return Branche.find("niveauEnseignement.id =?1",id).list();
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

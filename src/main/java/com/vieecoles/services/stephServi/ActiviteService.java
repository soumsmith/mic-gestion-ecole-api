package com.vieecoles.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import com.vieecoles.entities.Activite;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class ActiviteService implements PanacheRepositoryBase<Activite,Integer>{
	
	public List<Activite> getList() {
		try {
			return Activite.listAll();
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<Activite>() ;
		}
	}
	
	public Activite findById(long id) {
		System.out.println(String.format("find by id :: %s", id));
		return Activite.findById(id);
	}
	@Transactional
	public Response save(Activite activite) {
		System.out.println("persist activite ...");
		activite.persist();
		return Response.created(URI.create("/classe/" + activite.getId())).build();
	}
	
	@Transactional
	public Activite update(Activite activite) {
		System.out.println("updating classe ...");
		Activite atv = Activite.findById(activite.getId());
		if(atv != null) {
			atv.setClasse(activite.getClasse());
			atv.setHeure(activite.getHeure());
			atv.setJour(activite.getJour());
			atv.setMatiere(activite.getMatiere());
		}
//		System.out.println(new Gson().toJson(cl));
		return atv;
	}
	
	
	public Activite updateAndDisplay(Activite activite) {
		Activite atv ;
		if(update(activite)!=null) {
			atv = findById(activite.getId());
			return atv;
		}
			
		return null;
			
		
	}
	
	@Transactional
	public void delete(String id) {
		
			System.out.println("delete activite id "+id);
			Activite atv = findById(Long.parseLong(id));
			atv.delete();
		
	}
	
	
}

package com.vieecoles.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import com.vieecoles.entities.Heures;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class HeuresService implements PanacheRepositoryBase<Heures, Long>{
	
	public List<Heures> getList() {
		try {
			return Heures.listAll();
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<Heures>() ;
		}
	}
	
	public Heures findById(long id) {
		System.out.println(String.format("find by id :: %s", id));
		return Heures.findById(id);
	}
	@Transactional
	public Response save(Heures heures) {
		System.out.println("persist activite ...");
		heures.persist();
		return Response.created(URI.create("/classe/" + heures.getId())).build();
	}
	
	@Transactional
	public Heures update(Heures heures) {
		System.out.println("updating heures ...");
		Heures hr = Heures.findById(heures.getId());
		if(hr != null) {
			hr.setLibelle(heures.getLibelle());
			hr.setRang(heures.getRang());
			hr.setHeure_deb(heures.getHeure_deb());
			hr.setHeure_fin(heures.getHeure_fin());
		}
//		System.out.println(new Gson().toJson(cl));
		return hr;
	}
	
	
	public Heures updateAndDisplay(Heures heures) {
		Heures hr ;
		if(update(heures)!=null) {
			hr = findById(heures.getId());
			return hr;
		}
			
		return null;
			
		
	}
	
	@Transactional
	public void delete(String id) {
		
			System.out.println("delete heures id "+id);
			Heures hr = findById(Long.parseLong(id));
			hr.delete();
		
	}

}

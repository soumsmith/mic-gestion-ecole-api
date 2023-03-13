package com.vieecoles.steph.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import com.vieecoles.steph.entities.NotesLoader;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@RequestScoped
public class NotesLoaderService implements PanacheRepositoryBase<NotesLoader, Long> {

	Logger logger = Logger.getLogger(NotesLoaderService.class.getName());
	@Transactional
	public void create(NotesLoader notesLoader) {
		try {
			System.out.println("--> chargement de notes");
			notesLoader.persist();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erreur durant la persistance - chargement de notes");
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void manyCreate(List<NotesLoader> notesLoaders) {
			for(NotesLoader nl : notesLoaders) 
				create(nl);
	}
	
	
	public List<NotesLoader> findByEvaluation(Long evalId){
		return NotesLoader.find("evaluationLoader.id =?1", evalId).list();
	}

}

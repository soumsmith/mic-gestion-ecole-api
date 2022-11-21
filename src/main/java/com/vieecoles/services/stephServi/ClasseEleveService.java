package com.vieecoles.services;

import java.net.URI;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.vieecoles.entities.Activite;
import com.vieecoles.entities.AnneeScolaire;
import com.vieecoles.entities.ClasseEleve;
import com.vieecoles.entities.Eleve;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class ClasseEleveService implements PanacheRepositoryBase<ClasseEleve, Long>{

	 public  List<ClasseEleve> findByid(Long id){
	       return  ClasseEleve.findById(id) ;
	   }
	 
	 public List<ClasseEleve> getList() {
			return ClasseEleve.listAll();
		}
	 
	 public Response create(ClasseEleve ce) {
			ce.persist();
			return Response.created(URI.create("/ClasseEleve/" + ce.getId())).build();
		}
	 public ClasseEleve update(ClasseEleve classeEleve) {
		 ClasseEleve entity = ClasseEleve.findById(classeEleve.getId());
			if (entity == null) {
				throw new NotFoundException();
			}
			// get  classe annee by id + eleve by id pour set
//			entity.setClasseAnnee(classeEleve.getClasseAnnee());
			entity.setAnnee(classeEleve.getAnnee());
			entity.setClasse(classeEleve.getClasse());
			entity.setEleve(classeEleve.getEleve());
			
			return entity;
		}

		public void delete(long id) {
			ClasseEleve entity = ClasseEleve.findById(id);
			if (entity == null) {
				throw new NotFoundException();
			}
			entity.delete();
		}
		public ClasseEleve updateAndDisplay(ClasseEleve classeEleve) {
			ClasseEleve ce ;
			if(update(classeEleve)!=null) {
				ce = findById(classeEleve.getId());
				return ce;
			}
				
			return null;
				
			
		}
		
		public List<ClasseEleve> getByClasseAnnee(Long classeId, Long anneeId) {
			return ClasseEleve.find("classe.id = ?1 and annee.id = ?2", classeId,anneeId).list();
		}

		
		
		public long count() {
			return ClasseEleve.count();
		}
}

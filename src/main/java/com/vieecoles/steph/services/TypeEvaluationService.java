package com.vieecoles.steph.services;

import com.vieecoles.steph.entities.TypeEvaluation;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TypeEvaluationService implements PanacheRepositoryBase<TypeEvaluation, Long>{

	public List<TypeEvaluation> getList() {
		try {
			return TypeEvaluation.listAll();
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<TypeEvaluation>() ;
		}
	}

	public TypeEvaluation findById(long id) {
		System.out.println(String.format("find by id :: %s", id));
		return TypeEvaluation.findById(id);
	}
	@Transactional
	public Response save(TypeEvaluation typeEvaluation) {
		System.out.println("persist TypeEvaluation ...");
		typeEvaluation.persist();
		return Response.created(URI.create("/typeEvaluation/" + typeEvaluation.getId())).build();
	}

	@Transactional
	public TypeEvaluation update(TypeEvaluation typeEvaluation) {
		System.out.println("updating TypeEvaluation ...");
		TypeEvaluation te = TypeEvaluation.findById(typeEvaluation.getId());
		if(te != null) {
			te.setCode(typeEvaluation.getCode());
			te.setLibelle(typeEvaluation.getLibelle());
		}
		return te;
	}


	public TypeEvaluation updateAndDisplay(TypeEvaluation typeEvaluation) {
		TypeEvaluation te ;
		if(update(typeEvaluation)!=null) {
			te = findById(typeEvaluation.getId());
			return te;
		}
		return null;
	}

	@Transactional
	public void delete(String id) {
			System.out.println("delete TypeEvaluation id "+id);
			TypeEvaluation te = findById(Long.parseLong(id));
			te.delete();
	}

}

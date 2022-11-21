package com.vieecoles.services;

import java.net.URI;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import com.vieecoles.entities.ClasseAnnee;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class ClasseAnneeService implements PanacheRepositoryBase<ClasseAnnee, Long> {

	public List<ClasseAnnee> getList() {
		return ClasseAnnee.listAll();
	}

	public ClasseAnnee findById(Long id) {
		return ClasseAnnee.findById(id);
	}

	@Transactional
	public Response create(ClasseAnnee classeAnnee) {
		classeAnnee.persist();
		return Response.created(URI.create("/ClasseAnnee/" + classeAnnee.getId())).build();
	}
}

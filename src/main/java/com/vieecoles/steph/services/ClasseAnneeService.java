package com.vieecoles.ressource.steph.services;

import com.vieecoles.entities.ClasseAnnee;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

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

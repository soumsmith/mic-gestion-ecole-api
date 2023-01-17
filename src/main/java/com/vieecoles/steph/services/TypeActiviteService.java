package com.vieecoles.steph.services;

import com.vieecoles.steph.entities.TypeActivite;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class TypeActiviteService implements PanacheRepositoryBase<TypeActivite, Long>{

	public List<TypeActivite> getAll() {
		return TypeActivite.listAll();
	}
}

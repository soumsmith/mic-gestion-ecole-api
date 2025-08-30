package com.vieecoles.steph.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import com.vieecoles.steph.entities.Periodicite;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class PeriodiciteService implements PanacheRepositoryBase<Periodicite, Integer>{

	public List<Periodicite> listAll(){
		return Periodicite.listAll(Sort.by("ordre").ascending());
	}

	public Periodicite getById(int id) {
		return Periodicite.findById(id);
	}

	@Transactional
	public Periodicite create(Periodicite periodicite) {
		periodicite.persist();
		return periodicite;
	}

	public Periodicite update(Periodicite periodicite) {
		Periodicite entity = getById(periodicite.getId());
		 if(entity == null) {
	            throw new NotFoundException();
	        }
		 entity.setCode(periodicite.getCode());
		 entity.setLibelle(periodicite.getLibelle());
		 entity.setOrdre(periodicite.getOrdre());
		 entity.setIsDefault(periodicite.getIsDefault());
		 return entity;
	}

	@Transactional
	public void delete(int id) {
		try {
		Periodicite.deleteById(id);
		}catch(RuntimeException r) {
			r.printStackTrace();
		}
	}
}

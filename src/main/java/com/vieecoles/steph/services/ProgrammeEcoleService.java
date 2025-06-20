package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import com.vieecoles.steph.entities.ProgrammeEcole;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class ProgrammeEcoleService implements PanacheRepositoryBase<ProgrammeEcole, String>{

	public List<ProgrammeEcole> getList(){
		return ProgrammeEcole.listAll();
	}

	public ProgrammeEcole getById(String id) {
		return ProgrammeEcole.findById(id);
	}

	public List<ProgrammeEcole> getByEcole(Long ecoleId) {
		List<ProgrammeEcole> list;
		try {
			list = find("ecole.id = ?1 and statut= 'ACTIF'", ecoleId).list();
		}catch (RuntimeException e) {
			// TODO: handle exception
			list = new ArrayList<ProgrammeEcole>();
		}
		return list;
	}
}

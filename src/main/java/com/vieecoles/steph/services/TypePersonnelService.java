package com.vieecoles.steph.services;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;

import com.vieecoles.steph.entities.TypePersonnel;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@RequestScoped
public class TypePersonnelService implements PanacheRepositoryBase<TypePersonnelService, Long>{

		public List<TypePersonnel> getList(){
			return TypePersonnel.listAll();
		}

		// A modifier en filtrant les infos pour ne retenir que les éducateurs et les professeurs.

		public List<TypePersonnel> getEducateurAndProf(){
			return TypePersonnel.listAll();
		}

		public void create(TypePersonnel typePersonnel) {
			typePersonnel.persist();
		}
}

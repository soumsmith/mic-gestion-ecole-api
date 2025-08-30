package com.vieecoles.steph.ressources;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import com.vieecoles.steph.entities.TypePersonnel;
import com.vieecoles.steph.services.TypePersonnelService;

@Path("/typepersonnel")
public class TypePersonnelResource {

	@Inject
	TypePersonnelService typePersonnelService;

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TypePersonnel> getListAll() {
		return typePersonnelService.getList();
	}

}

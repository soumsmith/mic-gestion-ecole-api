package com.vieecoles.steph.ressources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

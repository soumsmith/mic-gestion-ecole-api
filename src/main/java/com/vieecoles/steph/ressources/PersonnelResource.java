package com.vieecoles.steph.ressources;

import javax.inject.Inject;
import javax.print.attribute.standard.MediaSize.NA;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.services.PersonnelService;

@Path("/personnels")
public class PersonnelResource {
	
	@Inject
	PersonnelService personnelService;
	
	@GET
	@Path("/get-by-id/{id}")
	@Tag(name = "Personnel")
	public Response getById(@PathParam("id") Long id) {
		System.out.println(personnelService.getById(id));
		return Response.ok().entity(personnelService.getById(id)).build();
	}

	@GET
	@Path("/list")
	@Tag(name = "Personnel")
	public Response list() {
		return Response.ok().entity(personnelService.getList()).build();
	}
	
	@GET
	@Path("/get-by-fonction")
	@Tag(name = "Personnel")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listProf(@QueryParam("fonction") int fonctionId, @QueryParam("ecole") Long ecoleId) {
		return Response.ok().entity(personnelService.getListByFonction(fonctionId, ecoleId)).build();
	}
	
	@GET
	@Path("/get-by-fonction-and-classe")
	@Tag(name = "Personnel")
	public Response listProfByClasse(@QueryParam("fonction") Long fonctionId, Long classeId) {
		return Response.ok().entity(personnelService.getListByFonctionAndClasse(fonctionId, classeId)).build();
	}
	
	@GET
	@Path("/get-by-user-id/{id}")
	@Tag(name = "Personnel")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByUserId(@PathParam("id") Long userId) {
		return Response.ok().entity(personnelService.getByUserId(userId)).build();
	}
}

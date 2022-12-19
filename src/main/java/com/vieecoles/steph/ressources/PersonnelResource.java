package com.vieecoles.ressource.steph.ressources;

import com.vieecoles.services.PersonnelService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/personnel")
public class PersonnelResource {

	@Inject
	PersonnelService personnelService;

	@GET
	@Path("/list")
	@Tag(name = "Personnel")
	public Response list() {
		return Response.ok().entity(personnelService.getList()).build();
	}

	@GET
	@Path("/get-by-fonction")
	@Tag(name = "Personnel")
	public Response listProf(@QueryParam("fonction") int fonctionId) {
		return Response.ok().entity(personnelService.getListByFonction(fonctionId)).build();
	}
}

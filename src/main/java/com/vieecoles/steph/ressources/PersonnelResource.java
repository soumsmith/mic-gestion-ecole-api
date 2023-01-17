package com.vieecoles.steph.ressources;

import javax.inject.Inject;
import javax.print.attribute.standard.MediaSize.NA;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.services.PersonnelService;

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
	
	@GET
	@Path("/get-by-fonction-and-classe")
	@Tag(name = "Personnel")
	public Response listProfByClasse(@QueryParam("fonction") int fonctionId, long classeId) {
		return Response.ok().entity(personnelService.getListByFonctionAndClasse(fonctionId, classeId)).build();
	}
}

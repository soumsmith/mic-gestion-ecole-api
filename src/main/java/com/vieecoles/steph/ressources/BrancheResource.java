package com.vieecoles.ressource.steph.ressources;

import com.vieecoles.services.BrancheService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/branche")
public class BrancheResource {

	@Inject
	BrancheService brancheService;

	@GET
	@Path("/list")
	@Operation(description = "Obtenir la liste des branches", summary = "")
	@Tag(name = "Branche")
	public Response list() {
		return Response.ok().entity(brancheService.getList()).build();
	}
}

package com.vieecoles.ressource.steph.ressources;

import com.vieecoles.services.LangueService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/langues")
public class LangueResource {

	@Inject
	LangueService langueService;

	@GET
	@Path("/list")
	@Operation(description = "Obtenir la liste des langues", summary = "")
	@Tag(name = "Langues")
	public Response list() {
		return Response.ok().entity(langueService.getList()).build();
	}
}

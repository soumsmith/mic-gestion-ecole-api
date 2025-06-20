package com.vieecoles.steph.ressources;

import com.vieecoles.steph.services.LangueService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

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

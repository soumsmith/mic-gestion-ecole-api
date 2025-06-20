package com.vieecoles.steph.ressources;

import com.vieecoles.steph.services.FiliereService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/filiere")
public class FiliereResource {
	@Inject
	FiliereService filiereService;

	@GET
	@Path("/list")
	@Operation(description = "Obtenir la liste des Filière", summary = "")
	@Tag(name = "Filiere")
	public Response list() {
		return Response.ok().entity(filiereService.getList()).build();
	}
}

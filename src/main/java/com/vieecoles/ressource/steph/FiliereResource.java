package com.vieecoles.ressource.steph;


import com.vieecoles.services.stephServi.FiliereService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

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

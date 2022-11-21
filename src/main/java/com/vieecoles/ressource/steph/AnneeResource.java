package com.vieecoles.ressource.steph;


import com.vieecoles.services.stephServi.AnneeService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
@Path("/annee")
public class AnneeResource {

	@Inject
	AnneeService anneeService;
	
	@GET
	@Path("/list")
	@Operation(description = "Obtenir la liste des années", summary = "")
	@Tag(name = "Année scolaire")
	public Response list() {
		return Response.ok().entity(anneeService.getList()).build();
	}
}

package com.vieecoles.ressource.steph;

import com.vieecoles.services.stephServi.SerieService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/serie")
public class SerieResource {
	@Inject
	SerieService serieService;
	
	@GET
	@Path("/list")
	@Operation(description = "Obtenir la liste des série", summary = "")
	@Tag(name = "Série")
	public Response list() {
		return Response.ok().entity(serieService.getList()).build();
	}
}

package com.vieecoles.steph.ressources;

import com.vieecoles.steph.services.SerieService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

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

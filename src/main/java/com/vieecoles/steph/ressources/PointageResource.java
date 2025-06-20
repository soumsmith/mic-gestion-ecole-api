package com.vieecoles.steph.ressources;

import com.vieecoles.steph.services.PointageService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/pointage")
public class PointageResource {

	@Inject
	PointageService pointageService;

	@GET
	@Path("/list")
	@Operation(description = "Obtenir la liste des niveaux", summary = "")
	@Tag(name = "Pointage")
	public Response list() {
		return Response.ok().entity(pointageService.getList()).build();
	}
}

package com.vieecoles.steph.ressources;

import com.vieecoles.steph.services.PointageService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

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

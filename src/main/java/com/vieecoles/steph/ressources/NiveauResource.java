package com.vieecoles.ressource.steph.ressources;

import com.vieecoles.services.NiveauService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/niveau")
public class NiveauResource {
	@Inject
	NiveauService niveauService;

	@GET
	@Path("/list")
	@Operation(description = "Obtenir la liste des niveaux", summary = "")
	@Tag(name = "Niveau")
	public Response list() {
		return Response.ok().entity(niveauService.getList()).build();
	}
}

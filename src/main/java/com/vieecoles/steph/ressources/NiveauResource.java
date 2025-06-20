package com.vieecoles.steph.ressources;

import com.vieecoles.steph.services.NiveauService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

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

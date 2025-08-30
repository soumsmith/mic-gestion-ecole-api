package com.vieecoles.steph.ressources;

import com.vieecoles.steph.services.CategorieMatiereService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/categorie-matiere")
public class CategorieMatiereResource {

	@Inject
	CategorieMatiereService categorieMatiereService;

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Categorie matiere")
	public Response list() {
		return Response.ok().entity(categorieMatiereService.getList()).build();
	}

}

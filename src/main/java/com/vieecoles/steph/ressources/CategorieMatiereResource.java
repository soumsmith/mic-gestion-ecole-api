package com.vieecoles.ressource.steph.ressources;

import com.vieecoles.services.CategorieMatiereService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

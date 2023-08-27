package com.vieecoles.steph.ressources;

import com.google.gson.Gson;
import com.vieecoles.steph.entities.AnneeScolaire;
import com.vieecoles.steph.entities.ClasseMatiere;
import com.vieecoles.steph.services.AnneeService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

	@POST
	@Path("/save")
	@Tag(name = "Année scolaire")
	public Response saveAndUpdateDisplay(AnneeScolaire classeMatieres) {
		Gson g = new Gson();
		try {
			System.out.println(g.toJson(classeMatieres));
			AnneeScolaire annee = anneeService.handleOpenAnnee(classeMatieres);
			return Response.ok(annee).build();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e).build();
		}
	}
}

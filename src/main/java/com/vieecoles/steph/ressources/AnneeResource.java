package com.vieecoles.steph.ressources;

import com.google.gson.Gson;
import com.vieecoles.steph.entities.AnneeScolaire;
import com.vieecoles.steph.services.AnneeService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	
	@GET
	@Path("/list-to-central")
	@Operation(description = "Obtenir la liste des années", summary = "")
	@Tag(name = "Année scolaire")
	public Response listCentral() {
		return Response.ok().entity(anneeService.getListByCentral()).build();
	}

	@GET
	@Path("/get-by-id/{id}")
	@Operation(description = "Obtenir une année scolaire via son id", summary = "")
	@Tag(name = "Année scolaire")
	public Response getById(@PathParam("id") Long id) {
		return Response.ok().entity(anneeService.getById(id)).build();
	}

	@GET
	@Path("/delete/{id}")
	@Operation(description = "Obtenir une année scolaire via son id", summary = "")
	@Tag(name = "Année scolaire")
	public Response delete(@PathParam("id") Long id) {
		try {
			anneeService.delete(id);
			return Response.ok().build();
		} catch (RuntimeException r) {
			r.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@GET
	@Path("/delete-with-periodes/{id}")
	@Operation(description = "Obtenir une année scolaire via son id", summary = "")
	@Tag(name = "Année scolaire")
	public Response handleDelete(@PathParam("id") Long id) {
		try {
			anneeService.handleDelete(id);
			return Response.ok().build();
		} catch (RuntimeException r) {
			r.printStackTrace();
			return Response.serverError().build();
		}
	}

	@POST
	@Path("/save-update")
	@Tag(name = "Année scolaire")
	public Response saveOrDisplayAndDisplay(AnneeScolaire anneeScolaire) {
		Gson g = new Gson();
		try {
			System.out.println(g.toJson(anneeScolaire));
			AnneeScolaire annee = anneeService.handleOpenAnnee(anneeScolaire);
			return Response.ok(annee).build();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e).build();
		}
	}
	
	@POST
	@Path("/sharing")
	@Tag(name = "Année scolaire")
	public Response sharingAnneeProcess(AnneeScolaire anneeScolaire) {
		try {
			AnneeScolaire annee = anneeService.sharing(anneeScolaire);
			return Response.ok(annee).build();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e).build();
		}
	}

}

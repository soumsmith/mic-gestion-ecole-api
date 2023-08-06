package com.vieecoles.steph.ressources;

import com.vieecoles.steph.services.BrancheService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/branche")
public class BrancheResource {

	@Inject
	BrancheService brancheService;

	@GET
	@Path("/list")
	@Operation(description = "Obtenir la liste des branches", summary = "")
	@Tag(name = "Branche")
	public Response list() {
		return Response.ok().entity(brancheService.getList()).build();
	}

	@GET
	@Path("/get-by-niveau-enseignement")
	@Operation(description = "Obtenir la liste des branches", summary = "")
	@Tag(name = "Branche")
	public Response getByNiveauEnseignement(@QueryParam("ecole") Long ecoleId) {
		return Response.ok().entity(brancheService.findByNiveauEnseignementViaEcole(ecoleId)).build();
	}

	@GET
	@Path("/get-by-niveau-enseignement-only")
	@Operation(description = "Obtenir la liste des branches", summary = "")
	@Tag(name = "Branche")
	public Response getByNiveauEnseignementOnly(@QueryParam("id") Long id) {
		return Response.ok().entity(brancheService.findByNiveauEnseignement(id)).build();
	}

}

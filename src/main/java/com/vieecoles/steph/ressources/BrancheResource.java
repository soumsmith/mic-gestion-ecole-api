package com.vieecoles.steph.ressources;

import com.vieecoles.steph.services.BrancheService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

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

	@GET
	@Path("/get-by-niveau-enseignement-projection")
	@Operation(description = "Obtenir la liste des branches", summary = "")
	@Tag(name = "Branche")
	public Response getByNiveauEnseignementProjection(@QueryParam("niveau") Long niveau) {
		return Response.ok().entity(brancheService.findByNiveauEnseignementProjection(niveau)).build();
	}

	@GET
	@Path("/get-by-programme-niveau")
	@Operation(description = "Obtenir la liste (dto) des branches par programme et niveau d'enseignement", summary = "")
	@Tag(name = "Branche")
	public Response getByNiveauEnseignementOnly(@QueryParam("programme") String programme,@QueryParam("niveau") String niveau) {
		return Response.ok().entity(brancheService.findByProgrammeAndNiveauEnseignement(programme, niveau)).build();
	}

}

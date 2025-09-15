package com.vieecoles.steph.ressources;

import com.vieecoles.steph.entities.Ecole;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.services.ProgrammeService;

@Path("programme-ecole")
public class ProgrammeEcoleResource {

	@Inject
	ProgrammeService programmeService;

	@GET
	@Path("list-by-ecole")
	@Operation(description = "Obtenir la liste des programmes par école", summary = "")
	@Tag(name = "Programme")
	public Response getListByEcole(@QueryParam("ecoleId") Long ecoleId) {
		return Response.ok(programmeService.listByEcole(ecoleId)).build();
	}

	@GET
	@Path("list-by-ecole-vie-ecole")
	@Operation(description = "Obtenir la liste des programmes par école", summary = "")
	@Tag(name = "Programme")
	public Response getListByEcolevieecole(@QueryParam("ecoleId") String ecoleCode) {
		Ecole ecole = Ecole.find("identifiantVieEcole =?1",ecoleCode).firstResult();
		if (ecole == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			return Response.ok(programmeService.listByEcole(ecole.getId())).build();
		}

	}

}

package com.vieecoles.steph.ressources;

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

}

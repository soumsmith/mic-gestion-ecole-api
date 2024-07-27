package com.vieecoles.steph.ressources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.services.ProgrammeService;

@Path("programme-ecole")
public class ProgrammeEcoleResource {
	
	@Inject
	ProgrammeService programmeService;
	
	@GET
	@Path("list-by-ecole")
	@Operation(description = "Obtenir la liste des branches", summary = "")
	@Tag(name = "Programme")
	public Response getListByEcole(@QueryParam("ecoleId") Long ecoleId) {		
		return Response.ok(programmeService.listByEcole(ecoleId)).build();
	}

}

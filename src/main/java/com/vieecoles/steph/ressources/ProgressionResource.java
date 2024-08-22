package com.vieecoles.steph.ressources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.dto.ProgressionDto;
import com.vieecoles.steph.services.ProgressionService;

@Path("/progression")
public class ProgressionResource {

	@Inject
	ProgressionService progressionService;

	@Path("/handle-save")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(description = "Enregistrement des progressions", summary = "")
	@Tag(name = "Progression")
	public Response handleSave(ProgressionDto dto) {
		System.out.println(dto);
		try {
			return Response.ok(progressionService.handleSave(dto)).build();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity("Erreur ::: " + e.getMessage()).build();
		}
	}
}

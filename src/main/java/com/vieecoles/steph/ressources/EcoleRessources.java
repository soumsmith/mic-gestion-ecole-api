package com.vieecoles.steph.ressources;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.services.EcoleService;

@Path("/ecoles")
@Tag(name = "Ecoles", description = "ecole erea")
public class EcoleRessources {

	@Inject
	EcoleService ecoleService;


	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		try {
			;
			return Response.ok(ecoleService.getList()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(new String("hum")).build();
		}
	}
}

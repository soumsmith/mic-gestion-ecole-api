package com.vieecoles.steph.ressources;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.google.gson.Gson;
import com.vieecoles.steph.dto.ProgressionSeanceDto;
import com.vieecoles.steph.entities.ProgressionSeance;
import com.vieecoles.steph.services.ProgressionSeanceService;

@Path("progression-seance")
@Tag(name = "Progression seance")
public class ProgressionSeanceResource {

	@Inject
	ProgressionSeanceService progressionSeanceService;

	@Path("/handle-save")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response handleSave(ProgressionSeanceDto dto) {
		try {
			Gson g = new Gson();
//			System.out.println(g.toJson(dto));
			ProgressionSeance ps = progressionSeanceService.convertToEntity(dto);
			String message = progressionSeanceService.handleSave(ps);
			return Response.ok(message).build();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e).build();
		}

	}

	@Path("/get-by-seance-and-position")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBySeanceAndPosition(@QueryParam("seance") String seance, @QueryParam("position") Integer position) {
		try {
			ProgressionSeanceDto dto = progressionSeanceService.getDtoBySeanceAndPosition(seance, position);
			return Response.ok(dto).build();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e).build();
		}

	}
}

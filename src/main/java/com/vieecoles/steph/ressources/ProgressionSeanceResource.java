package com.vieecoles.steph.ressources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
			System.out.println(g.toJson(dto));
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
	public Response handleSave(@QueryParam("seance") String seance, @QueryParam("position") Integer position) {
		try {
			ProgressionSeanceDto dto = progressionSeanceService.getDtoBySeanceAndPosition(seance, position);
			return Response.ok(dto).build();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e).build();
		}

	}
}

package com.vieecoles.steph.ressources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.google.gson.Gson;
import com.vieecoles.steph.entities.AbsenceEleve;
import com.vieecoles.steph.services.AbsenceService;

@Path("absence-eleve")
public class AbsenceResource {
	
	@Inject
	AbsenceService absenceService;

	@GET
	@Path("/get-by-id/{id}")
	@Tag(name = "Absence")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") String id) {
		return Response.ok().entity(absenceService.getById(id)).build();
	}
	
	@POST
	@Path("/save-list-process")
	@Tag(name = "Absence")
	@Produces(MediaType.TEXT_PLAIN)
	public Response saveProcess(List<AbsenceEleve> absList) {
//		Gson g = new Gson();
//		System.out.println(g.toJson(absList));
		return Response.ok().entity(absenceService.handleUpdateOrCreate(absList)).build();
	}

}

package com.vieecoles.steph.ressources;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.entities.AppelNumerique;
import com.vieecoles.steph.services.AppelNumeriqueService;

@Path("appel-numerique")
@Tag(name="Appel",description = "Appel numérique")
public class AppelNumeriqueResource {

	@Inject
	AppelNumeriqueService appelNumeriqueService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/get-all")
	public Response getAll() {
		List<AppelNumerique> list = new ArrayList<AppelNumerique>();
		try {
			list = appelNumeriqueService.getAll();
			return Response.ok(list).build();
		} catch (RuntimeException e) {
			return Response.serverError().entity(list).build();
		}
	}
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/save")
	public Response save(AppelNumerique appel) {
		try {
			UUID uuid = appelNumeriqueService.save(appel);
			return Response.ok(uuid).build();
		}catch (RuntimeException e) {
			// TODO: handle exception
			return Response.serverError().entity(e).build();
		}
	}
}

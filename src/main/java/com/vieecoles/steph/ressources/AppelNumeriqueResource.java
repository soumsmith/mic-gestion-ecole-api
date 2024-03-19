package com.vieecoles.steph.ressources;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.google.gson.Gson;
import com.vieecoles.steph.entities.AppelNumerique;
import com.vieecoles.steph.services.AppelNumeriqueDto;
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes (MediaType.TEXT_PLAIN)
	@Path("/get-list-eleve/{seanceId}/{position}")
	public Response getListEleveBySeance(@PathParam("seanceId") String seanceId, @PathParam("position") Integer position) {
		try {
			return Response.ok(appelNumeriqueService.getListeAppel(seanceId, position)).build();
		} catch (RuntimeException e) {
			return Response.serverError().entity(e).build();
		}
	}
	
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/save")
	public Response save(AppelNumerique appel) {
		try {
			String id = appelNumeriqueService.save(appel);
			return Response.ok(id).build();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e).build();
		}
	}
	
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/save-handle-dto")
	public Response save(AppelNumeriqueDto dto) {
		Gson gson = new Gson();
		System.out.println(gson.toJson(dto));
		try {
			AppelNumerique appel = appelNumeriqueService.saverFromDto(dto);
			// Access-Control-Expose-Headers nécessaire pour que les headers non default soient accessibles requete CORS
			return Response.ok("Appel sauvegardé").header("Access-Control-Expose-Headers", "Appel-Id,Date-Save").header("Appel-Id", appel.getId()).header("Date-Save", appel.getDate()).build();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e).build();
		}
	}
}

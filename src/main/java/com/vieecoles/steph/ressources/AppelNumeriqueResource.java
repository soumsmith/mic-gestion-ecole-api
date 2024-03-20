package com.vieecoles.steph.ressources;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.services.AppelNumeriqueDto;
import com.vieecoles.steph.services.AppelNumeriqueService;
import com.vieecoles.steph.util.DateUtils;

@Path("appel-numerique")
@Tag(name = "Appel", description = "Appel numérique")
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
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/get-list-eleve/{seanceId}/{position}")
	public Response getListEleveBySeance(@PathParam("seanceId") String seanceId,
			@PathParam("position") Integer position) {
		try {
			return Response.ok(appelNumeriqueService.getListeAppel(seanceId, position)).build();
		} catch (RuntimeException e) {
			return Response.serverError().entity(e).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/save")
	public Response save(AppelNumerique appel) {
		try {
			String id = appelNumeriqueService.save(appel);
			return Response.ok(id).build();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/save-handle-dto")
	public Response save(AppelNumeriqueDto dto) {
		Gson gson = new Gson();
		System.out.println(gson.toJson(dto));
		try {
			LocalTime timeDebut = LocalTime.parse(dto.getHeureDebutSeance(), DateTimeFormatter.ofPattern("HH:mm"));
			LocalTime timeDelai = timeDebut.plusMinutes(Constants.DEFAULT_DELAI_APPEL_MINUTES);
			System.out.println("tiem delai :"+timeDelai.toString());
			Boolean timeEnded = DateUtils.timeEnded(timeDelai.toString());
			if (timeEnded) {
				return Response.serverError().entity("Delai d'appel atteint").build();
			}else {
				// Access-Control-Expose-Headers nécessaire pour que les headers non default
				// soient accessibles requete CORS
				AppelNumerique appel = appelNumeriqueService.saverFromDto(dto);
				return Response.ok("Appel sauvegardé").header("Access-Control-Expose-Headers", "Appel-Id,Date-Save")
						.header("Appel-Id", appel.getId()).header("Date-Save", appel.getDate()).build();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e).build();
		}
	}
}

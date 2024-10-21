package com.vieecoles.steph.ressources;

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
			if(!progressionService.progressionValidator(dto)) {
				throw new RuntimeException("Veuillez vérifier que les données envoyées sont correctement renseignées");
			}
			if(progressionService.ifAlreadyExist(dto)) {
				throw new RuntimeException("Cette progression existe déjà");
			}
			return Response.ok(progressionService.handleSave(dto)).build();
		} catch (RuntimeException e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return Response.serverError().entity("Erreur ::: " + e.toString()).build();
		}
	}
	
	@Path("/handle-delete/{id}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	@Operation(description = "Suppression de progression", summary = "")
	@Tag(name = "Progression")
	public Response handleDelete(@PathParam("id") String id) {
		String message = "";
		try {
			message = progressionService.handleDelete(id);
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok(message).build();
	}
	
	@Path("/get-by-annee/{annee}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Operation(description = "Listes des progressions par annee", summary = "")
	@Tag(name = "Progression")
	public Response getByAnnee(@PathParam("annee") Long annee) {
		return Response.ok(progressionService.listDtoByAnnee(annee)).build();
	}
	
	@Path("/get-by-seance/{seance}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Operation(description = "Obténir la liste des progressions via une seance", summary = "")
	@Tag(name = "Progression")
	public Response getBySeance(@PathParam("seance") String seance) {
		return Response.ok(progressionService.getProgressionBySeance(seance)).build();
	}
}

package com.vieecoles.steph.ressources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.dto.ProgressionDto;
import com.vieecoles.steph.services.ProgressionService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
	@Operation(description = "Listes des progressions par annee", summary = "")
	@Tag(name = "Progression")
	public Response getByAnnee(@PathParam("annee") Long annee) {
		return Response.ok(progressionService.listDtoByAnnee(annee)).build();
	}

	@Path("/get-by-seance/{seance}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(description = "Obténir la liste des progressions via une seance", summary = "")
	@Tag(name = "Progression")
	public Response getBySeance(@PathParam("seance") String seance) {
		return Response.ok(progressionService.getProgressionBySeance(seance)).build();
	}

	@Path("/get-at-now/{annee}/{niveau}/{branche}/{matiere}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(description = "Obténir la liste des progressions à effectuer à la date du jour", summary = "")
	@Tag(name = "Progression")
	public Response getAtNow(@PathParam("annee") Long annee, @PathParam("niveau") Long niveau, @PathParam("branche") Long branche, @PathParam("matiere") Long matiere) {
		return Response.ok(progressionService.getProgressionAtNow(annee, niveau, branche, matiere)).build();
	}
}

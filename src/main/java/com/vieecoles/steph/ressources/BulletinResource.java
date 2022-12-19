package com.vieecoles.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.Query;

import com.vieecoles.dto.MoyenneEleveDto;
import com.vieecoles.services.BulletinService;

@Path("/bulletin")
public class BulletinResource {

	@Inject
	BulletinService bulletinService;

	@GET
	@Path("/handle-save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Operation(description = "Obtenir la liste des années", summary = "")
	@Tag(name = "Année scolaire")
	public Response handleSave(@QueryParam("classe") String classe ,@QueryParam("annee") String  annee, @QueryParam("periode") String periode) {
		int nbreEleve = 0;
		try {
			nbreEleve = bulletinService.handleSave(classe, annee, periode);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		return Response.ok(nbreEleve == 0 ? "Aucun bulletin sauvegardé" : String.format("%s Bulletin(s) sauvegardé(s)",nbreEleve)).build();
	}
}

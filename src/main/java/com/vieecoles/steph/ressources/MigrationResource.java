package com.vieecoles.steph.ressources;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.services.MigrationService;

@Path("/migration")
public class MigrationResource {

	@Inject
	MigrationService migrationService;


	@GET
	@Tag(name = "Migration", description = "Processus de migration des matieres vers les matieres écoles")
	@Path("/new-matiere-release")
	@Produces(MediaType.TEXT_PLAIN)
	public Response migrationToMatiereEcoleDatas() {
		try {
		 migrationService.doHandleMigrationMatereEcole();
		 return Response.ok("Migration bien effectuée").build();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity("Une erreur s'est produite voir le log").build();
		}

	}

}

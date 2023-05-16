package com.vieecoles.steph.ressources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

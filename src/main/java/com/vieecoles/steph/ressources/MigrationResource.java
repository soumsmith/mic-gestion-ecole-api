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
	@Produces(MediaType.TEXT_PLAIN)
	@GET
	@Tag(name = "Migration", description = "Processus de migration des matieres vers les matieres écoles")
	public Response migrationToMatiereEcoleDatas() {
		try {
		 migrationService.doHandleMigrationMatereEcole();
		 return Response.ok().build();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
	}

}

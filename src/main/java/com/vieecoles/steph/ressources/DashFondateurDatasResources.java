package com.vieecoles.steph.ressources;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import com.vieecoles.steph.services.DashBoardFondateurDatasService;

@Path("/dashboard-fondateur")
public class DashFondateurDatasResources {

	@Inject
	DashBoardFondateurDatasService dashBoardFondateurDatasService;

	@Path("/eleve-block/{ecole}/{annee}")
	@GET
    @Tag(name = "DashFondateur")
    public Response eleveDatas(@PathParam("ecole") Long ecole, @PathParam("annee") Long annee) {
        return Response.ok().entity(dashBoardFondateurDatasService.buildDashObject(ecole, annee)).build();
    }
}

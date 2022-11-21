package com.vieecoles.ressource.steph;

import com.vieecoles.services.stephServi.MatiereService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/matiere")
public class MatiereResource {

	@Inject
    MatiereService matiereService;
	
	@GET
    @Path("/list")
    @Tag(name = "Matiere")
	@Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.ok().entity(matiereService.getList()).build();
    }
}

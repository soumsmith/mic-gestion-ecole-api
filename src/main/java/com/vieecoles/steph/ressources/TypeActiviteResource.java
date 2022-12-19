package com.vieecoles.ressource.steph.ressources;

import com.vieecoles.services.TypeActiviteService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/type-activite")
public class TypeActiviteResource {

	@Inject
	TypeActiviteService typeActiviteService;

	@GET
    @Path("/list")
    @Tag(name = "TypeActivite")
    public Response list() {
        return Response.ok().entity(typeActiviteService.getAll()).build();
    }
}

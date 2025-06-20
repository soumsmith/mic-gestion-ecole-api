package com.vieecoles.steph.ressources;

import com.vieecoles.steph.services.TypeActiviteService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

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
	
	@GET
    @Path("/get-by-ecole/{ecoleId}")
    @Tag(name = "TypeActivite")
    public Response getByEcole(@PathParam("ecoleId") Long ecoleId) {
        return Response.ok().entity(typeActiviteService.getByEcole(ecoleId)).build();
    }
	
	@GET
    @Path("/get-by-ecole-and-type/{ecoleId}/")
    @Tag(name = "TypeActivite")
    public Response getByEcole(@PathParam("ecoleId") Long ecoleId, @QueryParam("type") String type) {
        return Response.ok().entity(typeActiviteService.getByEcoleAndTypeSeane(ecoleId, type)).build();
    }
}

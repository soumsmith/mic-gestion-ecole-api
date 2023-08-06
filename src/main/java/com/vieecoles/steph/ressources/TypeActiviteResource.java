package com.vieecoles.steph.ressources;

import com.vieecoles.steph.services.TypeActiviteService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
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

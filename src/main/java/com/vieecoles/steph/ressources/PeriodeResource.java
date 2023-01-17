package com.vieecoles.steph.ressources;

import com.vieecoles.steph.services.PeriodeService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/periodes")
public class PeriodeResource {
	@Inject
	PeriodeService periodeService;

	@GET
    @Path("/list")
    @Tag(name = "Periode")
    public Response list() {
        return Response.ok().entity(periodeService.getList()).build();
    }
}

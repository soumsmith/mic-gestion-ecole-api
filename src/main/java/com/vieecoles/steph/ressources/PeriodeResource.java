package com.vieecoles.steph.ressources;

import com.vieecoles.steph.services.PeriodeService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

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

	@GET
    @Path("/list-by-periodicite")
    @Tag(name = "Periode")
    public Response getByperiodicite(@QueryParam("id") Integer periodiciteId) {
        return Response.ok().entity(periodeService.getListByPeriodicite(periodiciteId)).build();
    }

	@GET
    @Path("/list-periodicite-past-and-current")
    @Tag(name = "Periode")
    public Response getListFilterByDateByPeriodicite(@QueryParam("id") Integer periodiciteId, @QueryParam("ecoleId") Long ecoleId) {
        return Response.ok().entity(periodeService.getListFilterByDateByPeriodicite(periodiciteId, ecoleId)).build();
    }

}

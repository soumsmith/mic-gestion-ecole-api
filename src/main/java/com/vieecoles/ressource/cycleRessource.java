package com.vieecoles.ressource;

import com.vieecoles.entities.cycle;
import com.vieecoles.services.cycleService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/cycle")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class cycleRessource {
    @Inject
    cycleService matService ;
    @GET
    public List<cycle> list() {
        return matService.getListcycle();
    }

    @GET
    @Path("/{id}")
    public cycle get(@PathParam("id") Long id) {
        return matService.findById(id);
    }
    @POST
    @Transactional
    public Response create(cycle mat) {
        return matService.createcycle(mat);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public cycle update(@PathParam("id") Long id, cycle mat) {
    return matService.updateCycle(id,mat);

    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        matService.deletecycle(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<cycle> search(@PathParam("libelle") String libelle) {
        return matService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return matService.count();
    }


}

package com.vieecoles.ressource;

import com.vieecoles.entities.fonction;
import com.vieecoles.services.fonctionService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/fonction")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class fonctionRessource {
    @Inject
    fonctionService matService ;
    @GET
    public List<fonction> list() {
        return matService.getListfonction();
    }

    @GET
    @Path("/{id}")
    public fonction get(@PathParam("id") Long id) {
        return matService.findById(id);
    }
    @POST
    @Transactional
    public Response create(fonction mat) {
        return matService.createfonction(mat);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public fonction update(@PathParam("id") Long id, fonction mat) {
    return matService.updatefonction(id,mat);

    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        matService.deletefonction(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<fonction> search(@PathParam("libelle") String libelle) {
        return matService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return matService.count();
    }


}

package com.vieecoles.ressource;

import com.vieecoles.entities.pays;
import com.vieecoles.services.paysService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/pays")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class paysRessource {
    @Inject
    paysService matService ;
    @GET
    public List<pays> list() {
        return matService.getListpays();
    }

    @GET
    @Path("/{id}")
    public pays get(@PathParam("id") Long id) {
        return matService.findById(id);
    }
    @POST
    @Transactional
    public Response create(pays mat) {
        return matService.createpays(mat);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public pays update(@PathParam("id") Long id, pays mat) {
    return matService.updatepays(id,mat);

    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        matService.deletepays(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<pays> search(@PathParam("libelle") String libelle) {
        return matService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return matService.count();
    }


}

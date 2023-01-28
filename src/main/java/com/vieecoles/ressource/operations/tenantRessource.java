package com.vieecoles.ressource.operations;

import com.vieecoles.entities.tenant;
import com.vieecoles.services.tenantService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/tenant")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class tenantRessource {
    @Inject
    tenantService matService ;
    @GET
    public List<tenant> list() {
        return matService.getListTenat() ;
    }

    @GET
    @Path("/{id}")
    public tenant get(@PathParam("id") Long id) {
        return matService.findById(id);
    }
    @POST
    @Transactional
    public tenant create(tenant mat) {
        return matService.createtenant(mat);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public tenant update(@PathParam("id") String id, tenant mat) {
    return matService.updatetenant(id,mat);

    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") String id) {
        matService.delete(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<tenant> search(@PathParam("libelle") String libelle) {
        return matService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return matService.count();
    }


}

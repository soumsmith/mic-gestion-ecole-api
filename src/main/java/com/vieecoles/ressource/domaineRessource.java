package com.vieecoles.ressource;

import com.vieecoles.entities.domaine;
import com.vieecoles.services.domaineService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/domaine")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class domaineRessource {
    @Inject
    domaineService domService ;
    @GET
    public List<domaine> list() {
        return domService.getListdomaine();
    }

    @GET
    @Path("/{id}")
    public domaine get(@PathParam("id") Long id) {
        return domService.findById(id);
    }
    @POST
    @Transactional
    public Response create(domaine dom) {
        return domService.createdomaine(dom);
    }


    @PUT
    @Path("/{id}")
    @Transactional
    public domaine update(@PathParam("id") Long id, domaine dom) {
    return  domService.updatedomaine(id,dom);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
       domService.deletedomaine(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<domaine> search(@PathParam("libelle") String libelle) {
        return domService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return domService.count();
    }


}

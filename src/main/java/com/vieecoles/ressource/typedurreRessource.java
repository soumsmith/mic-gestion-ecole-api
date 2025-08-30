package com.vieecoles.ressource;


import com.vieecoles.entities.typedurre;
import com.vieecoles.services.typedurreService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/type-durre")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class typedurreRessource {
    @Inject
    typedurreService typService ;
    @GET
    public List<typedurre> list() {
        return typService.getListtypedurre();
    }

    @GET
    @Path("/{id}")
    public typedurre get(@PathParam("id") Long id) {
        return typService.findById(id);
    }
    @POST
    @Transactional
    public Response create(typedurre zon) {
        return typService.createtypedurre(zon);
    }


    @PUT
    @Path("/{id}")
    @Transactional
    public typedurre update(@PathParam("id") Long id, typedurre zon) {
    return  typService.updatetypedurre(id,zon);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        typService.deletetypedurre(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<typedurre> search(@PathParam("libelle") String libelle) {
        return typService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return typService.count();
    }


}

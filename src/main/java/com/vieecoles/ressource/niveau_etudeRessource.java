package com.vieecoles.ressource;

import com.vieecoles.entities.niveau_etude;
import com.vieecoles.services.niveau_etudeService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/niveau_etude")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class niveau_etudeRessource {
    @Inject
    niveau_etudeService domService ;
    @GET
    public List<niveau_etude> list() {
        return domService.getListdomaine();
    }

    @GET
    @Path("/{id}")
    public niveau_etude get(@PathParam("id") Long id) {
        return domService.findById(id);
    }
    @POST
    @Transactional
    public Response create(niveau_etude dom) {
        return domService.createdomaine(dom);
    }


    @PUT
    @Path("/{id}")
    @Transactional
    public niveau_etude update(@PathParam("id") Long id, niveau_etude dom) {
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
    public List<niveau_etude> search(@PathParam("libelle") String libelle) {
        return domService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return domService.count();
    }


}

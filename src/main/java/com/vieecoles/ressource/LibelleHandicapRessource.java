package com.vieecoles.ressource;

import com.vieecoles.dao.entities.Libellehandicap;
import com.vieecoles.services.LibelleHandicapService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/LibelleHandicap")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LibelleHandicapRessource {
    @Inject
    LibelleHandicapService matService ;
    @Inject
    EntityManager em;


    @GET

    public List<Libellehandicap> list() {
        return matService.findHandicap();
    }


    @GET
    @Path("/{id}")
    public Libellehandicap get(@PathParam("id") Long id) {
        return matService.findByIdLibelle(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Libellehandicap create(  Libellehandicap libellehandicap){

        return matService.create(libellehandicap);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public int update(Libellehandicap libellehandicap) {

    return matService.updateObjet(libellehandicap);

    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        matService.deletobjet(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<Libellehandicap> search(@PathParam("libelle") String libelle) {
        return matService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return matService.count();
    }


}

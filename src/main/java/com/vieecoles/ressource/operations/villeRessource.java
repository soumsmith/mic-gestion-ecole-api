package com.vieecoles.ressource.operations;

import com.vieecoles.entities.pays;
import com.vieecoles.entities.operations.ville;
import com.vieecoles.services.operations.villeService;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/ville")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class villeRessource {
    @Inject
    villeService matService ;
    @Inject
    EntityManager em;
    @GET
    @Path("direction-regionale/{id}")
    public List<ville> list(@PathParam("id") Long id) {
        return matService.findVilleByDirect(id) ;
    }

    @GET

    public List<ville> list() {
        return matService.findAllVilles() ;
    }





}

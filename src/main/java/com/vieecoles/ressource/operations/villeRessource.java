package com.vieecoles.ressource.operations;

import com.vieecoles.entities.pays;
import com.vieecoles.entities.operations.ville;
import com.vieecoles.services.operations.villeService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

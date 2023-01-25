package com.vieecoles.ressource;

import com.vieecoles.entities.Direction_regionale;
import com.vieecoles.entities.pays;
import com.vieecoles.entities.operations.ville;
import com.vieecoles.services.DirectionRegionalService;
import com.vieecoles.services.operations.villeService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/DirectionGenerale")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DirectionGeneraleRessource {
    @Inject
    DirectionRegionalService matService ;
    @Inject
    EntityManager em;
    @GET
    @Path("pays/{pays}")
    public List<Direction_regionale> list(@PathParam("pays") Long pays) {
        return matService.findDirectByIdPays(pays) ; }

    @GET

    public List<Direction_regionale> list() {
        return matService.findAllDirection_regionales();
    }

}

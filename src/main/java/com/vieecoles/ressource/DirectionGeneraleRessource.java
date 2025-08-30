package com.vieecoles.ressource;

import com.vieecoles.entities.Direction_regionale;
import com.vieecoles.entities.pays;
import com.vieecoles.entities.operations.ville;
import com.vieecoles.services.DirectionRegionalService;
import com.vieecoles.services.operations.villeService;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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

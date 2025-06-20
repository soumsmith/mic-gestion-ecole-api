package com.vieecoles.ressource.operations.etats;


import com.vieecoles.entities.Rapports;
import com.vieecoles.entities.Zone;
import com.vieecoles.services.zoneService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/rapports")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RapportsRessource {


    @GET
    @Path("/{idEnseignement}")
    @Transactional
    public List<Rapports> get(@PathParam("idEnseignement") Long idEnseignement) {
        return Rapports.find ("id_Niveau_Enseignement = ?1", idEnseignement).list () ;
    }


}

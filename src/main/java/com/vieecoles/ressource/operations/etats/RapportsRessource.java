package com.vieecoles.ressource.operations.etats;


import com.vieecoles.entities.Rapports;
import com.vieecoles.entities.Zone;
import com.vieecoles.services.zoneService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

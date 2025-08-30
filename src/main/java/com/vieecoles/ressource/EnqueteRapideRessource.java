package com.vieecoles.ressource;

import com.vieecoles.dto.Rapide_rapideDto;
import com.vieecoles.entities.domaine;
import com.vieecoles.entities.operations.Rapide_rapide;
import com.vieecoles.entities.operations.Rapide_rapide2;
import com.vieecoles.projection.EnqueteSelectDto;
import com.vieecoles.services.EnqueteRapidService;
import com.vieecoles.services.domaineService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/enquete-rapide")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnqueteRapideRessource {
    @Inject
    EnqueteRapidService enqueteRapidService ;

    @POST
    @Transactional
    public String  create(Rapide_rapideDto dom) {
        return enqueteRapidService.insertorModifEnquete(dom) ;
    }



    @GET
    @Path("/liste-par-ecole-aff-naff/{idEcole}/{idAnnee}")
    public List<EnqueteSelectDto>  search(@PathParam("idEcole") Long  idEcole ,@PathParam("idAnnee") Long  idAnnee) {
        return enqueteRapidService.getList(idEcole,idAnnee);
    }



}

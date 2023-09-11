package com.vieecoles.ressource;

import com.vieecoles.dto.Rapide_rapide2Dto;
import com.vieecoles.dto.Rapide_rapideDto;
import com.vieecoles.entities.operations.Rapide_rapide2;
import com.vieecoles.projection.EnqueteSelectDto;
import com.vieecoles.services.EnqueteRapidService;
import com.vieecoles.services.EnqueteRapidService2;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/enquete-rapide")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnqueteRapide2Ressource {
    @Inject
    EnqueteRapidService2 enqueteRapidService2 ;

    @POST
    @Transactional
    @Path("/reunion")
    public String  create(Rapide_rapide2Dto dom) {
        return enqueteRapidService2.insertorModifEnquete(dom) ;
    }



    @GET
    @Path("/liste-par-ecole/{idEcole}/{idAnnee}")
    public Rapide_rapide2  search(@PathParam("idEcole") Long  idEcole , @PathParam("idAnnee") Long  idAnnee) {
        return enqueteRapidService2.checkExistEnquete(idEcole,idAnnee);
    }



}

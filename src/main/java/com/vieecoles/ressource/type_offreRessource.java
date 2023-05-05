package com.vieecoles.ressource;

import com.vieecoles.entities.niveau_etude;
import com.vieecoles.entities.type_offre;
import com.vieecoles.services.niveau_etudeService;
import com.vieecoles.services.type_offreService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/type_offre")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class type_offreRessource {
    @Inject
    type_offreService domService ;
    @GET
    public List<type_offre> list() {
        return domService.getListtype_offre() ;
    }




}

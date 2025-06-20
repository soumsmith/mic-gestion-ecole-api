package com.vieecoles.ressource;

import com.vieecoles.entities.type_evaluation;
import com.vieecoles.services.type_evaluationService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/type-evaluation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class type_evaluationRessource {
    @Inject
    type_evaluationService typ_evalService ;
    @GET
    public List<type_evaluation> list() {
        return typ_evalService.getListevaluation();
    }

    @GET
    @Path("/{id}")
    public type_evaluation get(@PathParam("id") Long id) {
        return typ_evalService.findById(id);
    }
    @POST
    @Transactional
    public Response create(type_evaluation typeval) {
        return typ_evalService.createtype_eval(typeval);
    }


    @PUT
    @Path("/{id}")
    @Transactional
    public type_evaluation update(@PathParam("id") Long id, type_evaluation type_eval) {
    return  typ_evalService.updatetype_eval(id,type_eval);
    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        typ_evalService.deletetype_eval(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<type_evaluation> search(@PathParam("libelle") String libelle) {
        return typ_evalService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return typ_evalService.count();
    }


}

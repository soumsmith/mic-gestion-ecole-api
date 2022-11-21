package com.vieecoles.ressource;

import com.vieecoles.dao.entities.type_objet;
import com.vieecoles.services.type_objetService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/type-objet")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class type_objetRessource {
    @Inject
    type_objetService typ_objService ;
    @GET
    public List<type_objet> list() {
        return typ_objService.getListtype_objet();
    }

    @GET
    @Path("/{id}")
    public type_objet get(@PathParam("id") Long id) {
        return typ_objService.findById(id);
    }
    @POST
    @Transactional
    public Response create(type_objet typr_obj) {
        return typ_objService.createtype_objet(typr_obj);
    }


    @PUT
    @Path("/{id}")
    @Transactional
    public type_objet update(@PathParam("id") Long id, type_objet type_obj) {
    return  typ_objService.updatetype_obj(id,type_obj);
    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        typ_objService.deletetype_objet(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<type_objet> search(@PathParam("libelle") String type_objet_libelle) {
        return typ_objService.search(type_objet_libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return typ_objService.count();
    }


}

package com.vieecoles.ressource;

import com.vieecoles.entities.type_personnel;
import com.vieecoles.services.type_personnelService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/type-personnel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class type_personnelRessource {
    @Inject
    type_personnelService typ_personService ;
    @GET
    public List<type_personnel> list() {
        return typ_personService.getListTypePersonnel();
    }

    @GET
    @Path("/{id}")
    public type_personnel get(@PathParam("id") Long id) {
        return typ_personService.findById(id);
    }
    @POST
    @Transactional
    public Response create(type_personnel typr_perso) {
        return typ_personService.createtype_personnel(typr_perso);
    }


    @PUT
    @Path("/{id}")
    @Transactional
    public type_personnel update(@PathParam("id") Long id, type_personnel type_person) {
    return  typ_personService.updatetype_personnel(id,type_person);
    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        typ_personService.deletetype_personnel(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<type_personnel> search(@PathParam("libelle") String type_perso_libelle) {
        return typ_personService.search(type_perso_libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return typ_personService.count();
    }


}

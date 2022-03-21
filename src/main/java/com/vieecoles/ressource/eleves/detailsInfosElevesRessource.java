package com.vieecoles.ressource.eleves;

import com.vieecoles.entities.operations.details_infos_eleves;
import com.vieecoles.services.eleves.details_info_elevesService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/objet")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class detailsInfosElevesRessource {
    @Inject
    details_info_elevesService matService ;
    @Inject
    EntityManager em;
    @GET
    @Path("categorie_info/{categ}")
    public List<details_infos_eleves> list(@PathParam("categ") Long categ) {
        return matService.findByIdCategorie(categ);
    }

    @GET

    public List<details_infos_eleves> list() {
        return matService.findAlldetailsInfoEleves();
    }

    @GET
    @Path("/{id}")
    public details_infos_eleves get(@PathParam("id") Long id) {
        return matService.findByIDdetailsInfosEleves(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create( details_infos_eleves details_infos_eleves ) {

        return matService.create(details_infos_eleves);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public int update( details_infos_eleves details_infos_eleves ) {
            return matService.updatdetailsIbfosEleves(details_infos_eleves);
    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        matService.deletInfoElev(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<details_infos_eleves> search(@PathParam("libelle") String libelle) {
        return matService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return matService.count();
    }


}

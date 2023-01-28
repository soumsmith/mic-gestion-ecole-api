package com.vieecoles.ressource;

import com.vieecoles.entities.domaine_formation;
import com.vieecoles.services.domaineEtudeService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/Domaine_formation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class domaineFormationRessource {
    @Inject
    domaineEtudeService domService ;
    @GET
    public List<domaine_formation> list() {
        return domService.getListdomaine();
    }

    @GET
    @Path("/{id}")
    public domaine_formation get(@PathParam("id") Long id) {
        return domService.findById(id);
    }
    @POST
    @Transactional
    public Response create(domaine_formation dom) {
        return domService.createdomaine(dom);
    }


    @PUT
    @Path("/{id}")
    @Transactional
    public domaine_formation update(@PathParam("id") Long id, domaine_formation dom) {
    return  domService.updatedomaine(id,dom);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
       domService.deletedomaine(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<domaine_formation> search(@PathParam("libelle") String libelle) {
        return domService.search(libelle) ;
    }

   /*  @GET
    @Path("/searchFond/{libelle}")
    public domaine_formation searchfon(@PathParam("libelle") String libelle) {
        return domService.searchDomFon(libelle) ;
    } */

    @GET
    @Path("/count")
    public Long count() {
        return domService.count();
    }


}

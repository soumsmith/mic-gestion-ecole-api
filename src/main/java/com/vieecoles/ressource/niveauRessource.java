package com.vieecoles.ressource;

import com.vieecoles.dao.entities.Niveau;
import com.vieecoles.services.niveauService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/niveau")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class niveauRessource {
    @Inject
   niveauService nivService ;
    @GET
    public List<Niveau> list() {
        return nivService.getListNIveau();
    }

    @GET
    @Path("/{id}")
    public Niveau get(@PathParam("id") Long id) {
        return nivService.findById(id);
    }

    @GET
    @Path("ecole/{code}")
    public List<Niveau> getNiveauByEcole(@PathParam("code") String code) {
        return nivService.findNiveauByEcole(code) ;
    }



    @POST
    @Transactional
    public Response create(Niveau niv) {
        return nivService.createniveau(niv);
    }


    @PUT
    @Path("/{id}")
    @Transactional
    public Niveau update(@PathParam("id") Long id, Niveau niv) {
    return  nivService.updateNiveau(id,niv);
    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
       nivService.deleteNiveau(id);
    }

    @GET
    @Path("/search/{niveaulibelle}")
    public List<Niveau> search(@PathParam("niveaulibelle") String niveaulibelle) {
        return nivService.search(niveaulibelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return nivService.count();
    }


}

package com.vieecoles.ressource;

import com.vieecoles.dao.entities.utilisateur;
import com.vieecoles.services.utilisateurService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/utilisateur")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class utilisateurRessource {
    @Inject
    utilisateurService matService ;
    @GET
    public List<utilisateur> list() {
        return matService.getListutlisateur();
    }

    @GET
    @Path("/{id}")
    public utilisateur get(@PathParam("id") Long id) {
        return matService.findById(id);
    }
    @POST
    @Transactional
    public Response create(utilisateur mat) {
        return matService.createutilisateur(mat);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public utilisateur update(@PathParam("id") Long id, utilisateur mat) {
    return matService.updateutilisateur(id,mat);

    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        matService.deleteutilisateur(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<utilisateur> search(@PathParam("libelle") String libelle) {
        return matService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return matService.count();
    }


}

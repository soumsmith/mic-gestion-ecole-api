package com.vieecoles.ressource;


import com.vieecoles.entities.profil;

import com.vieecoles.services.profilService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/profil")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class profilRessource {
    @Inject
    profilService proService ;
    @GET
    public List<profil> list() {
        return proService.getListprofil();
    }

    @GET
    @Path("/{id}")
    public profil get(@PathParam("id") Long id) {
        return proService.findById(id);
    }
    @POST
    @Transactional
    public Response create(profil prof) {
        return proService.createprofil(prof);
    }


    @PUT
    @Path("/{id}")
    @Transactional
    public profil update(@PathParam("id") Long id, profil prof) {
    return  proService.updateprofil(id,prof);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        proService.deleteprofil(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<profil> search(@PathParam("libelle") String libelle) {
        return proService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return proService.count();
    }


}

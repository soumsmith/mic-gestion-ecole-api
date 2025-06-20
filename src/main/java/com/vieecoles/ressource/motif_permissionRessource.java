package com.vieecoles.ressource;

import com.vieecoles.entities.motif_permission;
import com.vieecoles.services.motif_persmissionService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/motif-permission")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class motif_permissionRessource {
    @Inject
    motif_persmissionService motService ;
    @GET
    public List<motif_permission> list() {
        return motService.getListmotifPermission();
    }

    @GET
    @Path("/{id}")
    public motif_permission get(@PathParam("id") Long id) {
        return motService.findById(id);
    }
    @POST
    @Transactional
    public Response create(motif_permission mot) {
        return motService.createmotifPermission(mot);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public motif_permission update(@PathParam("id") Long id, motif_permission mat) {
    return motService.updatemotif_permission(id,mat);

    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        motService.deleteMotifPermiss(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<motif_permission> search(@PathParam("libelle") String libelle) {
        return motService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return motService.count();
    }


}

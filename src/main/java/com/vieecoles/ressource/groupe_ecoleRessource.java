package com.vieecoles.ressource;


import com.vieecoles.entities.groupe_ecole;
import com.vieecoles.services.groupe_ecoleService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/groupe-ecole")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class groupe_ecoleRessource {
    @Inject
    groupe_ecoleService matService ;
    @GET
    public List<groupe_ecole> list() {
        return matService.getListgroupeEcole();
    }

    @GET
    @Path("/{id}")
    public groupe_ecole get(@PathParam("id") Long id) {
        return matService.findById(id);
    }
    @POST
    @Transactional
    public Response create(groupe_ecole mat) {
        return matService.creategroupeEcole(mat);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public groupe_ecole update(@PathParam("id") Long id, groupe_ecole mat) {
    return matService.updategroupeEcole(id,mat);

    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        matService.deletegroupeEcole(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<groupe_ecole> search(@PathParam("libelle") String libelle) {
        return matService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return matService.count();
    }


}

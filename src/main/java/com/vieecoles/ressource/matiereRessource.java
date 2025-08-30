package com.vieecoles.ressource;

import com.vieecoles.entities.matiere;
import com.vieecoles.services.matiereService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
@Tag(name = "MatiereSoum", description = "Matiere ressources")
@Path("/matieresoum")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class matiereRessource {
    @Inject
    matiereService matService ;
    @GET
    public List<matiere> list() {
        return matService.getListMatiere();
    }

    @GET
    @Path("/{id}")
    public matiere get(@PathParam("id") Long id) {
        return matService.findById(id);
    }

    @GET
    @Path("ecole/{tenant}")
    public List<matiere> get(@PathParam("tenant") String  tenant) {
        return matService.ListeMatiereParEcole(tenant) ;
    }

    @POST
    @Transactional
    public Response create(matiere mat) {
        System.out.println("mon Test"+mat.toString());
        return matService.creatematiere(mat);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public matiere update(@PathParam("id") Long id, matiere mat) {
    return matService.updatematiere(id,mat);

    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        matService.deleteById(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<matiere> search(@PathParam("libelle") String libelle) {
        return matService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return matService.count();
    }


}

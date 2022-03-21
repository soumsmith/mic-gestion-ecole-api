package com.vieecoles.ressource.eleves;

import com.vieecoles.entities.operations.categorie_information;
import com.vieecoles.services.eleves.categorie_informationService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/pays")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class categorieInformationRessource {
    @Inject
    categorie_informationService matService ;
    @GET
    public List<categorie_information> list() {
        return matService.getListCategorieInformation();
    }

    @GET
    @Path("/{id}")
    public categorie_information get(@PathParam("id") Long id) {
        return matService.getCategorieInById(id);
    }
    @POST
    @Transactional
    public Response create(categorie_information mat) {
        return matService.createCategorieInformation(mat);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public categorie_information update(categorie_information mat) {
    return matService.updatecategorieInformation(mat);

    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        matService.deletecategorie_information(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<categorie_information> search(@PathParam("libelle") String libelle) {
        return matService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return matService.count();
    }


}

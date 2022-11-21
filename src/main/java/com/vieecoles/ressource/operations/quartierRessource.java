package com.vieecoles.ressource.operations;

import com.vieecoles.dao.entities.operations.commune;
import com.vieecoles.dao.entities.operations.quartier;
import com.vieecoles.services.operations.quartierService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/quartier")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class quartierRessource {
    @Inject
    quartierService matService ;
    @Inject
    EntityManager em;
    @GET
    @Path("commune/{commune}")
    public List<quartier> list(@PathParam("commune") Long commune) {
        return matService.findByIdcommune(commune);
    }

    @GET

    public List<quartier> list() {
        return matService.findAllquartier();
    }


    @GET
    @Path("/{id}")
    public quartier get(@PathParam("id") Long id) {
        return matService.findByIDquartier(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response create(  @FormParam("quartier_code") String quartier_code, @FormParam("quartier_libelle") String quartier_libelle ,
                             @FormParam("commune_id") Long commune_id) {
        commune typObj= em.getReference(commune.class,commune_id);

        quartier obj = new quartier();

        obj.setCommune(typObj);
        obj.setQuartiercode(quartier_code);
        obj.setQuartierlibelle(quartier_libelle);

        return matService.create(obj);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public int update(@FormParam("quartier_id") Long quartier_id,@FormParam("quartier_code") String quartier_code, @FormParam("quartier_libelle") String quartier_libelle ,
                      @FormParam("commune_id") Long commune_id) {
        commune typObj= em.getReference(commune.class,commune_id);

        quartier obj = new quartier();
        obj.setQuartierid(quartier_id);
        obj.setQuartiercode(quartier_code);
        obj.setQuartierlibelle(quartier_libelle);
        obj.setCommune(typObj);
    return matService.updatequartier(obj);

    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        matService.deletquartier(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<quartier> search(@PathParam("libelle") String libelle) {
        return matService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return matService.count();
    }


}

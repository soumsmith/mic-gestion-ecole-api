package com.vieecoles.ressource.operations;

import com.vieecoles.entities.operations.ville;
import com.vieecoles.entities.pays;
import com.vieecoles.services.operations.villeService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/ville")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class villeRessource {
    @Inject
    villeService matService ;
    @Inject
    EntityManager em;
    @GET
    @Path("pays/{pays}")
    public List<ville> list(@PathParam("pays") Long pays) {
        return matService.findByIdPays(pays);
    }

    @GET

    public List<ville> list() {
        return matService.findAllville();
    }


    @GET
    @Path("/{id}")
    public ville get(@PathParam("id") Long id) {
        return matService.findByIDville(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response create(  @FormParam("ville_code") String ville_code, @FormParam("ville_libelle") String ville_libelle ,
                             @FormParam("pays_id") Long pays_id) {
        pays typObj= em.getReference(pays.class,pays_id);

        ville obj = new ville();

        obj.setPays(typObj);
        obj.setVillecode(ville_code);
        obj.setVillelibelle(ville_libelle);

        return matService.create(obj);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public int update(@FormParam("ville_id") Long ville_id,@FormParam("ville_code") String ville_code, @FormParam("ville_libelle") String ville_libelle ,
                      @FormParam("pays_id") Long pays_id) {
        pays typObj= em.getReference(pays.class,pays_id);

        ville obj = new ville();
        obj.setPays(typObj);
        obj.setVilleid(ville_id);
        obj.setVillecode(ville_code);
        obj.setVillelibelle(ville_libelle);
    return matService.updateObjet(obj);

    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        matService.deletobjet(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<ville> search(@PathParam("libelle") String libelle) {
        return matService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return matService.count();
    }


}

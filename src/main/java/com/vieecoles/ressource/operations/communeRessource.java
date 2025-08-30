package com.vieecoles.ressource.operations;

import com.vieecoles.entities.operations.commune;
import com.vieecoles.entities.operations.ville;
import com.vieecoles.services.operations.communeService;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/commune")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class communeRessource {
    @Inject
    communeService matService ;
    @Inject
    EntityManager em;
    @GET
    @Path("ville/{ville}")
    public List<commune> list(@PathParam("ville") Long ville) {

        System.out.println("Villeid "+ville);
        return matService.findByIdville(ville);
    }

    @GET

    public List<commune> list() {
        return matService.findAllcommune();
    }


    @GET
    @Path("/{id}")
    public commune get(@PathParam("id") Long id) {
        return matService.findByIDcommune(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response create(  @FormParam("commune_code") String commune_code, @FormParam("commune_libelle") String commune_libelle ,
                             @FormParam("ville_id") Long ville_id) {
        ville typObj= em.getReference(ville.class,ville_id);

        commune obj = new commune();

        obj.setVille(typObj);
        obj.setCommunecode(commune_code);
        obj.setCommunelibelle(commune_libelle);

        return matService.create(obj);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public int update(@FormParam("commune_id") Long commune_id,@FormParam("commune_code") String commune_code, @FormParam("commune_libelle") String commune_libelle ,
                      @FormParam("ville_id") Long ville_id) {
        ville typObj= em.getReference(ville.class,ville_id);

        commune obj = new commune();
        obj.setCommunelibelle(commune_libelle);
        obj.setCommunecode(commune_code);
        obj.setVille(typObj);
        obj.setCommuneid(commune_id);
    return matService.updatecommune(obj);

    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        matService.deletobjet(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<commune> search(@PathParam("libelle") String libelle) {
        return matService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return matService.count();
    }


}

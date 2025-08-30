package com.vieecoles.ressource;

import com.vieecoles.dto.objetDto;
import com.vieecoles.entities.objet;
import com.vieecoles.entities.type_objet;
import com.vieecoles.services.objetService;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/objet")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class objetRessource {
    @Inject
    objetService matService ;
    @Inject
    EntityManager em;
    @GET
    @Path("type-objet/{type_objet}")
    public List<objet> list(@PathParam("type_objet") Long type_objet) {
        return matService.findByIdTypeObjet(type_objet);
    }

    @GET

    public List<objetDto> list() {
        return matService.findAllobjet();
    }


    @GET
    @Path("/{id}")
    public objet get(@PathParam("id") Long id) {
        return matService.findByIDObjet(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response create(  @FormParam("objet_code") String objet_code, @FormParam("objet_libelle") String objet_libelle ,
                             @FormParam("type_objet_id") Long type_objet_id) {
        type_objet typObj= em.getReference(type_objet.class,type_objet_id);

        objet obj = new objet();

        obj.setType_objet(typObj);
        obj.setObjetcode(objet_code);
        obj.setObjetlibelle(objet_libelle);

        return matService.create(obj);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public int update(@FormParam("objet_id") Long objet_id,@FormParam("objet_code") String objet_code, @FormParam("objet_libelle") String objet_libelle ,
                      @FormParam("type_objet_id") Long type_objet_id) {
        type_objet typObj= em.getReference(type_objet.class,type_objet_id);

        objet obj = new objet();
        obj.setObjetid(objet_id);
        obj.setType_objet(typObj);
        obj.setObjetcode(objet_code);
        obj.setObjetlibelle(objet_libelle);
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
    public List<objet> search(@PathParam("libelle") String libelle) {
        return matService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return matService.count();
    }


}

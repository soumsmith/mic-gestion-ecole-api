package com.vieecoles.ressource.operations;

import com.vieecoles.entities.niveau;
import com.vieecoles.entities.operations.classe;
import com.vieecoles.services.operations.classeService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
@Tag(name = "Mes Operations", description = "mes Operations")
@Path("/classe")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class classeRessource {
    @Inject
    classeService matService ;
    @Inject
    EntityManager em;
    @GET
    @Path("niveau/{niv_id}")
    public List<classe> list(@PathParam("niv_id") Long niv_id) {
        return matService.findByIdTypeclasse(niv_id);
    }

    @GET

    public List<classe> list() {
        return matService.findAllclasse();
    }


    @GET
    @Path("/{id}")
    public classe get(@PathParam("id") Long id) {
        return matService.findByIDclasse(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response create(  @FormParam("classe_code") String classe_code, @FormParam("classe_libelle") String classe_libelle ,
                             @FormParam("niveau_id") Long niveau_id) {
        niveau typObj= em.getReference(niveau.class,niveau_id);

        classe obj = new classe();

        obj.setNiveau(typObj);
        obj.setClassecode(classe_code);
        obj.setClasselibelle(classe_libelle);

        return matService.create(obj);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public int update(@FormParam("classe_id") Long classe_id,@FormParam("classe_code") String classe_code, @FormParam("classe_libelle") String classe_libelle ,
                      @FormParam("niveau_id") Long niveau_id) {
        niveau  typObj= em.getReference(niveau.class,niveau_id);

        classe obj = new classe();
        obj.setClasselibelle(classe_libelle);
        obj.setClassecode(classe_code);
        obj.setNiveau(typObj);
        obj.setClasseid(classe_id);
    return matService.updateclasse(obj);

    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        matService.deletclasse(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<classe> search(@PathParam("libelle") String libelle) {
        return matService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return matService.count();
    }


}

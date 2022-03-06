package ci.gouv.dgbf.ressource;

import ci.gouv.dgbf.entities.matiere;
import ci.gouv.dgbf.services.matiereService;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/matiere")
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
    @POST
    @Transactional
    public Response create(matiere mat) {
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

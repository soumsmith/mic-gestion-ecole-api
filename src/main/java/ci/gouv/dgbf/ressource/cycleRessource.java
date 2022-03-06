package ci.gouv.dgbf.ressource;

import ci.gouv.dgbf.entities.cycle;
import ci.gouv.dgbf.entities.pays;
import ci.gouv.dgbf.services.cycleService;
import ci.gouv.dgbf.services.paysService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/cycle")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class cycleRessource {
    @Inject
    cycleService matService ;
    @GET
    public List<cycle> list() {
        return matService.getListcycle();
    }

    @GET
    @Path("/{id}")
    public cycle get(@PathParam("id") Long id) {
        return matService.findById(id);
    }
    @POST
    @Transactional
    public Response create(cycle mat) {
        return matService.createcycle(mat);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public cycle update(@PathParam("id") Long id, cycle mat) {
    return matService.updateCycle(id,mat);

    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        matService.deletecycle(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<cycle> search(@PathParam("libelle") String libelle) {
        return matService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return matService.count();
    }


}

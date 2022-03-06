package ci.gouv.dgbf.ressource;


import ci.gouv.dgbf.entities.typedurre;
import ci.gouv.dgbf.entities.zone;
import ci.gouv.dgbf.services.typedurreService;
import ci.gouv.dgbf.services.zoneService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/type-durre")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class typedurreRessource {
    @Inject
    typedurreService typService ;
    @GET
    public List<typedurre> list() {
        return typService.getListtypedurre();
    }

    @GET
    @Path("/{id}")
    public typedurre get(@PathParam("id") Long id) {
        return typService.findById(id);
    }
    @POST
    @Transactional
    public Response create(typedurre zon) {
        return typService.createtypedurre(zon);
    }


    @PUT
    @Path("/{id}")
    @Transactional
    public typedurre update(@PathParam("id") Long id, typedurre zon) {
    return  typService.updatetypedurre(id,zon);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        typService.deletetypedurre(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<typedurre> search(@PathParam("libelle") String libelle) {
        return typService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return typService.count();
    }


}

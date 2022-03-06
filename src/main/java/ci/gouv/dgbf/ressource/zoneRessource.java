package ci.gouv.dgbf.ressource;


import ci.gouv.dgbf.entities.zone;

import ci.gouv.dgbf.services.zoneService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/zone")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class zoneRessource {
    @Inject
    zoneService zonService ;
    @GET
    public List<zone> list() {
        return zonService.getListzone();
    }

    @GET
    @Path("/{id}")
    public zone get(@PathParam("id") Long id) {
        return zonService.findById(id);
    }
    @POST
    @Transactional
    public Response create(zone zon) {
        return zonService.createzone(zon);
    }


    @PUT
    @Path("/{id}")
    @Transactional
    public zone update(@PathParam("id") Long id, zone zon) {
    return  zonService.updatezone(id,zon);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        zonService.deletezone(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<zone> search(@PathParam("libelle") String libelle) {
        return zonService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return zonService.count();
    }


}

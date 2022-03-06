package ci.gouv.dgbf.ressource;

import ci.gouv.dgbf.entities.motif_permission;
import ci.gouv.dgbf.entities.motif_sanction;
import ci.gouv.dgbf.services.motif_persmissionService;
import ci.gouv.dgbf.services.motif_sanctionService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/motif-sanction")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class motif_sanctionRessource {
    @Inject
    motif_sanctionService motService ;
    @GET
    public List<motif_sanction> list() {
        return motService.getListmotifsanction();
    }

    @GET
    @Path("/{id}")
    public motif_sanction get(@PathParam("id") Long id) {
        return motService.findById(id);
    }
    @POST
    @Transactional
    public Response create(motif_sanction mot) {
        return motService.createmotifSanction(mot);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public motif_sanction update(@PathParam("id") Long id, motif_sanction mat) {
    return motService.updatemotif_sanction(id,mat);

    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        motService.deleteMotifsanction(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<motif_sanction> search(@PathParam("libelle") String libelle) {
        return motService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return motService.count();
    }


}

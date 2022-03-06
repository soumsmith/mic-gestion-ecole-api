package ci.gouv.dgbf.ressource;

import ci.gouv.dgbf.entities.matiere;
import ci.gouv.dgbf.entities.motif_permission;
import ci.gouv.dgbf.services.matiereService;
import ci.gouv.dgbf.services.motif_persmissionService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/motif-permission")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class motif_permissionRessource {
    @Inject
    motif_persmissionService motService ;
    @GET
    public List<motif_permission> list() {
        return motService.getListmotifPermission();
    }

    @GET
    @Path("/{id}")
    public motif_permission get(@PathParam("id") Long id) {
        return motService.findById(id);
    }
    @POST
    @Transactional
    public Response create(motif_permission mot) {
        return motService.createmotifPermission(mot);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public motif_permission update(@PathParam("id") Long id, motif_permission mat) {
    return motService.updatemotif_permission(id,mat);

    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        motService.deleteMotifPermiss(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<motif_permission> search(@PathParam("libelle") String libelle) {
        return motService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return motService.count();
    }


}

package ci.gouv.dgbf.ressource;

import ci.gouv.dgbf.entities.emprunteur;
import ci.gouv.dgbf.entities.pays;
import ci.gouv.dgbf.services.emprunteurService;
import ci.gouv.dgbf.services.paysService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/emprunteur")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class emprunteurRessource {
    @Inject
    emprunteurService matService ;
    @GET
    public List<emprunteur> list() {
        return matService.getListemprunteur();
    }

    @GET
    @Path("/{id}")
    public emprunteur get(@PathParam("id") Long id) {
        return matService.findById(id);
    }
    @POST
    @Transactional
    public Response create(emprunteur mat) {
        return matService.createemprunteur(mat);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public emprunteur update(@PathParam("id") Long id, emprunteur mat) {
    return matService.updateemprunteur(id,mat);

    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        matService.deleteemprunteur(id);
    }

    @GET
    @Path("/search/{nom}")
    public List<emprunteur> search(@PathParam("nom") String libelle) {
        return matService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return matService.count();
    }


}

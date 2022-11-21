package com.vieecoles.ressource;

import com.vieecoles.dao.entities.Annee_scolaire;
import com.vieecoles.services.annee_scolaireService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/annee-scolaire")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class anne_scolaireRessource {
    @Inject
    annee_scolaireService anneeScolaireService ;
    @GET
    public List<Annee_scolaire> list() {
        return anneeScolaireService.getListNIveau();
    }

    @GET
    @Path("/{id}")
    public Annee_scolaire get(@PathParam("id") Long id) {
        return anneeScolaireService.findById(id);
    }

    @GET
    @Path("ecole/{code}")
    public List<Annee_scolaire> getAnneeScolaireByEcole(@PathParam("code") String code) {
        return anneeScolaireService.findAnneScolaireByEcole(code) ;
    }

    @GET
    @Path("ecole/visible/{code}")
    public Annee_scolaire getAnneeScolaireVisibleEcole(@PathParam("code") String code) {
        return anneeScolaireService.findAnneScolaireVisibleByEcole(code) ;
    }

    @POST
    @Transactional
    public Response create(Annee_scolaire niv) {
        return anneeScolaireService.createniveau(niv);
    }


    @PUT
    @Path("/{id}")
    @Transactional
    public Annee_scolaire update(@PathParam("id") Long id, Annee_scolaire niv) {
    return  anneeScolaireService.updateNiveau(id,niv);
    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        anneeScolaireService.deleteNiveau(id);
    }

    @GET
    @Path("/search/{niveaulibelle}")
    public List<Annee_scolaire> search(@PathParam("niveaulibelle") String niveaulibelle) {
        return anneeScolaireService.search(niveaulibelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return anneeScolaireService.count();
    }


}

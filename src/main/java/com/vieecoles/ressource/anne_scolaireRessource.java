package com.vieecoles.ressource;

import com.vieecoles.entities.Annee_Scolaire;
import com.vieecoles.services.annee_scolaireService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/annee-scolaire")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class anne_scolaireRessource {
    @Inject
    annee_scolaireService anneeScolaireService ;
    @GET
    public List<Annee_Scolaire> list() {
        return anneeScolaireService.getListNIveau();
    }

    @GET
    @Path("/{id}")
    public Annee_Scolaire get(@PathParam("id") Long id) {
        return anneeScolaireService.findById(id);
    }

    @GET
    @Path("ecole/{code}")
    public List<Annee_Scolaire> getAnneeScolaireByEcole(@PathParam("code") String code) {
        return anneeScolaireService.findAnneScolaireByEcole(code) ;
    }

    @GET
    @Path("ecole/visible/{code}")
    public Annee_Scolaire getAnneeScolaireVisibleEcole(@PathParam("code") String code) {
        return anneeScolaireService.findAnneScolaireVisibleByEcole(code) ;
    }

    @POST
    @Transactional
    public Response create(Annee_Scolaire niv) {
        return anneeScolaireService.createniveau(niv);
    }


    @PUT
    @Path("/{id}")
    @Transactional
    public Annee_Scolaire update(@PathParam("id") Long id, Annee_Scolaire niv) {
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
    public List<Annee_Scolaire> search(@PathParam("niveaulibelle") String niveaulibelle) {
        return anneeScolaireService.search(niveaulibelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return anneeScolaireService.count();
    }


}

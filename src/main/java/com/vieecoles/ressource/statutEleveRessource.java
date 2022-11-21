package com.vieecoles.ressource;

import com.vieecoles.dao.entities.Statuts_eleve;
import com.vieecoles.services.statutEleveService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/statutEleve")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class statutEleveRessource {
    @Inject
    statutEleveService statutEleveService ;
    @GET
    public List<Statuts_eleve> list() {
        return statutEleveService.getListNIveau();
    }

    @GET
    @Path("/{id}")
    public Statuts_eleve get(@PathParam("id") Long id) {
        return statutEleveService.findById(id);
    }

    @GET
    @Path("ecole/{code}")
    public List<Statuts_eleve> getStatutEleveByEcole(@PathParam("code") String code) {
        return statutEleveService.findStatutEleveByEcole(code) ;
    }



    @POST
    @Transactional
    public Response create(Statuts_eleve niv) {
        return statutEleveService.createniveau(niv);
    }


    @PUT
    @Path("/{id}")
    @Transactional
    public Statuts_eleve update(@PathParam("id") Long id, Statuts_eleve niv) {
    return  statutEleveService.updateNiveau(id,niv);
    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        statutEleveService.deleteNiveau(id);
    }

    @GET
    @Path("/search/{niveaulibelle}")
    public List<Statuts_eleve> search(@PathParam("niveaulibelle") String niveaulibelle) {
        return statutEleveService.search(niveaulibelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return statutEleveService.count();
    }


}

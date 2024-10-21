package com.vieecoles.Restclient.Resources;

import com.vieecoles.Restclient.Service.InscriptionService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/inscriptions-pouls-paid")
@ApplicationScoped
public class InscriptionResource {

    @Inject
    InscriptionService inscriptionService;

    @POST
    @Path("/creer-toutes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response creerInscriptions() {
        inscriptionService.creerInscriptionsPourTous();
        return Response.ok("Inscriptions créées avec succès").build();
    }
}

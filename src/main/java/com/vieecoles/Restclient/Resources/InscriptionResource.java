package com.vieecoles.Restclient.Resources;

import com.vieecoles.Restclient.Service.InscriptionService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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

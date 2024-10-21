package com.vieecoles.Restclient;

import com.vieecoles.dto.InscriptionRequest;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

@Path("/api/inscriptions")
@RegisterRestClient(configKey = "inscription-api")
public interface InscriptionApiClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void creerInscription(InscriptionRequest request); // Méthode pour appeler l'API d'inscription
}

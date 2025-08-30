package com.vieecoles.Restclient;

import com.vieecoles.dto.InscriptionRequest;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;

@Path("/api/inscriptions")
@RegisterRestClient(configKey = "inscription-api")
public interface InscriptionApiClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void creerInscription(InscriptionRequest request); // Méthode pour appeler l'API d'inscription
}

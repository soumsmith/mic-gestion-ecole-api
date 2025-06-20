package com.vieecoles.Restclient.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import com.vieecoles.Restclient.InscriptionApiClient;
import com.vieecoles.dto.InscriptionRequest;
import com.vieecoles.entities.EleveInscriptionArabePaid;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.List;

@ApplicationScoped
public class InscriptionService {


    @Inject
    @RestClient
    InscriptionApiClient inscriptionApiClient; // Client REST pour appeler l'API d'inscription

    public void creerInscriptionsPourTous() {
        List<EleveInscriptionArabePaid> eleves = EleveInscriptionArabePaid.listAll() ;
        System.out.println(eleves);

        for (EleveInscriptionArabePaid eleve : eleves) {
            try {
                InscriptionRequest request = new InscriptionRequest();
                request.setAuditActeur("DBA");
                request.setAuditSession(""); // Si vous avez une session à remplir
                request.setIdEleve(eleve.getELEVE());
                request.setIdTypeAffectation(eleve.getStatut_affectation());
                request.setIdAnciennete(eleve.getAnciennete());
                request.setIdScolarite(eleve.getScolarite_arabe());
                request.setIdClasse(eleve.getClasse_arabe());
                request.setIdClasse2(eleve.getClasse_francais());
                request.setIdScolarite2(eleve.getScolarite_francais());
                System.out.println("Requête envoyée : " + request);

                inscriptionApiClient.creerInscription(request); // Appel de l'API d'inscription
            } catch (Exception e) {
                // Gérer les erreurs d'appel à l'API
                System.err.println("Erreur lors de la création d'inscription pour l'élève : " + eleve.getELEVE());
                e.printStackTrace();
            }
        }
    }
}

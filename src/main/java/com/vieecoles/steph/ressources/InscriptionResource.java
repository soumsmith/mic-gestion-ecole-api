package com.vieecoles.ressource.steph.ressources;

import com.vieecoles.entities.Inscription;
import com.vieecoles.services.InscriptionService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.util.List;

@ApplicationScoped
@Path("/inscription")
public class InscriptionResource {
	@Inject
	InscriptionService inscriptionService;

	@GET
    @Path("/retrieve-by-branche-annee-statut/{annee}")
    @Operation(description = "Obtenir liste des inscrit par branche et statut", summary = "")
	@Tag(name = "Inscription")
    public List<Inscription> getByBrancheAndAnneeAndStatut(@PathParam("annee") long annee, @QueryParam("branche") long branche,@QueryParam("statut") String statut) {
    	return  inscriptionService.getByBrancheAndAnneeAndStatut(branche, annee, statut);

    }

	@GET
    @Path("/retrieve-to-attrib-classe/{annee}")
    @Operation(description = "Obtenir liste des inscrit par branche et statut", summary = "")
	@Tag(name = "Inscription")
    public List<Inscription> getEleveToAttribClasse(@PathParam("annee") long annee, @QueryParam("branche") long branche,@QueryParam("statut") String statut) {
    	return  inscriptionService.getByBrancheAndAnneeAndStatutNotinClasse(branche, annee, statut);

    }
}

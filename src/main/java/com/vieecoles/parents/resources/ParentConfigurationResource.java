package com.vieecoles.parents.resources;

import com.vieecoles.parents.entities.ParentConfiguration;
import com.vieecoles.parents.entities.ParentEnfant;
import com.vieecoles.parents.dto.ParentConfigurationRequest;
import com.vieecoles.parents.dto.EleveAssociationRequest;
import com.vieecoles.parents.services.ParentConfigurationService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/parent")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Parent Configuration", description = "Gestion des configurations parent")
public class ParentConfigurationResource {

    @Inject
    ParentConfigurationService parentConfigService;

    @GET
    @Path("/configuration/{telephone}")
    @Operation(summary = "Récupérer la configuration d'un parent")
    public Response getParentConfiguration(@PathParam("telephone") String telephone) {
        try {
            ParentConfiguration config = parentConfigService.getConfiguration(telephone);
            if (config == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Configuration non trouvée\"}")
                    .build();
            }
            return Response.ok(config).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Erreur lors de la récupération: " + e.getMessage() + "\"}")
                .build();
        }
    }

    @POST
    @Path("/configuration")
    @Operation(summary = "Sauvegarder la configuration d'un parent")
    public Response saveParentConfiguration(ParentConfigurationRequest request) {
        try {
            ParentConfiguration config = parentConfigService.saveConfiguration(request);
            return Response.ok(config).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Erreur lors de la sauvegarde: " + e.getMessage() + "\"}")
                .build();
        }
    }

    @GET
    @Path("/enfants/{telephone}")
    @Operation(summary = "Récupérer les enfants d'un parent")
    public Response getEnfantsByParent(@PathParam("telephone") String telephone) {
        try {
            List<ParentEnfant> enfants = parentConfigService.getEnfantsByParent(telephone);
            return Response.ok(enfants).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Erreur lors de la récupération: " + e.getMessage() + "\"}")
                .build();
        }
    }

    @POST
    @Path("/enfants/associate")
    @Operation(summary = "Associer un élève à un parent")
    public Response associateEleveToParent(EleveAssociationRequest request) {
        try {
            ParentEnfant association = parentConfigService.associateEleve(request);
            return Response.ok(association).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"message\": \"Erreur lors de l'association: " + e.getMessage() + "\"}")
                .build();
        }
    }

    @DELETE
    @Path("/enfants/{telephone}/{matricule}")
    @Operation(summary = "Supprimer l'association d'un élève")
    public Response removeEleveAssociation(@PathParam("telephone") String telephone,
                                        @PathParam("matricule") String matricule) {
        try {
            boolean removed = parentConfigService.removeEleveAssociation(telephone, matricule);
            if (removed) {
                return Response.ok("{\"message\": \"Association supprimée avec succès\"}").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Association non trouvée\"}")
                    .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Erreur lors de la suppression: " + e.getMessage() + "\"}")
                .build();
        }
    }
}

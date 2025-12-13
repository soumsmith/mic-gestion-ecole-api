package com.vieecoles.ressource.Notification;


import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import com.vieecoles.dto.TokenRegistrationRequest;
import com.vieecoles.dto.TokenUnregistrationRequest;
import com.vieecoles.entities.Notification.NotificationToken;
import com.vieecoles.services.notifications.NotificationTokenService;

import java.util.Map;
import java.util.List;


@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationTokenResource {
    private static final Logger LOG = Logger.getLogger(NotificationTokenResource.class);
    
    @Inject
    NotificationTokenService tokenService;
    
    /**
     * Enregistre un token FCM pour un utilisateur
     * POST /api/notifications/register-token
     */
    @POST
    @Path("/register-token")
    @Transactional
    public Response registerToken(@Valid TokenRegistrationRequest request) {
        try {
            tokenService.registerToken(
                request.userId,
                request.token,
                request.deviceType,
                request.matricules
            );
            
            return Response.ok()
                .entity(Map.of(
                    "success", true, 
                    "message", "Token enregistré avec succès",
                    "matriculesCount", request.matricules.size()
                ))
                .build();
        } catch (Exception e) {
            LOG.errorf(e, "Erreur lors de l'enregistrement du token");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of("success", false, "error", e.getMessage()))
                .build();
        }
    }
    
    /**
     * Supprime un token FCM
     * DELETE /api/notifications/unregister-token?userId={userId}&token={token}
     */
    @DELETE
    @Path("/unregister-token")
    @Transactional
    public Response unregisterToken(
            @QueryParam("userId") @NotBlank(message = "L'ID utilisateur est requis") String userId,
            @QueryParam("token") @NotBlank(message = "Le token FCM est requis") String token) {
        try {
            tokenService.unregisterToken(userId, token);
            
            return Response.ok()
                .entity(Map.of("success", true, "message", "Token supprimé avec succès"))
                .build();
        } catch (Exception e) {
            LOG.errorf(e, "Erreur lors de la suppression du token");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of("success", false, "error", e.getMessage()))
                .build();
        }
    }
    
    /**
     * Récupère tous les tokens d'un utilisateur
     * GET /api/notifications/tokens/{userId}
     */
    @GET
    @Path("/tokens/{userId}")
    public Response getUserTokens(@PathParam("userId") String userId) {
        try {
            List<NotificationToken> tokens = tokenService.getUserTokens(userId);
            return Response.ok(tokens).build();
        } catch (Exception e) {
            LOG.errorf(e, "Erreur lors de la récupération des tokens");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of("success", false, "error", e.getMessage()))
                .build();
        }
    }
    
    /**
     * Récupère tous les tokens associés à un matricule
     * GET /api/notifications/tokens-by-matricule/{matricule}
     */
    @GET
    @Path("/tokens-by-matricule/{matricule}")
    public Response getTokensByMatricule(@PathParam("matricule") String matricule) {
        try {
            List<String> tokens = tokenService.getTokenStringsByMatricule(matricule);
            return Response.ok(Map.of(
                "matricule", matricule,
                "tokens", tokens,
                "count", tokens.size()
            )).build();
        } catch (Exception e) {
            LOG.errorf(e, "Erreur lors de la récupération des tokens par matricule");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of("success", false, "error", e.getMessage()))
                .build();
        }
    }
    
    /**
     * Récupère tous les matricules associés à un token
     * GET /api/notifications/matricules-by-token?token={token}
     */
    @GET
    @Path("/matricules-by-token")
    public Response getMatriculesByToken(@QueryParam("token") @NotBlank String token) {
        try {
            List<String> matricules = tokenService.getMatriculesByToken(token);
            return Response.ok(Map.of(
                "token", token,
                "matricules", matricules,
                "count", matricules.size()
            )).build();
        } catch (Exception e) {
            LOG.errorf(e, "Erreur lors de la récupération des matricules par token");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of("success", false, "error", e.getMessage()))
                .build();
        }
    }
}

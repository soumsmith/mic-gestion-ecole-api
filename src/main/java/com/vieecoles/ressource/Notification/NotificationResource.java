package com.vieecoles.ressource.Notification;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.vieecoles.dto.MatriculeNotificationRequest;
import com.vieecoles.dto.MulticastNotificationRequest;
import com.vieecoles.dto.NotificationRequest;
import com.vieecoles.dto.TopicNotificationRequest;
import com.vieecoles.services.notifications.FirebaseService;
import com.vieecoles.services.notifications.NotificationTokenService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;

@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationResource {
    
    private static final Logger LOG = Logger.getLogger(NotificationResource.class);
    
    @Inject
    FirebaseService firebaseService;
    @Inject
  NotificationTokenService tokenService;

   @POST
    @Path("/send-by-matricule")
    public Response sendNotificationByMatricule(@Valid MatriculeNotificationRequest request) {
        try {
            // Récupérer tous les tokens associés au matricule
            List<String> tokens = tokenService.getTokenStringsByMatricule(request.matricule);
            
            if (tokens.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of(
                        "success", false,
                        "message", "Aucun token trouvé pour le matricule: " + request.matricule,
                        "matricule", request.matricule
                    ))
                    .build();
            }
            
            // Envoyer la notification à tous les tokens
            BatchResponse response = firebaseService.sendNotificationToMultipleTokens(
                tokens,
                request.title,
                request.body,
                request.data
            );
            
            LOG.infof("Notification envoyée pour le matricule %s: %d succès, %d échecs", 
                request.matricule, response.getSuccessCount(), response.getFailureCount());
            
            return Response.ok()
                .entity(Map.of(
                    "success", true,
                    "matricule", request.matricule,
                    "tokensCount", tokens.size(),
                    "successCount", response.getSuccessCount(),
                    "failureCount", response.getFailureCount()
                ))
                .build();
        } catch (FirebaseMessagingException e) {
            LOG.errorf(e, "Erreur lors de l'envoi de la notification pour le matricule: %s", request.matricule);
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of(
                    "success", false,
                    "error", e.getMessage(),
                    "errorCode", e.getErrorCode(),
                    "matricule", request.matricule
                ))
                .build();
        } catch (Exception e) {
            LOG.errorf(e, "Erreur inattendue lors de l'envoi de la notification pour le matricule: %s", request.matricule);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of(
                    "success", false,
                    "error", e.getMessage(),
                    "matricule", request.matricule
                ))
                .build();
        }
    }

    
    /**
     * Envoie une notification à un token spécifique
     * POST /api/notifications/send
     */
    @POST
    @Path("/send")
    public Response sendNotification(@Valid NotificationRequest request) {
        try {
            String messageId = firebaseService.sendNotificationToToken(
                request.token,
                request.title,
                request.body,
                request.data
            );
            
            return Response.ok()
                .entity(Map.of("success", true, "messageId", messageId))
                .build();
        } catch (FirebaseMessagingException e) {
            LOG.errorf(e, "Erreur lors de l'envoi de la notification");
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of(
                    "success", false,
                    "error", e.getMessage(),
                    "errorCode", e.getErrorCode()
                ))
                .build();
        }
    }
    
    /**
     * Envoie une notification à un topic
     * POST /api/notifications/send-to-topic
     */
    @POST
    @Path("/send-to-topic")
    public Response sendNotificationToTopic(@Valid TopicNotificationRequest request) {
        try {
            String messageId = firebaseService.sendNotificationToTopic(
                request.topic,
                request.title,
                request.body,
                request.data
            );
            
            return Response.ok()
                .entity(Map.of("success", true, "messageId", messageId))
                .build();
        } catch (FirebaseMessagingException e) {
            LOG.errorf(e, "Erreur lors de l'envoi de la notification au topic");
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of(
                    "success", false,
                    "error", e.getMessage(),
                    "errorCode", e.getErrorCode()
                ))
                .build();
        }
    }
    
    /**
     * Envoie une notification à plusieurs tokens
     * POST /api/notifications/send-multicast
     */
    @POST
    @Path("/send-multicast")
    public Response sendMulticastNotification(@Valid MulticastNotificationRequest request) {
        try {
            BatchResponse response = firebaseService.sendNotificationToMultipleTokens(
                request.tokens,
                request.title,
                request.body,
                request.data
            );
            
            return Response.ok()
                .entity(Map.of(
                    "success", true,
                    "successCount", response.getSuccessCount(),
                    "failureCount", response.getFailureCount()
                ))
                .build();
        } catch (FirebaseMessagingException e) {
            LOG.errorf(e, "Erreur lors de l'envoi des notifications multicast");
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of(
                    "success", false,
                    "error", e.getMessage(),
                    "errorCode", e.getErrorCode()
                ))
                .build();
        }
    }
}

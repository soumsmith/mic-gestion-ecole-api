package com.vieecoles.ressource.Notification;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.vieecoles.dto.MatriculeNotificationRequest;
import com.vieecoles.dto.MulticastNotificationRequest;
import com.vieecoles.dto.MultipleMatriculesNotificationRequest;
import com.vieecoles.dto.NotificationRequest;
import com.vieecoles.dto.TopicNotificationRequest;
import com.vieecoles.services.notifications.FirebaseService;
import com.vieecoles.services.notifications.NotificationTokenService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
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
            LOG.infof("Tentative d'envoi de notification pour le matricule: %s", request.matricule);
            
            // Vérifier que Firebase est initialisé
            if (FirebaseApp.getApps().isEmpty()) {
                LOG.error("Firebase n'est pas initialisé");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of(
                        "success", false,
                        "error", "Firebase n'est pas initialisé. Vérifiez la configuration Firebase.",
                        "errorCode", "FIREBASE_NOT_INITIALIZED",
                        "matricule", request.matricule
                    ))
                    .build();
            }
            
            // Récupérer tous les tokens associés au matricule
            List<String> tokens = tokenService.getTokenStringsByMatricule(request.matricule);
            
            LOG.infof("Tokens trouvés pour le matricule %s: %d", request.matricule, tokens.size());
            
            if (tokens.isEmpty()) {
                LOG.warnf("Aucun token trouvé pour le matricule: %s", request.matricule);
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of(
                        "success", false,
                        "message", "Aucun token trouvé pour le matricule: " + request.matricule,
                        "errorCode", "NO_TOKENS_FOUND",
                        "matricule", request.matricule,
                        "suggestion", "Assurez-vous que des tokens ont été enregistrés avec ce matricule via POST /api/notifications/register-token"
                    ))
                    .build();
            }
            
            // Vérifier que les tokens ne sont pas vides ou invalides
            List<String> validTokens = tokens.stream()
                .filter(token -> token != null && !token.trim().isEmpty())
                .toList();
            
            if (validTokens.isEmpty()) {
                LOG.warnf("Aucun token valide trouvé pour le matricule: %s", request.matricule);
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of(
                        "success", false,
                        "error", "Tous les tokens associés à ce matricule sont invalides",
                        "errorCode", "INVALID_TOKENS",
                        "matricule", request.matricule
                    ))
                    .build();
            }
            
            LOG.infof("Envoi de la notification à %d token(s) valide(s) pour le matricule: %s", 
                validTokens.size(), request.matricule);
            
            // Envoyer la notification à tous les tokens
            BatchResponse response = firebaseService.sendNotificationToMultipleTokens(
                validTokens,
                request.title,
                request.body,
                request.data
            );
            
            LOG.infof("Notification envoyée pour le matricule %s: %d succès, %d échecs", 
                request.matricule, response.getSuccessCount(), response.getFailureCount());
            
            // Vérifier s'il y a des échecs et logger les détails
            if (response.getFailureCount() > 0) {
                LOG.warnf("Certaines notifications ont échoué pour le matricule %s", request.matricule);
                response.getResponses().forEach((sendResponse) -> {
                    if (!sendResponse.isSuccessful()) {
                        LOG.errorf("Échec d'envoi: %s - %s", 
                            sendResponse.getException().getErrorCode(),
                            sendResponse.getException().getMessage());
                    }
                });
            }
            
            return Response.ok()
                .entity(Map.of(
                    "success", true,
                    "matricule", request.matricule,
                    "tokensCount", validTokens.size(),
                    "successCount", response.getSuccessCount(),
                    "failureCount", response.getFailureCount()
                ))
                .build();
        } catch (FirebaseMessagingException e) {
            LOG.errorf(e, "Erreur Firebase lors de l'envoi de la notification pour le matricule: %s", request.matricule);
            
            // Gérer spécifiquement les erreurs 404 de Firebase
            if (e.getMessage() != null && e.getMessage().contains("404") || 
                e.getMessage() != null && e.getMessage().contains("/batch")) {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of(
                        "success", false,
                        "error", "Erreur de configuration Firebase ou tokens invalides. Vérifiez la configuration Firebase et que les tokens sont valides.",
                        "errorCode", "FIREBASE_CONFIG_ERROR",
                        "matricule", request.matricule,
                        "details", e.getMessage()
                    ))
                    .build();
            }
            
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of(
                    "success", false,
                    "error", e.getMessage(),
                    "errorCode", e.getErrorCode() != null ? e.getErrorCode() : "UNKNOWN",
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
    @POST
    @Path("/send-by-multiple-matricules")
    public Response sendNotificationToMultipleMatricules(@Valid MultipleMatriculesNotificationRequest request) {
        try {
            LOG.infof("Tentative d'envoi de notification pour %d matricule(s): %s", 
                request.matricules.size(), request.matricules);
            
            // Vérifier que Firebase est initialisé
            if (FirebaseApp.getApps().isEmpty()) {
                LOG.error("Firebase n'est pas initialisé");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of(
                        "success", false,
                        "error", "Firebase n'est pas initialisé. Vérifiez la configuration Firebase.",
                        "errorCode", "FIREBASE_NOT_INITIALIZED",
                        "matricules", request.matricules
                    ))
                    .build();
            }
            
            // Récupérer tous les tokens associés à ces matricules (sans doublons)
            List<String> tokens = tokenService.getTokenStringsByMatricules(request.matricules);
            
            LOG.infof("Tokens trouvés pour %d matricule(s): %d token(s)", 
                request.matricules.size(), tokens.size());
            
            if (tokens.isEmpty()) {
                LOG.warnf("Aucun token trouvé pour les matricules: %s", request.matricules);
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of(
                        "success", false,
                        "message", "Aucun token trouvé pour les matricules fournis",
                        "errorCode", "NO_TOKENS_FOUND",
                        "matricules", request.matricules,
                        "suggestion", "Assurez-vous que des tokens ont été enregistrés avec ces matricules via POST /api/notifications/register-token"
                    ))
                    .build();
            }
            
            // Vérifier que les tokens ne sont pas vides ou invalides
            List<String> validTokens = tokens.stream()
                .filter(token -> token != null && !token.trim().isEmpty())
                .distinct() // Éliminer les doublons
                .toList();
            
            if (validTokens.isEmpty()) {
                LOG.warnf("Aucun token valide trouvé pour les matricules: %s", request.matricules);
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of(
                        "success", false,
                        "error", "Tous les tokens associés à ces matricules sont invalides",
                        "errorCode", "INVALID_TOKENS",
                        "matricules", request.matricules
                    ))
                    .build();
            }
            
            LOG.infof("Envoi de la notification à %d token(s) unique(s) pour %d matricule(s)", 
                validTokens.size(), request.matricules.size());
            
            // Envoyer la notification à tous les tokens
            BatchResponse response = firebaseService.sendNotificationToMultipleTokens(
                validTokens,
                request.title,
                request.body,
                request.data
            );
            
            LOG.infof("Notification envoyée pour %d matricule(s): %d succès, %d échecs", 
                request.matricules.size(), response.getSuccessCount(), response.getFailureCount());
            
            // Vérifier s'il y a des échecs et logger les détails
            if (response.getFailureCount() > 0) {
                LOG.warnf("Certaines notifications ont échoué pour les matricules: %s", request.matricules);
                response.getResponses().forEach((sendResponse) -> {
                    if (!sendResponse.isSuccessful()) {
                        LOG.errorf("Échec d'envoi: %s - %s", 
                            sendResponse.getException().getErrorCode(),
                            sendResponse.getException().getMessage());
                    }
                });
            }
            
            return Response.ok()
                .entity(Map.of(
                    "success", true,
                    "matricules", request.matricules,
                    "matriculesCount", request.matricules.size(),
                    "tokensCount", validTokens.size(),
                    "successCount", response.getSuccessCount(),
                    "failureCount", response.getFailureCount()
                ))
                .build();
        } catch (FirebaseMessagingException e) {
            LOG.errorf(e, "Erreur Firebase lors de l'envoi de la notification pour les matricules: %s", request.matricules);
            
            // Gérer spécifiquement les erreurs 404 de Firebase
            if (e.getMessage() != null && e.getMessage().contains("404") || 
                e.getMessage() != null && e.getMessage().contains("/batch")) {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of(
                        "success", false,
                        "error", "Erreur de configuration Firebase ou tokens invalides. Vérifiez la configuration Firebase et que les tokens sont valides.",
                        "errorCode", "FIREBASE_CONFIG_ERROR",
                        "matricules", request.matricules,
                        "details", e.getMessage()
                    ))
                    .build();
            }
            
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of(
                    "success", false,
                    "error", e.getMessage(),
                    "errorCode", e.getErrorCode() != null ? e.getErrorCode() : "UNKNOWN",
                    "matricules", request.matricules
                ))
                .build();
        } catch (Exception e) {
            LOG.errorf(e, "Erreur inattendue lors de l'envoi de la notification pour les matricules: %s", request.matricules);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of(
                    "success", false,
                    "error", e.getMessage(),
                    "matricules", request.matricules
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

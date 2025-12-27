package com.vieecoles.services.notifications;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.io.InputStream;

@ApplicationScoped
public class FirebaseService {
    
    private static final Logger LOG = Logger.getLogger(FirebaseService.class);
    
    @ConfigProperty(name = "firebase.service-account.path")
    String serviceAccountPath;
    
    @PostConstruct
    void init() {
        try {
            // Vérifier si Firebase est déjà initialisé
            if (FirebaseApp.getApps().isEmpty()) {
                InputStream serviceAccount = getClass()
                    .getClassLoader()
                    .getResourceAsStream(serviceAccountPath);
                
                if (serviceAccount == null) {
                    throw new RuntimeException(
                        "Fichier de service Firebase non trouvé: " + serviceAccountPath
                    );
                }
                
                FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
                
                FirebaseApp.initializeApp(options);
                LOG.info("✅ Firebase initialisé avec succès");
            } else {
                LOG.info("ℹ️ Firebase déjà initialisé");
            }
        } catch (Exception e) {
            LOG.error("❌ Erreur lors de l'initialisation de Firebase", e);
            throw new RuntimeException("Impossible d'initialiser Firebase", e);
        }
    }
    
    /**
     * Envoie une notification à un token FCM spécifique
     */
    public String sendNotificationToToken(
            String token,
            String title,
            String body,
            java.util.Map<String, String> data) throws FirebaseMessagingException {
        
        Notification notification = Notification.builder()
            .setTitle(title)
            .setBody(body)
            .build();
        
        Message.Builder messageBuilder = Message.builder()
            .setToken(token)
            .setNotification(notification);
        
        // Ajouter les données personnalisées
        if (data != null && !data.isEmpty()) {
            messageBuilder.putAllData(data);
        }
        
        Message message = messageBuilder.build();
        
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            LOG.infof("✅ Notification envoyée avec succès: %s", response);
            return response;
        } catch (FirebaseMessagingException e) {
            LOG.errorf(e, "❌ Erreur lors de l'envoi de la notification");
            throw e;
        }
    }
    
    /**
     * Envoie une notification à un topic
     */
    public String sendNotificationToTopic(
            String topic,
            String title,
            String body,
            java.util.Map<String, String> data) throws FirebaseMessagingException {
        
        Notification notification = Notification.builder()
            .setTitle(title)
            .setBody(body)
            .build();
        
        Message.Builder messageBuilder = Message.builder()
            .setTopic(topic)
            .setNotification(notification);
        
        if (data != null && !data.isEmpty()) {
            messageBuilder.putAllData(data);
        }
        
        Message message = messageBuilder.build();
        
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            LOG.infof("✅ Notification envoyée au topic %s: %s", topic, response);
            return response;
        } catch (FirebaseMessagingException e) {
            LOG.errorf(e, "❌ Erreur lors de l'envoi de la notification au topic %s", topic);
            throw e;
        }
    }
    
    /**
     * Envoie une notification à plusieurs tokens (multicast)
     */
    public BatchResponse sendNotificationToMultipleTokens(
            java.util.List<String> tokens,
            String title,
            String body,
            java.util.Map<String, String> data) throws FirebaseMessagingException {
        
        Notification notification = Notification.builder()
            .setTitle(title)
            .setBody(body)
            .build();
        
        MulticastMessage.Builder messageBuilder = MulticastMessage.builder()
            .addAllTokens(tokens)
            .setNotification(notification);
        
        if (data != null && !data.isEmpty()) {
            messageBuilder.putAllData(data);
        }
        
        MulticastMessage message = messageBuilder.build();
        
        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
            LOG.infof("✅ Notifications envoyées: %d succès, %d échecs", 
                response.getSuccessCount(), response.getFailureCount());
            return response;
        } catch (FirebaseMessagingException e) {
            LOG.errorf(e, "❌ Erreur lors de l'envoi des notifications");
            throw e;
        }
    }
}

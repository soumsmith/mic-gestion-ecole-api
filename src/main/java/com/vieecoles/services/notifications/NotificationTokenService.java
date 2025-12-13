package com.vieecoles.services.notifications;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import com.vieecoles.entities.Notification.NotificationToken;

import java.util.List;

@ApplicationScoped
public class NotificationTokenService {
  private static final Logger LOG = Logger.getLogger(NotificationTokenService.class);
    
    @Inject
    EntityManager entityManager;
    
    @Transactional
    public void registerToken(String userId, String token, String deviceType, List<String> matricules) {
        // Vérifier si le token existe déjà
        NotificationToken existingToken = entityManager
            .createQuery("SELECT t FROM NotificationToken t WHERE t.token = :token", NotificationToken.class)
            .setParameter("token", token)
            .getResultStream()
            .findFirst()
            .orElse(null);
        
        if (existingToken != null) {
            // Mettre à jour le token existant
            existingToken.setUserId(userId);
            existingToken.setDeviceType(deviceType);
            existingToken.setUpdatedAt(java.time.LocalDateTime.now());
            
            // Supprimer les anciens matricules
            existingToken.getMatricules().clear();
            
            // Ajouter les nouveaux matricules
            for (String matricule : matricules) {
                existingToken.addMatricule(matricule);
            }
            
            entityManager.merge(existingToken);
            LOG.infof("Token mis à jour pour l'utilisateur: %s avec %d matricules", userId, matricules.size());
        } else {
            // Créer un nouveau token
            NotificationToken newToken = new NotificationToken();
            newToken.setUserId(userId);
            newToken.setToken(token);
            newToken.setDeviceType(deviceType);
            newToken.setCreatedAt(java.time.LocalDateTime.now());
            newToken.setUpdatedAt(java.time.LocalDateTime.now());
            
            // Ajouter les matricules
            for (String matricule : matricules) {
                newToken.addMatricule(matricule);
            }
            
            entityManager.persist(newToken);
            LOG.infof("Nouveau token enregistré pour l'utilisateur: %s avec %d matricules", userId, matricules.size());
        }
    }
    
    /**
     * Ajoute des matricules à un token existant
     */
    @Transactional
    public void addMatriculesToToken(String token, List<String> matricules) {
        NotificationToken tokenEntity = entityManager
            .createQuery("SELECT t FROM NotificationToken t WHERE t.token = :token", NotificationToken.class)
            .setParameter("token", token)
            .getResultStream()
            .findFirst()
            .orElse(null);
        
        if (tokenEntity != null) {
            for (String matricule : matricules) {
                // Vérifier si le matricule n'existe pas déjà
                boolean exists = tokenEntity.getMatricules().stream()
                    .anyMatch(tm -> tm.getMatricule().equals(matricule));
                
                if (!exists) {
                    tokenEntity.addMatricule(matricule);
                }
            }
            tokenEntity.setUpdatedAt(java.time.LocalDateTime.now());
            entityManager.merge(tokenEntity);
            LOG.infof("Matricules ajoutés au token: %d", matricules.size());
        }
    }
    
    /**
     * Supprime des matricules d'un token
     */
    @Transactional
    public void removeMatriculesFromToken(String token, List<String> matricules) {
        NotificationToken tokenEntity = entityManager
            .createQuery("SELECT t FROM NotificationToken t WHERE t.token = :token", NotificationToken.class)
            .setParameter("token", token)
            .getResultStream()
            .findFirst()
            .orElse(null);
        
        if (tokenEntity != null) {
            for (String matricule : matricules) {
                tokenEntity.removeMatricule(matricule);
            }
            tokenEntity.setUpdatedAt(java.time.LocalDateTime.now());
            entityManager.merge(tokenEntity);
            LOG.infof("Matricules supprimés du token: %d", matricules.size());
        }
    }
    
    @Transactional
    public void unregisterToken(String userId, String token) {
        NotificationToken tokenEntity = entityManager
            .createQuery("SELECT t FROM NotificationToken t WHERE t.userId = :userId AND t.token = :token", NotificationToken.class)
            .setParameter("userId", userId)
            .setParameter("token", token)
            .getResultStream()
            .findFirst()
            .orElse(null);
        
        if (tokenEntity != null) {
            entityManager.remove(tokenEntity);
            LOG.infof("Token supprimé pour l'utilisateur: %s", userId);
        }
    }
    
    public List<NotificationToken> getUserTokens(String userId) {
        return entityManager
            .createQuery("SELECT t FROM NotificationToken t WHERE t.userId = :userId", NotificationToken.class)
            .setParameter("userId", userId)
            .getResultList();
    }
    
    public List<String> getUserTokenStrings(String userId) {
        return getUserTokens(userId).stream()
            .map(NotificationToken::getToken)
            .toList();
    }
    
    /**
     * Récupère tous les tokens associés à un matricule
     */
    public List<NotificationToken> getTokensByMatricule(String matricule) {
        return entityManager
            .createQuery(
                "SELECT DISTINCT t FROM NotificationToken t " +
                "JOIN t.matricules tm " +
                "WHERE tm.matricule = :matricule",
                NotificationToken.class
            )
            .setParameter("matricule", matricule)
            .getResultList();
    }
    
    /**
     * Récupère tous les tokens (chaînes) associés à un matricule
     */
    public List<String> getTokenStringsByMatricule(String matricule) {
        return getTokensByMatricule(matricule).stream()
            .map(NotificationToken::getToken)
            .toList();
    }
    
    /**
     * Récupère tous les tokens associés à plusieurs matricules
     */
    public List<NotificationToken> getTokensByMatricules(List<String> matricules) {
        return entityManager
            .createQuery(
                "SELECT DISTINCT t FROM NotificationToken t " +
                "JOIN t.matricules tm " +
                "WHERE tm.matricule IN :matricules",
                NotificationToken.class
            )
            .setParameter("matricules", matricules)
            .getResultList();
    }
    
    /**
     * Récupère tous les tokens (chaînes) associés à plusieurs matricules
     */
    public List<String> getTokenStringsByMatricules(List<String> matricules) {
        return getTokensByMatricules(matricules).stream()
            .map(NotificationToken::getToken)
            .distinct()
            .toList();
    }
    
    /**
     * Récupère tous les matricules associés à un token
     */
    public List<String> getMatriculesByToken(String token) {
        NotificationToken tokenEntity = entityManager
            .createQuery("SELECT t FROM NotificationToken t WHERE t.token = :token", NotificationToken.class)
            .setParameter("token", token)
            .getResultStream()
            .findFirst()
            .orElse(null);
        
        if (tokenEntity != null) {
            return tokenEntity.getMatricules().stream()
                .map(com.vieecoles.entities.Notification.TokenMatricule::getMatricule)
                .toList();
        }
        return java.util.Collections.emptyList();
    }
}

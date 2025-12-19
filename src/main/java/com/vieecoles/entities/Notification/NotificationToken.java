package com.vieecoles.entities.Notification;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "notification_tokens")
public class NotificationToken {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private String userId;
    
    @Column(name = "token", nullable = false, unique = true, length = 500)
    private String token;
    
    @Column(name = "device_type", nullable = false)
    private String deviceType; // "android" ou "ios"
    
    @OneToMany(mappedBy = "token", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TokenMatricule> matricules = new HashSet<>();
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    // Getters et setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getDeviceType() {
        return deviceType;
    }
    
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
    
    public Set<TokenMatricule> getMatricules() {
        return matricules;
    }
    
    public void setMatricules(Set<TokenMatricule> matricules) {
        this.matricules = matricules;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Méthode utilitaire pour ajouter un matricule
    public void addMatricule(String matricule) {
        TokenMatricule tokenMatricule = new TokenMatricule();
        tokenMatricule.setToken(this);
        tokenMatricule.setMatricule(matricule);
        this.matricules.add(tokenMatricule);
    }
    
    // Méthode utilitaire pour supprimer un matricule
    public void removeMatricule(String matricule) {
        this.matricules.removeIf(tm -> tm.getMatricule().equals(matricule));
    }
}

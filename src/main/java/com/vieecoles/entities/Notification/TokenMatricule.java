package com.vieecoles.entities.Notification;

import jakarta.persistence.*;

@Entity
@Table(name = "token_matricules", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"token_id", "matricule"}))
public class TokenMatricule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "token_id", nullable = false)
    private NotificationToken token;
    
    @Column(nullable = false, length = 50)
    private String matricule;
    
    @Column(nullable = false)
    private java.time.LocalDateTime createdAt;
    
    // Constructeurs
    public TokenMatricule() {
        this.createdAt = java.time.LocalDateTime.now();
    }
    
    // Getters et setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public NotificationToken getToken() {
        return token;
    }
    
    public void setToken(NotificationToken token) {
        this.token = token;
    }
    
    public String getMatricule() {
        return matricule;
    }
    
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
    
    public java.time.LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(java.time.LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

package com.vieecoles.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class TokenRegistrationRequest {
    
    @NotBlank(message = "L'ID utilisateur est requis")
    public String userId;
    
    @NotBlank(message = "Le token FCM est requis")
    public String token;
    
    @NotBlank(message = "Le type d'appareil est requis")
    public String deviceType; // "android" ou "ios"
    
    @NotEmpty(message = "Au moins un matricule est requis")
    public List<String> matricules; // Liste des matricules associés au token
    
    // Constructeurs
    public TokenRegistrationRequest() {}
    
    public TokenRegistrationRequest(String userId, String token, String deviceType, List<String> matricules) {
        this.userId = userId;
        this.token = token;
        this.deviceType = deviceType;
        this.matricules = matricules;
    }
}
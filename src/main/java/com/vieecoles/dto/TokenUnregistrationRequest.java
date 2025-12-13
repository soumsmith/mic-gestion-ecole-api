package com.vieecoles.dto;
import jakarta.validation.constraints.NotBlank;

public class TokenUnregistrationRequest {
    
    @NotBlank(message = "L'ID utilisateur est requis")
    public String userId;
    
    @NotBlank(message = "Le token FCM est requis")
    public String token;
    
    // Constructeurs
    public TokenUnregistrationRequest() {}
    
    public TokenUnregistrationRequest(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}

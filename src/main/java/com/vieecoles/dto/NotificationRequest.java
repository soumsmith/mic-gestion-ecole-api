package com.vieecoles.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public class NotificationRequest {
    
    @NotBlank(message = "Le token FCM est requis")
    public String token;
    
    @NotBlank(message = "Le titre est requis")
    public String title;
    
    @NotBlank(message = "Le corps est requis")
    public String body;
    
    public Map<String, String> data;
    
    // Constructeurs, getters, setters
    public NotificationRequest() {}
    
    public NotificationRequest(String token, String title, String body, Map<String, String> data) {
        this.token = token;
        this.title = title;
        this.body = body;
        this.data = data;
    }
}


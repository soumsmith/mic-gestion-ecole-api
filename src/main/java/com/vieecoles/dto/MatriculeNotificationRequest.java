package com.vieecoles.dto;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;

public class MatriculeNotificationRequest {

    @NotBlank(message = "Le matricule est requis")
    public String matricule;
    
    @NotBlank(message = "Le titre est requis")
    public String title;
    
    @NotBlank(message = "Le corps est requis")
    public String body;
    
    public Map<String, String> data;
    
    // Constructeurs
    public MatriculeNotificationRequest() {}
    
    public MatriculeNotificationRequest(String matricule, String title, String body, Map<String, String> data) {
        this.matricule = matricule;
        this.title = title;
        this.body = body;
        this.data = data;
    }
}

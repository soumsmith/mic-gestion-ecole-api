package com.vieecoles.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

public class MultipleMatriculesNotificationRequest {
    
    @NotEmpty(message = "Au moins un matricule est requis")
    public List<String> matricules;
    
    @NotBlank(message = "Le titre est requis")
    public String title;
    
    @NotBlank(message = "Le corps est requis")
    public String body;
    
    public Map<String, String> data;
    
    // Constructeurs
    public MultipleMatriculesNotificationRequest() {}
    
    public MultipleMatriculesNotificationRequest(List<String> matricules, String title, String body, Map<String, String> data) {
        this.matricules = matricules;
        this.title = title;
        this.body = body;
        this.data = data;
    }
}
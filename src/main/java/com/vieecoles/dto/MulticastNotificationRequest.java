package com.vieecoles.dto;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;


import jakarta.validation.constraints.NotNull;
public class MulticastNotificationRequest {
    @NotNull(message = "La liste des tokens est requise")
    public List<String> tokens;
    
    @NotBlank(message = "Le titre est requis")
    public String title;
    
    @NotBlank(message = "Le corps est requis")
    public String body;
    
    public Map<String, String> data;

}

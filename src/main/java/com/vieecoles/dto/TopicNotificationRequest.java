package com.vieecoles.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Map;

public class TopicNotificationRequest {
    
    @NotBlank(message = "Le topic est requis")
    public String topic;
    
    @NotBlank(message = "Le titre est requis")
    public String title;
    
    @NotBlank(message = "Le corps est requis")
    public String body;
    
    public Map<String, String> data;
}

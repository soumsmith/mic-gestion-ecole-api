package com.vieecoles.steph.dto;
public class LockResponseDto {
    public String conceptId;
    public String message;
    public boolean isLocked;

    public LockResponseDto(String conceptId, String message) {
        this.conceptId = conceptId;
        this.message = message;
    }
    
    public LockResponseDto(String conceptId, String message, Boolean isLocked) {
        this.conceptId = conceptId;
        this.message = message;
        this.isLocked = isLocked;
    }
}

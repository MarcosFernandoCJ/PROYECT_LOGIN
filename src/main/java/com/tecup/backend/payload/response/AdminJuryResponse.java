package com.tecup.backend.payload.response;

public class AdminJuryResponse {
    private Long userId;
    private String username;
    private Long eventId;
    private String eventName;
    private String message;

    public AdminJuryResponse(Long userId, String username, Long eventId, String eventName, String message) {
        this.userId = userId;
        this.username = username;
        this.eventId = eventId;
        this.eventName = eventName;
        this.message = message;
    }

    // Getters y Setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


package com.tecup.backend.payload.request;

import jakarta.validation.constraints.NotNull;

public class InscriptionRequest {
    @NotNull
    private Long eventId; // ID del evento al que el usuario desea inscribirse

    // Getters y Setters
    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}

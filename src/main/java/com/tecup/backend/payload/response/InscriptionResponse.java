package com.tecup.backend.payload.response;

public class InscriptionResponse {
    private Long id; // ID de la inscripción
    private String username;
    private String eventName;
    private String fecha_Inscripcion;

    // Constructor
    public InscriptionResponse(Long id, String username, String eventName, String fecha_Inscripcion) {
        this.id = id;
        this.username = username;
        this.eventName = eventName;
        this.fecha_Inscripcion = fecha_Inscripcion;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getFecha_Inscripcion() {
        return fecha_Inscripcion;
    }

    public void setFecha_Inscripcion(String fecha_Inscripcion) {
        this.fecha_Inscripcion = fecha_Inscripcion;
    }
}

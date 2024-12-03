package com.tecup.backend.payload.response;

import java.util.List;

public class AdminResponse {

    private Long userId; // ID del usuario afectado
    private String username; // Nombre del usuario afectado
    private List<String> roles; // Roles actuales del usuario
    private String message; // Mensaje sobre el resultado de la operación

    public AdminResponse() {}

    public AdminResponse(Long userId, String username, List<String> roles, String message) {
        this.userId = userId;
        this.username = username;
        this.roles = roles;
        this.message = message;
    }

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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

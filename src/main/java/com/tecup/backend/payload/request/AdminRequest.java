package com.tecup.backend.payload.request;

import jakarta.validation.constraints.NotNull;

public class AdminRequest {

    @NotNull
    private Long userId; // ID del usuario al que se aplicará la operación

    @NotNull
    private String roleName; // Nombre del rol a asignar o eliminar

    public AdminRequest() {}

    public AdminRequest(Long userId, String roleName) {
        this.userId = userId;
        this.roleName = roleName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}

package com.tecup.backend.payload.response;

import java.util.Date;

public class ScoreResponse {
    private String juryName; // Nombre del jurado
    private String groupName; // Nombre del grupo
    private int score; // Puntaje asignado
    private Date fechaPuntaje; // Fecha en que se asignó el puntaje

    public ScoreResponse(String juryName, String groupName, int score, Date fechaPuntaje) {
        this.juryName = juryName;
        this.groupName = groupName;
        this.score = score;
        this.fechaPuntaje = fechaPuntaje;
    }

    // Getters y Setters
    public String getJuryName() {
        return juryName;
    }

    public void setJuryName(String juryName) {
        this.juryName = juryName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getFechaPuntaje() {
        return fechaPuntaje;
    }

    public void setFechaPuntaje(Date fechaPuntaje) {
        this.fechaPuntaje = fechaPuntaje;
    }
}

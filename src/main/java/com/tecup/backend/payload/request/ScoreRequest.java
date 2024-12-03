package com.tecup.backend.payload.request;

public class ScoreRequest {
    private Long groupId; // ID del grupo al que se asignará el puntaje
    private int score; // Puntaje a asignar

    // Getters y Setters
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

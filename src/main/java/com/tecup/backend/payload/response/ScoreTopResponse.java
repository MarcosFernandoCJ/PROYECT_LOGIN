package com.tecup.backend.payload.response;

public class ScoreTopResponse {
    private int score;
    private String groupName;

    public ScoreTopResponse(int score, String groupName) {
        this.score = score;
        this.groupName = groupName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}

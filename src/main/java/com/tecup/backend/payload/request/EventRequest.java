package com.tecup.backend.payload.request;


import java.util.Date;

public class EventRequest {
    private String name;
    private String description;
    private String place;
    private Date startDate;
    private Date endDate;
    private int max_participants_group;
    private String imgEvent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getMax_participants_group() {
        return max_participants_group;
    }

    public void setMax_participants_group(int max_participants_group) {
        this.max_participants_group = max_participants_group;
    }

    public String getImgEvent() {
        return imgEvent;
    }

    public void setImgEvent(String imgEvent) {
        this.imgEvent = imgEvent;
    }
}

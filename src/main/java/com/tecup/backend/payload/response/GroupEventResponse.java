package com.tecup.backend.payload.response;

public class GroupEventResponse {
    private Long groupId;
    private String nameGroupEvent;
    private String nameEvent;
    private String nameDepartment;
    private int max_participants_group;

    public GroupEventResponse(Long groupId, String nameGroupEvent, String nameEvent, String nameDepartment, int max_participants_group) {
        this.groupId = groupId;
        this.nameGroupEvent = nameGroupEvent;
        this.nameEvent = nameEvent;
        this.nameDepartment = nameDepartment;
        this.max_participants_group = max_participants_group;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getNameGroupEvent() {
        return nameGroupEvent;
    }

    public void setNameGroupEvent(String nameGroupEvent) {
        this.nameGroupEvent = nameGroupEvent;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public String getNameDepartment() {
        return nameDepartment;
    }

    public void setNameDepartment(String nameDepartment) {
        this.nameDepartment = nameDepartment;
    }

    public int getMax_participants_group() {
        return max_participants_group;
    }

    public void setMax_participants_group(int max_participants_group) {
        this.max_participants_group = max_participants_group;
    }
}

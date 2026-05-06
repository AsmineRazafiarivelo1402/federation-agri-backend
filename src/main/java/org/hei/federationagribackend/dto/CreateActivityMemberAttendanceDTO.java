package org.hei.federationagribackend.dto;

public class CreateActivityMemberAttendanceDTO {
    private String memberIdentifier;
    private String attendanceStatus;

    public CreateActivityMemberAttendanceDTO() {
    }

    public CreateActivityMemberAttendanceDTO(String attendanceStatus, String memberIdentifier) {
        this.attendanceStatus = attendanceStatus;
        this.memberIdentifier = memberIdentifier;
    }

    public String getMemberIdentifier() {
        return memberIdentifier;
    }

    public void setMemberIdentifier(String memberIdentifier) {
        this.memberIdentifier = memberIdentifier;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }
}

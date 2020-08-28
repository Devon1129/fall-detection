package com.example.walabotsmartsecuritydefense.group;

public class Child {
    private String roomId;
    private String deviceId;//deviceId
    private String roomNumber;//roomNumber
    private String connectFlag;//connectFlag
    private String presence;//presence
    private String datetime;//datetime
    private boolean isChecked;//history(button)

    public Child(String roomId, String deviceId, String roomNumber, String connectFlag, String presence, String datetime) {
//    public Child(String userid, String fullname, String username) {
        this.roomId = roomId;
        this.deviceId = deviceId;
        this.roomNumber = roomNumber;
        this.connectFlag = connectFlag;
        this.presence = presence;
        this.datetime = datetime;
        this.isChecked = false;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public void toggle() {
        this.isChecked = !this.isChecked;
    }

    //get
    public String getRoomId() {
        return roomId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getConnectFlag() {
        return connectFlag;
    }

    public String getPresence() {
        return presence;
    }

    public String getDatetime() {
        return datetime;
    }

    public boolean getChecked() {
        return this.isChecked;
    }
}

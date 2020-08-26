package com.example.walabotsmartsecuritydefense.table.monitoring;

import org.litepal.crud.LitePalSupport;

 /*
    //即時監測
    //api/status.php?apitoken=xxx

        //api response:
        {"result":"全部監測點",
        "rooms":
        [{"zoneName":"紅樓A棟",
        "roomId":"42",
        "roomNumber":"紅樓A棟穿堂旁通道",

        "devices":
        [{"deviceId":"id_ODQ6ODk6ZWM6NDA6NDc6ZDA",
        "deviceType":"2",
        "connectFlag":"1",
        "presence":"in_spot",
        "datetime":"2020-08-25 15:37:49"}]},
     */

public class MonitoringPointStatus extends LitePalSupport {
    private int id;
    private String zoneName;
    private String roomId;
    private String roomNumber;
    private String deviceId;
    private String deviceType;
    private String connectFlag;
    private String presence;
    private String datetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getConnectFlag() {
        return connectFlag;
    }

    public void setConnectFlag(String connectFlag) {
        this.connectFlag = connectFlag;
    }

    public String getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}

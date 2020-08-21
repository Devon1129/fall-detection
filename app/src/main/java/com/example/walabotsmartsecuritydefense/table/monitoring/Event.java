package com.example.walabotsmartsecuritydefense.table.monitoring;

import org.litepal.crud.LitePalSupport;

    /* 取得歷史紀錄
    api/dump.php?table=event&apitoken=xxx

    api response:
    {"result":[
                {"id":"7290", //serialNumber
                "deviceId":"id_ODQ6ODk6ZWM6NDA6NDc6ZDA",
                "eventType":"fall",
                "eventCode":"fall_exit",
                "eventTime":"2020-08-13 16:58:54",
                "responseTime":"",
                "note":"",
                "createTime":"2020-08-13 16:59:20"},
     */

public class Event extends LitePalSupport {
    private int id;
    private String serialNumber;
    private String deviceId;
    private String eventType;
    private String eventCode;
    private String eventTime;
    private String responseTime;
    private String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    private String createTime;

}

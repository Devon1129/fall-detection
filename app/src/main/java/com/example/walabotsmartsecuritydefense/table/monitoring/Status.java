package com.example.walabotsmartsecuritydefense.table.monitoring;

import org.litepal.crud.LitePalSupport;

/* 取得即時狀態
    api/dump.php?table=status&apitoken=xxx

     api response:
    {"result":[
        {"id":"11964", //serialNumber
        "deviceId":"id_MzA6QUU6QTQ6RTQ6MDY6ODQ",
        "eventType":"presence",
        "eventCode":"in_spot",
        "eventTime":"2020-08-20 15:47:29",
        "responseTime":"",
        "note":"",
        "createTime":"2020-08-20 15:48:04"},
 */

public class Status extends LitePalSupport {
    private int id;
    private String serialNumber;
    private String deviceId;
    private String eventType;
    private String eventCode;
    private String eventTime;
    private String responseTime;
    private String note;
    private String createTime;

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
}

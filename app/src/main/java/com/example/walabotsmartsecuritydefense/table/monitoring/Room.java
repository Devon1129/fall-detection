package com.example.walabotsmartsecuritydefense.table.monitoring;

import org.litepal.crud.LitePalSupport;


    /* 取得設施[監測點]
    api/dump.php?table=room&apitoken=xxx

    api response:
     {"result":[
        {"id":"5", //serialNumber
        "customerId":"zhps",
        "zoneName":"測試區域",
        "roomNumber":"呆伯特隔間",
        "roomType":"",
        "deviceCount":"",
        "caregiverList":"",
        "residentList":"",
        "digit2":"",
        "style":"",
        "note":"",
        "activeFlag":"1",
        "createTime":"2020-07-03 09:46:12",
        "updateTime":"2020-08-19 10:34:46"},
     */

public class Room extends LitePalSupport {
    private int id;
    private String serialNumber;
    private String customerId;
    private String zoneName;
    private String roomNumber;
    private String roomType;
    private String deviceCount;
    private String caregiverList;
    private String residentList;
    private String digit2;
    private String style;
    private String note;
    private String activeFlag;
    private String createTime;
    private String updateTime;

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(String deviceCount) {
        this.deviceCount = deviceCount;
    }

    public String getCaregiverList() {
        return caregiverList;
    }

    public void setCaregiverList(String caregiverList) {
        this.caregiverList = caregiverList;
    }

    public String getResidentList() {
        return residentList;
    }

    public void setResidentList(String residentList) {
        this.residentList = residentList;
    }

    public String getDigit2() {
        return digit2;
    }

    public void setDigit2(String digit2) {
        this.digit2 = digit2;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

}





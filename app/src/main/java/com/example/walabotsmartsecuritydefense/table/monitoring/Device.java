package com.example.walabotsmartsecuritydefense.table.monitoring;

import org.litepal.crud.LitePalSupport;

     /* 取得設備
     api/dump.php?table=device&apitoken=xxx

     api response:
         {"result":[
            {"id":"1", //serialNumber
            "customerId":"zhps",
            "roomId":"5",
            "roomNumber":"",
            "patternId":"1",
            "deviceId":"id_YTQ6YTQ6ZDM6YjU6OWI6NDE",
            "deviceSerial":"",
            "deviceMacAddress":"",
            "deviceType":"2",
            "deviceConfig":"0,1.5,0.3,1.5,0,1.8;0,0,0,0",
            "deviceModel":"WH21BBUS02",
            "firmwareVersion":"1.6.50",
            "caregiverList":"",
            "note":"",
            "lastStateTime":"2020-08-20 15:23:57",
            "connectFlag":"1",
            "rebootFlag":"0",
            "activeFlag":"1",
            "createTime":"2020-06-12 16:55:35",
            "updateTime":"2020-08-20 15:24:01"},
         */

public class Device extends LitePalSupport {
    private int id;
    private String serialNumber;
    private String customerId;
    private String roomId;
    private String roomNumber;
    private String patternId;
    private String deviceId;
    private String deviceSerial;
    private String deviceMacAddress;
    private String deviceType;
    private String deviceConfig;
    private String deviceModel;
    private String firmwareVersion;
    private String caregiverList;
    private String note;
    private String lastStateTime;
    private String connectFlag;
    private String rebootFlag;
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

    public String getPatternId() {
        return patternId;
    }

    public void setPatternId(String patternId) {
        this.patternId = patternId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public String getDeviceMacAddress() {
        return deviceMacAddress;
    }

    public void setDeviceMacAddress(String deviceMacAddress) {
        this.deviceMacAddress = deviceMacAddress;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceConfig() {
        return deviceConfig;
    }

    public void setDeviceConfig(String deviceConfig) {
        this.deviceConfig = deviceConfig;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getCaregiverList() {
        return caregiverList;
    }

    public void setCaregiverList(String caregiverList) {
        this.caregiverList = caregiverList;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLastStateTime() {
        return lastStateTime;
    }

    public void setLastStateTime(String lastStateTime) {
        this.lastStateTime = lastStateTime;
    }

    public String getConnectFlag() {
        return connectFlag;
    }

    public void setConnectFlag(String connectFlag) {
        this.connectFlag = connectFlag;
    }

    public String getRebootFlag() {
        return rebootFlag;
    }

    public void setRebootFlag(String rebootFlag) {
        this.rebootFlag = rebootFlag;
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

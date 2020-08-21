package com.example.walabotsmartsecuritydefense.table.monitoring;

import org.litepal.crud.LitePalSupport;

    /* 取得區域
    api/dump.php?table=zone&apitoken=xxx

    api response:
         {"result":[{"id":"3",//serialNumber
                "customerId":"zhps",
                "zoneName":"測試區域",
                "note":"",
                "activeFlag":"1",
                "createTime":"2020-07-03 09:42:51",
                "updateTime":"2020-08-19 09:36:49"},
     */

public class Zone extends LitePalSupport {
    private int id;
    private String serialNumber;
    private String customerId;
    private String zoneName;
    private String note;
    private String activeFlag;
    private String createTime;
    private String updateTime;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

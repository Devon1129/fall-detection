package com.example.walabotsmartsecuritydefense.table;

import org.litepal.crud.LitePalSupport;


    /* 取得異常通知
        api/dump.php?table=notification_copy&username=xxx&apitoken=xxx

        api response:
        {"result":[
            {"id":"159", //serialNumber
            "source":"189",
            "username":"xhwg85",
            "readFlag":"0",
            "createTime":"2020-08-24 17:00:32",
            "updateTime":"",
            "customerId":"zhps",
            "roomId":"",
            "category":"in_spot",
            "title":"校園智慧安防",
            "content":"[總監寶座]監控點狀態：監測點有人。[2020-08-24 16:59:56]",
            "link":""}]}
 */

public class Notification extends LitePalSupport {
    private int id;
    private String serialNumber;
    private String source;
    private String username;
    private String readFlag;
    private String createTime;
    private String updateTime;
    private String customerId;
    private String roomId;
    private String category;
    private String title;
    private String content;
    private String link;

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

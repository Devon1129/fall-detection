package com.example.walabotsmartsecuritydefense.table;

import org.litepal.crud.LitePalSupport;

public class Announcement extends LitePalSupport {
    private int id;
    private String category;
    private String content;
    private String sort;
    private String publishFlag;
    private String createDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getPublishFlag() {
        return publishFlag;
    }

    public void setPublishFlag(String publish_flag) {
        this.publishFlag = publish_flag;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String create_date) {
        this.createDate = create_date;
    }
}

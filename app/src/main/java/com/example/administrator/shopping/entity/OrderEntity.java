package com.example.administrator.shopping.entity;

import java.io.Serializable;

public class OrderEntity implements Serializable {
    private long uuid;
    private String goodscount;
    private String goodsprice;
    private String datetime;
    private String status;
    private String isexist;
    private String description;


    public OrderEntity() {

    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "uuid=" + uuid +
                ", goodscount='" + goodscount + '\'' +
                ", goodsprice='" + goodsprice + '\'' +
                ", datetime='" + datetime + '\'' +
                ", status='" + status + '\'' +
                ", isexist='" + isexist + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }

    public String getGoodscount() {
        return goodscount;
    }

    public void setGoodscount(String goodscount) {
        this.goodscount = goodscount;
    }

    public String getGoodsprice() {
        return goodsprice;
    }

    public void setGoodsprice(String goodsprice) {
        this.goodsprice = goodsprice;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsexist() {
        return isexist;
    }

    public void setIsexist(String isexist) {
        this.isexist = isexist;
    }
}

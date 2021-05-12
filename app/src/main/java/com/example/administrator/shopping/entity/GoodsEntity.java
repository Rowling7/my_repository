package com.example.administrator.shopping.entity;

import android.graphics.Bitmap;

public class GoodsEntity {
    private String uuid;
    private String price;
    private String picture;
    private String suppiler;
    private String name;
    private String productionDate;
    private String shelfLife;
    private String weight;
    private String originPlace;
    private String star;
    private String format;
    private String description;


    private String number;
    private String amount;


    public GoodsEntity() {

    }


    @Override
    public String toString() {
        return "GoodsEntity{" +
                "uuid='" + uuid + '\'' +
                ", price='" + price + '\'' +
                ", picture=" + picture +
                ", suppiler='" + suppiler + '\'' +
                ", name='" + name + '\'' +
                ", productionDate='" + productionDate + '\'' +
                ", shelfLife='" + shelfLife + '\'' +
                ", weight='" + weight + '\'' +
                ", originPlace='" + originPlace + '\'' +
                ", star='" + star + '\'' +
                ", format='" + format + '\'' +
                ", description='" + description + '\'' +
                ", number='" + number + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSuppiler() {
        return suppiler;
    }

    public void setSuppiler(String suppiler) {
        this.suppiler = suppiler;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

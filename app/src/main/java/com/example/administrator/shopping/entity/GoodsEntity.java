package com.example.administrator.shopping.entity;

public class GoodsEntity {
    private String uuid;
    private String price;
    private String picture;
    private String suppiler;
    private String name;
    private String production_date;
    private String shelf_life;
    private String weight;
    private String origin_place;
    private String star;
    private String format;

    public GoodsEntity() {

    }

    @Override
    public String toString() {
        return "GoodsEntity{" +
                "uuid='" + uuid + '\'' +
                ", price='" + price + '\'' +
                ", picture='" + picture + '\'' +
                ", suppiler='" + suppiler + '\'' +
                ", name='" + name + '\'' +
                ", production_date='" + production_date + '\'' +
                ", shelf_life='" + shelf_life + '\'' +
                ", weight='" + weight + '\'' +
                ", origin_place='" + origin_place + '\'' +
                ", star='" + star + '\'' +
                ", format='" + format + '\'' +
                '}';
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

    public String getProduction_date() {
        return production_date;
    }

    public void setProduction_date(String production_date) {
        this.production_date = production_date;
    }

    public String getShelf_life() {
        return shelf_life;
    }

    public void setShelf_life(String shelf_life) {
        this.shelf_life = shelf_life;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getOrigin_place() {
        return origin_place;
    }

    public void setOrigin_place(String origin_place) {
        this.origin_place = origin_place;
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


}

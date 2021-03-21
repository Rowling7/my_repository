package com.example.administrator.shopping.entity;

public class ShoppingCartEntity {

    private Long uuid;
    private String entityuser_uuid;
    private String goods_uuid;
    private String Shop_uuid;
    private String isExist;

    private String name;
    private String price;
    private String number;


    public ShoppingCartEntity() {

    }

    @Override
    public String toString() {
        return "ShoppingCartEntity{" +
                "uuid=" + uuid +
                ", entityuser_uuid='" + entityuser_uuid + '\'' +
                ", goods_uuid='" + goods_uuid + '\'' +
                ", Shop_uuid='" + Shop_uuid + '\'' +
                ", isExist='" + isExist + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", number='" + number + '\'' +
                '}';
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public String getEntityuser_uuid() {
        return entityuser_uuid;
    }

    public void setEntityuser_uuid(String entityuser_uuid) {
        this.entityuser_uuid = entityuser_uuid;
    }

    public String getGoods_uuid() {
        return goods_uuid;
    }

    public void setGoods_uuid(String goods_uuid) {
        this.goods_uuid = goods_uuid;
    }

    public String getShop_uuid() {
        return Shop_uuid;
    }

    public void setShop_uuid(String shop_uuid) {
        Shop_uuid = shop_uuid;
    }

    public String getIsExist() {
        return isExist;
    }

    public void setIsExist(String isExist) {
        this.isExist = isExist;
    }
}

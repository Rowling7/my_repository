package com.example.administrator.shopping.entity;

public class AddressEntity {
    private long uuid;
    private String username;
    private long entityuser_uuid;
    private String address;

    public  AddressEntity(){

    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "uuid=" + uuid +
                ", username='" + username + '\'' +
                ", entityuser_uuid=" + entityuser_uuid +
                ", address='" + address + '\'' +
                '}';
    }

    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getEntityuser_uuid() {
        return entityuser_uuid;
    }

    public void setEntityuser_uuid(long entityuser_uuid) {
        this.entityuser_uuid = entityuser_uuid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

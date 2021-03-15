package com.example.administrator.shopping.entity;


public class EntityUserEntity {
    private String uuid;
    //private Long uuid;
    private String userName;
    private String phone;
    private String sex;
    private String userType;
    private String area;
    private String age;
    private String address;
    private String password;

    public EntityUserEntity() {

    }

    @Override
    public String toString() {
        return "EntityUserEntity{" +
                "uuid=" + uuid +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", userType='" + userType + '\'' +
                ", area='" + area + '\'' +
                ", age='" + age + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /*public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }*/

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
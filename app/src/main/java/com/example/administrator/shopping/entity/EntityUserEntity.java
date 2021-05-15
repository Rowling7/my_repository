package com.example.administrator.shopping.entity;


public class EntityUserEntity {

    private long uuid;
    private String userName;
    private String phone;
    private String sex;
    private String userType;
    private String area;
    private String age;
    private String address;
    private String password;
    private String wallet;
    private String realName;
    private String create_time;
    private String head;
    private String birthday;

    private String numbForNopay;
    private String numbForReceived;
    private String isnull;


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
                ", wallet='" + wallet + '\'' +
                ", realName='" + realName + '\'' +
                ", create_time='" + create_time + '\'' +
                ", head='" + head + '\'' +
                ", birthday='" + birthday + '\'' +
                ", numbForNopay='" + numbForNopay + '\'' +
                ", numbForReceived='" + numbForReceived + '\'' +
                ", isnull='" + isnull + '\'' +
                '}';
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getIsnull() {
        return isnull;
    }

    public void setIsnull(String isnull) {
        this.isnull = isnull;
    }

    public String getNumbForNopay() {
        return numbForNopay;
    }

    public void setNumbForNopay(String numbForNopay) {
        this.numbForNopay = numbForNopay;
    }

    public String getNumbForReceived() {
        return numbForReceived;
    }

    public void setNumbForReceived(String numbForReceived) {
        this.numbForReceived = numbForReceived;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
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

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
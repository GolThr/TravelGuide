package com.golthr.travelguide.obj;

import cn.bmob.v3.BmobUser;

public class Inform extends BmobUser {
    private String name;
    private String Address;
    private String Sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }
}

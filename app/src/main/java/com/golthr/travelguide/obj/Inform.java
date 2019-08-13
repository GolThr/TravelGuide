package com.golthr.travelguide.obj;

import cn.bmob.v3.BmobUser;

public class Inform extends BmobUser {
<<<<<<< HEAD
    private String Name;
=======
    private String name;
>>>>>>> origin/master
    private String Address;
    private String Sex;

    public String getName() {
<<<<<<< HEAD
        return Name;
    }

    public void setName(String name) {
        Name = name;
=======
        return name;
    }

    public void setName(String name) {
        this.name = name;
>>>>>>> origin/master
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

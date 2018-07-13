package com.example.sahni.travelvendorapp.Data;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by sahni on 27/6/18.
 */

public class Vendor {
    private Long phone;
    private float money;
    private String password;
    private String name;

    public Long getPhone() {
        return phone;
    }
    public String getPassword() {
        return password;
    }
    public float getMoney() {
        return money;
    }
    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setPhone(Long phone) {
        this.phone = phone;
    }
    public void setMoney(float money) {
        this.money = money;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public Vendor(String name, Long phone, String password){
        this.name=name;
        this.phone=phone;
        this.password=password;
        money=0;
    }
    public Vendor(){

    }
}

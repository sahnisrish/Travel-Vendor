package com.example.sahni.travelvendorapp.Data;

/**
 * Created by sahni on 10/7/18.
 */

public class Driver {
    private long phone;
    private String name;
    private String photo;
    private Boolean verified;

    public String getName() {
        return name;
    }
    public long getPhone() {
        return phone;
    }
    public String getPhoto() {
        return photo;
    }
    public Boolean getVerified() {
        return verified;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Driver(){

    }
}

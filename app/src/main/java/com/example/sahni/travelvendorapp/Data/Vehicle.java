package com.example.sahni.travelvendorapp.Data;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

/**
 * Created by sahni on 11/7/18.
 */

public class Vehicle {
    private String car;
    private String type;
    private String current_job;
    private String number;
    private GeoPoint location;
    private Boolean verified;
    private String rc_book;
    private String car_insurance;
    private String car_manufacturer;
    private String fuel_type;
    private Timestamp insurance_expiry;
    private long insurance_no;
    private long years;
    private ArrayList<String> pictures;

    public String getType() {
        return type;
    }
    public String getNumber() {
        return number;
    }
    public GeoPoint getLocation() {
        return location;
    }
    public String getCar() {
        return car;
    }
    public String getCurrent_job() {
        return current_job;
    }
    public Boolean getVerified() {
        return verified;
    }
    public ArrayList<String> getPictures() {
        return pictures;
    }
    public long getInsurance_no() {
        return insurance_no;
    }
    public long getYears() {
        return years;
    }
    public String getCar_insurance() {
        return car_insurance;
    }
    public String getCar_manufacturer() {
        return car_manufacturer;
    }
    public String getFuel_type() {
        return fuel_type;
    }
    public String getRc_book() {
        return rc_book;
    }
    public Timestamp getInsurance_expiry() {
        return insurance_expiry;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void setCar(String car) {
        this.car = car;
    }
    public void setCurrent_job(String current_job) {
        this.current_job = current_job;
    }
    public void setLocation(GeoPoint location) {
        this.location = location;
    }
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public void setCar_insurance(String car_insurance) {
        this.car_insurance = car_insurance;
    }
    public void setCar_manufacturer(String car_manufacturer) {
        this.car_manufacturer = car_manufacturer;
    }
    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }
    public void setInsurance_expiry(Timestamp insurance_expiry) {
        this.insurance_expiry = insurance_expiry;
    }
    public void setInsurance_no(long insurance_no) {
        this.insurance_no = insurance_no;
    }
    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }
    public void setRc_book(String rc_book) {
        this.rc_book = rc_book;
    }
    public void setYears(long years) {
        this.years = years;
    }
}

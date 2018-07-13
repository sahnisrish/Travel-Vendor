package com.example.sahni.travelvendorapp.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.sahni.travelvendorapp.Data.Driver;
import com.example.sahni.travelvendorapp.Data.Job;
import com.example.sahni.travelvendorapp.Data.Vehicle;
import com.example.sahni.travelvendorapp.Data.Vendor;
import com.example.sahni.travelvendorapp.Repository.Cache.DataCache;
import com.example.sahni.travelvendorapp.Repository.Firebase.CloudFirestore;

import java.util.ArrayList;

/**
 * Created by sahni on 27/6/18.
 */

public class Repo {
    private static Repo INSTANCE;
    private CloudFirestore firestore;
    private DataCache dataCache;
    private Repo(){
        firestore=CloudFirestore.getInstance();
        dataCache=DataCache.getInstance();
    }
    public static Repo getInstance(){
        if(INSTANCE==null)
            INSTANCE=new Repo();
        return INSTANCE;
    }

    public MutableLiveData<Vendor> fetchVendor(Long phone) {
        MutableLiveData<Vendor> vendor;
        if(dataCache.getVendor(phone)!=null){
            vendor=new MutableLiveData<>();
            vendor.setValue(dataCache.getVendor(phone));
        }
        else {
            vendor=firestore.getVendorDetails(phone);
        }
        return vendor;
    }

    public LiveData<Boolean> createAccount(Vendor vendor,ArrayList<Vehicle> vehicles, ArrayList<Driver> drivers) {
        return firestore.addVendor(vendor,vehicles,drivers);
    }

    public LiveData<ArrayList<Job>> fetchJobs() {
        MutableLiveData<ArrayList<Job>> jobs;
        jobs=firestore.getJobs();
        return jobs;
    }

    public LiveData<ArrayList<Driver>> fetchDrivers(String vendor) {
        return firestore.getDrivers(vendor);
    }

    public LiveData<ArrayList<Vehicle>> fetchVehicles(String s) {
        return firestore.getVehicles(s);
    }

    public void deleteVendor(String s) {
        firestore.deleteVendor(s);
    }
}

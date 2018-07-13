package com.example.sahni.travelvendorapp.Repository.Cache;

import com.example.sahni.travelvendorapp.Data.Vendor;

import java.util.HashMap;

/**
 * Created by sahni on 27/6/18.
 */

public class DataCache {
    private static DataCache INSTANCE;
    private HashMap<Long,Vendor> vendorData;
    private DataCache(){
        vendorData =new HashMap<>();
    }
    public static DataCache getInstance(){
        if(INSTANCE==null)
            INSTANCE=new DataCache();
        return INSTANCE;
    }
    public Vendor getVendor(Long phone){
        Vendor vendor=null;
        if(vendorData.containsKey(phone))
            vendor=vendorData.get(phone);
        return vendor;
    }

    public void addVendor(Long phone, Vendor vendor) {
        vendorData.put(phone,vendor);
    }
}

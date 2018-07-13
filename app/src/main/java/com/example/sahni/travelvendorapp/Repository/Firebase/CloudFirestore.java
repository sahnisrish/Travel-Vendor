package com.example.sahni.travelvendorapp.Repository.Firebase;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.sahni.travelvendorapp.Data.Driver;
import com.example.sahni.travelvendorapp.Data.Job;
import com.example.sahni.travelvendorapp.Data.Vehicle;
import com.example.sahni.travelvendorapp.Data.Vendor;
import com.example.sahni.travelvendorapp.Repository.Cache.DataCache;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sahni on 27/6/18.
 */

public class CloudFirestore {
    private final static String Vendors="Vendors";
    private final static String Jobs="Job";
    private final static String Drivers="Drivers";
    private final static String Vehicles="Vehicles";

    private FirebaseFirestore database;
    private DataCache dataCache;
    private static CloudFirestore INSTANCE;


    private CloudFirestore(){
        database=FirebaseFirestore.getInstance();
        dataCache=DataCache.getInstance();
    }
    public static CloudFirestore getInstance(){
        if(INSTANCE==null)
            INSTANCE=new CloudFirestore();
        return INSTANCE;
    }
    public MutableLiveData<Vendor> getVendorDetails(final Long phone){
        final MutableLiveData<Vendor> vendor=new MutableLiveData<>();
        database.collection(Vendors)
                .document(phone+"")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful() && task.getResult().exists()) {
                            vendor.setValue( task.getResult().toObject(Vendor.class));
                            dataCache.addVendor(phone,task.getResult().toObject(Vendor.class));
                        }
                        else
                            vendor.setValue(null);
                    }
                });
        return vendor;
    }

    public LiveData<Boolean> addVendor(final Vendor vendor, final ArrayList<Vehicle> vehicles, final ArrayList<Driver> drivers) {
        final MutableLiveData<Boolean> completed=new MutableLiveData<>();
        database.collection(Vendors)
                .document(vendor.getPhone()+"")
                .set(vendor)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            dataCache.addVendor(vendor.getPhone(),vendor);
                            completed.setValue(true);
                            for(int i=0;i<vehicles.size();i++){
                                database.collection("/"+Vendors+"/"+vendor.getPhone()+"/"+Vehicles)
                                        .document(vehicles.get(i).getNumber()+"")
                                        .set(vehicles.get(i))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(!task.isSuccessful())
                                                    completed.setValue(false);
                                            }
                                        });
                            }
                            for(int i=0;i<drivers.size();i++){
                                database.collection("/"+Vendors+"/"+vendor.getPhone()+"/"+Drivers)
                                        .document(drivers.get(i).getPhone()+"")
                                        .set(drivers.get(i))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(!task.isSuccessful())
                                                    completed.setValue(false);
                                            }
                                        });
                            }
                        }
                        else
                            completed.setValue(false);
                    }
                });
        return completed;
    }

    public MutableLiveData<ArrayList<Job>> getJobs() {
        final MutableLiveData<ArrayList<Job>> jobs=new MutableLiveData<>();
        database.collection(Jobs)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            ArrayList<Job> bookings=new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Job job=document.toObject(Job.class);
                                job.setId(document.getId());
                                if(!job.getCompleted())
                                    bookings.add(job);
                            }
                            jobs.setValue(bookings);
                        }
                        else
                            jobs.setValue(null);
                    }
                });
        return jobs;
    }

    public LiveData<ArrayList<Driver>> getDrivers(String vendor) {
        final MutableLiveData<ArrayList<Driver>> drivers= new MutableLiveData<>();
        database.collection("/"+Vendors+"/"+vendor+"/"+Drivers)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            ArrayList<Driver> driverArrayList=new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Driver driver=document.toObject(Driver.class);
                                driverArrayList.add(driver);
                            }
                            drivers.setValue(driverArrayList);
                        }
                        else
                            drivers.setValue(null);
                    }
                });
        return drivers;
    }

    public LiveData<ArrayList<Vehicle>> getVehicles(String s) {
        final MutableLiveData<ArrayList<Vehicle>> vehicles=new MutableLiveData<>();
        database.collection("/"+Vendors+"/"+s+"/"+Vehicles)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            ArrayList<Vehicle> vehicleArrayList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Vehicle vehicle = document.toObject(Vehicle.class);
                                vehicleArrayList.add(vehicle);
                            }
                            vehicles.setValue(vehicleArrayList);
                        }
                        else
                            vehicles.setValue(null);
                    }
                });
        return vehicles;
    }

    public void deleteVendor(String s) {
        database.collection(Vendors)
                .document(s)
                .delete();
    }
}

package com.example.sahni.travelvendorapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.example.sahni.travelvendorapp.Constant;
import com.example.sahni.travelvendorapp.Data.Driver;
import com.example.sahni.travelvendorapp.Data.Job;
import com.example.sahni.travelvendorapp.Data.Vehicle;
import com.example.sahni.travelvendorapp.Data.Vendor;
import com.example.sahni.travelvendorapp.Repository.Repo;
import com.example.sahni.travelvendorapp.UI.Fragment.VehiclesFragment;
import com.example.sahni.travelvendorapp.UI.ListAdapter.BookingsAdapter;
import com.example.sahni.travelvendorapp.UI.ListAdapter.DriversAdapter;
import com.example.sahni.travelvendorapp.UI.Fragment.BookingFragment;
import com.example.sahni.travelvendorapp.UI.Fragment.DriversFragment;
import com.example.sahni.travelvendorapp.UI.ListAdapter.VehiclesAdapter;

import java.util.ArrayList;

/**
 * Created by sahni on 3/7/18.
 */

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<Vendor> vendor;
    private Repo repository;

    private ArrayList<Job> jobs;
    private BookingsAdapter bookingsAdapter;
    private MutableLiveData<BookingFragment> bookingFragment;

    private ArrayList<Driver> drivers;
    private DriversAdapter driversAdapter;
    private MutableLiveData<DriversFragment> driversFragment;

    private ArrayList<Vehicle> vehicles;
    private VehiclesAdapter vehiclesAdapter;
    private MutableLiveData<VehiclesFragment> vehiclesFragment;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository=Repo.getInstance();
        jobs=new ArrayList<>();
        drivers=new ArrayList<>();
        vehicles=new ArrayList<>();
        bookingFragment = new MutableLiveData<>();
        vehiclesFragment = new MutableLiveData<>();
        driversFragment = new MutableLiveData<>();
    }

    public LiveData<Vendor> fetchVendor(SharedPreferences sharedPreferences) {
        if(vendor==null){
            Long phone=sharedPreferences.getLong(Constant.PHONE_KEY,-1);
            vendor=repository.fetchVendor(phone);
        }
        return vendor;
    }
    public LiveData<ArrayList<Job>> fetchJobs() {
        return repository.fetchJobs();
    }

    public BookingsAdapter getBookingsAdapter() {
        return bookingsAdapter;
    }
    public void setBookingsAdapter(BookingsAdapter bookingsAdapter) {
        this.bookingsAdapter = bookingsAdapter;
    }
    public ArrayList<Job> getJobs() {
        return jobs;
    }
    public void setJobs(ArrayList<Job> jobs) {
        this.jobs.clear();
        this.jobs.addAll(jobs);
    }
    public void setBookingFragment(BookingFragment bookingFragment) {
        this.bookingFragment.setValue(bookingFragment);
    }
    public LiveData<BookingFragment> getBookingFragment() {
        return bookingFragment;
    }

    public DriversAdapter getDriversAdapter() {
        return driversAdapter;
    }
    public void setDriversAdapter(DriversAdapter driversAdapter) {
        this.driversAdapter = driversAdapter;
    }
    public ArrayList<Driver> getDrivers() {
        return drivers;
    }
    public void setDrivers(ArrayList<Driver> drivers) {
        this.drivers.clear();
        this.drivers.addAll(drivers);
    }
    public LiveData<DriversFragment> getDriversFragment() {
        return driversFragment;
    }
    public void setDriversFragment(DriversFragment driversFragment) {
        this.driversFragment.setValue(driversFragment);
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }
    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles.clear();
        this.vehicles.addAll(vehicles);
    }
    public VehiclesAdapter getVehiclesAdapter() {
        return vehiclesAdapter;
    }
    public void setVehiclesAdapter(VehiclesAdapter vehiclesAdapter) {
        this.vehiclesAdapter = vehiclesAdapter;
    }
    public LiveData<VehiclesFragment> getVehiclesFragment() {
        return vehiclesFragment;
    }
    public void setVehiclesFragment(VehiclesFragment vehiclesFragment) {
        this.vehiclesFragment.setValue(vehiclesFragment);
    }

    public LiveData<Bitmap> fetchPhoto() {
        return null;
    }

    public LiveData<ArrayList<Driver>> fetchDrivers() {
        return repository.fetchDrivers(vendor.getValue().getPhone()+"");
    }

    public LiveData<ArrayList<Vehicle>> fetchVehicles() {
        return repository.fetchVehicles(vendor.getValue().getPhone()+"");
    }
}

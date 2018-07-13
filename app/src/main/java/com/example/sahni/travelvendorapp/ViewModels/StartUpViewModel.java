package com.example.sahni.travelvendorapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.sahni.travelvendorapp.Data.Driver;
import com.example.sahni.travelvendorapp.Data.Vehicle;
import com.example.sahni.travelvendorapp.Data.Vendor;
import com.example.sahni.travelvendorapp.Repository.Repo;

import java.util.ArrayList;

/**
 * Created by sahni on 19/6/18.
 */

public class StartUpViewModel extends AndroidViewModel {
    private MutableLiveData<String> password;
    private MutableLiveData<Long> phone;
    private MutableLiveData<String> name;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<Driver> drivers;
    private boolean logIn=false;
    private boolean firstAccess=true;
    Repo repository;
    public StartUpViewModel(@NonNull Application application) {
        super(application);
        repository=Repo.getInstance();
        vehicles=new ArrayList<>();
        drivers=new ArrayList<>();
    }

    public LiveData<String> getPassword() {
        return password;
    }
    public LiveData<Long> getPhone() {
        return phone;
    }
    public boolean loggedIn(){
        return logIn;
    }
    public boolean getFirstAccess(){
        return firstAccess;
    }


    public void setPassword(String password) {
        if(this.password==null)
            this.password=new MutableLiveData<>();
        this.password.setValue(password);
    }
    public void setPhone(String phone) {
        if(this.phone==null)
            this.phone=new MutableLiveData<>();
        this.phone.setValue(Long.parseLong(phone));
    }
    public void setLogIn(boolean logIn) {
        this.logIn = logIn;
    }
    public void setFirstAccess(boolean firstAccess) {
        this.firstAccess = firstAccess;
    }

    public LiveData<Vendor> fetchVendor() {
        return repository.fetchVendor(phone.getValue());
    }

    public void setName(String name) {
        if(this.name==null)
            this.name=new MutableLiveData<>();
        this.name.setValue(name);
    }

    public LiveData<Boolean> createAccount() {
        Vendor vendor;
        vendor=new Vendor(name.getValue(),phone.getValue(),password.getValue());
        return repository.createAccount(vendor,vehicles,drivers);
    }

    public void deleteVendor() {
        repository.deleteVendor(phone.getValue()+"");
    }
}

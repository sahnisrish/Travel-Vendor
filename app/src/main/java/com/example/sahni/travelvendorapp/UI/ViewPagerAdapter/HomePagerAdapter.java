package com.example.sahni.travelvendorapp.UI.ViewPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sahni.travelvendorapp.UI.Fragment.BookingFragment;
import com.example.sahni.travelvendorapp.UI.ListAdapter.CallBacks.BookingCallback;
import com.example.sahni.travelvendorapp.UI.ListAdapter.CallBacks.DriverCallback;
import com.example.sahni.travelvendorapp.UI.Fragment.DriversFragment;
import com.example.sahni.travelvendorapp.UI.Fragment.VehiclesFragment;
import com.example.sahni.travelvendorapp.UI.ListAdapter.CallBacks.VehiclesCallBack;

/**
 * Created by sahni on 3/7/18.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {
    BookingCallback bookingCallback;
    DriverCallback driverCallback;
    VehiclesCallBack vehiclesCallBack;
    public HomePagerAdapter(FragmentManager fm, BookingCallback bookingCallback, DriverCallback driverCallback, VehiclesCallBack vehiclesCallBack) {
        super(fm);
        this.bookingCallback=bookingCallback;
        this.driverCallback=driverCallback;
        this.vehiclesCallBack=vehiclesCallBack;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        if(position==0) {
            fragment=new BookingFragment(bookingCallback);
        }
        else if(position==1) {
            fragment=new DriversFragment(driverCallback);
        }
        else if(position==2) {
            fragment=new VehiclesFragment(vehiclesCallBack);
        }
        return fragment;
    }
}

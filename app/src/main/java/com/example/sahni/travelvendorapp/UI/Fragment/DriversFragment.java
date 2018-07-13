package com.example.sahni.travelvendorapp.UI.Fragment;


import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sahni.travelvendorapp.Data.Driver;
import com.example.sahni.travelvendorapp.R;
import com.example.sahni.travelvendorapp.UI.Activity.HomeActivity;
import com.example.sahni.travelvendorapp.UI.ListAdapter.DriversAdapter;
import com.example.sahni.travelvendorapp.UI.ListAdapter.CallBacks.DriverCallback;
import com.example.sahni.travelvendorapp.ViewModels.HomeViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriversFragment extends Fragment {

    View rootView;
    View view;
    ProgressBar progressBar;
    FloatingActionButton addDriver;
    HomeViewModel viewModel;
    RecyclerView driversList;
    DriverCallback callback;

    public DriversFragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public DriversFragment(DriverCallback callback){
        this.callback=callback;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_drivers, container, false);

        viewModel= ViewModelProviders.of(getActivity()).get(HomeViewModel.class);

        progressBar=rootView.findViewById(R.id.progress);
        view=rootView.findViewById(R.id.view);
        driversList=rootView.findViewById(R.id.drivers);
        addDriver=rootView.findViewById(R.id.add);

        viewModel.setDriversFragment(this);

        addDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Add Driver",Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.setDriversAdapter(new DriversAdapter(getContext(),viewModel.getDrivers(),callback));
        driversList.setAdapter(viewModel.getDriversAdapter());
        driversList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        return rootView;
    }

    private void ToggleProgress() {
        if(progressBar.getVisibility()==View.VISIBLE){
            progressBar.setVisibility(View.GONE);
            view.setAlpha(1f);
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            view.setAlpha(.3f);
        }
    }

    public void refresh() {
        ToggleProgress();
        viewModel.fetchDrivers().observe(this, new Observer<ArrayList<Driver>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Driver> drivers) {
                if(drivers!=null) {
                    viewModel.setDrivers(drivers);
                    viewModel.getDriversAdapter().notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(),"Error!!",Toast.LENGTH_SHORT).show();
                }
                ToggleProgress();
            }
        });
    }
}

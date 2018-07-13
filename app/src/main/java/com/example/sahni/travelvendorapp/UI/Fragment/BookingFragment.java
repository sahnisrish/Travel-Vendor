package com.example.sahni.travelvendorapp.UI.Fragment;


import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sahni.travelvendorapp.Data.Job;
import com.example.sahni.travelvendorapp.R;
import com.example.sahni.travelvendorapp.UI.Activity.HomeActivity;
import com.example.sahni.travelvendorapp.UI.ListAdapter.BookingsAdapter;
import com.example.sahni.travelvendorapp.UI.ListAdapter.CallBacks.BookingCallback;
import com.example.sahni.travelvendorapp.ViewModels.HomeViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment {

    View rootView;
    View view;
    ProgressBar progressBar;
    HomeViewModel viewModel;
    RecyclerView bookingList;
    BookingCallback callback;

    public BookingFragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public BookingFragment(BookingCallback callback){
        this.callback=callback;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_booking, container, false);
        viewModel= ViewModelProviders.of(getActivity()).get(HomeViewModel.class);

        progressBar=rootView.findViewById(R.id.progress);
        view=rootView.findViewById(R.id.view);
        bookingList=rootView.findViewById(R.id.bookings);

        viewModel.setBookingFragment(this);

        viewModel.setBookingsAdapter(new BookingsAdapter(getContext(),viewModel.getJobs(),callback));
        bookingList.setAdapter(viewModel.getBookingsAdapter());
        bookingList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

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
        viewModel.fetchJobs().observe(this, new Observer<ArrayList<Job>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Job> jobs) {
                if(jobs!=null) {
                    viewModel.setJobs(jobs);
                    viewModel.getBookingsAdapter().notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(),"Error!!",Toast.LENGTH_SHORT).show();
                }
                ToggleProgress();
            }
        });
    }
}

package com.example.sahni.travelvendorapp.UI.ListAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sahni.travelvendorapp.Data.Driver;
import com.example.sahni.travelvendorapp.Data.Vehicle;
import com.example.sahni.travelvendorapp.R;
import com.example.sahni.travelvendorapp.UI.ListAdapter.CallBacks.VehiclesCallBack;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sahni on 11/7/18.
 */

public class VehiclesAdapter extends RecyclerView.Adapter<VehiclesAdapter.DriversViewHolder> {

    Context context;
    ArrayList<Vehicle> vehicles;
    VehiclesCallBack callback;

    public VehiclesAdapter(Context context, ArrayList<Vehicle> vehicles, VehiclesCallBack callback){
        this.callback=callback;
        this.context=context;
        this.vehicles =vehicles;
    }

    @NonNull
    @Override
    public DriversViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=inflater.inflate(R.layout.vehicle_item,parent,false);
        DriversViewHolder viewHolder=new DriversViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DriversViewHolder holder, int position) {
        Vehicle vehicle= vehicles.get(position);
        holder.name.setText(vehicle.getCar());
        holder.number.setText(vehicle.getNumber());

        if(vehicle.getCurrent_job().equals("")){
            holder.onRide.setText("Free");
            holder.onRide.setTextColor(context.getResources().getColor(R.color.colorGreen));
        }
        else {
            holder.onRide.setText("On Ride");
            holder.onRide.setTextColor(context.getResources().getColor(R.color.colorOrange));
        }

        if(vehicle.getVerified()){
            holder.verified.setTextColor(context.getResources().getColor(R.color.colorGreen));
            holder.verified.setText("Verified");
        }
        else {
            holder.verified.setText("Verification Pending");
            holder.verified.setTextColor(context.getResources().getColor(R.color.colorOrange));
        }

    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    class DriversViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView number;
        TextView verified;
        TextView onRide;
        public DriversViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            number=itemView.findViewById(R.id.number);
            verified=itemView.findViewById(R.id.verified);
            onRide=itemView.findViewById(R.id.on_ride);
        }
    }
}
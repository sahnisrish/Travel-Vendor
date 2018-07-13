package com.example.sahni.travelvendorapp.UI.ListAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.sahni.travelvendorapp.Data.Driver;
import com.example.sahni.travelvendorapp.R;
import com.example.sahni.travelvendorapp.UI.ListAdapter.CallBacks.DriverCallback;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sahni on 11/7/18.
 */

public class DriversAdapter extends RecyclerView.Adapter<DriversAdapter.DriversViewHolder> {

    Context context;
    ArrayList<Driver> drivers;
    DriverCallback callback;

    public DriversAdapter(Context context, ArrayList<Driver> drivers, DriverCallback callback){
        this.callback=callback;
        this.context=context;
        this.drivers=drivers;
    }

    @NonNull
    @Override
    public DriversViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=inflater.inflate(R.layout.driver_item,parent,false);
        DriversViewHolder viewHolder=new DriversViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DriversViewHolder holder, int position) {
        Driver driver=drivers.get(position);
        holder.phone.setText(driver.getPhone()+"");
        holder.name.setText(driver.getName());
        if(!(driver.getPhoto().equals(""))){
            callback.setPhoto(holder.profile,driver.getPhoto());
        }
        if(driver.getVerified()){
            holder.verified.setTextColor(context.getResources().getColor(R.color.colorGreen));
            holder.verified.setText("Verified");
        }
        else {
            holder.verified.setTextColor(context.getResources().getColor(R.color.colorOrange));
            holder.verified.setText("Verification Pending");
        }
    }

    @Override
    public int getItemCount() {
        return drivers.size();
    }

    class DriversViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profile;
        TextView name;
        TextView phone;
        TextView verified;
        public DriversViewHolder(View itemView) {
            super(itemView);
            profile=itemView.findViewById(R.id.profile);
            name=itemView.findViewById(R.id.name);
            phone=itemView.findViewById(R.id.phone);
            verified=itemView.findViewById(R.id.verified);
        }
    }
}
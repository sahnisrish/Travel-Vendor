package com.example.sahni.travelvendorapp.UI.ListAdapter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sahni.travelvendorapp.Data.Job;
import com.example.sahni.travelvendorapp.R;
import com.example.sahni.travelvendorapp.UI.ListAdapter.CallBacks.BookingCallback;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by sahni on 3/7/18.
 */

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.BookingsViewHolder>{

    Context context;
    ArrayList<Job> bookings;
    BookingCallback callback;

    public BookingsAdapter(Context context, ArrayList<Job> bookings,BookingCallback callback){
        this.context=context;
        this.bookings=bookings;
        this.callback=callback;
    }
    @NonNull
    @Override
    public BookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=inflater.inflate(R.layout.booking_item,parent,false);
        BookingsViewHolder viewHolder=new BookingsViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookingsViewHolder holder, int position) {
        final Job currentJob=bookings.get(position);
        if(!currentJob.getOneway())
            holder.oneWay.setText("Round Trip");
        holder.time.setText(getDate(currentJob.getStart_time().getSeconds()));
        holder.cars.setText(currentJob.getType().toUpperCase());
        holder.toFrom.setText(getToandFrom(currentJob.getStart(),currentJob.getEnd()));
        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.book(currentJob);
            }
        });
    }

    private String getToandFrom(GeoPoint start, GeoPoint end) {
        String string="";
        Geocoder geocoder=new Geocoder(context);
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(start.getLatitude(), start.getLongitude(), 1);
            if (addresses != null && addresses.size() > 0) {
                String city = addresses.get(0).getLocality();
                string=string+city+" to ";
            }
            addresses = geocoder.getFromLocation(end.getLatitude(), end.getLongitude(), 1);
            if (addresses != null && addresses.size() > 0) {
                String city = addresses.get(0).getLocality();
                string=string+city;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public String getDate(Long timestamp) {
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");
        calendar.setTimeInMillis(timestamp * 1000);
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date currenTimeZone = calendar.getTime();
        return sdf.format(currenTimeZone);
    }

    class BookingsViewHolder extends RecyclerView.ViewHolder{
        TextView toFrom;
        TextView oneWay;
        TextView time;
        TextView cars;
        Button book;
        public BookingsViewHolder(View itemView) {
            super(itemView);
            toFrom=itemView.findViewById(R.id.to_from);
            oneWay=itemView.findViewById(R.id.one_way);
            time=itemView.findViewById(R.id.time);
            cars=itemView.findViewById(R.id.preferedCars);
            book=itemView.findViewById(R.id.book);
        }
    }
}

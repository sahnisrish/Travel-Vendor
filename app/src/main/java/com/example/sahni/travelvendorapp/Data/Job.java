package com.example.sahni.travelvendorapp.Data;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

/**
 * Created by sahni on 3/7/18.
 */

public class Job {
    private boolean completed;
    private String id;
    private GeoPoint end;
    private GeoPoint start;
    private float fare;
    private String type;
    private Timestamp start_time;
    private boolean one_way;

    public float getFare() {
        return fare;
    }
    public GeoPoint getEnd() {
        return end;
    }
    public GeoPoint getStart() {
        return start;
    }
    public String getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public Timestamp getStart_time() {
        return start_time;
    }
    public Boolean getOneway(){
        return one_way;
    }
    public Boolean getCompleted(){
        return completed;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public void setEnd(GeoPoint end) {
        this.end = end;
    }
    public void setFare(float fare) {
        this.fare = fare;
    }
    public void setOne_way(boolean one_way) {
        this.one_way = one_way;
    }
    public void setStart(GeoPoint start) {
        this.start = start;
    }
    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }
    public void setType(String type) {
        this.type = type;
    }

}

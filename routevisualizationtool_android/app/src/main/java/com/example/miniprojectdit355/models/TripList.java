
package com.example.miniprojectdit355.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TripList {

    @SerializedName("Trip")
    @Expose
    private List<Trip> trip = null;

    public List<Trip> getTrip() {
        return trip;
    }

    public void setTrip(List<Trip> trip) {
        this.trip = trip;
    }

}

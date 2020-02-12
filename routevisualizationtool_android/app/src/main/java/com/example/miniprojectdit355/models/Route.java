
package com.example.miniprojectdit355.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Route {

    @SerializedName("TripList")
    @Expose
    private TripList tripList;

    public TripList getTripList() {
        return tripList;
    }

    public void setTripList(TripList tripList) {
        this.tripList = tripList;
    }

}

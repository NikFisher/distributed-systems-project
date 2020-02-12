
package com.example.miniprojectdit355.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JourneyName {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("routeIdxFrom")
    @Expose
    private String routeIdxFrom;
    @SerializedName("routeIdxTo")
    @Expose
    private String routeIdxTo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRouteIdxFrom() {
        return routeIdxFrom;
    }

    public void setRouteIdxFrom(String routeIdxFrom) {
        this.routeIdxFrom = routeIdxFrom;
    }

    public String getRouteIdxTo() {
        return routeIdxTo;
    }

    public void setRouteIdxTo(String routeIdxTo) {
        this.routeIdxTo = routeIdxTo;
    }

}


package com.example.miniprojectdit355.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JourneyId {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("routeIdxFrom")
    @Expose
    private String routeIdxFrom;
    @SerializedName("routeIdxTo")
    @Expose
    private String routeIdxTo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

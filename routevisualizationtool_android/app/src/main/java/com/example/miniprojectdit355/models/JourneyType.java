
package com.example.miniprojectdit355.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JourneyType {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("routeIdxFrom")
    @Expose
    private String routeIdxFrom;
    @SerializedName("routeIdxTo")
    @Expose
    private String routeIdxTo;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

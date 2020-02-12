
package com.example.miniprojectdit355.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Direction {

    @SerializedName("routeIdxFrom")
    @Expose
    private String routeIdxFrom;
    @SerializedName("routeIdxTo")
    @Expose
    private String routeIdxTo;
    @SerializedName("$")
    @Expose
    private String $;

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

    public String get$() {
        return $;
    }

    public void set$(String $) {
        this.$ = $;
    }

}

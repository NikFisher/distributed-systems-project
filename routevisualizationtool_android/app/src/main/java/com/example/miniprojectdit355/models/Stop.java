
package com.example.miniprojectdit355.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Stop {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("routeIdx")
    @Expose
    private String routeIdx;
    @SerializedName("arrTime")
    @Expose
    private String arrTime;
    @SerializedName("arrDate")
    @Expose
    private String arrDate;
    @SerializedName("depTime")
    @Expose
    private String depTime;
    @SerializedName("depDate")
    @Expose
    private String depDate;
    @SerializedName("track")
    @Expose
    private String track;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getRouteIdx() {
        return routeIdx;
    }

    public void setRouteIdx(String routeIdx) {
        this.routeIdx = routeIdx;
    }

    public String getArrTime() {
        return arrTime;
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    public String getArrDate() {
        return arrDate;
    }

    public void setArrDate(String arrDate) {
        this.arrDate = arrDate;
    }

    public String getDepTime() {
        return depTime;
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Stop stop = (Stop) obj;
        return (stop.getName().equalsIgnoreCase(this.getName()));
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return this.getName();
    }
}

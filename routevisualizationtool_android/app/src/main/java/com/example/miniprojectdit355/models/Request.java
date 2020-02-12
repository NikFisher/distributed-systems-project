package com.example.miniprojectdit355.models;

import com.mapbox.mapboxsdk.geometry.LatLng;

public class Request {
    public Integer deviceId;
    public Integer requestId;
    public LatLng origin;
    public LatLng destination;
    public String timeOfDeparture;
    public String purpose;
    public String realTime;
    public String realDate;
    public String issuance;

    public String getIssuance() {
        return issuance;
    }

    public void setIssuance(String issuance) {
        this.issuance = issuance;
    }

    public Request(Integer deviceId, Integer requestId, LatLng origin, LatLng destination, String timeOfDeparture, String purpose, String issuance) {
        this.deviceId = deviceId;
        this.requestId = requestId;
        this.origin = origin;
        this.destination = destination;
        this.timeOfDeparture = timeOfDeparture;
        this.purpose = purpose;
        this.issuance = issuance;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public LatLng getOrigin() {
        return origin;
    }

    public void setOrigin(LatLng origin) {
        this.origin = origin;
    }

    public LatLng getDestination() {
        return destination;
    }

    public void setDestination(LatLng destination) {
        this.destination = destination;
    }

    public String getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public void setTimeOfDeparture(String timeOfDeparture) {
        this.timeOfDeparture = timeOfDeparture;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getRealTime(){
        String[] dateandtime = getTimeOfDeparture().split(" ");
        String [] componentsOfTime = dateandtime[1].split(":");
        return componentsOfTime[0]+":"+componentsOfTime[1];
    }

    public String getRealDate(){
        String[] dateandtime = getTimeOfDeparture().split(" ");
        return dateandtime[0];
    }


}

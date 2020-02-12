package com.example.miniprojectdit355.models;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.List;

public class Note {
    String title;
    String description;
    LatLng coordinates;
    String type;
    String priority;
    boolean select;

    //Empty Constructor
    public Note(){
    }

    public Note(String title, String description, LatLng coordinates, String type, String priority, boolean select) {
        this.title = title;
        this.description = description;
        this.coordinates = coordinates;
        this.type = type;
        this.priority = priority;
        this.select = select;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean isSelect() {
        return select;
    }
}

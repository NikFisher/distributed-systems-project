
package com.example.miniprojectdit355.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Color {

    @SerializedName("fgColor")
    @Expose
    private String fgColor;
    @SerializedName("bgColor")
    @Expose
    private String bgColor;
    @SerializedName("stroke")
    @Expose
    private String stroke;

    public String getFgColor() {
        return fgColor;
    }

    public void setFgColor(String fgColor) {
        this.fgColor = fgColor;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getStroke() {
        return stroke;
    }

    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

}

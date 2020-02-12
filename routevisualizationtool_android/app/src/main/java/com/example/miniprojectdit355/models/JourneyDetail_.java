
package com.example.miniprojectdit355.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JourneyDetail_ {

    @SerializedName("noNamespaceSchemaLocation")
    @Expose
    private String noNamespaceSchemaLocation;
    @SerializedName("servertime")
    @Expose
    private String servertime;
    @SerializedName("serverdate")
    @Expose
    private String serverdate;
    @SerializedName("Stop")
    @Expose
    private List<Stop> stop = null;
    @SerializedName("Color")
    @Expose
    private Color color;
    @SerializedName("GeometryRef")
    @Expose
    private GeometryRef geometryRef;
//    @SerializedName("JourneyName")
//    @Expose
//    private List<JourneyName> journeyName = null;
//    @SerializedName("JourneyType")
//    @Expose
//    private List<JourneyType> journeyType = null;
//    @SerializedName("JourneyId")
//    @Expose
//    private JourneyId journeyId;
//    @SerializedName("Direction")
//    @Expose
//    private List<Direction> direction = null;

    public String getNoNamespaceSchemaLocation() {
        return noNamespaceSchemaLocation;
    }

    public void setNoNamespaceSchemaLocation(String noNamespaceSchemaLocation) {
        this.noNamespaceSchemaLocation = noNamespaceSchemaLocation;
    }

    public String getServertime() {
        return servertime;
    }

    public void setServertime(String servertime) {
        this.servertime = servertime;
    }

    public String getServerdate() {
        return serverdate;
    }

    public void setServerdate(String serverdate) {
        this.serverdate = serverdate;
    }

    public List<Stop> getStop() {
        return stop;
    }

    public void setStop(List<Stop> stop) {
        this.stop = stop;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public GeometryRef getGeometryRef() {
        return geometryRef;
    }

    public void setGeometryRef(GeometryRef geometryRef) {
        this.geometryRef = geometryRef;
    }

//    public JourneyName getJourneyName() {
//        return journeyName;
//    }
//
//    public void setJourneyName(JourneyName journeyName) {
//        this.journeyName = journeyName;
//    }

//    public List<JourneyType> getJourneyType() {
//        return journeyType;
//    }
//
//    public void setJourneyType(List<JourneyType> journeyType) {
//        this.journeyType = journeyType;
//    }
//
//    public JourneyId getJourneyId() {
//        return journeyId;
//    }
//
//    public void setJourneyId(JourneyId journeyId) {
//        this.journeyId = journeyId;
//    }
//
//    public List<Direction> getDirection() {
//        return direction;
//    }
//
//    public void setDirection(List<Direction> direction) {
//        this.direction = direction;
//    }

}

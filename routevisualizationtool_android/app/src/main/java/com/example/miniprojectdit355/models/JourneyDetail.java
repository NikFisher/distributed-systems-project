
package com.example.miniprojectdit355.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JourneyDetail {

    @SerializedName("JourneyDetail")
    @Expose
    private JourneyDetail_ journeyDetail;

    public JourneyDetail_ getJourneyDetail() {
        return journeyDetail;
    }

    public void setJourneyDetail(JourneyDetail_ journeyDetail) {
        this.journeyDetail = journeyDetail;
    }

}

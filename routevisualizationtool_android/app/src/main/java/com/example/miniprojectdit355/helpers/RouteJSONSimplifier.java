package com.example.miniprojectdit355.helpers;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.miniprojectdit355.MainActivity;
import com.example.miniprojectdit355.R;
import com.example.miniprojectdit355.models.JourneyDetail;
import com.example.miniprojectdit355.models.Leg;
import com.example.miniprojectdit355.models.Route;
import com.example.miniprojectdit355.models.Stop;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class RouteJSONSimplifier {

    public static void addJouneyStops(Route route) {
        List<Leg> legsWithJourney = new ArrayList<Leg>();
        legsWithJourney.addAll(RouteJSONSimplifier.getLegsWithJourney(route.getTripList().getTrip().get(0).getLeg()));
        for (final Leg leg : legsWithJourney) {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, leg.getJourneyDetailRef().getRef(), null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Gson g = new Gson();
                            JourneyDetail journeyDetail = g.fromJson(response.toString(), JourneyDetail.class);
                            List<Stop> stops = journeyDetail.getJourneyDetail().getStop();
//                            Toast.makeText(getApplicationContext(), stops.toString(), Toast.LENGTH_SHORT).show();
                            MainActivity.totalStops.addAll(RouteJSONSimplifier.getSelectedStops(stops, leg));
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast toast = Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                            toast.show();
                        }

                    }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", getApplicationContext().getString(R.string.vasttrafik_token));
                    return headers;
                }


            };
            queue.add(jsonObjectRequest);
        }

    }

    public static ArrayList<Leg> getLegsWithJourney(List<Leg> legs) {
        ArrayList<Leg> toReturn = new ArrayList<>();
        for (int i = 0; i < legs.size(); i++) {
            if (!legs.get(i).getType().equalsIgnoreCase("WALK")) {
                toReturn.add(legs.get(i));
            }
        }
        return toReturn;
    }

    public static ArrayList<Leg> getWalkingLegs(List<Leg> legs){
        ArrayList<Leg> toReturn = new ArrayList<>();
        for (int i = 0; i < legs.size(); i++) {
            if (legs.get(i).getType().equalsIgnoreCase("WALK")) {
                toReturn.add(legs.get(i));
            }
        }
        return toReturn;
    }

    public static ArrayList<Stop> getSelectedStops(List<Stop> stops, Leg leg) {
        Stop originStop = new Stop();
        originStop.setId(leg.getOrigin().getId());
        originStop.setName(leg.getOrigin().getName());
        Stop destStop = new Stop();
        destStop.setName(leg.getDestination().getName());
        destStop.setId(leg.getDestination().getId());
        int originIndex = 0, destinationIndex = 0;

        for (int i = 0; i < stops.size(); i++) {
            if (originStop.getName().equalsIgnoreCase(stops.get(i).getName())) {
                originIndex = i;
                break;
            }
        }
        for (int i = originIndex; i < stops.size(); i++) {
            if (destStop.getName().equalsIgnoreCase(stops.get(i).getName())) {
                destinationIndex = i;
                break;
            }
        }
        ArrayList<Stop> toReturn = new ArrayList<>();
        for (int i = 0; i <= destinationIndex; i++) {
            toReturn.add(stops.get(i));
        }
        return toReturn;
    }


}

package com.example.miniprojectdit355.helpers;

import android.content.res.Resources;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.miniprojectdit355.R;
import com.example.miniprojectdit355.VolleyCallback;
import com.mapbox.mapboxsdk.storage.Resource;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class SuggestedRouteFetcher {

    public static String url = "https://api.vasttrafik.se/bin/rest.exe/v2/trip";

    public SuggestedRouteFetcher() {

    }

    public static void getRoute(com.example.miniprojectdit355.models.Request request, final VolleyCallback callback) {
        String realUrl = formatURLtrip(request.getOrigin().getLatitude(), request.getOrigin().getLongitude(), "From", request.getDestination().getLatitude(), request.getDestination().getLongitude(), "Destination", request.getRealDate(), request.getRealTime());
//        Toast.makeText(getApplicationContext(), realUrl, Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, realUrl , null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        callback.onSuccessResponse(response);

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

    public static String formatURLtrip(double originLat, double originLon, String originName, double destlat, double destlon, String destName, String date, String time){
        String formattedURL = url + "?originCoordLat=" + originLat;
        formattedURL+= "&originCoordLong=" + originLon;
        formattedURL+= "&originCoordName=" + originName;
        formattedURL+= "&destCoordLat=" + destlat;
        formattedURL+= "&destCoordLong=" + destlon;
        formattedURL+= "&destCoordName=" + destName;
        formattedURL+= "&date=" + date;
        formattedURL+= "&time=" + time;
        formattedURL+= "&format=json";
        formattedURL+= "&maxNo=1";
        return formattedURL;
    }

}

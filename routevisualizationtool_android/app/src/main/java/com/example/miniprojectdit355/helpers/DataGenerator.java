package com.example.miniprojectdit355.helpers;

import com.example.miniprojectdit355.MainActivity;
import com.example.miniprojectdit355.models.Request;
import com.google.gson.Gson;
import com.mapbox.mapboxsdk.geometry.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DataGenerator {

    public static long deviceID = 0;
    public static long requestID = 0;
    public static LatLng gothenburgNE = new LatLng(57.729031, 11.987918);
    public static LatLng gothenburgSW = new LatLng(57.683696, 11.914567);


    public DataGenerator() {

    }

    public static BigDecimal truncateDecimal(double x, int numberofDecimals) {
        if (x > 0) {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
        } else {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
        }
    }

    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public static double randBetweenDec(double start, double end) {
        return truncateDecimal(start + (Math.random() * (end - start)), 6).doubleValue();
    }


    public Request generateRequest() {

        LatLng origin = pointGenerator();
        LatLng dest = pointGenerator();
        String why = MainActivity.purpose[(int) (Math.random() * (MainActivity.purpose.length - 1 + 1))];

        SimpleDateFormat dfDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        int year = 2020;
//        int month = randBetween(0, 11);
        int month = 1;
        int hour = randBetween(12, 23);
        int min = randBetween(0, 59);
        int sec = randBetween(0, 59);


        GregorianCalendar gc = new GregorianCalendar(year, month, 1);
//        int day = randBetween(16, gc.getActualMaximum(gc.DAY_OF_MONTH));
        int day = 4;

        gc.set(year, month, day, hour, min, sec);

        String date = dfDateTime.format(gc.getTime());

        JSONObject toReturn = new JSONObject();
        JSONObject originInfo = new JSONObject();
        JSONObject destInfo = new JSONObject();


        try {

            originInfo.put("latitude", origin.getLatitude());
            originInfo.put("longitude", origin.getLongitude());
            destInfo.put("latitude", dest.getLatitude());
            destInfo.put("longitude", dest.getLongitude());
            toReturn.put("deviceID", deviceID++);
            toReturn.put("requestID", requestID++);
            toReturn.put("origin", originInfo);
            toReturn.put("destination", destInfo);
            toReturn.put("timeOfDeparture", date);
            toReturn.put("purpose", why);
        } catch (JSONException e) {
            e.printStackTrace();

        }

        Gson g = new Gson();
        Request objectToReturn = g.fromJson(toReturn.toString(), Request.class);

        return objectToReturn;

    }

    public LatLng pointGenerator() {
        double lat = randBetweenDec(gothenburgSW.getLatitude(), gothenburgNE.getLatitude());
        double lng = randBetweenDec(gothenburgSW.getLongitude(), gothenburgNE.getLongitude());
        return new LatLng(lat, lng);
    }
}

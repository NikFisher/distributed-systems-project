package com.example.miniprojectdit355.helpers;

import com.example.miniprojectdit355.MainActivity;
import com.example.miniprojectdit355.models.Leg;
import com.example.miniprojectdit355.models.Request;
import com.example.miniprojectdit355.models.Route;
import com.example.miniprojectdit355.models.Stop;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Identifier {

    public static HashMap<Stop, Integer> occurrences = new HashMap<>();


    public Identifier(ArrayList<Route> routes) {

    }

    public static void getBottlenecks() {
//        ArrayList<Stop> stops = new ArrayList<>();

        HashSet<Stop> setOfStops = new HashSet<Stop>(MainActivity.totalStops);

//        for (JSONObject x : routes){
//            ArrayList<Stop> stopsForRoute = RouteJSONSimplifier.getJourneyStops(x.getJourneyURL());
//            stops.addAll(stopsForRoute);
//            setOfStops.addAll(stopsForRoute);
//        }


//        Toast.makeText(getApplicationContext(), "SET \n"+set.size()+" TOTAL "+MainActivity.totalStops.size(), Toast.LENGTH_SHORT).show();


//        for (Stop x : MainActivity.totalStops){
//            occurrences.put(x, 0);
//        }

        for (Stop x : MainActivity.totalStops) {
            if (occurrences.containsKey(x)) {
                Integer count = occurrences.get(x);
                count = count + 1;
                occurrences.put(x, count);
            } else {
                occurrences.put(x, 0);
            }

        }


        Iterator<Map.Entry<Stop, Integer>> hmIterator = occurrences.entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry<Stop, Integer> value = hmIterator.next();
            if (value.getValue() > 50) {
                if (!MainActivity.bottleneck.contains(value.getKey())) {
                    MainActivity.bottleneck.add(value.getKey());
                }
            }

        }

    }

    public static void getBlindspots(Request request, Route route) {
        LatLng origin = request.getOrigin();
        LatLng dest = request.getDestination();
        List<Leg> legs = route.getTripList().getTrip().get(0).getLeg();
        Leg originLeg = legs.get(0);
        Leg destLeg = legs.get(legs.size() - 1);

        SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date originStartTime = new Date();
        Date originEndTime = new Date();
        Date destStartTime = new Date();
        Date destEndTime = new Date();

        try {
            originStartTime = df.parse(originLeg.getOrigin().getTime());
            originEndTime = df.parse(originLeg.getDestination().getTime());
            destStartTime = df.parse(destLeg.getOrigin().getTime());
            destEndTime = df.parse(destLeg.getDestination().getTime());
        } catch (ParseException e) {

        }

        long originLegDiff = (originEndTime.getTime() - originStartTime.getTime());
        long destLegDiff = (destEndTime.getTime() - destStartTime.getTime());

        int originDays = (int) ( originLegDiff / (1000 * 60 * 60 * 24));
        int originHours = (int) ((originLegDiff - (1000 * 60 * 60 * 24 * originDays)) / (1000 * 60 * 60));
        int originMin = (int) (originLegDiff - (1000 * 60 * 60 * 24 * originDays) - (1000 * 60 * 60 * originHours)) / (1000 * 60);
        originHours = (originHours < 0 ? -originHours : originHours);

        int destDays = (int) ( destLegDiff / (1000 * 60 * 60 * 24));
        int destHours = (int) ((destLegDiff - (1000 * 60 * 60 * 24 * destDays)) / (1000 * 60 * 60));
        int destMin = (int) (destLegDiff - (1000 * 60 * 60 * 24 * destDays) - (1000 * 60 * 60 * destHours)) / (1000 * 60);
        destHours = (destHours < 0 ? -destHours : destHours);

        if(originMin>=6&&originLeg.getType().equalsIgnoreCase("WALK")){
            MainActivity.blindspot.add(origin);
        }

        if(destMin>=6&&destLeg.getType().equalsIgnoreCase("WALK")){
            MainActivity.blindspot.add(dest);
        }

    }

}

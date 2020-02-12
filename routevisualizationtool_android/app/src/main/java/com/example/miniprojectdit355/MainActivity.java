package com.example.miniprojectdit355;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.example.miniprojectdit355.fragments.MapFragment;
import com.example.miniprojectdit355.fragments.NoteFragment;
import com.example.miniprojectdit355.fragments.RouteFragment;
import com.example.miniprojectdit355.helpers.DataGenerator;
import com.example.miniprojectdit355.helpers.Identifier;
import com.example.miniprojectdit355.helpers.RouteJSONSimplifier;
import com.example.miniprojectdit355.helpers.SuggestedRouteFetcher;
import com.example.miniprojectdit355.models.Note;
import com.example.miniprojectdit355.models.Request;
import com.example.miniprojectdit355.models.Route;
import com.example.miniprojectdit355.models.Stop;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {
    public static List<Note> mList = new ArrayList<>();
    public static ArrayList<Route> routeList = new ArrayList<>();
    public static List<Stop> totalStops = new ArrayList<>();
    public static ArrayList<Stop> bottleneck = new ArrayList<>();
    public static ArrayList<LatLng> blindspot = new ArrayList<>();
    public static List<Request> RequestList = new ArrayList<>();
    public TimePicker start;
    public TimePicker end;
    public Spinner purposeSpinner;
    static Calendar startDateData;
    static Calendar endDateData;
    int counting = 0;
    Calendar c = Calendar.getInstance();
    //final String serverUri = "tcp://iot.eclipse.org:1883";
    final String serverUri = "tcp://192.168.43.225:1883";
    final String subscriptionTopic = "filtered_data";
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    Calendar rightNow;
    Calendar beginningTime;
    public static String[] purpose = {"Work","School","Other"};
    public static String selectedPurpose="work";

    MqttAndroidClient mqttAndroidClient;
    String clientId = "client-mujahid";
    Request requestToDisp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //HIDING THE STATUS BAR
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_access_token));

        rightNow = Calendar.getInstance();
        startDateData = (Calendar) rightNow.clone();
        endDateData = (Calendar) rightNow.clone();


        beginningTime = (Calendar) rightNow.clone();
        beginningTime.add(Calendar.DATE, -365);

        setContentView(R.layout.activity_main);



        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {


               if(RequestList.size()!=0){
                   final Request item = RequestList.get(counting);

                SuggestedRouteFetcher.getRoute(item, new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(JSONObject result) {
                        Gson g = new Gson();
                        counting++;

                        Route newRoute;

                        newRoute = g.fromJson(result.toString(), Route.class);

                        if (newRoute.getTripList().getTrip() != null) {
                            routeList.add(newRoute);
                          //  Toast.makeText(MainActivity.this, "Finally", Toast.LENGTH_SHORT).show();

                            Identifier.getBlindspots(item, newRoute);

                            RouteJSONSimplifier.addJouneyStops(newRoute);
                        }

                    }
                });


                handler.postDelayed(this, 5000);

            }
            }
        };

        handler.postDelayed(r, 5000);

//        final Request toDisplay = x.generateRequest();
//        RequestList.add(toDisplay);
//        SuggestedRouteFetcher.getRoute(toDisplay, new VolleyCallback() {
//            @Override
//            public void onSuccessResponse(JSONObject result) {
//                Gson g = new Gson();
//                Route newRoute;
//                newRoute = g.fromJson(result.toString(), Route.class);
//                if (newRoute.getTripList().getTrip() != null) {
//                    routeList.add(newRoute);
//                    Identifier.getBlindspots(toDisplay, newRoute);
//                    Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_LONG).show();
////                Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
//                    RouteJSONSimplifier.addJouneyStops(newRoute);
//                }
//
//            }
//        });


        final Handler y = new Handler();
        final Runnable s = new Runnable() {
            @Override
            public void run() {

                Identifier.getBottlenecks();
//
                y.postDelayed(this, 500);
            }
        };

        y.postDelayed(s, 500);

        final Handler l = new Handler();
        final Runnable u = new Runnable() {
            @Override
            public void run() {
               // Toast.makeText(MainActivity.this, bottleneck.toString(), Toast.LENGTH_SHORT).show();
                l.postDelayed(this, 5000);
            }
        };

        l.postDelayed(u, 5000);

        final Handler z = new Handler();
        final Runnable t = new Runnable() {
            @Override
            public void run() {
                counting=0;
                routeList.clear();
                bottleneck.clear();
                totalStops.clear();
                blindspot.clear();
                RequestList.clear();
                //Toast.makeText(MainActivity.this, "Everything cleared!" + blindspot.size() + " " + totalStops.size() + " " + bottleneck.size(), Toast.LENGTH_LONG).show();
                z.postDelayed(this, 60000);
            }
        };

        z.postDelayed(t, 60000);


        bottomNavigationView = findViewById(R.id.bottom_nav);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showFragment(new RouteFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.nav_map:
                        menuItem.setChecked(true);
                        showFragment(new MapFragment());
                        break;
                    case R.id.nav_route:
                        menuItem.setChecked(true);
                        showFragment(new RouteFragment());
                        break;
                    case R.id.nav_notes:
                        menuItem.setChecked(true);
                        showFragment(new NoteFragment());
                        break;

                }
                return false;
            }
        });

        int day = rightNow.get(Calendar.DAY_OF_MONTH);
//        Toast.makeText(this, String.valueOf(day), Toast.LENGTH_SHORT).show();

        //Set Up an options list for the time frame
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "I got clicked", Toast.LENGTH_SHORT).show();
                inflateDatePicker();
//                JSONObject container = new JSONObject();
//                JSONObject request = new JSONObject();
//                try{
//                    request.put("LOGIN", getString(R.string.swedishtransport_api_key));
//                    container.put("REQUEST", request);
//
//                }catch (JSONException e){
//
//                }
//                Toast.makeText(MainActivity.this, container.toString(), Toast.LENGTH_LONG).show();
            }
        });

        mqttAndroidClient = new MqttAndroidClient(this, serverUri, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

                if (reconnect) {
                    Toast.makeText(MainActivity.this, "Reconnected to : " + serverURI, Toast.LENGTH_SHORT).show();
                    // Because Clean Session is true, we need to re-subscribe
                    subscribeToTopic();
                } else {
                    Toast.makeText(MainActivity.this, "Connected to: " + serverURI, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                Toast.makeText(MainActivity.this, "The Connection was lost.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

                JSONObject requestComplete = new JSONObject(new String(message.getPayload()));
                JSONObject originJson = (JSONObject)requestComplete.get("origin");
                JSONObject destinationJson = (JSONObject)requestComplete.get("destination");


                    int deviceId =Integer.parseInt( requestComplete.getString("deviceId"));
                    int requestId = Integer.parseInt(requestComplete.getString("requestId"));
                    LatLng origin = new LatLng(originJson.getDouble("latitude"),originJson.getDouble("longitude"));
                    LatLng destination = new LatLng(destinationJson.getDouble("latitude"),destinationJson.getDouble("longitude"));
                    String timeOfDeparture = requestComplete.getString("timeOfDeparture");
                    String purpose = requestComplete.getString("purpose");
                    String issuance =requestComplete.getString("issuance");
                    final Request newRequestG = new Request(deviceId,requestId,origin,destination,timeOfDeparture,purpose,issuance);
                    RequestList.add(newRequestG);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);

        try {
            //addToHistory("Connecting to " + serverUri);
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MainActivity.this, "Failed to connect to: " + serverUri, Toast.LENGTH_SHORT).show();
                }
            });


        } catch (MqttException ex) {
            ex.printStackTrace();
        }

    }



    public void subscribeToTopic() {
        try {
            mqttAndroidClient.subscribe(subscriptionTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                  Toast.makeText(MainActivity.this, "Subscribed! ", Toast.LENGTH_SHORT).show();



                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MainActivity.this, "Failed to subscribe", Toast.LENGTH_SHORT).show();
                }




            });







        } catch (MqttException ex) {
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.time_picker:
                inflateTimePicker();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
    }

    public void inflateDatePicker() {


        final AlertDialog.Builder datePickerDialog = new AlertDialog.Builder(this);
        LinearLayout datePickerLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_datepicker, null);
        final DateRangeCalendarView calendarView = datePickerLayout.findViewById(R.id.datePickerCal);
        calendarView.setVisibleMonthRange(beginningTime, rightNow);
        calendarView.setCurrentMonth(rightNow);

        Calendar startSelectionDate = Calendar.getInstance();
        startSelectionDate.add(Calendar.MONTH, 0);
        Calendar endSelectionDate = (Calendar) startSelectionDate.clone();
        endSelectionDate.add(Calendar.DATE, 0);

        calendarView.setSelectedDateRange(startSelectionDate, endSelectionDate);
        datePickerDialog.setView(datePickerLayout);
        datePickerDialog.setTitle("Select date range");
        datePickerDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startDateData = calendarView.getStartDate();
                endDateData = calendarView.getEndDate();
                Toast.makeText(MainActivity.this, "Data has been set\n" + "Start " + startDateData.getTime().toString() + "\nEnd " + endDateData.getTime().toString(), Toast.LENGTH_LONG).show();
            }
        });
        datePickerDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        datePickerDialog.show();

    }
    public void inflateTimePicker(){
//        final int hour = c.get(Calendar.HOUR_OF_DAY);
//        final int minute=c.get(Calendar.MINUTE);
//
//        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker timePicker, int i, int i1) {
//                String amPm;
//                if(i==0){
//                    i+=12;
//                    amPm="AM";
//                }
//                else if(i==12){
//                    amPm="PM";
//                }else if(i>12){
//                    i-=12;
//                    amPm="PM";
//                }else{
//                    amPm="AM";
//                }
//
//                String timeFormatted = String.format("%02d:%02d",i,i1)+ amPm;
//                Toast.makeText(MainActivity.this, timeFormatted, Toast.LENGTH_LONG).show();
//            }
//        },hour,minute,false).show();

        final LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_timepicker, null);
        start = layout.findViewById(R.id.startTime);
        end = layout.findViewById(R.id.endTime);
        start.setHour(startDateData.get(Calendar.HOUR_OF_DAY));
        start.setMinute(startDateData.get(Calendar.MINUTE));
        end.setHour(endDateData.get(Calendar.HOUR_OF_DAY));
        end.setMinute(endDateData.get(Calendar.MINUTE));
        purposeSpinner = layout.findViewById(R.id.purposeSpinner);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,purpose);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        purposeSpinner.setAdapter(aa);
        if(selectedPurpose.equalsIgnoreCase("work")){
            purposeSpinner.setSelection(0);
        }else if(selectedPurpose.equalsIgnoreCase("school")){
            purposeSpinner.setSelection(1);
        }else{
            purposeSpinner.setSelection(2);
        }
        start.setIs24HourView(true);
        end.setIs24HourView(true);
        final AlertDialog.Builder dialog = new AlertDialog.Builder( this );
        dialog.setView(layout);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(MainActivity.this, String.valueOf(start.getHour()), Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, end., Toast.LENGTH_SHORT).show();
                startDateData.set(Calendar.HOUR_OF_DAY, start.getHour());
                startDateData.set(Calendar.MINUTE, start.getMinute());
                endDateData.set(Calendar.HOUR_OF_DAY, end.getHour());
                endDateData.set(Calendar.MINUTE, end.getMinute());
                selectedPurpose = purposeSpinner.getSelectedItem().toString();
//                Toast.makeText(MainActivity.this, selectedPurpose, Toast.LENGTH_SHORT).show();
                publishDateData(startDateData,endDateData, selectedPurpose);

            }
        });
        dialog.show();


    }

    public void publishDateData(Calendar startTime, Calendar endTime, String purpose){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String toPublish = "{\"startDate\":\""+df.format(startTime.getTime())+"\",\"endDate\":\""+df.format(endTime.getTime())+"\"}";
        Toast.makeText(this, "Start Time: "+df.format(startTime.getTime())+"\nEnd Time: "+df.format(endTime.getTime())+"\nPurpose: "+selectedPurpose, Toast.LENGTH_SHORT).show();
        MqttMessage finalPublish = new MqttMessage(toPublish.getBytes());
        try {
            mqttAndroidClient.publish("date_filter_param", finalPublish);
            mqttAndroidClient.publish("purpose_param", new MqttMessage(purpose.toLowerCase().getBytes()));
            counting=0;
            routeList.clear();
            bottleneck.clear();
            totalStops.clear();
            blindspot.clear();
            RequestList.clear();
        }catch (MqttException e){

        }
    }



}

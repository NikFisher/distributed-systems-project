package com.example.miniprojectdit355.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniprojectdit355.MainActivity;
import com.example.miniprojectdit355.R;
import com.example.miniprojectdit355.VolleyCallback;
import com.example.miniprojectdit355.helpers.DataGenerator;
import com.example.miniprojectdit355.helpers.Identifier;
import com.example.miniprojectdit355.helpers.RouteJSONSimplifier;
import com.example.miniprojectdit355.helpers.SuggestedRouteFetcher;
import com.example.miniprojectdit355.models.Note;
import com.example.miniprojectdit355.models.Request;
import com.example.miniprojectdit355.models.Route;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.GeoJson;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.tapadoo.alerter.Alerter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {
    MapView mapView;
    FloatingActionButton layerFab;
    String mapStyle;
    private MapboxMap mapboxMap;
    public static final String ICON_ID = "location";
    public static List<Point> points;
    public static int count = 0;
    public static int bottleneckCount =0;
    public static int countValueRoute=0;
    public static ArrayList<Layer> layers = new ArrayList<>();
    static Bundle savedState = new Bundle();
    int countValue = 0;
    int bottleneckValue =0;
    String color ="#1e88e5";
    boolean stopBottleneckDisplay = false;
    boolean stopBlindspotsDisplay = false;
    int counter = 0 ;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
        savedState.putInt("Count Value",count);
        savedState.putInt("Bottleneck Value",bottleneckCount);
        savedState.putInt("Count Value Route",countValueRoute);
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Handler y = new Handler();
        final Runnable s = new Runnable() {
            @Override
            public void run() {

                Toast.makeText(getContext(), "Bottlenecks:"+MainActivity.bottleneck.size() + "\nBlindspots:" + MainActivity.blindspot.size() , Toast.LENGTH_SHORT).show();
//
                y.postDelayed(this, 5000);
            }
        };

        y.postDelayed(s, 5000);
        
        
        if(savedState!=null){
            countValue = savedState.getInt("Count Value");
            bottleneckValue=savedState.getInt("Bottleneck Value");
            countValueRoute =savedState.getInt("Count Value Route");

//                Toast.makeText(getContext(),"Count Value: "+countValue + " " + "Bottleneck Value: " + bottleneckValue, Toast.LENGTH_LONG).show();
             }

        else{

        }

//        Toast.makeText(getContext(),String.valueOf(MainActivity.blindspot.size()), Toast.LENGTH_SHORT).show();

        setHasOptionsMenu(true);
        final View root = inflater.inflate(R.layout.fragment_map, container, false);



        layerFab = root.findViewById(R.id.fabMaps);
        layerFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflateLayerDialogue();
            }
        });
        mapView = root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                MapFragment.this.mapboxMap = mapboxMap;
                mapboxMap.addOnMapLongClickListener(new MapboxMap.OnMapLongClickListener() {
                    @Override
                    public boolean onMapLongClick(@NonNull final LatLng point) {
                     createNotePopup(point);


                        return false;
                    }
                });

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded()
                {
                    @Override
                    public void onStyleLoaded(@NonNull final Style style) {
                        mapStyle = Style.MAPBOX_STREETS;
                        MainActivity.RequestList.clear();
//                        Toast.makeText(getContext(),"LayerId to begin from is: "+countValueRoute, Toast.LENGTH_LONG).show();

                            final Handler handler = new Handler();

                            final Runnable r = new Runnable() {
                                public void run() {
                                    countValue = 0;
                                    Random rand = new Random();
                                    while ( countValue<MainActivity.RequestList.size()&& !MainActivity.RequestList.isEmpty()){
                                    LatLng origin = MainActivity.RequestList.get(countValue).origin;
                                    LatLng destination = MainActivity.RequestList.get(countValue).destination;
                                    initArray(origin,destination);


                                    if(style.isFullyLoaded()){
                                        routeDisplay(style,String.valueOf(countValueRoute),String.valueOf(countValueRoute));

                                    }
                                    countValue++;
                                    countValueRoute++;
                                    }
                                    handler.postDelayed(this, 1000);
                                }
                            };

                            handler.postDelayed(r, 1000);





                    }
                });



            }
        });


        return root;
    }

    public void inflateLayerDialogue() {
        final AlertDialog.Builder layerDial = new AlertDialog.Builder(getContext());
        final LinearLayout layerLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_maplayer, null);
        final RadioGroup styleGroup = layerLayout.findViewById(R.id.style);



        layerDial.setView(layerLayout);
        layerDial.setTitle("SELECT ONE: ");
        layerDial.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, final int which) {
                int checkedButtonId = styleGroup.getCheckedRadioButtonId();
                final RadioButton checkedButton = layerLayout.findViewById(checkedButtonId);
                if (checkedButton.getText().equals("Show Blindspots")){


                    mapStyle = Style.MAPBOX_STREETS;
                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull final MapboxMap mapboxMap) {

                            mapboxMap.setStyle(mapStyle, new Style.OnStyleLoaded() {
                                @Override
                                public void onStyleLoaded(@NonNull final Style style) {
                                    if(style.isFullyLoaded()) {

                                                 final Handler handler = new Handler();
                                                 final Runnable runnable = new Runnable() {
                                                     @Override
                                                     public void run() {


                                                         int index = 0;
                                                         while (MainActivity.blindspot.size() != 0 &&index<MainActivity.blindspot.size()) {
                                                             if (index >= MainActivity.blindspot.size()) {
                                                                 MainActivity.blindspot.clear();
                                                                 // Toast.makeText(getContext(), "Blindspots List is now emptied!", Toast.LENGTH_SHORT).show();
                                                             }
                                                             if(style.isFullyLoaded()){
                                                             blindspotDisplay(style, MainActivity.blindspot.get(index), "#1e88e5");
                                                             }
                                                             index++;

                                                         }


                                                         handler.postDelayed(this, 1000);

                                                     }

                                                 };
                                                 handler.postDelayed(runnable, 1000);


                                    }

//                                    Toast.makeText(getContext(), "Display Blindspots!", Toast.LENGTH_SHORT).show();


                                }
                            });
                        }
                    });


                }
                else if (checkedButton.getText().equals("Show Bottlenecks")){
                    bottleneckValue = bottleneckCount;
                    countValue=count;

                    mapStyle = Style.MAPBOX_STREETS;
                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull MapboxMap mapboxMap) {

                            mapboxMap.setStyle(mapStyle, new Style.OnStyleLoaded() {
                                @Override
                                public void onStyleLoaded(@NonNull final Style style) {
                                  
                                    if(style.isFullyLoaded()) {


                                            final Handler handler = new Handler();
                                            final Runnable runnable = new Runnable() {
                                                @Override
                                                public void run() {



                                                        int index = 0;
                                                        while (MainActivity.bottleneck.size() != 0) {
                                                            if (index >= MainActivity.bottleneck.size()) {
                                                                MainActivity.bottleneck.clear();
                                                            }

                                                            if (index < MainActivity.bottleneck.size()) {
                                                                double Latitude = Double.valueOf(MainActivity.bottleneck.get(index).getLat());
                                                                double Longitude = Double.valueOf(MainActivity.bottleneck.get(index).getLon());

//                                                                if (Identifier.occurrences.get(MainActivity.bottleneck.get(index)) > 2000) {
                                                                    color = "#ff0000";
//                                                                } else {
//                                                                    color = "#1e88e5";
//                                                                }
                                                                if(style.isFullyLoaded()){
                                                                bottleneckDisplay(style, Latitude, Longitude, color);}
                                                                index++;
                                                            }



                                                        }


                                                        handler.postDelayed(this, 2000);

                                                }


                                            };
                                            handler.postDelayed(runnable, 2000);



                                    }
                                    }
                            });
                        }
                    });


                }


                else
                {mapStyle = Style.MAPBOX_STREETS;
                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull MapboxMap mapboxMap) {

                            mapboxMap.setStyle(mapStyle, new Style.OnStyleLoaded() {
                                @Override
                                public void onStyleLoaded(@NonNull final Style style) {

                                    if(style.isFullyLoaded()) {
//                                        Toast.makeText(getContext(),"LayerId to begin from now is: "+countValueRoute, Toast.LENGTH_LONG).show();
                                        MainActivity.RequestList.clear();


                                            final Handler handler = new Handler();

                                            final Runnable r = new Runnable() {
                                                public void run() {
                                                    countValue = 0 ;
                                                    while ( countValue<MainActivity.RequestList.size()&& !MainActivity.RequestList.isEmpty()){
                                                        LatLng origin = MainActivity.RequestList.get(countValue).origin;
                                                        LatLng destination = MainActivity.RequestList.get(countValue).destination;
                                                        initArray(origin,destination);


                                                        if(style.isFullyLoaded()){
                                                            try{
                                                                routeDisplay(style,String.valueOf(countValueRoute),String.valueOf(countValueRoute));
                                                            }
                                                            catch (Exception e){
                                                                e.printStackTrace();
                                                            }

                                                        }
                                                        countValue++;
                                                        countValueRoute++;
                                                    }
                                                    handler.postDelayed(this, 1000);
                                                }
                                            };

                                            handler.postDelayed(r, 1000);


                                        }






//                                    }
                                }
                            });
                        }
                    });


                }



            }
        });
        layerDial.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        layerDial.show();
    }


    public void initArray(LatLng origin,LatLng destination){
        points = new ArrayList<>();
        points.add(Point.fromLngLat(origin.getLongitude(), origin.getLatitude()));
        points.add(Point.fromLngLat(destination.getLongitude(),destination.getLatitude()));
    }

    public void routeDisplay(Style style,String sourceId,String lineId){
        style.addSource(
                new GeoJsonSource(sourceId,
                        FeatureCollection.fromFeatures(new Feature[] {Feature.fromGeometry(
                                LineString.fromLngLats(points)
                        )}))


        );

        style.addLayer(new LineLayer(lineId, sourceId).withProperties(
                PropertyFactory.lineCap(Property.LINE_CAP_ROUND),
                PropertyFactory.lineJoin(Property.LINE_JOIN_ROUND),
                PropertyFactory.lineWidth(5f),
                PropertyFactory.lineColor(Color.parseColor("#e55e5e"))
        ));

    }

    public void bottleneckDisplay(Style style,double Latitude,double Longitude,String color){

        style.addImage(ICON_ID,
                                BitmapUtils.getBitmapFromDrawable( getResources().getDrawable(R.drawable.bottleneck_circle)),
                                true);

                        SymbolManager symbolManager = new SymbolManager(mapView, mapboxMap, style);
                        symbolManager.setIconAllowOverlap(true);
                        symbolManager.setIconIgnorePlacement(true);

                        symbolManager.create(new SymbolOptions()
                                .withLatLng(new LatLng(Latitude, Longitude))
                                .withIconImage(ICON_ID)
                               .withIconColor(PropertyFactory.iconColor(color).getValue())
                                .withIconOpacity(0.5f)
                               //.withIconSize(2.0f)
                                 );
    }

    public void blindspotDisplay(Style style,LatLng point,String color){

        style.addImage(ICON_ID,
                BitmapUtils.getBitmapFromDrawable( getResources().getDrawable(R.drawable.blindspot_icon)),
                true);

        SymbolManager symbolManager = new SymbolManager(mapView, mapboxMap, style);
        symbolManager.setIconAllowOverlap(true);
        symbolManager.setIconIgnorePlacement(true);
        double latitude = point.getLatitude();
        double longitude = point.getLongitude();
        symbolManager.create(new SymbolOptions()
                        .withLatLng(new LatLng(latitude, longitude))
                        .withIconImage(ICON_ID)
                        .withIconColor(PropertyFactory.iconColor(color).getValue())
                        //.withIconOpacity(0.5f)
                 .withIconSize(0.05f)
        );
    }

    public void createNotePopup(final LatLng point){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View noteDialog = getLayoutInflater().inflate(R.layout.dialog_note, null);
        builder.setView(noteDialog);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        // Toast.makeText(getContext(), MainActivity.routeList.get(1).getTripList().getTrip().get(1).getLeg().get(1).getDestination().getName(), Toast.LENGTH_SHORT).show();
        // Toast.makeText(getContext(), DataGenerator.truncateDecimal(point.getLatitude(),6) +","+DataGenerator.truncateDecimal(point.getLongitude(),6) , Toast.LENGTH_SHORT).show();
        final EditText title = noteDialog.findViewById(R.id.note_title);
        final EditText description = noteDialog.findViewById(R.id.note_description);
        final Spinner prioritySpinner = noteDialog.findViewById(R.id.priority_spinner);
        final Spinner typeSpinner = noteDialog.findViewById(R.id.type_spinner);
        final TextView latitudeText = noteDialog.findViewById(R.id.latitude_val);
        final TextView longitudeText = noteDialog.findViewById(R.id.longitude_val);
        latitudeText.setText(String.valueOf(DataGenerator.truncateDecimal(point.getLatitude(),3)));
        longitudeText.setText(String.valueOf(DataGenerator.truncateDecimal(point.getLongitude(),3)));
        String priorityOptions[] ={"High","Moderate","Low"};
        String typeOptions[]={"Blindspot","Bottleneck"};
        ArrayAdapter<String> typeArrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,typeOptions);
        typeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeArrayAdapter);
        ArrayAdapter<String> PriorityArrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, priorityOptions);
        PriorityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(PriorityArrayAdapter);

        ImageButton closeButton = noteDialog.findViewById(R.id.dialog_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        Button addButton  = noteDialog.findViewById(R.id.add_note_btn);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().toString().equals("")|| description.getText().toString().equals("")){
                    Alerter.create(getActivity()).setText("Please fill in the details").setTitle("Missing Field(s)!").setBackgroundColorRes(R.color.colorPrimary).show();
                }else{
                    MainActivity.mList.add(new Note(title.getText().toString(),description.getText().toString(),point,typeSpinner.getSelectedItem().toString(),prioritySpinner.getSelectedItem().toString(),false));
                    alertDialog.dismiss();}
            }
        });

    }

}



package com.example.miniprojectdit355.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.miniprojectdit355.MainActivity;
import com.example.miniprojectdit355.R;
import com.example.miniprojectdit355.adapters.RouteAdapter;
import com.example.miniprojectdit355.models.Request;
import com.example.miniprojectdit355.models.Route;
import com.example.miniprojectdit355.models.Stop;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RouteFragment extends Fragment {
    RecyclerView routeView;
    public static LinearLayout noteOptions;
    Button deleteButton, SelectAllButton, UnSelectAllButton;
    List<Request> requestsList = new ArrayList<>();


    public RouteFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route, container, false);
        routeView = view.findViewById(R.id.routeList);

        noteOptions = view.findViewById(R.id.note_options);
        deleteButton = view.findViewById(R.id.btn_delete);
        SelectAllButton = view.findViewById(R.id.btn_select_all);
        UnSelectAllButton = view.findViewById(R.id.btn_unselect_all);

        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                requestsList= new ArrayList<>();
                requestsList=MainActivity.RequestList;
                populateList();
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);


        /*SelectAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "SELECT ALL", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < mList1.size(); i++) {
                    mList1.get(i).setSelect(true);
                }
                populateList();

            }
        });*/

        SelectAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "SELECT ALL", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < requestsList.size(); i++) {
//                    routeList.get(i).setSelect(true);
                }
                populateList();

            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "Delete", Toast.LENGTH_SHORT).show();
                /*for(int i=0;i<mList.size();i++){
                    if(mList.get(i).isSelect()){
                        mList.remove(i);
                    }
                }*/

                Iterator<Request> iter = requestsList.iterator();
                while (iter.hasNext()) {
                    Request item = iter.next();
                    /*if (item.isSelect()) {
                        iter.remove();
                    }*/
                }

                noteOptions.setVisibility(View.GONE);

                populateList();

            }
        });
        UnSelectAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "UNSELECT ALL", Toast.LENGTH_SHORT).show();
                /*for (int i = 0; i < routeList.size(); i++) {
                    routeList.get(i).setSelect(false);

                }*/

                noteOptions.setVisibility(View.GONE);

                populateList();


            }
        });

        populateList();





        return view;

    }

    public void populateList() {
        RouteAdapter routeAdapter = new RouteAdapter(requestsList, getContext());
        routeView.setAdapter(routeAdapter);
        routeAdapter.notifyDataSetChanged();
    }

}

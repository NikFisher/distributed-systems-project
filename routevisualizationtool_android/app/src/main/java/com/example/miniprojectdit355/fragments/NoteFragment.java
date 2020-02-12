package com.example.miniprojectdit355.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.miniprojectdit355.MainActivity;
import com.example.miniprojectdit355.R;
import com.example.miniprojectdit355.adapters.NoteAdapter;
import com.example.miniprojectdit355.models.Note;
import com.example.miniprojectdit355.models.Vehicle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment {
    public static LinearLayout noteOptions;
    LinearLayout emptyMessageContainer;
    public static final String ICON_ID = "location";
    NoteAdapter noteAdapter;
    Spinner proritySpinner;

    int[] number = {1, 2, 3, 4};
    int[] color = {R.color.colorAccent, R.color.colorPrimary, R.color.colorBrown, R.color.colorPurple};
    List<Vehicle> vehicles = new ArrayList<>();
    Button deleteButton, SelectAllButton, UnSelectAllButton;
    FloatingActionButton testbutton;
    List<Note> mList = MainActivity.mList;
    String[] priorityArray = new String[] {
            "High","Moderate","Low"
    };


    RecyclerView recyclerView;

    public NoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        emptyMessageContainer= view.findViewById(R.id.empty_message);
        if(mList.size()<=0){
            emptyMessageContainer.setVisibility(View.VISIBLE);
        }else {
            emptyMessageContainer.setVisibility(View.GONE);
        }

















        recyclerView = view.findViewById(R.id.recycler_view);
        noteOptions = view.findViewById(R.id.note_options);
        deleteButton = view.findViewById(R.id.btn_delete);
        SelectAllButton = view.findViewById(R.id.btn_select_all);
        UnSelectAllButton = view.findViewById(R.id.btn_unselect_all);




        SelectAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "SELECT ALL", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < mList.size(); i++) {
                    mList.get(i).setSelect(true);
                }
                populateList();

            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Delete", Toast.LENGTH_SHORT).show();
                /*for(int i=0;i<mList.size();i++){
                    if(mList.get(i).isSelect()){
                        mList.remove(i);
                    }
                }*/

                Iterator<Note> iter = mList.iterator();
                while (iter.hasNext()) {
                    Note item = iter.next();
                    if (item.isSelect()) {
                        iter.remove();
                    }
                }

                noteOptions.setVisibility(View.GONE);

                populateList();

            }
        });
        UnSelectAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "UNSELECT ALL", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < mList.size(); i++) {
                    mList.get(i).setSelect(false);

                }

                noteOptions.setVisibility(View.GONE);

                populateList();


            }
        });

        populateList();


        return view;

    }

    public void populateList() {
        NoteAdapter noteAdapter = new NoteAdapter(this.mList, getContext());
        recyclerView.setAdapter(noteAdapter);
        if (mList.size()<=0){
            emptyMessageContainer.setVisibility(View.VISIBLE);
        }else{
            emptyMessageContainer.setVisibility(View.GONE);
        }
    }

}

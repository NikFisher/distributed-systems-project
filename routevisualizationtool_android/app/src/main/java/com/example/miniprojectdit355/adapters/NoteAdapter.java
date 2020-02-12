package com.example.miniprojectdit355.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniprojectdit355.MainActivity;
import com.example.miniprojectdit355.R;
import com.example.miniprojectdit355.fragments.MapFragment;
import com.example.miniprojectdit355.fragments.NoteFragment;
import com.example.miniprojectdit355.models.Note;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {
    Context mContext;
    List<Note> mList;
    MapView mapView;
    String mapStyle;
    MapboxMap mapboxMap;
    public NoteAdapter(List<Note> mList,Context mContext){
        this.mList=mList;
        this.mContext=mContext;
    }
    @NonNull
    @Override
    public NoteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.note_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteAdapter.MyViewHolder holder, final int position) {
        final Note item = mList.get(position);
        holder.titleBox.setText(item.getTitle());
        holder.descriptionBox.setText(item.getDescription());
        holder.checkBox.setChecked(item.isSelect());
        holder.noteType.setText(item.getType());
        switch (item.getType()){
            case "Blindspot":
                holder.typeIcon.setImageResource(R.drawable.blindspot_icon);
                break;
            case "Bottleneck":
                holder.typeIcon.setImageResource(R.drawable.bottleneck_circle);
                break;
        }
        final String color = item.getPriority();
        switch (color){
            case "High":
                holder.noteColor.setBackground(mContext.getDrawable(R.drawable.danger_note));
                break;
            case "Moderate":
                holder.noteColor.setBackground(mContext.getDrawable(R.drawable.warning_note));
                break;
            case "Low":
                holder.noteColor.setBackground(mContext.getDrawable(R.drawable.primary_note));
                break;
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(holder.checkBox.isChecked()){
                    NoteFragment.noteOptions.setVisibility(View.VISIBLE);
                    item.setSelect(true);

                }else{
                 item.setSelect(false);
                }
                int flag = 0;
                for(int i =0;i<mList.size();i++){
                    if(mList.get(i).isSelect()){
                        flag=1;
                    }
                }
                if(flag==0){
                    NoteFragment.noteOptions.setVisibility(View.GONE);
                }

            }
        });
        holder.locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                final View popupLocation = LayoutInflater.from(mContext).inflate(R.layout.popup_location_note, null);
                mapView = popupLocation.findViewById(R.id.mapViewLocation);
                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                       // this.mapboxMap = mapboxMap;


                        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded()
                        {
                            @Override
                            public void onStyleLoaded(@NonNull final Style style) {
                                mapStyle = Style.MAPBOX_STREETS;

                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(new LatLng(item.getCoordinates().getLatitude(), item.getCoordinates().getLongitude()))
                                        .zoom(12)
                                        .tilt(20)
                                        .build();
                                mapboxMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(item.getCoordinates().getLatitude(), item.getCoordinates().getLongitude()))
                                        .title(item.getTitle()));
                               mapboxMap.setCameraPosition(cameraPosition);



                            }
                        });



                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setView(popupLocation);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                ImageButton closeBtn = popupLocation.findViewById(R.id.location_close_btn);
               closeBtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       alertDialog.dismiss();
                   }
               });








                Toast.makeText(mContext, "Latitude: "+String.valueOf(item.getCoordinates().getLatitude()) + "Longitude: "+ String.valueOf(item.getCoordinates().getLongitude()), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    protected class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleBox;
        TextView descriptionBox;
        TextView noteType;
        RelativeLayout noteColor;
        CheckBox checkBox;
        ImageButton locationBtn;
        ImageView typeIcon;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleBox= itemView.findViewById(R.id.title_note);
            descriptionBox=itemView.findViewById(R.id.descrip_note);
            noteColor= itemView.findViewById(R.id.note_color);
            checkBox = itemView.findViewById(R.id.checkbox);
            locationBtn=itemView.findViewById(R.id.location_btn);
            noteType= itemView.findViewById(R.id.note_type);
            typeIcon = itemView.findViewById(R.id.note_type_icon);
        }
    }

}

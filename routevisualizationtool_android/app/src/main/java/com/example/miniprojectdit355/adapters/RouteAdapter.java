package com.example.miniprojectdit355.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.miniprojectdit355.R;
import com.example.miniprojectdit355.fragments.RouteFragment;
import com.example.miniprojectdit355.models.Request;
import com.example.miniprojectdit355.models.Route;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.MyViewHolder> {
    List<Request> mList;
    Context mContext;

    public RouteAdapter(List<Request> list, Context context ){
        this.mList = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RouteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_item,parent,false);
       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RouteAdapter.MyViewHolder holder, int position) {

        Request item = mList.get(position);
        holder.start.setText(item.origin.getLatitude() +" , " + item.origin.getLongitude());
        holder.end.setText(item.destination.getLatitude() +" , " + item.destination.getLongitude());
        holder.DateTime.setText(item.getTimeOfDeparture());

        /*final Route item = mList.get(position);
        holder.DateTime.setText(item.getRequestDate() + " " + item.getRequestTime());
        holder.start.setText(item.getStart().getLatitude() + "," + item.getStart().getLongitude());
        holder.end.setText(item.getEnd().getLatitude() + "," + item.getEnd().getLongitude());
        holder.startStop.setText(item.getStartStop().getName());
        holder.endStop.setText(item.getEndStop().getName());
        holder.checkBox.setChecked(item.isSelect());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(holder.checkBox.isChecked()){
                    RouteFragment.noteOptions.setVisibility(View.VISIBLE);
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
                    RouteFragment.noteOptions.setVisibility(View.GONE);
                }

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView DateTime, start, end, startStop, endStop;
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            DateTime=itemView.findViewById(R.id.routeTime);
            start = itemView.findViewById(R.id.startCoord);
            end = itemView.findViewById(R.id.endCoord);
            startStop = itemView.findViewById(R.id.startStop);
            endStop = itemView.findViewById(R.id.endStop);
            checkBox=itemView.findViewById(R.id.checkbox);
        }
    }
}

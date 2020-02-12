package com.example.miniprojectdit355.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniprojectdit355.R;
import com.example.miniprojectdit355.models.Note;
import com.example.miniprojectdit355.models.Vehicle;

import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.MyViewHolder> {

    Context mContext;
    List<Vehicle> mList;
    public VehicleAdapter(List<Vehicle> mList, Context mContext){
        this.mList=mList;
        this.mContext=mContext;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.tram_number_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Vehicle item = mList.get(position);
        holder.design.setText(String.valueOf(item.getNumber()));
        holder.design.setBackground(mContext.getDrawable(item.getNumber()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button design;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            design = itemView.findViewById(R.id.tram_number);
        }
    }
}

package com.henceforth.rhino.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.henceforth.rhino.R;
import com.henceforth.rhino.utills.AddVehicles;
import com.henceforth.rhino.webServices.pojo.AddedVehicle;
import com.henceforth.rhino.webServices.pojo.VehicleListing;

import java.util.ArrayList;

/**
 * Created by HOME on 5/6/2017.
 */

public class NumberPlateAdapter extends RecyclerView.Adapter<NumberPlateAdapter.MyViewHolder> {
    private ArrayList<VehicleListing> vehiclesInfoList;
    private Context mContext;

    public NumberPlateAdapter(Context mContext, ArrayList<VehicleListing> vehiclesInfoList) {
        this.vehiclesInfoList = vehiclesInfoList;
        this.mContext = mContext;
    }

    @Override
    public NumberPlateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_no_plate, parent, false);
        return new NumberPlateAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NumberPlateAdapter.MyViewHolder holder, int position) {
        final VehicleListing item = vehiclesInfoList.get(position);
        holder.tvPlateNo.setText(item.getLicense_plate_no());
         }

    @Override
    public int getItemCount() {
        return vehiclesInfoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlateNo;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvPlateNo = (TextView) itemView.findViewById(R.id.tvPlateNo);

        }
    }
}
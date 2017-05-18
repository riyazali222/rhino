package com.henceforth.rhino.adapters;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.henceforth.rhino.R;
import com.henceforth.rhino.fragments.RaiseRequestFragment;
import com.henceforth.rhino.utills.ApplicationGlobal;
import com.henceforth.rhino.webServices.Services;

import java.util.ArrayList;
import java.util.List;



public class ServiceTypeAdapter extends RecyclerView.Adapter<ServiceTypeAdapter.MyViewHolder> {

    private List<Services> servicesList = new ArrayList<>();
    private Context mContext;
    private List<Services> listToDisplay;


    public ServiceTypeAdapter(Context mContext, List<Services> servicesList) {
        this.servicesList = servicesList;
        this.mContext = mContext;
    }

    @Override
    public ServiceTypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service_type, parent, false);
        return new ServiceTypeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ServiceTypeAdapter.MyViewHolder holder, int position) {
        final Services item = servicesList.get(position);
        holder.tvServiceType.setText(item.getDesc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sending Broadcast
                ApplicationGlobal.prefsManager.setServiceTypeCode(item.getService_id());
                ApplicationGlobal.prefsManager.setServiceTypeName(item.getDesc());
                Intent intent = new Intent("send_data");

                intent.putExtra("ServiceList", item.getDesc());
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

                LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent("delete"));
            }

        });

    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvServiceType;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvServiceType = (TextView) itemView.findViewById(R.id.tvServiceType);
        }
    }
}

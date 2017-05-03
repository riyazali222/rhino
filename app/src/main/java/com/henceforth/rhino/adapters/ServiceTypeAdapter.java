package com.henceforth.rhino.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.henceforth.rhino.R;
import com.henceforth.rhino.webServices.Services;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HOME on 5/3/2017.
 */

public class ServiceTypeAdapter extends RecyclerView.Adapter<ServiceTypeAdapter.MyViewHolder> {

    private List<Services> servicesList = new ArrayList<>();
    private Context mContext;


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
    public void onBindViewHolder(ServiceTypeAdapter.MyViewHolder holder, int position) {
        final Services item = servicesList.get(position);
        holder.tvServiceType.setText(item.getService());
    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvServiceType;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvServiceType=(TextView)itemView.findViewById(R.id.tvServiceType);
        }
    }
}

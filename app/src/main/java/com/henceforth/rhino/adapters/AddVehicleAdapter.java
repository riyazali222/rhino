package com.henceforth.rhino.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.henceforth.rhino.R;
import com.henceforth.rhino.utills.VehiclesInfo;
import com.henceforth.rhino.webServices.pojo.NotificationsLists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HOME on 5/1/2017.
 */

public class AddVehicleAdapter extends RecyclerView.Adapter<AddVehicleAdapter.MyViewHolder>{
    List<VehiclesInfo> vehiclesInfoList = new ArrayList<>();
    Context mContext;

    public AddVehicleAdapter(Context mContext,  List<VehiclesInfo> vehiclesInfoList) {
        this.vehiclesInfoList = vehiclesInfoList;
        this.mContext = mContext;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_add_vehicles, parent, false);
        return new AddVehicleAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final VehiclesInfo item = vehiclesInfoList.get(position);
        holder.tvVehicleBrand.setText(item.getBrand());
        holder.tvRegistrationNo.setText(item.getLicenceNo());
        holder.tvVehicleName.setText(item.getVehicleType());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvVehicleName, tvRegistrationNo,tvVehicleBrand;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvVehicleBrand=(TextView) itemView.findViewById(R.id.tvVehicleBrand);
            tvRegistrationNo=(TextView) itemView.findViewById(R.id.tvRegistrationNo);
            tvVehicleName=(TextView) itemView.findViewById(R.id.tvVehicleName);
        }
    }
}

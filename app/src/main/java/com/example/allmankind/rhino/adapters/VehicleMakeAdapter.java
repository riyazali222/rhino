package com.example.allmankind.rhino.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.allmankind.rhino.R;
import com.example.allmankind.rhino.utills.ItemsList;

import java.util.List;


public class VehicleMakeAdapter extends RecyclerView.Adapter<VehicleMakeAdapter.VehicleMakeViewHolder> implements Filterable {


    private Context mContext;
    private List<ItemsList> itemList;
    private List<ItemsList> listToDisplay;

    public VehicleMakeAdapter(Context mContext, List<ItemsList> itemList) {
        this.mContext=mContext;
        this.itemList = itemList;
        this.listToDisplay = itemList;
    }

    @Override
    public VehicleMakeAdapter.VehicleMakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout_vehicle_make, parent, false);
        return new VehicleMakeAdapter.VehicleMakeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VehicleMakeAdapter.VehicleMakeViewHolder holder, final int position) {
        final ItemsList item = itemList.get(position);
        holder.tvAudi.setText(item.getname());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }


    public class VehicleMakeViewHolder extends RecyclerView.ViewHolder {
        TextView tvAudi;

        VehicleMakeViewHolder(View view) {
            super(view);
            tvAudi = (TextView) view.findViewById(R.id.tvAudi);

        }

    }


}






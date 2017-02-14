package com.example.allmankind.rhino.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.allmankind.rhino.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {


    private String[] items;
    private Context mContext;


    public HistoryAdapter(Context mContext, String[] items) {
        this.items = items;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout_history, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        /*holder.bind(items[position]);*/
        holder.tvNotification.setText(items[position]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate,tvNotification;


        MyViewHolder(View view) {
            super(view);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvNotification = (TextView) view.findViewById(R.id.tvNotification);
        }
       /* public void bind(String text) {
            tvLicence.setText(text);
        }*/

    }


}


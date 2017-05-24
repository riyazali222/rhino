package com.henceforth.rhino.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.henceforth.rhino.R;
import com.henceforth.rhino.webServices.pojo.NotificationsLists;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {


    private List<NotificationsLists> listNotification = new ArrayList<>();
    private Context mContext;


    public NotificationAdapter(Context mContext, List<NotificationsLists> listNotification) {
        this.listNotification = listNotification;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout_notificatio, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        /*holder.bind(items[position]);*/
        final NotificationsLists item = listNotification.get(position);
         holder.tvNotification.setText(item.getNotifMsg());
        holder.tvDate.setText(item.getCreatedAt());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listNotification.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvNotification;


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


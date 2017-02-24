package com.henceforth.rhino.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.henceforth.rhino.R;
import com.henceforth.rhino.webServices.pojo.YearLists;

import java.util.ArrayList;
import java.util.List;


public class YearAdapter extends RecyclerView.Adapter<YearAdapter.MyViewHolder> {

    private List<String> yearList = new ArrayList<String>();
    private Context mContext;

    public YearAdapter(Context mContext, List<String> yearList) {
        this.mContext = mContext;
        this.yearList = yearList;
    }

    @Override
    public YearAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.year_dialog_row_layout, parent, false);
        return new YearAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(YearAdapter.MyViewHolder holder, final int position) {
        holder.tvYear.setText(yearList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return yearList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvYear;

        MyViewHolder(View view) {
            super(view);
            tvYear = (TextView) view.findViewById(R.id.tvYear);

        }

    }

}

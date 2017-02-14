package com.example.allmankind.rhino.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allmankind.rhino.R;
import com.example.allmankind.rhino.adapters.HistoryAdapter;

public class HistoryFragment extends Fragment {
    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;
    String[] notificationsList = {"Rachel Platten's songs are beautiful and I find helpful meaning in them.Its all love and friendship that makes me want to sing and dance forever with my family and friends!",
            "Hands,put your empty hands in your mine And scars, show me all the scars you hide And hey, if your wings are broken Please take mine so your can open, too",
    "OMG WHO SAW THE ADORABLE LITTLE GIR JUMP INTO HER DADS HANDS?!?!"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        historyAdapter=new HistoryAdapter(getActivity(), notificationsList);
        recyclerView.setAdapter(historyAdapter);
        return view;

    }
}

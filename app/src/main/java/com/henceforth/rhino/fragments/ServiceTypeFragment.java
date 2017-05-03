package com.henceforth.rhino.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.app.DialogFragment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.henceforth.rhino.R;
import com.henceforth.rhino.adapters.ServiceTypeAdapter;
import com.henceforth.rhino.webServices.Services;

import java.util.ArrayList;
import java.util.List;

public class ServiceTypeFragment extends DialogFragment {
    Context mContext;
    Toolbar toolbar;
    private List<Services> servicesList = new ArrayList<>();
    RecyclerView recyclerView;
    ServiceTypeAdapter serviceTypeAdapter;


   @Override
   public void onStart() {
       super.onStart();
       Dialog dialog = getDialog();
       if (dialog != null) {
           dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
           dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       }
   }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        }
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_service_type, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar=(Toolbar)getView().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
      init();

    }

    private void init() {
        recyclerView=(RecyclerView)getView().findViewById(R.id.rvServiceType);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        serviceTypeAdapter=new ServiceTypeAdapter(getActivity(),servicesList);
        recyclerView.setAdapter(serviceTypeAdapter);
        addItems();

    }

    private void addItems() {
        Services s=new Services("Tow - Light Duty Standard");
        servicesList.add(s);

        s= new Services("Tow - Light Duty Flatbed");
        servicesList.add(s);
        s= new Services("Tow - Light Duty Dollies");
        servicesList.add(s);
        s= new Services("Tow - Medium Duty");
        servicesList.add(s);
        s= new Services("Tow - Heavy Duty");
        servicesList.add(s);
        s= new Services("Tow - Motorcycle (Flatbed)");
        servicesList.add(s);
        s= new Services("Jump Start");
        servicesList.add(s);
        s= new Services("Tire Change - Has Jack");
        servicesList.add(s);
        s= new Services("Tire Change - Has Spare");
        servicesList.add(s);
        s= new Services("Tire Change - Has Jack &amp; Spare");
        servicesList.add(s);
        s= new Services("Tire Change - No Jack/Spare");
        servicesList.add(s);
        s= new Services("Fuel Delivery - Unleaded");
        servicesList.add(s);
        s= new Services("Fuel Delivery - Diesel");
        servicesList.add(s);
        s= new Services("Winching");
        servicesList.add(s);
        s= new Services("Lock Out");
        servicesList.add(s);
        s= new Services("Bicycle");
        servicesList.add(s);
        s= new Services("Passenger Transport");
        servicesList.add(s);


        serviceTypeAdapter.notifyDataSetChanged();
    }



}

package com.henceforth.rhino.fragments;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.app.DialogFragment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.henceforth.rhino.R;
import com.henceforth.rhino.activities.VehicleMakeActivity;
import com.henceforth.rhino.adapters.ServiceTypeAdapter;
import com.henceforth.rhino.utills.CommonMethods;
import com.henceforth.rhino.webServices.Services;
import com.henceforth.rhino.webServices.apis.RestClient;
import com.henceforth.rhino.webServices.pojo.ItemsList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
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
        recyclerView = (RecyclerView) getView().findViewById(R.id.rvServiceType);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        serviceTypeAdapter = new ServiceTypeAdapter(getActivity(), servicesList);
        recyclerView.setAdapter(serviceTypeAdapter);
       // addItems();
        hitServiceApi();

    }

    private void hitServiceApi() {
        if(CommonMethods.isNetworkConnected(getActivity())) {

            RestClient.get().ServiceListResponse().enqueue(new Callback<List<Services>>() {
                @Override
                public void onResponse(Call<List<Services>> call, Response<List<Services>> response) {
                    try {
                        if (response.code() == 200 && response.body() != null) {
                            servicesList.clear();
                            servicesList.addAll(response.body());
//                            listToDisplay.clear();
//                            listToDisplay.addAll(itemsListArrayList);
                            serviceTypeAdapter.notifyDataSetChanged();
                        } else
                            CommonMethods.showErrorMessage(getActivity(),
                                    response.errorBody());
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<List<Services>> call, Throwable t) {

                }
            });
        }
        else {
            Toast.makeText(getActivity(),"Internet not connected",Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver,
                new IntentFilter("delete"));

    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onPause();
    }

    //receiving Broadcast
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getDialog().dismiss();
        }
    };

}

package com.henceforth.rhino.dialoges;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.DialogFragment;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.henceforth.rhino.R;
import com.henceforth.rhino.adapters.ServiceTypeAdapter;
import com.henceforth.rhino.utills.CommonMethods;
import com.henceforth.rhino.webServices.Services;
import com.henceforth.rhino.utills.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceTypeDialog extends DialogFragment {
    Context mContext;
    Toolbar toolbar;
    private List<Services> servicesList = new ArrayList<>();
    private List<Services> listToDisplay = new ArrayList<>();
    RecyclerView recyclerView;
    ServiceTypeAdapter serviceTypeAdapter;
    public EditText etSearchServices;


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                    .MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
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
        addTextListener();

    }

    private void init() {
        etSearchServices=(EditText)getView().findViewById(R.id.etSearchServices);
        recyclerView = (RecyclerView) getView().findViewById(R.id.rvServiceType);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        serviceTypeAdapter = new ServiceTypeAdapter(getActivity(), listToDisplay);
        recyclerView.setAdapter(serviceTypeAdapter);
       // addItems();
        hitServiceApi();

    }
    private void addTextListener() {

        etSearchServices.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listToDisplay.clear();
                for (Services items : servicesList) {
                    if (items.getDesc().toLowerCase().contains(etSearchServices.getText().toString()
                            .toLowerCase())) {
                        // Adding Matched items
                        listToDisplay.add(items);
                    }

                }

                serviceTypeAdapter.notifyDataSetChanged();

            }
        });
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
                            listToDisplay.clear();
                            listToDisplay.addAll(servicesList);
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

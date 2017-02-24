package com.henceforth.rhino.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.henceforth.rhino.R;
import com.henceforth.rhino.adapters.HistoryAdapter;
import com.henceforth.rhino.utills.ApplicationGlobal;
import com.henceforth.rhino.utills.CommonMethods;
import com.henceforth.rhino.webServices.apis.RestClient;
import com.henceforth.rhino.webServices.pojo.NotificationsLists;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {
    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;
    List<NotificationsLists> notificationsList=new ArrayList<>();
    TextView tvNoNoti;
    int notification_Id= 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        tvNoNoti=(TextView) view.findViewById(R.id.tvNoNoti);
        tvNoNoti.setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        historyAdapter=new HistoryAdapter(getActivity(), notificationsList);
        recyclerView.setAdapter(historyAdapter);
        //getFcmId(device_type,device_id,fcm_id);
        loadNotificationsList();
        return view;

    }



    private void loadNotificationsList() {
        if(CommonMethods.isNetworkConnected(getActivity())) {

            RestClient.get().NotificationListResponse().enqueue(new Callback<List<NotificationsLists>>() {
                @Override
                public void onResponse(Call<List<NotificationsLists>> call, Response<List<NotificationsLists>>
                                                                                      response) {
                    try {
                        if (response.code() == 200 && response.body() != null ) {

                            ApplicationGlobal.prefsManager.setNotificationId(notification_Id);
                            notificationsList.clear();
                            notificationsList.addAll(response.body());
                            historyAdapter.notifyDataSetChanged();
                        }
                        if(notificationsList.size()==0){
                            tvNoNoti.setVisibility(View.VISIBLE);
                        }

                        else {

                            CommonMethods.showErrorMessage(getActivity(),
                                    response.errorBody());
                        }
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<List<NotificationsLists>> call, Throwable t) {

                }
            });
        }
        else {
            Toast.makeText(getActivity(),"Internet not connected",Toast.LENGTH_SHORT).show();
        }

    }
}

package com.henceforth.rhino.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.henceforth.rhino.R;
import com.henceforth.rhino.adapters.NotificationAdapter;
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
    private RecyclerView recyclerView;
    private NotificationAdapter historyAdapter;
    private List<NotificationsLists> notificationsList = new ArrayList<>();
    private TextView tvNoNoti;
    private int notification_Id = 1;
    private SwipeRefreshLayout swipeRefresh;
    private boolean isRefreshing = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        swipeRefresh = (SwipeRefreshLayout) getView().findViewById(R.id.swipeRefresh);
        tvNoNoti = (TextView) getView().findViewById(R.id.tvNoNoti);
        tvNoNoti.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        historyAdapter = new NotificationAdapter(getActivity(), notificationsList);
        recyclerView.setAdapter(historyAdapter);
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(getActivity(),
                R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isRefreshing) {
                    if (CommonMethods.isNetworkConnected(getActivity())) {
                        isRefreshing = true;
                        loadNotificationsList();
                    } else {
                        isRefreshing = false;
                        swipeRefresh.setRefreshing(false);
                        CommonMethods.showInternetNotConnectedToast(getActivity());
                    }
                }
            }
        });
        if (CommonMethods.isNetworkConnected(getActivity())) {
            isRefreshing = true;
            swipeRefresh.setRefreshing(true);
            loadNotificationsList();
        } else {
            tvNoNoti.setVisibility(View.VISIBLE);
            tvNoNoti.setText("Internet Not Connected");
        }

    }

    private void loadNotificationsList() {

        RestClient.get().NotificationListResponse().enqueue(new Callback<List<NotificationsLists>>() {
            @Override
            public void onResponse(Call<List<NotificationsLists>> call,
                                   Response<List<NotificationsLists>>
                                           response) {
                isRefreshing = false;
                swipeRefresh.setRefreshing(false);
                try {
                    if (response.code() == 200 && response.body() != null) {

                        ApplicationGlobal.prefsManager.setNotificationId(notification_Id);
                        notificationsList.clear();
                        notificationsList.addAll(response.body());
                        historyAdapter.notifyDataSetChanged();
                        if (notificationsList.size() == 0) {
                            tvNoNoti.setVisibility(View.VISIBLE);
                            tvNoNoti.setText(getString(R.string.noNotification));
                        } else tvNoNoti.setVisibility(View.GONE);
                    } else
                        CommonMethods.showErrorMessage(getActivity(),
                                response.errorBody());

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<List<NotificationsLists>> call, Throwable t) {
                CommonMethods.showErrorToast(getActivity());
                isRefreshing = false;
                swipeRefresh.setRefreshing(false);
            }
        });

    }
}

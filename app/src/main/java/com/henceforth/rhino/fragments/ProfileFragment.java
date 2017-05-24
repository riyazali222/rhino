package com.henceforth.rhino.fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.henceforth.rhino.R;
import com.henceforth.rhino.adapters.AddVehicleAdapter;
import com.henceforth.rhino.utills.AddVehicles;
import com.henceforth.rhino.utills.ApplicationGlobal;
import com.henceforth.rhino.utills.CommonMethods;
import com.henceforth.rhino.utills.Constants;
import com.henceforth.rhino.utills.PrefsManager;
import com.henceforth.rhino.webServices.ProfileData;
import com.henceforth.rhino.webServices.apis.APIs;
import com.henceforth.rhino.webServices.apis.RestClient;
import com.henceforth.rhino.webServices.pojo.EditProfile;
import com.henceforth.rhino.webServices.pojo.NotificationsLists;
import com.henceforth.rhino.webServices.pojo.ProfileList;
import com.henceforth.rhino.webServices.pojo.RemoveVehicles;
import com.henceforth.rhino.webServices.pojo.VehicleListing;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileFragment extends Fragment {
    @BindView(R.id.tvCustomerId) TextView tvCustomerId;
    @BindView(R.id.etName) TextView etName;
    @BindView(R.id.tvEmail) TextView tvEmail;
    @BindView(R.id.tvPhone) TextView tvPhone;
    @BindView(R.id.tvAddress) TextView tvAddress;
    @BindView(R.id.tvCity) TextView tvCity;
    @BindView(R.id.tvState) TextView tvState;
    @BindView(R.id.tvCountry) TextView tvCountry;
    @BindView(R.id.lin_lay) LinearLayout linLay;
    @BindView(R.id.ivProfile) ImageView ivProfile;
    @BindView(R.id.btnAdd) Button btnAdd;
    @BindView(R.id.relativeLayout) RelativeLayout relativeLayout;
    @BindView(R.id.rvVehicles) RecyclerView rvVehicles;
    @BindView(R.id.ivEdit) ImageView ivEdit;

    ArrayList<ProfileData> profileDataList = new ArrayList<>();
    String delete;
    AddVehicleAdapter addVehicleAdapter;
    List<VehicleListing> list = new ArrayList<>();
    VehicleListing listing;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        profiledata();
        Intent intent=new Intent("Profile");
        EditProfile editProfile = new Gson().fromJson(ApplicationGlobal.prefsManager.getProfile()
                , EditProfile.class);
        intent.putExtra("Image",editProfile.getImage());
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    private void profiledata() {
        if (!ApplicationGlobal.prefsManager.getProfile().isEmpty()) {
            EditProfile editProfile = new Gson().fromJson(ApplicationGlobal
                    .prefsManager.getProfile(), EditProfile.class);

            etName.setText(" " +editProfile.getFirstname()+ " " + editProfile.getMiddlename()
                    + " "
                    + editProfile.getLastname() );
            tvCustomerId.setText(" " +editProfile.getCustomerId());
            if(!editProfile.getAddress1().isEmpty() && !editProfile.getAddress3().isEmpty() &&
                    editProfile.getAddress2().isEmpty())
            tvAddress.setText(" " +editProfile.getAddress1() + ", " + editProfile.getAddress2() + ""
                    + editProfile.getAddress3() + " ");
            else if(!editProfile.getAddress1().isEmpty() && editProfile.getAddress3().isEmpty() &&
                    editProfile.getAddress2().isEmpty())
                tvAddress.setText(" " +editProfile.getAddress1() + "" + editProfile.getAddress2() + ""
                        + editProfile.getAddress3() + " ");
            else if(!editProfile.getAddress1().isEmpty() && editProfile.getAddress3().isEmpty() &&
                    !editProfile.getAddress2().isEmpty()){
                tvAddress.setText(" " +editProfile.getAddress1() + " " + editProfile.getAddress2() + ", "
                        + editProfile.getAddress3() + " ");
            }
            else if(!editProfile.getAddress1().isEmpty() && !editProfile.getAddress3().isEmpty() &&
                    !editProfile.getAddress2().isEmpty()){
                tvAddress.setText(" " +editProfile.getAddress1() + ", " + editProfile.getAddress2() + ", "
                        + editProfile.getAddress3() + " ");
            }
            else tvAddress.setText("");
            tvPhone.setText(" " +editProfile.getPhoneNo());
            tvCity.setText(" " +editProfile.getCity());
            tvState.setText(" " +editProfile.getState());
            tvCountry.setText(" " +editProfile.getCountry());
            tvEmail.setText(" " +editProfile.getEmail());
            Glide.with(getActivity()).load(editProfile.getImage()).centerCrop().into(ivProfile);
        }
    }

    private void hitVehicleListingApi() {


        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIs VehiclesApi = retrofit.create(APIs.class);
        final Call<List<VehicleListing>> responce = VehiclesApi.VehicleListingResponse(
                ApplicationGlobal.prefsManager.getSessionID());
        responce.enqueue(new Callback<List<VehicleListing>>() {
            @Override
            public void onResponse(Call<List<VehicleListing>> call, Response<List<VehicleListing>> response) {

                try {
                    if (response.code() == 200 && response.body() != null) {

                        list.clear();
                        list.addAll(response.body());
                        addVehicleAdapter.notifyDataSetChanged();

                    } else
                        CommonMethods.showErrorMessage(getActivity(),
                                response.errorBody());

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<List<VehicleListing>> call, Throwable t) {
            }


        });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        // hitVehicleListingApi();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        hitVehicleListingApi();
    }

    private void DeleteVehicle(String id, String del) {
        CommonMethods.showProgressDialog(getActivity());
        RestClient.get().DeleteVehiclesResponse(id, "delete")
                .enqueue(new Callback<RemoveVehicles>() {
                    @Override
                    public void onResponse(Call<RemoveVehicles> call,
                                           Response<RemoveVehicles> response) {

                        CommonMethods.dismissProgressDialog();
                        try {
                            if (response.code() == 200 && response.body() != null) {
                                hitVehicleListingApi();
                                list.clear();
                                addVehicleAdapter.notifyDataSetChanged();

                            } else
                                CommonMethods.showErrorMessage(getActivity(),
                                        response.errorBody());
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onFailure(Call<RemoveVehicles> call, Throwable t) {
                       // CommonMethods.showErrorToast(getActivity());
                        CommonMethods.dismissProgressDialog();
                    }
                });
    }

    private void init() {
        Glide.with(getActivity())
                .load(ApplicationGlobal.prefsManager.getUserImage())
                .centerCrop()
                .into(ivProfile);
        btnAdd = (Button) getView().findViewById(R.id.btnAdd);
        rvVehicles = (RecyclerView) getView().findViewById(R.id.rvVehicles);
        rvVehicles.setLayoutManager(new LinearLayoutManager(getActivity()));
        addVehicleAdapter = new AddVehicleAdapter(getActivity(), list);
        rvVehicles.setAdapter(addVehicleAdapter);
        rvVehicles.setNestedScrollingEnabled(false);


    }


    @OnClick({R.id.btnAdd, R.id.ivEdit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_bottom)
                        .add(R.id.main_frame, new AddVehicleFragment()).addToBackStack("")
                        .commit();
                break;
            case R.id.ivEdit:
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_bottom)
                        .add(R.id.main_frame, new EditProfileFragment()).addToBackStack("")
                        .commit();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver,
                new IntentFilter("UPDATE"));

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
            if (intent.hasExtra("Data_New")) {
                ProfileData profileData = intent.getParcelableExtra("Data_New");
                profileDataList.addAll(ApplicationGlobal.prefsManager.getUserInfoList());
                etName.setText(profileData.getFname() + " " + profileData.getMname() + " "
                        + profileData.getLname());
                tvCustomerId.setText(profileData.getCustId());
                tvAddress.setText(profileData.getAdd1() + " " + profileData.getAdd2() + " "
                        + profileData.getAdd3() + " ");
                tvPhone.setText(profileData.getPhNo());
                tvCity.setText(profileData.getCity());
                tvState.setText(profileData.getState());
                tvCountry.setText(profileData.getCountry());
            } else if (intent.hasExtra("DELETE")) {
                String VehicleId = intent.getStringExtra("DELETE");
                DeleteVehicle(VehicleId, delete);
            } else if (intent.hasExtra("Update")) {

                profiledata();

            }
            else if(intent.hasExtra("PROFILE")){
                profiledata();

            }
            else if(intent.hasExtra("Updated_list")){
                hitVehicleListingApi();
            }
            else {

                hitVehicleListingApi();
            }


        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            VehicleListing listing = bundle.getParcelable("ADD_VEHICLE");
            hitVehicleListingApi();

        }
    }
}

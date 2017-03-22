package com.henceforth.rhino.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.ListPopupWindow;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.henceforth.rhino.R;
import com.henceforth.rhino.activities.VehicleMakeActivity;
import com.henceforth.rhino.activities.YearPickerActivity;
import com.henceforth.rhino.utills.ApiList;
import com.henceforth.rhino.utills.ApplicationGlobal;
import com.henceforth.rhino.utills.CommonMethods;
import com.henceforth.rhino.utills.Constants;
import com.henceforth.rhino.utills.LocationGetter;
import com.henceforth.rhino.webServices.apis.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceProviderFragment extends Fragment implements View.OnClickListener {
    EditText etLicence, etMileage, etVehicleModel, etRequestType,
            etPhoneNo;
    TextView tvVehicleMake, tvContactInfo, tvVehicleYear, etVehicleType;
    private LocationEnableRequest locationEnableRequest = new LocationEnableRequest();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_provider, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        etLicence = (EditText) getView().findViewById(R.id.etLicence);
        etMileage = (EditText) getView().findViewById(R.id.etMileage);
        etVehicleType = (TextView) getView().findViewById(R.id.etVehicleType);
        etRequestType = (EditText) getView().findViewById(R.id.etRequestType);
        etVehicleModel = (EditText) getView().findViewById(R.id.etVehicleModel);
        tvVehicleYear = (TextView) getView().findViewById(R.id.tvVehicleYear);
        etPhoneNo = (EditText) getView().findViewById(R.id.etPhoneNo);
        tvVehicleMake = (TextView) getView().findViewById(R.id.tvVehicleMake);
        tvContactInfo = (TextView) getView().findViewById(R.id.tvContactInfo);
        tvContactInfo.setOnClickListener(this);
        getView().findViewById(R.id.btnSubmit).setOnClickListener(this);
        tvVehicleMake.setOnClickListener(this);
        tvVehicleYear.setOnClickListener(this);
        etVehicleType.setOnClickListener(this);
        getActivity().registerReceiver(locationEnableRequest,
                new IntentFilter("LocationEnableRequest"));

        //set different color for phone and dial

        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString str1 = new SpannableString(getString(R.string.dial));
        str1.setSpan(new ForegroundColorSpan(Color.WHITE), 0, str1.length(), 0);
        builder.append(str1);

        SpannableString str2 = new SpannableString(getString(R.string.dial_no));
        str2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(),
                R.color.toolbar_background)), 0, str2.length(), 0);
        builder.append(str2);

        tvContactInfo.setText(builder, TextView.BufferType.SPANNABLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvVehicleMake:
                Intent intent = new Intent(getActivity(), VehicleMakeActivity.class);
                startActivityForResult(intent, 2);
                break;

            case R.id.btnSubmit:
                CommonMethods.hideKeyboard(getActivity());
                if (etLicence.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterRegistrationNumber));
                else if (etMileage.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterVehicleMilage));
                else if (etVehicleType.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterVehicleType));
                else if (etRequestType.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterVehicleRequestType));
                else if (tvVehicleMake.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.selectVehicleCompany));
                else if (etVehicleModel.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterVehicleModel));
                else if (tvVehicleYear.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterVehicleYear));
                else if (etPhoneNo.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterPhoneNumber));
                else {
                    if (CommonMethods.isNetworkConnected(getActivity()))
                        checkLocation();

                    else
                        CommonMethods.showInternetNotConnectedToast(getActivity());

                }
                break;

            case R.id.etVehicleType:
                showPopupWindow(etVehicleType, R.array.array_list);
                break;

            case R.id.tvContactInfo:
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + 1800243394));
                startActivity(callIntent);
                break;

            case R.id.tvVehicleYear:
                Intent j = new Intent(getActivity(), YearPickerActivity.class);
                startActivityForResult(j, 3);
                break;
        }
    }

    private void showPopupWindow(final TextView tv, final int array) {
        final ListPopupWindow popupWindow = new ListPopupWindow(getActivity());
        popupWindow.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.vehicle_type_popup,
                R.id.tvRow, getResources().getStringArray(array)));
        popupWindow.setAnchorView(tv);
        popupWindow.setWidth(ListPopupWindow.WRAP_CONTENT);
        popupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
        popupWindow.setModal(true);
        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tv.setText(getResources().getStringArray(array)[i]);
                popupWindow.dismiss();
            }
        });
        popupWindow.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            String name = data.getStringExtra("name");
            Integer id = data.getIntExtra("id", 1);
            ApplicationGlobal.prefsManager.setVehicleId(id);
            tvVehicleMake.setText(name);
        } else if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            String name = data.getStringExtra("year");
            tvVehicleYear.setText(name);
        }
    }


    private void requestServicesResponse(String license_plate_no,
                                         String vehicle_mileage, String type_of_vehicle,
                                         String request_type,
                                         String vehicle_model, String vehicle_year, String phone_no) {

        RestClient.get().requestServicesResponse(license_plate_no, vehicle_mileage,
                type_of_vehicle, request_type, vehicle_model, vehicle_year,
                phone_no, ApplicationGlobal.myLat, ApplicationGlobal.myLng).enqueue(new Callback<ApiList>() {
            @Override
            public void onResponse(Call<ApiList> call, Response<ApiList> response) {
                CommonMethods.dismissProgressDialog();
                try {
                    if (response.code() == 200 & response.body() != null) {

                        Toast.makeText(getActivity(), response.body().getmessage(),
                                Toast.LENGTH_SHORT).show();

                    } else
                        CommonMethods.showErrorMessage(getActivity(), response.errorBody());
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ApiList> call, Throwable t) {
                CommonMethods.dismissProgressDialog();
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.ENABLE_LOCATION_PERMISSION)
            checkLocation();
    }

    private void checkLocation() {
        if (CommonMethods.isLocationEnabled(getActivity())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    ActivityCompat.checkSelfPermission
                            (getActivity(),
                                    Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        Constants.ENABLE_LOCATION_PERMISSION);

                return;
            }
            ApplicationGlobal.isGettingLocation = true;
            CommonMethods.showProgressDialog(getActivity());
            new LocationGetter(getActivity());
            getLocation();
        } else
            CommonMethods.displayLocationSettingsRequest(getActivity());
    }

    private void getLocation() {
        if (ApplicationGlobal.myLat == 0)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getLocation();
                }
            }, 500);
        else {
            requestServicesResponse(etLicence.getText().toString(),
                    etMileage.getText().toString(), etVehicleType.getText().toString(),
                    etRequestType.getText().toString(), etVehicleModel.getText().toString(),
                    tvVehicleYear.getText().toString() + "-01-01", etPhoneNo.getText().toString());
        }
    }

    private class LocationEnableRequest extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            checkLocation();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(locationEnableRequest);
    }
}

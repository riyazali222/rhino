package com.henceforth.rhino.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ListPopupWindow;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.henceforth.rhino.R;
import com.henceforth.rhino.activities.LoginActivity;
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
    Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_provider, container, false);

        etLicence = (EditText) view.findViewById(R.id.etLicence);
        etMileage = (EditText) view.findViewById(R.id.etMileage);
        etVehicleType = (TextView) view.findViewById(R.id.etVehicleType);
        etRequestType = (EditText) view.findViewById(R.id.etRequestType);
        //etVehicleId = (EditText) view.findViewById(R.id.etVehicleId);
        etVehicleModel = (EditText) view.findViewById(R.id.etVehicleModel);
        tvVehicleYear = (TextView) view.findViewById(R.id.tvVehicleYear);
        etPhoneNo = (EditText) view.findViewById(R.id.etPhoneNo);
        tvVehicleMake = (TextView) view.findViewById(R.id.tvVehicleMake);
        tvContactInfo = (TextView) view.findViewById(R.id.tvContactInfo);
        view.findViewById(R.id.tvContactInfo).setOnClickListener(this);
        view.findViewById(R.id.btnSubmit).setOnClickListener(this);
        view.findViewById(R.id.tvVehicleMake).setOnClickListener(this);
        view.findViewById(R.id.tvVehicleYear).setOnClickListener(this);
        view.findViewById(R.id.etVehicleType).setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvVehicleMake:
                Intent intent = new Intent(getActivity(), VehicleMakeActivity.class);
                startActivityForResult(intent, 2);


                break;
            case R.id.btnSubmit:
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                String license_plate_no = etLicence.getText().toString();
                String vehicle_mileage = etMileage.getText().toString();
                String type_of_vehicle = etVehicleType.getText().toString();
                String request_type = etRequestType.getText().toString();
                Integer vehicle_make_id = ApplicationGlobal.prefsManager.getVehicleId();
                String vehicle_model = etVehicleModel.getText().toString();
                String vehicle_year = tvVehicleYear.getText().toString() + "-01-01";
                String phone_no = etPhoneNo.getText().toString();
                if (license_plate_no.isEmpty()) {
                    Toast.makeText(getActivity(), "Licence Plate of Vehicle is Empty", Toast.LENGTH_SHORT).show();
                } else if (vehicle_mileage.isEmpty()) {
                    Toast.makeText(getActivity(), "Vehicle Mileage is Empty", Toast.LENGTH_SHORT).show();
                } else if (type_of_vehicle.isEmpty()) {
                    Toast.makeText(getActivity(), "Type of Vehicle is Empty", Toast.LENGTH_SHORT).show();
                } else if (request_type.isEmpty()) {
                    Toast.makeText(getActivity(), "Vehicle Request Type is Empty", Toast.LENGTH_SHORT).show();
                } else if (vehicle_make_id == null) {
                    Toast.makeText(getActivity(), "Select a Vehicle", Toast.LENGTH_SHORT).show();
                } else if (vehicle_model.isEmpty()) {
                    Toast.makeText(getActivity(), "Vehicle Model is Empty", Toast.LENGTH_SHORT).show();
                } else if (vehicle_year.isEmpty()) {
                    Toast.makeText(getActivity(), "Vehicle Year is Empty", Toast.LENGTH_SHORT).show();
                } else if (phone_no.isEmpty()) {
                    Toast.makeText(getActivity(), "Phone Number is Empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (CommonMethods.isNetworkConnected(getActivity())) {
                        checkLocation();

                    } else {
                        Toast.makeText(getActivity(), "Internet not connected", Toast.LENGTH_SHORT).show();
                    }
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

        if (resultCode == 2) {
            String name = data.getStringExtra("name");
            Integer id = data.getIntExtra("id", 1);
            ApplicationGlobal.prefsManager.setVehicleId(id);
            tvVehicleMake.setText(name);
        } else {
            String name = data.getStringExtra("year");
            tvVehicleYear.setText(name);
        }
    }


    private void requestServicesResponse(String license_plate_no,
                                         String vehicle_mileage, String type_of_vehicle,
                                         String request_type,
                                         String vehicle_model, String vehicle_year, String phone_no,
                                         double lat, double lng) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.string_title_upload_progressbar_));
        progressDialog.show();


        RestClient.get().requestServicesResponse(license_plate_no, vehicle_mileage,
                type_of_vehicle, request_type, vehicle_model, vehicle_year,
                phone_no, lat, lng).enqueue(new Callback<ApiList>() {
            @Override
            public void onResponse(Call<ApiList> call, Response<ApiList> response) {
                progressDialog.dismiss();
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
                progressDialog.dismiss();
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
            new LocationGetter(getActivity());

            getLocation();
        } else CommonMethods.displayLocationSettingsRequest(getActivity());
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
            /*if (CommonMethods.isNetworkConnected(getActivity()))*/
            requestServicesResponse(etLicence.getText().toString(),
                    etMileage.getText().toString(), etVehicleType.getText().toString(),
                    etRequestType.getText().toString(),
                    etVehicleModel.getText().toString(),
                    tvVehicleYear.getText().toString() + "-01-01",
                    etPhoneNo.getText().toString(), ApplicationGlobal.myLat, ApplicationGlobal.myLng);

            /*requestServicesResponse(license_plate_no, vehicle_mileage, type_of_vehicle, request_type,
                    vehicle_make_id, vehicle_model,
                    vehicle_year, phone_no, lat, lng);*/
        }
    }


}

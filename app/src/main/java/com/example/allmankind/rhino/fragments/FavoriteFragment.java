package com.example.allmankind.rhino.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allmankind.rhino.R;
import com.example.allmankind.rhino.activities.VehicleMakeActivity;
import com.example.allmankind.rhino.utills.ApiList;
import com.example.allmankind.rhino.utills.ApplicationGlobal;
import com.example.allmankind.rhino.utills.CommonMethods;
import com.example.allmankind.rhino.utills.Constants;
import com.example.allmankind.rhino.utills.PrefsManager;
import com.example.allmankind.rhino.webServices.apis.APIs;
import com.example.allmankind.rhino.webServices.apis.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;
import static com.example.allmankind.rhino.R.id.tvVehicleMake;

public class FavoriteFragment extends Fragment implements View.OnClickListener {
    EditText etLicence, etMileage, etVehicleType, etRequestType, etVehicleId, etVehicleModel,
            etVehicleYear, etPhoneNo;
    TextView tvVehicleMake;
    Button btnSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        etLicence = (EditText) view.findViewById(R.id.etLicence);
        etMileage = (EditText) view.findViewById(R.id.etMileage);
        etVehicleType = (EditText) view.findViewById(R.id.etVehicleType);
        etRequestType = (EditText) view.findViewById(R.id.etRequestType);
        etVehicleId = (EditText) view.findViewById(R.id.etVehicleId);
        etVehicleModel = (EditText) view.findViewById(R.id.etVehicleModel);
        etVehicleYear = (EditText) view.findViewById(R.id.etVehicleYear);
        etPhoneNo = (EditText) view.findViewById(R.id.etPhoneNo);
        tvVehicleMake = (TextView) view.findViewById(R.id.tvVehicleMake);
        view.findViewById(R.id.btnSearch).setOnClickListener(this);
        view.findViewById(R.id.tvVehicleMake).setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvVehicleMake:
                /*Intent i = new Intent(getActivity(), VehicleMakeActivity.class);
                startActivity(i);*/
                Intent intent = new Intent(getActivity(), VehicleMakeActivity.class);
                startActivityForResult(intent, 2);


                break;
            case R.id.btnSearch:
                String vendor_id = "1";
                String product_id = "1";
                String service_id = "1";
                String vehicle_id = "1";
                String name = "Service 1";
                String license_plate_no = etLicence.getText().toString();
                String vehicle_mileage = etMileage.getText().toString();
                String type_of_vehicle = etVehicleType.getText().toString();
                String request_type = etRequestType.getText().toString();
                String vehicle_make_id = etVehicleId.getText().toString();
                String vehicle_model = etVehicleModel.getText().toString();
                String vehicle_year = etVehicleYear.getText().toString();
                String phone_no = etPhoneNo.getText().toString();
                String location = "Chandigarh";


                if (!license_plate_no.isEmpty() && !vehicle_mileage.isEmpty()
                        && !type_of_vehicle.isEmpty() && !request_type.isEmpty()
                        && !vehicle_make_id.isEmpty() && !vehicle_model.isEmpty()
                        && !vehicle_year.isEmpty() && !phone_no.isEmpty()) {

                    requestServicesResponse(vendor_id, product_id, service_id, vehicle_id, name,
                            license_plate_no, vehicle_mileage, type_of_vehicle, request_type,
                            vehicle_make_id, vehicle_model, vehicle_year, phone_no, location);
                } else {
                    Toast.makeText(getActivity(), "Fields are empty !", Toast.LENGTH_LONG).show();

                }

                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            tvVehicleMake.setText(ApplicationGlobal.prefsManager.getVehicleName());
        }
    }


    private void requestServicesResponse(String vendor_id, String product_id, String service_id,
                                         String vehicle_id, String name, String license_plate_no,
                                         String vehicle_mileage, String type_of_vehicle,
                                         String request_type, String vehicle_make_id,
                                         String vehicle_model, String vehicle_year,
                                         String phone_no, String location) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.string_title_upload_progressbar_));
        progressDialog.show();


        RestClient.get().requestServicesResponse(vendor_id, product_id,
                service_id, vehicle_id, name, license_plate_no, vehicle_mileage,
                type_of_vehicle, request_type, vehicle_make_id, vehicle_model, vehicle_year,
                phone_no, location).enqueue(new Callback<ApiList>() {
            @Override
            public void onResponse(Call<ApiList> call, Response<ApiList> response) {
                progressDialog.dismiss();
                try {
                    if (response.code() == 200 & response.body() != null) {

                        Toast.makeText(getActivity(), response.body().getmessage(),
                                Toast.LENGTH_LONG).show();

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

}
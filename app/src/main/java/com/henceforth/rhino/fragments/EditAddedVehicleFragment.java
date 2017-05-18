package com.henceforth.rhino.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.henceforth.rhino.R;
import com.henceforth.rhino.activities.VehicleMakeActivity;
import com.henceforth.rhino.activities.YearPickerActivity;
import com.henceforth.rhino.utills.AddVehicles;
import com.henceforth.rhino.utills.ApplicationGlobal;
import com.henceforth.rhino.utills.CommonMethods;
import com.henceforth.rhino.webServices.apis.RestClient;
import com.henceforth.rhino.webServices.pojo.VehicleListing;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.henceforth.rhino.activities.YearPickerActivity.year;
import static com.henceforth.rhino.utills.ApplicationGlobal.prefsManager;

public class EditAddedVehicleFragment extends Fragment implements View.OnClickListener {

    /*@BindView(R.id.toolbarAddVehicle) Toolbar toolbarAddVehicle;
    @BindView(R.id.etLicence) EditText etLicence;
    @BindView(R.id.etVIN) EditText etVIN;
    @BindView(R.id.etMemid) EditText etMemid;
    @BindView(R.id.etMileage) EditText etMileage;
    @BindView(R.id.etVehicleType) EditText etVehicleType;
    @BindView(R.id.tvBrandName) EditText tvBrandName;
    @BindView(R.id.etVehicleModel) EditText etVehicleModel;
    @BindView(R.id.tvVehicleYear) EditText tvVehicleYear;
    @BindView(R.id.buttonSubmit) Button buttonSubmit;*/
    Unbinder unbinder;
    private EditText etLicence, etMileage, etVIN, etMemid, etVehicleType, tvBrandName, etVehicleModel,
            tvVehicleYear;
    Toolbar toolbarAddVehicle;
    Button buttonSubmit;
    int userVehicleId,vehicleMakeId;
    String noPlate,mileage,vType,brandName,model,year,vIn,meMID;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_added_vehicle_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        toolbarAddVehicle = (Toolbar) view.findViewById(R.id.toolbarAddVehicle);
        toolbarAddVehicle.setNavigationIcon(R.drawable.ic_close_white);
        toolbarAddVehicle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        init();
    }

    private void init() {
        etMileage = (EditText) getView().findViewById(R.id.etMileage);
        etLicence = (EditText) getView().findViewById(R.id.etLicence);
        etVIN = (EditText) getView().findViewById(R.id.etVIN);
        etMemid = (EditText) getView().findViewById(R.id.etMemid);
        etVehicleModel = (EditText) getView().findViewById(R.id.etVehicleModel);
        etVehicleType = (EditText) getView().findViewById(R.id.etVehicleType);
        tvVehicleYear = (EditText) getView().findViewById(R.id.tvVehicleYear);
        tvBrandName = (EditText) getView().findViewById(R.id.tvBrandName);
        buttonSubmit = (Button) getView().findViewById(R.id.buttonSubmit);
        add();
        etVehicleType.setOnClickListener(this);
        tvBrandName.setOnClickListener(this);
        tvVehicleYear.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.etVehicleType:
                showPopupWindow(etVehicleType, R.array.array_list);
                break;

            case R.id.tvVehicleYear:
                Intent j = new Intent(getActivity(), YearPickerActivity.class);
                startActivityForResult(j, 3);
                break;
            case R.id.tvBrandName:
                Intent intent = new Intent(getActivity(), VehicleMakeActivity.class);
                startActivityForResult(intent, 2);
                break;

            case R.id.buttonSubmit:
                 noPlate = etLicence.getText().toString().trim();
                 mileage = etMileage.getText().toString();
                 vType = etVehicleType.getText().toString().trim();
                 brandName = tvBrandName.getText().toString().trim();
                 model = etVehicleModel.getText().toString().trim();
                 year = tvVehicleYear.getText().toString().trim();
                 vIn = etVIN.getText().toString().trim();
                 meMID = etMemid.getText().toString().trim();
                CommonMethods.hideKeyboard(getActivity());
                if (noPlate.isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterRegistrationNumber));
                else if (mileage.isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterVehicleMilage));
                else if (meMID.isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterMembershipId));
                else if (vIn.isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterVehicleIdNumber));
                else if (vType.isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterVehicleType));
                else if (brandName.isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterBrandName));
                else if (model.isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterVehicleModel));
                else if (year.isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterVehicleYear));
                else {
                    hitApi();

                }

                break;

        }

    }

    private void showPopupWindow(final TextView tv, final int array) {
        final ListPopupWindow popupWindow = new ListPopupWindow(getActivity());
        popupWindow.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.popup_vehicle_type,
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
            prefsManager.setVehicleBrandId(id);
            tvBrandName.setText(name);
        } else if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            String name = data.getStringExtra("year");
            tvVehicleYear.setText(name);
        }
    }

    private void add() {
        Bundle bundle = getArguments();
        AddVehicles vehicles = bundle.getParcelable("list_info");
        assert vehicles != null;
        userVehicleId=vehicles.getUser_vehicle_id();
        etLicence.setText(vehicles.getLicense_plate_no());
        etMemid.setText(vehicles.getMembership_id());
        etMileage.setText(vehicles.getVehicle_mileage().toString());
        etVehicleModel.setText(vehicles.getVehicle_model());
        etVehicleType.setText(vehicles.getType_of_vehicle());
        etVIN.setText(vehicles.getVehicle_identification_number());
        vehicleMakeId=vehicles.getVehicle_make_id();
        //tvBrandName.setText(vehicles.getVehicle_make_id().toString());
        tvVehicleYear.setText(vehicles.getVehicle_year().toString());
    }
    private void hitApi() {
        CommonMethods.showProgressDialog(getActivity());
        RestClient.get().AddVehicleApi(String.valueOf(userVehicleId), noPlate, vIn, meMID, mileage, vType,
                String.valueOf(vehicleMakeId), model,
                Integer.parseInt(year))
                .enqueue(new Callback<AddVehicles>() {
                    @Override
                    public void onResponse(Call<AddVehicles> call, Response<AddVehicles> response) {

                        CommonMethods.dismissProgressDialog();
                        try {
                            if (response.code() == 200 && response.body() != null) {
                                Toast.makeText(getActivity(), "Vehicle successfully edited",
                                        Toast.LENGTH_LONG).show();
                               /* VehicleListing listing = new VehicleListing(response.body().getUser_vehicle_id(),
                                        noPlate, vIn, meMID, Integer.valueOf(mileage), vType,
                                        ApplicationGlobal.prefsManager.getVehicleBrandId(), model,
                                        Integer.parseInt(year), ApplicationGlobal.prefsManager.getBrandName());
                                ProfileFragment pf = new ProfileFragment();
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("ADD_VEHICLE", listing);
                                pf.setArguments(bundle);
                                *//*getFragmentManager().beginTransaction().replace(R.id.main_frame, pf)
                                        .commit();*//*
                                getActivity().onBackPressed();
                                Intent i = new Intent("UPDATE");
                                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(i);*/
                                getActivity().onBackPressed();
                            }
                            else
                                CommonMethods.showErrorMessage(getActivity(),
                                        response.errorBody());
                            Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onFailure(Call<AddVehicles> call, Throwable t) {
                        Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
                        CommonMethods.dismissProgressDialog();
                    }
                });
    }
}

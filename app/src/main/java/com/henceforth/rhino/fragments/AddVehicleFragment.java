package com.henceforth.rhino.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.henceforth.rhino.utills.ApplicationGlobal.prefsManager;


public class AddVehicleFragment extends BaseFragment {
    @BindView(R.id.toolbarAddVehicle) Toolbar toolbarAddVehicle;
    @BindView(R.id.etLicence) EditText etLicence;
    @BindView(R.id.etMileage) EditText etMileage;
    @BindView(R.id.etVehicleType) EditText etVehicleType;
    @BindView(R.id.tvBrandName) EditText tvBrandName;
    @BindView(R.id.etVehicleModel) EditText etVehicleModel;
    @BindView(R.id.tvVehicleYear) EditText tvVehicleYear;
    @BindView(R.id.buttonSubmit) Button buttonSubmit;
    @BindView(R.id.etVIN) EditText etVIN;
    @BindView(R.id.etMemid) EditText etMemid;
    private Context mContext;


    private String noPlate, vIn, meMID, mileage, year, vType, brandName, model;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_vehicle;
    }

    protected void init() {
        toolbarAddVehicle = (Toolbar) getView().findViewById(R.id.toolbarAddVehicle);
        toolbarAddVehicle.setNavigationIcon(R.drawable.ic_close_white);
        toolbarAddVehicle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonMethods.hideKeyboard(getActivity());
                getActivity().onBackPressed();
            }
        });
    }


    @OnClick({R.id.etVehicleType, R.id.tvBrandName, R.id.tvVehicleYear, R.id.buttonSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.etVehicleType:
                showPopupWindow(etVehicleType, R.array.array_list);
                break;
            case R.id.tvBrandName:
                Intent intent = new Intent(getActivity(), VehicleMakeActivity.class);
                startActivityForResult(intent, 2);
                break;

            case R.id.tvVehicleYear:
                Intent j = new Intent(getActivity(), YearPickerActivity.class);
                startActivityForResult(j, 3);
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

    private void hitApi() {
        CommonMethods.showProgressDialog(getActivity());
        RestClient.get().AddVehicleApi("", noPlate, vIn, meMID, mileage, vType,
                String.valueOf(prefsManager.getVehicleBrandId()), model,
                Integer.parseInt(year),brandName)
                .enqueue(new Callback<AddVehicles>() {
                    @Override
                    public void onResponse(Call<AddVehicles> call, Response<AddVehicles> response) {

                        CommonMethods.dismissProgressDialog();
                        try {
                            if (response.code() == 200 && response.body() != null) {
                                Toast.makeText(getActivity(), "Vehicle Added successfully",
                                        Toast.LENGTH_LONG).show();
                               // AddVehicles addVehicles=AddVehicles()
                                VehicleListing listing = new VehicleListing(response.body().getUser_vehicle_id(),
                                        noPlate, vIn, meMID, Integer.valueOf(mileage), vType,
                                        ApplicationGlobal.prefsManager.getVehicleBrandId(), model,
                                        Integer.parseInt(year), brandName);
                                ProfileFragment pf = new ProfileFragment();
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("ADD_VEHICLE", listing);
                                pf.setArguments(bundle);
                                /*getFragmentManager().beginTransaction().replace(R.id.main_frame, pf)
                                        .commit();*/
                                getActivity().onBackPressed();
                                Intent i = new Intent("UPDATE");
                                LocalBroadcastManager.getInstance(mContext).sendBroadcast(i);
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
            ApplicationGlobal.prefsManager.setBrandName(name);
            tvBrandName.setText(name);
        } else if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            String name = data.getStringExtra("year");
            tvVehicleYear.setText(name);
        }
    }

}

package com.henceforth.rhino.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.henceforth.rhino.R;
import com.henceforth.rhino.activities.VehicleMakeActivity;
import com.henceforth.rhino.activities.YearPickerActivity;
import com.henceforth.rhino.utills.ApplicationGlobal;
import com.henceforth.rhino.utills.CommonMethods;
import com.henceforth.rhino.utills.VehiclesInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HOME on 5/1/2017.
 */

public class AddVehicleFragment extends Fragment {
    @BindView(R.id.toolbarAddVehicle)
    Toolbar toolbarAddVehicle;
    @BindView(R.id.etLicence)
    EditText etLicence;
    @BindView(R.id.etMileage)
    EditText etMileage;
    @BindView(R.id.etVehicleType)
    TextView etVehicleType;
    @BindView(R.id.tvBrandName)
    TextView tvBrandName;
    @BindView(R.id.etVehicleModel)
    EditText etVehicleModel;
    @BindView(R.id.tvVehicleYear)
    TextView tvVehicleYear;
    @BindView(R.id.buttonSubmit)
    Button buttonSubmit;
    List<VehiclesInfo> vehiclesInfoList = new ArrayList<>();
   public static String Licence, Mileage, VehicleType, BrandName, Model, VehicleYear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_vehicle, container, false);
        ButterKnife.bind(this, view);
        return view;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        toolbarAddVehicle = (Toolbar) getView().findViewById(R.id.toolbarAddVehicle);
        toolbarAddVehicle.setNavigationIcon(R.drawable.ic_close_white);
        toolbarAddVehicle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        Licence=etLicence.getText().toString();
        Mileage=etMileage.getText().toString();
        VehicleType=etVehicleType.getText().toString();
        BrandName=tvBrandName.getText().toString();
        Model=etVehicleModel.getText().toString();
        VehicleYear=tvVehicleYear.getText().toString();
        AddItems();
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
                CommonMethods.hideKeyboard(getActivity());
                if (etLicence.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterRegistrationNumber));
                else if (etMileage.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterVehicleMilage));
                else if (etVehicleType.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterVehicleType));
                else if (tvBrandName.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterBrandName));

                else if (etVehicleModel.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterVehicleModel));
                else if (tvVehicleYear.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterVehicleYear));


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
            tvBrandName.setText(name);
        } else if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            String name = data.getStringExtra("year");
            tvVehicleYear.setText(name);
        }
    }

    private void AddItems() {

    }
}

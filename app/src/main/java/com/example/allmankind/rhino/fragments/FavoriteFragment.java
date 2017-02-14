package com.example.allmankind.rhino.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.allmankind.rhino.R;
import com.example.allmankind.rhino.activities.VehicleMakeActivity;

public class FavoriteFragment extends Fragment {
    EditText etLicence, etMileage, etVehicleType, etRequestType, etVehicleId, etVehicleModel,
            etVehicleYear;
    TextView etVehicleMake;

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

        etVehicleMake = (TextView) view.findViewById(R.id.etVehicleMake);
        etVehicleMake.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), VehicleMakeActivity.class);
                startActivity(i);
            }
        });

        return view;
    }


}
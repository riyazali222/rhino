package com.henceforth.rhino.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.henceforth.rhino.R;
import com.henceforth.rhino.adapters.AddVehicleAdapter;
import com.henceforth.rhino.utills.VehiclesInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HOME on 5/1/2017.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {


    AddVehicleAdapter addVehicleAdapter;
    List<VehiclesInfo> infoList = new ArrayList<>();
    @BindView(R.id.ivProfile)
    ImageView ivProfile;
    @BindView(R.id.lin_lay)
    LinearLayout linLay;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.rvVehicles)
    RecyclerView rvVehicles;
    @BindView(R.id.ivEdit)
    ImageView ivEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        btnAdd = (Button) getView().findViewById(R.id.btnAdd);
        rvVehicles = (RecyclerView) getView().findViewById(R.id.rvVehicles);
        rvVehicles.setLayoutManager(new LinearLayoutManager(getActivity()));
        addVehicleAdapter = new AddVehicleAdapter(getActivity(), infoList);
        rvVehicles.setAdapter(addVehicleAdapter);
        addItems();
        //btnAdd.setOnClickListener(this);
    }

    private void addItems() {
        VehiclesInfo info = new VehiclesInfo(AddVehicleFragment.Licence, AddVehicleFragment.Mileage,
                AddVehicleFragment.VehicleType, AddVehicleFragment.BrandName,
                AddVehicleFragment.Model, AddVehicleFragment.VehicleYear);
        infoList.add(info);
        addVehicleAdapter.notifyDataSetChanged();


    }

    @Override
    public void onClick(View v) {

    }


    @OnClick({R.id.btnAdd, R.id.ivEdit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                getFragmentManager().beginTransaction()
                        .add(R.id.main_frame, new AddVehicleFragment()).addToBackStack("")
                        .commit();
                break;
            case R.id.ivEdit:
                getFragmentManager().beginTransaction()
                        .add(R.id.main_frame, new EditProfileFragment()).addToBackStack("")
                        .commit();
                break;
        }
    }
}

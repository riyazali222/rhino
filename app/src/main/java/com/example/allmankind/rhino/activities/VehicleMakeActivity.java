package com.example.allmankind.rhino.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.allmankind.rhino.R;
import com.example.allmankind.rhino.adapters.VehicleMakeAdapter;
import com.example.allmankind.rhino.utills.CommonMethods;
import com.example.allmankind.rhino.webServices.apis.RestClient;
import com.example.allmankind.rhino.webServices.pojo.ItemsList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VehicleMakeActivity extends AppCompatActivity {
    Toolbar toolbarVehMake;
    RecyclerView recyclerView;
    public VehicleMakeAdapter vehicleMakeAdapter;
    public EditText etSearch;
    private List<ItemsList> itemsListArrayList = new ArrayList<>();
    private List<ItemsList> listToDisplay = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_make);
        etSearch = (EditText) findViewById(R.id.etSearch);
        init();
        addTextListener();
        loadVehicleList();
    }

    private void init() {
        //initialise toolbar
        toolbarVehMake = (Toolbar) findViewById(R.id.toolbarVehMake);
        toolbarVehMake.setNavigationIcon(R.drawable.ic_toolbar_arrow);
        toolbarVehMake.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        // initialise recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        vehicleMakeAdapter = new VehicleMakeAdapter(this, listToDisplay);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(vehicleMakeAdapter);


    }

    private void loadVehicleList() {


        RestClient.get().VehicleListResponse().enqueue(new Callback<List<ItemsList>>() {
            @Override
            public void onResponse(Call<List<ItemsList>> call, Response<List<ItemsList>> response) {
                try {
                    if (response.code() == 200 && response.body() != null) {
                        itemsListArrayList.clear();
                        itemsListArrayList.addAll(response.body());
                        listToDisplay.clear();
                        listToDisplay.addAll(itemsListArrayList);
                        vehicleMakeAdapter.notifyDataSetChanged();
                    } else
                        CommonMethods.showErrorMessage(VehicleMakeActivity.this,
                                response.errorBody());
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<List<ItemsList>> call, Throwable t) {

            }
        });
        //  request.VehicleListResponse( ApplicationGlobal.prefsManager.getSessionId()).enqueue(new Callback<List<ItemsList>>() {


    }


    private void addTextListener() {

        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listToDisplay.clear();
                for (ItemsList items : itemsListArrayList) {
                    if (items.getVehicle_name().toLowerCase().contains(etSearch.getText().toString().toLowerCase())) {
                        // Adding Matched items
                        listToDisplay.add(items);
                    }

                }

                vehicleMakeAdapter.notifyDataSetChanged();

            }
        });
    }


}


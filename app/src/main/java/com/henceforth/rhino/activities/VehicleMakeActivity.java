package com.henceforth.rhino.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.henceforth.rhino.R;
import com.henceforth.rhino.adapters.VehicleMakeAdapter;
import com.henceforth.rhino.utills.CommonMethods;
import com.henceforth.rhino.utills.RecyclerItemClickListener;
import com.henceforth.rhino.utills.RestClient;
import com.henceforth.rhino.webServices.pojo.ItemsList;

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
    public static String name;
    public static Integer id;

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

        toolbarVehMake = (Toolbar) findViewById(R.id.toolbarVehMake);
        toolbarVehMake.setNavigationIcon(R.drawable.ic_close_white);
        toolbarVehMake.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        vehicleMakeAdapter = new VehicleMakeAdapter(this, listToDisplay);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(vehicleMakeAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        name = listToDisplay.get(position).getVehicle_name();
                        id=listToDisplay.get(position).getId();
                        Intent intent = new Intent();
                        intent.putExtra("name", name);
                        intent.putExtra("id",id);
                        setResult(Activity.RESULT_OK, intent);
                        finish();//finishing activity
                    }
                }));
    }

    private void loadVehicleList() {

        if(CommonMethods.isNetworkConnected(VehicleMakeActivity.this)) {

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
        }
        else {
            Toast.makeText(this,"Internet not connected",Toast.LENGTH_SHORT).show();
        }

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


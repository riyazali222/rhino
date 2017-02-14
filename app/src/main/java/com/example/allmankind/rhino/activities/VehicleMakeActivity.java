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
import android.widget.TextView;

import com.example.allmankind.rhino.R;
import com.example.allmankind.rhino.adapters.VehicleMakeAdapter;
import com.example.allmankind.rhino.utills.ItemsList;

import java.util.ArrayList;
import java.util.List;


public class VehicleMakeActivity extends AppCompatActivity {
    Toolbar toolbarVehMake;
    RecyclerView recyclerView;
    public VehicleMakeAdapter vehicleMakeAdapter;
    TextView toolbarTitle;
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
        prepareItemListData();
        listToDisplay.addAll(itemsListArrayList);
    }

    private void init() {
        //initialise toolbar
        toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
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
                    if (items.getname().toLowerCase().contains(etSearch.getText().toString().toLowerCase())) {
                        // Adding Matched items
                        listToDisplay.add(items);
                    }

                }

                vehicleMakeAdapter.notifyDataSetChanged();

            }
        });
    }


    private void prepareItemListData() {
        ItemsList il = new ItemsList("Audi");
        itemsListArrayList.add(il);

        il = new ItemsList("Mercedes");
        itemsListArrayList.add(il);

        il = new ItemsList("Ford");
        itemsListArrayList.add(il);

        il = new ItemsList("Swaraj mazda");
        itemsListArrayList.add(il);

        il = new ItemsList("Traveller");
        itemsListArrayList.add(il);

        il = new ItemsList("Aston Martin");
        itemsListArrayList.add(il);

        il = new ItemsList("Royal Royce");
        itemsListArrayList.add(il);

        il = new ItemsList("Lamborghini");
        itemsListArrayList.add(il);

        il = new ItemsList("Mahindra");
        itemsListArrayList.add(il);

        vehicleMakeAdapter.notifyDataSetChanged();
    }


}


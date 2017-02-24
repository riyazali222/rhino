
package com.henceforth.rhino.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.henceforth.rhino.R;
import com.henceforth.rhino.adapters.YearAdapter;
import com.henceforth.rhino.utills.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class YearPickerActivity extends AppCompatActivity {
    Toolbar toolbarVehYear;
    RecyclerView recyclerViewYear;
    YearAdapter yearListAdapter;
    public static String year;
    public EditText etSearchYear;
    private List<String> yearList =  new ArrayList<String>();
    public static int currentYear;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_picker);
        //etSearchYear = (EditText) findViewById(R.id.etSearchYear);
        //addTextListener();
        init();
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);



        for(int i=currentYear; i>1989; i--)
        {

            Integer item=Integer.valueOf(i);
            yearList.add(item.toString());
        }
        yearListAdapter.notifyDataSetChanged();

       // loadYearList();
    }


    private void init() {
        toolbarVehYear = (Toolbar) findViewById(R.id.toolbarVehYear);
        toolbarVehYear.setNavigationIcon(R.drawable.ic_toolbar_arrow);
        toolbarVehYear.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        recyclerViewYear = (RecyclerView) findViewById(R.id.recyclerViewYear);
        yearListAdapter = new YearAdapter(this, yearList);
        recyclerViewYear.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewYear.setAdapter(yearListAdapter);
        recyclerViewYear.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        year = yearList.get(position);
                        Intent intent = new Intent();
                        intent.putExtra("year", year);
                        setResult(3, intent);
                        finish();//finishing activity
                    }
                }));

    }


   /* private void loadYearList() {



        for (int i = currentYear; i > 1989; i--) {
            yearList.add(String.valueOf(i));
        }



        yearListAdapter.notifyDataSetChanged();
    }*/

}